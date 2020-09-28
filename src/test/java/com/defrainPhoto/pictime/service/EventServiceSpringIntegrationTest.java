package com.defrainPhoto.pictime.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.defrainPhoto.pictime.model.Event;
import com.defrainPhoto.pictime.model.EventTime;
import com.defrainPhoto.pictime.model.EventType;
import com.defrainPhoto.pictime.model.Timeslot;
import com.defrainPhoto.pictime.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@Transactional
public class EventServiceSpringIntegrationTest {
	
	private static EventType eventType;
	private static Event event;
	private static User photographerOne;
	private static User photographerTwo;
	private static Timeslot timeslotOne;
	private static Timeslot timeslotTwo;

	@Autowired
	EventTypeService eventTypeService;
	
	@Autowired
	EventService eventService;
	
	@BeforeClass
	public static void doOnce() {
		eventType = new EventType(1l, "Wedding", 1250);
		photographerOne = new User("Ben", "Walters", "bw@email.com", "pwd1");
		photographerTwo = new User("Jen", "Davis", "jd@email.com", "pwd2");
		event = new Event(1l, "The first Event", LocalDate.now(), eventType);
		timeslotOne = new Timeslot(1l, new EventTime(1230, 15), event, "guests arrive", "", null, new HashSet<User>(Arrays.asList(photographerOne)), null, false);
		timeslotTwo = new Timeslot(2l, new EventTime(1230, 15), event, "guests leave", "", null, new HashSet<User>(Arrays.asList(photographerTwo)), null, false);
	}
	
	@Test
	public void testChangePhotographer() {
		eventTypeService.addEventType(eventType);
		
		eventService.addEvent(event);
		eventService.addPhotographer(event.getId(), photographerOne);
		eventService.addPhotographer(event.getId(), photographerTwo);
		eventService.addTimeslot(event.getId(), timeslotOne);
		eventService.addTimeslot(event.getId(), timeslotTwo);
		
		Event foundEvent = eventService.findById(1l);
		assertEquals(event, foundEvent);
		assertEquals(2, foundEvent.getPhotographers().size());
		List<Timeslot> timeslots = new ArrayList<Timeslot>(foundEvent.getTimeslots());
		assertEquals(timeslots.get(0).getPhotographers().stream().findFirst().get(), photographerOne);
		assertEquals(timeslots.get(1).getPhotographers().stream().findFirst().get(), photographerTwo);
		
		eventService.switchPhotographer(foundEvent.getId(), photographerOne, photographerTwo);
		foundEvent = eventService.findById(1l);
		assertEquals(event, foundEvent);
		assertEquals(1, foundEvent.getPhotographers().size());
		timeslots = new ArrayList<Timeslot>(foundEvent.getTimeslots());
		assertEquals(timeslots.get(0).getPhotographers().stream().findFirst().get(), photographerTwo);
		assertEquals(timeslots.get(1).getPhotographers().stream().findFirst().get(), photographerTwo);
		assertEquals(1, timeslots.get(0).getPhotographers().size());
		assertEquals(1, timeslots.get(1).getPhotographers().size());
	}
}
