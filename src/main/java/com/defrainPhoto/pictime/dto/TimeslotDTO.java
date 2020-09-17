package com.defrainPhoto.pictime.dto;

import java.util.Set;

import com.defrainPhoto.pictime.model.Client;
import com.defrainPhoto.pictime.model.EventTime;
import com.defrainPhoto.pictime.model.Location;
import com.defrainPhoto.pictime.model.User;

public interface TimeslotDTO {

	long getId();

	EventTime getTime();

	String getTitle();

	String getNotes();

	Client getClient();

	Location getLocation();

	Set<User> getPhotographers();

}