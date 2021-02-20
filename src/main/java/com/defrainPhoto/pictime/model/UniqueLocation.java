package com.defrainPhoto.pictime.model;

public class UniqueLocation {
	
	private Location location;
	private boolean isUnique;
	
	public UniqueLocation() {}


	public UniqueLocation(Location location, boolean isUnique) {
		this.location = location;
		this.isUnique = isUnique;
	}
	
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public boolean isUnique() {
		return isUnique;
	}
	
	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}
}
