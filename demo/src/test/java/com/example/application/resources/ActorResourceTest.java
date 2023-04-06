package com.example.application.resources;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Film;
import com.example.domains.entities.FilmActor;
import com.example.domains.entities.dtos.ActorDto;
import com.example.domains.entities.dtos.ActorShort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;

@WebMvcTest(ActorResource.class)
class ActorResourceTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private ActorService srv;

	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Value
	static class ActorShortMock implements ActorShort {
		int actorId;
		String nombre;
	}

	@Nested
	class GetAll{
		
		@Test
		void testGetAllString() throws Exception {
			
			List<ActorShort> lista = new ArrayList<>(
			        Arrays.asList(
			        		new ActorShortMock(1, "Pepito Grillo"),
			        		new ActorShortMock(2, "Carmelo Coton"),
			        		new ActorShortMock(3, "Capitan Tan"))
			        		);
			
			when(srv.getByProjection(ActorShort.class)).thenReturn(lista);
			
			mockMvc.perform(get("/api/actores/v1").accept(MediaType.APPLICATION_JSON))
				.andExpectAll(
						status().isOk(), 
						content().contentType("application/json"),
						jsonPath("$.size()").value(3)
						).andDo(print());
		}
		
		@Test
		void testGetWithSort() throws Exception {
			
			List<ActorShort> listaOrdenadaByName = new ArrayList<>(
			        Arrays.asList(
			        		new ActorShortMock(3, "Capitan Tan"),
	        				new ActorShortMock(2, "Carmelo Coton"),
			        		new ActorShortMock(1, "Pepito Grillo")
			        		));
	
			
			when(srv.getByProjection(Sort.by("firstName"), ActorShort.class)).thenReturn(listaOrdenadaByName);
			
			mockMvc.perform(get("/api/actores/v1")
					.param("sort", "firstName")
					.accept(MediaType.APPLICATION_JSON))
				.andExpectAll(
						status().isOk(), 
						content().contentType("application/json"),
						jsonPath("$.size()").value(3),
						jsonPath("$[0].nombre").value("Capitan Tan")
						).andDo(print());
		}
	}


	@Test
	void testGetAllPageable() throws Exception {
		List<ActorDto> lista = new ArrayList<>(
		        Arrays.asList(new ActorDto(1, "Pepito", "Grillo"),
		        		new ActorDto(2, "Carmelo", "Coton"),
		        		new ActorDto(3, "Capitan", "Tan")));

		when(srv.getByProjection(PageRequest.of(0, 20), ActorDto.class))
			.thenReturn(new PageImpl<>(lista));
		
		mockMvc.perform(get("/api/actores/v1").queryParam("page", "0"))
			.andExpectAll(
				status().isOk(), 
				content().contentType("application/json"),
				jsonPath("$.content.size()").value(3),
				jsonPath("$.size").value(3)
				);
	}
	
	@Nested
	class Pelis{
		@Test
		void testGetPelis() throws Exception {
			var id = 1;
			var actor = new Actor(id, "Pepito", "Grillo");
			
			var actorFilms=new ArrayList<FilmActor>(
					Arrays.asList(
							new FilmActor(new Film(),actor),
							new FilmActor(new Film(),actor)
							));
			
			actor.setFilmActors(actorFilms);
			
			when(srv.getOne(actor.getActorId())).thenReturn(Optional.of(actor));
			
			mockMvc.perform(get("/api/actores/v1/{id}/pelis", id))
			.andExpectAll(
				status().isOk(),
				jsonPath("$.size()").value(2)
				)
		        .andDo(print());
		}
		@Test
		void testGetPelisActorNotFound() throws Exception {
			
			int id = 1;
			var ele = new Actor(id, "Pepito", "Grillo");
			
			when(srv.getOne(id)).thenReturn(Optional.empty());
			
			mockMvc.perform(get("/api/actores/v1/{id}/pelis", id))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("Not Found"))
		        .andDo(print());
		}
	}

	@Test
	void testGetOne() throws Exception {
		int id = 1;
		var ele = new Actor(id, "Pepito", "Grillo");
		
		when(srv.getOne(id)).thenReturn(Optional.of(ele));
		
		mockMvc.perform(get("/api/actores/v1/{id}", id))
			.andExpect(status().isOk())
	        .andExpect(jsonPath("$.id").value(id))
	        .andExpect(jsonPath("$.nombre").value(ele.getFirstName()))
	        .andExpect(jsonPath("$.apellido").value(ele.getLastName()))
	        .andDo(print());
	}
	
	@Test
	void testGetOne404() throws Exception {
		
		int id = 1;
		var ele = new Actor(id, "Pepito", "Grillo");
		
		when(srv.getOne(id)).thenReturn(Optional.empty());
		
		mockMvc.perform(get("/api/actores/v1/{id}", id))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.title").value("Not Found"))
	        .andDo(print());
	}

	@Test
	void testCreate() throws Exception {
		int id = 1;
		var ele = new Actor(id, "Pepito", "Grillo");
		
		when(srv.add(ele)).thenReturn(ele);
		
		mockMvc.perform(post("/api/actores/v1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(ActorDto.from(ele)))
			)
			.andExpect(status().isCreated())
	        .andExpect(header().string("Location", "http://localhost/api/actores/v1/1"))
	        .andDo(print());
	}

	@Test
	void testUpdate() throws JsonProcessingException, Exception {
		
		var actor = new Actor(1, "Pepito", "Grillo");

		when(srv.modify(actor)).thenReturn(actor);
		
		mockMvc.perform(put("/api/actores/v1/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ActorDto.from(actor)))
				)
				.andExpect(status().isNoContent())
		        .andDo(print());
	}

	@Test
	void testDelete() throws JsonProcessingException, Exception {
		var actor = new Actor(1, "Pepito", "Grillo");


		doNothing().when(srv).deleteById(1);
		
		mockMvc.perform(delete("/api/actores/v1/1")
				)
				.andExpect(status().isNoContent())
		        .andDo(print());	
		
		//El verify "verifica" que se ha llamado al metodo mockeado
		//Hay que ponerlo luego de donde se "llama" al metodo:
		verify(srv,times(1)).deleteById(1);
		
	}

}