package com.defrainPhoto.pictime.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = StartTimeEndTimeValidator.class)
//@Target({ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface StartTimeEndTime {

	String message() default "start time must be before end time. " +
			"Found: 'start time'=${validatedValue.startTime}, " + 
			"'end time'=${validatedValue.endTime}";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
}
