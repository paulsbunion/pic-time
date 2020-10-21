package com.defrainPhoto.pictime.controller.mvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/mvc/events/calendar")
public class EventMVCController {

//	private final String MVC_CLIENT_URL_BASE = "/mvc/clients/";
	private final String LIST_EVENTS_URL = "event/calendar-thymeleaf";
//	private final String EDIT_CLIENT_URL = "client/edit-client";
//	private final String NEW_CLIENT_URL = "client/new-client";
//	private final String SHOW_CLIENT_URL = "client/show-client";
	
	@GetMapping("/{year}/{month}")
	public ModelAndView showMonthCalendar(@PathVariable("year") Integer year, @PathVariable("month") Integer month) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		List<Integer> daysInMonth = Arrays.asList(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
		List<Integer> eventDays = Arrays.asList(2,15,15);
		
		params.put("daysInWeek", 7);
		params.put("weekStartDay", (int)(Math.random() * 6));
		params.put("daysInMonth", daysInMonth.get(month - 1));
		params.put("events", eventDays);
		
		return new ModelAndView(LIST_EVENTS_URL, params) ;
	}
}
