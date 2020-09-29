package com.defrainPhoto.pictime.service;

import java.util.List;

import com.defrainPhoto.pictime.model.Event;
import com.defrainPhoto.pictime.model.Timeslot;
import com.defrainPhoto.pictime.model.User;

public interface EventService {

	public List<Event> findAll();

	public Event findById(long id);

	public Event addEvent(Event event);

	public void deleteEvent(long id);

	public Event addPhotographer(long eventId, User photographer);

	public Event removePhotographer(long eventId, User photographer);

	public Event switchPhotographer(long eventId, User photographerOne, User photographerTwo);

	public Event addTimeslot(long eventId, Timeslot timeslot);

	public Event deleteTimeslot(long eventId, long timeslotId);

	public Event updateEvent(Event event);

}
