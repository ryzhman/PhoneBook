package com.phoneBook.service;

import com.phoneBook.entities.User;

public interface UserService {
	
	public void addNewUser(User user);
	
	public User getUserById(Integer id);
	
	public Integer deleteUser(User user);
	
	public User login(String login, String pass);

}
