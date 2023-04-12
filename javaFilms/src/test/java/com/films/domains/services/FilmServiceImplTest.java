package com.films.domains.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.films.domains.contracts.repository.FilmRepository;
import com.films.domains.core.exceptions.DuplicateKeyException;
import com.films.domains.core.exceptions.InvalidDataException;
import com.films.domains.core.exceptions.NotFoundException;
import com.films.domains.entities.Film;
import com.films.domains.entities.Film.Rating;

import com.films.domains.entities.Language;


@DataJpaTest
@ComponentScan(basePackages = "com.films")
class FilmServiceImplTest {
	
	@MockBean
	FilmRepository dao;
	
	@Autowired
	FilmServiceImpl srv;
	
	@Nested
	class Add{
		
		@Test
		void testAdd() throws DuplicateKeyException, InvalidDataException {
			var film = new Film();

			film.setFilmId(1);
			film.setTitle("Title");
			film.setDescription("Description");
			film.setLastUpdate(Timestamp.from(Instant.now()));
			film.setLength(100);
			film.setRating(Rating.ADULTS_ONLY);
			film.setReleaseYear(Short.valueOf("2000"));
			film.setRentalDuration(Byte.valueOf("1"));
			film.setRentalRate(BigDecimal.valueOf(1));
			film.setReplacementCost(BigDecimal.valueOf(1));
			film.setLanguage(new Language());
			film.setLanguageVO(new Language());
			film.setActors(List.of());
			film.setCategories(List.of());
			
			when(dao.save(film)).thenReturn(film);
			
			srv.add(film);
			
			assertTrue(film instanceof Film);
			assertEquals(1, film.getFilmId());
		}
		
		@Test
		void testAddNull() throws DuplicateKeyException, InvalidDataException {
									
			assertThrows(InvalidDataException.class, ()-> srv.add(null));
		}
		
		@Test
		void testAddInvalid() throws DuplicateKeyException, InvalidDataException {
			
			var film = new Film();
						
			assertThrows(InvalidDataException.class, ()-> srv.add(film));
		}
		
		@Test
		void testAddDuplicate() throws DuplicateKeyException, InvalidDataException {
			
			var film = new Film();
			film.setFilmId(1);
			film.setTitle("Title");
			film.setDescription("Description");
			film.setLastUpdate(Timestamp.from(Instant.now()));
			film.setLength(100);
			film.setRating(Rating.ADULTS_ONLY);
			film.setReleaseYear(Short.valueOf("2000"));
			film.setRentalDuration(Byte.valueOf("1"));
			film.setRentalRate(BigDecimal.valueOf(1));
			film.setReplacementCost(BigDecimal.valueOf(1));
			film.setLanguage(new Language());
			film.setLanguageVO(new Language());
			film.setActors(List.of());
			film.setCategories(List.of());
			
			when(dao.existsById(1)).thenReturn(true);

			assertThrows(DuplicateKeyException.class, ()-> srv.add(film));
		}
		
	}
	
	@Nested
	class FindAll{
		
		@Test
		void testFindAll() throws DuplicateKeyException, InvalidDataException {

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
		void testGetByProjection() {

			List<Film> lista = new ArrayList<>(
					Arrays.asList(
						new Film(1),
						new Film(2),
						new Film(3)
						));
			
			when(dao.findAllBy(Film.class)).thenReturn(lista);
			
			var filmList = srv.getByProjection(Film.class);
			
			assertAll("Propieties",
					()-> assertNotNull(filmList),
					()-> assertTrue(filmList.size()>0),
					()-> assertEquals(1, filmList.get(0).getFilmId())
					);
		}
		
		@Test
		void testGetSort() {
			
			var film1 = new Film(1);
			film1.setTitle("C");

			var film2 = new Film(2);
			film2.setTitle("A");
			
			var film3 = new Film(3);
			film3.setTitle("B");

			List<Film> filmList = new ArrayList<>(
					Arrays.asList(
						film2,
						film3,
						film1
						));
			
			when(dao.findAll(Sort.by("title"))).thenReturn(filmList);
			
			var filmList2 = (List<Film>)srv.getAll(Sort.by("title"));

			
			assertAll("Propieties",
					()-> assertNotNull(filmList2),
					()-> assertTrue(filmList2.size()==3),
					()-> assertEquals("A", filmList2.get(0).getTitle())
					);
		}
		
		@Test
		void testGetByProjectionSortClassOfT() {
			
			var film1 = new Film(1);
			film1.setTitle("C");

			var film2 = new Film(2);
			film2.setTitle("A");
			
			var film3 = new Film(3);
			film3.setTitle("B");

			List<Film> filmList = new ArrayList<>(
					Arrays.asList(
						film2,
						film3,
						film1
						));
			
			when(dao.findAllBy(Sort.by("title"), Film.class)).thenReturn(filmList);
			
			var filmList2 = (List<Film>)srv.getByProjection(Sort.by("title"), Film.class);

			
			assertAll("Propieties",
					()-> assertNotNull(filmList2),
					()-> assertTrue(filmList2.size()==3),
					()-> assertEquals("A", filmList2.get(0).getTitle())
					);
		}
		
		@Test
		void testGetByProjectionPageable() {
			
			var film1 = new Film(1);
			film1.setTitle("A");

			var film2 = new Film(2);
			film2.setTitle("B");
			

			List<Film> filmList = new ArrayList<>(
					Arrays.asList(
						film1,
						film2
						));
			
			var page = new PageImpl<Film>(filmList);
			
			when(dao.findAll(Pageable.ofSize(2))).thenReturn(page);
			
			var pageFilms = srv.getAll(Pageable.ofSize(2));

			
			assertAll("Propieties",
					()-> assertNotNull(pageFilms),
					()-> assertTrue(pageFilms.getSize()==2),
					()-> assertTrue(pageFilms.getContent().get(0).getTitle()=="A")

					);
		}
	}
	
