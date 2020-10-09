package com.defrainPhoto.pictime.dto;

import java.util.Set;

import com.defrainPhoto.pictime.model.Client;
import com.defrainPhoto.pictime.model.EventTime;
import com.defrainPhoto.pictime.model.Location;
import com.defrainPhoto.pictime.model.User;

public class TimeslotDTOImpl implements TimeslotDTO {
	private long id;
	private EventTime time;
	private String title;
	private String notes;
	private Client client;
	private Set<User> photographers;
	private Location location;
	private boolean trackMileage;

	public TimeslotDTOImpl() {
		
	}
	
	public TimeslotDTOImpl(long id, EventTime time, String title, String notes, Client client,
			Set<User> photographers, Location location, boolean trackMileage) {
		super();
		this.id = id;
		this.time = time;
		this.title = title;
		this.notes = notes;
		this.client = client;
		this.photographers = photographers;
		this.location = location;
		this.trackMileage = trackMileage;
	}

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
	public Set<User> getPhotographers() {
		return photographers;
	}

	public void setPhotographers(Set<User> photographers) {
		this.photographers = photographers;
	}

	public boolean isTrackMileage() {
		return trackMileage;
	}

	public void setTrackMileage(boolean trackMileage) {
		this.trackMileage = trackMileage;
	}

	public void addPhotographer(User photographer) {
		photographers.add(photographer);
	}
}
