package com.defrainPhoto.pictime.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.defrainPhoto.pictime.dto.TimeslotDTO;
import com.defrainPhoto.pictime.dto.TimeslotDTOImpl;
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

}
