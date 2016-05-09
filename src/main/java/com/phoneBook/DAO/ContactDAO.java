package com.phoneBook.DAO;

import java.util.List;

import com.phoneBook.entities.Contact;

public interface ContactDAO {
	public void save(Contact cont);
	
	public Contact getById(int id);
	
	public List<Contact> getAll();
	
	public int remove(Contact cont);
	
	

}
