/*
 * MockFrame2TestCase - a JUnit extension for testing Frame2 events and event
 * handlers in the context of the HttpFrontController. Based on StrutsTestCase
 * by Deryl Sealef This library is free software; you can redistribute it and/or
 * modify it under the terms of the Apache Software License as published by the
 * Apache Software Foundation; either version 1.1 of the License, or (at your
 * option) any later version. This library is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Apache
 * Software Foundation Licens for more details. You may view the full text here:
 * http: www.apache.org/LICENSE.txt
 */

package servletunit.frame2;

import java.io.File;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.apache.commons.digester.Digester;
import org.megatome.frame2.front.HttpFrontController;
import org.megatome.frame2.front.Frame2ContextListener;
import org.megatome.frame2.log.LoggerFactory;

import servletunit.HttpServletResponseSimulator;
import servletunit.ServletContextSimulator;

// NIT: cleanup description

/**
 * MockFrame2TestCase is an extension of the base JUnit testcase that provides
 * additional methods to aid in testing WAM events and event handlers. It uses a
 * mock object approach to simulate a servlet container, and tests the execution
 * of events as they are actually run through the WAM HttpFrontController.
 * WamTestCase provides methods that set up the request path, request parameters
 * for event subclasses, as well as methods that can verify that the correct
 * ActionForward was used and that the proper ActionError messages were
 * supplied. <br>
 * <br>
 * <b>NOTE: </b> By default, the Struts ActionServlet will look for the file
 * <code>WEB-INF/struts-config.xml</code>, so you must place the directory
 * that <i>contains </i> WEB-INF in your CLASSPATH. If you would like to use an
 * alternate configuration file, please see the setConfigFile() method for
 * details on how this file is located.
 */

public class MockFrame2TestCase extends TestCase {

    /**
     * Constructor for MockFrame2TestCase.
     * @param name
     */
    public MockFrame2TestCase(String name) {
        super(name);
    }

    //HttpServletRequestSimulator request;
    Frame2HttpServletRequestSimulator request;

    HttpServletResponseSimulator response;

    MockFrame2ServletContextSimulator context;

    MockFrame2ServletConfigSimulator config;

    ServletContextListener listener;

    String eventPath;

    boolean isInitialized;

    boolean servletIsInitialized;

    HttpFrontController servlet;

    /**
     * A check that every method should run to ensure that the base class setUp
     * method has been called.
     */

    private void confirmSetup() {
        if (!isInitialized) {
            throw new AssertionFailedError(
                    "You are overriding the setUp() method without calling super.setUp().  You must call the superclass setUp() method in your TestCase subclass to ensure proper initialization.");
        }
    }

    /**
     * Sets up the test fixture for this test. This method creates an instance
     * of the ActionServlet, initializes it to validate forms and turn off
     * debugging, and creates a mock HttpServletRequest and HttpServletResponse
     * object to use in this test.
     */
    protected void setUp() throws Exception {
        if (servlet == null) {
            servlet = new HttpFrontController();
        }
        LoggerFactory.setType("org.megatome.frame2.log.impl.StandardLogger",
                getClass().getClassLoader());
        config = new MockFrame2ServletConfigSimulator();
        //request = new
        // HttpServletRequestSimulator(config.getServletContext());
        request = new Frame2HttpServletRequestSimulator(config
                .getServletContext());
        response = new HttpServletResponseSimulator();
        context = (MockFrame2ServletContextSimulator)config
                .getServletContext();
        listener = new Frame2ContextListener();
        isInitialized = true;

        sendContextInitializedEvent(null, null);

        servlet.init(config);
    }

    public ServletContextListener getContextListener() {
        return listener;
    }

    public void sendContextInitializedEvent(String key, String value) {
        if (key != null && value != null) {
            context.setInitParameter(key, value);
        }
        listener.contextInitialized(new ServletContextEvent(context));
    }

    public void sendContextDestroyedEvent() {
        listener.contextDestroyed(new ServletContextEvent(context));
    }

    public void sendContextInitializedEvent(String key1, String value1,
            String key2, String value2) {
        if (key1 != null && value1 != null) {
            context.setInitParameter(key1, value1);
        }

        if (key2 != null && value2 != null) {
            context.setInitParameter(key2, value2);
        }

        listener.contextInitialized(new ServletContextEvent(context));
    }

