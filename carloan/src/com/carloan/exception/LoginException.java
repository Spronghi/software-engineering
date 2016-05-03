package com.carloan.exception;


public class LoginException extends CommonServiceException {
	private static final long serialVersionUID = 1L;

	public LoginException() {
        super();
	}

	public LoginException(String message) {
		super(message);
	}
}
