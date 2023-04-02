package com.films.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CategoryTest {

	
	@Nested
	class Constructors{
		
		@Test
		void testCategory() {
			var cat = new Category();
			assertNotNull(cat);
		}
		
		@Test
		void testCategoryWithId() {
			var cat = new Category(1);
			assertNotNull(cat);
			assertEquals(1, cat.getCategoryId());
		}
		
	}
	
	@Nested
	class SettersAndGetters{
		
		@Test
		void testCategorySettersAndGetters() {
			var cat = new Category();
			
			var date = Timestamp.from(Instant.now());
			
			cat.setCategoryId(1);
			cat.setName("Name");
			cat.setLastUpdate(date);
			cat.setFilmCategories(null);
			
			assertAll("Setters & Getters2",
					()-> assertEquals(1, cat.getCategoryId()),
					()-> assertEquals("Name", cat.getName()),
					()-> assertEquals(date, cat.getLastUpdate()),
					()-> assertEquals(null, cat.getFilmCategories())
					);
		}
	}
	
	@Nested
	class FilmCategories{
		
		@Test
		void testAddFilmCategory() {
			var category = new Category();
			
			category.addFilmCategory(new FilmCategory());
			
			assertTrue(category.getFilmCategories().size()==1);
		}
		
		@Test
		void testRemoveFilmCategory() {
			var category = new Category();
			
			var filmCategory = new FilmCategory();
			
			category.addFilmCategory(filmCategory);
			category.removeFilmCategory(filmCategory);
			
			assertTrue(category.getFilmCategories().size()==0);
		}
		
	}
	
	@Nested
	class Equals{
		@Test
		void testEquals1() {
			var category = new Category(1);
			var category2 = new Category(1);
			
			assertTrue(category.equals(category2));
		}
		
		@Test
		void testEquals2() {
			var category = new Category(1);
			var category2 = new Category(2);
			
			assertFalse(category.equals(category2));
		}
		
		@Test
		void testEquals3() {
			var category = new Category(1);
			
			assertFalse(category.equals(null));
		}
		@Test
		void testEquals4() {
			var category = new Category(1);
			
			assertTrue(category.equals(category));
		}
		
	}
	
	@Nested
	class ToString{
		@Test
		void testToString() {
			var cat = new Category(1);
			cat.setName("Name");
			assertAll("Strings",
					()-> assertTrue(cat.toString().contains("Category")),
					()-> assertTrue(cat.toString().contains("1")),
					()-> assertTrue(cat.toString().contains("Name"))
					);
		}
	}
}
