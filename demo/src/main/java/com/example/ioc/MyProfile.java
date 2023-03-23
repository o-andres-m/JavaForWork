package com.example.ioc;

import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class MyProfile{

	//Vemos los perfiles, asi elige una segun el caso:

	@Bean
	@Profile("default")
	UnaTonteria unaTonteria() {
		return new UnaTonteria() {
			
			@Override
			public String dimeAlgo() {
				return "Dice una tonteria POR DEFAULT";
			}
		};
	}
	
	@Bean
	@Profile("test")
	UnaTonteria unaTonteriaTest() {
		return new UnaTonteria() {
			
			@Override
			public String dimeAlgo() {
				return "Dice una tonteria DESDE TEST";
			}
		};
	}
	
	@Bean
	@Profile("prod")
	UnaTonteria unaTonteriaProd() {
		return new UnaTonteria() {
			
			@Override
			public String dimeAlgo() {
				return "Dice una tonteria DESDE PRODUCCION";
			}
		};
	}
	
}
