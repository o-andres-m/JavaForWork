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

import com.films.domains.contracts.services.ActorService;
import com.films.domains.core.exceptions.BadRequestException;
import com.films.domains.core.exceptions.DuplicateKeyException;
import com.films.domains.core.exceptions.InvalidDataException;
import com.films.domains.core.exceptions.NotFoundException;
import com.films.domains.entities.dto.ActorDTO;
import com.films.domains.entities.dto.ActorShort;
import com.films.domains.entities.dto.ElementoDto;

import jakarta.validation.Valid;


@RestController
@RequestMapping(path = {"/api/category/v1", "/api/category"})
public class CategoryResource {
	
	@Autowired
	private ActorService srv;

	@GetMapping
	public List<ActorShort> getAll(
								@RequestParam (required = false) String sort
								) {
		if (sort != null) {
			return (List<ActorShort>)srv.getByProjection(Sort.by(sort) , ActorShort.class);
		}

		return srv.getByProjection(ActorShort.class);
	}
	
	
	
	@GetMapping(params = "page")
	public Page<ActorDTO> getAllPageable(Pageable page) {
		return srv.getByProjection(page, ActorDTO.class);
	}
	
	
	@GetMapping(path = {"/{id:\\d+}"})
	public ActorDTO getOne(@PathVariable int id) throws NotFoundException{
		var actor = srv.getOne(id);
		if (actor.isEmpty()) throw new NotFoundException();
		return ActorDTO.from(actor.get());
	}
	
	
	@GetMapping(path = {"/{id}/pelis"}) 
	public List<ElementoDto<Integer, String>> getActorFilms(@PathVariable int id) throws NotFoundException{
		var actor = srv.getOne(id);
		if (actor.isEmpty()) throw new NotFoundException();
		
		return actor.get().getFilmActors().stream()
				.map(o -> new ElementoDto<>(o.getFilm().getFilmId(), o.getFilm().getTitle()))
				.toList();
	}
	
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody ActorDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException{
		
		var actor = ActorDTO.from(item);
		
		srv.add(actor);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(actor.getActorId()).toUri();

		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{id}")
	@ResponseStatus (HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody ActorDTO item) throws BadRequestException, NotFoundException, InvalidDataException {
		if (id != item.getActorId()) throw new BadRequestException("No coincide ID");
		srv.modify(ActorDTO.from(item));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}

}