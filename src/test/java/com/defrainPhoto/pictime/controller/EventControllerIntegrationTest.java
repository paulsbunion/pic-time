package com.defrainPhoto.pictime.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.modelmapper.spi.MatchingStrategy;
import org.modelmapper.spi.NamingConvention;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.defrainPhoto.pictime.dto.EventDTO;
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
	
	@MockBean
	ModelMapper modelMapperFake;
	
	ModelMapper realMapper = new ModelMapper();

	
	private static EventType eventType;
	private static Event e1;
	private static Event e2;
	private static User p1;
	private static User p2;
	private static Timeslot ts1;
	private static Timeslot ts2;
	private static Timeslot ts3;
	
	public EventControllerIntegrationTest() {
		realMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		realMapper.getConfiguration().setFieldMatchingEnabled(true)
		.setFieldAccessLevel(AccessLevel.PRIVATE)
		.setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR);
	}
	
	private void generateData() {
		eventType = new EventType(3l, "Basic Event", 1000);
		e1 = new Event(1l, "Big Event", LocalDate.now(), eventType);
		e2 = new Event(2l, "Small Event", LocalDate.now(), eventType);
		p1 = new User("Bob", "Walters", "bw@email.com", "pwd");
		p2 = new User("Sally", "Smith", "ss@email.com", "pwd");
		p1.setId(1l);
		p2.setId(2l);
		ts1 = new Timeslot(1l, new EventTime(LocalTime.of(12, 0, 0), LocalTime.of(12,15,0)), e1, "first ts", "", null, new HashSet<User>(Arrays.asList(p1)), null, false);
		ts2 = new Timeslot(2l, new EventTime(LocalTime.of(12, 15, 0), LocalTime.of(12,30,0)), e1, "second ts", "", null, new HashSet<User>(Arrays.asList(p1, p2)), null, false);
		ts3 = new Timeslot(3l, new EventTime(LocalTime.of(12, 30, 0), LocalTime.of(12,45,0)), e2, "third ts", "", null, new HashSet<User>(Arrays.asList(p1, p2)), null, false);
	}

	@Before
	public void setup() {
		generateData();
	}
	@WithMockUser
	@Test
	public void testAddEvent() throws Exception {

		when(eventService.addEvent(e1)).thenReturn(e1);
		when(modelMapperFake.map(e1, EventDTO.class)).thenReturn(realMapper.map(e1,EventDTO.class));

		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println(asJsonString(e1));
		mvc.perform(post("/events/").with(csrf()).content(asJsonString(e1)).contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(e1.getId().intValue())))
				.andExpect(jsonPath("$.eventName", is(e1.getEventName())))
				.andExpect(jsonPath("$.eventType.id", is(e1.getEventType().getId().intValue())));
	}
	
	@WithMockUser
	@Test
	public void testUpdateEventSwitchPhotographer() throws Exception {
		e1.addPhotographer(p1);
		e1.addPhotographer(p2);
		e1.addTimeslot(ts1);
		
		when(eventService.addEvent(e1)).thenReturn(e1);
		when(eventService.updateEvent(e1)).thenReturn(e1);
		when(eventService.switchPhotographer(1l, 1l, 2l)).thenReturn(e1);
		
		when(userService.findById(1l)).thenReturn(p1).thenReturn(p1);
		when(userService.findById(2l)).thenReturn(p2).thenReturn(p2);
		
//		when(modelMapperFake.map(e1, EventDTO.class)).thenReturn(realMapper.map(e1,EventDTO.class));
		when(modelMapperFake.map(e1, EventDTO.class)).thenReturn(test());
		
		
		mvc.perform(post("/events/").with(csrf()).content(asJsonString(e1)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(e1.getId().intValue())))
				.andExpect(jsonPath("$.eventName", is(e1.getEventName())))
				.andExpect(jsonPath("$.eventType.id", is(e1.getEventType().getId().intValue())))
				.andExpect(jsonPath("$.photographers.[*]", hasSize(2)))
				.andExpect(jsonPath("$.timeslots.[0].photographers.[*]", hasSize(1)))
				.andExpect(jsonPath("$.timeslots[0].photographers.[0].email", is("bw@email.com")));
		
		
		ts1.addPhotographer(p2);
		ts1.removePhotographer(p1);
		e1.setTimeslots(Arrays.asList(ts1));
		e1.removePhotographer(p1);
		
		when(modelMapperFake.map(e1, EventDTO.class)).thenReturn(realMapper.map(e1,EventDTO.class));
		
		mvc.perform(put("/events/1/switchPhotographer/oldPhotographerId/1/newPhotographerId/2").with(csrf()).content(asJsonString(e1)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(e1.getId().intValue())))
				.andExpect(jsonPath("$.eventName", is(e1.getEventName())))
				.andExpect(jsonPath("$.eventType.id", is(e1.getEventType().getId().intValue())))
				.andExpect(jsonPath("$.photographers.[*]", hasSize(1)))
				.andExpect(jsonPath("$.timeslots.[0].photographers.[*]", hasSize(1)))
				.andExpect(jsonPath("$.timeslots[0].photographers.[0].email", is("ss@email.com")));
	}

	private EventDTO test() {
		EventDTO result = realMapper.map(e1,EventDTO.class);
		System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
		System.out.println(asJsonString(result));
		return result;
	}

	@WithMockUser
	@Test
	public void testGetAllEvents() throws Exception {
		when(eventService.findAll()).thenReturn(Arrays.asList(e1, e2));
		List<Event> eventList = Arrays.asList(e1, e2);
		when(modelMapperFake.map(eventList, EventDTO[].class)).thenReturn(realMapper.map(eventList,EventDTO[].class));
		
		mvc.perform(get("/events/").with(csrf()).content(asJsonString("")).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(jsonPath("$.[*]", hasSize(2)));
	}

	@WithMockUser
	@Test
	public void testGetAllTimeslots() throws Exception {
		
		e1.addTimeslot(ts1);
		e1.addTimeslot(ts2);
		when(eventService.getAllTimeslots(1l)).thenReturn(Arrays.asList(ts1, ts2));
		
		mvc.perform(get("/events/1/timeslots").with(csrf()).content(asJsonString("")).contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.[*]", hasSize(2)));
	}
	
	@WithMockUser
	@Test
	public void testGetTimeslot() throws Exception {
		
		e1.addTimeslot(ts1);
		
		when(eventService.getTimeslot(1l)).thenReturn(ts1);
		
		mvc.perform(get("/events/1/timeslots/1").with(csrf()).content(asJsonString("")).contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.title", is("first ts")));
	}
	
	@WithMockUser
	@Test
	public void testAddTimeslot() throws Exception {
		String data = asJsonString(ts1);
		System.out.println("Here data");
		System.out.println(data);
		when(eventService.addTimeslot(1l, ts1)).thenReturn(ts1);
		ResultActions result = mvc.perform(post("/events/1/timeslots/").with(csrf()).content(asJsonString(ts1)).contentType(MediaType.APPLICATION_JSON))
//		data = result.toString();
//		System.out.println(data);
//		.andExpect(content().json());
		.andExpect(jsonPath("$.id", is(1)));
	}
	
	@WithMockUser
	@Test
	public void testUpdateTimeslot() throws Exception {
		when(eventService.updateTimeslot(ts1)).thenReturn(ts1);
		when(eventService.addTimeslot(1l, ts1)).thenReturn(ts1);
		
		mvc.perform(post("/events/1/timeslots/").with(csrf()).content(asJsonString(ts1)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id", is(1)));
		ts1.setNotes("New Notes");
		
		mvc.perform(put("/events/1/timeslots/1").with(csrf()).content(asJsonString(ts1)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.notes", is("New Notes")));
	}
	
	@WithMockUser
	@Test
	public void testDeleteTimeslot() throws Exception {
		
		doNothing().when(eventService).deleteEvent(2l);
		
		mvc.perform(delete("/events/1/timeslots/2").with(csrf()).content(asJsonString("")).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
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
			String result = mapper.writeValueAsString(obj); 
			
//			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//			System.out.println(result);
			return result;
//			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
