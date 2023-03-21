package com.example.ioc;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Data;

@Data    //lombock
@Component   // es un componente/servicio
@ConfigurationProperties("rango") 
//desde el application.properties saca los valores de "rango"
//@Builder
public class Rango {
	
	// No le asignamos valor, porque los trae desde el configuration.propietis
	//Lo importante es tener los valores alla previamente definidos
	// En este caso seran valor. xxxxx
	// y aqui tenemos un min y un max. asi que buscara valor.min valor.max
	
	// OJO!!! Si aca tenemos un tipo INT, el valor tiene que ser INT
	private int min;
	private int max;

}
