/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.partyplanner.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 *
 * @author gustavo
 */
public class HomeServletNGTest {
	
	private Map attributes;
	private Map parameters;
	
	public HomeServletNGTest() {
	}

	@org.testng.annotations.BeforeClass
	public static void setUpClass() throws Exception {
	}

	@org.testng.annotations.AfterClass
	public static void tearDownClass() throws Exception {
	}

	/**
	 * Test of processRequest method, of class HomeServlet.
	 * @throws java.lang.Exception
	 */
	@org.testng.annotations.Test
	public void testProcessRequest() throws Exception {
		System.out.println("processRequest");
		
		attributes = new HashMap();
		parameters = new HashMap();
		
		HttpServletRequest request;
		request = mock(HttpServletRequest.class);
		
		when(request.getParameter(anyString())).thenAnswer(new Answer() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				String key = (String) invocation.getArguments()[0];
 
                return parameters.get(key);
			}
		});
		
		when(request.getAttribute(anyString())).thenAnswer(new Answer() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				String key = (String) invocation.getArguments()[0];
 
                return parameters.get(key);
			}
		});
		
		HttpServletResponse response;
		response = mock(HttpServletResponse.class);
		
		RequestDispatcher dispatcher;
		dispatcher = mock(RequestDispatcher.class);
		
//		when(request.getPathInfo()).thenReturn("/");
		
		StringWriter sw = new StringWriter();
		PrintWriter pw  = new PrintWriter(sw);
		
		when(response.getWriter()).thenReturn(pw);
		
		when(request.getRequestDispatcher("/WEB-INF/view/home.jsp")).thenReturn(dispatcher);
		
//		HomeServlet instance = new HomeServlet();
//		instance.processRequest(request, response);
		
		String result = sw.getBuffer().toString().trim();
	}
	
}
