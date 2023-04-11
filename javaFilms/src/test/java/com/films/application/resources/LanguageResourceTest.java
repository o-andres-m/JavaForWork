package com.films.application.resources;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.films.domains.contracts.services.LanguageService;
import com.films.domains.core.exceptions.InvalidDataException;
import com.films.domains.entities.Film;
import com.films.domains.entities.Language;


@WebMvcTest(LanguageResource.class)
class LanguageResourceTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	LanguageService srv;
	
	@Autowired
	ObjectMapper om;
	
	@Nested
	class GetAll{
		
		@Test
		void testGetAll() throws Exception {
			
			List<Language> languageList = new ArrayList<>(
					Arrays.asList(	new Language(),
									new Language(),
									new Language()));
			
			when(srv.getByProjection(Language.class)).thenReturn(languageList);
			
			mockMvc.perform(get("/api/language/v1").accept(MediaType.APPLICATION_JSON))
				.andExpectAll(
						status().isOk(), 
						content().contentType("application/json"),
						jsonPath("$.size()").value(3)
						).andDo(print());
		}	
	}
	
	@Nested
	class GetOne{
		
		@Test
		void testGetOne() throws Exception {
			
			var lan = new Language();
			
			lan.setLanguageId(1);
			lan.setName("Language");
			
			when(srv.getOne(1)).thenReturn(Optional.of(lan));
			
			mockMvc.perform(get("/api/language/v1/1"))
				.andExpectAll(
						status().isOk(), 
						jsonPath("$.id").value(lan.getLanguageId()),
						jsonPath("$.language").value(lan.getName())
						).andDo(print());
		}	
		
		@Test
		void testGetOneNotFound() throws Exception {
			
			when(srv.getOne(999)).thenReturn(Optional.empty());
			
			mockMvc.perform(get("/api/language/v1/999"))
				.andExpectAll(
						status().isNotFound(), 
						jsonPath("$.title").value("Not Found")
						).andDo(print());
		}
		
	}
	
	@Nested
	class Create{
		
		@Test
		void testCreate() throws Exception {
			
			var lan = new Language();
			lan.setLanguageId(1);
			lan.setName("Language");
		
			when(srv.add(lan)).thenReturn(lan);
			
			mockMvc.perform(post("/api/language/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(lan))
				)
				.andExpect(status().isCreated())
		        .andExpect(header().string("Location", "http://localhost/api/language/v1/1"))
		        .andDo(print());
		}	
		
		@Test
		void testCreateWithoutName() throws Exception {
			
			var lan = new Language();
			lan.setLanguageId(0);
		
			when(srv.add(lan)).thenThrow(InvalidDataException.class);
			
			mockMvc.perform(post("/api/language/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(lan))
				)
				.andExpect(status().isBadRequest())
		        .andExpect(jsonPath("$.title").value("Invalid Data"))
		        .andDo(print());
		}
		
	}
	
	@Nested
	class Update{
		
		@Test
		void testUpdate() throws JsonProcessingException, Exception {
			
			var lan = new Language(1);
			lan.setName("Language");

			when(srv.modify(lan)).thenReturn(lan);
			
			mockMvc.perform(put("/api/language/v1/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(om.writeValueAsString(lan))
					)
					.andExpect(status().isNoContent())
			        .andDo(print());
		}
		
		@Test
		void testUpdateDoesntMatchId() throws JsonProcessingException, Exception {
			
			var lan = new Language(1);
			lan.setName("Language");

			when(srv.modify(lan)).thenReturn(lan);
			
			mockMvc.perform(put("/api/language/v1/2")
					.contentType(MediaType.APPLICATION_JSON)
					.content(om.writeValueAsString(lan))
					)
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.title").value("Bad Request"))
					.andExpect(jsonPath("$.detail").value("IDs doesnt match"))
					.andDo(print());
		}
		
	}
	
	@Nested
	class Delete{
		@Test
		void testDelete() throws Exception{

			doNothing().when(srv).deleteById(1);
			
			mockMvc.perform(delete("/api/language/v1/1")
					)
					.andExpect(status().isNoContent())
			        .andDo(print());	
			
			verify(srv,times(1)).deleteById(1);
		}
	}
	
	@Nested
	class FilmsLanguage{
		
		@Test
		void testGetFilmsWithThisLanguage() throws Exception{
			
			var lan = new Language();
			
			lan.setLanguageId(1);
			lan.setName("Language");
			
			var filmList = new ArrayList<Film>(
					Arrays.asList(	new Film(),
									new Film(),
									new Film()));
					
			lan.setFilms(filmList);
			
			when(srv.getOne(1)).thenReturn(Optional.of(lan));
			
			mockMvc.perform(get("/api/language/v1/1/films"))
				.andExpectAll(
						status().isOk(), 
						jsonPath("$.size()").value(3)
						).andDo(print());
		}
		
		@Test
		void testGetFilmsInCategoryNoExists() throws Exception{
			
			when(srv.getOne(999)).thenReturn(Optional.empty());
			
			mockMvc.perform(get("/api/language/v1/999/films"))
				.andExpectAll(
						status().isNotFound(), 
						jsonPath("$.title").value("Not Found")
						).andDo(print());
		}
		
	}



}
