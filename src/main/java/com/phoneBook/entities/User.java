package com.phoneBook.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * 
 * @author Олександр
 *
 * Domain User class to represent basic entity, which has an access to wep application
 * 
 * Two constructors are available: default and with login, password and fullname arguments.
 * Field contacts is supposed to be added manually, isActive - by default true.
 * 
 */
@Entity
@Table(name="users")
public class User implements Serializable{
	private static final long serialVersionUID = -3168790065852717102L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="user_login", length=10)
	private String login;
	
	@Column(name="user_pass", length=15)
	private String password;
	
	@Column(name="user_fullname", length=30)
	private String fullname;
	
	@OneToMany(mappedBy="ownerUser", cascade=CascadeType.ALL)
	private Set<Contact> contacts;
	
	/*
	 * Flag to mark whether a user active or deleted
	 */
	@Column(name="user_active")
	private Boolean isActive;
	
	public User(){}
	
	public User(String login, String password, String fullname){
		this.login = login;
		this.password = password;
		this.fullname=fullname;
	}

	public int getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Set<Contact> getContacts() {
		return contacts;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}

	@Override
	public String toString() {
		return "User id - " + id + ", login - " + login + ", password - " + password + ", fullname - " + fullname
				+ ", contacts - " + contacts + ", isActive - " + isActive;
	}
	
	
	
}
