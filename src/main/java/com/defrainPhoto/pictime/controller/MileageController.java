package com.defrainPhoto.pictime.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.defrainPhoto.pictime.model.Event;
import com.defrainPhoto.pictime.model.UserEventMileage;
import com.defrainPhoto.pictime.model.Location;
import com.defrainPhoto.pictime.model.LocationDistance;
import com.defrainPhoto.pictime.model.Timeslot;
import com.defrainPhoto.pictime.service.LocationDistanceService;

@RestController
@RequestMapping("/mileage/")
public class MileageController {
	
	@Autowired
	LocationDistanceService locationDistanceService;
	
	@Autowired
	PhotographerController photographerController;
	
	@GetMapping("{year}/{user}")
	public List<UserEventMileage> getYearMileageForUser(@PathVariable("year") int year, @PathVariable("user") Long user) {
		List<Event> allEventsForYear = photographerController.getAllEventsForPhotographerByYear(user, year);
		return null;
		
		// just need to get the mileage for each matching event.
		// the event mileage will be updated only when needed by the timeslot updates/changes on save.
		// could do as a database trigger, but how to call google maps api? after change, update a field to update mileage?
		// async call.  for now just update on server whenever a client makes a change.
		// i.e., the user adds a new destination and then saves timeline. this save calls the mileage api to update 
		// any new locationdistances and then recalulate the total distance for the event.  
		// when a user asks for the total mileage for the year, then the database only needs one select to get all milege
		// entries for that year.
		
		/*Long totalDistanceForYear = locationDistanceService.getAllLocationDistances()
		Location userHome = getUserHome();
		Location lastLocation = userHome;
		Location currentLocation = userHome;
		Long totalMilege = 0l;
		
		for (Event e : allEventsForYear) {
			List<Timeslot> eventTimeslots = e.getTimeslots();
			if (eventTimeslots.size() > 0) {
				
				for (Timeslot timeslot : eventTimeslots) {
					Location tempLocation = timeslot.getLocation();
					if (tempLocation != null && tempLocation != currentLocation)) {
						lastLocation = currentLocation;
						currentLocation = tempLocation;
						
						totalMilege += updateDistance(lastLocation, currentLocation);
					}
				}
			}
		}
		
		allEventsForYear.stream().filter(e -> e.getTimeslots().size() > 0).peek(s -> s.getTimeslots().stream().filter(ts -> ts.isTrackMileage())
				);
		 */	
	}

	private Long updateDistance(Location lastLocation, Location currentLocation) {
		long total = 0;
		
		if (lastLocation.getId() > currentLocation.getId()) {
			Location temp = lastLocation;
			lastLocation = currentLocation;
			currentLocation = temp;
		}
		
//		LocationDistance locationDistance = Location
		
		return total;
	}

}
