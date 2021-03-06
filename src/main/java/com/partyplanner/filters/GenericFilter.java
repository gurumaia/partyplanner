/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.partyplanner.filters;

import com.partyplanner.model.UserBeanLocal;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

/**
 * This filter adds useful generic information to the request
 * 
 */
@WebFilter(filterName = "GenericFilter", urlPatterns = {"/*"})
public class GenericFilter implements Filter {
	
	private static final Logger logger = LogManager.getLogger(GenericFilter.class.getName());
	
	@EJB
	UserBeanLocal user;

	// The filter configuration object we are associated with.  If
	// this value is null, this filter instance is not currently
	// configured. 
	private FilterConfig filterConfig = null;
	
	public GenericFilter() {
	}
	
	/**
	 * Generic filter processing - preServlet.
	 * <p>
	 * Adds logging information to the log4j thread context object (uuid, user's email and session id).
	 * Adds a boolean to the request showing if the user's logged in.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException 
	 */
	private void doBeforeProcessing(RequestWrapper request, ResponseWrapper response)
			throws IOException, ServletException {
		// Logging information
		ThreadContext.put("uuid",UUID.randomUUID().toString());
		ThreadContext.put("user", request.getRemoteUser());
		ThreadContext.put("sessionId", request.getSession().getId());
		
		// Is user logged in?
		Boolean loggedIn;
		loggedIn = (request.getUserPrincipal() != null);
		request.setAttribute("loggedIn",loggedIn);
		if (loggedIn) {
			logger.debug("User is logged in.");
			HttpSession session = request.getSession();
			if ( session.getAttribute("user_first_name") == null ) {
				// User just logged in. Populate session.
				logger.debug("User just logged in, session isn't populated. Populate now.");
				try {
					logger.debug("Set user first name");
					session.setAttribute("user_first_name", user.getFirstName(request.getRemoteUser()));
				} catch (Exception e) {
					logger.error("There was an unexpected error accessing the UserBean model while populating the HttpSession",e);
				}
			}
		}
			
		logger.debug("DoBeforeProcessing");
	}	
	
	private void doAfterProcessing(RequestWrapper request, ResponseWrapper response)
			throws IOException, ServletException {
		ThreadContext.clearAll();
	}

	/**
	 *
	 * @param request The servlet request we are processing
	 * @param response The servlet response we are creating
	 * @param chain The filter chain we are processing
	 *
	 * @exception IOException if an input/output error occurs
	 * @exception ServletException if a servlet error occurs
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain)
			throws IOException, ServletException {
		
		

	// Create wrappers for the request and response objects.
		// Using these, you can extend the capabilities of the
		// request and response, for example, allow setting parameters
		// on the request before sending the request to the rest of the filter chain,
		// or keep track of the cookies that are set on the response.
		//
		// Caveat: some servers do not handle wrappers very well for forward or
		// include requests.
		RequestWrapper wrappedRequest = new RequestWrapper((HttpServletRequest) request);
		ResponseWrapper wrappedResponse = new ResponseWrapper((HttpServletResponse) response);
		
		doBeforeProcessing(wrappedRequest, wrappedResponse);
		
		Throwable problem = null;
		
		try {
			chain.doFilter(wrappedRequest, wrappedResponse);
		} catch (Throwable t) {
		// If an exception is thrown somewhere down the filter chain,
			// we still want to execute our after processing, and then
			// rethrow the problem after that.
			problem = t;
			t.printStackTrace();
		}
		
		doAfterProcessing(wrappedRequest, wrappedResponse);

	// If there was a problem, we want to rethrow it if it is
		// a known type, otherwise log it.
		if (problem != null) {
			if (problem instanceof ServletException) {
				throw (ServletException) problem;
			}
			if (problem instanceof IOException) {
				throw (IOException) problem;
			}
			sendProcessingError(problem, response);
		}
	}

	/**
	 * Return the filter configuration object for this filter.
	 */
	public FilterConfig getFilterConfig() {
		return (this.filterConfig);
	}

	/**
	 * Set the filter configuration object for this filter.
	 *
	 * @param filterConfig The filter configuration object
	 */
	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	/**
	 * Destroy method for this filter
	 */
	public void destroy() {		
	}

	/**
	 * Init method for this filter
	 */
	public void init(FilterConfig filterConfig) {		
		this.filterConfig = filterConfig;
		if (filterConfig != null) {
			logger.debug("Initializing filter");
		}
	}

	/**
	 * Return a String representation of this object.
	 */
	@Override
	public String toString() {
		if (filterConfig == null) {
			return ("GenericFilter()");
		}
		StringBuffer sb = new StringBuffer("GenericFilter(");
		sb.append(filterConfig);
		sb.append(")");
		return (sb.toString());
		
	}
	
