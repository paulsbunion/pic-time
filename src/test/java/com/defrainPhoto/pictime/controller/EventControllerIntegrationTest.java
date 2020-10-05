package com.defrainPhoto.pictime.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
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
import com.defrainPhoto.pictime.model.EventTime;
import com.defrainPhoto.pictime.model.EventType;
import com.defrainPhoto.pictime.model.Timeslot;
import com.defrainPhoto.pictime.model.User;
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

	
	private static EventType eventType;
	private static Event e1;
	private static Event e2;
	private static User p1;
	private static User p2;
	private static Timeslot ts1;
	private static Timeslot ts2;
	private static Timeslot ts3;
	
	private void generateData() {
		eventType = new EventType(3l, "Basic Event", 1000);
		e1 = new Event(1l, "Big Event", LocalDate.now(), eventType);
		e2 = new Event(2l, "Small Event", LocalDate.now(), eventType);
		p1 = new User("Bob", "Walters", "bw@email.com", "pwd");
		p2 = new User("Sally", "Smith", "ss@email.com", "pwd");
		ts1 = new Timeslot(1l, new EventTime(1200, 15), e1, "first ts", "", null, new HashSet<User>(Arrays.asList(p1)), null, false);
		ts2 = new Timeslot(2l, new EventTime(1215, 15), e1, "first ts", "", null, new HashSet<User>(Arrays.asList(p1, p2)), null, false);
		ts3 = new Timeslot(3l, new EventTime(1230, 15), e2, "first ts", "", null, new HashSet<User>(Arrays.asList(p1, p2)), null, false);
	}

	@Before
	public void setup() {
		generateData();
	}
	@WithMockUser
	@Test
	public void testAddEvent() throws Exception {

		when(eventService.addEvent(e1)).thenReturn(e1);

		mvc.perform(post("/events/").with(csrf()).content(asJsonString(e1)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(e1.getId().intValue())))
				.andExpect(jsonPath("$.eventName", is(e1.getEventName())))
				.andExpect(jsonPath("$.eventType.id", is(e1.getEventType().getId().intValue())));
	}
	
	@WithMockUser
	@Test
	public void testUpdateEvent() throws Exception {
		
		when(eventService.addEvent(e1)).thenReturn(e1);
		when(eventService.updateEvent(e1)).thenReturn(e1);
		
		e1.addPhotographer(p1);
		e1.addPhotographer(p2);
		e1.addTimeslot(ts1);
		
		mvc.perform(post("/events/").with(csrf()).content(asJsonString(e1)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(e1.getId().intValue())))
				.andExpect(jsonPath("$.eventName", is(e1.getEventName())))
				.andExpect(jsonPath("$.eventType.id", is(e1.getEventType().getId().intValue())))
				.andExpect(jsonPath("$.photographers.[*]", hasSize(2)));
		
		
		
		mvc.perform(put("/events/1").with(csrf()).content(asJsonString(e1)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(e1.getId().intValue())))
				.andExpect(jsonPath("$.eventName", is(e1.getEventName())))
				.andExpect(jsonPath("$.eventType.id", is(e1.getEventType().getId().intValue())));
	}
	
	@WithMockUser
	@Test
	public void testDeleteEvent() throws Exception {
		mvc.perform(delete("/events/1").with(csrf()).content(asJsonString("")).contentType(MediaType.APPLICATION_JSON));
	}
	
	@WithMockUser
	@Test
	public void testGetAllEvents() throws Exception {
		mvc.perform(get("/events").with(csrf()).content(asJsonString("")).contentType(MediaType.APPLICATION_JSON));
	}

	@WithMockUser
	@Test
	public void testSwitchPhotographer() throws Exception {
		mvc.perform(put("/events/1/switchphotographer?old=1&new=2").with(csrf()).content(asJsonString("")).contentType(MediaType.APPLICATION_JSON));
	}
	
	@WithMockUser
	@Test
	public void testGetAllTimeslots() throws Exception {
		mvc.perform(get("/events/1/timeslots").with(csrf()).content(asJsonString("")).contentType(MediaType.APPLICATION_JSON));
	}
	
	@WithMockUser
	@Test
	public void testGetTimeslot() throws Exception {
		mvc.perform(get("/events/1/timeslots/1").with(csrf()).content(asJsonString("")).contentType(MediaType.APPLICATION_JSON));
	}
	
	@WithMockUser
	@Test
	public void testAddTimeslot() throws Exception {
		mvc.perform(post("/events/1/timeslots/").with(csrf()).content(asJsonString("")).contentType(MediaType.APPLICATION_JSON));
	}
	
	@WithMockUser
	@Test
	public void testUpdateTimeslot() throws Exception {
		mvc.perform(put("/events/1/timeslots/1").with(csrf()).content(asJsonString("")).contentType(MediaType.APPLICATION_JSON));
	}
	
	@WithMockUser
	@Test
	public void testRemoveTimeslot() throws Exception {
		mvc.perform(delete("/events/1/timeslots/2").with(csrf()).content(asJsonString("")).contentType(MediaType.APPLICATION_JSON));
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
