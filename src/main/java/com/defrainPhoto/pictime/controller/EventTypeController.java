package com.defrainPhoto.pictime.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.defrainPhoto.pictime.model.EventType;
import com.defrainPhoto.pictime.service.EventTypeService;

@RestController
public class EventTypeController {

	@Autowired
	EventTypeService eventTypeService;

	@GetMapping("/eventTypes")
	public List<EventType> getAllEventTypes() {
		return eventTypeService.getAllEventTypes();
	}

	@PostMapping("/eventTypes")
	public EventType addEventType(@Valid @RequestBody EventType eventType) {
		return eventTypeService.addEventType(eventType);
	}

}
