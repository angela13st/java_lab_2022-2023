package com.angela.errorwrapper;


public class NoDataFoundException extends RuntimeException {


	private static final long serialVersionUID = -3292671094522658664L;

	public NoDataFoundException() {
		super();
	}

	public NoDataFoundException(String message) {
		super(message);
	}
	
	
}
