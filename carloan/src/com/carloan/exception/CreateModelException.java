package com.carloan.exception;

public class CreateModelException extends CommonServiceException {
	private static final long serialVersionUID = -5475594501131780713L;

	public CreateModelException() {
        super();
	}

	public CreateModelException(String message) {
		super(message);
	}
}
