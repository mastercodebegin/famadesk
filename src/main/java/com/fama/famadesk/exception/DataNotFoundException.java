package com.fama.famadesk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException 
{
	public DataNotFoundException() {

	}

	public DataNotFoundException(String message) {
		super(message);
	}
}
