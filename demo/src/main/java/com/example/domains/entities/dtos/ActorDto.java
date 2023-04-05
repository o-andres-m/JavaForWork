	package com.example.domains.entities.dtos;

import com.example.domains.entities.Actor;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

//Si le ponemos VALUE es como un "DATA" pero sin SETTERS y con un constructor completo
//Esto es porque sera INMUTABLE, es decir, no lo podremos modificar..

@Value

/*
 * Si le ponemos @Data, sera MUTABLE, y habria que ponerle el @AllArgsConstructor
 * y el @NoArgsConstructor. Porque tiene por defecto solo el constructor SIN PARAMETROS
 * Y al ponerle el @AllArgsConstructor es necesario meterle el @Noargs.. para que
 * tenga ambos.
 */
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class ActorDto {
	
	//@JsonProperty("id")
	private int actorId;
	
	//@JsonProperty("nombre")
	private String firstName;
	
	//@JsonProperty("apellido")
	private String lastName;
	
	
	
	public static ActorDto from(Actor target) {
		
		return new ActorDto(target.getActorId(),target.getFirstName(),target.getLastName());
		
	}
	
	
	public static Actor from(ActorDto target) {
		
		return new Actor(target.getActorId(),target.getFirstName(),target.getLastName());
		
	}

}
