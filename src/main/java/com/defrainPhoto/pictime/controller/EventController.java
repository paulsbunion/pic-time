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

import com.defrainPhoto.pictime.model.Event;
import com.defrainPhoto.pictime.service.EventService;

@RestController
@RequestMapping("/events/")
public class EventController {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EventService eventService;
	
	@GetMapping("{id}")
	public Event getEvent(@PathVariable("id") long id) {
		log.info("Event REST controller getting Event with ID: " + id);
		return eventService.findById(id);
	}
	
	@GetMapping
	public List<Event> getAllEvents() {
		log.info("Event REST controller getting all Events");
		return eventService.findAll();
	}
	
	@GetMapping("user/{id}")
	public List<Event> getAllEventsForUser(@PathVariable("id") long id) {
		log.info("Event REST controller getting all Events for User with ID: " + id);
		return eventService.getAllEventsForPhotographer(id);
	}
	
	@PostMapping
	public Event addEvent(@Valid @RequestBody Event event) {
		log.info("Event REST controller adding new Event");
		return eventService.addEvent(event);
	}
	
	@PutMapping("{id}")
	public void deleteEvent(@PathVariable("id") long id) {
		log.info("Event REST controller deleting Event with ID: " + id);
		try {
			eventService.deleteEvent(id);
		}
		catch (EmptyResultDataAccessException e) {
			log.error("Error occured in calling delete Event by ID, Empty Result for ID: " + id, e);
		}
	}
}
