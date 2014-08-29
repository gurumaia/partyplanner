/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.partyplanner.model;

import com.partyplanner.persistence.UserEntity;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author gustavo
 */
@Stateless
public class UserBean implements UserBeanLocal {
	
	private static final Logger logger = LogManager.getLogger(UserBean.class.getName());
	
	@PersistenceContext
	private EntityManager em;
	
	// Add business logic below. (Right-click in editor and choose
	// "Insert Code > Add Business Method")

	/**
	 *
	 * @param email
	 * @return User's name
	 */
	
	@Override
	public String getFirstName(final String email) {
		logger.debug("Entering method getFirstName("+email+")");
		logger.debug("Executing Named Query: getUserByEmail");
		String firstName = null;
		try {
			UserEntity u = (UserEntity) em.createNamedQuery("getUserByEmail").setParameter("email", email).getSingleResult();
			firstName = u.getFirstName();
			
		} catch (Exception e) {
			logger.warn("User not found with email: "+email, e);
		}
		return firstName;
	}

	@Override
	public Integer getId(final String email) {
		logger.debug("Entering method getId("+email+")");
		logger.debug("Executing Named Query: getUserByEmail");
		Integer id = null;
		try {
			UserEntity u = (UserEntity) em.createNamedQuery("getUserByEmail").setParameter("email", email).getSingleResult();
			id = u.getId();
		} catch (Exception e) {
			logger.warn("User not found with email: "+email,e);
		}
		return id;
	}
	
	
	
	@Override
	public boolean registerUser(final String nickname, 
								final String firstName, 
								final String lastName, 
								final String email, 
								final String password, 
								final Date birthDate, 
								final Boolean optin,
								final Boolean gender,
								final String ipAddress) {
		
		logger.debug("Entering method registerUser("+nickname+","+firstName+","+lastName+","+email+",********,"+birthDate.toString()+","+optin.toString()+","+gender.toString()+","+ipAddress);
		UserEntity userEntity = new UserEntity();
//		UserEntity userEntity = null;
		
		userEntity.setNickname(nickname);
		userEntity.setFirstName(firstName);
		userEntity.setLastName(lastName);
		userEntity.setEmail(email);
		
		userEntity.setPassword(encodePassword(password));
		
		userEntity.setBirthDate(new java.sql.Date(birthDate.getTime()));
		userEntity.setCommunicationOptin(optin);
		userEntity.setGender(gender);
		userEntity.setLastLoginAttemptIp(ipAddress);
		
		try {
			logger.debug("Trying to persist user: "+nickname);
			em.persist(userEntity);
		} catch (PersistenceException e) {
			logger.error("Error registering user",e);
			return false;
		}
		
		return true;
	}
	
	private String encodePassword(String password) {
		logger.debug("Entering method encodePassword(********)");
		MessageDigest md;
		byte byteArray[];
		byteArray = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byteArray = md.digest();
		} catch (NoSuchAlgorithmException e) {
			logger.error("Error encrypting password: ",e);
			return null;
		}
		
		return Base64.encode(byteArray);
		
	}

	@Override
	public boolean isUserRegistered(String email) {
		logger.debug("Entering method isUserRegistered("+email+")");
		try {
			UserEntity u = (UserEntity) em.createNamedQuery("getUserByEmail").setParameter("email", email).getSingleResult();
		}
		catch (javax.persistence.NoResultException e) {
			logger.debug("No results returned");
			return false;
		}
		return true;
	}

	@Override
	public boolean isNicknameTaken(String nickname) {
		logger.debug("Entering method isNicknameTaken("+nickname+")");
		try {
			UserEntity u = (UserEntity) em.createNamedQuery("getUserByNickname").setParameter("nickname", nickname).getSingleResult();
		}
		catch (javax.persistence.NoResultException e) {
			logger.debug("No results returned");
			return false;
		}
		return true;
	}
}