    /**
     * Returns an HttpServletRequest object that can be used in this test.
     */
    public HttpServletRequest getRequest() {
        confirmSetup();
        return request;
    }

    // 	/**
    // 	 * Returns a HttpServletRequestWrapper object that can be used
    // 	 * in this test. Note that if {@link #setRequestWrapper} has not been
    // 	 * called, this method will return an instance of
    // 	 * javax.servlet.http.HttpServletRequestWrapper.
    // 	 */
    // 	public HttpServletRequestWrapper getRequestWrapper() {
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Entering getRequestWrapper()");
    // 		init();
    // 		if (requestWrapper == null) {
    // 			if (logger.isDebugEnabled())
    // 				logger.debug("Exiting getRequestWrapper()");
    // 			return new HttpServletRequestWrapper(request);
    // 		} else {
    // 			if (logger.isDebugEnabled()) {
    // 				logger.debug(
    // 					"getRequestWrapper() : wrapper class is '" + requestWrapper.getClass() +
    // "'");
    // 			}
    // 			if (logger.isDebugEnabled())
    // 				logger.debug("Exiting getRequestWrapper()");
    // 			return requestWrapper;
    // 		}
    // 	}

    // 	/**
    // 	 * Set this TestCase to use a given HttpServletRequestWrapper
    // 	 * class when calling Action.perform(). Note that if this
    // 	 * method is not called, then the normal HttpServletRequest
    // 	 * object is used.
    // 	 *
    // 	 * @param wrapper an HttpServletRequestWrapper object to be
    // 	 * used when calling Action.perform().
    // 	 */
    // 	public void setRequestWrapper(HttpServletRequestWrapper wrapper) {
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Entering setRequestWrapper() : wrapper = " + wrapper);
    // 		init();
    // 		if (wrapper == null)
    // 			throw new IllegalArgumentException("wrapper class cannot be null!");
    // 		else {
    // 			if (wrapper.getRequest() == null)
    // 				wrapper.setRequest(request);
    // 			_requestWrapper = wrapper;
    // 		}
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Exiting setRequestWrapper()");
    // 	}

    /**
     * Returns an HttpServletResponse object that can be used in this test.
     */
    public HttpServletResponse getResponse() {
        confirmSetup();
        return response;
    }

    // 	/**
    // 	 * Returns an HttpServletResponseWrapper object that can be used in
    // 	 * this test. Note that if {@link #setResponseWrapper} has not been
    // 	 * called, this method will return an instance of
    // 	 * javax.servlet.http.HttpServletResponseWrapper.
    // 	 */
    // 	public HttpServletResponseWrapper getResponseWrapper() {
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Entering getResponseWrapper()");
    // 		init();
    // 		if (responseWrapper == null) {
    // 			if (logger.isDebugEnabled())
    // 				logger.debug("Exiting getResponseWrapper()");
    // 			return new HttpServletResponseWrapper(response);
    // 		} else {
    // 			if (logger.isDebugEnabled()) {
    // 				logger.debug(
    // 					"getRequestWrapper() : wrapper class is '" + responseWrapper.getClass() +
    // "'");
    // 			}
    // 			if (logger.isDebugEnabled())
    // 				logger.debug("Exiting getResponseWrapper()");
    // 			return responseWrapper;
    // 		}
    // 	}

    // 	/**
    // 	 * Set this TestCase to use a given HttpServletResponseWrapper
    // 	 * class when calling Action.perform(). Note that if this
    // 	 * method is not called, then the normal HttpServletResponse
    // 	 * object is used.
    // 	 *
    // 	 * @param wrapper an HttpServletResponseWrapper object to be
    // 	 * used when calling Action.perform().
    // 	 */
    // 	public void setResponseWrapper(HttpServletResponseWrapper wrapper) {
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Entering setResponseWrapper() : wrapper = " + wrapper);
    // 		init();
    // 		if (wrapper == null)
    // 			throw new IllegalArgumentException("wrapper class cannot be null!");
    // 		else {
    // 			if (wrapper.getResponse() == null)
    // 				wrapper.setResponse(response);
    // 			_responseWrapper = wrapper;
    // 		}
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Exiting setResponseWrapper()");
    // 	}

