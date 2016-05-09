package com.phoneBook.service;

import java.util.List;

import com.phoneBook.entities.Contact;

public interface ContactService {
	
	public void add(Contact cont);
	
	public Contact findById(int id);
	
	public List<Contact> findAll();
	
	public int delete(Contact cont);
	

}
