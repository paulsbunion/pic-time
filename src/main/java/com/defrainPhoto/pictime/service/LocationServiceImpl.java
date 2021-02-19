package com.defrainPhoto.pictime.service;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

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
		if (result != null && result.size() > 10) {
			result.subList(10, result.size()).clear();;
		}
		return result;
	}

	@Override
	public boolean existsByLocation(@Valid Location location) {
		List<Location> result = locationRepository.existsByLocation(location);
		if (result == null) {
			result = new LinkedList<Location>();
		}
		return result.size() == 0 ? true : false;
	}
	
//	@Override
//	public Page<LocationDTO> doAutoComplete(String input, Pageable pageable) {
//		Page<LocationDTO> result = locationRepository.findAllByInputString(input, pageable);
//		return result;
//	}

}
