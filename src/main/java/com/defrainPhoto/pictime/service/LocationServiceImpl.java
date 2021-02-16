package com.defrainPhoto.pictime.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.defrainPhoto.pictime.dto.LocationDTO;
import com.defrainPhoto.pictime.model.Location;
import com.defrainPhoto.pictime.repository.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	LocationRepository locationRepository;
	
	@Override
	public List<LocationDTO> getAllLocations() {
		return locationRepository.findAllByOrderByDescriptionAsc();
	}

	@Override
	public Location getLocationById(Long id) {
		return locationRepository.findById(id).get();
	}

	@Override
	public Location addLocation(Location location) {
		return locationRepository.save(location);
	}

	@Override
	public Location updateLocationById(Location location) {
		return locationRepository.save(location);
	}

	@Override
	public void deleteLocationById(Long id) {
		locationRepository.deleteById(id);
	}

	@Override
	public List<LocationDTO> doAutoComplete(String input) {
//		return locationRepository.findByDescriptionContainingIgnoreCase(input);
		List<LocationDTO> result = locationRepository.findAllByInputString(input);
		return result;
	}
	
//	@Override
//	public Page<LocationDTO> doAutoComplete(String input, Pageable pageable) {
//		Page<LocationDTO> result = locationRepository.findAllByInputString(input, pageable);
//		return result;
//	}

}
