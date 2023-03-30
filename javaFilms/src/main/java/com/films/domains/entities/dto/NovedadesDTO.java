package com.films.domains.entities.dto;

import java.util.List;

import com.films.domains.entities.Category;
import com.films.domains.entities.Language;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class NovedadesDTO {
	private List<FilmEditDTO> films;
	private List<ActorDTO> actors;
	private List<Category> categories;
	private List<Language> languages;
	
}