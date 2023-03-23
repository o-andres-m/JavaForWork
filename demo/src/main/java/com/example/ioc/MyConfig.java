package com.example.ioc;

import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

//@Configuration  le comentamos el configuration, porque toma el MyProfile
//Lo usamos cuando tenemos un objeto que se puede crear de varias formas
public class MyConfig{


	@Bean
	UnaTonteria unaTonteria() {
		return new UnaTonteria() {
			
			@Override
			public String dimeAlgo() {
				return "Dice una tonteria";
			}
		};
	}
	
	@Bean
	@Primary
	UnaTonteria otraTonteria() {
		return new UnaTonteria() {
			
			@Override
			public String dimeAlgo() {
				return "Dice OTRA tonteria";
			}
		};
	}

	//	@Bean
	//	@Primary
	//	Rango unRango() {
	//		return Rango.builder().min(10).max(100).build();
	//	}
}
