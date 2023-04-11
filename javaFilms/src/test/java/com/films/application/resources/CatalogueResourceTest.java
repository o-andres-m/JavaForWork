package com.films.application.resources;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.films.application.services.CatalogueService;
import com.films.domains.entities.Category;
import com.films.domains.entities.Language;
import com.films.domains.entities.dto.ActorDTO;
import com.films.domains.entities.dto.FilmDetailsDTO;
import com.films.domains.entities.dto.NewsDto;


@WebMvcTest(CatalogueResource.class)
class CatalogueResourceTest {
	
	@MockBean
	CatalogueService srv;
	
	@Autowired
	MockMvc mockMvc;

	@Test
	void testGetAll() throws Exception {
		var newsDto = new NewsDto();
		
		newsDto.setFilms(new ArrayList<FilmDetailsDTO>());
		newsDto.setActors(new ArrayList<ActorDTO>());
		newsDto.setCategories(new ArrayList<Category>());
		newsDto.setLanguages(new ArrayList<Language>());

		when(srv.news(null)).thenReturn(newsDto);
		
		mockMvc.perform(get("/api/catalogue/v1").accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
				status().isOk(),
				jsonPath("$.films").isArray(),
				jsonPath("$.actors").isArray(),
				jsonPath("$.categories").isArray(),
				jsonPath("$.languages").isArray()
				).andDo(print());
	}
	
	@Test
	void testGetAllWithTime() throws Exception {
		var newsDto = new NewsDto();
		
		newsDto.setFilms(new ArrayList<FilmDetailsDTO>());
		
		newsDto.setActors(new ArrayList<ActorDTO>(
				Arrays.asList(new ActorDTO(1,"Actor","Dto"))));
		
		newsDto.setCategories(new ArrayList<Category>());
		newsDto.setLanguages(new ArrayList<Language>());
		
		
		var stringTime = "2023-01-01 00:00:00";
		
		var time = Timestamp.valueOf(stringTime);

		when(srv.news(time)).thenReturn(newsDto);
		
		mockMvc.perform(get("/api/catalogue/v1")
				.param("time", stringTime)
				.accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
				status().isOk(),
				jsonPath("$.films").isArray(),
				jsonPath("$.actors").isArray(),
				jsonPath("$.actors[0].id").value(1),
				jsonPath("$.categories").isArray(),
				jsonPath("$.languages").isArray()
				).andDo(print());
	}
}
