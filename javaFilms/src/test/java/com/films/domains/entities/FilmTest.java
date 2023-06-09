package com.films.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.films.domains.entities.Film.Rating;

class FilmTest {
	
	@Nested
	class Constructors{
		
		@Test
		void testConstructor() {
			var film = new Film();
			assertNotNull(film);
		}
		
		@Test
		void testConstructorId() {
			var film = new Film(1);
			assertNotNull(film);
			assertEquals(1,film.getFilmId());
		}
		
		@Test
		void testFullConstructor() {
			var film = new Film(1,"Title", "Description",
					Short.valueOf("0"),new Language(),new Language(), Byte.valueOf("0"),
					BigDecimal.valueOf(1),100,
					BigDecimal.valueOf(1), Rating.ADULTS_ONLY);
			
			assertNotNull(film);
			assertEquals("Title", film.getTitle());
		}
		
		@Test
		void testPartialConstructor() {
			var film = new Film("Title",new Language(), Byte.valueOf("0"),
					BigDecimal.valueOf(1),100,
					BigDecimal.valueOf(1));
			
			assertNotNull(film);
			assertEquals("Title", film.getTitle());
		}
	}
	
	
	@Nested
	class ValidateData{
		
		@Test
		void testEptyAttributes() {
			var film = new Film();
			assertFalse(film.isValid());
		}
		
		@Test
		void testLenghtNegative() {
			var film = new Film();
			film.setFilmId(1);
			film.setTitle("Title");
			film.setDescription("Description");
			film.setLastUpdate(Timestamp.from(Instant.now()));
			//Negative:
			film.setLength(-5);
			film.setRating(Rating.ADULTS_ONLY);
			film.setReleaseYear(Short.valueOf("2000"));
			film.setRentalDuration(Byte.valueOf("1"));
			film.setRentalRate(BigDecimal.valueOf(1));
			film.setReplacementCost(BigDecimal.valueOf(1));
			film.setLanguage(new Language());
			film.setLanguageVO(new Language());
			film.setActors(List.of());
			film.setCategories(List.of());
			
			assertTrue(film.isInvalid());
		}
		
	}
	
	@Nested
	class GettersAndSetters{
		@Test
		void testAllSetters() {
			
			var film = new Film();
			
			var date = Timestamp.from(Instant.now());
			
			film.setFilmId(1);
			film.setTitle("Title");
			film.setDescription("Description");
			film.setLastUpdate(date);
			film.setLength(100);
			film.setRating(Rating.ADULTS_ONLY);
			film.setReleaseYear(Short.valueOf("0"));
			film.setRentalDuration(Byte.valueOf("0"));
			film.setRentalRate(BigDecimal.valueOf(0));
			film.setReplacementCost(BigDecimal.valueOf(0));
			film.setLanguage(null);
			film.setLanguageVO(null);
			film.setActors(List.of());
			film.setCategories(List.of());
			
			assertNotNull(film);
			assertAll("Setters & Getters",
					()-> assertEquals(1, film.getFilmId()),
					()-> assertEquals("Title", film.getTitle()),
					()-> assertEquals("Description", film.getDescription()),
					()-> assertEquals(date, film.getLastUpdate()),
					()-> assertEquals(100, film.getLength()),
					()-> assertEquals(Rating.ADULTS_ONLY.getValue(), film.getRating().getValue()),
					()-> assertEquals(Short.valueOf("0"), film.getReleaseYear()),
					()-> assertEquals(Byte.valueOf("0"), film.getRentalDuration()),
					()-> assertEquals(BigDecimal.valueOf(0), film.getRentalRate()),
					()-> assertEquals(BigDecimal.valueOf(0), film.getReplacementCost()),
					()-> assertEquals(null, film.getLanguage()),
					()-> assertEquals(null, film.getLanguageVO()),
					()-> assertEquals(List.of(), film.getActors()),
					()-> assertEquals(List.of(), film.getCategories())				
					);
		}
	}
	
	@Nested
	class FilmsAndActors{
		
		@Test
		void testAddActor() {
			
			var film = new Film();
			
			film.addActor(new Actor());
			
			assertEquals(1, film.getActors().size());
		}
		
		@Test
		void testAddActorWithId() {
			
			var film = new Film();
			
			film.addActor(1);
			
			assertEquals(1, film.getActors().size());
		}
		@Test
		void testRemoveActors() {
			
			var film = new Film();
			var actor = new Actor(1);
			film.addActor(actor.getActorId());
			film.removeActor(actor);
			
			assertEquals(0, film.getActors().size());
		}
		
