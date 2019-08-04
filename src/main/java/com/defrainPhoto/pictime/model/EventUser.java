package com.defrainPhoto.pictime.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@IdClass(EventUserPK.class)
public class EventUser {

	@Id
	@Column(name = "event_user_id")
	@GeneratedValue
	private Long eventUserId;
	
	@Id
	@JoinColumn(name = "event_id")
	@ManyToOne
	private Event event;
	
	@ManyToOne
	private User user;
}
