package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.example.ioc.StringRepository;
import com.example.ioc.StringRepositoryMockImpl;

@SpringBootTest
//@Disabled
class DemoApplicationTests {

	@Autowired
	StringRepository dao;
	
	@Test
	void contextLoads() {
		// Mock, porque el mock esta como @primery
		assertEquals("Soy el StringRepository Mockeado", dao.load());
	}
	
	@Value("${mi.valor:(Sin valor)}")
	private String config;
	@Test
	void valueLoad() {
		assertEquals("Valor Por Defecto", config);
	}
	
	
	//////////////////////////////

	
	public static class IocTestConfing{
		
		@Bean
		StringRepository getServicio() {
			return new StringRepositoryMockImpl();
			
		}
	}
		
	@Nested
	@ContextConfiguration(classes = IocTestConfing.class)
	//Hacemos un context para poder "usarla" en varios lados
	@ActiveProfiles("test")
	// Activamos un profile, ojo! toma el valor del application-test.propieties
	class IocTest{
		
		@Autowired
		StringRepository dao;
		
		
		@Test
		void contextLoads() {
			assertEquals("Soy el StringRepository Mockeado", dao.load());
		}
		
		@Value("${mi.valor:(Sin valor)}")
		private String config;
		@Test
		void valueLoad() {
			assertEquals("Valor Para Test", config);
		}
	}
	
	
	//////////////////////////////

	
	@Nested
	class IocUnicoTest{
		
		@TestConfiguration //Se usaria aqui esta configuracion
		public static class IocTestConfing{
			
			@Bean
			StringRepository getServicio() {
				return new StringRepositoryMockImpl();
				
			}
		}
		
		@Autowired
		StringRepository dao;
		
		
		@Test
		void contextLoads() {
			assertEquals("Soy el StringRepository Mockeado", dao.load());
		}
		
	}
	
	
	

}
