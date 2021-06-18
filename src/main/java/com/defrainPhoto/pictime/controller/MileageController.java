package com.defrainPhoto.pictime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mileage/")
public class MileageController {
	
	@Autowired
	LocationController locationController;
	
	@Autowired
	PhotographerController photographerController;
	
	@GetMapping("{year}/{user}")
	public void getYearMileageForUser(@PathVariable("year") int year, @PathVariable("user") Long user) {
		photographerController.getAllEventsForPhotographerByYear(user, year);
	}

}
