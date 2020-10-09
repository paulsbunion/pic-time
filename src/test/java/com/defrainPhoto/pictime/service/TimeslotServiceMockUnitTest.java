package com.defrainPhoto.pictime.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.ComponentScan;

import com.defrainPhoto.pictime.dto.TimeslotDTO;
import com.defrainPhoto.pictime.dto.TimeslotDTOImpl;
import com.defrainPhoto.pictime.model.Client;
import com.defrainPhoto.pictime.model.Event;
import com.defrainPhoto.pictime.model.EventTime;
import com.defrainPhoto.pictime.model.Location;
import com.defrainPhoto.pictime.model.Timeslot;
import com.defrainPhoto.pictime.model.User;
import com.defrainPhoto.pictime.repository.TimeslotRepository;

@ComponentScan
@RunWith(MockitoJUnitRunner.class)
public class TimeslotServiceMockUnitTest {

	@InjectMocks
	TimeslotService timeslotService = new TimeslotServiceImpl();

	@Mock
	TimeslotRepository timeslotRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllTimeslots() {
		TimeslotDTO timeslot = new TimeslotDTOImpl();

		when(timeslotRepository.findAllBy()).thenReturn(Arrays.asList(timeslot));

		List<TimeslotDTO> results = timeslotService.getAllTimeslots();

		assertEquals(1, results.size());
	}

	@Test
	public void testAddPhotographer() {
		Event event = new Event();
		User photographerOne = new User("Sally", "Butters", "sb@b.com", "pwd");
		event.addPhotographer(photographerOne);

		Timeslot timeslot = new Timeslot(1l, new EventTime(1200, 15), event, "Test shoot", "", null, null, null, false);

		when(timeslotRepository.findById(1l)).thenReturn(Optional.of(timeslot)).thenReturn(Optional.of(timeslot));

		Timeslot result = timeslotService.findTimeslotById(1l);
		assertEquals(0, result.getPhotographers().size());

		timeslot.addPhotographer(event.getPhotographers().stream().findFirst().get());

		result = timeslotService.findTimeslotById(1l);
		assertEquals(1, result.getPhotographers().size());

	}
	
	@Test
	public void testSwitchPhotographer() {
		Event event = new Event();
		User photographerOne = new User("Sally", "Butters", "sb@b.com", "pwd");
		User photographerTwo = new User("Tom", "Caldwell", "tc@b.com", "pwd");
		User photographerThree = new User("Ursela", "Davis", "ud@b.com", "pwd");
		event.addPhotographer(photographerOne);
		event.addPhotographer(photographerTwo);
		event.addPhotographer(photographerThree);

		Timeslot timeslot = new Timeslot(1l, new EventTime(1200, 15), event, "Test shoot", "", null, null, null, false);

		when(timeslotRepository.findById(1l)).thenReturn(Optional.of(timeslot)).thenReturn(Optional.of(timeslot));

		timeslot.addPhotographer(event.getPhotographers().stream().filter(p -> p.getFirstName().equals("Sally")).findFirst().orElse(null));
		timeslot.addPhotographer(event.getPhotographers().stream().filter(p -> p.getFirstName().equals("Tom")).findFirst().orElse(null));
		
		Timeslot result = timeslotService.findTimeslotById(1l);
		assertEquals(2, result.getPhotographers().size());
		assertFalse(result.getPhotographers().contains(photographerThree));
		
		timeslotService.changePhotographer(timeslot, photographerTwo, photographerThree);
		
		result = timeslotService.findTimeslotById(1l);
		assertEquals(2, result.getPhotographers().size());
		assertFalse(result.getPhotographers().contains(photographerTwo));
	}

	@Test
	public void testSetLocation() {
		Event event = new Event();
		User photographerOne = new User("Sally", "Butters", "sb@b.com", "pwd");
		event.addPhotographer(photographerOne);
		Location location = new Location(1l, "Pittsburg", "PA", "32147", "12 Street", "");

		Timeslot timeslot = new Timeslot(1l, new EventTime(1200, 15), event, "Test shoot", "", null, null, null, false);
		
		when(timeslotRepository.findById(1l)).thenReturn(Optional.of(timeslot)).thenReturn(Optional.of(timeslot));
		
		Timeslot result = timeslotService.findTimeslotById(1l);
		assertEquals(null, result.getLocation());

		timeslot.setLocation(location);

		result = timeslotService.findTimeslotById(1l);
		assertEquals(location, result.getLocation());
	}

	@Test
	public void testAddNotes() {
		Event event = new Event();

		Timeslot timeslot = new Timeslot(1l, new EventTime(1200, 15), event, "Test shoot", "", null, null, null, false);
		
		when(timeslotRepository.findById(1l)).thenReturn(Optional.of(timeslot)).thenReturn(Optional.of(timeslot));
		
		Timeslot result = timeslotService.findTimeslotById(1l);
		assertEquals("", result.getNotes());

		String notes = "New notes";
		timeslot.setNotes(notes);
		
		result = timeslotService.findTimeslotById(1l);
		assertEquals(notes, result.getNotes());
	}

	@Test
	public void testAddClient() {
		Event event = new Event();
		Client client = new Client(2l, "Betty", "Adams", "123 E main", "123-222-3333", "a@b.com", false);

		Timeslot timeslot = new Timeslot(1l, new EventTime(1200, 15), event, "Test shoot", "", null, null, null, false);
		
		when(timeslotRepository.findById(1l)).thenReturn(Optional.of(timeslot)).thenReturn(Optional.of(timeslot));
		
		Timeslot result = timeslotService.findTimeslotById(1l);
		assertEquals(null, result.getClient());

		timeslot.setClient(client);

		result = timeslotService.findTimeslotById(1l);
		assertEquals(client, result.getClient());
	}

}
