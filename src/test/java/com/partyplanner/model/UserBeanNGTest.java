/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.partyplanner.model;

import com.partyplanner.persistence.UserEntity;
import java.util.Date;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import static org.testng.Assert.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author gustavo
 */
public class UserBeanNGTest {
	@InjectMocks
	private static final UserBean userBean = new UserBean();
	@Mock
	private static EntityManager em = mock(EntityManager.class);
	private static Query query;
	private static UserEntity ue;
	
	
	public UserBeanNGTest() {
	}

	@BeforeMethod
	public void setUpTest() throws Exception {
		System.out.println("SETUP");
		MockitoAnnotations.initMocks(this);
		
		query = mock(Query.class);
		ue = mock(UserEntity.class);
		
		
//		userBean.em = em;
		when(em.createNamedQuery(anyString())).thenReturn(query);
		when(query.setParameter(anyString(), anyString())).thenReturn(query);
		
		
	}

	@AfterMethod
	public static void tearDownTest() throws Exception {
		System.out.println("TEARDOWN");
		em = null;
		query = null;
		ue = null;
	}

	/**
	 * Test of getFirstName method, of class UserBean.
	 * Success case.
	 * @throws java.lang.Exception
	 */
	@Test
	public void testGetFirstName_Success() throws Exception {
		String email = "gurumaia@gmail.com";
		String expResult = "Gustavo";
		
		when(query.getSingleResult()).thenReturn(ue);
		when(ue.getFirstName()).thenReturn(expResult);
		
		String result = userBean.getFirstName(email);
		assertEquals(result, expResult);
	}

	/**
	 * Test of getFirstName method, of class UserBean.
	 * Failure case
	 * @throws java.lang.Exception
	 */
	@Test
	public void testGetFirstName_Failure() throws Exception {
		String email = "asdf@gmail.com";
		String expResult = null;
		
		when(query.getSingleResult()).thenReturn(ue);
		when(ue.getFirstName()).thenThrow(NoResultException.class);
		
		String result = userBean.getFirstName(email);
		assertEquals(result, expResult);
	}

	/**
	 * Test of getId method, of class UserBean.
	 * Success case
	 * @throws java.lang.Exception
	 */
	@Test
	public void testGetId_Success() throws Exception {
		String email = "gurumaia@gmail.com";
		Integer expResult = 1;
		
		when(query.getSingleResult()).thenReturn(ue);
		when(ue.getId()).thenReturn(expResult);
		
		Integer result = userBean.getId(email);
		assertEquals(result, expResult);
	}

	/**
	 * Test of getId method, of class UserBean.
	 * Failure case
	 * @throws java.lang.Exception
	 */
	@Test
	public void testGetId_Failure() throws Exception {
		String email = "gurumaia@gmail.com";
		Integer expResult = null;
		
		when(query.getSingleResult()).thenReturn(ue);
		when(ue.getId()).thenThrow(NoResultException.class);
		
		Integer result = userBean.getId(email);
		System.out.println(result);
		assertEquals(result, expResult);
	}

	/**
	 * Test of registerUser method, of class UserBean.
	 * @throws java.lang.Exception
	 */
	@Test
	public void testRegisterUser_Success() throws Exception {
		System.out.println("registerUser");
		String nickname = "gurumaia";
		String firstName = "Gustavo";
		String lastName = "Maia";
		String email = "gurumaia@gmail.com";
		String password = "teste01";
		Date birthDate = new Date(1);
		Boolean optin = false;
		Boolean gender = true;
		String ipAddress = "127.0.0.1";
		
		Boolean expResult = true;
		
//		when(em.persist(ue))
		
		Boolean result = userBean.registerUser(nickname, firstName, lastName, email, password, birthDate, optin, gender, ipAddress);
		System.out.println(result);
		assertEquals(result, expResult);
		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
	}

	/**
	 * Test of isUserRegistered method, of class UserBean.
	 * Success case.
	 * @throws java.lang.Exception
	 */
	@Test
	public void testIsUserRegistered_Success() throws Exception {
		String email = "gurumaia@gmail.com";
		boolean expResult = true;
		
		when(query.getSingleResult()).thenReturn(ue);
		
		boolean result = userBean.isUserRegistered(email);
		assertEquals(result, expResult);
	}

	/**
	 * Test of isUserRegistered method, of class UserBean.
	 * Failure case.
	 * @throws java.lang.Exception
	 */
	@Test
	public void testIsUserRegistered_Failure() throws Exception {
		String email = "gurumaia@gmail.com";
		boolean expResult = false;
		
		when(query.getSingleResult()).thenThrow(NoResultException.class);
		
		boolean result = userBean.isUserRegistered(email);
		assertEquals(result, expResult);
	}

	/**
	 * Test of isNicknameTaken method, of class UserBean.
	 * Success case
	 * @throws java.lang.Exception
	 */
	@Test
	public void testIsNicknameTaken_Success() throws Exception {
		String nickname = "gurumaia";
		boolean expResult = true;
		
		when(query.getSingleResult()).thenReturn(ue);
		
		boolean result = userBean.isNicknameTaken(nickname);
		assertEquals(result, expResult);
	}

	/**
	 * Test of isNicknameTaken method, of class UserBean.
	 * Failure case
	 * @throws java.lang.Exception
	 */
	@Test
	public void testIsNicknameTaken_Failure() throws Exception {
		String nickname = "gurumaia";
		boolean expResult = false;
		
		when(query.getSingleResult()).thenThrow(NoResultException.class);
		
		boolean result = userBean.isNicknameTaken(nickname);
		assertEquals(result, expResult);
	}
	
}
