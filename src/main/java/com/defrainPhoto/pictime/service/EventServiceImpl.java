package com.defrainPhoto.pictime.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.defrainPhoto.pictime.model.Event;
import com.defrainPhoto.pictime.model.Timeslot;
import com.defrainPhoto.pictime.model.User;
import com.defrainPhoto.pictime.repository.EventRepository;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	TimeslotService timeslotService;

	@Autowired
	EventRepository eventRepository;

	@Override
	public List<Event> findAll() {
		return eventRepository.findAll();
	}

	@Override
	public Event findById(long id) {
		return eventRepository.findById(id).get();
	}

	@Override
	@Transactional
	public void switchPhotographer(long eventId, User oldPhotographer, User newPhotographer) {
		if (oldPhotographer.equals(newPhotographer)) {
			return;
		}
		Optional<Event> event = eventRepository.findById(eventId);
		if (event.isPresent()) {
			Event foundEvent = event.get();

			if (!foundEvent.getPhotographers().contains(newPhotographer)) {
				foundEvent.addPhotographer(newPhotographer);
			} else {
			}

			foundEvent.getTimeslots().stream().filter(timeslot -> timeslot.getPhotographers().contains(oldPhotographer))
					.forEach(timeslot -> {
//						timeslot.addPhotographer(newPhotographer);
//						timeslot.removePhotographer(oldPhotographer);
						timeslotService.changePhotographer(timeslot, oldPhotographer, newPhotographer);
					});
			foundEvent.removePhotographer(oldPhotographer);
		}
	}

	@Override
	public void addEvent(Event event) {
		eventRepository.save(event);
	}

	@Override
	@Transactional
	public void addPhotographer(long eventId, User newPhotographer) {
		Event foundEvent = findById(eventId);
		if (foundEvent != null) {
			foundEvent.addPhotographer(newPhotographer);
		}
	}

	@Override
	public void addTimeslot(Long id, Timeslot newTimeslot) {
		Event foundEvent = findById(id);
		if (foundEvent != null) {
			foundEvent.addTimeslot(newTimeslot);
		}
	}

}
