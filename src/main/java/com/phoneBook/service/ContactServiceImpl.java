package com.phoneBook.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phoneBook.DAO.ContactDAO;
import com.phoneBook.entities.Contact;

@Named
@Transactional
public class ContactServiceImpl implements ContactService {
	@Inject
	private ContactDAO contactDAO;
	
	@Override
	public void add(Contact cont) {
		contactDAO.save(cont);
	}

	@Override
	public Contact findById(int id) {
		Contact contact = contactDAO.getById(id);
		return contact;
	}

	@Override
	public List<Contact> findAll() {
		List<Contact> list = contactDAO.getAll();
		return list;
	}

	@Override
	public int delete(Contact cont) {
		int removedId = contactDAO.remove(cont);
		return removedId;
	}
}
