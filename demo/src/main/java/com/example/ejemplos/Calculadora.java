package com.example.ejemplos;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class Calculadora {
	
	public BigDecimal suma(BigDecimal a, BigDecimal b){
		return a.add(b);
	}
	
	public double sumaDouble(double a, double b) {
		var result = BigDecimal.valueOf(a+b);
		return(result.setScale(15,RoundingMode.HALF_DOWN).doubleValue());
	}

	public double divide(double a, double b) {
		
		if (b==0) throw new ArithmeticException("Division por Cero");

		return a/b;
	}

	public BigDecimal restar(BigDecimal a, BigDecimal b) {
		return a.subtract(b);
	}

}
