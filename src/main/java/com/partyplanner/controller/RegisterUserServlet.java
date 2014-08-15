/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.partyplanner.controller;

import com.partyplanner.model.UserBeanLocal;
import com.partyplanner.util.Validator;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author gustavo
 */
@WebServlet(name = "RegisterUserServlet", urlPatterns = {"/RegisterUser"})
public class RegisterUserServlet extends HttpServlet {
	
	private static final Logger logger = LogManager.getLogger(Validator.class.getName());

	/**
	 * User input variables
	 */
	protected String nickname;
	protected String firstName;
	protected String lastName;
	protected String email;
	protected String emailConfirmation;
	protected String password;
	protected String passwordConfirmation;
	protected Date birthDate;
	protected Boolean optin;
	protected Boolean gender;
	protected String ipAddress;
	
	/**
	 * Internal variables
	 */
	protected HashMap messages = new HashMap();
	@EJB
	UserBeanLocal user;
			
	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			/**
			 * Validate input
			 */
			logger.debug("Processing RegisterUserServlet.");
			parseInput(request);
			
			if(user.isUserRegistered(email))
			{
				logger.info("E-mail address ("+email+") is already in use.");
				messages.put("email", "This e-mail address is already in use.");
			}
			if(user.isNicknameTaken(nickname))
			{
				logger.info("Nickname ("+nickname+") is already in use.");
				messages.put("nickname", "This nickname is already in use.");
			}
			if(!messages.isEmpty())
			{
				logger.info("Validation errors on registration for user: "+nickname);
				messages.put("alert", "Please correct the fields below:");
				request.getRequestDispatcher("/WEB-INF/view/registration.jsp").forward(request, response);
			}

			boolean retVal = user.registerUser(nickname, firstName, lastName, email, password, birthDate, optin, gender, ipAddress);
			if (retVal) {
				logger.info("User "+nickname+" registered successfully.");
				request.getRequestDispatcher("/WEB-INF/view/registration_success.jsp").include(request, response);
			} else {
				logger.info("Error registering user "+nickname);
				messages.put("alert", "Unknown error registering user. Please try again.");
				request.getRequestDispatcher("/WEB-INF/view/registration.jsp").include(request, response);
			}
			
		}
	}
	
	
	protected void parseInput(HttpServletRequest request) {
		logger.debug("Entering method parseInput.");
		Validator vdt = new Validator();
		nickname = vdt.validateStringWithLength(request.getParameter("nickname"), "nickname", 3, 32 );
		firstName = vdt.validateStringWithLength(request.getParameter("first_name"), "first_name", 3, 35 );
		lastName = vdt.validateStringWithLength(request.getParameter("last_name"), "last_name", 0, 35 );
		email = vdt.validateEmail( request.getParameter("email") , "email");
		emailConfirmation = vdt.validateConfirmation(request.getParameter("email_confirmation"), email, "email_confirmation");
		password = vdt.validatePassword(request.getParameter("password"), "password");
		passwordConfirmation = vdt.validateConfirmation(request.getParameter("password_confirmation"), password, "password_confirmation");
		birthDate = vdt.validateDate(request.getParameter("birth_date"), "birth_date");
		optin = vdt.validateBoolean(request.getParameter("optin") );
		gender = vdt.validateGender(request.getParameter("gender"), "gender");
		ipAddress = vdt.sanityzeString(request.getRemoteAddr() );
		messages = vdt.getErrorMap();
		request.setAttribute("messages", messages);
		request.setAttribute("nickname", nickname);
		request.setAttribute("first_name", firstName);
		request.setAttribute("last_name", lastName);
		request.setAttribute("email", email);
		request.setAttribute("email_confirmation", emailConfirmation);
		request.setAttribute("password", password);
		request.setAttribute("password_confirmation", passwordConfirmation);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String birthDateString = birthDate != null ? df.format(birthDate) : "none";
		request.setAttribute("birth_date", birthDateString);
		request.setAttribute("optin", optin);
		request.setAttribute("gender", gender);
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		processRequest(request, response);
//	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
