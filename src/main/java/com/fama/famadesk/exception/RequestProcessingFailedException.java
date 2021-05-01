package com.fama.famadesk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public class RequestProcessingFailedException extends Exception 
{
	private String technicalMessage;
	
	RequestProcessingFailedException() {}

	public RequestProcessingFailedException(String message) {
		super(message);
	}

	public RequestProcessingFailedException(String message, String technicalMessage) 
	{
		super(message);
		setTechnicalMessage(technicalMessage);
	}
	
	public RequestProcessingFailedException(Throwable message) {
		super(message);
	}
	
	@JsonIgnore
	public String getTechnicalMessage() 
	{
		//Safety check : In case technical message is NULL, need to return normal exception message
		return (this.technicalMessage!=null) ? this.technicalMessage : this.getMessage();
	}

	public void setTechnicalMessage(String technicalMessage) {
		this.technicalMessage = technicalMessage;
	}
}