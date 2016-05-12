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

import org.hibernate.annotations.ColumnDefault;

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
	
	@Column(name="user_login", length=10, nullable=false)
	private String login;
	
	@Column(name="user_pass", length=15, nullable=false)
	private String password;
	
	@Column(name="user_fullname", length=30)
	private String fullname;
	
//	@OneToMany(mappedBy="ownerUser", cascade=CascadeType.ALL)
//	private Set<Contact> contacts;
	
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
		this.isActive=true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "User id - " + id + ", login - " + login + ", password - " + password + ", fullname - " + fullname
				 + ", isActive - " + isActive;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fullname == null) ? 0 : fullname.hashCode());
		result = prime * result + ((isActive == null) ? 0 : isActive.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		User other = (User) obj;
		if (fullname == null) {
			if (other.fullname != null)
				return false;
		} else if (!fullname.equals(other.fullname))
			return false;
		if (isActive == null) {
			if (other.isActive != null)
				return false;
		} else if (!isActive.equals(other.isActive))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
	
	
	
}
