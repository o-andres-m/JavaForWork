package com.example.ioc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.example.exceptions.InvalidArgumentException;
import com.example.exceptions.InvalidDataException;

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
		return "Soy el StringRepository Mockeado";
	}

	@Override
	public void save(String item) throws InvalidDataException {
		//if(item == "") throw new InvalidArgumentException();
		if(item == "") throw new InvalidDataException("La cadena no puede estar vacia");

		System.out.println("Anterior : "+ultimo);
		this.ultimo=item;
		System.out.println("Guardado en item  : "+item);
	}

}
