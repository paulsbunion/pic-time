package com.defrainPhoto.pictime.dto;

import java.util.Set;

import com.defrainPhoto.pictime.model.Client;
import com.defrainPhoto.pictime.model.EventTime;
import com.defrainPhoto.pictime.model.EventUser;
import com.defrainPhoto.pictime.model.Location;
import com.defrainPhoto.pictime.model.Timeline;

public interface TimeslotDTO {

	long getId();

	EventTime getTime();

	Timeline getTimeline();

	String getTitle();

	String getNotes();

	Client getClient();

	Location getLocation();

	Set<EventUser> getPhotographers();

}