package com.defrainPhoto.pictime.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class EventListDTO {
	private Long id;
	private String eventName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
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
	
	
}
