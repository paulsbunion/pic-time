package com.defrainPhoto.pictime.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.defrainPhoto.pictime.model.Event;
import com.defrainPhoto.pictime.repository.EventRepository;

@Service
public class EventServiceImpl implements EventService {
	
	@Autowired
	EventRepository eventRepository;

	@Override
	public List<Event> findAll() {
		return eventRepository.findAll();
	}

}
