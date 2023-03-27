package com.example.validators;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import jakarta.validation.Validator;

class ValidatorsTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Nested
	class OK{

		@ParameterizedTest(name = "{displayName} -> {0}")
		@CsvSource(value = {
				"00000000T", 
				"00000001r",
				"99999999R",
				"12345678z", 
				"45673254S",
				"72849506L",
				"23574660M",
				"00988812L",
				"09688807B",
				"27089437Z",
				"00040601Y",
				"44998547J"
				})
		@NullSource
		void testValidateDni(String dni) {
			var validator = new Validators();
			
			assertTrue(validator.isNif(dni));
		}
		
		@Test
		void isNotNif() {
			var validator = new Validators();
			
			assertTrue(validator.isNotNif("01234a67Z"));
		}
	}
	
	@Nested
	class KO{
		
		@ParameterizedTest(name = "{displayName} -> {0}")
		@CsvSource(value = {
				"0123" , 
				"0123A" , 
				"01234a67Z" ,
				"012345678-",
				"0123456789",
				"45673254A"
				})
		@EmptySource
		void testInvalidSintax(String dni) {
			var validator = new Validators();
			
			assertFalse(validator.isNif(dni));
		}
		


	}
	
	

}
