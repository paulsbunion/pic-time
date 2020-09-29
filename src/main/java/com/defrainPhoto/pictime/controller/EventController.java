package com.defrainPhoto.pictime.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@PostMapping
	public Event addEvent(@Valid @RequestBody Event event) {
		log.info("Event REST controller adding new Event");
		return eventService.addEvent(event);
	}
}
