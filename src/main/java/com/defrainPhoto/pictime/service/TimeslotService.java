package com.defrainPhoto.pictime.service;

import java.util.List;

import com.defrainPhoto.pictime.dto.TimeslotDTO;
import com.defrainPhoto.pictime.model.Timeslot;
import com.defrainPhoto.pictime.model.User;

public interface TimeslotService {
	public List<TimeslotDTO> getAllTimeslots();

	public List<Timeslot> getAllTimeslotsByEventId(Long id);

	public List<TimeslotDTO> findAllTimeslotsByUserId(Long userID);

	public void addTimeslot(Timeslot timeslot);

	public Timeslot findTimeslotById(long id);

	public void changePhotographer(Timeslot timeslot, User photographerTwo, User photographerThree);

	public Timeslot updateTimeslot(Timeslot updatedTimeslot);

	public void deleteTimeslot(long timeslotId);
}
