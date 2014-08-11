/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.partyplanner.model;

import java.util.Date;
import javax.ejb.Local;

/**
 *
 * @author gustavo
 */
@Local
public interface UserBeanLocal {

	String getFirstName(final String email);
	Boolean registerUser(final String nickname, final String firstName, final String lastName, final String email, final String password, final Date birthDate, final Boolean gender, final Boolean optin);
}
