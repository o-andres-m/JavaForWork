package com.films.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoryTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testCategoryInt() {
		var cat = new Category(1);
		assertNotNull(cat);
	}

	@Test
	void testCategoryIntString() {
		var cat = new Category(1,"Name");
		assertNotNull(cat);
		assertEquals("Name", cat.getName());
		}
}