    /**
     * Returns an HttpSession object that can be used in this test.
     */
    public HttpSession getSession() {
        confirmSetup();
        return request.getSession(true);
    }

    public ServletContext getContext() {
        confirmSetup();
        return context;
    }

    public HttpFrontController getServlet() {
        confirmSetup();
        return servlet;
    }

    // 	/**
    // 	 * Returns the ActionServlet controller used in this
    // 	 * test.
    // 	 *
    // 	 */
    // 	public ActionServlet getActionServlet() {
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Entering getActionServlet()");
    // 		init();
    // 		try {
    // 			if (!servletIsInitialized) {
    // 				if (logger.isDebugEnabled()) {
    // 					logger.debug("getActionServlet() : intializing servlet");
    // 				}
    // 				this._servlet.init(config);
    // 				servletIsInitialized = true;
    // 			}
    // 		} catch (ServletException e) {
    // 			if (logger.isDebugEnabled())
    // 				logger.debug("Error in getActionServlet()", e.getRootCause());
    // 			throw new AssertionFailedError(e.getMessage());
    // 		}
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Exiting getActionServlet()");
    // 		return servlet;
    // 	}

    // 	/**
    // 	 * Sets the ActionServlet to be used in this test execution. This
    // 	 * method should only be used if you plan to use a customized
    // 	 * version different from that provided in the Struts distribution.
    // 	 */
    // 	public void setActionServlet(ActionServlet servlet) {
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Entering setActionServlet() : servlet = " + servlet);
    // 		init();
    // 		if (servlet == null)
    // 			throw new AssertionFailedError("Cannot set ActionServlet to null");
    // 		this._servlet = servlet;
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Exiting setActionServlet()");
    // 		servletIsInitialized = false;
    // 	}

    /**
     * Executes the Action instance to be tested. This method calls the
     * ActionServlet.doPost() method to execute the Action instance to be
     * tested, passing along any parameters set in the HttpServletRequest
     * object. It stores any results for further validation.
     * @exception AssertionFailedError if there are any execution errors while
     *            calling Action.perform()
     */
    public void doEvent() {
        confirmSetup();

        try {
            servlet.doPost(request, response);
        } catch (ServletException se) {
            fail("Error running doEvent(): " + se.getRootCause().getClass()
                    + " - " + se.getRootCause().getMessage());
        } catch (Exception ex) {
            fail("Error running action.perform(): " + ex.getClass() + " - "
                    + ex.getMessage());
        }
    }

    /**
     * Adds an HttpServletRequest parameter to be used in setting up the
     * ActionForm instance to be used in this test. Each parameter added should
     * correspond to an attribute in the ActionForm instance used by the Action
     * instance being tested.
     */
    public void addRequestParameter(String parameterName, String parameterValue) {
        confirmSetup();
        request.addParameter(parameterName, parameterValue);
    }

    /**
     * Adds an HttpServletRequest parameter that is an array of String values to
     * be used in setting up the ActionForm instance to be used in this test.
     * Each parameter added should correspond to an attribute in the ActionForm
     * instance used by the Action instance being tested.
     */
    public void addRequestParameter(String parameterName,
            String[] parameterValues) {
        confirmSetup();
        request.addParameter(parameterName, parameterValues);
    }

    /**
     * Sets the request path instructing the ActionServlet to used a particual
     * ActionMapping.
     * @param pathInfo the request path to be processed. This should correspond
     *        to a particular action mapping, as would normally appear in an
     *        HTML or JSP source file.
     */

    public void setRequestPathInfo(String pathInfo) {
        confirmSetup();
        eventPath = stripActionPath(pathInfo);

        request.setPathInfo(eventPath);
    }

    public void setServletPath(String pathInfo) {
        confirmSetup();
        eventPath = stripActionPath(pathInfo);

        request.setServletPath(eventPath);
    }

    protected static String stripActionPath(String path) {
        if (path == null)
            return null;

        int slash = path.lastIndexOf("/");
        int period = path.lastIndexOf(".");
        if ((period >= 0) && (period > slash))
            path = path.substring(0, period);
        return path;
    }

