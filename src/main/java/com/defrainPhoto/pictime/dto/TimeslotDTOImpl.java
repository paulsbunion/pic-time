package com.defrainPhoto.pictime.dto;

import java.util.Set;

import com.defrainPhoto.pictime.model.Client;
import com.defrainPhoto.pictime.model.EventTime;
import com.defrainPhoto.pictime.model.EventUser;
import com.defrainPhoto.pictime.model.Location;

public class TimeslotDTOImpl implements TimeslotDTO {
	private long id;
	private EventTime time;
	private String title;
	private String notes;
	private Client client;
	private Set<EventUser> photographers;
	private Location location;
	private boolean trackMileage;

	@Override
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public EventTime getTime() {
		return time;
	}

	public void setTime(EventTime time) {
		this.time = time;
	}

	@Override
	public String getTitle() {
		return title;
	}

	public void setTitle(String heading) {
		this.title = heading;
	}

	@Override
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public Set<EventUser> getPhotographers() {
		return photographers;
	}

	public void setPhotographers(Set<EventUser> photographers) {
		this.photographers = photographers;
	}

	public boolean isTrackMileage() {
		return trackMileage;
	}

	public void setTrackMileage(boolean trackMileage) {
		this.trackMileage = trackMileage;
	}

	public void addPhotographer(EventUser photographer) {
		photographers.add(photographer);
		// photographer.setEvent(timeline.getEvent());
	}
}
