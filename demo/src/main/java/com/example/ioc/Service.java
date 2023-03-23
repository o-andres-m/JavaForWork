package com.example.ioc;

import com.example.exceptions.NotFoundException;

public interface Service <K,V>{ //Tiene una CLAVE y un VALOR
	
	V get(K id); //returna un VALUE cuando le haces get, enviandule una KEY id
	
	void add(V item) throws NotFoundException;
	
	void modify(V item);
	
	void remove(K id);

}
