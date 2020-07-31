package com.defrainPhoto.pictime.model;

import java.util.Objects;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.defrainPhoto.pictime.constraint.PhoneNumber;

@Entity
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;
	@NotNull
	@NotEmpty
	private String firstName;
	@NotNull
	@NotEmpty
	private String lastName;
	@NotNull
	@NotEmpty
	private String address;
	@PhoneNumber
	private String phoneNumber;
	@Email
	private String email;
	private boolean autoRemind;
	//private Set<Event> events;
	
	public Client() {}
	
	public Client(Long id, String firstName, String lastName, String address, String phoneNumber, String email,
			boolean autoRemind) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.autoRemind = autoRemind;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isAutoRemind() {
		return autoRemind;
	}
	public void setAutoRemind(boolean autoRemind) {
		this.autoRemind = autoRemind;
	}

	@Override
	public int hashCode() {
		return 13;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj.getClass() == Optional.class) {
        	Optional opt = (Optional)obj;
        	obj = opt.get();
        }
        if (getClass() != obj.getClass())
            return false;
        Client other = (Client) obj;
        return id != null && id.equals(other.getId());
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address
				+ ", phoneNumber=" + phoneNumber + ", email=" + email + ", autoRemind=" + autoRemind + "]";
	}
	
	
	
}