	private void sendProcessingError(Throwable t, ServletResponse response) {
		String stackTrace = getStackTrace(t);		
		
		if (stackTrace != null && !stackTrace.equals("")) {
			try {
				response.setContentType("text/html");
				PrintStream ps = new PrintStream(response.getOutputStream());
				PrintWriter pw = new PrintWriter(ps);				
				pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

				// PENDING! Localize this for next official release
				pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");				
				pw.print(stackTrace);				
				pw.print("</pre></body>\n</html>"); //NOI18N
				pw.close();
				ps.close();
				response.getOutputStream().close();
			} catch (Exception ex) {
			}
		} else {
			try {
				PrintStream ps = new PrintStream(response.getOutputStream());
				t.printStackTrace(ps);
				ps.close();
				response.getOutputStream().close();
			} catch (Exception ex) {
			}
		}
	}
	
	public static String getStackTrace(Throwable t) {
		String stackTrace = null;
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			pw.close();
			sw.close();
			stackTrace = sw.getBuffer().toString();
		} catch (Exception ex) {
		}
		return stackTrace;
	}
	
	public void log(String msg) {
		filterConfig.getServletContext().log(msg);		
	}

	/**
	 * This request wrapper class extends the support class
	 * HttpServletRequestWrapper, which implements all the methods in the
	 * HttpServletRequest interface, as delegations to the wrapped request. You
	 * only need to override the methods that you need to change. You can get
	 * access to the wrapped request using the method getRequest()
	 */
	class RequestWrapper extends HttpServletRequestWrapper {
		
		public RequestWrapper(HttpServletRequest request) {
			super(request);
		}

	// You might, for example, wish to add a setParameter() method. To do this
		// you must also override the getParameter, getParameterValues, getParameterMap,
		// and getParameterNames methods.
		protected Hashtable localParams = null;
		
		public void setParameter(String name, String[] values) {
			logger.debug("setParameter(" + name + "=" + values + ")" + " localParams = " + localParams);
			
			if (localParams == null) {
				localParams = new Hashtable();
				// Copy the parameters from the underlying request.
				Map wrappedParams = getRequest().getParameterMap();
				Set keySet = wrappedParams.keySet();
				for (Iterator it = keySet.iterator(); it.hasNext();) {
					Object key = it.next();
					Object value = wrappedParams.get(key);
					localParams.put(key, value);
				}
			}
			localParams.put(name, values);
		}
		
		@Override
		public String getParameter(String name) {
			logger.debug("getParameter(" + name + ") localParams = " + localParams);
			if (localParams == null) {
				return getRequest().getParameter(name);
			}
			Object val = localParams.get(name);
			if (val instanceof String) {
				return (String) val;
			}
			if (val instanceof String[]) {
				String[] values = (String[]) val;
				return values[0];
			}
			return (val == null ? null : val.toString());
		}
		
		@Override
		public String[] getParameterValues(String name) {
			logger.debug("getParameterValues(" + name + ") localParams = " + localParams);
			if (localParams == null) {
				return getRequest().getParameterValues(name);
			}
			return (String[]) localParams.get(name);
		}
		
		@Override
		public Enumeration<String> getParameterNames() {
			logger.debug("getParameterNames() localParams = " + localParams);
			if (localParams == null) {
				return getRequest().getParameterNames();
			}
			return localParams.keys();
		}		
		
		@Override
		public Map<String,String[]> getParameterMap() {
			logger.debug("getParameterMap() localParams = " + localParams);
			if (localParams == null) {
				return getRequest().getParameterMap();
			}
			return localParams;
		}
	}

	/**
	 * This response wrapper class extends the support class
	 * HttpServletResponseWrapper, which implements all the methods in the
	 * HttpServletResponse interface, as delegations to the wrapped response.
	 * You only need to override the methods that you need to change. You can
	 * get access to the wrapped response using the method getResponse()
	 */
	class ResponseWrapper extends HttpServletResponseWrapper {
		
		public ResponseWrapper(HttpServletResponse response) {
			super(response);			
		}

	// You might, for example, wish to know what cookies were set on the response
		// as it went throught the filter chain. Since HttpServletRequest doesn't
		// have a get cookies method, we will need to store them locally as they
		// are being set.
	/*
		 protected Vector cookies = null;
	
		 // Create a new method that doesn't exist in HttpServletResponse
		 public Enumeration getCookies() {
		 if (cookies == null)
		 cookies = new Vector();
		 return cookies.elements();
		 }
	
		 // Override this method from HttpServletResponse to keep track
		 // of cookies locally as well as in the wrapped response.
		 public void addCookie (Cookie cookie) {
		 if (cookies == null)
		 cookies = new Vector();
		 cookies.add(cookie);
		 ((HttpServletResponse)getResponse()).addCookie(cookie);
		 }
		 */
	}
	
}
