package com.defrainPhoto.pictime.controller.mvc;

import java.time.LocalTime;

import com.defrainPhoto.pictime.model.EventTime;

public class TimeslotTimeSpan {

	private static final int TIMESLOT_OVERFLOW_MINUTE = 6;
	private int startRow;
	private int stopRow;
	private int span;
	
	public TimeslotTimeSpan(int start, int stop) {
		this.startRow = start;
		this.stopRow = stop;
		this.span = stop - start;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getStopRow() {
		return stopRow;
	}

	public void setStopRow(int stopRow) {
		this.stopRow = stopRow;
	}
	

	public int getSpan() {
		return span;
	}

	public void setSpan(int span) {
		this.span = span;
	}

	static TimeslotTimeSpan mapTimeToGridSpan(EventTime eventTime) {
		int start = -1;
		int stop = -1;
		int startTimeHour = eventTime.getStartTime().getHour();
		int startTimeMinute = (int) eventTime.getStartTime().getMinute();
		LocalTime endTime = eventTime.getEndTime();
		int endHour = 23;
		int endMinute = 0;
		if (endTime == null) {
			
		}
		else {
			endHour = (int) eventTime.getEndTime().getHour();
			endMinute =(int) eventTime.getEndTime().getMinute();
		}
						
//		System.out.println("DURATION: " + eventTime.getTotalMinutes());
//		System.out.println("endtimed: " + eventTime.getEndTime());
//		System.out.println(startTimeHour + ":" + startTimeMinute);
//		System.out.println(endHour+ ":" + endMinute);
		start = startTimeHour * 4 + startTimeMinute / 15 + 1;
		stop = endHour * 4 + endMinute / 15;
		// adjust for partial quadrant
		if (endMinute % 15 > TIMESLOT_OVERFLOW_MINUTE) {
			stop++;
		}
//		System.out.println("*************************************************************************");
//		System.out.println(start);
//		System.out.println(stop);
//		System.out.println(stop - start);
		
		return new TimeslotTimeSpan(start, stop + 1);
	}
}
