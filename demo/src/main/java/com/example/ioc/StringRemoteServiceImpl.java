package com.example.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("Remoto")
public class StringRemoteServiceImpl implements StringService {
	
	@Autowired
	private StringRepository dao; //data acceso object
	//Aca usamos autowired
	//Ponemos el QUALIFIER para "elegir" cual usar

	@Override
	public String get(Integer id) {
		// TODO Auto-generated method stub
		return dao.load() + " EN REMOTO";
	}

	@Override
	public void add(String item) {
		dao.save(item);
	}

	@Override
	public void modify(String item) {
		dao.save(item);
	}

	@Override
	public void remove(Integer id) {
		dao.save(id.toString());
		
	}



}
