package com.phoneBook.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.phoneBook.entities.User;

@Repository
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
		if(user.getId()==0)
			em.persist(user);
		else
			em.merge(user);	
	}

	@Override
	public User findUserByLogin(String login) {
		TypedQuery<User> query = em.createQuery("from Users where login=:login", User.class);
		query.setParameter("login", login);
		user = query.getSingleResult();
		return user;
	}

	@Override
	public User findUserByID(Integer id) {
		User user = em.find(User.class, id);
		return user;
	}
	
	/**
	 * Fetching all users from DB
	 * @return List<User> list contains all users
	 */
	@Override
	public List<User> findAllUsers(){
		List<User> list = null;
		TypedQuery<User> query = em.createQuery("from User", User.class);
		list = query.getResultList();
		return list;
	}

}
