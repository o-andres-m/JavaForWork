package com.example.application.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.ActorDto;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.validation.Valid;


@RestController
@RequestMapping(path = {"/api/actores/v1", "/api/actors"})
//El v1 es el control de versiones
public class ActorResource {
	
	@Autowired
	ActorService srv;
	
	@GetMapping
	public List<ActorDto> findAll(){
		
		return srv.getByProjection(ActorDto.class);
	}
	
	@GetMapping(path = {"/{id}"})
	public ActorDto getOne(@PathVariable int id) throws NotFoundException{
		var actor = srv.getOne(id);
		if (actor.isEmpty()) throw new NotFoundException();
		return ActorDto.from(actor.get());
	}
	
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody ActorDto item) throws BadRequestException, DuplicateKeyException, InvalidDataException{
		
		//Se convierte a ENTIDAD
		var actor = ActorDto.from(item);
		
		//El ADD VALIDA EL ACTOR!! y lo guarda en DB
		srv.add(actor);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(actor.getActorId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	
	



}
