package com.fama.famadesk.exception.advice;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDataReturnDetail {

	@JsonIgnore
	private int code;
	
	private String message;
	
	@JsonIgnore
	private String messageSource;
	
	private Map<String, String> fieldMap;
	
	@JsonIgnore
	private String errorDetails;

}
