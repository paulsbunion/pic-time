package com.defrainPhoto.pictime.constraint;

import java.time.LocalTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.defrainPhoto.pictime.model.EventTime;

public class StartTimeEndTimeValidator implements ConstraintValidator<StartTimeEndTime, EventTime> {

	@Override
	public boolean isValid(EventTime eventTime, ConstraintValidatorContext context) {
		if (eventTime == null || eventTime.getStartTime() == null || eventTime.getEndTime() == null) {
			return false;
		}
		return eventTime.getStartTime().isBefore(eventTime.getEndTime());
	}


}
