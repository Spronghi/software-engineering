package com.carloan.exception;

public class UpdateModelException extends CommonServiceException {
	private static final long serialVersionUID = 1L;
	public UpdateModelException() {
        super();
    }

	public UpdateModelException(String message) {
		super(message);
	}
}
