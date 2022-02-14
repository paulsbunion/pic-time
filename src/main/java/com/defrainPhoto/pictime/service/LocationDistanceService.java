package com.defrainPhoto.pictime.service;

import com.defrainPhoto.pictime.model.LocationDistance;

public interface LocationDistanceService {
	
	public LocationDistance getLocationDistanceById(Long startLocationId, Long endLocationId);
	
	public LocationDistance getLocationDistanceByStartLocationEndLocation(Long startLocation, Long endLocation);
	
	public LocationDistance addLocationDistance(LocationDistance locationDistance);
}
