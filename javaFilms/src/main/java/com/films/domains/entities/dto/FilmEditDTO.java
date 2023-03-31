package com.films.domains.entities.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.films.domains.entities.Film;
import com.films.domains.entities.Language;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmEditDTO {
	
	private int filmId;
	
	private String description;
	
	private int length;
	
	@Pattern(regexp = "^(G|PG|PG-13|R|NC-17)$")
	private String rating;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
	private Short releaseYear;
	
	private byte rentalDuration;
	
	private BigDecimal rentalRate;
	
	private BigDecimal replacementCost;
	
	@NotBlank
	@Size(min=2, max = 128)
	private String title;
	
	@NotNull
	private Integer languageId;
	
	private Integer languageVOId;
	
	//Estamos en EDIT, al recibir el EDIT, vamos a recibir una 
	//LISTA de NUMEROS de los ID de Actores
	private List<Integer> actors = new ArrayList<Integer>();
	
	//Idem arriba, pero con categoría.
	private List<Integer> categories = new ArrayList<Integer>();

 	public static FilmEditDTO from(Film film) {
		return new FilmEditDTO(
				film.getFilmId(), 
				film.getDescription(),
				film.getLength(),
				film.getRating() == null ? null : film.getRating().getValue(),
				film.getReleaseYear(),
				film.getRentalDuration(),
				film.getRentalRate(),
				film.getReplacementCost(),
				film.getTitle(),
				film.getLanguage() == null ? null : film.getLanguage().getLanguageId(),
				film.getLanguageVO() == null ? null : film.getLanguageVO().getLanguageId(),
				film.getActors().stream()
					.map(actor -> actor.getActorId())
					.collect(Collectors.toList()),
				film.getCategories().stream().map(item -> item.getCategoryId())
					.collect(Collectors.toList())
				);
	}
	public static Film from(FilmEditDTO filmEditDTO) {
		Film film = new Film(
				filmEditDTO.getFilmId(), 
				filmEditDTO.getTitle(),
				filmEditDTO.getDescription(),
				filmEditDTO.getReleaseYear(),
				filmEditDTO.getLanguageId() == null ? null : new Language(filmEditDTO.getLanguageId()),
				filmEditDTO.getLanguageVOId() == null ? null : new Language(filmEditDTO.getLanguageVOId()),
				filmEditDTO.getRentalDuration(),
				filmEditDTO.getRentalRate(),
				filmEditDTO.getLength(),
				filmEditDTO.getReplacementCost(),
				filmEditDTO.getRating() == null ? null : Film.Rating.getEnum(filmEditDTO.getRating())
				);
		filmEditDTO.getActors().stream()
			.forEach(actor -> film.addActor(actor));
		filmEditDTO.getCategories().stream()
			.forEach(category -> film.addCategory(category));
		return film;
	}

}