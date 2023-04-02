package com.films.domains.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import com.films.domains.contracts.repository.FilmRepository;

@DataJpaTest
@ComponentScan(basePackages = "com.example")
@Disabled
class FilmServiceImplTest {
	
	@MockBean
	FilmRepository dao;
	
	@Autowired
	FilmServiceImpl srv;
	

	@BeforeEach
	void setUp() throws Exception {
		
		
	}

	@AfterEach
	void tearDown() throws Exception {
		
	}

	@Test
	@Disabled
	void testAdd() {
		fail("Not yet implemented");

	}

	@Test
	@Disabled
	void testModify() {
		fail("Not yet implemented");
	}

}
