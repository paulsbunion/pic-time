package com.defrainPhoto.pictime.model;

import javax.persistence.Embeddable;

@Embeddable
public class EventTime {

	private long startTime;
	private long totalMinutes;
	
	public EventTime() {}
	
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * Get the total time in minutes
	 * @return the time represented as minutes
	 */
	public long getTotalMinutes() {
		return totalMinutes;
	}
	
	public long getDurationHour() {
		return totalMinutes / 60;
	}
	
	public long getDurationMinute() {
		return totalMinutes % 60;
	}
	
	public void setTotalMinutes(long minutes) {
		this.totalMinutes = minutes;
	}
	
	public void setTotalMinutes(long hours, long minutes) {
		this.totalMinutes = hours * 60 + minutes;
	}
	
}