	@Nested
	class Modify {
		
		@Test
		void testModifyNull() throws InvalidDataException {
									
			assertThrows(InvalidDataException.class, ()-> srv.modify(null));
		}
		
		@Test
		void testModifyInvalid() throws InvalidDataException {
			
			var film = new Film();
						
			assertThrows(InvalidDataException.class, ()-> srv.modify(film));
		}
		
		@Test
		void testModifyEpty() throws NotFoundException {
			
			var film = new Film();
			film.setFilmId(1);
			film.setTitle("Title");
			film.setDescription("Description");
			film.setLastUpdate(Timestamp.from(Instant.now()));
			film.setLength(100);
			film.setRating(Rating.ADULTS_ONLY);
			film.setReleaseYear(Short.valueOf("2000"));
			film.setRentalDuration(Byte.valueOf("1"));
			film.setRentalRate(BigDecimal.valueOf(1));
			film.setReplacementCost(BigDecimal.valueOf(1));
			film.setLanguage(new Language());
			film.setLanguageVO(new Language());
			film.setActors(List.of());
			film.setCategories(List.of());
			
			when(dao.findById(film.getFilmId())).thenReturn(Optional.empty());
						
			assertThrows(NotFoundException.class, ()-> srv.modify(film));
		}
		
		@Test
		void testModify() {
			
			var film = new Film();
			film.setFilmId(1);
			film.setTitle("Title");
			film.setDescription("Description");
			film.setLastUpdate(Timestamp.from(Instant.now()));
			film.setLength(100);
			film.setRating(Rating.ADULTS_ONLY);
			film.setReleaseYear(Short.valueOf("2000"));
			film.setRentalDuration(Byte.valueOf("1"));
			film.setRentalRate(BigDecimal.valueOf(1));
			film.setReplacementCost(BigDecimal.valueOf(1));
			film.setLanguage(new Language());
			film.setLanguageVO(new Language());
			film.setActors(List.of());
			film.setCategories(List.of());
			
			when(dao.save(film.merge(film))).thenReturn(film);
						
			assertEquals(1, film.getFilmId());
			assertTrue(film instanceof Film);
		}
		
	}
	
	@Nested
	class Delete{
		
		@Test
		void testDelete() {
												
			assertThrows(InvalidDataException.class, ()-> srv.delete(null));
		}
		
		@Test
		void testDelete2() throws InvalidDataException {
								
			var film = new Film();
			film.setFilmId(1);
			film.setTitle("Title");
			film.setDescription("Description");
			film.setLastUpdate(Timestamp.from(Instant.now()));
			film.setLength(100);
			film.setRating(Rating.ADULTS_ONLY);
			film.setReleaseYear(Short.valueOf("2000"));
			film.setRentalDuration(Byte.valueOf("1"));
			film.setRentalRate(BigDecimal.valueOf(1));
			film.setReplacementCost(BigDecimal.valueOf(1));
			film.setLanguage(new Language());
			film.setLanguageVO(new Language());
			film.setActors(List.of());
			film.setCategories(List.of());
			
			doNothing().when(dao).delete(film);
			
			srv.delete(film);
			
			verify(dao,times(1)).delete(film);
		}
	}

}
