package com.films.domains.entities.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.films.domains.entities.Actor;
import com.films.domains.entities.Category;
import com.films.domains.entities.Film;
import com.films.domains.entities.Film.Rating;

class FilmEditDTOTest {

	@Test
	void testReturnFilmDetailsDto() {
		var film = new Film();
		film.setFilmId(1);
		film.setDescription("Description");
		film.setLength(1);
		film.setRating(Rating.ADULTS_ONLY);
		film.setReleaseYear(Short.valueOf("2000"));
		film.setRentalDuration(Byte.valueOf("10"));
		film.setRentalRate(null);
		film.setReplacementCost(null);
		film.setTitle("Title");
		film.setLanguage(null);
		film.setLanguageVO(null);
		film.setActors(List.of(new Actor()));
		film.setCategories(List.of(new Category()));

		assertNotNull(FilmEditDTO.from(film));
		assertTrue(FilmEditDTO.from(film) instanceof FilmEditDTO);
	}
	
	@Test
	void testAttributesReturnDto() {
		var film = new Film();
		film.setFilmId(1);
		film.setDescription("Description");
		film.setLength(1);
		film.setRating(Rating.ADULTS_ONLY);
		film.setReleaseYear(Short.valueOf("2000"));
		film.setRentalDuration(Byte.valueOf("10"));
		film.setRentalRate(null);
		film.setReplacementCost(null);
		film.setTitle("Title");
		film.setLanguage(null);
		film.setLanguageVO(null);
		film.setActors(List.of());
		film.setCategories(List.of());

		var filmDto = FilmEditDTO.from(film);
		
		assertNotNull(filmDto);
		
		assertAll("Attributes",
				()-> assertEquals(film.getFilmId(),filmDto.getFilmId()),
				()-> assertEquals(film.getTitle(),filmDto.getTitle()),
				()-> assertEquals(film.getDescription(),filmDto.getDescription()),
				()-> assertEquals(film.getReleaseYear(),filmDto.getReleaseYear()),
				()-> assertEquals(film.getLanguage(),filmDto.getLanguageId()),
				()-> assertEquals(film.getLanguageVO(),filmDto.getLanguageVOId()),
				()-> assertEquals(film.getRentalDuration(),filmDto.getRentalDuration()),
				()-> assertEquals(film.getRentalRate(),filmDto.getRentalRate()),
				()-> assertEquals(film.getLength(),filmDto.getLength()),
				()-> assertEquals(film.getReplacementCost(),filmDto.getReplacementCost()),
				()-> assertEquals(Rating.ADULTS_ONLY.getValue(),filmDto.getRating()),
				()-> assertEquals(film.getActors(),filmDto.getActors()),
				()-> assertEquals(film.getCategories(),filmDto.getCategories())
				);
	}
	
	@Test
	void testAttributesReturnFilm() {
		var filmDto = new FilmEditDTO();
		filmDto.setFilmId(1);
		filmDto.setDescription("Description");
		filmDto.setLength(1);
		filmDto.setRating(Rating.ADULTS_ONLY.getValue());
		filmDto.setReleaseYear(Short.valueOf("2000"));
		filmDto.setRentalDuration(Byte.valueOf("10"));
		filmDto.setRentalRate(null);
		filmDto.setReplacementCost(null);
		filmDto.setTitle("Title");
		filmDto.setLanguageId(null);
		filmDto.setLanguageVOId(null);
		filmDto.setActors(List.of());
		filmDto.setCategories(List.of());

		var film = FilmEditDTO.from(filmDto);
		
		assertNotNull(film);
		
		assertAll("Attributes",
				()-> assertEquals(filmDto.getFilmId(),film.getFilmId()),
				()-> assertEquals(filmDto.getTitle(),film.getTitle()),
				()-> assertEquals(filmDto.getDescription(),film.getDescription()),
				()-> assertEquals(filmDto.getReleaseYear(),film.getReleaseYear()),
				()-> assertEquals(filmDto.getLanguageId(),film.getLanguage()),
				()-> assertEquals(filmDto.getLanguageVOId(),film.getLanguageVO()),
				()-> assertEquals(filmDto.getRentalDuration(),film.getRentalDuration()),
				()-> assertEquals(filmDto.getRentalRate(),film.getRentalRate()),
				()-> assertEquals(filmDto.getLength(),film.getLength()),
				()-> assertEquals(filmDto.getReplacementCost(),film.getReplacementCost()),
				()-> assertEquals(Rating.ADULTS_ONLY.getValue(),film.getRating().getValue()),
				()-> assertEquals(filmDto.getActors(),film.getActors()),
				()-> assertEquals(filmDto.getCategories(),film.getCategories())
				);
	}
}
