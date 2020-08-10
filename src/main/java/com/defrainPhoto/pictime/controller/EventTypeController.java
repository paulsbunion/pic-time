package com.defrainPhoto.pictime.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.defrainPhoto.pictime.model.EventType;
import com.defrainPhoto.pictime.service.EventTypeService;

@RestController
@RequestMapping("/event-types")
public class EventTypeController {
	
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EventTypeService eventTypeService;

	@GetMapping
	public List<EventType> getAllEventTypes() {
		log.info("EventType REST controller getting all EventTypes");
		return eventTypeService.getAllEventTypes(); 
	}

	@PostMapping
	public EventType addEventType(@Valid @RequestBody EventType eventType) {
		log.info("EventType REST controller adding new EventType");
		return eventTypeService.addEventType(eventType);
	}

	@GetMapping("/{id}")
	public EventType getEventTypeById(@PathVariable("id") Long id) {
		log.info("EventType REST controller getting EventType with ID: " + id);
		return eventTypeService.getEventTypeById(id);
	}

	@PutMapping("/{id}")
	public EventType updateEventType(@PathVariable("id") long id, @Valid @RequestBody EventType eventDetails) {
		log.info("EventType REST controller updating EventType with ID: " + id);
		EventType oldEventType = eventTypeService.getEventTypeById(id);
		if(oldEventType == null) {
			log.error("No EventType found with ID: " + id);
			return null;
		}
		
		return eventTypeService.updateEventTypeById(eventDetails);
	}

	public void deleteEventypeById(Long id) {
		log.info("Deleting EventType with ID: " + id);
		try {
			eventTypeService.deleteEventTypeById(id);
		}
		catch (EmptyResultDataAccessException e) {
			log.error("Error occured in calling delete EvenType by ID, Empty Result for ID: " + id, e);
		}
	}

}
