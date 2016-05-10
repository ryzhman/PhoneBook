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
	
	@Override
	public String toString() {
		return "Contact id - " + id + ", surname - " + surname + ", name - " + name + ", patName - " + patName
				+ ", telNumberMob - " + telNumberMob + ", telNumberHome - " + telNumberHome + ", address - " + address
				+ ", email - " + email + ", ownerUser - " + ownerUser;
	}
}
