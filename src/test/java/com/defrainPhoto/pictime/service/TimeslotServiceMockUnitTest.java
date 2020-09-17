package com.defrainPhoto.pictime.service;

import static org.junit.Assert.assertEquals;
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
import com.defrainPhoto.pictime.model.Event;
import com.defrainPhoto.pictime.model.EventTime;
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
		
		Timeslot timeslot = new Timeslot(1l, new EventTime(1200, 15), event, "Test shoot", "", null, null, null,
				false);

		when(timeslotRepository.findById(1l)).thenReturn(Optional.of(timeslot)).thenReturn(Optional.of(timeslot));

		Timeslot result = timeslotRepository.findById(1l).get();
		assertEquals(0, result.getPhotographers().size());

		timeslot.addPhotographer(event.getPhotographers().stream().findFirst().get());

		result = timeslotRepository.findById(1l).get();
		assertEquals(1, result.getPhotographers().size());

	}

}
