package com.defrainPhoto.pictime.controller;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.defrainPhoto.pictime.dto.LocationDTO;
import com.defrainPhoto.pictime.model.Location;
import com.defrainPhoto.pictime.model.UniqueLocation;
import com.defrainPhoto.pictime.service.LocationService;

@RestController()
@RequestMapping("/locations")
public class LocationController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	LocationService locationService;

	@PostMapping
	public Location addLocation(@Valid @RequestBody Location location) {
		log.info("Location REST controller adding new Location");
		return locationService.addLocation(location);
	}

	@GetMapping
	public List<LocationDTO> getAllLocations() {
		log.info("Location REST controller getting all Locations");
		return locationService.getAllLocations();
	}
	
	@GetMapping("/search")
	public List<LocationDTO> doAutoComplete(@RequestParam("q") String input) {
		log.info("Location REST controller autocompleting find locations");
		return locationService.doAutoComplete(input);
	}
	
//	@GetMapping("/search")
//	public Page<LocationDTO> doAutoComplete(@RequestParam("q") String input, Pageable pageable) {
//		log.info("Location REST controller autocompleting find locations");
//		return locationService.doAutoComplete(input, pageable);
//	}

	@GetMapping("/{id}")
	public Location getLocationById(@PathVariable("id") Long id) {
		log.info("Location REST controller getting Location with ID: " + id);
		return locationService.getLocationById(id);
	}
	
	@PutMapping("/{id}")
	public Location updateLocation(@PathVariable("id") Long id, @Valid @RequestBody Location location) {
		log.info("Location REST controller updating Location with ID: " + id);
		Location oldLocation = locationService.getLocationById(id);
		if (oldLocation == null) {
			log.error("No Location found with ID: " + id);
			return null;
		}
		else {
			return locationService.updateLocationById(location);
		}
	}
	
	@DeleteMapping("/{id}")
	public void deleteLocationByID(@PathVariable("id") Long id) {
		log.info("Deleting Location with ID: " + id);
		try {
			locationService.deleteLocationById(id);
		}
		catch (EmptyResultDataAccessException e) {
			log.error("Error occured in calling delete Location by ID, Empty Result for ID: " + id, e);
		}
	}

	public UniqueLocation isUniqueWithExistingLocation(@Valid Location location) {
		boolean unique = true;
		Location result = locationService.existsByLocation(location);
		// if same location being updated, allow desc / zip changes
		if (result != null && location.getId() != result.getId()) {
			unique = false;
		}
		return new UniqueLocation(result, unique);
	}
}
