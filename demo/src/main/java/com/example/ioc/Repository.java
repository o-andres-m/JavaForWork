package com.example.ioc;

public interface Repository <T>{ // la T lo hace generico
	
	//el concepto de repositorio es GENERAL
	//Lo hace generico par aque sea de la misma manera para todos,
	//es decir, todos tienen los mismos METODOS
	
	T load();
	void save(T item);
	//
	

}
