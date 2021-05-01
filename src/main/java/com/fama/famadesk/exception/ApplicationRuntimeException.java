package com.fama.famadesk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ApplicationRuntimeException extends RuntimeException 
{
	ApplicationRuntimeException() {}

	public ApplicationRuntimeException(String message) {
		super(message);
	}

	public ApplicationRuntimeException(Throwable message) {
		super(message);
	}
}