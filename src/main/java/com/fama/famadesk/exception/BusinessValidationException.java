package com.fama.famadesk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BusinessValidationException extends RuntimeException 
{
	BusinessValidationException() {}

	public BusinessValidationException(String message) {
		super(message);
	}

}