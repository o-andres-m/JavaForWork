package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.example.core.test.Smoke;
import com.example.core.test.SpaceCamelCase;




//import lombok.experimental.var;

@DisplayName("Banco de pruebas - Calculadora")
@TestMethodOrder(MethodOrderer.DisplayName.class)
class CalculadoraTest {
	

	Calculadora calc;
	
	@BeforeEach
	void setUp() throws Exception {
		calc = new Calculadora();
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Nested
	class Suma{
		
		@Nested
		@DisplayNameGeneration(SpaceCamelCase.class)
		class OK{

			@Test
			@DisplayName("Primer prueba suma positivo")
			void testSumaPositivoPositivo() {
				
				var result = calc.suma(BigDecimal.valueOf(2), BigDecimal.valueOf(2));
				
				assertEquals(BigDecimal.valueOf(4), result,"Mensaje");
			}
			

			@Test
			void testSumaPositivoNegativo() {
				
				var result = calc.suma(BigDecimal.valueOf(3), BigDecimal.valueOf(-1));
				
				assertEquals(BigDecimal.valueOf(2), result);
			}
			
			@Test
			void testSumaNegativoPositivo() {
				
				var result = calc.suma(BigDecimal.valueOf(-2), BigDecimal.valueOf(22));
				
				assertEquals(BigDecimal.valueOf(20), result);
			}
			
			@Test
			void testSumaDecimales() {
				
				var result = calc.suma(BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.2));
				
				assertEquals(BigDecimal.valueOf(0.3), result);
			}
			
			@Test
			void testSumaCeros() {
				
				var result = calc.suma(BigDecimal.valueOf(0), BigDecimal.valueOf(0));
				
				assertEquals(BigDecimal.valueOf(0), result);
			}
			
		}
		
		@Nested
		class KO{
			
		}
		
	}
	
	@Nested
	class SumaDouble{
		
		@Nested
		class OK{


			@Test
			void testSumaDecimales() {
				
				var result = calc.sumaDouble(0.1,0.2);
				
				assertEquals(0.3, result);
			}
		
		}
		
		@Nested
		class KO{
			
		}
		
	}

	@Nested
	class Dividir{
		
		@Nested
		class OK{
			
			@Test
			void testDividir() {
				
				var result = calc.divide(1, 2);
				
				assertEquals(0.5, result);
			}
			
			@Test
			void testDividirPorUno() {
				
				var result = calc.divide(1, 1);
				
				assertEquals(1, result);
			}
			
		
		}
		
		@Nested
		class KO{
			
			@Test
			@Smoke //Anotacion propia! Ver CONFIGURACION DE EJECUCION
			void testDividirPorCero() {
						
				assertThrows(ArithmeticException.class, ()-> calc.divide(1,0));
			}
			
			@Test
			void testDividirPorCeroDouble() {
								
				assertThrows(ArithmeticException.class, ()-> calc.divide(1,0.0));
			}
		}
	}
	
	@Nested
	class Resta{
		
		@Nested
		class OK{
			
			@Test
			void testResta() {
				var calc = new Calculadora();
				
				assertEquals(BigDecimal.valueOf(-5.2), calc.restar(BigDecimal.valueOf(0),BigDecimal.valueOf(5.2)));
				
			}
		}
		
		@Nested
		class KO{
			
		}
	}
	

	@Nested
	@DisplayName("Prueba con el GENERADOR DE NOMBRE")
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class RestaConGeneradorDeNombre{
		
		@Nested
		class OK{
			
			@Test
			void test_Resta_Con_Nombre_Generado() {
				var calc = new Calculadora();
				
				assertEquals(BigDecimal.valueOf(-5.2), calc.restar(BigDecimal.valueOf(0),BigDecimal.valueOf(5.2)));
				
			}
		}
		
		@Nested
		class KO{
			
		}
	}
	
	
	// Tests con fallos:
	
	@Nested
	@DisplayName("Fallos forzados")
	class Fails{
		
		@Nested
		class OK{
			
		}
		
		@Nested
		class KO{

			@Test
			@DisplayName("Prueba con ERROR y Mensaje")
			void testSumaPositivoPositivoFail() {
				var calc = new Calculadora();
				
				var result = calc.suma(BigDecimal.valueOf(2), BigDecimal.valueOf(2));
				
				assertEquals(BigDecimal.valueOf(6), result,"Mensaje de error");
			}
			
			@Test
			void testNotImpYet () {
				fail("Test no impoementado todavia.");
			}
		}
		
		
	}
	
	@Nested
	class SumaDoubleParameterizedTests{
		
		@Nested
		class OK{

			@ParameterizedTest(name = "{displayName} -> {0} + {1} = {2}")
			@CsvSource(value = {
					"1,1,2" , 
					"0.1,0.2,0.3" ,
					"1,-1,0",
					"-1,1,0",
					"-1,-2,-3",
					"a,-2,-3",
					})
			void testSumaDecimalesWithParameterizedTest(double op1, double op2, double result) {
								
				assertEquals(result, calc.sumaDouble(op1, op2));
			}
		}
		
		@Nested
		class KO{
			
		}
	}
	
	@Nested
	@Disabled
	class SumaDisabled{
		
		@Nested
		class OK{
			@Test
			void testDisabled() {
								
				assertEquals(2, calc.sumaDouble(1, 1));
			}
		}
	}

	
}
