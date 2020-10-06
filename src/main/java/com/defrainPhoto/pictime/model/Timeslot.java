package com.defrainPhoto.pictime.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(scope = Timeslot.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Timeslot {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	private EventTime time;

//	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Event event;

	private String title;

	private String notes;

	@ManyToOne(fetch = FetchType.LAZY)
	private Client client;

	@ManyToMany
	@JoinTable(name = "timeslot_user", joinColumns = @JoinColumn(name = "timeslot_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> photographers = new HashSet<User>();

	@ManyToOne(fetch = FetchType.LAZY)
	private Location location;
	private boolean trackMileage;

	public long getId() {
		return id;
	}

	public Timeslot() {

	}

	public Timeslot(Long id, EventTime time, Event event, String title, String notes, Client client,
			Set<User> photographers, Location location, boolean trackMileage) {
		super();
		this.id = id;
		this.time = time;
		this.event = event;
		this.title = title;
		this.notes = notes;
		this.client = client;
		if (photographers != null) {
			this.photographers = photographers;
		}
		this.location = location;
		this.trackMileage = trackMileage;
	}

	public void setId(Long id) {
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

	public void addPhotographer(User newPhotographer) {
		if (this.photographers == null) {
			photographers = new HashSet<User>();
		}
		this.photographers.add(newPhotographer);
		newPhotographer.getTimeslots().add(this);
	}
	
	public void removePhotographer(User photographer) {
		photographers.remove(photographer);
		photographer.getTimeslots().remove(this);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Timeslot)) return false;
		return id != null && id.equals(((Timeslot) obj).getId());
	}
	
	
}
