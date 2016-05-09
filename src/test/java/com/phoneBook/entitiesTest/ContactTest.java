package com.phoneBook.entitiesTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.phoneBook.DAO.ContactDAOImpl;
import com.phoneBook.entities.Contact;

public class ContactTest {
	private static Contact testContact;
	private static Contact testContact2;
	private static List<Contact> allContTest;
	/*
	 * The creation of mock EntityManager object
	*/
	@Mock
	private static EntityManager em;
	@InjectMocks
	private ContactDAOImpl dao = new ContactDAOImpl();
	
//	em=mock(EntityManager.class);
	/*
	 * or in classic style
	 */
	
	@BeforeClass
	public static void setUpClass(){
	}
	
	@AfterClass
	public static void tearDownClass(){
	}
	
	@Before
	public void setUp(){
		testContact = new Contact("Ryzhkov", "Oleksandr", "Oleksandrovych", "380993270237", "380442322233", 
				"Kopernika 10 apt. 23", "ryzhman@gmail.com");
		testContact2 = new Contact("TestSurname", "TestName", "TestPatName", "TestNum1", "TestNum2", "TestAddress", "TestEmail");
		allContTest = new ArrayList<>();
		allContTest.add(testContact);
		allContTest.add(testContact2);

		/*
		 * Method to initialize annotated fields
		*/
        MockitoAnnotations.initMocks(this);
	}
	
	
	@After
	public void tearUp(){
	}
	
	/*
	 * Testing persist() method invocation
	*/
	@Test
	public void createContactTest(){
		dao.save(testContact);
		verify(em).persist(testContact);
	}
	
	/*
	 * testing getById() DAO method
	 * test 1 - assert Contact with id 1 was found
	 * test 2 - assert Contact with invalid id is null
	*/
	@Test
	public void findByIdTest(){
		when(em.find(Contact.class, 1)).thenReturn(testContact);
		
		assertEquals(testContact, dao.getById(1));
		assertNull(dao.getById(22));
		
		/*
		 * invocation of realMethod()
		*/
//		when(em.find(Contact.class, 1)).thenCallRealMethod();
	}
	
	@Test
	public void getAllTest(){
		Query query = mock(Query.class);
		when(query.getResultList()).thenReturn(allContTest);
		when(em.createQuery("from Contacts")).thenReturn(query);
		
		verify(em).createQuery("from Contacts");
		verify(query).getResultList();
		
//		assertEquals(allContTest, dao.getAll());
		
	}
	
	

}
