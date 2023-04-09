package com.films.application.resources;

import static org.mockito.ArgumentMatchers.any;
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
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
import com.films.domains.contracts.services.FilmService;
import com.films.domains.core.exceptions.InvalidDataException;
import com.films.domains.entities.Actor;
import com.films.domains.entities.Category;
import com.films.domains.entities.Film;
import com.films.domains.entities.Language;
import com.films.domains.entities.Film.Rating;
import com.films.domains.entities.dto.FilmEditDTO;
import com.films.domains.entities.dto.FilmShortDTO;


@WebMvcTest(FilmResource.class)
class FilmResourceTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper om;
	
	@MockBean
	FilmService srv;
	
	
	@Nested
	class GetAll{
		
		@Test
		void testGetAll() throws Exception {
			List<FilmShortDTO> listOfFilms = new ArrayList<>(
					Arrays.asList(	FilmShortDTO.from(new Film()),
							   		FilmShortDTO.from(new Film()),
									FilmShortDTO.from(new Film())));
			when(srv.getByProjection(FilmShortDTO.class)).thenReturn(listOfFilms);
			
			mockMvc.perform(get("/api/films/v1").accept(MediaType.APPLICATION_JSON))
				.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.size()").value(3)
					).andDo(print());
		}
		
		@Test
		void testGetAllSort() throws Exception {
			
			var film1 = new Film();
			film1.setFilmId(1);
			film1.setTitle("C");
			
			var film2 = new Film();
			film2.setFilmId(2);
			film2.setTitle("A");
			
			var film3 = new Film();
			film3.setFilmId(3);
			film3.setTitle("B");
			
			List<FilmShortDTO> listOfFilmsOrderByTitle = new ArrayList<>(
					Arrays.asList(	FilmShortDTO.from(film2),
							   		FilmShortDTO.from(film3),
									FilmShortDTO.from(film1)));
			
			when(srv.getByProjection(Sort.by("title"),FilmShortDTO.class)).thenReturn(listOfFilmsOrderByTitle);
			
			mockMvc.perform(get("/api/films/v1")
					.param("sort", "title")
					.accept(MediaType.APPLICATION_JSON)
					)
				.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.size()").value(3),
					jsonPath("$[0].filmId").value(2),
					jsonPath("$[0].title").value("A")
					).andDo(print());
		}
		@Test
		void testGetAllPageable() throws Exception {
			var film1 = new Film();
			film1.setFilmId(1);
			film1.setTitle("C");
			
			var film2 = new Film();
			film2.setFilmId(2);
			film2.setTitle("A");
			
			var film3 = new Film();
			film3.setFilmId(3);
			film3.setTitle("B");
			
			List<FilmShortDTO> listOfFilms = new ArrayList<>(
					Arrays.asList(	FilmShortDTO.from(film1),
							   		FilmShortDTO.from(film2),
									FilmShortDTO.from(film3)));

			when(srv.getByProjection(PageRequest.of(0, 20), FilmShortDTO.class))
				.thenReturn(new PageImpl<>(listOfFilms ));
			
			mockMvc.perform(get("/api/films/v1").queryParam("page", "0"))
				.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.content.size()").value(3),
					jsonPath("$.size").value(3)
					).andDo(print());
		}
	}
	
	@Nested
	class GetOne{

		@Test
		void testGetOne() throws Exception {
			
			var id = 1;
			var film = new Film();
			film.setFilmId(id);
			film.setTitle("A");
			film.setDescription("Description");
			film.setLastUpdate(Timestamp.from(Instant.now()));
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
			
			when(srv.getOne(id)).thenReturn(Optional.of(film));
			
			mockMvc.perform(get("/api/films/v1/{id}", id))
			.andExpectAll(
				status().isOk(),
		        jsonPath("$.filmId").value(id),
		        jsonPath("$.title").value(film.getTitle()),
		        jsonPath("$.description").value(film.getDescription())		   
		        ).andDo(print());
		}
		
		@Test
		void testGetOneNotFound() throws Exception {
			
			int id = 1;
			
			when(srv.getOne(id)).thenReturn(Optional.empty());
			
			mockMvc.perform(get("/api/films/v1/{id}", id))
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
			
			var id = 1;
			var film = new Film();
			film.setFilmId(id);
			film.setTitle("AB");
			film.setDescription("Description");
			film.setLastUpdate(Timestamp.from(Instant.now()));
			film.setLength(100);
			film.setRating(Rating.ADULTS_ONLY);
			film.setReleaseYear(Short.valueOf("0"));
			film.setRentalDuration(Byte.valueOf("0"));
			film.setRentalRate(BigDecimal.valueOf(0));
			film.setReplacementCost(BigDecimal.valueOf(0));
			film.setLanguage(new Language(2));
			film.setLanguageVO(new Language(1));
			film.setActors(List.of());
			film.setCategories(List.of());
			
			when(srv.add(film)).thenReturn(film);
			
			mockMvc.perform(post("/api/films/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(FilmEditDTO.from(film)))
				)
			.andExpectAll(
				status().isCreated(),
		        header().string("Location", "http://localhost/api/films/v1/1")
		        ).andDo(print());
		}
		
		@Test
		void testCreateError() throws Exception {
			
			var id = 1;
			var film = new Film();
			film.setFilmId(id);
			film.setTitle("");
			film.setDescription("Description");
			film.setLastUpdate(Timestamp.from(Instant.now()));
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
			
			when(srv.add(film)).thenThrow(new InvalidDataException() );
			
			mockMvc.perform(post("/api/films/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(FilmEditDTO.from(film)))
				)
			.andExpectAll(
				status().isBadRequest(),
				jsonPath("$.title").value("Invalid Data")
		        ).andDo(print());
		}

	}
	
	@Nested
	class Edit{

		@Test
		void testUpdate() throws JsonProcessingException, Exception{
			
			var id = 1;
			var film = new Film();
			film.setFilmId(id);
			film.setTitle("ABC");
			film.setDescription("Description");
			film.setLastUpdate(Timestamp.from(Instant.now()));
			film.setLength(100);
			film.setRating(Rating.ADULTS_ONLY);
			film.setReleaseYear(Short.valueOf("0"));
			film.setRentalDuration(Byte.valueOf("0"));
			film.setRentalRate(BigDecimal.valueOf(0));
			film.setReplacementCost(BigDecimal.valueOf(0));
			film.setLanguage(new Language(1) );
			film.setLanguageVO(new Language (2) );
			film.setActors(List.of());
			film.setCategories(List.of());
			
			
			when(srv.modify(film)).thenReturn(film);
			
			mockMvc.perform(put("/api/films/v1/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(om.writeValueAsString(FilmEditDTO.from(film)))
					)
					.andExpect(status().isNoContent())
			        .andDo(print());
		}
		
		@Test
		void testUpdateDifferentsIDs() throws JsonProcessingException, Exception{
			
			var id = 1;
			var film = new Film();
			film.setFilmId(id);
			film.setTitle("ABC");
			film.setDescription("Description");
			film.setLastUpdate(Timestamp.from(Instant.now()));
			film.setLength(100);
			film.setRating(Rating.ADULTS_ONLY);
			film.setReleaseYear(Short.valueOf("0"));
			film.setRentalDuration(Byte.valueOf("0"));
			film.setRentalRate(BigDecimal.valueOf(0));
			film.setReplacementCost(BigDecimal.valueOf(0));
			film.setLanguage(new Language(1) );
			film.setLanguageVO(new Language (2) );
			film.setActors(List.of());
			film.setCategories(List.of());
			
			
			when(srv.modify(film)).thenReturn(film);
			
			mockMvc.perform(put("/api/films/v1/2")
					.contentType(MediaType.APPLICATION_JSON)
					.content(om.writeValueAsString(FilmEditDTO.from(film)))
					)
			.andExpectAll(
					status().isBadRequest(),
					jsonPath("$.title").value("Bad Request"),
					jsonPath("$.detail").value("IDS doesn't match")

			        ).andDo(print());
		}
		
		@Test
		void testUpdateInvalidData() throws JsonProcessingException, Exception{
			
			var id = 1;
			var film = new Film();
			film.setFilmId(id);
			film.setTitle("ABC");
			film.setDescription("Description");
			film.setLastUpdate(Timestamp.from(Instant.now()));
			film.setLength(100);
			film.setRating(Rating.ADULTS_ONLY);
			film.setReleaseYear(Short.valueOf("0"));
			film.setRentalDuration(Byte.valueOf("0"));
			film.setRentalRate(BigDecimal.valueOf(0));
			film.setReplacementCost(BigDecimal.valueOf(0));
			//film.setLanguage(new Language(1) );
			film.setLanguageVO(new Language (2) );
			film.setActors(List.of());
			film.setCategories(List.of());
			
			
			when(srv.modify(film)).thenReturn(film);
			
			mockMvc.perform(put("/api/films/v1/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(om.writeValueAsString(FilmEditDTO.from(film)))
					)
			.andExpectAll(
					status().isBadRequest(),
					jsonPath("$.title").value("Invalid Data")
			        ).andDo(print());
		}

	}
	
	@Nested
	class Delete{
		@Test
		void testDelete() throws JsonProcessingException, Exception {

			doNothing().when(srv).deleteById(1);
			
			mockMvc.perform(delete("/api/films/v1/1")
					)
					.andExpect(status().isNoContent())
			        .andDo(print());	
			
			verify(srv,times(1)).deleteById(1);
	
		}
	}
	
	@Nested
	class FilmsActors{
		@Test
		void testGetActorsInFilm() throws Exception {
			
			var film = new Film();
			film.setFilmId(1);
			film.setTitle("A");
			
			var actorList = new ArrayList<Actor>(
					Arrays.asList(	new Actor(1,"Actor", "Number1"),
									new Actor(2,"Actor", "Number2")));
			
			film.setActors(actorList);
			
			when(srv.getOne(film.getFilmId())).thenReturn(Optional.of(film));
			
			mockMvc.perform(get("/api/films/v1/1/actors"))
			.andExpectAll(
				status().isOk(),
				jsonPath("$.size()").value(2),
				jsonPath("$[0].key").value(1),
				jsonPath("$[0].value").value("Actor Number1")

				)
		        .andDo(print());
		}
		@Test
		void testGetActorsInFilmNotFound() throws Exception {
			
			int id = 1;
			
			when(srv.getOne(id)).thenReturn(Optional.empty());
			
			mockMvc.perform(get("/api/films/v1/{id}/actors", id))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("Not Found"))
		        .andDo(print());
		}
	}

	@Nested
	class FilmsCategories{
		@Test
		void testGetActorsInFilm() throws Exception {
			
			var film = new Film();
			film.setFilmId(1);
			film.setTitle("A");
			
			var categoryList = new ArrayList<Category>(
					Arrays.asList(	new Category(1),
									new Category(2)));
			
			film.setCategories(categoryList);
			
			when(srv.getOne(film.getFilmId())).thenReturn(Optional.of(film));
			
			mockMvc.perform(get("/api/films/v1/1/categories"))
			.andExpectAll(
				status().isOk(),
				jsonPath("$.size()").value(2),
				jsonPath("$[0].key").value(1)
				)
		        .andDo(print());
		}
		@Test
		void testGetActorsInFilmNotFound() throws Exception {
			
			int id = 1;
			
			when(srv.getOne(id)).thenReturn(Optional.empty());
			
			mockMvc.perform(get("/api/films/v1/{id}/categories", id))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("Not Found"))
		        .andDo(print());
		}
	}
	
	@Nested
	class GetNews{
		
		@Test
		void testGetAllNews() throws Exception {
			
			var film1 = new Film();
			film1.setFilmId(1);
			film1.setTitle("A");
			
			var film2= new Film();
			film2.setFilmId(1);
			film2.setTitle("A");
			
			List<Film> listOfFilms = new ArrayList<>(
					Arrays.asList(film1, film2));
			
			var time = Timestamp.from(Instant.now().minusSeconds(864000));
			
			//when(srv.news(time)).thenReturn(listOfFilms);
			when(srv.news(any())).thenReturn(listOfFilms);
			
			mockMvc.perform(get("/api/films/v1/news").accept(MediaType.APPLICATION_JSON))
				.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.size()").value(2)
					).andDo(print());
		}
		
		@Test
		void testGetAllWith0Days() throws Exception {
			
			var film1 = new Film();
			film1.setFilmId(1);
			film1.setTitle("A");
			
			var film2= new Film();
			film2.setFilmId(1);
			film2.setTitle("A");
			
			List<Film> listOfFilms = new ArrayList<>(
					Arrays.asList(	film1,
							   		film2));
			
			//when(srv.news(Timestamp.from(Instant.now().minusSeconds(864000)))).thenReturn(listOfFilms);
			when(srv.news(any())).thenReturn(listOfFilms);

			mockMvc.perform(get("/api/films/v1/news")
					.param("days", "0")
					.accept(MediaType.APPLICATION_JSON))
				.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.size()").value(2)
					).andDo(print());
		}
		@Test
		void testGetAllWithDays() throws Exception {
			
			var days = 4;
			
			var film1 = new Film();
			film1.setFilmId(1);
			film1.setTitle("A");
			
			var film2= new Film();
			film2.setFilmId(1);
			film2.setTitle("A");
			
			List<Film> listOfFilms = new ArrayList<>(
					Arrays.asList(	film1, film2));
			
			//when(srv.news(Timestamp.from(Instant.now().minusSeconds(days*86400)))).thenReturn(listOfFilms);
			when(srv.news(any())).thenReturn(listOfFilms);

			mockMvc.perform(get("/api/films/v1/news")
					.param("days", String.valueOf(days))
					.accept(MediaType.APPLICATION_JSON))
				.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.size()").value(2)
					).andDo(print());
		}
	}
}
