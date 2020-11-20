package com.autobuds.PMDReportAggregator.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	 @ExceptionHandler(InvalidCredentialsException.class)
	 @ResponseStatus(HttpStatus.BAD_REQUEST)
	  public Map handleNotFoundException(InvalidCredentialsException ex) {
	   Map<String,String> response = new HashMap<>();
	   response.put("message",ex.getMessage() );
	    return response;
	  }

}
