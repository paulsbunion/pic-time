package com.defrainPhoto.pictime.dto;

import java.util.Optional;

import com.defrainPhoto.pictime.model.Location;

public class LocationDTOImpl implements LocationDTO {

	private Long id;
	private String city;
	private String state;
	private String zipcode;
	private String street;
	private String description;

	public LocationDTOImpl() {

	}

	public LocationDTOImpl(Long id, String city, String state, String zip, String street, String description) {
		this.id = id;
		this.city = city;
		this.state = state;
		this.zipcode = zip;
		this.street = street;
		this.description = description;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zip) {
		this.zipcode = zip;
	}

	@Override
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return 23;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj.getClass() == Optional.class) {
			@SuppressWarnings("rawtypes")
			Optional opt = (Optional) obj;
			obj = opt.get();
		}
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		return id != null && id.equals(other.getId());
	}
}