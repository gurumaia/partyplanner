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
import org.mockito.MockitoAnnotations;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author gustavo
 */
public class ValidatorNGTest {
	
	public ValidatorNGTest() {
		
	}

	@BeforeClass
	public void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	// <editor-fold defaultstate="expanded" desc="sanitizeString tests. Click on the + sign on the left to edit the code.">
	/**
	 * Test of sanityzeString method, of class Validator.
	 * Passing perfect string
	 * Should return the same string
	 */
	@Test
	public void testSanityzeString_PerfectString() {
//		System.out.println("testSanityzeString_PerfectString");
		String expResult = "OnePerfectString1!2@3#4$5%6¨";
		String inputString = expResult;
		
		Validator instance = new Validator();
		String result = instance.sanityzeString(inputString, false);
		assertEquals(result, expResult);
		System.out.println("testSanityzeString_PerfectString - Expected '"+expResult+"' and got '"+result+"'");
	}
	
	/**
	 * Test of sanityzeString method, of class Validator.
	 * Passing string enclosed in spaces
	 * Should return trimmed string
	 */
	@Test
	public void testSanityzeString_TrimString() {
//		System.out.println("testSanityzeString_TrimString");
		String expResult = "TrimThisString1!2@3#4$5%6¨";
		String inputString = "  "+expResult+"  ";
		
		
		Validator instance = new Validator();
		String result = instance.sanityzeString(inputString, false);
		assertEquals(result, expResult);
		System.out.println("testSanityzeString_TrimString - Expected '"+expResult+"' and got '"+result+"'");
	}
	
	/**
	 * Test of sanityzeString method, of class Validator.
	 * Passing null
	 * Should return null
	 */
	@Test
	public void testSanityzeString_Null() {
//		System.out.println("testSanityzeString_Null");
		String expResult = null;
		String inputString = expResult;
		
		Validator instance = new Validator();
		String result = instance.sanityzeString(inputString, false);
		assertEquals(result, expResult);
		System.out.println("testSanityzeString_Null - Expected '"+expResult+"' and got '"+result+"'");
	}// </editor-fold>

