package com.phoneBook.service;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.phoneBook.DAO.UserDAO;
import com.phoneBook.entities.User;

/**
 * 
 * @author Олександр
 * Service provides business logic for User entity.
 * UserDAO interface is injected to invoke DAO layer methods/
 *
 */
@Named
@Transactional
public class UserServiceImpl implements UserService {

	@Inject
	private UserDAO userDAO;
	
	/**
	 * Method stores new User. If user is null exception is thrown.
	 * 
	 * @param User new user to persist
	 * 
	 */
	@Override
	public void addNewUser(User user) throws IllegalArgumentException {
		if(user==null) throw new IllegalArgumentException();
		
		userDAO.saveUser(user);
	}

	/**
	 * Method searches for a User with a defined id.
	 * 
	 * @param Integer id to find User in DB
	 * @return User found User entity 
	 * OR null is User with passed id is not found
	 * @see com.phoneBook.service.UserService#getUserById(java.lang.Integer)
	 */
	@Override
	public User getUserById(Integer id) {
		if(id==null) return null;
		
		User user = userDAO.findUserByID(id);
		if(user!=null)
			return user;
		else return null;
	}

	/**
	 * Method deativate User in DB via altering his isActove boolean flag.
	 * To enhance performance firstly User is checked whether it is stored in DB by primary key (id),
	 * than his isActive is changed to false and User is persisted. 
	 * 
	 * @param User user to deactivate
	 * @return Integer id of deactivated User 
	 * OR null if it was not found
	 * 
	 * @see com.phoneBook.service.UserService#deleteUser(com.phoneBook.entities.User)
	 */
	@Override
	public Integer deleteUser(User user) {
		if(user==null) return null;
		
		if(userDAO.findUserByID(user.getId())!=null){
			user.setIsActive(false);
			userDAO.saveUser(user);
			return user.getId();
		}else return null;
	}

	/**
	 * Method realizes login logic, comparing provided password with a password from an User found in DB.
	 * 
	 * @param String login
	 * @param String pass
	 * @see com.phoneBook.service.UserService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public User login(String login, String pass) {
		if(login==null||login.isEmpty()||pass==null||pass.isEmpty()) return null;
		
		User user = userDAO.findUserByLogin(login);
		if(user.getPassword()==pass){
			return user;
		}else
			return null;
	}
}
