package com.example.domains.entities.dtos;

import lombok.Value;

//Si le ponemos VALUE es como un "DATA" pero sin SETTERS y con un constructor completo
//Esto es porque sera INMUTABLE, es decir, no lo podremos modificar...
@Value

/*
 * Si le ponemos @Data, sera MUTABLE, y habria que ponerle el @AllArgsConstructor
 * y el @NoArgsConstructor. Porque tiene por defecto solo el constructor SIN PARAMETROS
 * Y al ponerle el @AllArgsConstructor es necesario meterle el @Noargs.. para que
 * tenga ambos.
 */
public class ActorDto {
	
	private int actorId;

	private String firstName;

	private String lastName;

	private int edad;
	

}
