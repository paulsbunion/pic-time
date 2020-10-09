package com.defrainPhoto.pictime.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CityValidator implements ConstraintValidator<City, String> {

	private final String CITY_MATCHER = "([A-Za-z]+[ ]?)+";
	
	@Override
	public void initialize(City constraintAnnotation) {};

	@Override
	public boolean isValid(String city, ConstraintValidatorContext context) {

		return city != null && city.matches(CITY_MATCHER); 
	}

}
