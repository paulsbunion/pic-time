package com.defrainPhoto.pictime.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class EventUserId implements Serializable {
	
	private long eventId;
	private long userId;
	
	public EventUserId() {
	}
	public EventUserId(long eventId, long userId) {
		this.eventId = eventId;
		this.userId = userId;
	}
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		eventId = eventId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		userId = userId;
	}
	@Override
	public String toString() {
		return "EventUserId [eventId=" + eventId + ", userId=" + userId + "]";
	}
	
}
