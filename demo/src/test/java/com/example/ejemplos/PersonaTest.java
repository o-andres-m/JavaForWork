package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PersonaTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	
	
	@Test
	void testCreate() {
		
		var p = Persona.builder()
				.id(1)
				.nombre("Pepito")
				.apellidos("Grillo")
				.build();
		

		// assertNotNull(p);
		assertTrue(p instanceof Persona, "No es una instancia de clase Persona");
		
		assertAll("Validar Propiedades",
			()-> assertEquals(1, p.getId(),"Fallo getId"),
			()-> assertEquals("Pepito", p.getNombre(),"Fallo getNombre"),
			()-> assertEquals("Grillo", p.getApellidos(),"Fallo getApellido")
		);

	}

}
