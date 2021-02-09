package com.defrainPhoto.pictime.service;

import java.util.List;

import com.defrainPhoto.pictime.dto.LocationDTO;
import com.defrainPhoto.pictime.model.Location;

public interface LocationService {
	
	public List<LocationDTO> getAllLocations();
	
	public Location getLocationById(Long id);
	
	public Location addLocation(Location location);
	
	public Location updateLocationById(Location location);
	
	public void deleteLocationById(Long id);

	public List<LocationDTO> doAutoComplete(String input);
}
