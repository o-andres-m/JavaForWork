package com.films.domains.entities.dto;

import java.util.List;
import java.util.Map;

import com.films.domains.entities.Category;
import com.films.domains.entities.Language;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDto {
	
	//Original:
	//private List<FilmEditDTO> films;
	
	private List<FilmDetailsDTO> films;
	
	private List<ActorDTO> actors;
	
	private List<Category> categories;
	
	private List<Language> languages;

	
	
	@Override
	public String toString() {
		return "NovedadesDTO[\nfilms=" + films + ",\nactors=" + actors + ",\ncategories=" + categories + ",\nlanguages=" + languages + "\n]";
	}
}