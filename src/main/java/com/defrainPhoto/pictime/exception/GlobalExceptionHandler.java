package com.defrainPhoto.pictime.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UpdateEventException.class)
	public ResponseEntity<Object> handleUpdateEventException(UpdateEventException ex, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<String, Object>();
//		body.put("rsp_start_time", ex.getBindingResult().getFieldErrors());
		body.put("rsp_start_time", "End Time cannot be before Start Time!");
		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
		
	}
}
