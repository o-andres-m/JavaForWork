package com.example;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.servlet.http.HttpServletRequest;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

@ControllerAdvice
public class ApiExceptionHandler {
	public static class ErrorMessage implements Serializable {
		private static final long serialVersionUID = 1L;
		private String error, message;

		public ErrorMessage(String error, String message) {
			this.error = error;
			this.message = message;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	//El responseStatus va a ser el STATUS DE RESPUESTA DEL SERVIDOR (404)
    @ExceptionHandler({NotFoundException.class})
	// El exceptionHandler, es LA EXCEPTION QUE "AGARRA" y hace pasar por este metodo
    @ResponseBody
    //Va a responder un objeto tiipo JSON. 
    public ErrorMessage notFoundRequest(HttpServletRequest request, Exception exception) {
        return new ErrorMessage(exception.getMessage(), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ BadRequestException.class, DuplicateKeyException.class })
    @ResponseBody
    public ErrorMessage badRequest(Exception exception,HttpServletRequest request) {
        return new ErrorMessage(exception.getMessage(), request.getRequestURI());
    }
    
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ InvalidDataException.class })
    @ResponseBody
    public ErrorMessage invalidData(Exception exception) {
        return new ErrorMessage("Invalid Data.", exception.getMessage());
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    @ResponseBody
    public ErrorMessage methodArgument(Exception exception) {
        return new ErrorMessage("Method not allowed", "Ceck method and url");
    }
}


