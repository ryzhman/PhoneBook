package com.phoneBook.DAOTests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.phoneBook.DAO.UserDAO;
import com.phoneBook.DAO.UserDAOImpl;
import com.phoneBook.entities.User;

/**
 * 
 * @author Oleksandr Ryzhkov
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:**/application-context-test.xml", "/application-config.xml"})
@TransactionConfiguration(transactionManager = "transactionManager",defaultRollback = true)
@Transactional
public class UserDAOTest {
	private User testUser;
	private User testUser2;
	private List<User> allUsersList;
	private List<User> allUsersEmptyList;
	private User fetchedUser;
	@Mock
	private EntityManager em;
	@InjectMocks
	private UserDAO mockDAO = new UserDAOImpl();

	@Before
	public void setUp(){
		testUser = new User("testLogin", "testPass", "testFullName");
		testUser2 = new User("testLogin2", "testPass2", "testFullName2");
		
		allUsersList = Arrays.asList(testUser, testUser2);
		allUsersEmptyList = new ArrayList<>();
		
		/*
		 * Method to initialize annotated fields
		 */
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 *      Mock tests
	 */
	
	/**
	 * UserDAO.saveUser() mock test
	 * Test:
	 * -the invocation of em.persist();
	 * -the invocation of em.merge()
	 */
	@Test
	public void saveUserMockTest(){
		mockDAO.saveUser(testUser);
		verify(em, times(1)).persist(testUser);
		testUser.setId(1);
		mockDAO.saveUser(testUser);
		verify(em, times(1)).merge(testUser);
	}
	
	/**
	 * UserDAO.findUserByID() mock test
	 * Test:
	 * -equality of fetched User.fullName with prepared one;
	 * -notNull fetched user;
	 * -invocation of em.createQuery() with params;
	 * -invocation of query.setParameter() for testLogin
	 * -invocation of query.getSingleResult();
	 */
	@Test
	public void findUserByLoginMockTest(){
		TypedQuery<User> query = mock(TypedQuery.class);
		when(em.createQuery("from Users where login=:login", User.class)).thenReturn(query);
		when(query.setParameter("login", "testLogin")).thenReturn(query);
		when(query.getSingleResult()).thenReturn(testUser);
		
		fetchedUser = mockDAO.findUserByLogin("testLogin");
		
		assertTrue(testUser.equals(fetchedUser));
		assertNotNull(fetchedUser);
		verify(em, times(1)).createQuery("from Users where login=:login", User.class);
		verify(query, times(1)).setParameter("login", "testLogin");
		verify(query, times(1)).getSingleResult();
	}
	
	/**
	 * UserDAO.findUserByID() test
	 * Test:
	 * -notNull o fetchedUser;
	 * -equality of fetchedUser and testUser;
	 * -single invocation of em.find();
	 */
	@Test
	public void findUserByIdMockTest(){
		when(em.find(User.class, 1)).thenReturn(testUser);
		
		fetchedUser = mockDAO.findUserByID(1);
		assertNotNull(fetchedUser);
		assertEquals(testUser, fetchedUser);
		verify(em, times(1)).find(User.class, 1);
	}
	
	/**
	 * UserDAO.findAllUsers() test
	 * Test:
	 * -nonNull of fetched List<User>;
	 * -isEmpty of fetched List<User>;
	 * -the sizes of fetched List<User> and prepared one;
	 * -the equality of the 1st elements of fetched List<User> and prepared one;
	 * -the return of emptyList for the 2nd invocation of method (mocking empty DB);
	 * -the invocation of em.createQuery();
	 * -the invocation of query.getResultList().
	 */
	@Test
	public void findAllUsersMockTest(){
		TypedQuery<User> query = mock(TypedQuery.class);
		when(em.createQuery("from User", User.class)).thenReturn(query);
		when(query.getResultList()).thenReturn(allUsersList).thenReturn(allUsersEmptyList);
		
		List<User> list = mockDAO.findAllUsers();
		assertNotNull(list);
		assertFalse(list.isEmpty());
		assertEquals(testUser, list.get(0));
		List<User> emptyList = mockDAO.findAllUsers();
		assertTrue(emptyList.isEmpty());
		verify(em, times(2)).createQuery("from User", User.class);
		verify(query, times(2)).getResultList();
	}
	
	
	/**
	 * Integration test section
	 * */
	
	
	
	
	

}
