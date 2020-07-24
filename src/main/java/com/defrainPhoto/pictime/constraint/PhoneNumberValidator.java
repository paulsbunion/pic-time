package com.defrainPhoto.pictime.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
	
	// match phone number with possible parentheses or dashes/ spaces
	private final String MATCHER = "^(\\+?\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$";

	@Override
	public void initialize(PhoneNumber constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
		return phoneNumber != null && 
				phoneNumber.matches(MATCHER) &&
				phoneNumber.length() > 8; 
	}

}
