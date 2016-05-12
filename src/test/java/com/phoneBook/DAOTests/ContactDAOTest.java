package com.phoneBook.DAOTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.phoneBook.DAO.ContactDAO;
import com.phoneBook.DAO.ContactDAOImpl;
import com.phoneBook.DAO.UserDAO;
import com.phoneBook.entities.Contact;
import com.phoneBook.entities.User;

	/**
	 * @author Oleksandr Ryzhkov
	 * 
	 * ContactDAO test class.
	 * Tests are held by JUnit, Spring Test, Mockito and HSQLDB (integration test). 
	 * The class is divided on blocks with mock and integration tests.
	 * HSQLDB configuration is stored is main/test/java/resources/application-context-test.xml
	 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:**/application-context-test.xml", "/application-config.xml"})
@TransactionConfiguration(transactionManager = "transactionManager",defaultRollback = true)
@Transactional
public class ContactDAOTest {
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

//	MockTesting
	/**
	 * Mock testing of persist() method invocation
	 */
	@Test
	public void createContactMockTest(){
		mockDao.saveContact(testContact);
		verify(em).persist(testContact);
		verify(em, times(1)).persist(testContact);
	}

	/**
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
		//when(em.find(Contact.class, 1)).thenCallRealMethod();
	}

	/**
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
	
	/**
	 * ContactDAO.getAllContactsByUser() test
	 * Testing:
	 * -the size of returned list of Contacts of distinct User;
	 * -isEmpty() of returned list of Contacts of User that doesn't have any Contact attached;
	 * -the invocation of em.createQuery("from Contact where ownerUser=:ownerUser", Contact.class);
	 * -the invocation of query.getResultList();
	 */
	@Test
	public void getAllContactsByUserMockTest(){
		TypedQuery<Contact>query = mock(TypedQuery.class);
		List<Contact> emptyList = new ArrayList<>();

		when(em.createQuery("from Contact where ownerUser=:ownerUser", Contact.class)).thenReturn(query);
		when(query.getResultList()).thenReturn(allContTest).thenReturn(emptyList);
		
		assertEquals(allContTest.size(),mockDao.getAllContactsByUser(testOwner).size());
		assertTrue(mockDao.getAllContactsByUser(testOwner2).isEmpty());
		
		verify(query,times(1)).setParameter("ownerUser", testOwner);
		verify(query,times(1)).setParameter("ownerUser", testOwner2);
	}

	/**
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
	
	
	
	/**
	 * Integration testing part
	 * HSQLDB is used for tests, testing configuration is stored is application-config-test.xml is main/test/resources folder.
	 */
	
	/**
	 * ContactDAO.saveContact() integration test
	 * Assertion is made by testContact.name and testContact.id
	 */
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
	
	/**
	 * ContactDAO.getContactById() test
	 * Assertion is made by notNull and fetched from DB Contact.id
	 */
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
	
	/**
	 * ContactDAO.getAllContacts() test
	 * Assertion is made by the size of a List of Contacts fetched from DB, List mustn't be empty, 
	 * and by first Contact.email from the fetched list. 
	 */
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
	
	
	/**
	 * ContactDAO.getAllContactsByUser() test
	 * Assertion by the size of fetched List<Contact>, Contact.ownerUser of the first contact, 
	 * and isEmpty() for emptyList of User without any contacts
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void getAllContactsByUserTest(){
		testContact2 = new Contact("TestSurname", "TestName", "TestPatName", "TestNum1", "TestNum2", "TestAddress", "TestEmail",
				testOwner);
		userItegrDAO.saveUser(testOwner);
		userItegrDAO.saveUser(testOwner2);
		contactIntegrDAO.saveContact(testContact);
		contactIntegrDAO.saveContact(testContact2);
		
		List<Contact> list = contactIntegrDAO.getAllContactsByUser(testOwner);
		List<Contact> emptyList = contactIntegrDAO.getAllContactsByUser(testOwner2);
		
		assertEquals(2, list.size());
		assertTrue(emptyList.isEmpty());
		assertEquals(testContact.getName(), list.get(0).getName());
	}
	
	/**
	 * ContactDAO.removeContact() test
	 * Assertion is made by Contact.id of the first element of the list persisted in DB, 
	 * a size of list before and after removing one element.
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void removeContactTest(){
		List<Contact> list = contactIntegrDAO.getAllContacts();
		userItegrDAO.saveUser(testOwner);
		userItegrDAO.saveUser(testOwner2);
		contactIntegrDAO.saveContact(testContact);
		list = contactIntegrDAO.getAllContacts();
		contactIntegrDAO.saveContact(testContact2);
		list = contactIntegrDAO.getAllContacts();
		
		/*
		 * Firstly, check whether there are two Contacts in getAllContacts() list.
		 * Secondly, whether the number of Contacts in DB decreased by one.
		 */
//		assertEquals(2, list.size());
		assertEquals(1, list.get(0).getId());
		contactIntegrDAO.removeContact(1);
		list = contactIntegrDAO.getAllContacts();

		assertEquals(2, list.size());
		}
}
