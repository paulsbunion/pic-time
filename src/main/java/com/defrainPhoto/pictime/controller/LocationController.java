package com.defrainPhoto.pictime.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.defrainPhoto.pictime.model.Location;
import com.defrainPhoto.pictime.service.LocationService;

@RestController("/locations/")
public class LocationController {

	@Autowired
	LocationService locationService;
	
	@PostMapping
	public Location addLocation(@Valid @RequestBody Location location) {
		return locationService.addLocation(location);
	}
}
