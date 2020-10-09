package com.defrainPhoto.pictime.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ZipcodeValidator implements ConstraintValidator<Zipcode, String> {

	private final String ZIP_CODE_MATCHER = "\\d{5}(-\\d{4})?";
	
	@Override
	public void initialize(Zipcode constraintAnnotation) {};
	
	@Override
	public boolean isValid(String zipcode, ConstraintValidatorContext context) {
		return zipcode != null && zipcode.matches(ZIP_CODE_MATCHER);
	}
}
