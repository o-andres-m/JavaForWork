package com.films.domains.entities.dto;

import com.films.domains.entities.Actor;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Value
public class ActorDTO {
	
	@JsonProperty("id")
	private int actorId;
	
	@JsonProperty("nombre")
	private String firstName;
	
	@JsonProperty("apellidos")
	private String lastName;
	
	
	public static ActorDTO from(Actor actor) {
		return new ActorDTO(actor.getActorId(), actor.getFirstName(), actor.getLastName());
	}
	

	public static Actor from(ActorDTO actorDTO) {
		return new Actor(actorDTO.getActorId(), actorDTO.getFirstName(), actorDTO.getLastName());
	}

}
