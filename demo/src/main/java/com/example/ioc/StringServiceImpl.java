package com.example.ioc;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@Service
@Qualifier("Local")
//aca vemos que tenemos 2 service para 1 misma interface
//lo que hacemos los CUALIFICAMOS para decirle CUAL ELEGIR al momento de necesitarla
public class StringServiceImpl implements StringService {
	
	
	private StringRepository dao;
	
	//no usamos autowired, lo asignamos en constructr
	public StringServiceImpl(StringRepository dao) {
		this.dao = dao;
		System.out.println("Creando el StringServiceImp en LOCAL sin Autowired");
	}

	@Override
	public String get(Integer id) {
		return dao.load() + " en LOCAL";
	}

	@Override
	public void add(String item) throws NotFoundException {
		try {
			dao.save(item);
		} catch (InvalidDataException e) {
			throw new NotFoundException("No encontrado", e);
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
