package com.films.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


class ActorTest {

	@Nested
	class Constructors{
		
		@Test
		void testConstructor() {
			var actor = new Actor(1);
			
			assertNotNull(actor);
			assertEquals(1, actor.getActorId());
		}
		

		@Test
		void testFullConstructor() {
			var actor = new Actor(1,"First","Last");
			
			assertNotNull(actor);
			assertAll("Attributes",
					()-> assertEquals(1, actor.getActorId()),
					()-> assertEquals("First", actor.getFirstName()),
					()-> assertEquals("Last", actor.getLastName())
					);
		}
	}

	@Test
	void testSettersAndGetters() {
		var actor = new Actor();
		var date = Timestamp.from(Instant.now());
		
		actor.setActorId(1);
		actor.setFirstName("Actor");
		actor.setLastName("Surname");
		actor.setLastUpdate(date);
		actor.setFilmActors(null);

		assertAll("Attributes",
				()-> assertEquals(1, actor.getActorId()),
				()-> assertEquals("Actor", actor.getFirstName()),
				()-> assertEquals("Surname", actor.getLastName()),
				()-> assertEquals(date, actor.getLastUpdate()),
				()-> assertEquals(null, actor.getFilmActors())
				);
	}
	
	
	@Nested
	class FilmActors{
		@Test
		void testAddFilmActors() {
			var actor = new Actor();
			
			actor.addFilmActor(new FilmActor());
			
			assertTrue(actor.getFilmActors().size()==1);
		}
		
		@Test
		void testRemoveFilmActors() {
			var actor = new Actor();
			
			var filmActor = new FilmActor();
			
			actor.addFilmActor(filmActor);
			
			actor.removeFilmActor(filmActor);
			assertTrue(actor.getFilmActors().size()==0);
		}
	}
	
	@Nested
	class Equals{
		@Test
		void testEquals1() {
			var actor1 = new Actor(1);
			var actor2 = new Actor(1);
			
			assertTrue(actor1.equals(actor2));
		}
		
		@Test
		void testEquals2() {
			var actor1 = new Actor(1);
			var actor2 = new Actor(2);
			
			assertFalse(actor1.equals(actor2));
		}
		
		@Test
		void testEquals3() {
			var actor = new Actor(1);
			
			assertFalse(actor.equals(null));
		}
		@Test
		void testEquals4() {
			var actor = new Actor(1);
			
			assertTrue(actor.equals(actor));
		}
	}
	
	@Test
	void testToString() {
		var actor = new Actor(1, "Name", "LastName");
		
		assertAll("Strings",
				()-> assertTrue(actor.toString().contains("Actor")),
				()-> assertTrue(actor.toString().contains("actorId")),
				()-> assertTrue(actor.toString().contains("firstName")),
				()-> assertTrue(actor.toString().contains("lastName")),
				()-> assertTrue(actor.toString().contains("Name"))
				);
	}
}
