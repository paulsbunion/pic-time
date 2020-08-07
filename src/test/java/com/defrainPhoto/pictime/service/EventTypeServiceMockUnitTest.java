package com.defrainPhoto.pictime.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl.ConstraintType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.ComponentScan;

import static org.mockito.Mockito.when;

import com.defrainPhoto.pictime.model.EventType;
import com.defrainPhoto.pictime.repository.EventTypeRepository;

@ComponentScan
@RunWith(MockitoJUnitRunner.class)
public class EventTypeServiceMockUnitTest {

	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@InjectMocks
	EventTypeService eventTypeService = new EventTypeServiceImpl();

	@Mock
	EventTypeRepository eventTypeRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllEventTypes() {
		EventType eventType1 = new EventType(1l, "Basic Wedding", 500);
		EventType eventType2 = new EventType(2l, "Super Wedding", 5000);

		when(eventTypeRepository.findByOrderByTypeAsc()).thenReturn(Arrays.asList(eventType1, eventType2));
		List<EventType> eventTypes = eventTypeService.getAllEventTypes();
		assertEquals(2, eventTypes.size());
		assertEquals("Basic Wedding", eventTypes.get(0).getType());
		assertEquals("Super Wedding", eventTypes.get(1).getType());
	}

	@Test
	public void testInvalidEventType() {
		EventType eventType = new EventType(1l, "", -5);
		Set<ConstraintViolation<EventType>> violations = validator.validate(eventType);
		assertEquals(2, violations.size());
		
		eventType = new EventType(2l, "Normal Event", -1);
		violations = validator.validate(eventType);
		assertEquals(1, violations.size());
		assertEquals("Base Event Cost cannot be negative", violations.stream().findFirst().get().getMessage());
		
		eventType = new EventType(2l, "", 1);
		violations = validator.validate(eventType);
		assertEquals(1, violations.size());
		assertEquals("Event Type cannot be Blank", violations.stream().findFirst().get().getMessage());
		
		eventType = new EventType(2l, null, 1);
		violations = validator.validate(eventType);
		violations.stream().forEach(e -> System.out.println(e.getMessage()));
		assertEquals(2, violations.size());
		ConstraintViolation[] violationArray = violations.toArray(new ConstraintViolation[0]);
		assertEquals("Event Type cannot be Blank", violationArray[1].getMessage());
		assertEquals("Event Type cannot be Null", violationArray[0].getMessage());
	}

	@Test
	public void testValidEventType() {
		EventType eventType = new EventType(1l, "Basic Event", 500);
		Set<ConstraintViolation<EventType>> violations = validator.validate(eventType);
		assertEquals(0, violations.size());
		
		when(eventTypeRepository.save(eventType)).thenReturn(eventType);
		
		EventType savedEventType = eventTypeService.addEventType(eventType);
		assertTrue(eventType == savedEventType);
	}
	
	@Test
	public void testAddEventType() {
		EventType eventType = new EventType(1l, "Basic Event", 500);
		
		when(eventTypeRepository.save(eventType)).thenReturn(eventType);
		
		EventType savedEventType = eventTypeService.addEventType(eventType);
		assertTrue(eventType == savedEventType);
	}
	
	@Test
	public void testUpdateEventTypeById() {
		EventType eventType = new EventType(2l, "Basic Event", 100);
		
		when(eventTypeRepository.save(eventType)).thenReturn(eventType);
		EventType updatedEventType = eventTypeService.updateEventTypeById(eventType);
	}

}
