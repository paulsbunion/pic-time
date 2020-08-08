package com.defrainPhoto.pictime.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@IdClass(LocationDistancePK.class)
public class LocationDistance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -770841172800478068L;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE) // if parent location deleted, remove all child
	private Location startLocation;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Location endLocation;
	
	private String miles;

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
		return Objects.hash(getStartLocation(), getEndLocation(), getMiles());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
        if (!(obj instanceof LocationDistance)) return false;
        LocationDistance temp = (LocationDistance) obj;
        return Objects.equals(getStartLocation(), temp.getStartLocation()) &&
                Objects.equals(getEndLocation(), temp.getEndLocation()) &&
                Objects.equals(getMiles(), temp.getMiles());
	}
}
