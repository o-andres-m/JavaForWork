package com.example.ioc;

import com.example.exceptions.InvalidDataException;

public interface Repository <T>{ 
	
	//el concepto de repositorio es GENERAL
	//Lo hace generico par aque sea de la misma manera para todos,
	//es decir, todos tienen los mismos METODOS
	
	T load();
	void save(T item) throws InvalidDataException;
	

}
