package com.defrainPhoto.pictime.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class EventUserId implements Serializable {
	
	private long EventId;
	private long UserId;

}
