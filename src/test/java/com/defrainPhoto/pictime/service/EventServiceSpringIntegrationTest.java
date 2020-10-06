package com.defrainPhoto.pictime.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

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
import com.defrainPhoto.pictime.repository.UserRegistrationDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@Transactional
public class EventServiceSpringIntegrationTest {

	private static EventType eventType;
	private static Event event1;
	private static Event event2;
	private static User photographer1;
	private static User photographer2;
	private static Timeslot timeslot1;
	private static Timeslot timeslot2;

	@Autowired
	EventTypeService eventTypeService;

	@Autowired
	EventService eventService;

	@Autowired
	UserService userService;

	@Test
//	@Transactional
	public void testChangePhotographer() {
		generateData1();

		Event foundEvent = eventService.findById(1l);
		assertEquals(event1, foundEvent);
		assertEquals(2, foundEvent.getPhotographers().size());
		List<Timeslot> timeslots = new ArrayList<Timeslot>(foundEvent.getTimeslots());
		assertEquals(timeslots.get(0).getPhotographers().stream().findFirst().get(), photographer1);
		assertEquals(timeslots.get(1).getPhotographers().stream().findFirst().get(), photographer2);

		eventService.switchPhotographer(foundEvent.getId(), photographer1.getId(), photographer2.getId());
		foundEvent = eventService.findById(1l);
		assertEquals(event1, foundEvent);
		assertEquals(1, foundEvent.getPhotographers().size());
		timeslots = new ArrayList<Timeslot>(foundEvent.getTimeslots());
		assertEquals(timeslots.get(0).getPhotographers().stream().findFirst().get(), photographer2);
		assertEquals(timeslots.get(1).getPhotographers().stream().findFirst().get(), photographer2);
		assertEquals(1, timeslots.get(0).getPhotographers().size());
		assertEquals(1, timeslots.get(1).getPhotographers().size());
	}

	@Test
	public void testRemovePhotographer() {
		generateData2();

		List<Event> allEvents = eventService.findAll();
		Event foundEvent = eventService.findById(event2.getId());
		assertEquals(event2, foundEvent);
		assertEquals(2, foundEvent.getPhotographers().size());
		List<Timeslot> timeslots = new ArrayList<Timeslot>(foundEvent.getTimeslots());
		assertEquals(timeslots.get(0).getPhotographers().stream().findFirst().get(), photographer1);
		assertEquals(timeslots.get(1).getPhotographers().stream().findFirst().get(), photographer2);

		eventService.removePhotographer(foundEvent.getId(), photographer1);
		foundEvent = eventService.findById(event2.getId());
		assertEquals(event2, foundEvent);
		assertEquals(1, foundEvent.getPhotographers().size());
		timeslots = new ArrayList<Timeslot>(foundEvent.getTimeslots());
		assertEquals(timeslots.get(0).getPhotographers().stream().findFirst(), Optional.empty());
		assertEquals(timeslots.get(1).getPhotographers().stream().findFirst().get(), photographer2);
		assertEquals(0, timeslots.get(0).getPhotographers().size());
		assertEquals(1, timeslots.get(1).getPhotographers().size());
	}

	@Test
	public void testGetEventsForPhotographer() {
		generateData3();
		
		List<Event> foundEvents1 = eventService.getAllEventsForPhotographer(photographer1.getId());
		List<Event> foundEvents2 = eventService.getAllEventsForPhotographer(photographer2.getId());

		assertEquals(2, foundEvents1.size());
		assertEquals(1, foundEvents2.size());
		
		eventService.removePhotographer(foundEvents2.get(0).getId(), photographer1);
		
		foundEvents1 = eventService.getAllEventsForPhotographer(photographer1.getId());
		foundEvents2 = eventService.getAllEventsForPhotographer(photographer2.getId());
		
		assertEquals(1, foundEvents1.size());
		assertEquals(1, foundEvents2.size());
	}
	
