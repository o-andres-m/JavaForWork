package com.example.validators;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
				"00000001R",
				"99999999R",
				"12345678Z", 
				"45673254S",
				"72849506L",
				"23574660M",
				"00988812L",
				"09688807B",
				"27089437Z",
				"00040601Y",
				"44998547J"
				})
		void testValidateDni(String dni) {
			var validator = new Validators();
			
			assertTrue(validator.validar(dni));
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
				})
		void testInvalidSintax(String dni) {
			var validator = new Validators();
			
			assertFalse(validator.validar(dni));
		}
	}
	
	

}
