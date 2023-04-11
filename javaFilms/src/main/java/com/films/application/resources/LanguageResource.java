package com.films.application.resources;

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

import com.films.domains.contracts.services.LanguageService;
import com.films.domains.core.exceptions.BadRequestException;
import com.films.domains.core.exceptions.DuplicateKeyException;
import com.films.domains.core.exceptions.InvalidDataException;
import com.films.domains.core.exceptions.NotFoundException;
import com.films.domains.entities.Category;
import com.films.domains.entities.Language;
import com.films.domains.entities.dto.ItemDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = {"/api/language/v1","/api/language"})
public class LanguageResource {
	
	@Autowired
	LanguageService srv;
	
	
	@GetMapping
	public List<Language> getAll() {
		return srv.getByProjection(Language.class);
	}
//	
//	@GetMapping(path = {"/{id:\\d+}"})
//	public Category getOne(@PathVariable int id) throws NotFoundException{
//		var cat = srv.getOne(id);
//		if (cat.isEmpty()) throw new NotFoundException();
//		return cat.get();
//	}	
//	
//	@GetMapping(path = {"/{id}/films"}) 
//	public List<ItemDto<Integer, String>> getActorFilms(@PathVariable int id) throws NotFoundException{
//		var cat = srv.getOne(id);
//		if (cat.isEmpty()) throw new NotFoundException();
//		
//		return cat.get().getFilmCategories().stream()
//				.map(o -> new ItemDto<>(o.getFilm().getFilmId(), o.getFilm().getTitle()))
//				.toList();
//	}
//
//	@PostMapping
//	public ResponseEntity<Object> create(@Valid @RequestBody Category item) throws BadRequestException, DuplicateKeyException, InvalidDataException{
//				
//		srv.add(item);
//		
//		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//				.path("/{id}")
//				.buildAndExpand(item.getCategoryId()).toUri();
//
//		return ResponseEntity.created(location).build();
//	}
//
//	@PutMapping("/{id}")
//	@ResponseStatus (HttpStatus.NO_CONTENT)
//	public void update(@PathVariable int id, @Valid @RequestBody Category item) throws BadRequestException, NotFoundException, InvalidDataException {
//		if (id != item.getCategoryId()) throw new BadRequestException("IDs doesnt match");
//		srv.modify(item);
//	}
//	
//	@DeleteMapping("/{id}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void delete(@PathVariable int id) {
//		srv.deleteById(id);
//	}


}
