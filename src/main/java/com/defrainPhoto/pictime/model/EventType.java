package com.defrainPhoto.pictime.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EventType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String type;
	private int baseCost;
	
	public EventType() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getBaseCost() {
		return baseCost;
	}

	public void setBaseCost(int baseCost) {
		this.baseCost = baseCost;
	}
	
}
