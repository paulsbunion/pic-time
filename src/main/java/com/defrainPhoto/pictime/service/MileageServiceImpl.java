package com.defrainPhoto.pictime.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.defrainPhoto.pictime.dto.UserEventMileageDTO;
import com.defrainPhoto.pictime.model.Event;
import com.defrainPhoto.pictime.model.EventUserId;
import com.defrainPhoto.pictime.model.Location;
import com.defrainPhoto.pictime.model.Timeslot;
import com.defrainPhoto.pictime.model.User;
import com.defrainPhoto.pictime.model.UserEventMileage;
import com.defrainPhoto.pictime.repository.UserEventMileageRepository;

@Service
public class MileageServiceImpl implements MileageService {

	@Autowired
	UserEventMileageRepository eventMileageRepository;
	
	@Autowired
	EventService eventService;
	
	@Override
	public List<UserEventMileageDTO> getAllByUserId(Long id) {
		// get all events for the user
		
		// for each event, get all the timeslots
		// when a location changes, get the distance between each location
		// sum, then create userEventMileage
		
		// return list of userEventMileage
		
		List<UserEventMileageDTO> uevm = new ArrayList<UserEventMileageDTO>();
		
		
		List<Event> allUserEvents = eventService.getAllEventsForPhotographer(id); // change to just get event ids!!!!!!!!
		if (allUserEvents != null) {
			User thisUserMock = new User();
			thisUserMock.setId(id);
			
			for (Event e : allUserEvents) {
				double eventMileage = 0.0;
				
				uevm.add(new UserEventMileageDTO(new EventUserId(e.getId(), id), e.getEventName(), e.getDate(), eventMileage));
				// get timeslots
				List<Timeslot> eventTimeslots = eventService.getAllTimeslots(e.getId());
				
				Location lastLocation = null;
				Location currentLocation = null;
				Location tempLocation = null;
				
				for (int i = 0; i < eventTimeslots.size(); i++) {
					Timeslot ts = eventTimeslots.get(i);
					boolean isUserAtLocation = ts.getPhotographers().contains(thisUserMock);
					boolean isTrackMileage = ts.isTrackMileage();
					
					if (isTrackMileage) {
						tempLocation = ts.getLocation();
						
						if (isUserAtLocation) {
							// if last location same, do nothing
							// otherwise if last location null, update last location
							// else calculate distance
						}
						else {
							// if last location was null, do nothing
							// else if last location was not null, 
						}
					}
					
				}
			}
		}
		
		
		// old way to get events for testing/ manually added data
//		List<UserEventMileageDTO> uevm = eventMileageRepository.findAllByEventUserIdUserId(id);
//		if (uevm == null) {
//			System.out.println("its a null list!");
//		}
//		else {
//			System.out.println("It's not a null list with size: " + uevm.size());
//			uevm.stream().forEach(System.out::println);
//		}
		
		
		// test dummy::::
//		UserEventMileage userEventMileage = new UserEventMileage();
//		EventUserId eventUserId = new EventUserId();
//		eventUserId.setEventId(1);
//		eventUserId.setUserId(id);
//		
//		userEventMileage.setEventUserId(eventUserId);
//		userEventMileage.setTotalMiles(100);
//		userEventMileage.setYear(LocalDate.of(2021, 1, 12));
//		UserEventMileageDTO uevmdto = new ModelMapper().map(userEventMileage,UserEventMileageDTO.class);
//		
//		return List.of(uevmdto);
		
		return uevm;
	}

	@Override
	public List<UserEventMileage> findAllByUserIdAndYear(Long id, int year) {
		// TODO Auto-generated method stub
		return null;
	}

}
