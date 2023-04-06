package com.example.application.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.ActorDto;
import com.example.domains.entities.dtos.ActorShort;
import com.example.domains.entities.dtos.ElementoDto;
import com.example.domains.services.ActorServiceImpl;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@RestController
@RequestMapping(path = {"/api/actores/v1", "/api/actores"})
//El v1 es el control de versiones
public class ActorResource {
	
	@Autowired
	private ActorService srv;

	@GetMapping
	public List<ActorShort> getAll(
								@RequestParam (required = false) String sort
								) {
		if (!sort.isEmpty()) {
			return (List<ActorShort>)srv.getByProjection(Sort.by(sort) , ActorShort.class);
		}

		return srv.getByProjection(ActorShort.class);
	}
	
	
	
	@GetMapping(params = "page")
	public Page<ActorDto> getAllPageable(Pageable page) {
		return srv.getByProjection(page, ActorDto.class);
	}
	
	
	@GetMapping(path = {"/{id:\\d+}"}) //El \\d+ es el regex de solo numeros y positivos
	//Si le mandamos un STRING ahora a esta ruta, va a decir que NO PERMITE EL METODO GET
	//porque en realidad, estariamos usando otra ruta, porque no entra por aqui...
	public ActorDto getOne(@PathVariable int id) throws NotFoundException{
		var actor = srv.getOne(id);
		if (actor.isEmpty()) throw new NotFoundException();
		return ActorDto.from(actor.get());
	}
	
	
	@GetMapping(path = {"/{id}/pelis"}) 
	@Transactional  //TRANSACTIONAL!!! SIno no puede leer las peliculas del actor!!
	public List<ElementoDto<Integer, String>> getActorFilms(@PathVariable int id) throws NotFoundException{
		var actor = srv.getOne(id);
		if (actor.isEmpty()) throw new NotFoundException();
		
		return actor.get().getFilmActors().stream()
				.map(o -> new ElementoDto<>(o.getFilm().getFilmId(), o.getFilm().getTitle()))
				.toList();
	}
	
	@PostMapping
	//ojo! usa el @Valid, pero como "ActorDTO" no tiene validaciones, pasaria igual..
	// Despues cuando pasa por srv.add(actor) ahi si valida el metodo add..
	//El response entity es para "Generar" la respuesta
	public ResponseEntity<Object> create(@Valid @RequestBody ActorDto item) throws BadRequestException, DuplicateKeyException, InvalidDataException{
		
		//Se convierte a ENTIDAD
		var actor = ActorDto.from(item);
		
		//El ADD VALIDA EL ACTOR!! y lo guarda en DB
		srv.add(actor);
		
		//Crea la cabecera LOCATION, la crea con el ID que obtiene del actor guardado
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(actor.getActorId()).toUri();
		
		//Aca responde entiti, crea la respuesta con el build. Le estamos mandando el
		//location que creamos arriba, que es una URI.
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{id}")
	@ResponseStatus (HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody ActorDto item) throws BadRequestException, NotFoundException, InvalidDataException {
		//Verifica que coincidan los ID del mapping y del OBJ DTO a modificar
		if (id != item.getActorId()) throw new BadRequestException("No coincide ID");
		// Actualiza el Actor
		srv.modify(ActorDto.from(item));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}

}
