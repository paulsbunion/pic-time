package com.defrainPhoto.pictime.service;

import java.util.List;

import com.defrainPhoto.pictime.model.Location;

public interface LocationService {
	
	public List<Location> getAllLocations();
	
	public Location getLocationById(Long id);
	
	public Location addLocation(Location location);
	
	public Location updateLocationById(Location location);
	
	public void deleteLocationById(Long id);
}
