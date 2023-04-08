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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;


import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.films.domains.contracts.services.CategoryService;
import com.films.domains.core.exceptions.InvalidDataException;
import com.films.domains.entities.Category;
import com.films.domains.entities.Film;
import com.films.domains.entities.FilmCategory;

@WebMvcTest(CategoryResource.class)
class CategoryResourceTest {
	
	@MockBean
	CategoryService srv;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper om;
	
	
	@Nested
	class GetAll{
		
		@Test
		void testGetAll() throws Exception {
			
			List<Category> categoryList = new ArrayList<>(
					Arrays.asList(	new Category(),
									new Category(),
									new Category()));
			
			when(srv.getByProjection(Category.class)).thenReturn(categoryList);
			
			mockMvc.perform(get("/api/category/v1").accept(MediaType.APPLICATION_JSON))
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
			
			var cat = new Category();
			
			cat.setCategoryId(1);
			cat.setName("CategoryName");
			
			when(srv.getOne(1)).thenReturn(Optional.of(cat));
			
			mockMvc.perform(get("/api/category/v1/1"))
				.andExpectAll(
						status().isOk(), 
						jsonPath("$.id").value(cat.getCategoryId()),
						jsonPath("$.category").value(cat.getName())
						).andDo(print());
		}	
		
		@Test
		void testGetOneNotFound() throws Exception {
			
			when(srv.getOne(999)).thenReturn(Optional.empty());
			
			mockMvc.perform(get("/api/category/v1/999"))
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
			
			var cat = new Category();
			cat.setCategoryId(1);
			cat.setName("CategoryName");
		
			when(srv.add(cat)).thenReturn(cat);
			
			mockMvc.perform(post("/api/category/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(cat))
				)
				.andExpect(status().isCreated())
		        .andExpect(header().string("Location", "http://localhost/api/category/v1/1"))
		        .andDo(print());
		}	
		
		@Test
		void testCreateWithoutName() throws Exception {
			
			var cat = new Category();
			cat.setCategoryId(0);
		
			when(srv.add(cat)).thenThrow(InvalidDataException.class);
			
			mockMvc.perform(post("/api/category/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(cat))
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
			
			var cat = new Category(1);
			cat.setName("CategoryName");

			when(srv.modify(cat)).thenReturn(cat);
			
			mockMvc.perform(put("/api/category/v1/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(om.writeValueAsString(cat))
					)
					.andExpect(status().isNoContent())
			        .andDo(print());
		}
		
		@Test
		void testUpdateDoesntMatchId() throws JsonProcessingException, Exception {
			
			var cat = new Category(1);
			cat.setName("CategoryName");

			when(srv.modify(cat)).thenReturn(cat);
			
			mockMvc.perform(put("/api/category/v1/2")
					.contentType(MediaType.APPLICATION_JSON)
					.content(om.writeValueAsString(cat))
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
			
			mockMvc.perform(delete("/api/category/v1/1")
					)
					.andExpect(status().isNoContent())
			        .andDo(print());	
			
			verify(srv,times(1)).deleteById(1);
		}
	}
	
	@Nested
	class FilmsCategory{
		
		@Test
		void testGetFilmsInCategory() throws Exception{
			
			var cat = new Category();
			
			cat.setCategoryId(1);
			cat.setName("CategoryName");
			
			var filmCategoryList = new ArrayList<FilmCategory>(
					Arrays.asList(	new FilmCategory(new Film(),cat),
									new FilmCategory(new Film(),cat),
									new FilmCategory(new Film(),cat)));
			
			cat.setFilmCategories(filmCategoryList);
			
			when(srv.getOne(1)).thenReturn(Optional.of(cat));
			
			mockMvc.perform(get("/api/category/v1/1/films"))
				.andExpectAll(
						status().isOk(), 
						jsonPath("$.size()").value(3)
						).andDo(print());
		}
		
		@Test
		void testGetFilmsInCategoryNoExists() throws Exception{
			
			when(srv.getOne(999)).thenReturn(Optional.empty());
			
			mockMvc.perform(get("/api/category/v1/999/films"))
				.andExpectAll(
						status().isNotFound(), 
						jsonPath("$.title").value("Not Found")
						).andDo(print());
		}
		
	}
}
