package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import com.example.core.test.Smoke;

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
	
	@RepeatedTest(value = 4, name = "{displayName} {currentRepetition}/{totalRepetitions}")
	void testCreatePerson4Times(RepetitionInfo repetitionInfo) {
		
		var p = Persona.builder()
				.id(repetitionInfo.getCurrentRepetition())
				.nombre("Pepito Nº "+repetitionInfo.getCurrentRepetition())
				.apellidos("Grillo")
				.build();
		
		assertTrue(p instanceof Persona, "No es una instancia de clase Persona");
		
		assertAll("Validar Propiedades",
			()-> assertEquals(repetitionInfo.getCurrentRepetition(), p.getId(),"Fallo getId"),
			()-> assertEquals("Pepito Nº "+repetitionInfo.getCurrentRepetition(), p.getNombre(),"Fallo getNombre"),
			()-> assertEquals("Grillo", p.getApellidos(),"Fallo getApellido")
		);

	}
	
	@RepeatedTest(value = 4, name = "{displayName} {currentRepetition}/{totalRepetitions}")
	void testCreatePerson4TimesWithOneFailName(RepetitionInfo repetitionInfo) {
		
		var p = Persona.builder()
				.id(repetitionInfo.getCurrentRepetition())
				.nombre("Pepito Nº "+(repetitionInfo.getCurrentRepetition() % 3 == 0 ? "" : repetitionInfo.getCurrentRepetition()))
				.apellidos("Grillo")
				.build();
		
		assertTrue(p instanceof Persona, "No es una instancia de clase Persona");
		
		assertAll("Validar Propiedades",
			()-> assertEquals(repetitionInfo.getCurrentRepetition(), p.getId(),"Fallo getId"),
			()-> assertEquals("Pepito Nº "+repetitionInfo.getCurrentRepetition(), p.getNombre(),"Fallo getNombre"),
			()-> assertEquals("Grillo", p.getApellidos(),"Fallo getApellido")
		);
	}
	
	@Nested
	class TimeOutTest{
		
		@Test
		@Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
		void tryTimeOut(){
			try {
				Thread.sleep(120);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
