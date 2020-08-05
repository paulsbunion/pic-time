package com.defrainPhoto.pictime.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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

	when(eventTypeRepository.findAll()).thenReturn(Arrays.asList(eventType1, eventType2));
	
	assertEquals(2, eventTypeService.getAllEventTypes().size());
	}

}
