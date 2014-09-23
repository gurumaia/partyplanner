/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.partyplanner.util;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


/**
 * User input validation class.
 * <p>
 * This class has a {@link HashMap} {@link #errorMap} responsible for storing user-friendly error messages related to the fields being validated.
 * After calling all needed methods, call {@link #getErrorMap()} to retrieve the HashMap.
 */
public class Validator {
	
	private static final Logger logger = LogManager.getLogger(Validator.class.getName());
	protected HashMap<String,String> errorMap;
	
	ResourceBundle messageBundle = ResourceBundle.getBundle("messages");
	
	/**
	 * No-arg constructor.
	 * <p>
	 * I don't really remember why this is needed, but I remember that it is related to internationalization.
	 * This is probably a fault on my part, need to re-check.
	 */
	public Validator() {
		this.errorMap = new HashMap<>();
		logger.debug("Validator no-arg constructor");
	}

	/**
	 * This constructor receives an internationalization {@code ResourceBundle}.
	 * <p>
	 * This {@link ResourceBundle} is later used to retrieve localized friendly messages.
	 * 
	 * @param rb Internationalization {@code ResourceBundle}
	 */
	public Validator(ResourceBundle rb) {
		this.errorMap = new HashMap<>();
		logger.debug("Validator ResourceBundle constructor: "+rb);
		if ( rb != null ) {
			messageBundle = rb;
		}	
	}
	
	/**
	 * This method overloads {@link #sanityzeString(java.lang.String, boolean)} and sets the boolean as false.
	 * 
	 * @param str String to be sanitized
	 * @return Sanitized string on success, null on failure.
	 */
	public String sanityzeString(String str) {
		return sanityzeString(str, false);
	}
	
	/**
	 * Sanitizes a given string.
	 * <p>
	 * Currently only checks for null and trims the string.
	 * Will probably implement more generic string operations in the future.
	 * If the string is a password, don't write it to the log files.
	 * 
	 * @param str String to be sanitized
	 * @param isPassword Determines if the string represents a password or not
	 * @return Sanitized string on success, null on failure.
	 */
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
	
	/**
	 * This method overloads {@link #validateStringWithLength(java.lang.String, java.lang.String, int, int, boolean) } and sets the boolean as false.
	 *
	 * @param str String to be validated
	 * @param fieldName Field name to be used with {@code errorMap}
	 * @param minLength Minimum allowed string length
	 * @param maxLength Maximum allowed string length
	 * @return Validated String on success, null on failure
	 */
	public String validateStringWithLength(String str, String fieldName, int minLength, int maxLength) {
		return validateStringWithLength(str, fieldName, minLength, maxLength, false);
	}
	
