package com.defrainPhoto.pictime.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

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

import com.defrainPhoto.pictime.model.Event;
import com.defrainPhoto.pictime.model.EventType;
import com.defrainPhoto.pictime.service.EventService;
import com.defrainPhoto.pictime.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = EventController.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class EventControllerIntegrationTest {

	@Autowired
	MockMvc mvc;

	@InjectMocks
	EventController eventController;

	@MockBean
	EventService eventService;

	@MockBean
	UserService userService;

	@WithMockUser
	@Test
	public void testAddEvent() throws Exception {
		EventType eventType = new EventType(3l, "Basic Event", 1000);
		Event event = new Event(1l, "Big Event", LocalDate.now(), eventType);

		when(eventService.addEvent(event)).thenReturn(event);

		mvc.perform(post("/events/").with(csrf()).content(asJsonString(event)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(event.getId().intValue())))
				.andExpect(jsonPath("$.eventName", is(event.getEventName())))
				.andExpect(jsonPath("$.eventType.id", is(event.getEventType().getId().intValue())));
	}

	
	/**
	 * See link for refactor, use application.properties 
	 * https://reflectoring.io/configuring-localdate-serialization-spring-boot/
	 * @param obj
	 * @return
	 */
	public static String asJsonString(final Object obj) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			return mapper.writeValueAsString(obj);
//			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
