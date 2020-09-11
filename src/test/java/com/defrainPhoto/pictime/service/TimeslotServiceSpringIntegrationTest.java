package com.defrainPhoto.pictime.service;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.defrainPhoto.pictime.dto.TimeslotDTO;
import com.defrainPhoto.pictime.model.Client;
import com.defrainPhoto.pictime.model.Event;
import com.defrainPhoto.pictime.model.EventTime;
import com.defrainPhoto.pictime.model.EventUser;
import com.defrainPhoto.pictime.model.Location;
import com.defrainPhoto.pictime.model.Timeslot;
import com.defrainPhoto.pictime.model.User;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class TimeslotServiceSpringIntegrationTest {
	
	@Autowired
	TimeslotService timeslotService;
	@Autowired
	ClientService clientService;
	@Autowired
	LocationService locationService;
	@Autowired
	UserService userService;
	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	
	@Test
	public void testGetAllTimeslots() {
		createTimeslots();
		
		List<TimeslotDTO> timeslotDTOs= timeslotService.getAllTimeslots();
		assertEquals(2, timeslotDTOs.size());
	}

	@Test
	public void testGetTimelsotsForTimeline() {
		Long id = 0l;
		List<Timeslot> timelineTimeslots = timeslotService.getAllTimeslotsByEventId(id);
	}
	
	@Test
	public void testGetTimeslotsForUserID() {
		Long userID = 1l;		
		List<TimeslotDTO> allTimeslotsForUser = timeslotService.findAllTimeslotsByUserId(userID);
	}
	
	private void createTimeslots() {
		EventTime eventTime = new EventTime();
		eventTime.setStartTime(1200);
		eventTime.setTotalMinutes(45);
		
		Client client = new Client(1l, "bob", "bread", "address", "123-333-4444", "bob@bread.com", false);
		clientService.addClient(client);
		User user = new User("Photographer", "one", "photo@email.com", "password");
		user.setId(1l);
		
		EventUser eventUser = new EventUser();
		Event event = new Event();
		event.setId(1l);
		eventUser.setUser(user);
		eventUser.setEvent(event);
		eventUser.setEventUserId(1l);
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.createNativeQuery("INSERT into user (id) VALUES (1)").executeUpdate();
		entityManager.getTransaction().commit();
		
		entityManager.getTransaction().begin();
		entityManager.createNativeQuery("INSERT into event (id) VALUES (1)").executeUpdate();
		entityManager.getTransaction().commit();
		
		System.out.println("Results");
		entityManager.getTransaction().begin();
		List<Object[]> results2 = entityManager.createNativeQuery("SELECT * FROM event").getResultList();
		
		entityManager.getTransaction().commit();
		for (Object[] o : results2) {
			BigInteger id = (BigInteger) o[0];
			System.out.println(id);
		}
		entityManager.getTransaction().begin();
		entityManager.createNativeQuery("INSERT into event_user (event_user_id, event_id, user_id) VALUES (1, 1, 1)" ).executeUpdate();
		entityManager.getTransaction().commit();
		System.out.println("Results");
		entityManager.getTransaction().begin();
		List<Object[]> results = entityManager.createNativeQuery("SELECT * FROM event_user").getResultList();
		for (Object[] o : results) {
			BigInteger id = (BigInteger) o[0];
			BigInteger id2 = (BigInteger) o[1];
			BigInteger id3 = (BigInteger) o[2];
			System.out.println(id + " ");
		}
		entityManager.getTransaction().commit();
		Set<EventUser> photographers = Collections.singleton(eventUser);
		Location location = new Location(1l, "city", "OH", "43068", "11 street", "a place");
		locationService.addLocation(location);
		
		Timeslot timeslot = new Timeslot(1l, eventTime, event, "Arrive on location", "Dont forget props",
				client, photographers, location, false);
		timeslotService.addTimeslot(timeslot);
		
		eventTime.setStartTime(eventTime.getStartTime() + eventTime.getTotalMinutes());
		eventTime.setTotalMinutes(10);
		
		timeslot = new Timeslot(2l, eventTime, event, "secend Spot", null, null, null, null, false);
		timeslotService.addTimeslot(timeslot);
	}	
}
