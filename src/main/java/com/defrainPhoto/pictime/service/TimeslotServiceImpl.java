package com.defrainPhoto.pictime.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.defrainPhoto.pictime.dto.TimeslotDTO;
import com.defrainPhoto.pictime.exception.ResourceNotFoundException;
import com.defrainPhoto.pictime.model.Timeslot;
import com.defrainPhoto.pictime.model.User;
import com.defrainPhoto.pictime.repository.TimeslotRepository;

@Service
public class TimeslotServiceImpl implements TimeslotService {

	@Autowired
	TimeslotRepository timeslotRepository;
	
	@Override
	public List<TimeslotDTO> getAllTimeslots() {
		return timeslotRepository.findAllBy();
	}
	
	@Override
	public List<TimeslotDTO> findAllTimeslotsByUserId(Long id) {
		return timeslotRepository.findAllByPhotographersId(id);
	}

	@Override
	public List<Timeslot> getAllTimeslotsByEventId(Long id) {
//		return timeslotRepository.findAllByEventId(id);
		return timeslotRepository.findAllByEventIdOrderByTimeStartTime(id);
	}

	@Override
	public void addTimeslot(Timeslot timeslot) {
		timeslotRepository.save(timeslot);
	}

	@Override
	public Timeslot findTimeslotById(long id) {
		return timeslotRepository.findById(id).get();
	}

	@Override
	@Transactional
	public void changePhotographer(Timeslot timeslot, User oldPhotographer, User newPhotographer) {
		timeslot.getPhotographers().remove(oldPhotographer);
		timeslot.getPhotographers().add(newPhotographer);
	}

	@Override
	@Transactional
	public Timeslot updateTimeslot(Timeslot updatedTimeslot) {
		Optional<Timeslot> foundTimeslot = timeslotRepository.findById(updatedTimeslot.getId());
		if (foundTimeslot.isPresent()) {
			Timeslot timeslotUpdate = foundTimeslot.get();
			timeslotUpdate.setClient(updatedTimeslot.getClient());
			timeslotUpdate.setEvent(updatedTimeslot.getEvent());
			timeslotUpdate.setLocation(updatedTimeslot.getLocation());
			timeslotUpdate.setNotes(updatedTimeslot.getNotes());
			timeslotUpdate.setPhotographers(updatedTimeslot.getPhotographers());
			timeslotUpdate.setTime(updatedTimeslot.getTime());
			timeslotUpdate.setTitle(updatedTimeslot.getTitle());
			timeslotUpdate.setTrackMileage(updatedTimeslot.isTrackMileage());
			
			return timeslotRepository.save(timeslotUpdate);
		} else {
			throw new ResourceNotFoundException("Timeslot not found with id: " + updatedTimeslot.getId());
		}
	}

	@Override
	public void deleteTimeslot(long timeslotId) {
		try {
			Timeslot foundTimeslot = timeslotRepository.findById(timeslotId).get();
			timeslotRepository.delete(foundTimeslot);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}

}
