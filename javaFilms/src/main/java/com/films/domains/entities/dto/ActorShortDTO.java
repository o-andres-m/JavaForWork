package com.films.domains.entities.dto;

import com.films.domains.entities.Actor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActorShortDTO{

	
	int actorId;
	
	String name;
	
	public ActorShortDTO from(Actor actor) {
		return new ActorShortDTO(actor.getActorId(),actor.getFirstName()+ " "+ actor.getLastName());
	}
	

}
