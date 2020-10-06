package com.defrainPhoto.pictime.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.defrainPhoto.pictime.model.Event;
import com.defrainPhoto.pictime.model.EventTime;
import com.defrainPhoto.pictime.model.EventType;
import com.defrainPhoto.pictime.model.Timeslot;
import com.defrainPhoto.pictime.model.User;
import com.defrainPhoto.pictime.repository.EventRepository;
import com.defrainPhoto.pictime.repository.TimeslotRepository;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceMockUnitTest {
	
	private static EventType eventType;
	private static Event eventOne;
	private static Event eventTwo;
	private static Event eventThree;
	private static User photographerOne;
	private static User photographerTwo;
	private static Timeslot timeslotOne;
	private static Timeslot timeslotOnePt2;
	private static Timeslot timeslotTwo;

	@InjectMocks
	EventService eventService = new EventServiceImpl();
	
	@Mock
	TimeslotService timeslotService = new TimeslotServiceImpl();
	
	@Mock
	UserService userService;
	
	@Mock
	EventRepository eventRepository;
	
	@Mock
	TimeslotRepository timeslotRepository;
	
	@BeforeClass
	public static void createData() {
		eventType = new EventType(1l, "Mini Session", 50);
		eventOne = new Event(1l, "Bobs Event", LocalDate.of(2020, 1, 15), eventType);
		eventTwo = new Event(1l, "Bobs Second Event", LocalDate.of(2020, 1, 15), eventType);
		eventThree = new Event(1l, "Bobs Third Event", LocalDate.of(2020, 1, 15), eventType);
		photographerOne = new User("Ben", "Walters", "bw@email.com", "pwd");
		photographerTwo = new User("Sally", "Smith", "ss@email.com", "pwd2");
		photographerOne.setId(1l);
		photographerTwo.setId(2l);
		
		timeslotOne = new Timeslot(1l, new EventTime(1230, 15), eventOne, "guests arrive", "client wants pictures of their cars", 
				null, new HashSet<User>(Arrays.asList(photographerOne)), null, false);
		timeslotOnePt2 = new Timeslot(1l, new EventTime(1230, 15), eventOne, "guests arrive", "client wants pictures of their cars", 
				null, new HashSet<User>(Arrays.asList(photographerTwo)), null, false);
		timeslotTwo = new Timeslot(2l, new EventTime(1230, 15), eventOne, "guests leave", "", 
				null,  new HashSet<User>(Arrays.asList(photographerTwo)), null, false);
		eventOne.setTimeslots(Arrays.asList(timeslotOne, timeslotTwo));
	}
	
	@Test
	public void testGetAllEvents() {
		
		List<Event> allEvents = Arrays.asList(eventOne, eventTwo, eventThree);
		
		when(eventRepository.findAll()).thenReturn(allEvents);
		
		List<Event> events = eventService.findAll();
		
		assertEquals(3, events.size());
	}
	
	@Test
	public void testChangePhotographer() {
		
		eventOne.addPhotographer(photographerOne);
		
		when(eventRepository.findById(1l)).thenReturn(Optional.of(eventOne)).thenReturn(Optional.of(eventOne)).thenReturn(Optional.of(eventOne));
		
		Event foundEvent = eventService.findById(1l);
		assertEquals(photographerOne, foundEvent.getPhotographers().stream().findFirst().get());
		assertEquals(photographerOne, foundEvent.getTimeslots().get(0).getPhotographers().parallelStream().findFirst().get());
		assertEquals(photographerTwo, foundEvent.getTimeslots().get(1).getPhotographers().parallelStream().findFirst().get());
		
//		when(userService.findById(1l)).thenReturn(photographerOne);
		when(userService.findById(2l)).thenReturn(photographerTwo);
		
		doAnswer(invocation -> {
			timeslotOne = timeslotOnePt2;
			Timeslot found = eventOne.getTimeslots().get(0);
			found.removePhotographer(photographerOne);
			found.addPhotographer(photographerTwo);
			eventOne.getTimeslots().set(0, found);
			return null;
		}).when(timeslotService).changePhotographer(timeslotOne, photographerOne, photographerTwo);
		eventService.switchPhotographer(eventOne.getId(), photographerOne.getId(), photographerTwo.getId());
		
		foundEvent = eventService.findById(1l);
		assertEquals(1, foundEvent.getPhotographers().size());
		assertEquals(photographerTwo, foundEvent.getPhotographers().stream().findFirst().get());
		assertEquals(photographerTwo, foundEvent.getTimeslots().get(0).getPhotographers().parallelStream().findFirst().get());
		assertEquals(photographerTwo, foundEvent.getTimeslots().get(1).getPhotographers().parallelStream().findFirst().get());
	}
}
