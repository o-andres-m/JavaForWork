package com.example.domains.contracts.repositories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import com.example.domains.entities.Actor;

@DataJpaTest
//Para testear la DB virtual
//En realidad, es para testear metodos del repositorio, contra una DB virtual
class ActorRepositoryMemoryTest {
	
	@Autowired
	private TestEntityManager em;
	//Lo usamos para "llenar"la DB en memoria virtual
	//es decir, le podemos meter cosas a la DB entre otros metodos 
	
	@Autowired
	ActorRepository dao;

	@BeforeEach
	void setUp() throws Exception {
		em.persist(new Actor(0,"Juan","GOMEZ"));
		em.persist(new Actor(0,"Pedro","PEREZ"));
		em.persist(new Actor(0,"Oscar","CC"));
	}



	@Test
	void testAll() {
		assertEquals(3,dao.findAll().size());
	}
	
	@Test
	void testSave() {
		var item = new Actor(0,"Mark","CC");
		dao.save(item);
		assertNotNull(item);
		assertEquals(4,dao.findAll().size());
	}
	
	@Test
	void testOne() {
		var item = dao.getById(0);
		
		assertNotNull(item);
		assertEquals("Juan",item.getFirstName());
	}

}
