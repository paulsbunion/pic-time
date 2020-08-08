package com.defrainPhoto.pictime.controller.mvc;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.defrainPhoto.pictime.controller.EventTypeController;
import com.defrainPhoto.pictime.model.EventType;

@Controller
@RequestMapping("/mvc/event-types/")
public class EventTypeMVCController {
	
	private static final String LIST_EVENT_TYPES_URL = "event-type/list-event-types";
	private static final String NEW_EVENT_TYPE_URL = "event-type/new-event-type";
	private static final String MVC_EVENT_TYPE_URL_BASE = "/mvc/event-types/";
	private static final String EDIT_EVENT_TYPE_URL = "event-type/edit-event-type";

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EventTypeController eventTypeController;
	
	@GetMapping("list")
	public String getAllEventTypes(Model model) {
		log.info("MVC user calling get all event-types");
		List<EventType> allEventTypes = eventTypeController.getAllEventTypes();
		
		while(allEventTypes.isEmpty()) {
			addEventTypes();
			allEventTypes = eventTypeController.getAllEventTypes();
		}
		model.addAttribute("eventTypes", allEventTypes);
		return LIST_EVENT_TYPES_URL;
	}
	
	@GetMapping("new")
	public String newEventTypeForm(Model model) {
		log.info("MVC user calling new Event Type");
		model.addAttribute("eventType", new EventType());
		return NEW_EVENT_TYPE_URL;
	}
	
	@PostMapping("save")
	public String saveNewEventType(@Valid EventType eventType, BindingResult result, Model model) {
		if (result.hasErrors()) {
			log.error("Errors savinng new Event Type: " + (result.getFieldErrors().stream()
					.map(e -> e.getDefaultMessage()).collect(Collectors.joining(","))));
			return NEW_EVENT_TYPE_URL;
		}
		else {
			eventTypeController.addEventType(eventType);
			return "redirect:" + MVC_EVENT_TYPE_URL_BASE + "list";
		}
	}
	
	@GetMapping("edit/{id}")
	public String showEditEventTypeForm(@PathVariable("id") Long id, Model model) {
		EventType eventType = eventTypeController.getEventTypeById(id);
		model.addAttribute("eventType", eventType);
		return EDIT_EVENT_TYPE_URL;
	}
	
	@PostMapping("update/{id}")
	public String saveEditedEventTypeForm(@Valid EventType eventType, BindingResult result, @PathVariable("id") Long id, Model model) {
		if (result.hasErrors()) {
			log.error("Error updating EventType: " + (result.getFieldErrors().stream().map(e->e.getDefaultMessage()).collect(Collectors.joining(","))));
			eventType.setId(id);
			return EDIT_EVENT_TYPE_URL;
		}
		eventTypeController.updateEventType(id, eventType);
		return "redirect:" + MVC_EVENT_TYPE_URL_BASE + "list";
	}
	
	/**
	 * Helper method for testing data
	 */
	private void addEventTypes() {
		EventType et1 = new EventType(-1l, "Basic Event", 500);
		EventType et2 = new EventType(-1l, "Gold Wedding", 7628);
		EventType et3 = new EventType(-1l, "Pet Mini", 25);

		eventTypeController.addEventType(et1);
		eventTypeController.addEventType(et2);
		eventTypeController.addEventType(et3);
	}
}
