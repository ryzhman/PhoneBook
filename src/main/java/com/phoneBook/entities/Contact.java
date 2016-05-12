package com.phoneBook.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Олександр
 * 
 * Class represents domain Contact class, which includes all required fields for fulfilling Phonebook functionality.
 * 
 * Two constructors are available: default and with all (except Set<Contact>) information provided
 *
 */

@Entity
@Table(name="contacts")
public class Contact implements Serializable {
	private static final long serialVersionUID = -4579530641278060737L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="contact_id")
	private int id;
	
	@Column(name="contact_surname", nullable = false, length = 20)
	private String surname;
	
	@Column(name="contact_name", nullable = false, length = 20)
	private String name;
	
	@Column(name="contact_patname", nullable = false, length = 20)
	private String patName;
	
	@Column(name="contact_mobtel", nullable = false, length = 12)
	private String telNumberMob;
	
	@Column(name="contact_hometel", length = 12)
	private String telNumberHome;
	
	@Column(name="contact_address", length = 30)
	private String address;
	
	@Column(name="contact_email", length = 20)
	private String email;
	
	@Column(name="contact_is_available", nullable = false)
	private Boolean isAvailable = true;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User ownerUser;
	
	public Contact(){}
	
	public Contact(String surname, String name, String patName, String mobTel, String homeTel, String address, String email, User owner){
		this.surname = surname;
		this.name = name;
		this.patName = patName;
		this.telNumberMob = mobTel;
		this.telNumberHome = homeTel;
		this.address = address;
		this.email = email;
		this.ownerUser=owner;
	}
	
	
	public int getId() {
		return id;
	}

	public String getSurname() {
		return surname;
	}

	public String getName() {
		return name;
	}
	
	public String getPatName() {
		return patName;
	}

	public String getTelNumberMob() {
		return telNumberMob;
	}

	public String getTelNumberHome() {
		return telNumberHome;
	}

	public String getAddress() {
		return address;
	}

	public String getEmail() {
		return email;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPatName(String patName) {
		this.patName = patName;
	}

	public void setTelNumberMob(String telNumberMob) {
		this.telNumberMob = telNumberMob;
	}

	public void setTelNumberHome(String telNumberHome) {
		this.telNumberHome = telNumberHome;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Override
	public String toString() {
		return "Contact id - " + id + ", surname - " + surname + ", name - " + name + ", patName - " + patName
				+ ", telNumberMob - " + telNumberMob + ", telNumberHome - " + telNumberHome + ", address - " + address
				+ ", email - " + email + ", isAvailable - " + isAvailable + ", ownerUser - " + ownerUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((isAvailable == null) ? 0 : isAvailable.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((ownerUser == null) ? 0 : ownerUser.hashCode());
		result = prime * result + ((patName == null) ? 0 : patName.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result + ((telNumberHome == null) ? 0 : telNumberHome.hashCode());
		result = prime * result + ((telNumberMob == null) ? 0 : telNumberMob.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (isAvailable == null) {
			if (other.isAvailable != null)
				return false;
		} else if (!isAvailable.equals(other.isAvailable))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ownerUser == null) {
			if (other.ownerUser != null)
				return false;
		} else if (!ownerUser.equals(other.ownerUser))
			return false;
		if (patName == null) {
			if (other.patName != null)
				return false;
		} else if (!patName.equals(other.patName))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		if (telNumberHome == null) {
			if (other.telNumberHome != null)
				return false;
		} else if (!telNumberHome.equals(other.telNumberHome))
			return false;
		if (telNumberMob == null) {
			if (other.telNumberMob != null)
				return false;
		} else if (!telNumberMob.equals(other.telNumberMob))
			return false;
		return true;
	}
}
