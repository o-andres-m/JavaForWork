package com.films.application.services;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.films.domains.entities.dto.NewsDto;


@SpringBootTest
class CatalogueServiceImplTest {
	
	@Autowired
	CatalogueServiceImpl srv;

	@Test
	void testNovedadesReturnNewsDTO() {
		var value = srv.news(Timestamp.from(Instant.now()));
		assertNotNull(value);
		assertTrue(value instanceof NewsDto);
	}
	
	@Test
	void testWithNullTimestamp() {
		var value = srv.news(null);
		assertNotNull(value);
		assertTrue(value instanceof NewsDto);
	}
}
