package com.example.ioc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
// OJO!!! Porque primario?? Porque tenemos 2 implementaciones, entonces
//cuando intente buscar cual usar, le elegimos un PRIMARIO
// Esto se llama CAUALIFICACION
// Podemos tener mas de 1, pero con el @Qualifier "elegimos" cual usar
// Esto se usa mucho cuando etamos en migracion
public class StringRepositoryMockImpl implements StringRepository {
	
	private String ultimo = "";

	
	@Override
	public String load() {
		// TODO Auto-generated method stub
		return "Soy el StringRepository Mockeado";
	}

	@Override
	public void save(String item) {
		// TODO Auto-generated method stub
		System.out.println("Anterior : "+ultimo);
		this.ultimo=item;
		System.out.println("Guardado en item  : "+item);
	}

}