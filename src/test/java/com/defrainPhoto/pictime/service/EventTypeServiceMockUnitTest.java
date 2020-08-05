package com.defrainPhoto.pictime.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.ComponentScan;

import com.defrainPhoto.pictime.repository.EventTypeRepository;

@ComponentScan
@RunWith(MockitoJUnitRunner.class)
public class EventTypeServiceMockUnitTest {
	
	@InjectMocks
	EventTypeService eventTypeService;
	
	@Mock
	EventTypeRepository eventTypeRepository;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
