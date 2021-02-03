package com.defrainPhoto.pictime.controller.mvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.defrainPhoto.pictime.controller.EventController;
import com.defrainPhoto.pictime.controller.PhotographerController;
import com.defrainPhoto.pictime.dto.CalendarEventDTO;
import com.defrainPhoto.pictime.dto.EventDTO;
import com.defrainPhoto.pictime.dto.MonthDTO;
import com.defrainPhoto.pictime.dto.UserDTO;
import com.defrainPhoto.pictime.model.Client;
import com.defrainPhoto.pictime.model.Event;
import com.defrainPhoto.pictime.model.EventTime;
import com.defrainPhoto.pictime.model.EventType;
import com.defrainPhoto.pictime.model.Timeslot;
import com.defrainPhoto.pictime.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/mvc/events")
public class EventMVCController {
	
	@Autowired
	ModelMapper modelMapper;
	
	private static final String NEW_EVENT_URL = "event/new-event";
	private static final String MVC_ALL_EVENT_URL_BASE = "/mvc/events/";

	private static final String MVC_EVENT_URL_BASE = "/mvc/events/calendar/";

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EventController eventController;
	@Autowired
	PhotographerController photographerController;

//	private final String MVC_CLIENT_URL_BASE = "/mvc/clients/";
	private final String LIST_ALL_EVENTS_URL = "event/list-events";
	
	private final String LIST_EVENTS_URL = "event/calendar-thymeleaf";
	private final String LIST_EVENTS_FOR_DAY_URL = "event/day-calendar-thymeleaf";
	private final String EDIT_EVENT_URL = "event/edit-event";
//	private final String NEW_CLIENT_URL = "client/new-client";
	private final String SHOW_EVENT_URL = "event/show-event";
	
	@GetMapping("list")
	public String listAllEvents(Model model) {
		log.info("MVC user calling get all Events");
		List<EventDTO> allEvents = eventController.getAllEvents();
//		while (allEvents == null || allEvents.isEmpty()) {
//			log.info("Addind Dummy Client Data for testing");
//			addClients();
//			allClients = clientController.getAllClients();
//		}
		model.addAttribute("events", allEvents);
		LocalDate now = LocalDate.now();
		MonthDTO month = new MonthDTO(now);
		model.addAttribute("month", month);
		return LIST_ALL_EVENTS_URL;
	}
	
	@GetMapping("show/{id}")
	public String showEventDetails(@PathVariable("id") long id, Model model) {
		log.info("MVC user calling show event details for ID: " + id);
		model.addAttribute("event", eventController.getEvent(id));
		model.addAttribute("all_photographers", photographerController.getAllPhotographers());
		return SHOW_EVENT_URL;
	}
	
	@GetMapping("edit/{id}")
	public String showEditEventForm(@PathVariable("id") long id, Model model) {
		log.info("MVC user editing existing event with ID: " + id);
		Event event = eventController.getEvent(id);
		model.addAttribute("event", event);
//		model.addAttribute("all_photographers", photographerController.getAllPhotographers());
		
		List<UserDTO> availablePhotographers = new LinkedList<UserDTO>(photographerController.getAllPhotographers());
		List<UserDTO> assignedPhotographers = Arrays.asList(modelMapper.map(event.getPhotographers(), UserDTO[].class));
		if (assignedPhotographers != null && availablePhotographers != null) {
			availablePhotographers.removeAll(assignedPhotographers);
		}
		
		model.addAttribute("available_photographers", availablePhotographers);
		model.addAttribute("assigned_photographers", assignedPhotographers);
		
		return EDIT_EVENT_URL;
	}
	
	@PostMapping("update/{id}")
	public String updateEditedEvent(@Valid Event event, BindingResult result, @PathVariable("id") Long id, Model model) {
		log.info("MVC user saving edits to existing event with ID: " + id);
		if (result.hasErrors()) {
			log.error("Error saving event changes: " + result.getAllErrors());
			event.setId(id);
			return EDIT_EVENT_URL;
		}
		Event oldEvent = eventController.getEvent(id);
		event.setTimeslots(oldEvent.getTimeslots());
		eventController.updateEvent(event, id);
		return "redirect:" + MVC_ALL_EVENT_URL_BASE + "list";
	}
	
