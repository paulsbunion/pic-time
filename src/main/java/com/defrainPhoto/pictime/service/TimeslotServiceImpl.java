package com.defrainPhoto.pictime.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.defrainPhoto.pictime.dto.TimeslotDTO;
import com.defrainPhoto.pictime.repository.TimeslotRepository;

@Service
public class TimeslotServiceImpl implements TimeslotService {

	@Autowired
	TimeslotRepository timeslotRepository;
	
	@Override
	public List<TimeslotDTO> getAllTimeslots() {
		return timeslotRepository.findAllBy();
	}

}
