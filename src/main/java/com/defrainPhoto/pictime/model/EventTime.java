package com.defrainPhoto.pictime.model;

import javax.persistence.Embeddable;

@Embeddable
public class EventTime {

	private long startTime;
	private long totalMinutes;
	private long endTime;
	
	public EventTime() {}
	
	public EventTime(long startTime, long totalMinutes) {
		this.startTime = startTime;
		this.totalMinutes = totalMinutes;
		calculateEndTime();
	}

	private void calculateEndTime() {
		int startTimeHour = (int)getstartHour();
		int startTimeMinute = (int) getstartMinute();
		int endHour = startTimeHour + (int) getDurationHour();
		int endMinute = startTimeMinute + (int) getDurationMinute();
		
		if (endMinute > 60) {
			endHour++;
			endMinute -= 60;
		}
		
		this.endTime = endHour*100 + endMinute;
	}

	/**
	 * get the start time of the event in  military time, where 0 is midnight, 100 is 1:00 am,
	 * 1200 is noon, 2120 is 9:30 pm etc.
	 * @return the military time as a long ex. 1315 is 1:15 pm.
	 */
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	public long getEndTime() {
		if (endTime <= 0) {
			calculateEndTime();
		}
		return endTime;
	}
	
	public long getEndHour() {
		if (endTime <= 0) {
			calculateEndTime();
		}
		return endTime / 100;
	}
	
	public long getEndMinute() {
		if (endTime <= 0) {
			calculateEndTime();
		}
		return endTime % 100;
	}

	/**
	 * Get the total time in minutes
	 * @return the time represented as minutes
	 */
	public long getTotalMinutes() {
		return totalMinutes;
	}
	
	public long getstartHour() {
		return startTime / 100;
	}
	
	public long getstartMinute() {
		return startTime % 100;
	}
	
	public long getDurationHour() {
		return totalMinutes / 60;
	}
	
	public long getDurationMinute() {
		return totalMinutes % 60;
	}
	
	public void setTotalMinutes(long minutes) {
		this.totalMinutes = minutes;
		calculateEndTime();
	}
	
	public void setTotalMinutes(long hours, long minutes) {
		this.totalMinutes = hours * 60 + minutes;
		calculateEndTime();
	}
	
	public String printStartTime() {
		String ampm = "AM";
		String startMin = "" + getstartMinute();
		if (startMin.length() < 2) {
			startMin = "0" + startMin;
		}
		
		long stHr = getstartHour();
		if (stHr > 12) {
			stHr %= 12;
			ampm = "PM";
		}
		
		if (stHr == 0) {
			stHr = 12;
		}
				
		return stHr + ":" + startMin + " " +  ampm; 
	}
	
	public String printStartTimeMilitary() {
		String startMin = "" + getstartMinute();
		if (startMin.length() < 2) {
			startMin = "0" + startMin;
		}
		
		long stHr = getstartHour();
						
		return stHr + ":" + startMin; 
	}
	
	public String printEndTime() {
		String ampm = "AM";
		String endMin = "" + getEndMinute();
		if (endMin.length() < 2) {
			endMin = "0" + endMin;
		}
		
		long endHr = getEndHour();
		if (endHr > 12) {
			endHr %= 12;
			ampm = "PM";
		}
		
		if (endHr == 0) {
			endHr = 12;
		}
				
		return endHr + ":" + endMin + " " +  ampm; 
	}
	
	public String printEndTimeMilitary() {
		String endMin = "" + getEndMinute();
		if (endMin.length() < 2) {
			endMin = "0" + endMin;
		}
		
		long endHr = getEndHour();
				
		return endHr + ":" + endMin; 
	}
	
	public String printStartEnd() {
		return printStartTime() + "-" + printEndTime();
	}
	
}
