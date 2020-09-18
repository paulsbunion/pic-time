package com.defrainPhoto.pictime.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.defrainPhoto.pictime.model.Event;
import com.defrainPhoto.pictime.model.EventType;
import com.defrainPhoto.pictime.repository.EventRepository;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceMockUnitTest {

	@InjectMocks
	EventService eventService = new EventServiceImpl();
	
	@Mock
	EventRepository eventRepository;
	
	@Test
	public void getAllEvents() {
		
		EventType eventType = new EventType(1l, "Mini Session", 50);
		Event eventOne = new Event(1l, "Bobs Event", LocalDate.of(2020, 1, 15), eventType);
		Event eventTwo = new Event(1l, "Bobs Second Event", LocalDate.of(2020, 1, 15), eventType);
		Event eventThree = new Event(1l, "Bobs Third Event", LocalDate.of(2020, 1, 15), eventType);
		
		List<Event> allEvents = Arrays.asList(eventOne, eventTwo, eventThree);
		
		when(eventRepository.findAll()).thenReturn(allEvents);
		
		List<Event> events = eventService.findAll();
		
		assertEquals(3, events.size());
	}
}
