package com.defrainPhoto.pictime.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class LocationDistance implements Serializable {

	@Id
	private Long id; // combination of startLocation and endLocation
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Location startLocation;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Location endLocation;
	
	private String miles;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Location getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(Location startLocation) {
		this.startLocation = startLocation;
	}

	public Location getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(Location endLocation) {
		this.endLocation = endLocation;
	}

	public String getMiles() {
		return miles;
	}

	public void setMiles(String miles) {
		this.miles = miles;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getId(), getStartLocation(), getEndLocation(), getMiles());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
        if (!(obj instanceof LocationDistance)) return false;
        LocationDistance temp = (LocationDistance) obj;
        return Objects.equals(getId(), temp.getId()) &&
        		Objects.equals(getStartLocation(), temp.getStartLocation()) &&
                Objects.equals(getEndLocation(), temp.getEndLocation()) &&
                Objects.equals(getMiles(), temp.getMiles());
	}
}
