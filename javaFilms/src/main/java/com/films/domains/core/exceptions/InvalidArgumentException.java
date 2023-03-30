package com.films.domains.core.exceptions;

public class InvalidArgumentException extends RuntimeException {

	public InvalidArgumentException() {
		super();
	}

	public InvalidArgumentException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidArgumentException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidArgumentException(Throwable cause) {
		super(cause);
	}

}
