package com.defrainPhoto.pictime.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Timeslot {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private long id;
	
	private EventTime time;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Event event;
	
	private String title;
	
	private String notes;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Client client;
	
	@ManyToMany
//	@JoinTable(joinColumns = @JoinColumn(name = "time_slot_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
//	@JoinColumn(name = "timeline_event_id")
	private Set<EventUser> photographers;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Location location;
	private boolean trackMileage;
	public long getId() {
		return id;
	}
	
	public Timeslot() {
		
	}
	
	public Timeslot(long id, EventTime time, Event event, String title, String notes, Client client,
			Set<EventUser> photographers, Location location, boolean trackMileage) {
		super();
		this.id = id;
		this.time = time;
		this.event = event;
		this.title = title;
		this.notes = notes;
		this.client = client;
		this.photographers = photographers;
		this.location = location;
		this.trackMileage = trackMileage;
	}

	public void setId(long id) {
		this.id = id;
	}
	public EventTime getTime() {
		return time;
	}
	public void setTime(EventTime time) {
		this.time = time;
	}
	
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String heading) {
		this.title = heading;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
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
		//photographer.setEvent(timeline.getEvent());
	}
}
