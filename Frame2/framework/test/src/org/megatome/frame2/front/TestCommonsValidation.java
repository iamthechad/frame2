package org.megatome.frame2.front;

import javax.servlet.ServletException;

import org.megatome.frame2.Globals;

import servletunit.HttpServletRequestSimulator;
import servletunit.HttpServletResponseSimulator;
import servletunit.ServletContextSimulator;
import servletunit.frame2.MockFrame2TestCase;

public class TestCommonsValidation extends MockFrame2TestCase {

	private ServletContextSimulator _context;
	private HttpServletRequestSimulator _request;
	private HttpServletResponseSimulator _response;

	/**
	 * Constructor for TestCommonsValidation.
	 *
	 * @param name
	 */
	public TestCommonsValidation(String name) {
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
/*
	private void postNegativeHttpReqProc() {
		HttpFrontController servlet = initializeServlet();

		try {
			servlet.doPost(_request, _response);
		} catch (Exception e) {
			return;
		}
		fail();
	}
*/
	private void postHttpReqProc() {
		HttpFrontController servlet = initializeServlet();

		try {
			servlet.doPost(_request, _response);
		} catch (Exception e) {
			fail();
		}
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

	public void testInvokeValidate() {

		sendContextInitializedEvent(
			Globals.CONFIG_FILE,
			"/org/megatome/frame2/front/test-validator-config.xml");

		postHttpReqProc();

		//TODO assert various validation stuff when validate rtn done

	}
}
