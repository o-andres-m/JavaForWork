package com.films;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.films.domains.contracts.services.ActorService;
import com.films.domains.contracts.services.CategoryService;
import com.films.domains.entities.Actor;
import com.films.domains.entities.Category;

@SpringBootApplication
public class JavaFilmsApplication implements CommandLineRunner {
	
	@Autowired
	ActorService actorService;
	
	@Autowired
	CategoryService categoryService;

	public static void main(String[] args) {
		SpringApplication.run(JavaFilmsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("Hello JavaFilms!");
		
		var list = actorService.getAll();
		
		for (Actor actor : list) {
			System.out.println(actor.toString());
		}
		
		
		//categoryService.add(new Category(0,"Nueva Categoria2"));
		categoryService.deleteById(19);
		categoryService.getAll().forEach(System.out::println);

	}

}
