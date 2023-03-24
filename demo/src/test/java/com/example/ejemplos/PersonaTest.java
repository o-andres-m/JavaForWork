package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;

import com.example.core.test.Smoke;
import com.example.exceptions.InvalidDataException;
import com.example.ioc.PersonaRepository;


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
	
	@Nested
	class PersonaFullNewTests{
		
		@Nested
		class OK{
			
			@ParameterizedTest(name = "{displayName} -> {0} -> {1}")
			@CsvSource(value = {
					"1,Juan" , 
					"2,Hola" ,
					"3,Pedro",
					"4,Ab",
					"5,-Abcdefghijklmnopqrstuvwxyz",
					"6,-*!·%/(ABC",
					})
			void createWith2Attributes(int id, String name) {
				
				var persona = new Persona(id, name);
				
				assertNotNull(persona);
				assertAll( "Attributes",
						()-> assertEquals(id, persona.getId()),
						()-> assertEquals(name, persona.getNombre()),
						()-> assertEquals(Optional.empty(), persona.getApellidos()));
			}
			
			@ParameterizedTest(name = "{displayName} -> {0} - {1} {2}")
			@CsvSource(value = {
					"1,Juan, Gomez" , 
					"2,Hola, Segundo" ,
					"3,Pedro, Perez",
					"4,Ab, C",
					"5,-Abcdefghijklmnopqrstuvwxyz, ApellidoLargoProbandoQueSeaExtenso1234567890",
					"6,-*!·%/(ABC, !!&//(/(",
					})
			void createWith3Attributes(int id, String name, String surname) {
				
				var persona = new Persona(id, name, surname);
				
				assertNotNull(persona);
				assertAll( "Attributes",
						()-> assertEquals(id, persona.getId()),
						()-> assertEquals(name, persona.getNombre()),
						()-> assertEquals(surname, persona.getApellidos().get()));
			}
			
		}

		
		@Nested
		class KO{
			
			@RepeatedTest(value = 4, name = "{displayName} {currentRepetition}/{totalRepetitions}")
			void createWithFailName(int id, String name) {
				
				var persona = new Persona(id, name);
				
				assertNotNull(persona);
				assertAll( "Attributes",
						()-> assertEquals(id, persona.getId()),
						()-> assertEquals(name, persona.getNombre()));
			}
			
		}
	}
	
	@Nested
	class PersonaRepositoryTestGroup{
		
		@Mock
		PersonaRepository dao;
		
		@BeforeEach
		void setUp() throws Exception{
			dao = mock(PersonaRepository.class);
		}
		
		@Test
		void testLoad() {
						
			when(dao.load()).thenReturn(
								Persona.builder()
								.id(1)
								.nombre("Pepito")
								.apellidos("Gomez")
								.build());
				
			var p = dao.load();
			
			assertTrue(p instanceof Persona, "No es una instancia de Persona");
			assertAll("Validar Propiedades",
					()->assertEquals(1, p.getId()),
					()->assertEquals("Pepito", p.getNombre()),
					()->assertEquals("Gomez", p.getApellidos().get())
					);
		}
		
		@Test
		void testSave() throws InvalidDataException {
			
			doThrow(new IllegalArgumentException()).when(dao).save(null);
						
			assertThrows(IllegalArgumentException.class, ()-> dao.save(null));
		}
		
		
	}

}
