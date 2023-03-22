package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mysql.cj.xdevapi.Result;

import lombok.experimental.var;

class CalculadoraTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSumaPositivoPositivo() {
		var calc = new Calculadora();
		
		var result = calc.suma(2, 2);
		
		assertEquals(4, result);
	}

	@Test
	void testSumaPositivoNegativo() {
		var calc = new Calculadora();
		
		var result = calc.suma(3, -1);
		
		assertEquals(2, result);
	}
	
	@Test
	void testSumaNegativoPositivo() {
		var calc = new Calculadora();
		
		var result = calc.suma(-2, 22);
		
		assertEquals(20, result);
	}
	
	@Test
	void testSumaDecimales() {
		var calc = new Calculadora();
		
		var result = calc.suma(0.1, 0.22);
		
		assertEquals(0.32, result);
	}
	
	@Test
	void testSumaCeros() {
		var calc = new Calculadora();
		
		var result = calc.suma(0, 0);
		
		assertEquals(0, result);
	}
	
	
	@Test
	void testDividir() {
		var calc = new Calculadora();
		
		var result = calc.divide(1, 2);
		
		assertEquals(0.5, result);
	}
	
}
