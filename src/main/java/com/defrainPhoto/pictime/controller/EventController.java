package com.defrainPhoto.pictime.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.defrainPhoto.pictime.model.Event;
import com.defrainPhoto.pictime.model.Timeslot;
import com.defrainPhoto.pictime.service.EventService;

@RestController
@RequestMapping("/events")
public class EventController {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EventService eventService;
	
	@GetMapping("/{id}")
	public Event getEvent(@PathVariable("id") long id) {
		log.info("Event REST controller getting Event with ID: " + id);
		return eventService.findById(id);
	}
	
	@GetMapping
	public List<Event> getAllEvents() {
		log.info("Event REST controller getting all Events");
		return eventService.findAll();
	}
	
	@GetMapping("/user/{id}")
	public List<Event> getAllEventsForUser(@PathVariable("id") long id) {
		log.info("Event REST controller getting all Events for User with ID: " + id);
		return eventService.getAllEventsForPhotographer(id);
	}
	
	@PostMapping
	public Event addEvent(@Valid @RequestBody Event event) {
		log.info("Event REST controller adding new Event");
		return eventService.addEvent(event);
	}
	
	@PutMapping("/{id}")
	public Event updateEvent(@Valid @RequestBody Event updateEvent, @PathVariable(name = "id") Long id) {
		log.info("Event REST controller updating Event with ID: " + updateEvent.getId());
		updateEvent.setId(id);
		return eventService.updateEvent(updateEvent);
		 
	}
	
	@PutMapping("/{eventId}/switchPhotographer/oldPhotographerId/{oldId}/newPhotographerId/{newId}")
	public Event ChangeEventPhotographer(@PathVariable(name = "eventId") Long eventId, @PathVariable(name = "oldId") Long oldId,
			@PathVariable(name = "newId") Long newId) {
		log.info("Event REST controller swapping old photographer {} with new photographer {} for Event {} ",oldId, newId, eventId);
		return eventService.switchPhotographer(eventId, oldId, newId);
		 
	}
	
	@DeleteMapping("/{id}")
	public void deleteEvent(@PathVariable("id") long id) {
		log.info("Event REST controller deleting Event with ID: " + id);
		try {
			eventService.deleteEvent(id);
		}
		catch (EmptyResultDataAccessException e) {
			log.error("Error occured in calling delete Event by ID, Empty Result for ID: " + id, e);
		}
	}
	
	@GetMapping("/{id}/timeslots")
	public List<Timeslot> getAllTimeslots(@PathVariable(name = "id") Long eventId) {
		log.info("Event REST controller getting all timeslots for event{}", eventId);
		return eventService.getAllTimeslots(eventId);
	}
	
	@GetMapping("/{eventId}/timeslots/{timeslotId}")
	public Timeslot getEventTimeslot(@PathVariable(name = "eventId") Long eventId, @PathVariable(name = "timeslotId") Long timeslotId) {
		log.info("Event REST controller getting Timeslot with id {} for event with id {}", timeslotId, eventId);
		return eventService.getTimeslot(timeslotId);
	}
	
	@PostMapping("/{eventId}/timeslots")
	public Timeslot addTimeslot(@PathVariable(name = "eventId") Long eventId, @Valid @RequestBody Timeslot newTimeslot) {
		return eventService.addTimeslot(eventId, newTimeslot);
	}
	
	@PutMapping("/{eventId}/timeslots/{timeslotId}")
	public Timeslot updateTimeslot(@PathVariable(name = "eventId") Long eventId, @PathVariable(name = "eventId") Long timeslotId,
			@Valid @RequestBody Timeslot updatedTimeslot) {
		updatedTimeslot.setId(timeslotId);
		return eventService.updateTimeslot(updatedTimeslot);
	}
	
	@DeleteMapping("/{eventId}/timeslots/{timeslotId}")
	public void DeleteTimeslot(@PathVariable(name = "eventId") Long eventId, @PathVariable(name = "eventId") Long timeslotId) {
		eventService.deleteTimeslot(timeslotId);
	}
}
