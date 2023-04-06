package com.example.domains.entities.dtos;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.example.domains.entities.Film;

import lombok.Value;

@Value
public class FilmDetailsDTO {
	
	private int filmId;
	
	private String description;
	
	private int length;
	
	private String rating;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
	private Short releaseYear;
	
	private byte rentalDuration;
	
	private BigDecimal rentalRate;
	
	private BigDecimal replacementCost;
	
	private String title;
	
	private String language;
	
	private String languageVO;
	
	private List<String> actors;
	
	private List<String> categories;
	
	public static FilmDetailsDTO from(Film film) {
		return new FilmDetailsDTO(
				film.getFilmId(), 
				film.getDescription(),
				film.getLength(),
				film.getRating().getValue(),
				film.getReleaseYear(),
				film.getRentalDuration(),
				film.getRentalRate(),
				film.getReplacementCost(),
				film.getTitle(),
				film.getLanguage() == null ? null : film.getLanguage().getName(),
				film.getLanguageVO() == null ? null : film.getLanguageVO().getName(),
				film.getActors().stream()
					.map(actor -> actor.getFirstName() + " " + actor.getLastName())
					.sorted().toList(),
				film.getCategories().stream()
					.map(category -> category.getName()).sorted().toList()
				);
	}
}