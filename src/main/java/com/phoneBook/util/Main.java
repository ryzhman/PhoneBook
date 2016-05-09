package com.phoneBook.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.phoneBook.entities.Contact;
import com.phoneBook.service.ContactService;
import com.phoneBook.service.ContactServiceImpl;

public class Main {

	public static void main(String[] args) {
	 	ApplicationContext context = new ClassPathXmlApplicationContext("application-config.xml");
	 	
		ContactService service = (ContactService) context.getBean("contactServiceImpl"); 
		Contact testContact = new Contact("Ryzhkov", "Oleksandr", "Oleksandrovych", "380993270237", "380442322233", 
				"Kopernika 10 apt. 23", "ryzhman@gmail.com");
		service.add(testContact);
//		System.out.println(service.findById(1));
	}

}
