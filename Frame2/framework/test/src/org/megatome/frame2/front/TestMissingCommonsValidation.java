package org.megatome.frame2.front;

import javax.servlet.ServletException;

import org.megatome.frame2.Globals;

import servletunit.HttpServletRequestSimulator;
import servletunit.HttpServletResponseSimulator;
import servletunit.ServletContextSimulator;
import servletunit.frame2.MockFrame2TestCase;

/**
 * This test is for the case when CommonsValidator jars are not in
 * the classpath. If the plugin is not specified, this should not be an
 * error.
 * 
 * To successfully run these tests, you must remove commons-validator.jar from
 * the classpath.
 */
public class TestMissingCommonsValidation extends MockFrame2TestCase {

	private ServletContextSimulator _context;
	private HttpServletRequestSimulator _request;
	private HttpServletResponseSimulator _response;

	/**
	 * Constructor for TestCommonsValidation.
	 *
	 * @param name
	 */
	public TestMissingCommonsValidation(String name) {
		super(name);

	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		_context = (ServletContextSimulator)getContext();
		_request = (HttpServletRequestSimulator)getRequest();
		_response = (HttpServletResponseSimulator)getResponse();
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	private void postHttpReqProc() {
		HttpFrontController servlet = initializeServlet();

		try {
			servlet.doPost(_request, _response);
		} catch (Exception e) {
			fail();
		}
	}
	
	private void postNegativeHttpReqProc() {
		HttpFrontController servlet = initializeServlet();

		try {
			servlet.doPost(_request, _response);
		} catch (Exception e) {
		   return;
		}
		fail();
	}

	private HttpFrontController initializeServlet() {
		HttpFrontController servlet = getServlet();
		try {
			servlet.init();
		} catch (ServletException e) {
			fail("Unexpected ServletException: " + e.getMessage());
		}
		Configuration config = getServlet().getConfiguration();

		assertNotNull(config);

		_request.setServletPath("http://localhost/validateEvent.f2");

		return servlet;
	}
	
	public void testPrerequisites() {
	   try {
	      Object o = Class.forName("org.apache.commons.validator.ValidatorResources");
	   } catch (ClassNotFoundException e) {
	      // This is the expected result
	      return;
	   } catch (Exception e) {
	      fail("Unexpected Exception");
	   }
	   
	   fail("Did not catch expected ClassDef exception");
	}

	public void testInvokeValidateWithPlugin() {

		sendContextInitializedEvent(
			Globals.CONFIG_FILE,
			"/org/megatome/frame2/front/test-validator-config.xml");

		postNegativeHttpReqProc();
	}
	
	public void testInvokeValidateWithoutPlugin() {

		sendContextInitializedEvent(
			Globals.CONFIG_FILE,
			"/org/megatome/frame2/front/test-missing-validator-config.xml");

		postHttpReqProc();
	}
}
