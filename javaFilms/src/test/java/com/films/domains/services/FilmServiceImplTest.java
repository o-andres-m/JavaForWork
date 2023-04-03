package com.films.domains.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import com.films.domains.contracts.repository.FilmRepository;
import com.films.domains.contracts.services.FilmService;
import com.films.domains.core.exceptions.DuplicateKeyException;
import com.films.domains.core.exceptions.InvalidDataException;
import com.films.domains.entities.Film;



@DataJpaTest
@ComponentScan(basePackages = "com.films")
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
	void testAdd() throws DuplicateKeyException, InvalidDataException {
//		var film = new Film();
//		
//		film.setFilmId(1);
//		
//		when(dao.save(film)).thenReturn(film);
//		
//		srv.add(film);
//		
//		assertTrue(film instanceof Film);
//		assertEquals(1, film.getFilmId());
		
		List<Film> lista = new ArrayList<>(
								Arrays.asList(
									new Film(1),
									new Film(2),
									new Film(3)
									));
		when(dao.findAll()).thenReturn(lista);
		
		var returned = srv.getAll();
		
		assertTrue(returned instanceof List<Film>);
		assertTrue(returned.size()>0);
		


	}

	@Test
	@Disabled
	void testModify() {
		fail("Not yet implemented");
	}

}
