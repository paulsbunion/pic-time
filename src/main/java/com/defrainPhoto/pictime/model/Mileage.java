package com.defrainPhoto.pictime.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class Mileage {

	@Id
	private long eventId;
	private int year;
	private long totalMiles;
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private Event event;
	
	public Mileage() {}
	
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public long getTotalMiles() {
		return totalMiles;
	}
	public void setTotalMiles(long totalMiles) {
		this.totalMiles = totalMiles;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	
}
