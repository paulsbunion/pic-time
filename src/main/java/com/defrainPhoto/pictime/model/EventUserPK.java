package com.defrainPhoto.pictime.model;

import java.io.Serializable;
import java.util.Objects;

public class EventUserPK implements Serializable {

//	private Long eventId;
	private Event event;
	private Long eventUserId;
	
	
	private EventUserPK(){};
	
	public EventUserPK(Event event, Long eventUserId) {
		this.event = event;
//		this.eventId = eventId;
		this.eventUserId = eventUserId;
	}

	public Event getEvent() {
		return event;
	}

	public Long getEventUserId() {
		return eventUserId;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getEvent(), eventUserId);
	}
	
	@Override
	public boolean equals(Object o) {
		 if(this == o) return true;
	        if ( o == null || getClass() != o.getClass()) return false;
	        EventUserPK that = (EventUserPK) o;
	        return Objects.equals(getEvent().getId(), that.getEvent().getId()) &&
	        		Objects.equals(getEventUserId(), that.getEventUserId());
	}
}
