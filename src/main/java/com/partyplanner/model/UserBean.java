/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.partyplanner.model;

import com.partyplanner.persistence.UserEntity;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gustavo
 */
@Stateless
public class UserBean implements UserBeanLocal {
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
		UserEntity u = (UserEntity) em.createNamedQuery("getUserByEmail").setParameter("email", email).getSingleResult();
		return u.getFirstName();
	}

	@Override
	public int getId(final String email) {
		UserEntity u = (UserEntity) em.createNamedQuery("getUserByEmail").setParameter("email", email).getSingleResult();
		return u.getId();
	}
	
	
	
	@Override
	public boolean registerUser(final String nickname, 
								final String firstName, 
								final String lastName, 
								final String email, 
								final String password, 
								final Date birthDate, 
								final Boolean gender,
								final Boolean optin) {
		
		
		return true;
	}

	@Override
	public boolean isUserRegistered(String email) {
		try {
			UserEntity u = (UserEntity) em.createNamedQuery("getUserByEmail").setParameter("email", email).getSingleResult();
		}
		catch (javax.persistence.NoResultException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isNicknameTaken(String nickname) {
		try {
//			UserEntity u = (UserEntity) em.createNamedQuery("getUserByEmail").setParameter("email", email).getSingleResult();
		}
		catch (javax.persistence.NoResultException e) {
			return false;
		}
		return true;
	}
}
