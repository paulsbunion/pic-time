package com.defrainPhoto.pictime.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class MonthDTO {

	private int daysInMonth;
	private int weekStartDay;
	private String monthName;
	private int year;
	private int month;
	private int day;
	private String dayName;
	
	private LocalDate today;

	private LocalDate previousDay;
	private LocalDate previousMonth;
	private LocalDate previousYear;
	
	private LocalDate nextDay;
	private LocalDate nextMonth;
	private LocalDate nextYear;
	
	private DateTimeFormatter monthformatter = DateTimeFormatter.ofPattern("YYYY/M");
	private DateTimeFormatter dayformatter = DateTimeFormatter.ofPattern("YYYY/M/d");
	
	
	public MonthDTO() {
	}

	public MonthDTO(int year, int month) {
		this(year, month, 1);
	}

	public MonthDTO(Integer year, Integer month, Integer day) {
		LocalDate date = LocalDate.of(year, month, day);
		today = date;
		
		previousDay = date.minusDays(1);
		previousMonth = date.minusMonths(1);
		previousYear = date.minusYears(1);
		
		nextDay = date.plusDays(1);
		nextMonth= date.plusMonths(1);
		nextYear = date.plusYears(1);
		
		daysInMonth = date.lengthOfMonth();
		weekStartDay = date.getDayOfWeek().getValue();
		monthName = date.getMonth().getDisplayName(TextStyle.FULL, Locale.US);
		this.year = date.getYear();
		this.month = date.getMonth().getValue();
		this.day = date.getDayOfMonth();
		this.dayName = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
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

	public String getPreviousDayLink() {
		return  previousDay.format(dayformatter);
	}
	
	public String getNextDayLink() {
		return  nextDay.format(dayformatter);
	}
	
	public String getPreviousMonthLink() {
		return  previousMonth.format(monthformatter);
	}
	
	public String getNextMonthLink() {
		return  nextMonth.format(monthformatter);
	}
	
	public LocalDate getToday() {
		return today;
	}

	public void setToday(LocalDate today) {
		this.today = today;
	}
}
