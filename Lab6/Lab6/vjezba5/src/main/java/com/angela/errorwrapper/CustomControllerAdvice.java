package com.angela.errorwrapper;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class CustomControllerAdvice {

	// method that will get called on constraint validation exception (for example
	// when month in query is 0)
	@ExceptionHandler(value = { ConstraintViolationException.class, UnexpectedTypeException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleValidationFailure(ConstraintViolationException ex) {
		StringBuilder messages = new StringBuilder();

		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			messages.append(violation.getMessage());
		}
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ErrorResponse errorResponse = new ErrorResponse(status, messages.toString());

		return new ResponseEntity<>(errorResponse, status);
	}
	

	@ExceptionHandler(value = { NoDataFoundException.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleNoDataFound(NoDataFoundException ex) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorResponse errorResponse = new ErrorResponse(status, ex.getMessage());

		return new ResponseEntity<>(errorResponse, status);
	}
	
	@ExceptionHandler(value = { DataAlreadyExistsException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleDataAlreadyExists(DataAlreadyExistsException ex) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		ErrorResponse errorResponse = new ErrorResponse(status, ex.getMessage());

		return new ResponseEntity<>(errorResponse, status);
	}

}
