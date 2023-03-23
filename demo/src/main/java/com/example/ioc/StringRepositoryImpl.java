package com.example.ioc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
// Podria ser con el @Component que es el generico, pero con el repository
//le decimos mas especifico que es un repositorio
public class StringRepositoryImpl implements StringRepository {
	
	//La clase implementa el Repository
	//Importante el nombre!!
	
	
	

	@Override
	public String load() {
		return "Soy el StringRepositoryImpl";
	}

	@Override
	public void save(String item) {
		System.out.println("Guardado el String -> " + item);

	}

}
