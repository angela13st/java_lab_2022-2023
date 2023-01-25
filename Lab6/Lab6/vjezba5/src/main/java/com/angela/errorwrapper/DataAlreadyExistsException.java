package com.angela.errorwrapper;


public class DataAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3350808191349862376L;

	public DataAlreadyExistsException() {
		super();
	}

	public DataAlreadyExistsException(String message) {
		super(message);
	}

	
	
}
