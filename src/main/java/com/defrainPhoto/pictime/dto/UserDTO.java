package com.defrainPhoto.pictime.dto;

import java.util.HashSet;
import java.util.Set;

import com.defrainPhoto.pictime.model.Event;
import com.defrainPhoto.pictime.model.Timeslot;

public class UserDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;

//	@JsonBackReference
	private Set<Event> events = new HashSet<Event>();

	private Set<Timeslot> timeslots = new HashSet<Timeslot>();

	public UserDTO() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	public Set<Timeslot> getTimeslots() {
		return timeslots;
	}

	public void setTimeslots(Set<Timeslot> timeslots) {
		this.timeslots = timeslots;
	}
	
	

}
