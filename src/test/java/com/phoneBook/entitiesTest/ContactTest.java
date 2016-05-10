package com.phoneBook.entitiesTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.constraints.AssertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.phoneBook.DAO.ContactDAO;
import com.phoneBook.DAO.ContactDAOImpl;
import com.phoneBook.entities.Contact;
import com.phoneBook.entities.User;

public class ContactTest {
	private Contact testContact;
	private Contact testContact2;
	private Contact nullContact;
	private List<Contact> allContTest;
	private User testOwner;
	private User testOwner2;

	/*
	 * The creation of mock EntityManager object
	 */
	@Mock
	private EntityManager em;
	@InjectMocks
	private ContactDAO dao = new ContactDAOImpl();

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
		testOwner = new User("testLogin", "testPassword", "testFullname");
		testOwner2 = new User("testLogin2", "testPassword2", "testFullname2");
		testContact = new Contact("Ryzhkov", "Oleksandr", "Oleksandrovych", "380993270237", "380442322233", 
				"Kopernika 10 apt. 23", "ryzhman@gmail.com", testOwner);
		testContact2 = new Contact("TestSurname", "TestName", "TestPatName", "TestNum1", "TestNum2", "TestAddress", "TestEmail",
				testOwner2);
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
		verify(em, times(1)).persist(testContact);
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
		verify(em).find(Contact.class, 1);
		verify(em, times(1)).find(Contact.class, 1);
		assertNull(dao.getById(22));
		verify(em, times(1)).find(Contact.class, 22);

		/*
		 * invocation of realMethod()
		 */
		//		when(em.find(Contact.class, 1)).thenCallRealMethod();
	}

	/*
	 * ContactDAOImpl.getAll() method test
	 * testing:
	 * -the equality of query result and prepared result;
	 * -invocation of ContactDAOImpl.getAll() method;
	 * -invocation of TypedQuery.getResultList() method.
	 */
	@Test
	public void getAllTest(){
		TypedQuery<Contact> query = mock(TypedQuery.class);

		when(em.createQuery("from Contacts", Contact.class)).thenReturn(query);
		when(query.getResultList()).thenReturn(allContTest);

		assertEquals(allContTest, dao.getAll());
		verify(em).createQuery("from Contacts", Contact.class);
		verify(query).getResultList();
		verify(em, times(1)).createQuery("from Contacts", Contact.class);
		verify(query, times(1)).getResultList();
	}

	/*
	 * ContactDAOImpl.remove(Contact) method test
	 * Testing:
	 * -
	 */
	@Test
	public void removeTest(){
		nullContact = null;
		testContact.setId(1);

		assertNull(dao.remove(nullContact));
		assertTrue(1==dao.remove(testContact));
		verify(em).remove(testContact);
		verify(em, times(1)).remove(testContact);
	}
}
