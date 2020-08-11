package com.defrainPhoto.pictime.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.defrainPhoto.pictime.constraint.City;
import com.defrainPhoto.pictime.constraint.State;
import com.defrainPhoto.pictime.constraint.Street;
import com.defrainPhoto.pictime.constraint.Zipcode;

@Entity
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private long id;
	@City
	@NotEmpty
	@NotNull
	private String city;
	@State
	@NotEmpty
	@NotNull
	private String state;
	@Zipcode
	@NotEmpty
	@NotNull
	private String zip;
	@Street
	@NotEmpty
	@NotNull
	private String street;
	private String description;
	
	public Location() {
		
	}
	

	public Location(long id, String city, String state, String zip, String street, String description) {
		super();
		this.id = id;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.street = street;
		this.description = description;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
