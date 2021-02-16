package com.defrainPhoto.pictime.model;

import java.util.Optional;

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
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Location.class)
@Entity
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;
	@City
	@NotEmpty
	@NotNull(message = "")
	private String city;
	@State
	@NotEmpty
	@NotNull(message = "")
	private String state;
	@Zipcode
	@NotEmpty
	@NotNull(message = "")
	private String zipcode;
	@Street
	@NotEmpty
	@NotNull(message = "")
	private String street;
	@NotEmpty
	@NotNull
	private String description;
	
	public Location() {
		
	}
	

	public Location(Long id, String city, String state, String zip, String street, String description) {
		super();
		this.id = id;
		this.city = city;
		this.state = state;
		this.zipcode = zip;
		this.street = street;
		this.description = description;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zip) {
		this.zipcode = zip;
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
