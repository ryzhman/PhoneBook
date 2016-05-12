package com.phoneBook.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phoneBook.DAO.ContactDAO;
import com.phoneBook.entities.Contact;
import com.phoneBook.entities.User;

@Named
public class ContactServiceImpl implements ContactService {
	@Inject
	private ContactDAO contactDAO;
	
	@Transactional
	@Override
	public void addContact(Contact cont) {
		contactDAO.saveContact(cont);
	}

	@Override
	public Contact findContactById(Integer id) {
		Contact contact = contactDAO.getContactById(id);
		return contact;
	}

	@Override
	public List<Contact> findAllContacts() {
		List<Contact> list = contactDAO.getAllContacts();
		return list;
	}
	
	@Override
	public List<Contact> findAllContactsByUser(User user){
		List<Contact> list = contactDAO.getAllContactsByUser(user);
		return list;
	}

	@Transactional
	@Override
	public void deleteContact(Integer id) {
		contactDAO.removeContact(id);
	}
}
