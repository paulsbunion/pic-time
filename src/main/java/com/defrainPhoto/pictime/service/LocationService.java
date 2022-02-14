package com.defrainPhoto.pictime.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.defrainPhoto.pictime.dto.LocationDTO;
import com.defrainPhoto.pictime.model.Location;
import com.defrainPhoto.pictime.model.LocationDistance;

public interface LocationService {
	
	public List<LocationDTO> getAllLocations();
	
	public Location getLocationById(Long id);
	
	public Location addLocation(Location location);
	
	public Location updateLocationById(Location location);
	
	public void deleteLocationById(Long id);

	public List<LocationDTO> doAutoComplete(String input);

	public Location existsByLocation(@Valid Location location);
	
//	public Page<LocationDTO> doAutoComplete(String input, Pageable pageable);
	
}
