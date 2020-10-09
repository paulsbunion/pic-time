package com.defrainPhoto.pictime.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.defrainPhoto.pictime.dto.LocationDTO;
import com.defrainPhoto.pictime.dto.LocationDTOImpl;
import com.defrainPhoto.pictime.model.Location;
import com.defrainPhoto.pictime.repository.LocationRepository;

@RunWith(MockitoJUnitRunner.class)
public class LocationServiceMockUnitTest {

	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	@InjectMocks
	LocationService locationService = new LocationServiceImpl();

	@Mock
	LocationRepository locationRepository;

	@Test
	public void testAddNewLocation() {
		Location location = new Location(1l, "City", "CT", "22671", "101 Crown Gate Avenue", "The CROWN!");

		when(locationRepository.save(location)).thenReturn(location);
		Location result = locationService.addLocation(location);
		assertEquals(location.getId(), result.getId());
		assertEquals(location.getDescription(), result.getDescription());
	}
	
	@Test
	public void testAddNewLocationInvalidCity() {
		Location location = new Location(1l, "7Wg", "Michigan", "22671", "101 Crown Gate Avenue", "The CROWN!");

		when(locationRepository.save(location)).thenReturn(location);
		Location result = locationService.addLocation(location);
		
		Set<ConstraintViolation<Location>> violations = validator.validate(location);
		
		System.out.println("Violations");
		violations.stream().forEach(e->System.out.println(e.getMessage()));
		assertEquals(1, violations.size());
		assertEquals("Invalid City", violations.stream().findFirst().get().getMessage());
	}
	
	@Test
	public void testAddNewLocationInvalidState() {
		Location location = new Location(1l, "City", "Pickle", "22671", "101 Crown Gate Avenue", "The CROWN!");

		when(locationRepository.save(location)).thenReturn(location);
		Location result = locationService.addLocation(location);
		
		Set<ConstraintViolation<Location>> violations = validator.validate(location);
		
		System.out.println("Violations");
		violations.stream().forEach(e->System.out.println(e.getMessage()));
		assertEquals(1, violations.size());
		assertEquals("Invalid State", violations.stream().findFirst().get().getMessage());
	}
	
	@Test
	public void testAddNewLocationInvalidStreet() {
		Location location = new Location(1l, "City", "MN", "22671-3345", "101 Crown{ Gate Avenue", "The CROWN!");

		when(locationRepository.save(location)).thenReturn(location);
		Location result = locationService.addLocation(location);
		
		Set<ConstraintViolation<Location>> violations = validator.validate(location);
		
		System.out.println("Violations");
		violations.stream().forEach(e->System.out.println(e.getMessage()));
		assertEquals(1, violations.size());
		assertEquals("Invalid Street", violations.stream().findFirst().get().getMessage());
	}
	
	@Test
	public void testAddNewLocationInvalidSZipcode() {
		Location location = new Location(1l, "City", "WA", "22671:5", "101 Crown Gate Avenue", "The CROWN!");

		when(locationRepository.save(location)).thenReturn(location);
		Location result = locationService.addLocation(location);
		
		Set<ConstraintViolation<Location>> violations = validator.validate(location);
		
		System.out.println("Violations");
		violations.stream().forEach(e->System.out.println(e.getMessage()));
		assertEquals(1, violations.size());
		assertEquals("Invalid Zipcode", violations.stream().findFirst().get().getMessage());
	}

	@Test
	public void testUpdateLocationById() {
		Location location = new Location(1l, "City", "CT", "22671", "101 Crown Gate Avenue", "The CROWN!");

		when(locationRepository.save(location)).thenReturn(location);
		Location result = locationService.addLocation(location);
		result.setCity("PLAINVILLE");
		locationService.updateLocationById(location);
		assertEquals(Long.valueOf(1l), result.getId());
		assertEquals(location.getCity(), result.getCity());
	}

	@Test
	public void testGetAllLocations() {

		LocationDTO location = new LocationDTOImpl(1l, "City", "CT", "22671", "101 Crown Gate Avenue", "The CROWN!");
		LocationDTO locationNoDesc = new LocationDTOImpl(2l, "Orange Lake", "MN", "76533", "22 E Main St.", "");

		when(locationRepository.findAllBy()).thenReturn(Arrays.asList(location, locationNoDesc));

		List<LocationDTO> results = locationService.getAllLocations();
		assertTrue(results.size() == 2);
		assertEquals(Long.valueOf(2l), results.get(1).getId());
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
