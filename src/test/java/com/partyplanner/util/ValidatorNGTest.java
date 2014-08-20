/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.partyplanner.util;

import java.util.Date;
import java.util.HashMap;
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
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	/**
	 * Test of sanityzeString method, of class Validator.
	 * Passing perfect string
	 * Should return the same string
	 */
	@Test
	public void testSanityzeString_PerfectString() {
//		System.out.println("testSanityzeString_PerfectString");
		
		String str = "OnePerfectString1!2@3#4$5%6¨";
		String expResult = "OnePerfectString1!2@3#4$5%6¨";
		
		Validator instance = new Validator();
		String result = instance.sanityzeString(str, false);
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
		
		String str = "  TrimThisString1!2@3#4$5%6¨  ";
		String expResult = "TrimThisString1!2@3#4$5%6¨";
		
		Validator instance = new Validator();
		String result = instance.sanityzeString(str, false);
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
		
		String str = null;
		String expResult = null;
		
		Validator instance = new Validator();
		String result = instance.sanityzeString(str, false);
		assertEquals(result, expResult);
		System.out.println("testSanityzeString_Null - Expected '"+expResult+"' and got '"+result+"'");
	}

	/**
	 * Test of validateStringWithLength method, of class Validator.
	 * Passing Perfect Input (Perfect String, low minLength, high maxLength)
	 * Should return the string and shouldn't have anything in the Error Map
	 */
	@Test
	public void testValidateStringWithLength_PerfectInput() {
		
		
		String str = "OnePerfectString1!2@3#4$5%6¨";
		String expResult = "OnePerfectString1!2@3#4$5%6¨";
		
		String fieldName = "test_field";
		int minLength = 1;
		int maxLength = 100;

		Validator instance = new Validator();
		String result = instance.validateStringWithLength(str, fieldName, minLength, maxLength, false);
		
		System.out.println("testValidateStringWithLength_PerfectInput - Expected '"+expResult+"' and got '"+result+"'");
		assertEquals(result, expResult);
		HashMap errorMap = instance.getErrorMap();
		HashMap expErrorMap = new HashMap();
		System.out.println("testValidateStringWithLength_PerfectInput - Expected '"+expErrorMap.toString()+"' and got '"+errorMap.toString()+"'");
		assertEquals(errorMap, expErrorMap);
	}

	/**
	 * Test of validateEmail method, of class Validator.
	 */
	@Test
	public void testValidateEmail() {
		System.out.println("validateEmail");
		String email = "";
		String fieldName = "";
		Validator instance = new Validator();
		String expResult = "";
		String result = instance.validateEmail(email, fieldName);
		assertEquals(result, expResult);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of validatePassword method, of class Validator.
	 */
	@Test
	public void testValidatePassword() {
		System.out.println("validatePassword");
		String password = "";
		String fieldName = "";
		Validator instance = new Validator();
		String expResult = "";
		String result = instance.validatePassword(password, fieldName);
		assertEquals(result, expResult);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of validateStringWithRegex method, of class Validator.
	 */
	@Test
	public void testValidateStringWithRegex() {
		System.out.println("validateStringWithRegex");
		String str = "";
		String regex = "";
		String fieldName = "";
		boolean caseInsensitive = false;
		Validator instance = new Validator();
		boolean expResult = false;
		boolean result = instance.validateStringWithRegex(str, regex, fieldName, caseInsensitive);
		assertEquals(result, expResult);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of validateDate method, of class Validator.
	 */
	@Test
	public void testValidateDate() {
		System.out.println("validateDate");
		String date = "";
		String fieldName = "";
		Validator instance = new Validator();
		Date expResult = null;
		Date result = instance.validateDate(date, fieldName);
		assertEquals(result, expResult);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of validateConfirmation method, of class Validator.
	 */
	@Test
	public void testValidateConfirmation_3args() {
		System.out.println("validateConfirmation");
		String confirmationString = "";
		String originalString = "";
		String fieldName = "";
		Validator instance = new Validator();
		String expResult = "";
		String result = instance.validateConfirmation(confirmationString, originalString, fieldName);
		assertEquals(result, expResult);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of validateConfirmation method, of class Validator.
	 */
	@Test
	public void testValidateConfirmation_4args() {
		System.out.println("validateConfirmation");
		String confirmationString = "";
		String originalString = "";
		String fieldName = "";
		boolean isPassword = false;
		Validator instance = new Validator();
		String expResult = "";
		String result = instance.validateConfirmation(confirmationString, originalString, fieldName, isPassword);
		assertEquals(result, expResult);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of validateBoolean method, of class Validator.
	 */
	@Test
	public void testValidateBoolean() {
		System.out.println("validateBoolean");
		String booleanValue = "";
		Validator instance = new Validator();
		Boolean expResult = null;
		Boolean result = instance.validateBoolean(booleanValue);
		assertEquals(result, expResult);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of validateGender method, of class Validator.
	 */
	@Test
	public void testValidateGender() {
		System.out.println("validateGender");
		String gender = "";
		String fieldName = "";
		Validator instance = new Validator();
		Boolean expResult = null;
		Boolean result = instance.validateGender(gender, fieldName);
		assertEquals(result, expResult);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getErrorMap method, of class Validator.
	 */
	@Test
	public void testGetErrorMap() {
		System.out.println("getErrorMap");
		Validator instance = new Validator();
		HashMap expResult = null;
		HashMap result = instance.getErrorMap();
		assertEquals(result, expResult);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}
	
}
