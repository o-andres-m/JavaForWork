package com.films.domains.core.entities.dto;

import com.films.domains.entities.Actor;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActorDto {

	@JsonProperty("id")
	private int actorId;
	
	@JsonProperty("nombre")
	private String firstName;
	
	@JsonProperty("apellido")
	private String lastName;
	
	
	
	public static ActorDto from(Actor target) {
		
		return new ActorDto(target.getActorId(),target.getFirstName(),target.getLastName());
		
	}
	
	
	public static Actor from(ActorDto target) {
		
		return new Actor(target.getActorId(),target.getFirstName(),target.getLastName());
		
	}
}
