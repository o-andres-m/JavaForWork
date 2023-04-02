package com.films.domains.entities.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.films.domains.entities.Film;

class FilmShortDTOTest {

	@Test
	void testReturnFilmDetailsDto() {
		var film = new Film();
		film.setFilmId(1);
		film.setTitle("Title");

		assertNotNull(FilmShortDTO.from(film));
		assertTrue(FilmShortDTO.from(film) instanceof FilmShortDTO);
	}
	
	@Test
	void testAttributes() {
		var film = new Film();
		film.setFilmId(1);
		film.setTitle("Title");

		var filmDto = FilmShortDTO.from(film);
		
		assertNotNull(filmDto);
		
		assertAll("Attributes",
				()-> assertEquals(film.getFilmId(),filmDto.getFilmId()),
				()-> assertEquals(film.getTitle(),filmDto.getTitle())
				);
	}

}
