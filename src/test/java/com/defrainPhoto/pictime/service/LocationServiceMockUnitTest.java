package com.defrainPhoto.pictime.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.defrainPhoto.pictime.model.Location;
import com.defrainPhoto.pictime.repository.LocationRepository;

@RunWith(MockitoJUnitRunner.class)
public class LocationServiceMockUnitTest {

	@InjectMocks
	LocationService locationService = new LocationServiceImpl();

	@Mock
	LocationRepository locationRepository;

	@Test
	public void testAddNewLocation() {
		Location location = new Location(1l, "City", "CT", "22671", "101 Crown Gate Avenue", "The CROWN!");

		when(locationRepository.save(location)).thenReturn(location);
		Location result = locationService.saveLocation(location);
		assertEquals(location.getId(), result.getId());
		assertEquals(location.getDescription(), result.getDescription());
	}

	@Test
	public void testUpdateLocationById() {
		Location location = new Location(1l, "City", "CT", "22671", "101 Crown Gate Avenue", "The CROWN!");

		when(locationRepository.save(location)).thenReturn(location);
		Location result = locationService.saveLocation(location);
		result.setCity("PLAINVILLE");
		locationService.updateLocationById(location);
		assertEquals(1l, result.getId());
		assertEquals(location.getCity(), result.getCity());
	}

	@Test
	public void testGetAllLocations() {

		Location location = new Location(1l, "City", "CT", "22671", "101 Crown Gate Avenue", "The CROWN!");
		Location locationNoDesc = new Location(2l, "Orange Lake", "MN", "76533", "22 E Main St.", "");

		when(locationRepository.findAll()).thenReturn(Arrays.asList(location, locationNoDesc));

		List<Location> results = locationService.getAllLocations();
		assertTrue(results.size() == 2);
		assertEquals(2l, results.get(1).getId());
		assertEquals("", results.get(1).getDescription());

	}

	@Test
	public void testDeleteLocationById() {
		Location location = new Location(1l, "City", "CT", "22671", "101 Crown Gate Avenue", "The CROWN!");
		
		when(locationRepository.findById(1l)).thenReturn(Optional.of(location)).thenReturn(Optional.empty());
		Location beforeDelete = locationService.getLocationById(1l);
		locationService.deleteLocationById(1l);
		
		Mockito.verify(locationRepository, times(1)).deleteById(1l);
		assertEquals(location, beforeDelete);
	}

}
