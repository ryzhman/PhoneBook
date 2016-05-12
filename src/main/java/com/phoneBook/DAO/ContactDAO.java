package com.phoneBook.DAO;

import java.util.List;

import com.phoneBook.entities.Contact;
import com.phoneBook.entities.User;

public interface ContactDAO {
	public void saveContact(Contact cont);
	
	public Contact getContactById(Integer id);
	
	public List<Contact> getAllContacts();
	
	public List<Contact> getAllContactsByUser(User user);
	
	public void removeContact(Integer id);
}
