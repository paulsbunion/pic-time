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
	@NotEmpty(message = "Event Type cannot be Blank")
	@NotNull(message = "Event Type cannot be Null")
	private String type;
	@Min(value = 0, message = "Base Event Cost cannot be negative")
	private int baseCost;

	public EventType() {
	}

	public EventType(Long id, String typeOfEvent, int baseCost) {
		this.id = id;
		this.type = typeOfEvent;
		this.baseCost = baseCost;
	}

	public long getId() {
		return id;
	}

	public void setId(Long id) {
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
			Optional opt = (Optional) obj;
			obj = opt.get();
		}
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return id != null && id.equals(other.getId());
	}

}
