package com.example.application.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.dtos.ActorDto;
import com.example.domains.entities.dtos.FilmDetailsDTO;
import com.example.domains.entities.dtos.FilmEditDTO;
import com.example.domains.entities.dtos.FilmShortDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/film/v1")
public class FilmResource {

	@Autowired
	FilmService srv;
	
	@GetMapping
	public List<FilmShortDTO> getAll() {
		return srv.getByProjection(FilmShortDTO.class);
	}
	
	@GetMapping(path = {"/{id}"})
	public FilmDetailsDTO getOne(@PathVariable int id) throws NotFoundException{
		var film = srv.getOne(id);
		if (film.isEmpty()) throw new NotFoundException();
		return FilmDetailsDTO.from(film.get());
	}
	
//	@PostMapping
//	public ResponseEntity<Object> create(@Valid @RequestBody FilmDto item) throws BadRequestException, DuplicateKeyException, InvalidDataException{
//		
//		
//		srv.add(FilmDto.from(item));
//		
//		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//				.path("/{id}")
//				.buildAndExpand(item.getFilmId()).toUri();
//		
//		return ResponseEntity.created(location).build();
//	}
//	
//	@PutMapping("/{id}")
//	@ResponseStatus (HttpStatus.NO_CONTENT)
//	public void update(@PathVariable int id, @Valid @RequestBody ActorDto item) throws BadRequestException, NotFoundException, InvalidDataException {
//		//Verifica que coincidan los ID del mapping y del OBJ DTO a modificar
//		if (id != item.getActorId()) throw new BadRequestException("No coincide ID");
//		// Actualiza el Actor
//		srv.modify(ActorDto.from(item));
//	}
//	
	
//	@DeleteMapping("/{id}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void delete(@PathVariable int id) {
//		srv.deleteById(id);
//	}
	

}
