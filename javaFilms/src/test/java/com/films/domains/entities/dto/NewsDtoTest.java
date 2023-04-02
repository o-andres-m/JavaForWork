package com.films.domains.entities.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NewsDtoTest {

	@Test
	void testNewsDto() {
		var news = new NewsDto();
		
		assertNotNull(news);
		assertTrue(news instanceof NewsDto);
		assertTrue(news.toString() instanceof String);
	}

}
