package com.defrainPhoto.pictime.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.defrainPhoto.pictime.model.EventType;
import com.defrainPhoto.pictime.repository.EventTypeRepository;

public class EventTypeServiceImpl implements EventTypeService {

	@Autowired
	private EventTypeRepository eventTypeRepository;
	
	@Override
	public List<EventType> getAllEventTypes() {
		return eventTypeRepository.findAll();
	}

}
