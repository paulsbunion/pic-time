package com.defrainPhoto.pictime.controller.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.defrainPhoto.pictime.controller.LocationController;
import com.defrainPhoto.pictime.dto.LocationDTO;
import com.defrainPhoto.pictime.model.Location;

@Controller
@RequestMapping("/mvc/locations/")
public class LocationMVCController {
	
	private final String MVC_LOCATION_URL_BASE = "/mvc/locations/";
	private final String LIST_LOCATIONS_URL = "location/list-locations";
	private final String EDIT_LOCATION_URL = "location/edit-location";
	private final String NEW_LOCATION_URL = "location/new-location";
	private final String SHOW_LOCATION_URL = "location/show-location";
	
	private final static List<String> states = new ArrayList<String>();
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	LocationController locationController;
	
	public LocationMVCController() {
		populateStateMap();
	}
	
	@GetMapping("list")
	public String listAllLocations(Model model) {
		log.info("MVC user calling get all locations");
		List<LocationDTO> allLocations = locationController.getAllLocations();
//		while (allLocations == null || allLocations.isEmpty()) {
//			log.info("Addind Dummy Location Data for testing");
//			addLocations();
//			allLocations = locationController.getAllLocations();
//		}
		model.addAttribute("locations", allLocations);
		return LIST_LOCATIONS_URL;
	}
	
	@GetMapping("new")
	public String newLocationForm(Model model) {
		log.info("MVC user creating new Location");
		model.addAttribute("location", new Location());
		model.addAttribute("states", states);
		return NEW_LOCATION_URL;
	}
	
	@PostMapping("save")
	public String saveNewLocation(@Valid Location location, BindingResult result, Model model) {
		log.info("MVC user saving new Location");
		if (result.hasErrors()) {
			log.error("Error(s) saving new Location: " + result.getAllErrors());
			model.addAttribute("states", states);
			return NEW_LOCATION_URL;
		}
		// validate unique
		if (!locationController.isUnique(location)) {
			log.error("Error(s) saving new Location: Location already exists");
			result.addError(new FieldError("location", "location", "Location already exists: " + location.printLocationWithDescription()));
			model.addAttribute("states", states);
			return NEW_LOCATION_URL;
		}
		locationController.addLocation(location);
		return "redirect:" + MVC_LOCATION_URL_BASE + "list";
	}
	
	@GetMapping("show/{id}")
	public String showLocationDetails(@PathVariable("id") Long id, Model model) {
		log.info("MVC user calling show location details for ID: " + id);
		Location location = locationController.getLocationById(id);
		model.addAttribute("states", states);
		model.addAttribute("location", location);
		return SHOW_LOCATION_URL;
	}
	
	@GetMapping("edit/{id}")
	public String showLocationEditForm(@PathVariable("id") Long id, Model model) {
		log.info("MVC user editing existing Location with ID: " + id);
		Location location = locationController.getLocationById(id);
		model.addAttribute("location", location);
		model.addAttribute("states", states);
		return EDIT_LOCATION_URL;
	}
	
	@PostMapping("update/{id}")
	public String saveUpdatedLocation(@PathVariable("id") Long id, @Valid Location location, BindingResult result, Model model) {
		log.info("MVC user saving updated Location with ID: " + id);
		if (result.hasErrors()) {
			log.error("Error saving Location changes: " + result.getAllErrors());
			model.addAttribute("states", states);
			return EDIT_LOCATION_URL;
		}
		else {
			// validate unique
			if (!locationController.isUnique(location)) {
				log.error("Error(s) Updating Location:  updated Location already exists");
				result.addError(new FieldError("location", "location", "Location already exists: " + location.printLocationWithDescription()));
				model.addAttribute("states", states);
				return EDIT_LOCATION_URL;
			}
			
			locationController.updateLocation(id, location);
			return "redirect:" + MVC_LOCATION_URL_BASE + "list";
		}
	}
	
	@GetMapping("delete/{id}")
	public String deleteClient(@PathVariable("id") long id) {
		log.info("entering delete Location controller from Location MVC");
		try {
			locationController.deleteLocationByID(id);
		}
		catch (EmptyResultDataAccessException e) {
			log.error("Error occured in calling delete Location by ID, Empty Result for ID: " + id, e);
		}
		return "redirect:" + MVC_LOCATION_URL_BASE + "list";
	}
	
	/**
	 * Helper method to add dummy data for testing
	 */
	private void addLocations() {
		Location loc1 = new Location(1l, "New York", "NY", "45671-4444", "35 Ben Ave.", "");
		Location loc2 = new Location(2l, "CityVille", "KY", "12345", "4 Main Street", "The Big City");
		
		locationController.addLocation(loc1);
		locationController.addLocation(loc2);
	}
	
	
	public static List<String> getStatesList() {
		return states;
	}
	private void populateStateMap() {
		states.add("OH");
		states.add("AL");
		states.add("AK");
		states.add("AZ");
		states.add("AR");
		states.add("CA");
		states.add("CO");
		states.add("CT");
		states.add("DE");
		states.add("FL");
		states.add("GA");
		states.add("HI");
		states.add("ID");
		states.add("IL");
		states.add("IN");
		states.add("IA");
		states.add("KS");
		states.add("KY");
		states.add("LA");
		states.add("ME");
		states.add("MD");
		states.add("MA");
		states.add("MI");
		states.add("MN");
		states.add("MS");
		states.add("MO");
		states.add("MT");
		states.add("NE");
		states.add("NV");
		states.add("NH");
		states.add("NJ");
		states.add("NM");
		states.add("NY");
		states.add("NC");
		states.add("ND");
		states.add("OH");
		states.add("OK");
		states.add("OR");
		states.add("PA");
		states.add("RI");
		states.add("SC");
		states.add("SD");
		states.add("TN");
		states.add("TX");
		states.add("UT");
		states.add("VT");
		states.add("VA");
		states.add("WA");
		states.add("WV");
		states.add("WI");
		states.add("WY");
	}
}
