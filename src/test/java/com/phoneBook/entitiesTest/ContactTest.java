package com.phoneBook.entitiesTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.phoneBook.DAO.ContactDAO;
import com.phoneBook.DAO.ContactDAOImpl;
import com.phoneBook.DAO.UserDAO;
import com.phoneBook.entities.Contact;
import com.phoneBook.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"**/classpath:application-context-test.xml", "/application-config.xml"})
public class ContactTest {
	private Contact testContact;
	private Contact testContact2;
	private List<Contact> allContTest;
	private User testOwner;
	private User testOwner2;
	@Inject
	private ContactDAO contactIntegrDAO;
	@Inject
	private UserDAO userItegrDAO;
	/*
	 * The creation of mock EntityManager object
	 */
	@Mock
	private EntityManager em;
	@InjectMocks
	private ContactDAO mockDao = new ContactDAOImpl();

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

	
//	MockTesting
	/*
	 * Mock testing of persist() method invocation
	 */
	@Test
	public void createContactMockTest(){
		mockDao.saveContact(testContact);
		verify(em).persist(testContact);
		verify(em, times(1)).persist(testContact);
	}

	/*
	 * testing getById() DAO mock method
	 * test 1 - assert Contact with id 1 was found
	 * test 2 - assert Contact with invalid id is null
	 */
	@Test
	public void findByIdMockTest(){
		when(em.find(Contact.class, 1)).thenReturn(testContact);

		assertEquals(testContact, mockDao.getContactById(1));
		verify(em).find(Contact.class, 1);
		verify(em, times(1)).find(Contact.class, 1);
		assertNull(mockDao.getContactById(22));
		verify(em, times(1)).find(Contact.class, 22);

		/*
		 * invocation of realMethod()
		 */
		//		when(em.find(Contact.class, 1)).thenCallRealMethod();
	}

	/*
	 * ContactDAOImpl.getAll() method mock test
	 * testing:
	 * -the equality of query result and prepared result;
	 * -invocation of ContactDAOImpl.getAll() method;
	 * -invocation of TypedQuery.getResultList() method.
	 */
	@Test
	public void getAllMockTest(){
		TypedQuery<Contact> query = mock(TypedQuery.class);

		when(em.createQuery("from Contact", Contact.class)).thenReturn(query);
		when(query.getResultList()).thenReturn(allContTest);

		assertEquals(allContTest, mockDao.getAllContacts());
		verify(em).createQuery("from Contact", Contact.class);
		verify(query).getResultList();
		verify(em, times(1)).createQuery("from Contact", Contact.class);
		verify(query, times(1)).getResultList();
	}

	/*
	 * ContactDAOImpl.remove(Contact) method mock test
	 * Testing:
	 * -whether the em.remove() was invoked
	 * -whether the em.remove() was invoke once
	 */
	@Test
	public void removeMockTest(){
		when(em.find(Contact.class, 1)).thenReturn(testContact);
		
		mockDao.removeContact(1);
		
		verify(em).remove(testContact);
		verify(em, times(1)).remove(testContact);
	}
	
//	Integration testing
	
	@Test
	@Transactional
	@Rollback(true)
	public void createContactIntegrTest(){
		contactIntegrDAO.saveContact(testContact);
		userItegrDAO.saveUser(testOwner);
		List<Contact> allContacts = contactIntegrDAO.getAllContacts();
		
		assertEquals(testContact.getName(), allContacts.get(0).getName());
		assertEquals(1, allContacts.get(0).getId());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void getContactByIdTest(){
		userItegrDAO.saveUser(testOwner);
		contactIntegrDAO.saveContact(testContact);
		
		Contact cont = contactIntegrDAO.getContactById(1);
		assertNotNull(cont);
		assertEquals(1, cont.getId());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void getAllContactsTest(){
		userItegrDAO.saveUser(testOwner);
		userItegrDAO.saveUser(testOwner2);
		contactIntegrDAO.saveContact(testContact);
		contactIntegrDAO.saveContact(testContact2);
		List<Contact> list = contactIntegrDAO.getAllContacts();
		
		assertEquals(2, list.size());
		assertFalse(list.isEmpty());
		assertEquals(testContact.getEmail(), list.get(0).getEmail());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void removeContactTest(){
		userItegrDAO.saveUser(testOwner);
		userItegrDAO.saveUser(testOwner2);
		contactIntegrDAO.saveContact(testContact);
		contactIntegrDAO.saveContact(testContact2);
		List<Contact> list = contactIntegrDAO.getAllContacts();
		
		/*
		 * Firstly, check whether there are two Contacts in getAllContacts() list.
		 * Secondly, whether the number of Contacts in DB decreased by one.
		 */
		assertEquals(2, list.size());
		assertEquals(1, list.get(0).getId());
		assertEquals(1, list.size());
		contactIntegrDAO.removeContact(1);
		assertEquals(1, list.size());
	
		}
}
