package com.defrainPhoto.pictime.controller.mvc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.defrainPhoto.pictime.controller.LocationController;
import com.defrainPhoto.pictime.model.Client;
import com.defrainPhoto.pictime.model.Location;

@Controller
@RequestMapping("/mvc/locations/")
public class LocationMVCController {
	
	private final String MVC_LOCATION_URL_BASE = "/mvc/locations/";
	private final String LIST_LOCATIONS_URL = "location/list-locations";
	private final String EDIT_LOCATION_URL = "location/edit-location";
	private final String NEW_LOCATION_URL = "location/new-location";
	private final String SHOW_LOCATION_URL = "location/show-locaiton";
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	LocationController locationController;
	
	@GetMapping("list")
	public String listAllLocations(Model model) {
		log.info("MVC user calling get all locations");
		List<Location> allLocations = locationController.getAllLocations();
		while (allLocations == null || allLocations.isEmpty()) {
			log.info("Addind Dummy Location Data for testing");
			addLocations();
			allLocations = locationController.getAllLocations();
		}
		model.addAttribute("locations", allLocations);
		return LIST_LOCATIONS_URL;
	}

	private void addLocations() {
		Location loc1 = new Location(1l, "New York", "NY", "45671-4444", "35 Ben Ave.", "");
		Location loc2 = new Location(2l, "CityVille", "KY", "12345", "4 Main Street", "The Big City");
		
		locationController.addLocation(loc1);
		locationController.addLocation(loc2);
	}
}
