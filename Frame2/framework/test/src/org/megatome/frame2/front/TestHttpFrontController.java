package org.megatome.frame2.front;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.megatome.frame2.Globals;

import servletunit.HttpServletRequestSimulator;
import servletunit.HttpServletResponseSimulator;
import servletunit.ServletContextSimulator;
import servletunit.frame2.MockFrame2TestCase;

public class TestHttpFrontController extends MockFrame2TestCase {

	private ServletContextSimulator _context;
	private HttpServletRequestSimulator _request;
	private HttpServletResponseSimulator _response;

	/**
	 * Constructor for TestHttpFrontController.
	 *
	 * @param name
	 */
	public TestHttpFrontController(String name) {
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

	private void postNegativeHttpReqProc() {
		HttpFrontController servlet = initializeServlet();

		try {
			servlet.doPost(_request, _response);
		} catch (Exception e) {
			return;
		}
		fail();
	}

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

		_request.setServletPath("http://localhost/event1.f2");

		return servlet;
	}

	public void testBlankRequest() {
		boolean testShouldFail = false;
		try {
			doEvent();
			testShouldFail = true;
		} catch (Error e) {}
		assertFalse(testShouldFail);
	}

	public void testInit() {
		sendContextInitializedEvent(
			Globals.CONFIG_FILE,
			"/org/megatome/frame2/front/test-config.xml");

		try {
			getServlet().init();
		} catch (ServletException e) {
			fail("Unexpected ServletException: " + e.getMessage());
		}
		Configuration config = getServlet().getConfiguration();

		assertNotNull(config);

		Map globalForwards = config.getGlobalHTMLForwards();

		assertNotNull(globalForwards);
		assertEquals(10, globalForwards.size());

		assertNotNull(globalForwards.get("view1"));

		Map events = config.getEvents();

		assertNotNull(events);
		assertEquals(26, events.size());

		Map eventHandlers = config.getEventHandlers();

		assertNotNull(eventHandlers);
		assertEquals(14, eventHandlers.size());

		Map eventMappings = config.getEventMappings();

		assertNotNull(eventMappings);
		assertEquals(21, eventMappings.size());
	}

	// NIT: This test is really more of a test of the Mock framework.  Should it be
	// a separate test?
	public void testInitConfig() {
		assertNotNull(getRequest());
		assertNotNull(getResponse());
		assertNotNull(getContext());
		assertNotNull(getSession());

		HttpServlet servlet = getServlet();

		assertNotNull(servlet.getServletConfig());
		assertNotNull(servlet.getServletContext());
		assertNotNull(servlet.getServletInfo());
	}

	public void testInstantiateHttpRequestProcessor() {

		sendContextInitializedEvent(
			Globals.CONFIG_FILE,
			"/org/megatome/frame2/front/test-config.xml");

		postHttpReqProc();

	}

	public void testNegativeInstantiateHttpRequestProcessor() {

		sendContextInitializedEvent(
			Globals.CONFIG_FILE,
			"/org/megatome/frame2/front/httpRequestNegativeClass.xml");

		postNegativeHttpReqProc();

	}

}
