package com.carloan.exception;


public class ModelNotFoundException extends CommonServiceException {
	private static final long serialVersionUID = 1L;
	public ModelNotFoundException() {
        super();
	}

	public ModelNotFoundException(String message) {
		super(message);
	}
}
