package com.defrainPhoto.pictime.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StreetValidator implements ConstraintValidator<Street, String>{
	
	private final String STREET_MATCHER = "\\d+[ ][A-Za-z0-9.-[ ]]+";
	
	@Override
	public void initialize(Street constraintAnnotation) {};

	@Override
	public boolean isValid(String street, ConstraintValidatorContext context) {

		return street != null && street.matches(STREET_MATCHER); 
	}

}
