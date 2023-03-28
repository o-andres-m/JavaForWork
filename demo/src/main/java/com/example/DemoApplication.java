package com.example;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;




@SpringBootApplication
public class DemoApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		
	}
		
	@Autowired
	ActorRepository dao;

	
	@Override
	public void run(String... args) throws Exception {
		
		
		System.out.println("Aplicacion Running...");
		
		//var actor = new Actor(0, "Pepito", "Grillo");
		//dao.save(actor);
		
		dao.deleteById(201);
		
		var item = dao.findById(201);
		if(item.isPresent()) {
			var actor = item.get();
			actor.setLastName(actor.getLastName().toUpperCase());
			dao.save(actor);
			dao.findAll().forEach(System.out::println);
		}else {
			System.out.println("Actor no encontrado.");
		}
		
		var actorList = dao.findTop5ByFirstNameStartingWithOrderByLastNameDesc("P");
		
		actorList.forEach(System.out::println);
		
		
		var actorList2 = dao.findTop10ByFirstNameStartingWith("",Sort.by("ActorId"));
		
		actorList2.forEach(System.out::println);
		
		var actorList3 = dao.findJPQL(12);
		
		actorList3.forEach(System.out::println);
		
		var actorList4 = dao.findAllConJPQL();
		
		actorList4.forEach(System.out::println);
		
		var actorList5 = dao.findActorConNativeSQL(7);
		
		actorList5.forEach(System.out::println);
		
	}

}
