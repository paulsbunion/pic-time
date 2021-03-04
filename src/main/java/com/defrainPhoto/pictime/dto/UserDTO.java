package com.defrainPhoto.pictime.dto;

import java.util.Objects;

public class UserDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;

//	@JsonBackReference
//	private Set<Event> events = new HashSet<Event>();

//	private Set<Timeslot> timeslots = new HashSet<Timeslot>();

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

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		boolean test = Objects.equals(id, other.getId());
		return Objects.equals(id, other.getId());
	}

	@Override
	public String toString() {
		return "{\"id\":" + id + ", \"firstName\":\"" + firstName + "\", \"lastName\":\"" + lastName + "\"}";
	}

//	public Set<Event> getEvents() {
//		return events;
//	}
//
//	public void setEvents(Set<Event> events) {
//		this.events = events;
//	}
//
//	public Set<Timeslot> getTimeslots() {
//		return timeslots;
//	}
//
//	public void setTimeslots(Set<Timeslot> timeslots) {
//		this.timeslots = timeslots;
//	}
//	
	
}
