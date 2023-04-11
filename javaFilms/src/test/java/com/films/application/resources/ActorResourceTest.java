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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.films.domains.contracts.services.ActorService;
import com.films.domains.core.exceptions.InvalidDataException;
import com.films.domains.entities.Film;
import com.films.domains.entities.Actor;
import com.films.domains.entities.FilmActor;
import com.films.domains.entities.dto.ActorDTO;
import com.films.domains.entities.dto.ActorShort;
import com.films.domains.entities.dto.ActorShortDTO;

import lombok.Value;

@WebMvcTest(ActorResource.class)
class ActorResourceTest {

	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private ActorService srv;

	@Autowired
	ObjectMapper objectMapper;
	
	
	@Value
	static class ActorShortMock implements ActorShort {
		
		int actorId;
		
		String completeName;
		
		@Override
		public String getName() {
			return null;
		}
	}

	@Nested
	class GetAll{
		
		@Test
		void testGetAll() throws Exception {
			
			List<ActorShort> lista = new ArrayList<>(
			        Arrays.asList(
			        		new ActorShortMock(1, "FirstName LastName"),
			        		new ActorShortMock(2, "Second Actor"),
			        		new ActorShortMock(3, "Third NAN"))
			        		);
			
			when(srv.getByProjection(ActorShort.class)).thenReturn(lista);
			
			mockMvc.perform(get("/api/actors/v1").accept(MediaType.APPLICATION_JSON))
				.andExpectAll(
						status().isOk(), 
						content().contentType("application/json"),
						jsonPath("$.size()").value(3)
						).andDo(print());
		}
		
		@Test
		void testGetWithSort() throws Exception {
			
			List<ActorShort> listOrderedByName = new ArrayList<>(
			        Arrays.asList(
			        		new ActorShortMock(3, "FirstName LastName"),
			        		new ActorShortMock(1, "Second Actor"),
			        		new ActorShortMock(2, "Third NAN")
			        		));
	
			
			when(srv.getByProjection(Sort.by("firstName"), ActorShort.class)).thenReturn(listOrderedByName);
			
			mockMvc.perform(get("/api/actors/v1")
					.param("sort", "firstName")
					.accept(MediaType.APPLICATION_JSON))
				.andExpectAll(
						status().isOk(), 
						content().contentType("application/json"),
						jsonPath("$.size()").value(3),
						jsonPath("$[0].completeName").value("FirstName LastName"),
						jsonPath("$[0].actorId").value(3)
						).andDo(print());
		}
		
