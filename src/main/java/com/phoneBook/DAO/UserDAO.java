package com.phoneBook.DAO;

import com.phoneBook.entities.User;

public interface UserDAO {
	
	public void saveUser(User user);
	
	public User findUserByLogin(String login);
	
	public User login(String login, String pass);
	
	public boolean deactivateUser(User user);

}
