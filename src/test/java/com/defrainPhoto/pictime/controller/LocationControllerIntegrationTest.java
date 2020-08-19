package com.defrainPhoto.pictime.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.defrainPhoto.pictime.model.Location;
import com.defrainPhoto.pictime.service.LocationService;
import com.defrainPhoto.pictime.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = LocationController.class)
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class LocationControllerIntegrationTest {

	@Autowired
	MockMvc mvc;
	
	@InjectMocks
	LocationController locationController;
	
	@MockBean
	LocationService locationService;
	
	@MockBean
	UserService userService;
	
	@WithMockUser
	@Test
	public void testGetAllLocations() throws Exception {
		long id = 1l;
		
		Location location = new Location(id, "PickleCity", "MN", "56702", "123 South Pickle St.", "The Pickle Town");
		
		when(locationService.getAllLocations()).thenReturn(Arrays.asList(location));
		
		mvc.perform(get("/locations").with(csrf())).andExpect(status().isOk())
			.andExpect(jsonPath("[*]", hasSize(1)))
			.andExpect(jsonPath("[0].id", is(1)))
			.andExpect(jsonPath("[0].city", is("PickleCity")))
			.andExpect(jsonPath("[0].state", is("MN")))
			.andExpect(jsonPath("[0].zipcode", is("56702")))
			.andExpect(jsonPath("[0].street", is("123 South Pickle St.")))
			.andExpect(jsonPath("[0].description", is("The Pickle Town")));
	}
	
	@WithMockUser
	@Test
	public void testGetLocationById() throws Exception {
		long id = 1l;
		
		Location location = new Location(id, "PickleCity", "MN", "56702", "123 South Pickle St.", "The Pickle Town");
		
		when(locationService.getLocationById(id)).thenReturn(location);
		
		mvc.perform(get("/locations/1").with(csrf()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.city", is("PickleCity")))
			.andExpect(jsonPath("$.state", is("MN")))
			.andExpect(jsonPath("$.zipcode", is("56702")))
			.andExpect(jsonPath("$.street", is("123 South Pickle St.")))
			.andExpect(jsonPath("$.description", is("The Pickle Town")));
	}
	
	@WithMockUser
	@Test
	public void testAddLocation() throws Exception {
		Location location = new Location(1l, "PickleCity", "MN", "56702", "123 South Pickle St.", "The Pickle Town");
		
		when(locationService.addLocation(location)).thenReturn(location);
		
		mvc.perform(post("/locations/").with(csrf()).content(asJsonString(location)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(jsonPath("$.city", is("PickleCity")))
		.andExpect(jsonPath("$.state", is("MN")))
		.andExpect(jsonPath("$.zipcode", is("56702")))
		.andExpect(jsonPath("$.street", is("123 South Pickle St.")))
		.andExpect(jsonPath("$.description", is("The Pickle Town")));
	}
	
	@WithMockUser
	@Test
	public void testAddLocationInvalidCity() throws Exception {
		Location location = new Location(1l, "1WestCity6", "MN", "56702", "123 South Pickle St.", "The Pickle Town");
		
		when(locationService.addLocation(location)).thenReturn(location);
		
		mvc.perform(post("/locations/").with(csrf()).content(asJsonString(location)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@WithMockUser
	@Test
	public void testAddLocationInvalidState() throws Exception {
		Location location = new Location(1l, "PickleCity", "MSN", "56702", "123 South Pickle St.", "The Pickle Town");
		
		when(locationService.addLocation(location)).thenReturn(location);
		
		mvc.perform(post("/locations/").with(csrf()).content(asJsonString(location)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@WithMockUser
	@Test
	public void testAddLocationInvalidZipcode() throws Exception {
		Location location = new Location(1l, "PickleCity", "MN", "433", "123 South Pickle St.", "The Pickle Town");
		
		when(locationService.addLocation(location)).thenReturn(location);
		
		mvc.perform(post("/locations/").with(csrf()).content(asJsonString(location)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@WithMockUser
	@Test
	public void testAddLocationInvalidStreet() throws Exception {
		Location location = new Location(1l, "PickleCity", "MN", "56702-1234", "{Stret}", "The Pickle Town");
		
		when(locationService.addLocation(location)).thenReturn(location);
		
		mvc.perform(post("/locations/").with(csrf()).content(asJsonString(location)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@WithMockUser
	@Test
	public void testUpdateLocation() throws Exception {
		Location location = new Location(1l, "DownTown", "ND", "33254", "11 Main St.", "");
		
		when(locationService.addLocation(location)).thenReturn(location);
		when(locationService.updateLocationById(location)).thenReturn(location);
		when(locationService.getLocationById(1l)).thenReturn(location);
		
		mvc.perform(post("/locations").content(asJsonString(location)).contentType(MediaType.APPLICATION_JSON).with(csrf()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.city", is("DownTown")))
		.andExpect(jsonPath("$.state", is("ND")))
		.andExpect(jsonPath("$.zipcode", is("33254")))
		.andExpect(jsonPath("$.street", is("11 Main St.")))
		.andExpect(jsonPath("$.description", is("")));
		
		location.setDescription("DownTown ND!");
		
		mvc.perform(put("/locations/1").content(asJsonString(location)).contentType(MediaType.APPLICATION_JSON).with(csrf()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.city", is("DownTown")))
		.andExpect(jsonPath("$.state", is("ND")))
		.andExpect(jsonPath("$.zipcode", is("33254")))
		.andExpect(jsonPath("$.street", is("11 Main St.")))
		.andExpect(jsonPath("$.description", is("DownTown ND!")));
		
	}

	public static String asJsonString(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		}
		catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
