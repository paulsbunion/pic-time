package com.defrainPhoto.pictime.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String eventName;
	//private EventType eventType;
	private LocalDate eventDate;
	private long startTime; // start time in minutes (e.x. 5pm = 1020 (17*60))
	private long duration; // duration in minutes
	//private Timeline timeline;
	private String extraCost;
	private String notes;
	@OneToOne(mappedBy = "event")
	private Mileage mileage;
	
	public Event() {}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
//	public EventType getEventType() {
//		return eventType;
//	}
//	public void setEventType(EventType eventType) {
//		this.eventType = eventType;
//	}
	public LocalDate getEventDate() {
		return eventDate;
	}
	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
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
	
}
