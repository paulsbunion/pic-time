package com.defrainPhoto.pictime.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UpdateEventException.class)
	public ResponseEntity<Object> handleUpdateEventException(UpdateEventException ex, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<String, Object>();
//		body.put("rsp_start_time", ex.getBindingResult().getFieldErrors());
		body.put("rsp_start_time", "Start Time must be before End Time!");
		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
		
	}
	
//	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	@ResponseBody
//	public ValidationErrors handleValidationException(MethodArgumentNotValidException exception) {
//		Set<ValidationErrors> errors = new HashSet<ValidationErrors>();
		Set<String> errors = new HashSet<String>();
		for (FieldError er : ex.getBindingResult().getFieldErrors()) {
			errors.add(er.getField() + ": " + er.getDefaultMessage());
		}
//		for (ObjectError er : ex.getBindingResult().getAllErrors()) {
//			errors.add(er.getObjectName() + ": " + er.getDefaultMessage());
//		}
		return handleExceptionInternal(ex, errors, headers, status, request);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> dataIntegrityViolationExceptionHandler(Exception ex) {
		Set<String> body = new HashSet<String>();
		Throwable throwable = ex.getCause();
		while (throwable != null) {
			if (throwable instanceof SQLIntegrityConstraintViolationException) {
				String errorMessage = throwable.getMessage();
				if (errorMessage.toLowerCase().contains("duplicate")) {
					String[] data = errorMessage.split("for");
					if (data.length > 0) {
						body.add("already-exists:" +  data[0].replace("-", ", "));
					}
				}
			}
			throwable = throwable.getCause();
		}
		return new ResponseEntity<Object>(body, HttpStatus.CONFLICT);
	}
}
