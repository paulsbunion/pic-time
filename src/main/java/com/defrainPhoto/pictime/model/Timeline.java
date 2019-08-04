package com.defrainPhoto.pictime.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class Timeline {
	@Id
	private long eventId;
	private EventTime eventTime;
	private LocalDate eventDate;
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private Event event;

	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public EventTime getEventTime() {
		return eventTime;
	}

	public void setEventTime(EventTime eventTime) {
		this.eventTime = eventTime;
	}
	
	public LocalDate getEventDate() {
		return eventDate;
	}
	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
}