    /**
     * Sets an initialization parameter on the ActionServlet. Allows you to
     * simulate an init parameter that would normally have been found in
     * web.xml, but is not available while testing with mock objects.
     * @param key the name of the initialization parameter
     * @param value the value of the intialization parameter
     */
    public void setInitParameter(String key, String value) {
        confirmSetup();
        config.setInitParameter(key, value);
        context.setInitParameter(key, value);
        servletIsInitialized = false;
    }

    /**
     * Sets the context directory to be used with the getRealPath() methods in
     * the ServletContext and HttpServletRequest API.
     * @param contextDirectory a File object representing the root context
     *        directory for this application.
     */
    public void setContextDirectory(File contextDirectory) {
        confirmSetup();
        context.setContextDirectory(contextDirectory);
        servletIsInitialized = false;
    }

    /**
     * Sets the struts configuration file for a given sub-application. This
     * method can take either an absolute path, or a relative path. If an
     * absolute path is supplied, the configuration file will be loaded from the
     * underlying filesystem; otherwise, the ServletContext loader will be used.
     * @param pathname the location of the configuration file for this
     *        sub-application
     */
    public void setConfigFile(String pathname) {
        confirmSetup();
        config.setInitParameter("config", pathname);
        servletIsInitialized = false;
    }

    /**
     * Sets the location of the web.xml configuration file to be used to set up
     * the servlet context and configuration for this test. This method supports
     * both init-param and context-param tags, setting the ServletConfig and
     * ServletContext appropriately. This method can take either an absolute
     * path, or a relative path. If an absolute path is supplied, the
     * configuration file will be loaded from the underlying filesystem;
     * otherwise, the ServletContext loader will be used.
     */
    public void setServletConfigFile(String pathname) {
        confirmSetup();

        // pull in the appropriate parts of the
        // web.xml file -- first the init-parameters
        Digester digester = new Digester();
        digester.push(config);
        digester.setValidating(false);
        digester.addCallMethod("web-app/servlet/init-param",
                "setInitParameter", 2);
        digester.addCallParam("web-app/servlet/init-param/param-name", 0);
        digester.addCallParam("web-app/servlet/init-param/param-value", 1);
        try {
            InputStream input = context.getResourceAsStream(pathname);
            if (input == null)
                throw new AssertionFailedError("Invalid pathname: " + pathname);
            digester.parse(input);
            input.close();
        } catch (Exception e) {
            throw new AssertionFailedError(
                    "Received an exception while loading web.xml - "
                            + e.getClass() + " : " + e.getMessage());
        }

        // now the context parameters..
        digester = new Digester();
        digester.setValidating(false);
        digester.push(context);
        digester.addCallMethod("web-app/context-param", "setInitParameter", 2);
        digester.addCallParam("web-app/context-param/param-name", 0);
        digester.addCallParam("web-app/context-param/param-value", 1);
        try {
            InputStream input = context.getResourceAsStream(pathname);
            if (input == null)
                throw new AssertionFailedError("Invalid pathname: " + pathname);
            digester.parse(input);
            input.close();
        } catch (Exception e) {
            throw new AssertionFailedError(
                    "Received an exception while loading web.xml - "
                            + e.getClass() + " : " + e.getMessage());
        }
        servletIsInitialized = false;
    }

    /**
     * Returns the forward sent to RequestDispatcher.
     */
    private String getActualForward() {
        if (response.containsHeader("Location")) {
            return stripJSessionID(response.getHeader("Location"));
        }
        try {
            String strippedForward = request.getContextPath()
                    + stripJSessionID(((ServletContextSimulator)config
                            .getServletContext())
                            .getRequestDispatcherSimulator().getForward());
            return strippedForward;
        } catch (NullPointerException npe) {
            return null;
        }
    }

    /**
     * Strip ;jsessionid= <sessionid>from path.
     * @return stripped path
     */
    protected static String stripJSessionID(String path) {
        if (path == null)
            return null;

        String pathCopy = path.toLowerCase();
        int jsess_idx = pathCopy.indexOf(";jsessionid=");
        if (jsess_idx > 0) {
            // Strip jsessionid from obtained path
            StringBuffer buf = new StringBuffer(path);
            path = buf.delete(jsess_idx, jsess_idx + 44).toString();
        }
        return path;
    }

