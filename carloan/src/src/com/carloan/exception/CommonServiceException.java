package com.carloan.exception;

public class CommonServiceException extends Exception {
	private static final long serialVersionUID = 4889895466409683832L;

	public CommonServiceException() {
        super();
	}

	public CommonServiceException(String message) {
		super(message);
	}

}
