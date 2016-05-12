package com.phoneBook.service;

import java.util.List;

import com.phoneBook.entities.Contact;
import com.phoneBook.entities.User;

public interface ContactService {
	
	public void addContact(Contact cont);
	
	public Contact findContactById(Integer id);
	
	public List<Contact> findAllContacts();
	
	public List<Contact> findAllContactsByUser(User user);
	
	public void deleteContact(Integer id);
	

}
