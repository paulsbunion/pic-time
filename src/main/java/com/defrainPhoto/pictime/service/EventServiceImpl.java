package com.defrainPhoto.pictime.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.defrainPhoto.pictime.exception.ResourceNotFoundException;
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
	
	@Autowired
	UserService userService;

	@Override
	public List<Event> findAll() {
		return eventRepository.findAllByOrderByDateDesc();
	}

	@Override
	public Event findById(long id) {
		return eventRepository.findById(id).get();
	}

	@Override
	@Transactional
	public Event switchPhotographer(long eventId, Long oldPhotographerId, Long newPhotographerId) {
		
		Optional<Event> event = eventRepository.findById(eventId);
		if (event.isPresent()) {
			Event foundEvent = event.get();
			User oldPhotographer = foundEvent.getPhotographers().stream().filter(p -> p.getId() == oldPhotographerId).findFirst().orElse(null);
			User newPhotographer = userService.findById(newPhotographerId);
			
			if (oldPhotographer.equals(newPhotographer)) {
				return foundEvent;
			}

			if (!foundEvent.getPhotographers().contains(newPhotographer)) {
				foundEvent.addPhotographer(newPhotographer);
			} else {
			}

			foundEvent.getTimeslots().stream().filter(timeslot -> timeslot.getPhotographers().contains(oldPhotographer))
					.forEach(timeslot -> {
						timeslotService.changePhotographer(timeslot, oldPhotographer, newPhotographer);
					});
			foundEvent.removePhotographer(oldPhotographer);
			return foundEvent;
		}
		throw new ResourceNotFoundException("Event not found with id: " + eventId);
	}

	@Override
	@Transactional
	public Event addEvent(Event event) {
		return eventRepository.save(event);
	}

	@Override
	@Transactional
	public Event addPhotographer(long eventId, User newPhotographer) {
		Event foundEvent = findById(eventId);
		if (foundEvent != null) {
			foundEvent.addPhotographer(newPhotographer);
			return foundEvent;
		}
		throw new ResourceNotFoundException("Event not found with id: " + eventId);
	}

	// The new timeslot should have id set, then returned
	
	@Override
	@Transactional
	public Timeslot addTimeslot(long eventId, Timeslot newTimeslot) {
		Event foundEvent = findById(eventId);
		if (foundEvent != null) {
//			newTimeslot.setEvent(foundEvent);
			foundEvent.addTimeslot(newTimeslot);
			eventRepository.save(foundEvent);
//			Long newId = null;
//			Optional<Timeslot> addedTimeslot =  foundEvent.getTimeslots().stream().filter(ts -> ts.equals(newTimeslot)).findFirst();
//			if (addedTimeslot.isPresent()) {
//				newId = addedTimeslot.get().getId();
//			}
//			newTimeslot.setId(newId);
			return newTimeslot;
		}
		throw new ResourceNotFoundException("Event not found with id: " + eventId);
	}

	@Override
	public void deleteEvent(long id) {
		Optional<Event> foundEvent = eventRepository.findById(id);
		if (foundEvent.isPresent()) {
			eventRepository.delete(foundEvent.get());
		}
	}

	@Override
	@Transactional
	public Event removePhotographer(long eventId, User photographer) {
		Optional<Event> foundEvent = eventRepository.findById(eventId);
		if (foundEvent.isPresent()) {
			Event eventToUpdate = foundEvent.get();
			eventToUpdate.removePhotographer(photographer);

			eventToUpdate.getTimeslots().stream().filter(timeslot -> timeslot.getPhotographers().contains(photographer))
					.forEach(timeslot -> timeslot.removePhotographer(photographer));

			return eventToUpdate;
		}
		throw new ResourceNotFoundException("Event not found with id: " + eventId);
	}

	@Override
	public Event updateEvent(Event event) {
		Optional<Event> foundEvent = this.eventRepository.findById(event.getId());
		if (foundEvent.isPresent()) {
			Event eventUpdate = foundEvent.get();
			eventUpdate.setDate(event.getDate());
			eventUpdate.setEventName(event.getEventName());
			eventUpdate.setEventType(event.getEventType());
			eventUpdate.setExtraCost(event.getExtraCost());
			eventUpdate.setId(event.getId());
			eventUpdate.setMileage(event.getMileage());
			eventUpdate.setNotes(event.getNotes());
			eventUpdate.setPhotographers(event.getPhotographers());
			eventUpdate.setTimeslots(event.getTimeslots());
			eventRepository.save(eventUpdate);
			return eventUpdate;
		} else {
			throw new ResourceNotFoundException("Event not found with id: " + event.getId());
		}
	}

	@Override
	public List<Event> getAllEventsForPhotographer(long id) {
		return eventRepository.findAllByPhotographersId(id);
	}

	@Override
	public List<Timeslot> getAllTimeslots(long eventId) {
		return timeslotService.getAllTimeslotsByEventId(eventId);
	}

	@Override
	public Timeslot getTimeslot(Long timeslotId) {
		return timeslotService.findTimeslotById(timeslotId);
	}

	@Override
	@Transactional
	public Timeslot updateTimeslot(Timeslot updatedTimeslot) {
		return timeslotService.updateTimeslot(updatedTimeslot);
	}
	
	@Override
	@Transactional
	public void deleteTimeslot(long timeslotId) {
//		Event foundEvent = eventRepository.findById(eventId).get();
//		if (foundEvent != null) {
//			Timeslot foundTimeslot = timeslotService.findTimeslotById(timeslotId);
//			foundEvent.removeTimeslot(foundTimeslot);
//			return foundEvent;
//		}
//		throw new ResourceNotFoundException("Event not found with id: " + eventId);
		timeslotService.deleteTimeslot(timeslotId);
	}

	@Override
	public List<Event> findAllByYearAndMonth(Integer year, Integer month) {
		LocalDate fromDate = LocalDate.of(year, month, 1);
		
		if (month == 12) {
			year += 1;
			month = 1;
		}
		else {
			month++;
		}
		LocalDate toDate = LocalDate.of(year, month, 1);
		return eventRepository.findAllByDateGreaterThanEqualAndDateLessThan(fromDate, toDate);
	}

	@Override
	public List<Event> findAllByYearAndMonthAndDay(Integer year, Integer month, Integer day) {
		LocalDate date = LocalDate.of(year, month, day);
		return eventRepository.findAllByDate(date);
	}

}
