package com.defrainPhoto.pictime.service;

import java.util.List;

import com.defrainPhoto.pictime.model.EventType;

public interface EventTypeService {

	List<EventType> getAllEventTypes();

	EventType addEventType(EventType eventType);

	EventType updateEventTypeById(EventType eventType);

	EventType getEventType(EventType eventType);

	EventType getEventTypeById(Long id);

	void deleteEventTypeById(Long id);

}
