package com.phoneBook.DAO;

import java.util.List;

import com.phoneBook.entities.Contact;

public interface ContactDAO {
	public void save(Contact cont);
	
	public Contact getById(Integer id);
	
	public List<Contact> getAll();
	
	public Integer remove(Contact cont);
	
	

}
