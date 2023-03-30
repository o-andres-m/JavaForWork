package com.films;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.films.domains.contracts.services.ActorService;
import com.films.domains.contracts.services.CategoryService;
import com.films.domains.contracts.services.FilmService;
import com.films.domains.contracts.services.LanguageService;
import com.films.domains.entities.Actor;

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

	}

}
