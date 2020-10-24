package com.defrainPhoto.pictime.dto;

import java.time.LocalDate;
import java.util.List;

import com.defrainPhoto.pictime.model.Timeslot;

public class CalendarEventDTO {
	private Long id;
	private String eventName;
	private LocalDate date;

	private List<Timeslot> timeslots;

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

	public List<Timeslot> getTimeslots() {
		return timeslots;
	}

	public void setTimeslots(List<Timeslot> timeslots) {
		this.timeslots = timeslots;
	}

	
}

