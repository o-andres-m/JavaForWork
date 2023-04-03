package com.films.domains.services;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.films.domains.contracts.services.CategoryService;
import com.films.domains.core.exceptions.DuplicateKeyException;
import com.films.domains.core.exceptions.InvalidDataException;
import com.films.domains.entities.Category;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ComponentScan(basePackages = "com.films")
@AutoConfigureTestDatabase(replace = Replace.AUTO_CONFIGURED)
class CategoryServiceImpTest {
	
	@Autowired
	CategoryService srv;
	
	@Autowired
	private EntityManager em;

	@BeforeEach
	void setUp() throws Exception {
		
		var cat1 = new Category(0);
		cat1.setName("Category1");
		cat1.setLastUpdate(Timestamp.from(Instant.now()));
		cat1.setFilmCategories(List.of());
		em.persist(cat1);
		
		var cat2 = new Category(0);
		cat2.setName("Category1");
		cat2.setLastUpdate(Timestamp.from(Instant.now()));
		cat2.setFilmCategories(List.of());
		em.persist(cat2);
		
		var cat3 = new Category(0);
		cat3.setName("Category1");
		cat3.setLastUpdate(Timestamp.from(Instant.now()));
		cat3.setFilmCategories(List.of());
		em.persist(cat3);
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetByProjectionClassOfT() {
		var cat = srv.getByProjection(Category.class);
		
		assertAll("Data",
				()-> assertTrue(cat.size()==3),
				()-> assertEquals("Category1", cat.get(0).getName())
				);
		
	}

	@Test
	void testGetAll() {
		var cat = srv.getAll();
		
		assertAll("Data",
				()-> assertTrue(cat.size()==3),
				()-> assertEquals("Category1", cat.get(0).getName())
				);
	}

	@Test
	void testGetOne() {

		var categoryList = srv.getAll();
		
		var cat = srv.getOne(categoryList.get(0).getCategoryId()).get();
		
		assertEquals(categoryList.get(0).getName(), cat.getName());
	}

	
	@Nested
	class Add{
		@Test
		void testAdd() throws DuplicateKeyException, InvalidDataException {
		
			var cat = new Category(0);
			cat.setName("Categoryname");

			srv.add(cat);
			
			assertEquals(4, srv.getAll().size());;
		}
		
		@Test
		void testAddWithError() throws DuplicateKeyException, InvalidDataException {
		
			assertThrows(InvalidDataException.class, ()-> srv.add(null));
		}
		
		void testAddWithDuplicateKey() throws DuplicateKeyException, InvalidDataException {
			
			var cat = srv.getOne(1).get();

			assertThrows(InvalidDataException.class, ()-> srv.add(cat));
		}
	}

}
