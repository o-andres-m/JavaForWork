package com.films.application.resources;

import java.net.URI;
import java.sql.Timestamp;
import java.time.Instant;
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
import com.films.domains.entities.dto.FilmDetailsDTO;
import com.films.domains.entities.dto.FilmEditDTO;
import com.films.domains.entities.dto.FilmShortDTO;
import com.films.domains.entities.dto.ItemDto;

import io.swagger.v3.oas.annotations.Hidden;
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
	
	@Hidden
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
	
	@GetMapping(path = {"/{id}"}, params = {"mode=edit"})
	public FilmEditDTO getOneEdit(@PathVariable int id) throws NotFoundException{
		var film = srv.getOne(id);
		if (film.isEmpty()) throw new NotFoundException();
		return FilmEditDTO.from(film.get());
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
	
	
//	@GetMapping("/news")
//	public List<FilmShortDTO> getAllNews(@RequestParam (required = false) Integer days) {
//
//		
//		List<Film> filmList = new ArrayList<>();
//		
//		if(days==null || days<=0 ) {
//			
//			/**
//			 * Default Time: 864.000 -> 10 days	
//			 */
//			
//			filmList = srv.news(Timestamp.from(Instant.now().minusSeconds(864000)));
//		}else {
//
//			// 1 day = 86400 secs
//			
//			filmList = srv.news(Timestamp.from(Instant.now().minusSeconds(days*86400)));
//		}
//		return filmList.stream()
//				.map(item -> new FilmShortDTO(item.getFilmId(),item.getTitle()))
//				.toList();
//	}
	
	@GetMapping("/news")
	public List<FilmShortDTO> getAllNews(@RequestParam (required = false) Timestamp time) {
		if (time == null) {
			return srv.news(Timestamp.from(Instant.now().minusSeconds(864000)))
					.stream().map(film -> 
					new FilmShortDTO(film.getFilmId(),film.getTitle())).toList();
		}
		return srv.news(time).stream().map(film -> 
			new FilmShortDTO(film.getFilmId(),film.getTitle())).toList();
	}

}
