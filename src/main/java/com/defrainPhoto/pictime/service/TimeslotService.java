package com.defrainPhoto.pictime.service;

import java.util.List;

import com.defrainPhoto.pictime.dto.TimeslotDTO;
import com.defrainPhoto.pictime.model.Timeslot;

public interface TimeslotService {
	public List<TimeslotDTO> getAllTimeslots();

	public List<Timeslot> getAllTimeslotsByEventId(Long id);

	public List<TimeslotDTO> findAllTimeslotsByUserId(Long userID);

	public void addTimeslot(Timeslot timeslot);

	public Object findTimeslotById(long l);
}
