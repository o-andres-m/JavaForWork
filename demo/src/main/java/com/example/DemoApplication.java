package com.example;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.ioc.Rango;
import com.example.ioc.StringRepository;
import com.example.ioc.StringRepositoryImpl;
import com.example.ioc.StringService;
import com.example.ioc.StringServiceImpl;
import com.example.ioc.UnaTonteria;

import lombok.Data;
import lombok.experimental.var;

/**
 * 
 * @author osvmarti
 *
 */
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		//
		
		
	}
		
	//vamos a inyectar un local, y un remoto
	@Autowired
	@Qualifier("Local")
	@Lazy //Esto hace que NO LO INYECTE hasta tanto NO LO NECESITE
	private StringService srvLocal;
	
	@Autowired
	@Qualifier("Remoto")
	private StringService srvRemoto;
	
	
	@Value("${mi.valor:NO TIENE VALOR}") // lo saca del application.propietis.
	//Si no se lo ponemos en ${} pondra el valor que este dentro...
	// y luego de los : nos dice el valor default si no encuentra
	private String valor;
	
	@Autowired
	private Rango rango;
	
	
	@Autowired
	UnaTonteria unaTonteria;
	
	
	@Autowired
	UnaTonteria unaTonteriaProfiles;
	
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		//Inyeccion manual:
		/*
		
		StringRepository dao = new StringRepositoryImpl();
		
		var srv = new StringServiceImpl(dao);
		
		*/
		
		//Inyeccion automatica, ver linea 31
		//nos trae el srv
		System.out.println(srvLocal.get(1));
		System.out.println("-------------");
		
		//Inyectamos un local que es el de arriba, y un remoto
		System.out.println(srvRemoto.get(1));
		System.out.println("-------------");
		
		
		System.out.println("Usa el mockeado proque esta como @Repository @Primary");
		
		//Aca vamos a probar si la instanciacion en "local o remoto" son la misma
		srvLocal.add("Desde Local");
		System.out.println("-------------");
		
		srvRemoto.add("Desde Remoto");
		System.out.println("-------------");
		
		System.out.println(valor);
		
		System.out.println("--------");
		// Los valores del rango los obtenemos con el @configurationpropietis
		System.out.println(rango.getMin());
		System.out.println(rango.getMax());
		System.out.println(rango.toString());

		System.out.println("--------");

		//Vamos a ver el tema de configuration y bean
		System.out.println(unaTonteria.dimeAlgo());
		
		///////////////////////////77
		
		System.out.println(valor);
		
		///////////////7
		
		System.out.println(unaTonteriaProfiles.dimeAlgo());
		
		
		System.out.println("--------");

		System.out.println("Se agrego esto para GIT");

		
	}

}
