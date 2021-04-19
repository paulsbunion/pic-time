package com.defrainPhoto.pictime.exception;

import org.springframework.validation.BindingResult;

public class UpdateEventException extends RuntimeException {

	private static final long serialVersionUID = 8590922716641768309L;
	private BindingResult result;

	public UpdateEventException(BindingResult result) {
//		super();
//		this.result = result;
	}
	
	public BindingResult getBindingResult() {
		return result;
	}
}
