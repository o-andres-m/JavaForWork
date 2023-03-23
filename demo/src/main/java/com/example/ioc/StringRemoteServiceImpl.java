package com.example.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.exceptions.InvalidDataException;

@Service
@Qualifier("Remoto")
public class StringRemoteServiceImpl implements StringService {
	
	@Autowired
	private StringRepository dao; //data acceso object
	//Aca usamos autowired
	//Ponemos el QUALIFIER para "elegir" cual usar

	@Override
	public String get(Integer id) {
		return dao.load() + " EN REMOTO";
	}

	@Override
	public void add(String item) {
		try {
			dao.save(item);
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void modify(String item) {
		try {
			dao.save(item);
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void remove(Integer id) {
		try {
			dao.save(id.toString());
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



}