    /**
     * Verifies if the ActionServlet controller used this forward.
     * @param forwardName the logical name of a forward, as defined in the
     *        Struts configuration file. This can either refer to a global
     *        forward, or one local to the ActionMapping.
     * @exception AssertionFailedError if the ActionServlet controller used a
     *            different forward than <code>forwardName</code> after
     *            executing an Action object.
     */
    public void verifyForward(String forwardName) throws AssertionFailedError {
        confirmSetup();
        //		verifyForwardPath(
        //			servlet,
        //			eventPath,
        //			forwardName,
        //			getActualForward(),
        //			false,
        //			request,
        //			config.getServletContext(),
        //			config);
    }

    /**
     * Verifies if the ActionServlet controller used this actual path as a
     * forward.
     * @param forwardPath an absolute pathname to which the request is to be
     *        forwarded.
     * @exception AssertionFailedError if the ActionServlet controller used a
     *            different forward path than <code>forwardPath</code> after
     *            executing an Action object.
     */
    public void verifyForwardPath(String forwardPath)
            throws AssertionFailedError {
        confirmSetup();
        forwardPath = request.getContextPath() + forwardPath;

        String actualForward = getActualForward();
        if (actualForward == null) {
            throw new AssertionFailedError(
                    "Was expecting '"
                            + forwardPath
                            + "' but it appears the Action has tried to return an ActionForward that is not mapped correctly.");
        }
        if (!(actualForward.equals(forwardPath)))
            throw new AssertionFailedError("was expecting '" + forwardPath
                    + "' but received '" + actualForward + "'");
    }

    // 	/**
    // 	 * Verifies if the ActionServlet controller forwarded to the defined
    // 	 * input path.
    // 	 *
    // 	 * @exception AssertionFailedError if the ActionServlet controller
    // 	 * used a different forward than the defined input path after
    // 	 * executing an Action object.
    // 	 */
    // 	public void verifyInputForward() {
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Entering verifyInputForward()");
    // 		init();
    // 		Common.verifyForwardPath(
    // 			servlet,
    // 			eventPath,
    // 			null,
    // 			getActualForward(),
    // 			true,
    // 			request,
    // 			config.getServletContext(),
    // 			config);
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Exiting verifyInputForward()");
    // 	}

    // 	/**
    // 	 * Verifies if the ActionServlet controller sent these error messages.
    // 	 * There must be an exact match between the provided error messages, and
    // 	 * those sent by the controller, in both name and number.
    // 	 *
    // 	 * @param errorNames a String array containing the error message keys
    // 	 * to be verified, as defined in the application resource properties
    // 	 * file.
    // 	 *
    // 	 * @exception AssertionFailedError if the ActionServlet controller
    // 	 * sent different error messages than those in <code>errorNames</code>
    // 	 * after executing an Action object.
    // 	 */

    // 	public void verifyActionErrors(String[] errorNames) {
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Entering verifyActionErrors() : errorNames = " +
    // errorNames);
    // 		init();
    // 		Common.verifyActionMessages(request, errorNames, Action.ERROR_KEY,
    // "error");
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Exiting verifyActionErrors()");
    // 	}

    // 	/**
    // 	 * Verifies that the ActionServlet controller sent no error messages upon
    // 	 * executing an Action object.
    // 	 *
    // 	 * @exception AssertionFailedError if the ActionServlet controller
    // 	 * sent any error messages after excecuting and Action object.
    // 	 */
    // 	public void verifyNoActionErrors() {
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Entering verifyNoActionErrors()");
    // 		init();
    // 		Common.verifyNoActionMessages(request, Action.ERROR_KEY, "error");
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Exiting verifyNoActionErrors()");
    // 	}

    // 	/**
    // 	 * Verifies if the ActionServlet controller sent these action messages.
    // 	 * There must be an exact match between the provided action messages, and
    // 	 * those sent by the controller, in both name and number.
    // 	 *
    // 	 * @param messageNames a String array containing the action message keys
    // 	 * to be verified, as defined in the application resource properties
    // 	 * file.
    // 	 *
    // 	 * @exception AssertionFailedError if the ActionServlet controller
    // 	 * sent different action messages than those in <code>messageNames</code>
    // 	 * after executing an Action object.
    // 	 */
    // 	public void verifyActionMessages(String[] messageNames) {
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Entering verifyActionMessages() : messageNames = " +
    // messageNames);
    // 		init();
    // 		Common.verifyActionMessages(request, messageNames, Globals.MESSAGE_KEY,
    // "action");
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Exiting verifyActionMessages()");
    // 	}

