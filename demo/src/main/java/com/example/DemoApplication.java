package com.example;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;

import jakarta.transaction.Transactional;
import jakarta.validation.Validation;
import jakarta.validation.Validator;




@SpringBootApplication
public class DemoApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		
	}
		
	@Autowired
	ActorRepository dao;

	
	@Override
	@Transactional
	// Transactional es cuando usamos el LAZY, que no cierra la conexion con la DB
	public void run(String... args) throws Exception {
		
		
		System.out.println("Aplicacion Running...");
		
		//var actor = new Actor(0, "Pepito", "Grillo");
		//dao.save(actor);
		
//		dao.deleteById(201);
//		
//		var item = dao.findById(201);
//		if(item.isPresent()) {
//			var actor = item.get();
//			actor.setLastName(actor.getLastName().toUpperCase());
//			dao.save(actor);
//			dao.findAll().forEach(System.out::println);
//		}else {
//			System.out.println("Actor no encontrado.");
//		}
//		
//		var actorList = dao.findTop5ByFirstNameStartingWithOrderByLastNameDesc("P");
//		
//		actorList.forEach(System.out::println);
//		
//		
//		var actorList2 = dao.findTop10ByFirstNameStartingWith("",Sort.by("ActorId"));
//		
//		actorList2.forEach(System.out::println);
//		
//		var actorList3 = dao.findJPQL(12);
//		
//		actorList3.forEach(System.out::println);
//		
//		var actorList4 = dao.findAllConJPQL();
//		
//		actorList4.forEach(System.out::println);
//		
//		var actorList5 = dao.findActorConNativeSQL(7);
//		
//		actorList5.forEach(System.out::println);
//		
//		var actorList6 = dao.findAll((root,query,builder)-> builder.lessThan(root.get("actorId"),5));
//		
//		actorList6.forEach(System.out::println);
//		
//		var actorList7 = dao.findAll((root,query,builder)-> builder.greaterThan(root.get("actorId"),180));
//		
//		actorList7.forEach(System.out::println);
		
		
//		var item = dao.findById(1);
//		if(item.isPresent()) {
//			var actor = item.get();
//			System.out.println(actor);
//			actor.getFilmActors().forEach(valor-> System.out.println(valor.getFilm().getTitle()));
//		}else {
//			System.out.println("Actor no encontrado.");
//		}
//		
		//////////////////////////7
		
		// Validaciones:
		
//		var actor = new Actor(0, "Pepito", "o");
//		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//		var err = validator.validate(actor);
//		if (err.size()>0) {
//			err.forEach(e -> System.out.println(e.getPropertyPath() + ": " + e.getMessage()));
//		}else {
//			dao.save(actor);
//			System.out.println(actor);
//		}
		
		// Para evitar todo esto
		
		//Primero que todo hay que tener un EntityBase que es de donde EXTENDERA nuestra entidad
		//Por eso vemos que la entidad ACTOR extiende del EntityBase
		
//		var actor = new Actor(0, "Pepito", "OSCAR");
//		if (actor.isInvalid()) {
//			System.out.println(actor.getErrorsMessage());		
//		}else {
//			dao.save(actor);
//			System.out.println(actor);
//
//		}
		
		// Para probar el VALIDADOR DE NIF
		// Se le pone el decorador @Nif a LastName de Actor para esta prueba:
		
		var actor = new Actor(0, "4G", "OSCAR");
		if (actor.isInvalid()) {
			System.out.println(actor.getErrorsMessage());		
		}else {
			dao.save(actor);
			System.out.println(actor);

		}
		
		
		
	}

}
