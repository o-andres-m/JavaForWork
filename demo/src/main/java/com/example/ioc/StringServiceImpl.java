package com.example.ioc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("Local")
//aca vemos que tenemos 2 service para 1 misma interface
//lo que hacemos los CUALIFICAMOS para decirle CUAL ELEGIR al momento de necesitarla
public class StringServiceImpl implements StringService {
	
	
	private StringRepository dao; //data acceso object
	
	//no usamos autowired, lo asignamos en constructr
	public StringServiceImpl(StringRepository dao) {
		this.dao = dao;
		System.out.println("Creando el StringServiceImp en LOCAL sin Autowired");
	}

	@Override
	public String get(Integer id) {
		// TODO Auto-generated method stub
		return dao.load() + " en LOCAL";
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
