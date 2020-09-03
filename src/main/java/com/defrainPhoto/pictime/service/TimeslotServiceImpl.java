package com.defrainPhoto.pictime.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.defrainPhoto.pictime.dto.TimeslotDTO;
import com.defrainPhoto.pictime.model.Timeslot;
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
		return timeslotRepository.findAllByPhotographersUserId(id);
	}

	@Override
	public List<Timeslot> getAllTimeslotsByTimelineId(Long id) {
		return timeslotRepository.findAllByTimelineEventId(id);
	}

	@Override
	public void addTimeslot(Timeslot timeslot) {
		timeslotRepository.save(timeslot);
	}

	@Override
	public Object findTimeslotById(long id) {
		return timeslotRepository.findById(id);
	}

}
