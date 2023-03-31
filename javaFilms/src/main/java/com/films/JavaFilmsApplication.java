package com.films;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.films.application.services.NewsService;
import com.films.domains.contracts.services.ActorService;
import com.films.domains.contracts.services.CategoryService;
import com.films.domains.contracts.services.FilmService;
import com.films.domains.contracts.services.LanguageService;
import com.films.domains.entities.Actor;
import com.films.domains.entities.Film;
import com.films.domains.entities.Film.*;


import com.films.domains.entities.Language;

@SpringBootApplication
public class JavaFilmsApplication implements CommandLineRunner {
	
	@Autowired
	ActorService actorService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	LanguageService languageService;
	
	@Autowired
	FilmService filmService;
	
	@Autowired
	NewsService newsService;

	public static void main(String[] args) {
		SpringApplication.run(JavaFilmsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("Hello JavaFilms!");

//		actorService.getAll().forEach(System.out::println);
//		Thread.sleep(3000);
//		categoryService.getAll().forEach(System.out::println);
//		Thread.sleep(3000);
//		filmService.getAll().forEach(System.out::println);
//		Thread.sleep(3000);
//		languageService.getAll().forEach(System.out::println);
		
//		var film1 = filmService.getOne(1);
//		System.out.println(film1.get().getTitle());
//		System.out.println(film1.get().getDescription());
//		System.out.println(film1.get().getLanguageVO());
//		film1.get().getFilmActors().forEach(System.out::println);
		//System.out.println();
		//System.out.println(film1.get().getFilmCategories());
		
		
//		////////// De profe:
//		
//		var peli = new Film("Hola mundo", new Language(2));
//		peli.setRentalDuration((byte)3);
//		peli.setRating(Rating.ADULTS_ONLY);
//		peli.setLength(10);
//		peli.setRentalRate(new BigDecimal(10.0));
//		peli.setReplacementCost(new BigDecimal(10.0));
//		peli.addActor(1);
//		peli.addActor(2);
//		peli.addActor(3);
//		peli.addCategory(2);
//		peli.addCategory(4);
//		filmService.add(peli);
//		peli = filmService.getOne(1008).get();
//		peli.removeActor(new Actor(1));
//		peli.removeActor(new Actor(2));
//		peli.addActor(4);
//		peli.removeCategory(peli.getCategories().get(0));
//		peli.addCategory(1);
//		peli.setTitle("Adios mundo");
//		
//		
//		var peli = new Film("Hola mundo", new Language(2), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0));
////		peli.setRating(Rating.ADULTS_ONLY);
////		peli.addActor(1);
////		peli.addActor(2);
////		peli.addActor(3);
////		peli.addCategory(2);
////		peli.addCategory(4);
////		System.out.println(peli.getErrorsMessage());
////		peli = srv.add(peli);
////		System.out.println(peli.getFilmId());
////		peli = srv.getOne(1001).get();
////		peli.removeActor(new Actor(1));
////		peli.removeActor(new Actor(2));
////		peli.addActor(4);
////		peli.removeCategory(peli.getCategories().get(0));
////		peli.addCategory(1);
////		peli.setTitle("Adios mundo");
////		srv.modify(peli);
////		srv.deleteById(1001);
		
		var peli = new Film();
		peli.setTitle("Prueba Actores");
		peli.setLanguage(new Language(2));
		peli.setRentalDuration((byte)3);
		peli.setRating(Rating.ADULTS_ONLY);
		peli.setLength(10);
		peli.setRentalRate(new BigDecimal(10.0));
		peli.setReplacementCost(new BigDecimal(10.0));
		
		peli.addActor(10);
//		peli.addActor(2);
//		peli.addActor(4);
//		
//		peli.addCategory(2);
//		peli.addCategory(4);
		
		filmService.add(peli);
		
		//System.out.println(catalogoService.novedades(Timestamp.valueOf("2023-01-01 00:00:00")).toString());

	}

}
