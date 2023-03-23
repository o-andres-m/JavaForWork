package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



//import lombok.experimental.var;

@DisplayName("Banco de pruebas - Calculadora")
class CalculadoraTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Primer prueba suma positivo")
	void testSumaPositivoPositivo() {
		var calc = new Calculadora();
		
		var result = calc.suma(BigDecimal.valueOf(2), BigDecimal.valueOf(2));
		
		assertEquals(BigDecimal.valueOf(4), result);
	}

	@Test
	void testSumaPositivoNegativo() {
		var calc = new Calculadora();
		
		var result = calc.suma(BigDecimal.valueOf(3), BigDecimal.valueOf(-1));
		
		assertEquals(BigDecimal.valueOf(2), result);
	}
	
	@Test
	void testSumaNegativoPositivo() {
		var calc = new Calculadora();
		
		var result = calc.suma(BigDecimal.valueOf(-2), BigDecimal.valueOf(22));
		
		assertEquals(BigDecimal.valueOf(20), result);
	}
	
	@Test
	void testSumaDecimales() {
		var calc = new Calculadora();
		
		var result = calc.suma(BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.2));
		
		assertEquals(BigDecimal.valueOf(0.3), result);
	}
	
	@Test
	void testSumaCeros() {
		var calc = new Calculadora();
		
		var result = calc.suma(BigDecimal.valueOf(0), BigDecimal.valueOf(0));
		
		assertEquals(BigDecimal.valueOf(0), result);
	}
	
	
	@Test
	void testDividir() {
		var calc = new Calculadora();
		
		var result = calc.divide(BigDecimal.valueOf(1), BigDecimal.valueOf(2));
		
		assertEquals(BigDecimal.valueOf(0.5), result);
	}
	
	@Test
	void testDividirPorCero() {
		var calc = new Calculadora();
				
		assertThrows(ArithmeticException.class, ()-> calc.divide(BigDecimal.valueOf(1), BigDecimal.valueOf(0)));
	}
	
	@Test
	void testResta() {
		var calc = new Calculadora();
		
		assertEquals(BigDecimal.valueOf(-5.2), calc.restar(BigDecimal.valueOf(0),BigDecimal.valueOf(5.2)));
		
		
	}
	
}
