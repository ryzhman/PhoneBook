package com.phoneBook.DAO;

import java.util.List;

import com.phoneBook.entities.Contact;

public interface ContactDAO {
	public void saveContact(Contact cont);
	
	public Contact getContactById(Integer id);
	
	public List<Contact> getAllContacts();
	
	public void removeContact(Integer id);

}
