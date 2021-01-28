package com.defrainPhoto.pictime.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.defrainPhoto.pictime.model.EventType;
import com.defrainPhoto.pictime.model.Mileage;
import com.defrainPhoto.pictime.model.Timeslot;
import com.defrainPhoto.pictime.model.User;

public class EventDTO {

	private Long id;
	private String eventName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	private EventType eventType;

	private List<Timeslot> timeslots;
	private String extraCost;
	private String notes;

	private Mileage mileage;

	private Set<UserDTO> photographers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public List<Timeslot> getTimeslots() {
		return timeslots;
	}

	public void setTimeslots(List<Timeslot> timeslots) {
		this.timeslots = timeslots;
	}

	public String getExtraCost() {
		return extraCost;
	}

	public void setExtraCost(String extraCost) {
		this.extraCost = extraCost;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Mileage getMileage() {
		return mileage;
	}

	public void setMileage(Mileage mileage) {
		this.mileage = mileage;
	}

	public Set<UserDTO> getPhotographers() {
		return photographers;
	}

	public void setPhotographers(Set<UserDTO> photographers) {
		this.photographers = photographers;
	}
	
	
}
