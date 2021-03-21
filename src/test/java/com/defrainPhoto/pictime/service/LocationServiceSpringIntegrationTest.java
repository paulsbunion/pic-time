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

import com.defrainPhoto.pictime.model.Location;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class LocationServiceSpringIntegrationTest {

	@Autowired
	LocationService locationService;

	@Test
	public void testAddLocation() {
		Location location = new Location(1l, "AppleTown", "SD", "76428", "10 Elm St.", "home");

		Location savedLocation = locationService.addLocation(location);
		assertEquals(location, savedLocation);
	}

	@Test(expected = TransactionSystemException.class)
	public void testAddLocationInvalidStreet() {
		Location location = new Location(1l, "AppleTown", "SD", "76428", "Elm St.", "home");

		Location savedLocation = locationService.addLocation(location);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testAddLocationInvalidCity() {
		Location location = new Location(1l, "134", "SD", "76428", "10 Elm St.", "");

		Location savedLocation = locationService.addLocation(location);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testAddLocationInvalidState() {
		Location location = new Location(1l, "AppleTown", "Green", "76428-1234", "10 Elm St.", "");

		Location savedLocation = locationService.addLocation(location);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testAddLocationInvalidZip() {
		Location location = new Location(1l, "AppleTown", "SD", "7642", "10 Elm St.", "");

		Location savedLocation = locationService.addLocation(location);
	}

}
