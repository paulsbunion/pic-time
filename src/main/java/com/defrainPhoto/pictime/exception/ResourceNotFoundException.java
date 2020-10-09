package com.defrainPhoto.pictime.exception;

public class ResourceNotFoundException extends RuntimeException {

	private  static final long serialVersionUID = 3l;
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
	
	public ResourceNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
