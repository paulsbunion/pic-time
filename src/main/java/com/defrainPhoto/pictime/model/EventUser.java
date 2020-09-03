package com.defrainPhoto.pictime.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
@IdClass(EventUserPK.class)
public class EventUser {

	@Id
	@Column(name = "event_user_id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long eventUserId;
	
	@Id
	@JoinColumn(name = "event_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Event event;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	public Long getEventUserId() {
		return eventUserId;
	}

	public void setEventUserId(Long eventUserId) {
		this.eventUserId = eventUserId;
	}

	@Override
	public String toString() {
		return "EventUser [eventUserId=" + eventUserId + "]";
	}
	
}
