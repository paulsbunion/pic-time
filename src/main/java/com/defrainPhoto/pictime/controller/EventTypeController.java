package com.defrainPhoto.pictime.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.defrainPhoto.pictime.model.Client;
import com.defrainPhoto.pictime.model.EventType;
import com.defrainPhoto.pictime.service.EventTypeService;

@RestController
public class EventTypeController {

	@Autowired
	EventTypeService eventTypeService;

	@GetMapping("/event-types")
	public List<EventType> getAllEventTypes() {
		return eventTypeService.getAllEventTypes(); 
	}

	@PostMapping("/event-types")
	public EventType addEventType(@Valid @RequestBody EventType eventType) {
		return eventTypeService.addEventType(eventType);
	}

	@GetMapping("/event-types/{id}")
	public EventType getEventTypeById(@PathVariable("id") Long id) {
		return eventTypeService.getEventTypeById(id);
	}

	@PutMapping("/event-type/{id}")
	public EventType updateEventType(@PathVariable("id") long id, @Valid @RequestBody EventType eventDetails) {
		EventType oldEventType = eventTypeService.getEventTypeById(id);
		if(oldEventType == null) {
			return null;
		}
		
		return eventTypeService.updateEventTypeById(eventDetails);
	}

	public void deleteEventypeById(Long id) {
		eventTypeService.deleteEventTypeById(id);
	}

}
