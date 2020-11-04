package com.defrainPhoto.pictime.dto;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class MonthDTO {

	private int[] daysInEveryMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private int daysInMonth;
	private int weekStartDay;
	private String monthName;
	private int year;
	private int month;
	private int day;
	private String dayName;

	private int previousMonthLink;
	private int nextMonthLink;
	private int previousYearLink;
	private int nextYearLink;
	private int previousDay;
	private int nextDay;
	
	public MonthDTO() {
	}

	public MonthDTO(int year, int month) {
		this(year, month, 1);
	}

	public MonthDTO(Integer year, Integer month, Integer day) {
		LocalDate date = LocalDate.of(year, month, day);
		daysInMonth = daysInEveryMonth[month - 1];
		weekStartDay = date.getDayOfWeek().getValue();
		monthName = date.getMonth().getDisplayName(TextStyle.FULL, Locale.US);
//		monthName = date.getMonth().toString();
		this.year = date.getYear();
		this.month = date.getMonth().getValue();
		this.day = date.getDayOfMonth();
		this.dayName = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
//		this.dayName = date.getDayOfWeek().toString();

		int tempYear = year;
		int tempMonth = month;

		if (month == 1) {
			tempYear = year - 1;
			tempMonth = 12;
		} else {
			tempYear = year;
			tempMonth = month - 1;
		}
		previousYearLink = tempYear;
		previousMonthLink = tempMonth;

		if (month == 12) {
			tempYear = year + 1;
			tempMonth = 1;
		} else {
			tempYear = year;
			tempMonth = month + 1;
		}
		nextYearLink = tempYear;
		nextMonthLink = tempMonth;
	}

	public int getDaysInMonth() {
		return daysInMonth;
	}

	public void setDaysInMonth(int daysInMonth) {
		this.daysInMonth = daysInMonth;
	}

	public int getWeekStartDay() {
		return weekStartDay;
	}

	public void setWeekStartDay(int weekStartDay) {
		this.weekStartDay = weekStartDay;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}
	
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getDayName() {
		return dayName;
	}

	public void setDayName(String dayName) {
		this.dayName = dayName;
	}

	public int getPreviousMonthLink() {
		return previousMonthLink;
	}

	public void setPreviousMonthLink(int previousMonthLink) {
		this.previousMonthLink = previousMonthLink;
	}

	public int getNextMonthLink() {
		return nextMonthLink;
	}

	public void setNextMonthLink(int nextMonthLink) {
		this.nextMonthLink = nextMonthLink;
	}

	public int getPreviousYearLink() {
		return previousYearLink;
	}

	public void setPreviousYearLink(int previousYearLink) {
		this.previousYearLink = previousYearLink;
	}

	public int getNextYearLink() {
		return nextYearLink;
	}

	public void setNextYearLink(int nextYearLink) {
		this.nextYearLink = nextYearLink;
	}

	public int getPreviousDay() {
		return previousDay;
	}

	public void setPreviousDay(int previousDay) {
		this.previousDay = previousDay;
	}

	public int getNextDay() {
		return nextDay;
	}

	public void setNextDay(int nextDay) {
		this.nextDay = nextDay;
	}

}
