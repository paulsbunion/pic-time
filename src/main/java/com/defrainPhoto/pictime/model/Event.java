package com.defrainPhoto.pictime.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

	private LocalDate date;

	@ManyToOne
	private EventType eventType;

	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Timeslot> timeslots = new ArrayList<Timeslot>();

	private String extraCost;
	private String notes;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "event")
	private Mileage mileage;
//	@ManyToMany(mappedBy = "event")
//	@JoinTable(name = "event_users")
//	private Set<User> users;
	@OneToMany(mappedBy = "eventUserId", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EventUser> eventUsers = new ArrayList<EventUser>();

	public Event() {
	}

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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public List<Timeslot> getTimeslots() {
		return timeslots;
	}

	public void setTimeslots(List<Timeslot> timeslots) {
		this.timeslots = timeslots;
	}

	public void addTimeslot(Timeslot timeslot) {
		this.timeslots.add(timeslot);
		timeslot.setEvent(this);
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

	public void addEventUser(EventUser eventUser) {
		this.eventUsers.add(eventUser);
		eventUser.setEvent(this);
	}
	

	public List<EventUser> getEventUsers() {
		return eventUsers;
	}

	public void setEventUsers(List<EventUser> eventUsers) {
		this.eventUsers = eventUsers;
	}

	@Override
	public String toString() {
		return "eventId=" + id;
	}

}
