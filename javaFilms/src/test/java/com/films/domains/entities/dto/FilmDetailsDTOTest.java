package com.films.domains.entities.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.films.domains.entities.Actor;
import com.films.domains.entities.Category;
import com.films.domains.entities.Film;
import com.films.domains.entities.Film.Rating;

class FilmDetailsDTOTest {


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

		assertNotNull(FilmDetailsDTO.from(film));
		assertTrue(FilmDetailsDTO.from(film) instanceof FilmDetailsDTO);
	}
	
	@Test
	void testAttributes() {
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

		var filmDto = FilmDetailsDTO.from(film);
		
		assertNotNull(filmDto);
		
		assertAll("Attributes",
				()-> assertEquals(film.getFilmId(),filmDto.getFilmId()),
				()-> assertEquals(film.getDescription(),filmDto.getDescription()),
				()-> assertEquals(film.getLength(),filmDto.getLength()),
				()-> assertEquals(Rating.ADULTS_ONLY.getValue(),filmDto.getRating()),
				()-> assertEquals(film.getReleaseYear(),filmDto.getReleaseYear()),
				()-> assertEquals(film.getRentalDuration(),filmDto.getRentalDuration()),
				()-> assertEquals(film.getRentalRate(),filmDto.getRentalRate()),
				()-> assertEquals(film.getReplacementCost(),filmDto.getReplacementCost()),
				()-> assertEquals(film.getTitle(),filmDto.getTitle()),
				()-> assertEquals(film.getLanguage(),filmDto.getLanguage()),
				()-> assertEquals(film.getLanguageVO(),filmDto.getLanguageVO()),
				()-> assertEquals(film.getActors(),filmDto.getActors()),
				()-> assertEquals(film.getCategories(),filmDto.getCategories())
				);
	}

}
