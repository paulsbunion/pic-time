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

	public Event switchPhotographer(long eventId, Long oldId, Long newId);

	public Timeslot addTimeslot(long eventId, Timeslot timeslot);

	public void deleteTimeslot(long timeslotId);

	public Event updateEvent(Event event);

	public List<Event> getAllEventsForPhotographer(long id);
	
	public List<Event> getAllEventsForPhotographerBYYear(Long id, int year);

	public List<Timeslot> getAllTimeslots(long eventId);

	public Timeslot getTimeslot(Long timeslotId);

	public Timeslot updateTimeslot(Timeslot updatedTimeslot);

	public List<Event> findAllByYearAndMonth(Integer year, Integer month);

	public List<Event> findAllByYearAndMonthAndDay(Integer year, Integer month, Integer day);

	public void removePhotographerFromTimeslots(User p, List<Timeslot> list);

	public void addPhotographerToTimeslots(User p, List<Timeslot> timeslots);

}
