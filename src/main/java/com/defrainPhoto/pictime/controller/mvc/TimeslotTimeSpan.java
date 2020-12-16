package com.defrainPhoto.pictime.controller.mvc;

import com.defrainPhoto.pictime.model.EventTime;

public class TimeslotTimeSpan {

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
		int startTimeHour = (int) eventTime.getstartHour();
		int startTimeMinute = (int) eventTime.getstartMinute();
		int endHour = (int) eventTime.getEndHour();
		int endMinute =(int) eventTime.getEndMinute();
						
//		System.out.println("DURATION: " + eventTime.getTotalMinutes());
//		System.out.println("endtimed: " + eventTime.getEndTime());
//		System.out.println(startTimeHour + ":" + startTimeMinute);
//		System.out.println(endHour+ ":" + endMinute);
		start = startTimeHour * 4 + startTimeMinute / 15 + 1;
		stop = endHour * 4 + endMinute / 15;
//		System.out.println("*************************************************************************");
//		System.out.println(start);
//		System.out.println(stop);
//		System.out.println(stop - start);
		
		return new TimeslotTimeSpan(start, stop + 1);
	}
}