		@Test
		void testGetAllPageable() throws Exception {
			List<ActorDTO> list = new ArrayList<>(
			        Arrays.asList(new ActorDTO(1, "Name", "Surname"),
			        		new ActorDTO(2, "Second", "Actor"),
			        		new ActorDTO(3, "FirstName", "LastName")));

			when(srv.getByProjection(PageRequest.of(0, 20), ActorDTO.class))
				.thenReturn(new PageImpl<>(list ));
			
			mockMvc.perform(get("/api/actors/v1").queryParam("page", "0"))
				.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.content.size()").value(3),
					jsonPath("$.size").value(3)
					).andDo(print());
		}
	}


	@Nested
	class ActorFilms{
		@Test
		void testGetActorFilms() throws Exception {
			var id = 1;
			var actor = new Actor(id, "Name", "LastName");
			
			var actorFilms=new ArrayList<FilmActor>(
					Arrays.asList(
							new FilmActor(new Film(),actor),
							new FilmActor(new Film(),actor)
							));
			
			actor.setFilmActors(actorFilms);
			
			when(srv.getOne(actor.getActorId())).thenReturn(Optional.of(actor));
			
			mockMvc.perform(get("/api/actors/v1/{id}/films", id))
			.andExpectAll(
				status().isOk(),
				jsonPath("$.size()").value(2)
				)
		        .andDo(print());
		}
		@Test
		void testGetActorFilmsNotFound() throws Exception {
			
			int id = 1;
			
			when(srv.getOne(id)).thenReturn(Optional.empty());
			
			mockMvc.perform(get("/api/actors/v1/{id}/films", id))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("Not Found"))
		        .andDo(print());
		}
	}

	@Nested
	class GetOne{

		@Test
		void testGetOne() throws Exception {

			var actor = new Actor(1, "Name", "LastName");
			
			when(srv.getOne(1)).thenReturn(Optional.of(actor));
			
			mockMvc.perform(get("/api/actors/v1/1"))
			.andExpectAll(
				status().isOk(),
		        jsonPath("$.id").value(1),
		        jsonPath("$.name").value(actor.getFirstName()),
		        jsonPath("$.lastName").value(actor.getLastName())
		        ).andDo(print());
		}
		
		@Test
		void testGetOneNotFound() throws Exception {
			
			int id = 1;
			
			when(srv.getOne(id)).thenReturn(Optional.empty());
			
			mockMvc.perform(get("/api/actors/v1/{id}", id))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("Not Found"))
		        .andDo(print());
		}
	}
	
	@Nested
	class Create{
		

		@Test
		void testCreate() throws Exception {
			int id = 1;
			var actor = new Actor(id, "Name", "LastName");
			
			when(srv.add(actor)).thenReturn(actor);
			
			mockMvc.perform(post("/api/actors/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ActorDTO.from(actor)))
				)
				.andExpect(status().isCreated())
		        .andExpect(header().string("Location", "http://localhost/api/actors/v1/1"))
		        .andDo(print());
		}
		
		@Test
		void testCreateError() throws Exception {
			int id = 1;
			var actor = new Actor(id, "", "LastName");
			
			when(srv.add(actor)).thenThrow(new InvalidDataException() );
			
			mockMvc.perform(post("/api/actors/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ActorDTO.from(actor)))
				)
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.title").value("Bad Request"))
		        .andDo(print());
		}

	}
	
	@Nested
	class Update{

		@Test
		void testUpdate() throws JsonProcessingException, Exception {
			
			var actor = new Actor(1, "Name", "NewLastName");

			when(srv.modify(actor)).thenReturn(actor);
			
			mockMvc.perform(put("/api/actors/v1/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(ActorDTO.from(actor)))
					)
					.andExpect(status().isNoContent())
			        .andDo(print());
		}
		
		@Test
		void testUpdateDifferentsIDS() throws JsonProcessingException, Exception {
			
			var actor = new Actor(2, "Name", "NewLastName");
			
			mockMvc.perform(put("/api/actors/v1/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(ActorDTO.from(actor)))
					)
			.andExpectAll(
					status().isBadRequest(),
					jsonPath("$.title").value("Bad Request"),
					jsonPath("$.detail").value("ID's doesn't match")

			        ).andDo(print());
		}
	}
	
	@Nested
	class Delete{
		@Test
		void testDelete() throws JsonProcessingException, Exception {

			doNothing().when(srv).deleteById(1);
			
			mockMvc.perform(delete("/api/actors/v1/1")
					)
					.andExpect(status().isNoContent())
			        .andDo(print());	
			
			verify(srv,times(1)).deleteById(1);
		}
	}
	
	@Nested
	class News{
		@Test
		void testNews() throws JsonProcessingException, Exception {

			var actor1= new Actor(1);
			actor1.setFirstName("First1");
			actor1.setLastName("Last1");

			var actor2= new Actor(2);
			actor2.setFirstName("First2");
			actor2.setLastName("Last2");
			
			var actor3= new Actor(3);
			actor3.setFirstName("First3");
			actor3.setLastName("Last3");
			
			var listOfActors = new ArrayList<Actor>(
			        Arrays.asList(actor1, actor2, actor3));
			
			var stringTime = "2023-01-01 00:00:00";
			
			var time = Timestamp.valueOf(stringTime);
			
			when(srv.news(time)).thenReturn(listOfActors);
			
			mockMvc.perform(get("/api/actors/v1/news")
					.param("time", stringTime)
					)
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.size()").value(3))
			        .andDo(print());	
			
		}
	}
	
}