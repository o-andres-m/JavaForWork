package com.films.domains.services;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.films.domains.contracts.services.ActorService;
import com.films.domains.core.exceptions.DuplicateKeyException;
import com.films.domains.core.exceptions.InvalidDataException;
import com.films.domains.core.exceptions.NotFoundException;
import com.films.domains.entities.Actor;

@SpringBootTest
class ActorServiceImplTest {
	
	@Autowired
	ActorService srv;


	@Test
	void testGetByProjection() {
		var actorList = srv.getByProjection(Actor.class);
		
		assertAll("Propieties",
				()-> assertNotNull(actorList),
				()-> assertTrue(actorList.size()>0),
				()-> assertEquals(1, actorList.get(0).getActorId())
				);
	}
	
	@Test
	@Disabled
	void testGetByProjectionSortClassOfT() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled
	void testGetByProjectionPageableClassOfT() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled
	void testGetAllSort() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled
	void testGetAllPageable() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAll() {
		var actorList = srv.getAll();
		assertAll("Propieties",
				()-> assertNotNull(actorList),
				()-> assertTrue(actorList.size()>0),
				()-> assertEquals(1, actorList.get(0).getActorId())
				);
	}

	@Test
	void testGetOne() {
		var actor = srv.getOne(1);
		assertAll("Propieties",
				()-> assertNotNull(actor),
				()-> assertTrue(actor.get() instanceof Actor),
				()-> assertEquals(1, actor.get().getActorId())
				);
	}

	@Nested
	class Add{
		
		@Test
		void testAdd() throws DuplicateKeyException, InvalidDataException {
			var actor = new Actor(0,"Name", "LastName");
			
			srv.add(actor);
			
			assertAll("Attributes After Add",
					()-> assertNotNull(actor),
					()-> assertEquals("Name", actor.getFirstName()),
					()-> assertEquals("LastName", actor.getLastName()) 
					);
		}		
		
		@Test
		void testAddInvalidData() throws DuplicateKeyException, InvalidDataException {
			assertThrows(InvalidDataException.class, ()->srv.add(null));
		}
		
		@Test
		void testAddInvalidData2() throws DuplicateKeyException, InvalidDataException {
			var actor = new Actor();
			actor.setFirstName(null);
			assertThrows(InvalidDataException.class, ()->srv.add(actor));
		}	
		
		@Test
		void testAddDuplicateKey() throws DuplicateKeyException, InvalidDataException {
			var actor = new Actor(1,"Name", "LastName");
			
			assertThrows(DuplicateKeyException.class, ()->srv.add(actor));
		}	
	}


	@Nested
	class Modify{
		@Test
		void testModify() throws DuplicateKeyException, InvalidDataException, NotFoundException {
			
			var actor = new Actor(0,"Name", "LastName");
		
			srv.add(actor);
			
			actor.setLastName("NewLastName");
			
			srv.modify(actor);
			
			assertAll("Attributes After Add",
					()-> assertEquals("Name", actor.getFirstName()),
					()-> assertEquals("NewLastName", actor.getLastName()) 
					);
		}
		
		@Test
		void testModifyInvalidData() throws InvalidDataException, NotFoundException {
			
			assertThrows(InvalidDataException.class, ()-> srv.modify(null));
		}
		
		@Test
		void testModifyInvalidData2() throws InvalidDataException, NotFoundException {
			var actor = new Actor(0);
			actor.setFirstName(null);
			assertThrows(InvalidDataException.class, ()-> srv.modify(actor));
		}
		@Test
		void testModifyNotFound() throws InvalidDataException, NotFoundException {
			var actor = new Actor(99999,"Name", "LastName");
			assertThrows(NotFoundException.class, ()-> srv.modify(actor));
		}
		
	}

	@Nested
	class Delete{

		@Test
		void testDelete() throws DuplicateKeyException, InvalidDataException {
			var actor = new Actor(0,"Name", "LastName");
			
			srv.add(actor);
			
			var actorId = actor.getActorId();
			
			srv.delete(actor);
			
			assertTrue(srv.getOne(actorId).isEmpty());
		}
		
		@Test
		void testDeleteWithNullActor() throws InvalidDataException {
			
			assertThrows(InvalidDataException.class, ()-> srv.delete(null));
			
		}

		@Test
		void testDeleteById() throws DuplicateKeyException, InvalidDataException {
			var actor = new Actor(0,"Name", "LastName");
			
			srv.add(actor);
			
			var actorId = actor.getActorId();
			
			srv.deleteById(actorId);
			
			assertTrue(srv.getOne(actorId).isEmpty());
		}
		
	}


	@Nested
	class News{
		@Test
		void testNews() {
			var actorList = srv.news(Timestamp.from(Instant.now()));
			assertNotNull(actorList);
			assertAll("Returned",
					()-> assertTrue(actorList instanceof List<Actor>),
					()-> assertTrue(actorList.size()==0)									
					);
		}
	}
}

