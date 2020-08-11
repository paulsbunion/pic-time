package com.defrainPhoto.pictime.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.defrainPhoto.pictime.model.Location;
import com.defrainPhoto.pictime.repository.LocationRepository;

public class LocationServiceImpl implements LocationService {

	@Autowired
	LocationRepository locationRepository;

	@Override
	public List<Location> getAllLocations() {
		return locationRepository.findAll();
	}

	@Override
	public Location getLocationById(Long id) {
		return locationRepository.findById(id).get();
	}

	@Override
	public Location saveLocation(Location location) {
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

}