	@GetMapping("delete/{id}")
	public String deleteEvent(@PathVariable("id") long id) {
		log.info("entering delete Event controller from event MVC");
		try {
			eventController.deleteEvent(id);
		}
		catch (EmptyResultDataAccessException e) {
			log.error("Error occured in calling delete event by ID, Empty Result for ID: " + id, e);
		}
		return "redirect:" + MVC_ALL_EVENT_URL_BASE + "list";
	}
	
	@GetMapping("/new")
	public String newEventFormToday(Model model) {
		log.info("MVC user creating new Event for Today");
		LocalDate today = LocalDate.now();
		return "redirect:" + "/mvc/events/new/"  + today.format(MonthDTO.dayformatter);
	}
	
	@GetMapping("/new/{year}/{month}/{day}")
	public String newEventForm(Model model, @PathVariable("year") Integer year, @PathVariable("month") Integer month, @PathVariable("day") Integer day) {
		log.info("MVC user creating new Event");
		Event event = new Event();
		event.setDate(LocalDate.of(year, month, day));
		model.addAttribute("event", event);
		return NEW_EVENT_URL;
	}
	
	@PostMapping("/save")
	public String saveNewEvent(@Valid Event event, BindingResult result, Model model) {
		log.info("MVC user saving new Event");
		
		if(result.hasErrors()) {
			log.error("Error(s) saving new Event: " + result.getAllErrors());
			return NEW_EVENT_URL;
		}
		EventDTO eventDTO = eventController.addEvent(event);
		return "redirect:" + MVC_EVENT_URL_BASE + event.getDate().format(MonthDTO.dayformatter) + "?eventId=" +eventDTO.getId();
	}
	
	@PostMapping("/{event_id}/addphotographer/{photog_id}")
	@ResponseBody
	public String addPhotographerToEvent(@PathVariable("event_id") Long eventId, @PathVariable("photog_id") Long photogId) {
		log.info("MVC user adding photographer with id: " + photogId + " to event " + eventId);
		Event event = eventController.getEvent(eventId);
		User photographer = new User();
		photographer.setId(photogId);
		event.addPhotographer(photographer);
		eventController.updateEvent(event, eventId);
//		eventController.u
		return LIST_ALL_EVENTS_URL;
	}
	
	@PostMapping("/{event_id}/removephotographer/{photog_id}")
	@ResponseBody
	public String removePhotographerToEvent(@PathVariable("event_id") Long eventId, @PathVariable("photog_id") Long photogId) {
		log.info("MVC user removing photographer with id: " + photogId + " to event " + eventId);
		Event event = eventController.getEvent(eventId);
		User photographer = new User();
		photographer.setId(photogId);
		event.removePhotographer(photographer);
		eventController.updateEvent(event, eventId);
//		eventController.u
		return LIST_ALL_EVENTS_URL;
	}
	
	@GetMapping("/calendar/{year}/{month}")
	public ModelAndView showMonthCalendar(@PathVariable("year") Integer year, @PathVariable("month") Integer month) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		List<Integer> daysInMonth = Arrays.asList(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
		List<Integer> eventDays = Arrays.asList(2,15,15);
		
//		EventType et = new EventType(1l, "basic wedding", 1000);
//		Event event = new Event(1l, "bobs weddin", LocalDate.of(2020,2,5), et);
//		Event event2 = new Event(2l, "bobs bros weddin", LocalDate.of(2020,2,5), et);
//		eventController.addEvent(event);
//		eventController.addEvent(event2);
		
//		List<EventDTO> events = eventController.getAllEventsByYearAndMonthAndDay(2020, 2, 5);
//		EventDTO eventDTO = events.get(0);
//		Event event = new ModelMapper().map(eventDTO, Event.class);
//		Timeslot timeslot = new Timeslot(1l, new EventTime(1230, 45), event, "arrive", null, null, null, null, false);
//		eventController.addTimeslot(eventDTO.getId(), timeslot);
		
		List<CalendarEventDTO> eventDTOs = eventController.getAllEventsByYearAndMonth(year, month);
		MonthDTO monthDTO = new MonthDTO(year, month);
		
		params.put("daysInWeek", 7);
		params.put("month", monthDTO);
//		params.put("weekStartDay", (int)(Math.random() * 6));
//		params.put("weekStartDay", LocalDate.of(year, month, 1).getDayOfWeek().getValue());
//		params.put("daysInMonth", daysInMonth.get(month - 1));
		params.put("events", eventDTOs);
		
		return new ModelAndView(LIST_EVENTS_URL, params) ;
	}
	
