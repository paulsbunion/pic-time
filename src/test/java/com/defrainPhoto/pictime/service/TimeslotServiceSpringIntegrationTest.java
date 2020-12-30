package com.defrainPhoto.pictime.service;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.Before;
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

	@Before
	public void setup() {
		
		BigInteger count = new BigInteger("0");
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		List<Object[]> results2 = entityManager.createNativeQuery("SELECT * FROM event").getResultList();
		entityManager.getTransaction().commit();
		
		for (Object[] o : results2) {
			BigInteger id = (BigInteger) o[0];
			if (id != null) {
				count = id;
			}
		}
		
		if (count.intValue() < 1) {
			createTimeslots();
		}
	}

	@Test
	public void testGetAllTimeslots() {

		List<TimeslotDTO> timeslotDTOs = timeslotService.getAllTimeslots();
		assertEquals(2, timeslotDTOs.size());
	}

	@Test
	public void testGetTimelsotsForEvent() {
		Long id = 1l;
		List<Timeslot> eventTimeslots = timeslotService.getAllTimeslotsByEventId(id);
		assertEquals(2, eventTimeslots.size());
	}

	@Test
	public void testGetTimeslotsForUserID() {
		Long userID = 1l;
		List<TimeslotDTO> allTimeslotsForUser = timeslotService.findAllTimeslotsByUserId(userID);
	}

	private void createTimeslots() {
		System.out.println("CREATING DB ENTRIES");
		EventTime eventTime = new EventTime();
		eventTime.setStartTime(LocalTime.of(12, 0, 0));
		eventTime.setEndTime(LocalTime.of(12, 45, 0));

		Client client = new Client(1l, "bob", "bread", "address", "123-333-4444", "bob@bread.com", false);
		clientService.addClient(client);
		User user = new User("Photographer", "one", "photo@email.com", "password");
		user.setId(1l);

		Event event = new Event();
		event.setId(1l);

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.flush();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		entityManager.getTransaction().begin();
		entityManager.createNativeQuery("INSERT into user (id) VALUES (1)").executeUpdate();
		entityManager.getTransaction().commit();

		entityManager.getTransaction().begin();
		entityManager.createNativeQuery("INSERT into event (id) VALUES (1)").executeUpdate();
		entityManager.getTransaction().commit();

		Set<User> photographers = Collections.singleton(user);
		Location location = new Location(1l, "city", "OH", "43068", "11 street", "a place");
		locationService.addLocation(location);

		Timeslot timeslot = new Timeslot(1l, eventTime, event, "Arrive on location", "Dont forget props", client,
				photographers, location, false);
		timeslotService.addTimeslot(timeslot);

		eventTime.setStartTime(eventTime.getEndTime());
		eventTime.setEndTime(eventTime.getEndTime().plusMinutes(10));

		timeslot = new Timeslot(2l, eventTime, event, "secend Spot", null, null, null, null, false);
		timeslotService.addTimeslot(timeslot);
	}
}
