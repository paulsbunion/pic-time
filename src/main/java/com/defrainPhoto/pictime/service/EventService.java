package com.defrainPhoto.pictime.service;

import java.util.List;

import com.defrainPhoto.pictime.model.Event;
import com.defrainPhoto.pictime.model.Timeslot;
import com.defrainPhoto.pictime.model.User;

public interface EventService {

	public List<Event> findAll();

	public Event findById(long id);

	public void switchPhotographer(long eventId, User photographerOne, User photographerTwo);

	public void addEvent(Event event);

	public void addPhotographer(long id, User photographerOne);

	public void addTimeslot(Long id, Timeslot timeslotOne);
	
}
