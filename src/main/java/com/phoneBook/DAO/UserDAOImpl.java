package com.phoneBook.DAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.phoneBook.entities.Contact;
import com.phoneBook.entities.User;

public class UserDAOImpl implements UserDAO {
	private User user;
	/*	
	 * Injection of JPA's EntityManager
	 * EM is chosen to guarantee the flexibility o application in terms of using another persistence providers
	 * No dependency on Hibernate
	 */
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void saveUser(User user) {
		em.persist(user);
	}

	@Override
	public User findUserByLogin(String login) {
		TypedQuery<User> query = em.createQuery("from Users where login=:login", User.class);
		query.setParameter("login", login);
		user = query.getSingleResult();
		return user;
	}

	@Override
	public User login(String login, String pass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deactivateUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

}
