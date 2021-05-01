package com.fama.famadesk.exception.advice;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fama.famadesk.exception.ApplicationException;
import com.fama.famadesk.exception.ApplicationRuntimeException;
import com.fama.famadesk.exception.BadRequestException;
import com.fama.famadesk.exception.BusinessValidationException;
import com.fama.famadesk.exception.DataNotFoundException;
import com.fama.famadesk.exception.RequestProcessingFailedException;
//import com.fama.famadesk.exception.ApplicationRuntimeException;
//import com.fama.famadesk.exception.BadRequestException;
//import com.fama.famadesk.exception.BusinessValidationException;
//import com.fama.famadesk.exception.DataNotFoundException;
//import com.fama.famadesk.exception.RequestProcessingFailedException;
//import com.fama.famadesk.utils.ApplicationUtil;
//import com.fama.famadesk.utils.ApplicationUtil;

import lombok.extern.slf4j.Slf4j;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(annotations = RestController.class)
@Slf4j
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler 
{
	private static final String MESSAGE_SOURCE = "famacash";
	private static final String GENERIC_FAILURE_MSG = "Something went wrong";
	
	@ExceptionHandler(BadRequestException.class)
	public final ResponseEntity<Object> handleBadRequestException(BadRequestException ex, HttpServletRequest request) 
	{
		log.error("Bad request exception handled. Message : {}", ex.getMessage());

		ExceptionDataReturnDetail returnDetails = ExceptionDataReturnDetail.builder()
				.code(HttpStatus.BAD_REQUEST.value()).message(ex.getMessage()).errorDetails(ex.getMessage())
				.messageSource(MESSAGE_SOURCE).build();
		return new ResponseEntity<Object>(returnDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public final ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex, HttpServletRequest request) 
	{
		log.error("Max Upload Size Exceeded exception handled. Message : {}", ex.getMessage());
		ExceptionDataReturnDetail returnDetails = ExceptionDataReturnDetail.builder()
				.code(HttpStatus.BAD_REQUEST.value()).message(ex.getMessage()).errorDetails(ex.getMessage())
				.messageSource(MESSAGE_SOURCE).build();
		return new ResponseEntity<Object>(returnDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ApplicationException.class)
	public final ResponseEntity<Object> handleApplicationException(ApplicationException ex, HttpServletRequest request) 
	{
		log.error("Application exception handled. Message : {}", ex.getMessage());

		ExceptionDataReturnDetail returnDetails = ExceptionDataReturnDetail.builder()
				.code(HttpStatus.BAD_REQUEST.value()).message(ex.getMessage()).errorDetails(ex.getMessage())
				.messageSource(MESSAGE_SOURCE).build();

		return new ResponseEntity<Object>(returnDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ApplicationRuntimeException.class)
	public final ResponseEntity<Object> handleApplicationRuntimeException(ApplicationRuntimeException ex,
			HttpServletRequest request) 
	{
		log.error("Application Runtime exception handled. Message : {}", ex.getMessage());
		
		ExceptionDataReturnDetail returnDetails = ExceptionDataReturnDetail.builder()
				.code(HttpStatus.BAD_REQUEST.value()).message(ex.getMessage()).errorDetails(ex.getMessage())
				.messageSource(MESSAGE_SOURCE).build();

		return new ResponseEntity<Object>(returnDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NonUniqueResultException.class)
	public final ResponseEntity<Object> handleNonUniqueResultException(NonUniqueResultException ex,
			HttpServletRequest request) 
	{
		log.error("Non Unique Result exception handled. Message : {}", ex.getMessage());

		ExceptionDataReturnDetail returnDetails = ExceptionDataReturnDetail.builder()
				.code(HttpStatus.BAD_REQUEST.value()).message(GENERIC_FAILURE_MSG)
				.errorDetails(ex.getMessage()).messageSource(MESSAGE_SOURCE).build();
		
		return new ResponseEntity<Object>(returnDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BusinessValidationException.class)
	public final ResponseEntity<Object> handleBusinessValidationException(BusinessValidationException ex,
			HttpServletRequest request) 
	{
		log.error("Business Validation exception handled. Message : {}", ex.getMessage());
		
		ExceptionDataReturnDetail returnDetails = ExceptionDataReturnDetail.builder()
				.code(HttpStatus.BAD_REQUEST.value()).message(ex.getMessage()).errorDetails(ex.getMessage())
				.messageSource(MESSAGE_SOURCE).build();
		return new ResponseEntity<Object>(returnDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataNotFoundException.class)
	public final ResponseEntity<Object> handleDataNotFoundException(DataNotFoundException ex, HttpServletRequest request) 
	{
		log.error("Data Not Found exception handled. Message : {}", ex.getMessage());
		
		ExceptionDataReturnDetail returnDetails = ExceptionDataReturnDetail.builder().code(HttpStatus.NOT_FOUND.value())
				.message(ex.getMessage()).errorDetails(ex.getMessage()).messageSource(MESSAGE_SOURCE).build();

		return new ResponseEntity<Object>(returnDetails, HttpStatus.NOT_FOUND);
	}

	//This exception will be raised when processing of request is failed
	@ExceptionHandler(RequestProcessingFailedException.class)
	public final ResponseEntity<Object> handleRequestProcessingFailedException(RequestProcessingFailedException ex,
			HttpServletRequest request)
	{
		log.error("Request Processing Failed exception handled. Message : {}", ex.getMessage());
		
		ExceptionDataReturnDetail returnDetails = ExceptionDataReturnDetail.builder().code(HttpStatus.PRECONDITION_FAILED.value())
							.message(ex.getMessage()).errorDetails(ex.getMessage()).messageSource(MESSAGE_SOURCE).build();
		return new ResponseEntity<Object>(returnDetails, HttpStatus.PRECONDITION_FAILED);
	}
	
	//If API's JWT Token with request has expired, unauthorized exception needs to be raised
	@ExceptionHandler(TokenExpiredException.class)
	public final ResponseEntity<Object> handleTokenExpiredException(TokenExpiredException ex,
			HttpServletRequest request)
	{
		log.error("Token Expired exception handled. Message : {}", ex.getMessage());
		
		ExceptionDataReturnDetail returnDetails = ExceptionDataReturnDetail.builder().code(HttpStatus.UNAUTHORIZED.value())
							.message("Unauthorized access").errorDetails(ex.getMessage()).messageSource(MESSAGE_SOURCE).build();
		return new ResponseEntity<Object>(returnDetails, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(Throwable.class)
	public final ResponseEntity<Object> handleThrowable(Throwable ex,
			HttpServletRequest request)
	{
//		log.error("Throwable handled. Error-trace : {}", ApplicationUtil.getStackTrace(ex));
		
		ExceptionDataReturnDetail returnDetails = ExceptionDataReturnDetail.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
							.message(GENERIC_FAILURE_MSG).errorDetails(ex.getMessage()).messageSource(MESSAGE_SOURCE).build();
		return new ResponseEntity<Object>(returnDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) 
	{
		Map<String, String> errorResponseMap = new HashMap<String, String>();
		for (ObjectError errors : ex.getBindingResult().getAllErrors()) 
		{
			DefaultMessageSourceResolvable messaageResolver = (DefaultMessageSourceResolvable) errors.getArguments()[0];
			String fieldName = messaageResolver.getCodes()[0];
			fieldName = fieldName.substring(fieldName.indexOf(".") + 1, fieldName.length());
			errorResponseMap.put(fieldName, errors.getDefaultMessage());
		}
		
		ExceptionDataReturnDetail returnDetails = new ExceptionDataReturnDetail(status.value(),
				errorResponseMap.toString(), MESSAGE_SOURCE, errorResponseMap, errorResponseMap.toString());
		return new ResponseEntity<>(returnDetails, status);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) 
	{
		ExceptionDataReturnDetail returnDetail = new ExceptionDataReturnDetail(status.value(), null, MESSAGE_SOURCE, null,
				ex.getMessage());
		return new ResponseEntity<>(returnDetail, status);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) 
	{
		ExceptionDataReturnDetail returnDetail = new ExceptionDataReturnDetail(status.value(), null, MESSAGE_SOURCE, null,
				ex.getMessage());
		return new ResponseEntity<>(returnDetail, status);
	}
}