    // 	/**
    // 	 * Verifies that the ActionServlet controller sent no action messages upon
    // 	 * executing an Action object.
    // 	 *
    // 	 * @exception AssertionFailedError if the ActionServlet controller
    // 	 * sent any action messages after excecuting and Action object.
    // 	 */
    // 	public void verifyNoActionMessages() {
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Entering verifyNoActionMessages()");
    // 		init();
    // 		Common.verifyNoActionMessages(request, Globals.MESSAGE_KEY, "action");
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Exiting verifyNoActionMessages()");
    // 	}

    // 	/**
    // 	 * Returns the ActionForm instance stored in either the request or
    // session. Note
    // 	 * that no form will be returned if the Action being tested cleans up the
    // form
    // 	 * instance.
    // 	 *
    // 	 * @ return the ActionForm instance used in this test, or null if it does
    // not exist.
    // 	 */
    // 	public ActionForm getActionForm() {
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Entering getActionForm()");
    // 		init();
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Exiting getActionForm()");
    // 		return Common.getActionForm(servlet, eventPath, request, context);
    // 	}

    // 	/**
    // 	 * Sets an ActionForm instance to be used in this test. The given
    // ActionForm instance
    // 	 * will be stored in the scope specified in the Struts configuration file
    // (ie: request
    // 	 * or session). Note that while this ActionForm instance is passed to the
    // test, Struts
    // 	 * will still control how it is used. In particular, it will call the
    // ActionForm.reset()
    // 	 * method, so if you override this method in your ActionForm subclass, you
    // could potentially
    // 	 * reset attributes in the form passed through this method.
    // 	 *
    // 	 * @param form the ActionForm instance to be used in this test.
    // 	 */
    // 	public void setActionForm(ActionForm form) {
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Entering setActionForm() : form = " + form);
    // 		init();
    // 		// make sure action servlet is intialized
    // 		Common.setActionForm(form, request, eventPath, context,
    // this.getActionServlet());
    // 		if (logger.isDebugEnabled())
    // 			logger.debug("Exiting setActionForm()");
    // 	}

    //	protected static void verifyForwardPath(
    //		HttpFrontController servlet,
    //		String actionPath,
    //		String forwardName,
    //		String actualForwardPath,
    //		boolean isInputPath,
    //		HttpServletRequest request,
    //		ServletContext context,
    //		ServletConfig config) {
    //
    //		if ((forwardName == null) && (isInputPath)) {
    //			forwardName = getActionConfig(servlet, actionPath, request,
    // context).getInput();
    //			if (forwardName == null)
    //				throw new AssertionFailedError("Trying to validate against an input
    // mapping, but none is defined for this Action.");
    //		}
    //		if (!isInputPath) {
    //			ActionForward expectedForward =
    //				findForward(actionPath, forwardName, request, context, servlet);
    //			if (expectedForward == null) {
    //				expectedForward = servlet.findForward(forwardName);
    //			}
    //			if (expectedForward == null)
    //				throw new AssertionFailedError(
    //					"Cannot find forward '"
    //						+ forwardName
    //						+ "' - it is possible that it is not mapped correctly.");
    //			forwardName = expectedForward.getPath();
    //
    //		}
    //		if (!forwardName.startsWith("/"))
    //			forwardName = "/" + forwardName;
    //
    //			forwardName = request.getContextPath() + forwardName;
    //		if (actualForwardPath == null) {
    //			throw new AssertionFailedError(
    //				"Was expecting '"
    //					+ forwardName
    //					+ "' but it appears the Action has tried to return an ActionForward that
    // is not mapped correctly.");
    //		}
    //		if (!forwardName.equals(stripJSessionID(actualForwardPath)))
    //			throw new AssertionFailedError(
    //				"was expecting '" + forwardName + "' but received '" + actualForwardPath
    // + "'");
    //	}

    protected void setUserRole(String role) {
        request.setUserRole(role);
    }

    protected void setRemoteUser(String login) {
        request.setRemoteUser(login);
    }
}