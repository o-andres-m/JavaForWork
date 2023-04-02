package com.films.domains.contracts.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.films.domains.entities.Actor;

@SpringBootTest
class ActorRepositoryTest {
	
	@Autowired
	ActorRepository dao;

	@Test
	void testFindAll() {
		var data = dao.findAll();
		
		assertNotNull(data);
		assertAll("Instance and Size",
				()-> assertTrue(data instanceof List<Actor>),
				()-> assertTrue(data.size()>0)
				);
	}
	

	@Test
	void testFinByLastUpdate() {
		var data = dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp.from(Instant.now()));
		
		assertNotNull(data);
		assertAll("Instance and Size",
				()-> assertTrue(data instanceof List<Actor>),
				()-> assertTrue(data.size()==0)
				);
	}

}
