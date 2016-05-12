package com.phoneBook.util;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.phoneBook.DAO.UserDAOImpl;
import com.phoneBook.entities.Contact;
import com.phoneBook.entities.User;
import com.phoneBook.service.ContactService;
import com.phoneBook.service.UserService;

public class Main {

	public static void main(String[] args) {
//	 	ApplicationContext context = new ClassPathXmlApplicationContext("application-config.xml");
//	 	User user = new User("user1", "pass", "rea@sad.com");
//		ContactService contactService = (ContactService) context.getBean("contactServiceImpl"); 
//		UserService userService = (UserService) context.getBean("userServiceImpl");
//		Contact testContact = new Contact("Ryzhkov", "Alex", "Oleksandrovych", "380993270237", "380442322233", 
//				"Kopernika 10 apt. 23", "ryzhman@gmail.com", user);
//		Set<Contact> set = new HashSet<>();
//		set.add(testContact);
//		
//		
//		userService.addNewUser(user);
//		contactService.addContact(testContact);
//		contactService.delete(69);
//		System.out.println(contactService.findAllContactsByUser(user));
	}

}
