package com.defrainPhoto.pictime.service;

import java.util.List;

import com.defrainPhoto.pictime.dto.UserEventMileageDTO;
import com.defrainPhoto.pictime.model.UserEventMileage;

public interface MileageService {
	public List<UserEventMileageDTO> getAllByUserId(Long id);
	
	public List<UserEventMileage> findAllByUserIdAndYear(Long id, int year);
}
