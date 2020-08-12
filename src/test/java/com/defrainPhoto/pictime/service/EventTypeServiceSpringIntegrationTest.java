package com.defrainPhoto.pictime.service;

import static org.junit.Assert.assertEquals;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import com.defrainPhoto.pictime.model.EventType;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class EventTypeServiceSpringIntegrationTest {

	@Autowired
	EventTypeService eventTypeService;
	
	@Test(expected = ConstraintViolationException.class)
	public void testAddEventTypeInvalidBaseCost() {
		EventType eventType = new EventType(1l, "Basic Event", -500);
		
		eventTypeService.addEventType(eventType);
	}
	
	@Test
	public void testAddEventType() {
		EventType eventType = new EventType(1l, "Basic Event", 500);
		
		eventTypeService.addEventType(eventType);
		
		assertEquals(eventType, eventTypeService.getEventType(eventType));
	}
}
