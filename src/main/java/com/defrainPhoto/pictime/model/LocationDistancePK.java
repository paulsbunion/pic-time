package com.defrainPhoto.pictime.model;

import java.io.Serializable;
import java.util.Objects;

public class LocationDistancePK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1761320131142489912L;
	private Location startLocation;
	private Location endLocation;
	
	public LocationDistancePK() {}
	
	public LocationDistancePK(Location startLocation, Location endLocation) {
		this.startLocation = startLocation;
		this.endLocation = endLocation;
	}
	
	
	public Location getStartLocation() {
		return startLocation;
	}

	public Location getEndLocation() {
		return endLocation;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getStartLocation(), getEndLocation());
	}
	
	@Override
	public boolean equals(Object o) {
		 if(this == o) return true;
	        if ( o == null || getClass() != o.getClass()) return false;
	        LocationDistancePK that = (LocationDistancePK) o;
	        return Objects.equals(getStartLocation().getId(), that.getStartLocation().getId()) &&
	        		Objects.equals(getEndLocation(), that.getEndLocation());
	}
}
