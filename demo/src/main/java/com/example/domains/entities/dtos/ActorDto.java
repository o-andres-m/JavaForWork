package com.example.domains.entities.dtos;

import com.example.domains.entities.Actor;

import lombok.Value;

//Si le ponemos VALUE es como un "DATA" pero sin SETTERS y con un constructor completo
//Esto es porque sera INMUTABLE, es decir, no lo podremos modificar...
@Value

/*
 * Si le ponemos @Data, sera MUTABLE, y habria que ponerle el @AllArgsConstructor
 * y el @NoArgsConstructor. Porque tiene por defecto solo el constructor SIN PARAMETROS
 * Y al ponerle el @AllArgsConstructor es necesario meterle el @Noargs.. para que
 * tenga ambos.
 */
public class ActorDto {
	
	private int actorId;

	private String firstName;

	private String lastName;
	
	
	
	public static ActorDto from(Actor target) {
		
		return new ActorDto(target.getActorId(),target.getFirstName(),target.getLastName());
		
	}
	
	
	public static Actor from(ActorDto target) {
		
		return new Actor(target.getActorId(),target.getFirstName(),target.getLastName());
		
	}

}
