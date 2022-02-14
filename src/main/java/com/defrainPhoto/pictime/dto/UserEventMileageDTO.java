package com.defrainPhoto.pictime.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.defrainPhoto.pictime.model.EventUserId;

public class UserEventMileageDTO {
	
	private EventUserId eventUserId;
//	private long EventId;
//	private long UserId;
	
	private String eventName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	private Double totalMiles;
	
	
	public UserEventMileageDTO() {
	}
	
		public UserEventMileageDTO(EventUserId eventUserId, String eventName, LocalDate date, Double totalMiles) {
		this.eventUserId = eventUserId;
		this.eventName = eventName;
		this.date = date;
		this.totalMiles = totalMiles;
	}
	
//	public UserEventMileageDTO(long eventId, long userId, String eventName, LocalDate date, Long totalMiles) {
//		EventId = eventId;
//		UserId = userId;
//		this.eventName = eventName;
//		this.date = date;
//		this.totalMiles = totalMiles;
//	}


//	public long getEventId() {
//		return EventId;
//	}
//	public void setEventId(long eventId) {
//		EventId = eventId;
//	}
//	public long getUserId() {
//		return UserId;
//	}
//	public void setUserId(long userId) {
//		UserId = userId;
//	}
	public String getEventName() {
		return eventName;
	}
	public EventUserId getEventUserId() {
		return eventUserId;
	}

	public void setEventUserId(EventUserId eventUserId) {
		this.eventUserId = eventUserId;
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
	public Double getTotalMiles() {
		return totalMiles;
	}
	public void setTotalMiles(Double totalMiles) {
		this.totalMiles = totalMiles;
	}

	@Override
	public String toString() {
		return "UserEventMileageDTO [eventUserId=" + eventUserId + ", eventName=" + eventName + ", date=" + date
				+ ", totalMiles=" + totalMiles + "]";
	}
	
	
}
