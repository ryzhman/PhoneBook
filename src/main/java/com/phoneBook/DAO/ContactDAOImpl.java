package com.phoneBook.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.phoneBook.entities.Contact;

/**
 * Class realizes the basic DAO methods for Contact entity
 * 
 * @author Oleksandr Ryzhkov
 * @version 1.0
 */
@Repository
public class ContactDAOImpl implements ContactDAO {

	/*	
	 * Injection of JPA's EntityManager
	 * EM is chosen to guarantee the flexibility o application in terms of using another persistence providers
	 * No dependency on Hibernate
	 */	
	@PersistenceContext
	private EntityManager em;   

	/*	
	 * basic saving operation
	 * if an object is already in EntityManager cache and have id - it is updated
	 * @param Contact contact to persist
	 */	
	@Transactional
	@Override
	public void saveContact(Contact cont)  {
		if(cont.getId()==0)
			em.persist(cont);
		else
			em.merge(cont);
	}

	/*
	 * Finding persisted contact by id
	 * @param int id id of contact
	 * @return found contact or null
	 */
	@Override
	public Contact getContactById(Integer id) {
		Contact contact = em.find(Contact.class, id);
		//		Contact contact = (Contact)sessionFactory.getCurrentSession().get(Contact.class, id);
		return contact;
	}

	/*
	 * Finding all persisted contacts
	 * @return list of found contacts
	 */
	@Override
	public List<Contact> getAllContacts() {
		TypedQuery<Contact> query = em.createQuery("from Contact", Contact.class);
		List<Contact> list = null;
		list = query.getResultList();
		return list;
	}

	/*
	 * Removing contact from DB
	 * @param Contact cont Contact to remove
	 * @return int id of removed contact
	 */
	@Transactional
	@Override
	public void removeContact(Integer id) {
		em.remove(em.find(Contact.class, id));
	}
}