	@GetMapping("/calendar")
	public String showTodayCalendar() {
		LocalDate today = LocalDate.now();
		return "redirect:" + "/mvc/events/calendar/" + today.format(MonthDTO.monthformatter);
	}
	
	@GetMapping("/calendar/{year}/{month}/{day}")
	public ModelAndView showDayCalendar(@PathVariable("year") Integer year, @PathVariable("month") Integer month, @PathVariable("day") Integer day) {

		Map<String, Object> params = new HashMap<String, Object>();
		
		List<EventDTO> eventDTOs = eventController.getAllEventsByYearAndMonthAndDay(year, month, day);
		HashMap<Long, List<Timeslot>> eventTimeslots = new HashMap<Long, List<Timeslot>>(); 
		eventDTOs.forEach( e -> eventTimeslots.put(e.getId(), eventController.getAllTimeslots(e.getId())));
		HashMap<String, TimeslotTimeSpan> timeslotGridSpans = new HashMap<String, TimeslotTimeSpan>();
		timeslotGridSpans = mapTimeslots(eventTimeslots, timeslotGridSpans);
		MonthDTO monthDTO = new MonthDTO(year, month, day);

//		ObjectMapper mapper = new ObjectMapper();
//		eventTimeslots.values().stream().filter(e -> !e.isEmpty()).forEach(e -> {
//			e.forEach(et -> {
//			try {
//				if (et.getId() == 7) {
//				System.out.println(mapper.writeValueAsString(et));
//				}
//			} catch (JsonProcessingException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			return;
//			});});
		
		if (eventDTOs.isEmpty()) {
			params.put("eventExists", "false");
		}
		else {
			params.put("eventExists", "true");
		}
		
		params.put("month", monthDTO);
		params.put("events", eventDTOs);
		params.put("eventTimeslots", eventTimeslots);
		params.put("timeslotGridSpans", timeslotGridSpans);
		
		return new ModelAndView(LIST_EVENTS_FOR_DAY_URL, params) ;
	}
	
	@GetMapping("/{eventId}/timeslots/{timeslotId}/delete")
	public String deleteTimeslot(@PathVariable("eventId") long eventId, @PathVariable("timeslotId") long timeslotId) {
		log.info("entering delete Timeslot controller from event MVC");
		try {
			eventController.deleteTimeslot(eventId, timeslotId);
		}
		catch (EmptyResultDataAccessException e) {
			log.error("Error occured in calling delete Timeslot by ID, Empty Result for ID: " + timeslotId, e);
		}
		String eventDate = eventController.getEvent(eventId).getDate().format(MonthDTO.dayformatter);
		return "redirect:" + MVC_EVENT_URL_BASE + eventDate + "?eventId=" + eventId;
	}

	private HashMap<String, TimeslotTimeSpan> mapTimeslots(HashMap<Long, List<Timeslot>> eventTimeslots, HashMap<String, TimeslotTimeSpan> timeslotGridSpans) {
		eventTimeslots.forEach((eventId, timeslots) -> {
			timeslots.forEach(timeslot -> {
				timeslotGridSpans.put("" + eventId + "_" + timeslot.getId(), TimeslotTimeSpan.mapTimeToGridSpan(timeslot.getTime()));
			});
		});
		return timeslotGridSpans;
	}
}
