package com.defrainPhoto.pictime.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private long id;
	private String eventName;
	
	@ManyToOne
	private EventType eventType;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "event")
	private Timeline timeline;
	
	private String extraCost;
	private String notes;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "event")
	private Mileage mileage;
//	@ManyToMany(mappedBy = "event")
//	@JoinTable(name = "event_users")
//	private Set<User> users;
	@OneToMany(mappedBy = "eventUserId", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EventUser> eventUsers = new ArrayList<EventUser>();
	
	public Event() {}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	public Timeline getTimeline() {
		return timeline;
	}
	public void setTimeline(Timeline timeline) {
		this.timeline = timeline;
		timeline.setEvent(this);
	}
	public String getExtraCost() {
		return extraCost;
	}
	public void setExtraCost(String extraCost) {
		this.extraCost = extraCost;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Mileage getMileage() {
		return mileage;
	}
	public void setMileage(Mileage mileage) {
		this.mileage = mileage;
	}
		
	public void removeTimeline(Timeline timeline) {
		timeline.setEvent(null);
	}

	public void addEventUser(EventUser eventUser) {
		this.eventUsers.add(eventUser);
		eventUser.setEvent(this);
	}
}
