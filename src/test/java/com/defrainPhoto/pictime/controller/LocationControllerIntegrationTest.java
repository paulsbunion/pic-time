package com.defrainPhoto.pictime.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

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

	public static String asJsonString(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		}
		catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
