package com.fama.famadesk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public class ThirdPartyFailureException extends Exception 
{
	ThirdPartyFailureException() {}

	public ThirdPartyFailureException(String message) {
		super(message);
	}

	public ThirdPartyFailureException(ThirdPartyFailureException e) {
		super(e);
	}
}