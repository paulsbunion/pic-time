package com.defrainPhoto.pictime.model;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class EventType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;
	@NotEmpty(message = "Invalid name")
	@NotNull(message = "Invalid name")
	private String name;
	@NotNull(message = "Invalid amount")
	@Min(value = 0, message = "Cannot be negatve")
	private Integer baseCost;

	public EventType() {
	}

	public EventType(Long id, String typeOfEvent, Integer baseCost) {
		this.id = id;
		this.name = typeOfEvent;
		this.baseCost = baseCost;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBaseCost() {
		return baseCost;
	}

	public void setBaseCost(Integer baseCost) {
		this.baseCost = baseCost;
	}

	@Override
	public int hashCode() {
		return 11;
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
		EventType other = (EventType) obj;
		return id != null && id.equals(other.getId());
	}

}
