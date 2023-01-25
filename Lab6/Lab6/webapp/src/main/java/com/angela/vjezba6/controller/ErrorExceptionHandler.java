package com.angela.vjezba6.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import com.angela.vjezba6.validation.ErrorResponse;

@ControllerAdvice
public class ErrorExceptionHandler {

	@ExceptionHandler(value = { HttpClientErrorException.class })
	@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "")
	public ModelAndView handleResourceNotFoundException(HttpClientErrorException ex) {
		String responseBody = ex.getResponseBodyAsString();
		ErrorResponse errorResponse = new Gson().fromJson(responseBody, ErrorResponse.class);

		ModelAndView mav = new ModelAndView();
		mav.addObject("message", errorResponse.getMessage());
		mav.setViewName("error");
		return mav;
	}
}
