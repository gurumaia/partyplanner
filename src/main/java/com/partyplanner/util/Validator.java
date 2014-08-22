/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.partyplanner.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


/**
 *
 * @author gustavo
 */
public class Validator {
	
	private static final Logger logger = LogManager.getLogger(Validator.class.getName());
	protected HashMap errorMap = new HashMap();
	
	public String sanityzeString(String str) {
		return sanityzeString(str, false);
	}
	
	public String sanityzeString(String str, boolean isPassword) {
		if ( isPassword )
			logger.debug("Entering method sanityzeString: (********)");
		else
			logger.debug("Entering method sanityzeString: ("+str+")");
		if ( str == null ) {
			logger.debug("String validation failed. Value is null!");
			return null;
		} else {
			logger.debug("String sane.");
			return str.trim();
		}
	}
	
	public String validateStringWithLength(String str, String fieldName, int minLength, int maxLength) {
		return validateStringWithLength(str, fieldName, minLength, maxLength, false);
	}
	
	public String validateStringWithLength(String str, String fieldName, int minLength, int maxLength, boolean isPassword) {
		if ( isPassword )
			logger.debug("Entering method validateStringWithLength (********,"+fieldName+","+minLength+","+maxLength+")");
		else
			logger.debug("Entering method validateStringWithLength ("+str+","+fieldName+","+minLength+","+maxLength+")");
		String tmpStr;
		logger.debug("Sanitize String.");
		tmpStr = sanityzeString(str, isPassword);
		logger.debug("String sanitized.");
		if (	( minLength > 0 && (tmpStr == null || tmpStr.isEmpty() || tmpStr.length() < minLength) ) ||
				(tmpStr != null && tmpStr.length() > maxLength)) {
			String msg = fieldName+" must be between "+minLength+" and "+maxLength+" characters.";
			if ( isPassword )
				logger.info(msg+" (********)");
			else
				logger.info(msg+" ("+tmpStr+")");
			errorMap.put(fieldName, msg);
		}
		logger.debug("String validation with length success.");
		return tmpStr;
	}
	
	public String validateEmail(String email, String fieldName) {
		logger.debug("Entering method validateEmail ("+email+","+fieldName+")");
		logger.debug("Validate email string.");
		email = validateStringWithLength(email, fieldName, 5, 255);
		logger.debug("Email string validated.");

		if ( ! validateStringWithRegex(email, "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]{0,4}[a-z0-9])?", true) ) {
			String msg = fieldName+" is not a valid email.";
			logger.info(msg+" ("+email+")");
			errorMap.put(fieldName, msg);
		}
		logger.debug("Email validation success.");
		return email;
	}
	
	/**
	 *
	 * Password rules:
	 * - Must be at least 8 characters long;
	 * - Must be at max 255 characters long;
	 * - Must have at least one of each:
	 *	- Letter;
	 *	- Number;
	 * 
	 * @param password
	 * @param fieldName
	 * @return
	 */
	public String validatePassword(String password, String fieldName) {
		logger.debug("Entering method validatePassword.");
		logger.debug("Validate password string.");
		password = validateStringWithLength(password, fieldName, 8, 255, true);
		logger.debug("Password string validated.");
		
//		if ( ! validateStringWithRegex(password, "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%&*()_\\-=+\\[\\]{},<.>;:\\/?\\\\|]).{8,255}", fieldName, false) ) {
		if ( ! validateStringWithRegex(password, "^(?=.*\\d)(?=.*[a-z]).{8,255}", true) ) {
//			String msg = fieldName+" is not a valid password. Must include at least one uppercase letter, one lowercase letter, one number, one special character and be at least 8 characters long.";
			String msg = fieldName+" is not a valid password. Must include at least one letter and one number and be at least 8 characters long.";
			logger.info(msg);
			errorMap.put(fieldName, msg);
		}
		logger.debug("Password validation success.");
		return password;
	}
	
	/**
	 *
	 * @param str
	 * @param regex
	 * @param fieldName
	 * @param caseInsensitive
	 * @return
	 */
	public boolean validateStringWithRegex(String str, String regex, boolean caseInsensitive) {
		Pattern p;
		
		logger.debug("Compiling regex.");
		p = regex != null?Pattern.compile(regex):Pattern.compile("");
		logger.debug("Regex compiled.");
		
		logger.debug("Treating for null string.");
		str = str != null?str:"";
		
		logger.debug("Matching regex.");
		Matcher m = p.matcher(str);
		logger.debug("Regex match executed.");
		
		return m.matches();
		
	}
	
	/**
	 * Validates date input
	 * Accepts following formats:
	 *	- YYYY-MM-DD
	 *	- DD/MM/YYYY
	 * @param date
	 * @param fieldName
	 * @return 
	 */
	public Date validateDate(String date, String fieldName) {
		logger.debug("Entering method validateDate ("+date+","+fieldName+")");
		logger.debug("Sanitize String.");
		date = sanityzeString(date);
		logger.debug("String sanitized.");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date newDate = null;
		try {
			newDate = (Date) df.parse(date!=null?date:"");
		} catch (ParseException ex) {
			String msg = fieldName+" is not a valid date";
			logger.info(msg+" ("+newDate+")");
			errorMap.put(fieldName,msg);
		}
		
		return newDate;
	}
	
	public String validateConfirmation(String confirmationString, String originalString, String fieldName) {
		return validateConfirmation(confirmationString, originalString, fieldName, false);
	}
	
	public String validateConfirmation(String confirmationString, String originalString, String fieldName, boolean isPassword) {
		if ( isPassword )
			logger.debug("Entering method validateConfirmation (********,********,"+fieldName+")");
		else
			logger.debug("Entering method validateConfirmation ("+confirmationString+","+originalString+","+fieldName+")");
		logger.debug("Sanitize String.");
		confirmationString = sanityzeString(confirmationString,isPassword);
		confirmationString = confirmationString == null ? "" : confirmationString;
		originalString = originalString == null ? "" : originalString;
		logger.debug("String sanitized.");
		if ( !confirmationString.equals(originalString) ) {
			String msg = fieldName+" doesn't match.";
			logger.info(msg+" ("+originalString+") vs ("+confirmationString+")");
			errorMap.put(fieldName,msg);
		}
		return confirmationString;
	}
	
	public Boolean validateBoolean(String booleanValue) {
		logger.debug("Entering method validateConfirmation ("+booleanValue+")");
		if ( booleanValue != null && !booleanValue.equals("false") && !booleanValue.equals("") ) {
			logger.debug("Boolean validation failed. Value is null or false!");
			return true;
		} else {
			logger.debug("Boolean sane.");
			return false;
		}
	}
	
	public Boolean validateGender(String gender, String fieldName) {
		logger.debug("Entering method validateGender ("+gender+","+fieldName+")");
		logger.debug("Sanitize String.");
		gender = sanityzeString(gender);
		logger.debug("String sanitized.");
		if (gender == null || gender.isEmpty()) {
			String msg = fieldName+" is not a valid gender.";
			logger.info(msg+" ("+gender+")");
			errorMap.put(fieldName,msg);
			return null;
		}
		return gender.equals("male") || gender.equals("1");
		
	}

	public HashMap getErrorMap() {
		logger.debug("Entering method getErrorMap ()");
		return errorMap;
	}
}
