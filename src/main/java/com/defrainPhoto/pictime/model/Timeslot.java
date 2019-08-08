package com.defrainPhoto.pictime.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	private Timeline timeline;
	private String heading;
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
	public void setId(long id) {
		this.id = id;
	}
	public EventTime getTime() {
		return time;
	}
	public void setTime(EventTime time) {
		this.time = time;
	}
	public Timeline getTimeline() {
		return timeline;
	}
	public void setTimeline(Timeline timeline) {
		this.timeline = timeline;
	}
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
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
