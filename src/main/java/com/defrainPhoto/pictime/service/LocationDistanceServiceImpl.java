package com.defrainPhoto.pictime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.defrainPhoto.pictime.repository.LocationDistanceRepository;

import com.defrainPhoto.pictime.model.LocationDistance;

@Service
public class LocationDistanceServiceImpl implements LocationDistanceService {
	
	@Autowired
	LocationDistanceRepository locationDistanceRepository;
	
	@Override
	public LocationDistance getLocationDistanceById(Long startLocationId, Long endLocationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocationDistance getLocationDistanceByStartLocationEndLocation(Long startLocation, Long endLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocationDistance addLocationDistance(LocationDistance locationDistance) {
		// TODO Auto-generated method stub
		return null;
	}

}
