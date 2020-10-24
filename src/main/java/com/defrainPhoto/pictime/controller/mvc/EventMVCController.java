package com.defrainPhoto.pictime.controller.mvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.defrainPhoto.pictime.controller.EventController;
import com.defrainPhoto.pictime.dto.CalendarEventDTO;
import com.defrainPhoto.pictime.dto.EventDTO;
import com.defrainPhoto.pictime.dto.MonthDTO;
import com.defrainPhoto.pictime.model.Event;
import com.defrainPhoto.pictime.model.EventType;

@Controller
@RequestMapping("/mvc/events/calendar")
public class EventMVCController {
	
	@Autowired
	EventController eventController;

//	private final String MVC_CLIENT_URL_BASE = "/mvc/clients/";
	private final String LIST_EVENTS_URL = "event/calendar-thymeleaf";
	private final String LIST_EVENTS_FOR_DAY_URL = "event/day-calendar-thymeleaf";
//	private final String EDIT_CLIENT_URL = "client/edit-client";
//	private final String NEW_CLIENT_URL = "client/new-client";
//	private final String SHOW_CLIENT_URL = "client/show-client";
	
	@GetMapping("/{year}/{month}")
	public ModelAndView showMonthCalendar(@PathVariable("year") Integer year, @PathVariable("month") Integer month) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		List<Integer> daysInMonth = Arrays.asList(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
		List<Integer> eventDays = Arrays.asList(2,15,15);
		
//		EventType et = new EventType(1l, "basic wedding", 1000);
//		Event event = new Event(1l, "bobs weddin", LocalDate.of(2020,2,5), et);
//		Event event2 = new Event(2l, "bobs bros weddin", LocalDate.of(2020,2,5), et);
//		eventController.addEvent(event);
//		eventController.addEvent(event2);
		
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
	
	@GetMapping("/{year}/{month}/{day}")
	public ModelAndView showDayCalendar(@PathVariable("year") Integer year, @PathVariable("month") Integer month, @PathVariable("day") Integer day) {

		Map<String, Object> params = new HashMap<String, Object>();
		
		List<EventDTO> eventDTOs = eventController.getAllEventsByYearAndMonthAndDay(year, month, day);
		
		params.put("events", eventDTOs);
		
		return new ModelAndView(LIST_EVENTS_FOR_DAY_URL, params) ;
	}
}
