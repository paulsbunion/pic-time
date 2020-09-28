package com.defrainPhoto.pictime.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.defrainPhoto.pictime.dto.TimeslotDTO;
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
		return timeslotRepository.findAllByEventId(id);
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

}
