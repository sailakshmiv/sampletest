package com.boomerang.canvas.exception;

public class InvalidValueException extends RuntimeException {

	private static final long serialVersionUID = 5937066287357095615L;

	public InvalidValueException() {
		super();
	}

	public InvalidValueException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidValueException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidValueException(String message) {
		super(message);
	}

	public InvalidValueException(Throwable cause) {
		super(cause);
	}

}
