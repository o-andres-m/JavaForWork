package com.films.domains.contracts.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.films.domains.entities.Language;


@DataJpaTest
class LanguageRepositoryTest {

	@Autowired
	private TestEntityManager em;
	
	@Autowired
	LanguageRepository dao;
	
	
	@BeforeEach
	void setUp() throws Exception {
		var item = new Language(0, "English");
		item.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
		em.persist(item);
				
		var item2 = new Language(0, "Spanish");
		item2.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
		em.persist(item2);
		
		var item3 = new Language(0, "Italian");
		item3.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
		em.persist(item3);
	}
	
	@Test
	void testFindAll() {
		var data = dao.findAll();
		
		System.out.println(data);
				
		assertNotNull(data);
		assertAll("Instance and Size",
				()-> assertTrue(data instanceof List<Language>),
				()-> assertTrue(data.size()==3)
				);
	}
	

	@Test
	void testFinByLastUpdate() {
		var data = dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp.from(Instant.now()));
		
		assertNotNull(data);
		assertAll("Instance and Size",
				()-> assertTrue(data instanceof List<Language>),
				()-> assertTrue(data.size()==0)
				);
	}
	
	@Test
	void testFindOne() {
		var item4 = new Language(0, "Italian");
		item4.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
		
		var id = em.persist(item4).getLanguageId();
		
		var data = dao.findById(id);
		
		assertNotNull(data);
		assertAll("Name and Id",
				()-> assertTrue(data.get() instanceof Language),
				()-> assertEquals("Italian", data.get().getName()),
				()-> assertEquals(id, data.get().getLanguageId())
				);
	}
}