		@Test
		void testRemoveEmptyActors() {
			
			var film = new Film();
			var actor = new Actor(1);
			film.removeActor(actor);
			
			assertEquals(0, film.getActors().size());
		}
		@Test
		void testClearActors() {
			
			var film = new Film();
			
			film.addActor(1);
			
			film.clearActors();
			
			assertEquals(0, film.getActors().size());
		}
		
	}
	
	@Nested
	class FilmAndCategories{
		
		
		@Test
		void testAddCategory() {
			
			var film = new Film();
			
			film.addCategory(new Category());
			
			assertEquals(1, film.getCategories().size());
		}
		
		@Test
		void testAddCategoryWithId() {
			
			var film = new Film();
			
			film.addCategory(1);
			
			assertEquals(1, film.getCategories().size());
		}
		@Test
		void testRemoveCategories() {
			
			var film = new Film();
			var category = new Category(1);
			film.addCategory(category.getCategoryId());
			film.removeCategory(category);
			
			assertEquals(0, film.getCategories().size());
		}
		
		@Test
		void testRemoveEmptyCategories() {
			
			var film = new Film();
			var category = new Category(1);
			film.removeCategory(category);
			
			assertEquals(0, film.getCategories().size());
		}
		@Test
		void testClearCategories() {
			
			var film = new Film();
			
			film.addCategory(1);
			
			film.clearCategories();
			
			assertEquals(0, film.getCategories().size());
		}
	}
	
	@Nested
	class Equals{
		
		@Test
		void testEqual1(){
			var film = new Film(1);
			var film2 = new Film(1);
			
			assertTrue(film.equals(film2));
		}
		@Test
		void testEqual2(){
			var film = new Film(1);
			var film2 = new Film(2);
			
			assertFalse(film.equals(film2));
		}
		@Test
		void testEqual3(){
			var film = new Film(1);
			
			assertFalse(film.equals(null));
		}
		@Test
		void testEqual4(){
			var film = new Film(1);
			
			assertTrue(film.equals(film));
		}
	}
	
	@Nested
	class Merge{
		@Test
		void testMerge() {
			
			var film = new Film();
			
			var date = Timestamp.from(Instant.now());
			
			film.setFilmId(1);
			film.setTitle("Title");
			film.setDescription("Description");
			film.setLastUpdate(date);
			film.setLength(100);
			film.setRating(Rating.ADULTS_ONLY);
			film.setReleaseYear(Short.valueOf("0"));
			film.setRentalDuration(Byte.valueOf("0"));
			film.setRentalRate(BigDecimal.valueOf(0));
			film.setReplacementCost(BigDecimal.valueOf(0));
			film.setLanguage(null);
			film.setLanguageVO(null);
			film.setActors(List.of(new Actor(1), new Actor(2)));
			film.setCategories(List.of(new Category(1),new Category(2)));
			
			
			var film2 = new Film();
						
			film2.setFilmId(1);
			film2.setTitle("Title");
			film2.setDescription("Description");
			film2.setLastUpdate(date);
			film2.setLength(100);
			film2.setRating(Rating.ADULTS_ONLY);
			film2.setReleaseYear(Short.valueOf("0"));
			film2.setRentalDuration(Byte.valueOf("0"));
			film2.setRentalRate(BigDecimal.valueOf(0));
			film2.setReplacementCost(BigDecimal.valueOf(0));
			film2.setLanguage(null);
			film2.setLanguageVO(null);
			film2.setActors(List.of(new Actor(1), new Actor(3)));
			film2.setCategories(List.of(new Category(1), new Category(3)));
			
			film.merge(film2);
			
			assertEquals(film,film2);
			
//			assertAll("Attributes",
//					()-> assertEquals(film.getFilmId(), film2.getFilmId()),
//					()-> assertEquals(film.getTitle(), film2.getTitle()),
//					()-> assertEquals(film.getDescription(), film2.getDescription()),
//					()-> assertEquals(film.getLastUpdate(), film2.getLastUpdate()),
//					()-> assertEquals(film.getLength(), film2.getLength()),
//					()-> assertEquals(film.getRating().getValue(), film2.getRating().getValue()),
//					()-> assertEquals(film.getReleaseYear(), film2.getReleaseYear()),
//					()-> assertEquals(film.getRentalDuration(), film2.getRentalDuration()),
//					()-> assertEquals(film.getRentalRate(), film2.getRentalRate()),
//					()-> assertEquals(film.getReplacementCost(), film2.getReplacementCost()),
//					()-> assertEquals(film.getLanguage(), film2.getLanguage()),
//					()-> assertEquals(film.getLanguageVO(), film2.getLanguageVO()),
//					()-> assertEquals(film.getActors(), film2.getActors()),
//					()-> assertEquals(film.getCategories(), film2.getCategories())				
//					);
		}
	}
	
}