	@Test
	public void testDeleteEventDoesntDeleteUser() {
		generateData3();
		eventService.deleteEvent(event2.getId());
		
		assertNotNull(photographer1);
		assertNotNull(photographer2);
		
		List<Event> foundEvents1 = eventService.getAllEventsForPhotographer(photographer1.getId());
		List<Event> foundEvents2 = eventService.getAllEventsForPhotographer(photographer2.getId());
		assertEquals(1, foundEvents1.size());
		assertEquals(0, foundEvents2.size());
	}

	private void generateData1() {
		// for testChangePhotographer
		eventType = new EventType(1l, "Wedding", 1250);
		photographer1 = createUser("bw@email.com", "pwd1", "Ben", "Walters");
		photographer2 = createUser("jd@email.com", "pwd2", "Jen", "Davis");
		event1 = new Event(1l, "The first Event", LocalDate.now(), eventType);
		timeslot1 = new Timeslot(1l, new EventTime(1230, 15), event1, "guests arrive", "", null,
				new HashSet<User>(Arrays.asList(photographer1)), null, false);
		timeslot2 = new Timeslot(2l, new EventTime(1230, 15), event1, "guests leave", "", null,
				new HashSet<User>(Arrays.asList(photographer2)), null, false);

		eventTypeService.addEventType(eventType);

		eventService.addEvent(event1);
		eventService.addPhotographer(event1.getId(), photographer1);
		eventService.addPhotographer(event1.getId(), photographer2);
		eventService.addTimeslot(event1.getId(), timeslot1);
		eventService.addTimeslot(event1.getId(), timeslot2);
	}
	
	private void generateData2() {
		// for testRemovePhotographer
		photographer1 = createUser("bw@email.com", "pwd1", "Ben", "Walters");
		photographer2 = createUser("jd@email.com", "pwd2", "Jen", "Davis");
		
		eventType = new EventType(1l, "Wedding", 1250);
		eventType = eventTypeService.addEventType(eventType);
		
		event2 = new Event(2l, "The first Event", LocalDate.now(), eventType);
		event2 = eventService.addEvent(event2);
		eventService.addPhotographer(event2.getId(), photographer1);
		eventService.addPhotographer(event2.getId(), photographer2);
		Timeslot tsTwo = new Timeslot(1l, new EventTime(1230, 15), event1, "guests arrive", "", null,
				new HashSet<User>(Arrays.asList(photographer1)), null, false);
		Timeslot tsThree = new Timeslot(2l, new EventTime(1230, 15), event1, "guests arrive", "", null,
				new HashSet<User>(Arrays.asList(photographer2)), null, false);
		eventService.addTimeslot(event2.getId(), tsTwo);
		eventService.addTimeslot(event2.getId(), tsThree);
	}
	
	private void generateData3() {
		// for testGetEventsForPhotographer and testDeleteEventDoesntDeleteUser
		eventType = new EventType(2l, "mini", 200);
		eventType = eventTypeService.addEventType(eventType);
		photographer1 = createUser("se@email.com", "PassWord45", "Scott", "Evans");
		photographer2 = createUser("kg@email.com", "PassWord45", "Kelly", "George");
		event1 = new Event(2l, "The short Event", LocalDate.now(), eventType);
		event2 = new Event(3l, "The long Event", LocalDate.now(), eventType);
		event1 = eventService.addEvent(event1);
		event2 = eventService.addEvent(event2);
		eventService.addPhotographer(event1.getId(), photographer1);
		
		eventService.addPhotographer(event2.getId(), photographer1);
		eventService.addPhotographer(event2.getId(), photographer2);
	}

	private User createUser(String email, String password, String firstName, String lastName) {
		UserRegistrationDto user = new UserRegistrationDto();
		user.setEmail(email);
		user.setConfirmEmail(email);
		user.setPassword(password);
		user.setConfirmPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setTerms(true);
		return userService.save(user);
	}
}
