package com.films.application.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.films.domains.contracts.services.FilmService;
import com.films.domains.core.exceptions.BadRequestException;
import com.films.domains.core.exceptions.DuplicateKeyException;
import com.films.domains.core.exceptions.InvalidDataException;
import com.films.domains.core.exceptions.NotFoundException;
import com.films.domains.entities.dto.ActorDTO;
import com.films.domains.entities.dto.FilmDetailsDTO;
import com.films.domains.entities.dto.FilmEditDTO;
import com.films.domains.entities.dto.FilmShortDTO;
import com.films.domains.entities.dto.ItemDto;

import jakarta.validation.Valid;


@RestController
@RequestMapping(path = {"/api/films/v1","/api/films"})
public class FilmResource {

	@Autowired
	FilmService srv;
	
	@GetMapping
	public List<FilmShortDTO> getAll(@RequestParam (required = false) String sort) {
		if(sort==null) return srv.getByProjection(FilmShortDTO.class);
		return (List<FilmShortDTO>) srv.getByProjection(Sort.by(sort), FilmShortDTO.class);
	}
	
	@GetMapping(params = "page")
	public Page<FilmShortDTO> getAllPageable(Pageable page) {
		return srv.getByProjection(page, FilmShortDTO.class);
	}
	
	@GetMapping(path = {"/{id}"})
	public FilmDetailsDTO getOne(@PathVariable int id) throws NotFoundException{
		var film = srv.getOne(id);
		if (film.isEmpty()) throw new NotFoundException();
		return FilmDetailsDTO.from(film.get());
	}
	
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody FilmEditDTO item) throws DuplicateKeyException, InvalidDataException{
		
		
		var film = srv.add(FilmEditDTO.from(item));
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(film.getFilmId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping(path = {"/{id}/actors"}) 
	public List<ItemDto<Integer, String>> getActorsFilms(@PathVariable int id) throws NotFoundException{
		var film = srv.getOne(id);
		if (film.isEmpty()) throw new NotFoundException();
		
		return film.get().getActors().stream()
				.map(o -> new ItemDto<>(o.getActorId(), o.getFirstName()+' ' +o.getLastName()))
				.toList();
	}
	
	@GetMapping(path = {"/{id}/categories"}) 
	public List<ItemDto<Integer, String>> getCategoriesFilms(@PathVariable int id) throws NotFoundException{
		var film = srv.getOne(id);
		if (film.isEmpty()) throw new NotFoundException();
		
		return film.get().getCategories().stream()
				.map(o -> new ItemDto<>(o.getCategoryId(), o.getName()))
				.toList();
	}
	
	
	@PutMapping("/{id}")
	@ResponseStatus (HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody FilmEditDTO item) throws BadRequestException, NotFoundException, InvalidDataException {
		if (id != item.getFilmId()) throw new BadRequestException("IDS doesn't match");
		srv.modify(FilmEditDTO.from(item));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}
	

}
