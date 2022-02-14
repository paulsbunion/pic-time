
package com.defrainPhoto.pictime.model;

import java.time.LocalDate;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class UserEventMileage {

	//@Id
	//private long eventId;
	//@Id
	//private long userEvent;
	
	@EmbeddedId
	private EventUserId eventUserId;
	
	@DateTimeFormat(pattern = "yyy-MM-dd")
	private LocalDate date;
	
	private Double totalMiles;
	public EventUserId getEventUserId() {
		return eventUserId;
	}
	public void setEventUserId(EventUserId eventUserId) {
		this.eventUserId = eventUserId;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setYear(LocalDate date) {
		this.date = date;
	}
	public Double getTotalMiles() {
		return totalMiles;
	}
	public void setTotalMiles(Double totalMiles) {
		this.totalMiles = totalMiles;
	}
	
	
	//@OneToOne(fetch = FetchType.LAZY)
	//@MapsId
	//private Event event;
	//@MapsId
	//User user;
	
	/*public UserEventMileage() {}
	
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
	}*/
	
}
