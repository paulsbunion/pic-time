package com.defrainPhoto.pictime.model;

import java.time.LocalTime;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Embeddable
public class EventTime {

	private LocalTime startTime;
	private LocalTime endTime;
	
	public EventTime() {}
	
	public EventTime(LocalTime startTime, LocalTime endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	
	public String printStartTime() {
		return printTime(startTime);
	}
	
	public String printEndTime() {
		return printTime(endTime);
	}
	
	public String printTime(LocalTime time) {
		String ampm = "AM";
		String startMin = "" + time.getMinute();
		if (startMin.length() < 2) {
			startMin = "0" + startMin;
		}
		
		long stHr = time.getHour();
		if (stHr >= 12) {
			stHr %= 12;
			ampm = "PM";
		}
		
		if (stHr == 0) {
			stHr = 12;
		}
				
		return stHr + ":" + startMin + " " +  ampm; 
	}
	
	public String printStartTimeMilitary() {
		return printTimeMilitary(startTime);
	}
	
	public String printEndTimeMilitary() {
		return printTimeMilitary(endTime);
	}
	
	public String printTimeMilitary(LocalTime time) {
		String endMin = "" + time.getMinute();
		if (endMin.length() < 2) {
			endMin = "0" + endMin;
		}
		
		long endHr = time.getHour();
				
		return endHr + ":" + endMin; 
	}
	
	public String printStartEnd() {
		return printStartTime() + "-" + printEndTime();
	}
	
}
