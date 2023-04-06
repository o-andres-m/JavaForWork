package com.example.domains.entities.dtos;

import com.example.domains.entities.Film;

import lombok.Value;

@Value
public class FilmShortDTO {
	
	private int filmId;
	
	private String title;
	
	public static FilmShortDTO from(Film film) {
		return new FilmShortDTO(film.getFilmId(), film.getTitle());
	}
}