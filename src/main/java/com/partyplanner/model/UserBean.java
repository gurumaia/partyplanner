/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.partyplanner.model;

import java.util.Date;
import javax.ejb.Stateless;

/**
 *
 * @author gustavo
 */
@Stateless
public class UserBean implements UserBeanLocal {
	
	// Add business logic below. (Right-click in editor and choose
	// "Insert Code > Add Business Method")

	/**
	 *
	 * @param email
	 * @return User's name
	 */
	
	@Override
	public String getFirstName(final String email) {
		
//		return ue.getFirstName();
		return "mimimiaaaa";
	}
	
	
	
	@Override
	public Boolean registerUser(final String nickname, 
								final String firstName, 
								final String lastName, 
								final String email, 
								final String password, 
								final Date birthDate, 
								final Boolean gender,
								final Boolean optin) {
		
		
		return true;
	}
}