	/**
	 * Validates that input is a valid string between {@code minLength} and {@code maxLength} in length.
	 * <p>
	 * This method first calls {@link #sanityzeString(java.lang.String, boolean)} to sanity check the string and then validates if the string has the correct length, based on the {@code minLength} and {@code maxLength} variables.
	 * On errors, adds a friendly message, retrieved from the internationalization {@link #messageBundle} to the {@link #errorMap}.
	 * If the string is a password, don't write it to the log files.
	 *
	 * @param str String to be validated
	 * @param fieldName Field name to be used with {@code errorMap}
	 * @param minLength Minimum allowed string length
	 * @param maxLength Maximum allowed string length
	 * @param isPassword Determines if the string represents a password or not
	 * @return Validated String on success, null on failure
	 */
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
			String fieldString = messageBundle.getString("fields_"+fieldName);
			String msg = MessageFormat.format( messageBundle.getString("validator_string_with_length"), new Object[]{fieldString,minLength,maxLength} );
			if ( isPassword )
				logger.info(msg+" (********)");
			else
				logger.info(msg+" ("+tmpStr+")");
			errorMap.put(fieldName, msg);
		}
		logger.debug("String validation with length success.");
		return tmpStr;
	}
	
	/**
	 * Validates that input is a valid email string.
	 * <p>
	 * This method first calls {@link #validateStringWithLength(java.lang.String, java.lang.String, int, int)} passing a {@code minLength} of 5 and a {@code maxLength} of 255.
	 * Then it validates that the string conforms to an e-mail address using a regular expression, through {@link #validateStringWithRegex(java.lang.String, java.lang.String, boolean)}.
	 * On errors, adds a friendly message, retrieved from the internationalization {@link #messageBundle} to the {@link #errorMap}.
	 *
	 * @param email String representing the e-mail address to be validated
	 * @param fieldName Field name to be used with {@code errorMap}
	 * @return Validated String on success, null on failure
	 */
	public String validateEmail(String email, String fieldName) {
		logger.debug("Entering method validateEmail ("+email+","+fieldName+")");
		logger.debug("Validate email string.");
		email = validateStringWithLength(email, fieldName, 5, 255);
		logger.debug("Email string validated.");

		if ( ! validateStringWithRegex(email, "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]{0,4}[a-z0-9])?", true) ) {
			String fieldString = messageBundle.getString("fields_"+fieldName);
			String msg = MessageFormat.format( messageBundle.getString("validator_email"), fieldString );
			logger.info(msg+" ("+email+")");
			errorMap.put(fieldName, msg);
		}
		logger.debug("Email validation success.");
		return email;
	}
	
	/**
	 * Validates that input is a valid password string.
	 * <p>
	 * This method first calls {@link #sanityzeString(java.lang.String, boolean) } passing a {@code isPassword} of true.
	 * Then it validates that the string conforms to a password using a regular expression, through {@link #validateStringWithRegex(java.lang.String, java.lang.String, boolean)}.
	 * On errors, adds a friendly message, retrieved from the internationalization {@link #messageBundle} to the {@link #errorMap}.
	 * This method does NOT write the password string to the log files, even on DEBUG mode.
	 * <p>
	 * Password rules:
	 * <ul>
	 * <li>Must be at least 8 characters long;
	 * <li>Must be at max 255 characters long;
	 * <li>Must have at least one of each:<ul>
	 *  <li>Letter
	 *	<li>Number
	 *  </ul>
	 * </ul>
	 * 
	 * @param password String representing the password to be validated
	 * @param fieldName Field name to be used with {@code errorMap}
	 * @return Validated String on success, null on failure
	 */
	public String validatePassword(String password, String fieldName) {
		logger.debug("Entering method validatePassword.");
		logger.debug("Validate password string.");
		password = sanityzeString(password, true);
		logger.debug("Password string validated.");
		
//		if ( ! validateStringWithRegex(password, "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%&*()_\\-=+\\[\\]{},<.>;:\\/?\\\\|]).{8,255}", fieldName, false) ) {
		if ( ! validateStringWithRegex(password, "^(?=.*\\d)(?=.*[a-z]).{8,255}", true) ) {
			String fieldString = messageBundle.getString("fields_"+fieldName);
			String msg = MessageFormat.format( messageBundle.getString("validator_password"), fieldString );
			logger.info(msg);
			errorMap.put(fieldName, msg);
		}
		logger.debug("Password validation success.");
		return password;
	}
	
	/**
	 * Validates that input is a valid nickname string.
	 * <p>
	 * This method first calls {@link #sanityzeString(java.lang.String)}.
	 * Then it validates that the string conforms to a nickname using a regular expression, through {@link #validateStringWithRegex(java.lang.String, java.lang.String, boolean)}.
	 * On errors, adds a friendly message, retrieved from the internationalization {@link #messageBundle} to the {@link #errorMap}.
	 * <p>
	 * Nickname rules:
	 * <ul>
	 * <li>Must be at least 3 characters long;
	 * <li>Must be at max 32 characters long;
	 * <li>Must have only letters, numbers and these special chars: _-.
	 * </ul>
	 * 
	 * @param nickname String representing the nickname to be validated
	 * @param fieldName Field name to be used with {@code errorMap}
	 * @return Validated String on success, null on failure
	 */
	public String validateNickname(String nickname, String fieldName) {
		logger.debug("Entering method validateNickname("+nickname+","+fieldName+").");
		logger.debug("Validate nickname string.");
		nickname = sanityzeString(nickname);
		logger.debug("Nickname string validated.");
		
//		if ( ! validateStringWithRegex(password, "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%&*()_\\-=+\\[\\]{},<.>;:\\/?\\\\|]).{8,255}", fieldName, false) ) {
		if ( ! validateStringWithRegex(nickname, "^[a-zA-Z0-9-_.]{3,32}", false) ) {
			String fieldString = messageBundle.getString("fields_"+fieldName);
			String msg = MessageFormat.format( messageBundle.getString("validator_nickname"), fieldString );
			logger.info(msg);
			errorMap.put(fieldName, msg);
		}
		logger.debug("Nickname validation success.");
		return nickname;
	}
	
	/**
	 * Validates that input is a valid name string.
	 * <p>
	 * This method first calls {@link #sanityzeString(java.lang.String)}.
	 * Then it validates that the string conforms to a name using a regular expression, through {@link #validateStringWithRegex(java.lang.String, java.lang.String, boolean)}.
	 * On errors, adds a friendly message, retrieved from the internationalization {@link #messageBundle} to the {@link #errorMap}.
	 * <p>
	 * Naming rules:
	 * <ul>
	 * <li>Must be at least 3 characters long;
	 * <li>Must be at max 35 characters long;
	 * <li>Must have only letters
	 * </ul>
	 * 
	 * @param name String representing the nickname to be validated
	 * @param fieldName Field name to be used with {@code errorMap}
	 * @param isFirstName Boolean telling if this is a firts name or not. If it's not a first name, accept empty values
	 * @return Validated String on success, null on failure
	 */
	public String validateName(String name, String fieldName, boolean isFirstName) {
		logger.debug("Entering method validateNickname("+name+","+fieldName+").");
		logger.debug("Validate nickname string.");
		name = sanityzeString(name);
		logger.debug("Nickname string validated.");
		
		if ( ! isFirstName && name.isEmpty() ) {
			logger.debug("Nickname validation success.");
			return name;
		}
		
//		if ( ! validateStringWithRegex(password, "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%&*()_\\-=+\\[\\]{},<.>;:\\/?\\\\|]).{8,255}", fieldName, false) ) {
		if ( ! validateStringWithRegex(name, "^[a-zA-Z]{3,35}", false) ) {
			String fieldString = messageBundle.getString("fields_"+fieldName);
			String msg = MessageFormat.format( messageBundle.getString("validator_name"), fieldString );
			logger.info(msg);
			errorMap.put(fieldName, msg);
		}
		logger.debug("Nickname validation success.");
		return name;
	}
	
	/**
	 * Validates a String against a regular expression.
	 *
	 * @param str String to be validated.
	 * @param regex String representing the regular expression.
	 * @param caseInsensitive Boolean that will in the future work like this: true = Case Insensitive | false = Case Sensitive # NOT IMPLEMENTED YET
	 * @return {@code boolean true} when string matches regex, {@code boolean false} when it doesn't
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
	 * Validates that input String is a valid date.
	 * <p>
	 * This method first calls {@link #sanityzeString(java.lang.String)}.
	 * Then it creates a new {@link java.util.Date} object using {@link java.text.SimpleDateFormat} based on the input string.
	 * <p>
	 * Accepts following formats:
	 *	- YYYY-MM-DD
	 *	- DD/MM/YYYY # NOT IMPLEMENTED YET
	 * 
	 * @param date String representing date to be validated
	 * @param fieldName Field name to be used with {@code errorMap}
	 * @return {@code Date} based on the input string on success, null on failure
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
			String fieldString = messageBundle.getString("fields_"+fieldName);
			String msg = MessageFormat.format( messageBundle.getString("validator_date"), fieldString );
			logger.info(msg+" ("+newDate+")");
			errorMap.put(fieldName,msg);
		}
		
		return newDate;
	}
	
	/**
	 * This method overloads {@link #validateConfirmation(java.lang.String, java.lang.String, java.lang.String, boolean)} and sets the boolean as false.
	 *
	 * @param confirmationString String to be validated and confirmed
	 * @param originalString Original string to match with {@code confirmationString}
	 * @param fieldName Field name to be used with {@code errorMap}
	 * @return Validated {@code confirmationString} on success. Adds a message to {@code errorMap} on failure, but still returns the validated string
	 */
	public String validateConfirmation(String confirmationString, String originalString, String fieldName) {
		return validateConfirmation(confirmationString, originalString, fieldName, false);
	}
	
	/**
	 * Validates that {@code confirmationString} is valid and that it is equal to {@code originalString}.
	 * <p>
	 * This method first calls {@link #sanityzeString(java.lang.String, boolean)}, with {@code confirmationString}.
	 * Then it validates if {@code confirmationString} is identical to {@code originalString}.
	 *
	 * @param confirmationString String to be validated and confirmed
	 * @param originalString Original string to match with {@code confirmationString}
	 * @param fieldName Field name to be used with {@code errorMap}
	 * @param isPassword Determines if the string represents a password or not
	 * @return Validated {@code confirmationString} on success. Adds a message to {@code errorMap} on failure, but still returns the validated string
	 */
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
			String fieldString = messageBundle.getString("fields_"+fieldName);
			String msg = MessageFormat.format( messageBundle.getString("validator_confirmation"), fieldString );
			logger.info(msg+" ("+originalString+") vs ("+confirmationString+")");
			errorMap.put(fieldName,msg);
		}
		return confirmationString;
	}
	
	/**
	 * Transforms input {@code String} into boolean.
	 * <p>
	 * If the input string is null, false or empty, the {@code Boolean} value is set as false. For every other value, it is considered true.
	 *
	 * @param booleanValue String representing a boolean value
	 * @return false for null, "false" or "". true for everything else
	 */
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
	
	/**
	 * Validates that input String is a valid gender.
	 * <p>
	 * This method first calls {@link #sanityzeString(java.lang.String)}.
	 * Then it validates if the string represents male or female.
	 *
	 * @param gender String representing gender to be validated
	 * @param fieldName Field name to be used with {@code errorMap}
	 * @return true for male ("male" or "1"), false for female (every other string) or null on failures
	 */
	public Boolean validateGender(String gender, String fieldName) {
		logger.debug("Entering method validateGender ("+gender+","+fieldName+")");
		logger.debug("Sanitize String.");
		gender = sanityzeString(gender);
		logger.debug("String sanitized.");
		if (gender == null || gender.isEmpty()) {
			String fieldString = messageBundle.getString("fields_"+fieldName);
			String msg = MessageFormat.format( messageBundle.getString("validator_gender"),	fieldString );
			logger.info(msg+" ("+gender+")");
			errorMap.put(fieldName,msg);
			return null;
		}
		return gender.equals("male") || gender.equals("1");
		
	}

	/**
	 * Returns the {@link #errorMap} associated with this object.
	 *
	 * @return {@code errorMap}
	 */
	public HashMap<String,String> getErrorMap() {
		logger.debug("Entering method getErrorMap ()");
		return errorMap;
	}
}
