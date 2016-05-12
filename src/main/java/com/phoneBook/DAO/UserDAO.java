package com.phoneBook.DAO;

import java.util.List;

import com.phoneBook.entities.User;

public interface UserDAO {
	
	public void saveUser(User user);
	
	public User findUserByLogin(String login);
	
	public User findUserByID(Integer id);
	
	public List<User> findAllUsers();
	
}