	// <editor-fold defaultstate="expanded" desc="validateStringWithLength tests. Click on the + sign on the left to edit the code.">
	/**
	 * Test of validateStringWithLength method, of class Validator.
	 * Passing Perfect Input (Perfect String, low minLength, high maxLength)
	 * Should return the string and shouldn't have anything in the Error Map
	 */
	@Test
	public void testValidateStringWithLength_PerfectInput() {
		String expResult = "OnePerfectString1!2@3#4$5%6¨";
		String inputString = expResult;
		
		String fieldName = "nickname";
		int minLength = 1;
		int maxLength = 100;
		
		HashMap<String,String> expErrorMap = new HashMap<>();

		Validator instance = new Validator();
		String result = instance.validateStringWithLength(inputString, fieldName, minLength, maxLength, false);
		
		System.out.println("testValidateStringWithLength_PerfectInput - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateStringWithLength_PerfectInput - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}

	/**
	 * Test of validateStringWithLength method, of class Validator.
	 * Passing Short String
	 * Should return the string but should have an error in the Error Map
	 */
	@Test
	public void testValidateStringWithLength_ShortString() {
		String expResult = "ShortString";
		String inputString = expResult;
		
		String fieldName = "nickname";
		int minLength = 15;
		int maxLength = 100;
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put("nickname", "Nickname must be between "+minLength+" and "+maxLength+" characters.");

		Validator instance = new Validator();
		String result = instance.validateStringWithLength(inputString, fieldName, minLength, maxLength, false);
		
		System.out.println("testValidateStringWithLength_ShortString - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateStringWithLength_ShortString - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}

	/**
	 * Test of validateStringWithLength method, of class Validator.
	 * Passing Long String
	 * Should return the string but should have an error in the Error Map
	 */
	@Test
	public void testValidateStringWithLength_LongString() {
		String expResult = "LongString";
		String inputString = expResult;
		
		String fieldName = "nickname";
		int minLength = 1;
		int maxLength = 2;
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put("nickname", "Nickname must be between "+minLength+" and "+maxLength+" characters.");

		Validator instance = new Validator();
		String result = instance.validateStringWithLength(inputString, fieldName, minLength, maxLength, false);
		
		System.out.println("testValidateStringWithLength_LongString - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateStringWithLength_LongString - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validateStringWithLength method, of class Validator.
	 * Passing Empty String
	 * Should return the string and shouldn't have anything in the Error Map
	 */
	@Test
	public void testValidateStringWithLength_EmptyString() {
		String expResult = "";
		String inputString = expResult;
		
		String fieldName = "nickname";
		int minLength = 0;
		int maxLength = 2;
		
		HashMap<String,String> expErrorMap = new HashMap<>();
//		expErrorMap.put("nickname", "Nickname must be between "+minLength+" and "+maxLength+" characters.");

		Validator instance = new Validator();
		String result = instance.validateStringWithLength(inputString, fieldName, minLength, maxLength, false);
		
		System.out.println("testValidateStringWithLength_EmptyString - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateStringWithLength_EmptyString - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validateStringWithLength method, of class Validator.
	 * Passing Null String
	 * Should return the string and shouldn't have anything in the Error Map
	 */
	@Test
	public void testValidateStringWithLength_NullString() {
		String expResult = null;
		String inputString = expResult;
		
		String fieldName = "nickname";
		int minLength = 0;
		int maxLength = 2;
		
		HashMap<String,String> expErrorMap = new HashMap<>();
//		expErrorMap.put("nickname", "Nickname must be between "+minLength+" and "+maxLength+" characters.");

		Validator instance = new Validator();
		String result = instance.validateStringWithLength(inputString, fieldName, minLength, maxLength, false);
		
		System.out.println("testValidateStringWithLength_NullString - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateStringWithLength_NullString - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	// </editor-fold>

	// <editor-fold defaultstate="expanded" desc="validateEmail tests. Click on the + sign on the left to edit the code.">
	/**
	 * Test of validateEmail method, of class Validator.
	 * Passing Perfect Input (Perfect Email String)
	 * Should return the email string and shouldn't have anything in the Error Map
	 */
	@Test
	public void testValidateEmail_PerfectInput() {
		String expResult = "gurumaia@gmail.com";
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();

		Validator instance = new Validator();
		String result = instance.validateEmail(inputString, fieldName);
		
		System.out.println("testValidateEmail_PerfectInput - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateEmail_PerfectInput - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validateEmail method, of class Validator.
	 * Passing Invalid Email
	 * Should return the email string and should have an error in the error map
	 */
	@Test
	public void testValidateEmail_InvalidEmail() {
		String expResult = "gurumaiagmail.com";
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName, "Nickname is not a valid email.");

		Validator instance = new Validator();
		String result = instance.validateEmail(inputString, fieldName);
		
		System.out.println("testValidateEmail_InvalidEmail - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateEmail_InvalidEmail - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validateEmail method, of class Validator.
	 * Passing Empty String
	 * Should return the email string and should have an error in the error map
	 */
	@Test
	public void testValidateEmail_EmptyString() {
		String expResult = "";
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName, "Nickname is not a valid email.");

		Validator instance = new Validator();
		String result = instance.validateEmail(inputString, fieldName);
		
		System.out.println("testValidateEmail_EmptyString - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateEmail_EmptyString - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validateEmail method, of class Validator.
	 * Passing Null String
	 * Should return the email string and should have an error in the error map
	 */
	@Test
	public void testValidateEmail_NullString() {
		String expResult = null;
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName, "Nickname is not a valid email.");

		Validator instance = new Validator();
		String result = instance.validateEmail(inputString, fieldName);
		
		System.out.println("testValidateEmail_EmptyString - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateEmail_EmptyString - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	// </editor-fold>
	
	// <editor-fold defaultstate="expanded" desc="validatePassword tests. Click on the + sign on the left to edit the code.">
	/**
	 * Test of validatePassword method, of class Validator.
	 * Passing Perfect Input (Perfect Password String)
	 * Should return the password string and shouldn't have anything in the Error Map
	 */
	@Test
	public void testValidatePassword_PerfectInput() {
		String expResult = "ThisIsAPassword0!";
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();

		Validator instance = new Validator();
		String result = instance.validatePassword(inputString, fieldName);
		
		System.out.println("testValidatePassword_PerfectInput - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidatePassword_PerfectInput - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validatePassword method, of class Validator.
	 * Passing Short Password
	 * Should return the password string and should have an error in the error map
	 */
	@Test
	public void testValidatePassword_ShortPassword() {
		String expResult = "nopas01";
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName,"Nickname is not a valid password. Must include at least one letter and one number and be at least 8 characters long.");

		Validator instance = new Validator();
		String result = instance.validatePassword(inputString, fieldName);
		
		System.out.println("testValidatePassword_ShortPassword - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidatePassword_ShortPassword - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validatePassword method, of class Validator.
	 * Passing Long Password
	 * Should return the password string and should have an error in the error map
	 */
	@Test
	public void testValidatePassword_LongPassword() {
		String expResult = "longpassword01longpassword01longpassword01longpassword01longpassword01longpassword01longpassword01longpassword01longpassword01longpassword01longpassword01longpassword01longpassword01longpassword01longpassword01longpassword01longpassword01longpassword01long";
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName,"Nickname is not a valid password. Must include at least one letter and one number and be at least 8 characters long.");

		Validator instance = new Validator();
		String result = instance.validatePassword(inputString, fieldName);
		
		System.out.println("testValidatePassword_LongPassword - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidatePassword_LongPassword - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validatePassword method, of class Validator.
	 * Passing Password without numbers
	 * Should return the password string and should have an error in the error map
	 */
	@Test
	public void testValidatePassword_NoNumbers() {
		String expResult = "thisisnotapassword";
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName,"Nickname is not a valid password. Must include at least one letter and one number and be at least 8 characters long.");

		Validator instance = new Validator();
		String result = instance.validatePassword(inputString, fieldName);
		
		System.out.println("testValidatePassword_NoNumbers - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidatePassword_NoNumbers - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validatePassword method, of class Validator.
	 * Passing Empty Password
	 * Should return the password string and should have an error in the error map
	 */
	@Test
	public void testValidatePassword_EmptyPassword() {
		String expResult = "";
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName,"Nickname is not a valid password. Must include at least one letter and one number and be at least 8 characters long.");

		Validator instance = new Validator();
		String result = instance.validatePassword(inputString, fieldName);
		
		System.out.println("testValidatePassword_EmptyPassword - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidatePassword_EmptyPassword - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validatePassword method, of class Validator.
	 * Passing Null Password
	 * Should return the password string and should have an error in the error map
	 */
	@Test
	public void testValidatePassword_NullPassword() {
		String expResult = null;
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName,"Nickname is not a valid password. Must include at least one letter and one number and be at least 8 characters long.");

		Validator instance = new Validator();
		String result = instance.validatePassword(inputString, fieldName);
		
		System.out.println("testValidatePassword_NullPassword - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidatePassword_NullPassword - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	// </editor-fold>
	
	// <editor-fold defaultstate="expanded" desc="validateNickname tests. Click on the + sign on the left to edit the code.">
	/**
	 * Test of validateNickname method, of class Validator.
	 * Passing Perfect Input (Perfect NicknameString)
	 * Should return the nickname string and shouldn't have anything in the Error Map
	 */
	@Test
	public void testValidateNickname_PerfectInput() {
		String expResult = "ThisIsANickname01_.-.";
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();

		Validator instance = new Validator();
		String result = instance.validateNickname(inputString, fieldName);
		
		System.out.println("testValidateNickname_PerfectInput - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateNickname_PerfectInput - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of Nickname method, of class Validator.
	 * Passing Short Nickname
	 * Should return the Nickname string and should have an error in the error map
	 */
	@Test
	public void testValidateNickname_ShortNickname() {
		String expResult = "ni";
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName,"Nickname is not a valid nickname. Must be between 3 and 32 characters and have only letters, numbers and _ (underline), - (dash), . (dot).");

		Validator instance = new Validator();
		String result = instance.validateNickname(inputString, fieldName);
		
		System.out.println("testValidateNickname_ShortNickname - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateNickname_ShortNickname - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of Nickname method, of class Validator.
	 * Passing Long Nickname
	 * Should return the Nickname string and should have an error in the error map
	 */
	@Test
	public void testValidateNickname_LongNickname() {
		String expResult = "longnickname01longnickname01longn";
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName,"Nickname is not a valid nickname. Must be between 3 and 32 characters and have only letters, numbers and _ (underline), - (dash), . (dot).");

		Validator instance = new Validator();
		String result = instance.validateNickname(inputString, fieldName);
		
		System.out.println("testValidateNickname_LongNickname - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateNickname_LongNickname - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validateNickname method, of class Validator.
	 * Passing Nickname with unacceptable special chars
	 * Should return the nickname string and should have an error in the error map
	 */
	@Test
	public void testValidateNickname_SpecialChars() {
		String expResult = "thisisnotanickname!";
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName,"Nickname is not a valid nickname. Must be between 3 and 32 characters and have only letters, numbers and _ (underline), - (dash), . (dot).");

		Validator instance = new Validator();
		String result = instance.validateNickname(inputString, fieldName);
		
		System.out.println("testValidateNickname_SpecialChars - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateNickname_SpecialChars - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validateNickname method, of class Validator.
	 * Passing Empty Nickname
	 * Should return the nickname string and should have an error in the error map
	 */
	@Test
	public void testValidateNickname_EmptyNickname() {
		String expResult = "";
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName,"Nickname is not a valid nickname. Must be between 3 and 32 characters and have only letters, numbers and _ (underline), - (dash), . (dot).");

		Validator instance = new Validator();
		String result = instance.validateNickname(inputString, fieldName);
		
		System.out.println("testValidateNickname_EmptyNickname - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateNickname_EmptyNickname - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validateNickname method, of class Validator.
	 * Passing Null Nickname
	 * Should return the nickname string and should have an error in the error map
	 */
	@Test
	public void testValidateNickname_NullNickname() {
		String expResult = null;
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName,"Nickname is not a valid nickname. Must be between 3 and 32 characters and have only letters, numbers and _ (underline), - (dash), . (dot).");

		Validator instance = new Validator();
		String result = instance.validateNickname(inputString, fieldName);
		
		System.out.println("testValidateNickname_NullNickname - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateNickname_NullNickname - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	// </editor-fold>

	// <editor-fold defaultstate="expanded" desc="validateName tests. Click on the + sign on the left to edit the code.">
	/**
	 * Test of validateName method, of class Validator.
	 * Passing Perfect Input (Perfect NameString)
	 * Should return the name string and shouldn't have anything in the Error Map
	 */
	@Test
	public void testValidateName_PerfectInput() {
		String expResult = "ThisIsAName";
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();

		Validator instance = new Validator();
		String result = instance.validateName(inputString, fieldName, true);
		
		System.out.println("testValidateName_PerfectInput - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateName_PerfectInput - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validateName method, of class Validator.
	 * Passing Perfect Input (Perfect NameString) for a last name (empty string).
	 * Should return the name string and shouldn't have anything in the Error Map.
	 */
	@Test
	public void testValidateName_EmptyLastName() {
		String expResult = "";
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();

		Validator instance = new Validator();
		String result = instance.validateName(inputString, fieldName, false);
		
		System.out.println("testValidateName_EmptyLastName - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateName_EmptyLastName - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validateName method, of class Validator.
	 * Passing short last name.
	 * Should return the name string and shouldn't have anything in the Error Map.
	 */
	@Test
	public void testValidateName_ShortLastName() {
		String expResult = "la";
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName,"Nickname is not a valid name. Must be between 3 and 35 characters and have only letters.");

		Validator instance = new Validator();
		String result = instance.validateName(inputString, fieldName, false);
		
		System.out.println("testValidateName_ShortLastName - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateName_ShortLastName - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of Name method, of class Validator.
	 * Passing Short Name
	 * Should return the Name string and should have an error in the error map
	 */
	@Test
	public void testValidateName_ShortName() {
		String expResult = "na";
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName,"Nickname is not a valid name. Must be between 3 and 35 characters and have only letters.");

		Validator instance = new Validator();
		String result = instance.validateName(inputString, fieldName, true);
		
		System.out.println("testValidateName_ShortName - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateName_ShortName - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of Name method, of class Validator.
	 * Passing Long Name
	 * Should return the Name string and should have an error in the error map
	 */
	@Test
	public void testValidateName_LongName() {
		String expResult = "longnamelongnamelongnamelongnamelong";
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName,"Nickname is not a valid name. Must be between 3 and 35 characters and have only letters.");

		Validator instance = new Validator();
		String result = instance.validateName(inputString, fieldName, true);
		
		System.out.println("testValidateName_LongName - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateName_LongName - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validateName method, of class Validator.
	 * Passing Name with unacceptable special chars
	 * Should return the name string and should have an error in the error map
	 */
	@Test
	public void testValidateName_SpecialChars() {
		String expResult = "thisisnotaname01";
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName,"Nickname is not a valid name. Must be between 3 and 35 characters and have only letters.");

		Validator instance = new Validator();
		String result = instance.validateName(inputString, fieldName, true);
		
		System.out.println("testValidateName_SpecialChars - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateName_SpecialChars - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validateName method, of class Validator.
	 * Passing Empty Name
	 * Should return the name string and should have an error in the error map
	 */
	@Test
	public void testValidateName_EmptyName() {
		String expResult = "";
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName,"Nickname is not a valid name. Must be between 3 and 35 characters and have only letters.");

		Validator instance = new Validator();
		String result = instance.validateName(inputString, fieldName, true);
		
		System.out.println("testValidateName_EmptyName - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateName_EmptyName - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validateName method, of class Validator.
	 * Passing Null Name
	 * Should return the name string and should have an error in the error map
	 */
	@Test
	public void testValidateName_NullName() {
		String expResult = null;
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName,"Nickname is not a valid name. Must be between 3 and 35 characters and have only letters.");

		Validator instance = new Validator();
		String result = instance.validateName(inputString, fieldName, true);
		
		System.out.println("testValidateName_NullName - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateName_NullName - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	// </editor-fold>
	
	// <editor-fold defaultstate="expanded" desc="validateStringWithRegex tests. Click on the + sign on the left to edit the code.">
	/**
	 * Test of validateStringWithRegex method, of class Validator.
	 * Passing Perfect Input (Regex matching input string)
	 * Should return true
	 */
	@Test
	public void testValidateStringWithRegex_PerfectInput() {
		boolean expResult = true;
		String inputString = "MatchThisString";
		String regex = "^.*$";
		
		Validator instance = new Validator();
		boolean result = instance.validateStringWithRegex(inputString, regex, true);
		
		System.out.println("testValidateStringWithRegex_PerfectInput - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
	}
	
	/**
	 * Test of validateStringWithRegex method, of class Validator.
	 * Passing Bad Input (Regex NOT matching input string)
	 * Should return false
	 */
	@Test
	public void testValidateStringWithRegex_NoMatch() {
		boolean expResult = false;
		String inputString = "DoNotMatchThisString";
		String regex = "^\\d.*$";
		
		Validator instance = new Validator();
		boolean result = instance.validateStringWithRegex(inputString, regex, true);
		
		System.out.println("testValidateStringWithRegex_NoMatch - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
	}
	
	/**
	 * Test of validateStringWithRegex method, of class Validator.
	 * Passing Empty String
	 * Should return true
	 */
	@Test
	public void testValidateStringWithRegex_EmptyString() {
		boolean expResult = true;
		String inputString = "";
		String regex = "^.*$";
		
		Validator instance = new Validator();
		boolean result = instance.validateStringWithRegex(inputString, regex, true);
		
		System.out.println("testValidateStringWithRegex_EmptyString - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
	}
	
	/**
	 * Test of validateStringWithRegex method, of class Validator.
	 * Passing Null String
	 * Should return false
	 */
	@Test
	public void testValidateStringWithRegex_NullString() {
		boolean expResult = false;
		String inputString = null;
		String regex = "^.+$";
		
		Validator instance = new Validator();
		boolean result = instance.validateStringWithRegex(inputString, regex, true);
		
		System.out.println("testValidateStringWithRegex_NullString - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
	}
	
	/**
	 * Test of validateStringWithRegex method, of class Validator.
	 * Passing Empty Regex
	 * Should return false
	 */
	@Test
	public void testValidateStringWithRegex_EmptyRegex() {
		boolean expResult = false;
		String inputString = "DoNotMatchThisString";
		String regex = "";
		
		Validator instance = new Validator();
		boolean result = instance.validateStringWithRegex(inputString, regex, true);
		
		System.out.println("testValidateStringWithRegex_EmptyRegex - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
	}
	
	/**
	 * Test of validateStringWithRegex method, of class Validator.
	 * Passing Null Regex
	 * Should return false
	 */
	@Test
	public void testValidateStringWithRegex_NullRegex() {
		boolean expResult = false;
		String inputString = "DoNotMatchThisString";
		String regex = null;
		
		Validator instance = new Validator();
		boolean result = instance.validateStringWithRegex(inputString, regex, true);
		
		System.out.println("testValidateStringWithRegex_NullRegex - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
	}
	// </editor-fold>
	
	// <editor-fold defaultstate="expanded" desc="validateDate tests. Click on the + sign on the left to edit the code.">
	/**
	 * Test of validateDate method, of class Validator.
	 * Passing Perfect Input (Valid Date)
	 * Should return valid date and shouldn't have anything in the Error Map
	 * @throws java.text.ParseException
	 */
	@Test
	public void testValidateDate_PerfectInput() throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date expResult = df.parse("2014-08-21");
		String inputString = "2014-08-21";
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		
		Validator instance = new Validator();
		Date result = instance.validateDate(inputString, fieldName);
		
		System.out.println("testValidateDate_PerfectInput - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateDate_PerfectInput - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validateDate method, of class Validator.
	 * Passing Bad Input (Invalid Date)
	 * Should return null but should have an error in the Error Map
	 * @throws java.text.ParseException
	 */
	@Test
	public void testValidateDate_BadDate() throws ParseException {
		Date expResult = null;
		String inputString = "2014-0821a";
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName,"Nickname is not a valid date.");
		
		Validator instance = new Validator();
		Date result = instance.validateDate(inputString, fieldName);
		
		System.out.println("testValidateDate_BadDate - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateDate_BadDate - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validateDate method, of class Validator.
	 * Passing Empty Date
	 * Should return null but should have an error in the Error Map
	 * @throws java.text.ParseException
	 */
	@Test
	public void testValidateDate_EmptyDate() throws ParseException {
		Date expResult = null;
		String inputString = "";
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName,"Nickname is not a valid date.");
		
		Validator instance = new Validator();
		Date result = instance.validateDate(inputString, fieldName);
		
		System.out.println("testValidateDate_EmptyDate - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateDate_EmptyDate - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validateDate method, of class Validator.
	 * Passing Null Date
	 * Should return null but should have an error in the Error Map
	 * @throws java.text.ParseException
	 */
	@Test
	public void testValidateDate_NullDate() throws ParseException {
		Date expResult = null;
		String inputString = null;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName,"Nickname is not a valid date.");
		
		Validator instance = new Validator();
		Date result = instance.validateDate(inputString, fieldName);
		
		System.out.println("testValidateDate_NullDate - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateDate_NullDate - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	// </editor-fold>
	
	// <editor-fold defaultstate="expanded" desc="validateConfirmation tests. Click on the + sign on the left to edit the code.">
	/**
	 * Test of validateConfirmation method, of class Validator.
	 * Passing Perfect Input (equal strings)
	 * Should return the string and shouldn't have anything in the Error Map
	 */
	@Test
	public void testValidateConfirmation_PerfectInput() {
		String expResult = "OnePerfectString1!2@3#4$5%6¨";
		String originalString = expResult;
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();

		Validator instance = new Validator();
		String result = instance.validateConfirmation(inputString, originalString, fieldName);
		
		System.out.println("testValidateConfirmation_PerfectInput - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateConfirmation_PerfectInput - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validateConfirmation method, of class Validator.
	 * Passing Bad Input (different strings)
	 * Should return the string but should have an error in the error map
	 */
	@Test
	public void testValidateConfirmation_BadInput() {
		String expResult = "OnePerfectString1!2@3#4$5%6¨";
		String originalString = expResult+"a";
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		MockitoAnnotations.initMocks(this);
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName, "Nickname doesn't match.");

		Validator instance = new Validator();
		String result = instance.validateConfirmation(inputString, originalString, fieldName);
		
		System.out.println("testValidateConfirmation_BadInput - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateConfirmation_BadInput - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validateConfirmation method, of class Validator.
	 * Passing Empty Input (equal strings)
	 * Should return the string and shouldn't have anything in the Error Map
	 */
	@Test
	public void testValidateConfirmation_EmptyInput() {
		String expResult = "";
		String originalString = expResult;
		String inputString = expResult;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();

		Validator instance = new Validator();
		String result = instance.validateConfirmation(inputString, originalString, fieldName);
		
		System.out.println("testValidateConfirmation_EmptyInput - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateConfirmation_EmptyInput - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validateConfirmation method, of class Validator.
	 * Passing Null Input and Original String
	 * Should return an empty string and shouldn't have anything in the Error Map
	 */
	@Test
	public void testValidateConfirmation_NullInputAndOriginalString() {
		String expResult = "";
		String originalString = null;
		String inputString = originalString;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();

		Validator instance = new Validator();
		String result = instance.validateConfirmation(inputString, originalString, fieldName);
		
		System.out.println("testValidateConfirmation_NullInputAndOriginalString - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateConfirmation_NullInputAndOriginalString - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validateConfirmation method, of class Validator.
	 * Passing Null Original String (different strings)
	 * Should return an empty string but should have an error in the error map
	 */
	@Test
	public void testValidateConfirmation_NullOriginalString() {
		String expResult = "";
		String originalString = expResult;
		String inputString = null;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();

		Validator instance = new Validator();
		String result = instance.validateConfirmation(inputString, originalString, fieldName);
		
		System.out.println("testValidateConfirmation_EmptyInput - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateConfirmation_EmptyInput - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	// </editor-fold>
	
	// <editor-fold defaultstate="expanded" desc="validateBoolean tests. Click on the + sign on the left to edit the code.">
	/**
	 * Test of validateBoolean method, of class Validator.
	 * Passing True Input
	 * Should return true
	 */
	@Test
	public void testValidateBoolean_TrueInput() {
		boolean expResult = true;
		String inputString = "true";

		Validator instance = new Validator();
		boolean result = instance.validateBoolean(inputString);
		
		System.out.println("testValidateBoolean_TrueInput - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
	}
	
	/**
	 * Test of validateBoolean method, of class Validator.
	 * Passing False Input
	 * Should return false
	 */
	@Test
	public void testValidateBoolean_FalseInput() {
		boolean expResult = false;
		String inputString = "false";

		Validator instance = new Validator();
		boolean result = instance.validateBoolean(inputString);
		
		System.out.println("testValidateBoolean_FalseInput - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
	}
	
	/**
	 * Test of validateBoolean method, of class Validator.
	 * Passing Random Input
	 * Should return true
	 */
	@Test
	public void testValidateBoolean_RandomInput() {
		boolean expResult = true;
		String inputString = "OneRandomString";

		Validator instance = new Validator();
		boolean result = instance.validateBoolean(inputString);
		
		System.out.println("testValidateBoolean_RandomInput - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
	}
	
	/**
	 * Test of validateBoolean method, of class Validator.
	 * Passing Empty Input
	 * Should return false
	 */
	@Test
	public void testValidateBoolean_EmptyInput() {
		boolean expResult = false;
		String inputString = "";

		Validator instance = new Validator();
		boolean result = instance.validateBoolean(inputString);
		
		System.out.println("testValidateBoolean_EmptyInput - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
	}
	
	/**
	 * Test of validateBoolean method, of class Validator.
	 * Passing Null Input
	 * Should return false
	 */
	@Test
	public void testValidateBoolean_NullInput() {
		boolean expResult = false;
		String inputString = null;

		Validator instance = new Validator();
		boolean result = instance.validateBoolean(inputString);
		
		System.out.println("testValidateBoolean_NullInput - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
	}
	// </editor-fold>

	// <editor-fold defaultstate="expanded" desc="validateGender tests. Click on the + sign on the left to edit the code.">
	/**
	 * Test of validateGender method, of class Validator.
	 * Passing Perfect Input (Male)
	 * Should return true and shouldn't have anything in the Error Map
	 */
	@Test
	public void testValidateGender_PerfectInputMale() {
		Boolean expResult = true;
		String inputString = "male";
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();

		Validator instance = new Validator();
		Boolean result = instance.validateGender(inputString, fieldName);
		
		System.out.println("testValidateGender_PerfectInputMale - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateGender_PerfectInputMale - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validateGender method, of class Validator.
	 * Passing Perfect Input (Female)
	 * Should return false and shouldn't have anything in the Error Map
	 */
	@Test
	public void testValidateGender_PerfectInputFemale() {
		Boolean expResult = false;
		String inputString = "female";
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();

		Validator instance = new Validator();
		Boolean result = instance.validateGender(inputString, fieldName);
		
		System.out.println("testValidateGender_PerfectInputFemale - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateGender_PerfectInputFemale - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validateGender method, of class Validator.
	 * Passing Empty Input
	 * Should return null and should have an error in the error map
	 */
	@Test
	public void testValidateGender_EmptyInput() {
		Boolean expResult = null;
		String inputString = "";
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName,"Nickname is not a valid gender.");

		Validator instance = new Validator();
		Boolean result = instance.validateGender(inputString, fieldName);
		
		System.out.println("testValidateGender_EmptyInput - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateGender_EmptyInput - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	
	/**
	 * Test of validateGender method, of class Validator.
	 * Passing Null Input
	 * Should return null and should have an error in the error map
	 */
	@Test
	public void testValidateGender_NullInput() {
		Boolean expResult = null;
		String inputString = null;
		
		String fieldName = "nickname";
		
		HashMap<String,String> expErrorMap = new HashMap<>();
		expErrorMap.put(fieldName,"Nickname is not a valid gender.");

		Validator instance = new Validator();
		Boolean result = instance.validateGender(inputString, fieldName);
		
		System.out.println("testValidateGender_EmptyInput - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		
		HashMap errorMap = instance.getErrorMap();
		System.out.println("testValidateGender_EmptyInput - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}
	// </editor-fold>
	
}
