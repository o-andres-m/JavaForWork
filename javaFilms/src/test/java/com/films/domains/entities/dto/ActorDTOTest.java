package com.films.domains.entities.dto;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.films.domains.entities.Actor;


class ActorDTOTest {


	@Test
	void testInstanceActorDto() {
		
		var actor = new Actor();
		
		assertTrue(ActorDTO.from(actor) instanceof ActorDTO);
	}
	

	@Test
	void testInstanceActor() {
		
		var actor = new ActorDTO(0,null,null);
		
		assertTrue(ActorDTO.from(actor) instanceof Actor);
	}
	
	@Test
	void testAttributesDto() {
		
		var actor = new Actor();
		actor.setActorId(1);
		actor.setFirstName("FirstName");
		actor.setLastName("LASTNAME");
		
		var actorDto = ActorDTO.from(actor);
		
		assertAll("Attributes",
				()-> assertEquals(1, actorDto.getActorId()),
				()-> assertEquals("FirstName", actorDto.getFirstName()),
				()-> assertEquals("LASTNAME", actorDto.getLastName())
				);
	}
	
	@Test
	void testAttributesActor() {
		
		var actorDto = new ActorDTO(1,"FirstName","LASTNAME");
		
		var actor = ActorDTO.from(actorDto);
				
		assertAll("Attributes",
				()-> assertEquals(1, actor.getActorId()),
				()-> assertEquals("FirstName", actor.getFirstName()),
				()-> assertEquals("LASTNAME", actor.getLastName())
				);
	}
}
