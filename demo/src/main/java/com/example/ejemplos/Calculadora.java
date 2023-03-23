package com.example.ejemplos;

import java.math.BigDecimal;

public class Calculadora {
	
	public BigDecimal suma(BigDecimal a, BigDecimal b){
		return a.add(b);
	}

	public BigDecimal divide(BigDecimal a, BigDecimal b) {
		if (b.equals(BigDecimal.valueOf(0)))
			throw new ArithmeticException();
		return a.divide(b);
	}

	public BigDecimal restar(BigDecimal a, BigDecimal b) {
		// TODO Auto-generated method stub
		return a.subtract(b);
	}

}
