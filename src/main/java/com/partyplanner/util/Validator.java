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
import static java.util.regex.Pattern.CASE_INSENSITIVE;
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
		logger.debug("Entering method validateStringWithLength ("+str+","+fieldName+","+minLength+","+maxLength+")");
		String tmpStr;
		logger.debug("Sanitize String.");
		tmpStr = sanityzeString(str);
		logger.debug("String sanitized.");
		if (	( minLength > 0 && (tmpStr == null || tmpStr.isEmpty() || tmpStr.length() < minLength) ) ||
				(tmpStr != null && tmpStr.length() > maxLength)) {
			String msg = fieldName+" must be between "+minLength+" and "+maxLength+" characters.";
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
		logger.debug("Email string validated");
		Pattern p;
//		p = Pattern.compile("^([A-Z0-9_%+-]+)(\\.$1)+@[A-Z0-9.-]+\\.[A-Z]{2,4}$",CASE_INSENSITIVE);
		logger.debug("Compiling regex.");
		p = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]{0,4}[a-z0-9])?",CASE_INSENSITIVE);
		logger.debug("Regex compiled");
				
		logger.debug("Matching regex.");
		Matcher m = p.matcher(email);
		logger.debug("Regex match executed.");
		
		if ( ! m.matches() ) {
			String msg = fieldName+" is not a valid email";
			logger.info(msg+" ("+email+")");
			errorMap.put(fieldName, msg);
		}
		logger.debug("Email validation success.");
		return email;
	}
	
	public String validatePassword(String password, String fieldName) {
		logger.debug("Entering mehod validatePassword.");
		password = validateStringWithLength(password, fieldName, 8, 255);
		return password;
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
			newDate = (Date) df.parse(date);
		} catch (ParseException ex) {
			String msg = fieldName+" is not a valid date";
			logger.info(msg+" ("+newDate+")");
			errorMap.put(fieldName,msg);
		}
		
		return newDate;
	}
	
	public String validateConfirmation(String confirmationString, String originalString, String fieldName) {
		logger.debug("Entering method validateConfirmation ("+confirmationString+","+originalString+","+fieldName+")");
		logger.debug("Sanitize String.");
		confirmationString = sanityzeString(confirmationString);
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
		if ( booleanValue != null && !booleanValue.equals("false") ) {
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
