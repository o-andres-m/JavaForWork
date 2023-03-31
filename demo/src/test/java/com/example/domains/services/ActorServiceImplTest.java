package com.example.domains.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;
import com.example.exceptions.InvalidDataException;

@ComponentScan(basePackages = "com.example")
class ActorServiceImplTest {
	
	@MockBean
	ActorRepository dao;
	
	@Autowired
	ActorServiceImpl srv;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	
	@Test
	void testGetAll() {
		var listOfActors = new ArrayList<>(
				List.of(
				new Actor (1, "Pepito", "GOMEZ"),
				new Actor (2, "Juan", "PEREZ"),
				new Actor (3, "Pep", "SANCHEZ")
				));
		when(dao.findAll()).thenReturn(listOfActors);
		
		assertEquals(3, dao.findAll().size());
	}
	
	
	@Test
	void testGetOneById() {
		var listOfActors = new ArrayList<>(
				List.of(
				new Actor (1, "Pepito", "GOMEZ"),
				new Actor (2, "Juan", "PEREZ"),
				new Actor (3, "Pep", "SANCHEZ")
				));	

		when(dao.findById(1)).thenReturn(Optional.of(new Actor (1, "Pepito", "GOMEZ")));
		
		
		assertNotNull(dao.findById(1).get());
		assertEquals("GOMEZ",dao.findById(1).get().getLastName());
	}
	

	@Test
	void testAdd() {
		when(dao.save(any(Actor.class))).thenReturn(null);
		assertThrows(InvalidDataException.class, ()-> dao.save(null));
	}
	
	@Test
	void testGetByProjectionClassOfT() {
		fail("Not yet implemented");
	}

	@Test
	void testGetByProjectionSortClassOfT() {
		fail("Not yet implemented");
	}

	@Test
	void testGetByProjectionPageableClassOfT() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllSort() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllPageable() {
		fail("Not yet implemented");
	}


	@Test
	void testGetOne() {
		fail("Not yet implemented");
	}



	@Test
	void testModify() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteById() {
		fail("Not yet implemented");
	}

}
