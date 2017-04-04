package com.boomerang.canvas.exception;

public class JobFailedException extends Exception {

	private static final long serialVersionUID = -2240406746308884235L;

	public JobFailedException() {
		super();
	}

	public JobFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public JobFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public JobFailedException(String message) {
		super(message);
	}

	public JobFailedException(Throwable cause) {
		super(cause);
	}

}
