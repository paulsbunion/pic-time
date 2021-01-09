package com.defrainPhoto.pictime.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//@JsonIdentityReference(alwaysAsId = true)
@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	private String eventName;

	private LocalDate date;

	@ManyToOne
	private EventType eventType;

//	@JsonBackReference
//	@JsonIgnore
	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Timeslot> timeslots = new ArrayList<Timeslot>();

	private String extraCost;
	private String notes;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "event")
	private Mileage mileage;

//	@JsonBackReference
//	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "event_user", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> photographers;

	public Event() {
	}

	public Event(Long id, String eventName, LocalDate date, EventType eventType) {
		super();
		this.id = id;
		this.eventName = eventName;
		this.date = date;
		this.eventType = eventType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
		timeslots.add(timeslot);
		timeslot.setEvent(this);
	}

	public void removeTimeslot(Timeslot timeslot) {
		timeslots.remove(timeslot);
		timeslot.setEvent(null);
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

	public Set<User> getPhotographers() {
		return photographers;
	}

	public void setPhotographers(Set<User> photographers) {
		this.photographers = photographers;
	}

	public void addPhotographer(User newPhotographer) {
		if (this.photographers == null) {
			photographers = new HashSet<User>();
		}
		this.photographers.add(newPhotographer);
		newPhotographer.getEvents().add(this);
	}

	public void removePhotographer(User photographer) {
		photographers.remove(photographer);
		photographer.getEvents().remove(this);
	}

	@Override
	public String toString() {
		return "eventId=" + id;
	}

	@Override
	public int hashCode() {
		return 37;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Event))
			return false;

		return id != null && id.equals(((Event) o).getId());

	}

}
