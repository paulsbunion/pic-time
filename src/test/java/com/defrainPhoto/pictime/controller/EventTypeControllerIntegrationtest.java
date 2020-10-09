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

import com.defrainPhoto.pictime.model.EventType;
import com.defrainPhoto.pictime.service.EventTypeService;
import com.defrainPhoto.pictime.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = EventTypeController.class)
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class EventTypeControllerIntegrationtest {
	
	@Autowired
	MockMvc mvc;
	
	@InjectMocks
	EventTypeController eventTypeController;
	
	@MockBean
	EventTypeService eventTypeService;
	
	@MockBean
	UserService userService;
	
	@WithMockUser
	@Test
	public void testGetAllEventTypes() throws Exception {
		EventType eventType1 = new EventType(1l, "Basic Event", 1000);
		EventType eventType2 = new EventType(2l, "Super Event", 7689);
		
		when(eventTypeService.getAllEventTypes()).thenReturn(Arrays.asList(eventType1, eventType2));
		
		mvc.perform(get("/event-types")).andExpect(status().isOk())
			.andExpect(jsonPath("$[*]", hasSize(2)))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].name", is("Basic Event")))
			.andExpect(jsonPath("$[0].baseCost", is(1000)))
			.andExpect(jsonPath("$[1].id", is(2)))
			.andExpect(jsonPath("$[1].name", is("Super Event")))
			.andExpect(jsonPath("$[1].baseCost", is(7689)));
	}

	@WithMockUser
	@Test
	public void testAddEventType() throws Exception {
		EventType eventType = new EventType(1l, "Basic Event", 1000);
		
		when(eventTypeService.addEventType(eventType)).thenReturn(eventType);
		
		mvc.perform(post("/event-types").content(asJsonString(eventType)).contentType(MediaType.APPLICATION_JSON).with(csrf()))
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name", is("Basic Event")))
		.andExpect(jsonPath("$.baseCost", is(1000)));
	}
	
	@WithMockUser
	@Test
	public void testAddBadEventTypeNoName() throws Exception {
		EventType eventType = new EventType(1l, "", 1000);
		
		when(eventTypeService.addEventType(eventType)).thenReturn(eventType);
		
		mvc.perform(post("/event-types").content(asJsonString(eventType)).contentType(MediaType.APPLICATION_JSON).with(csrf()))
		.andExpect(status().isBadRequest());
		
	}
	
	@WithMockUser
	@Test
	public void testAddBadEventTypeNegativeCost() throws Exception {
		EventType eventType = new EventType(1l, "Blah", -1000);
		
		when(eventTypeService.addEventType(eventType)).thenReturn(eventType);
		
		mvc.perform(post("/event-types").content(asJsonString(eventType)).contentType(MediaType.APPLICATION_JSON).with(csrf()))
		.andExpect(status().isBadRequest());
		
	}
	
	@WithMockUser
	@Test
	public void testUpdateEventType() throws Exception {
		EventType eventType = new EventType(1l, "Fish Mini Session", 500);
		
		when(eventTypeService.addEventType(eventType)).thenReturn(eventType);
		when(eventTypeService.updateEventTypeById(eventType)).thenReturn(eventType);
		when(eventTypeService.getEventTypeById(1l)).thenReturn(eventType);
		
		mvc.perform(post("/event-types").content(asJsonString(eventType)).contentType(MediaType.APPLICATION_JSON).with(csrf()))
		.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name", is("Fish Mini Session")))
		.andExpect(jsonPath("$.baseCost", is(500)));
		
		eventType.setBaseCost(200);
		
		mvc.perform(put("/event-types/1").content(asJsonString(eventType)).contentType(MediaType.APPLICATION_JSON).with(csrf()))
		.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name", is("Fish Mini Session")))
		.andExpect(jsonPath("$.baseCost", is(200)));
		
	}
	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
