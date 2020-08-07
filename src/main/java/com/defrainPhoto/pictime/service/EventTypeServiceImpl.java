package com.defrainPhoto.pictime.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.defrainPhoto.pictime.model.EventType;
import com.defrainPhoto.pictime.repository.EventTypeRepository;

@Service
public class EventTypeServiceImpl implements EventTypeService {

	@Autowired
	private EventTypeRepository eventTypeRepository;
	
	@Override
	public List<EventType> getAllEventTypes() {
//		return eventTypeRepository.findAll();
		return eventTypeRepository.findByOrderByNameAsc();
	}

	@Override
	public EventType addEventType(EventType eventType) {
		eventType = eventTypeRepository.save(eventType);
		return eventType;
	}

	@Override
	public EventType updateEventTypeById(EventType eventType) {
		return eventTypeRepository.save(eventType);
	}

	@Override
	public EventType getEventType(EventType eventType) {
		return eventTypeRepository.findByName(eventType.getName());
	}

}
