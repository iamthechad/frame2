/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2007 Megatome Technologies.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by
 *        Megatome Technologies."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Frame2 Project", and "Frame2", 
 *    must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact iamthechad@sourceforge.net.
 *
 * 5. Products derived from this software may not be called "Frame2"
 *    nor may "Frame2" appear in their names without prior written
 *    permission of Megatome Technologies.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL MEGATOME TECHNOLOGIES OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package org.megatome.frame2.front;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.junit.Test;
import org.megatome.frame2.Globals;
import org.megatome.frame2.front.config.EventDef;
import org.megatome.frame2.front.config.EventHandlerDef;
import org.megatome.frame2.front.config.EventMapping;
import org.megatome.frame2.front.config.Forward;

import servletunit.HttpServletRequestSimulator;
import servletunit.HttpServletResponseSimulator;
import servletunit.frame2.MockFrame2TestCase;

public class TestHttpFrontController extends MockFrame2TestCase {

	private HttpServletRequestSimulator request;
	private HttpServletResponseSimulator response;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		this.request = (HttpServletRequestSimulator)getRequest();
		this.response = (HttpServletResponseSimulator)getResponse();
	}

	private void postNegativeHttpReqProc() {
		HttpFrontController servlet = initializeServlet();

		try {
			servlet.doPost(this.request, this.response);
			fail();
		} catch (Exception expected) {
			//Expected
		}
	}

	private void postHttpReqProc() {
		HttpFrontController servlet = initializeServlet();

		try {
			servlet.doPost(this.request, this.response);
		} catch (Exception e) {
			fail();
		}
	}

	private HttpFrontController initializeServlet() {
		HttpFrontController servlet = getServlet();
		try {
			servlet.init();
		} catch (ServletException e) {
			fail("Unexpected ServletException: " + e.getMessage()); //$NON-NLS-1$
		}
		Configuration config = getServlet().getConfiguration();

		assertNotNull(config);

		this.request.setServletPath("http://localhost/event1.f2"); //$NON-NLS-1$

		return servlet;
	}

	@Test
	public void testBlankRequest() {
		try {
			doEvent();
			fail();
		} catch (Error expected) {
			// expected
		}
	}

	@SuppressWarnings("boxing")
	@Test
	public void testInit() {
		sendContextInitializedEvent(
			Globals.CONFIG_FILE,
			"/org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$

		try {
			getServlet().init();
		} catch (ServletException e) {
			fail("Unexpected ServletException: " + e.getMessage()); //$NON-NLS-1$
		}
		Configuration config = getServlet().getConfiguration();

		assertNotNull(config);

		Map<String, Forward> globalForwards = config.getGlobalHTMLForwards();

		assertNotNull(globalForwards);
		assertEquals(13, globalForwards.size());

		assertNotNull(globalForwards.get("view1")); //$NON-NLS-1$

		Map<String, EventDef> events = config.getEvents();

		assertNotNull(events);
		assertEquals(29, events.size());

		Map<String, EventHandlerDef> eventHandlers = config.getEventHandlers();

		assertNotNull(eventHandlers);
		assertEquals(16, eventHandlers.size());

		Map<String, EventMapping> eventMappings = config.getEventMappings();

		assertNotNull(eventMappings);
		assertEquals(24, eventMappings.size());
	}

	// NIT: This test is really more of a test of the Mock framework.  Should it be
	// a separate test?
	@Test
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

	@Test
	public void testInstantiateHttpRequestProcessor() {

		sendContextInitializedEvent(
			Globals.CONFIG_FILE,
			"/org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$

		postHttpReqProc();

	}

	@Test
	public void testNegativeInstantiateHttpRequestProcessor() {

		sendContextInitializedEvent(
			Globals.CONFIG_FILE,
			"/org/megatome/frame2/front/httpRequestNegativeClass.xml"); //$NON-NLS-1$

		postNegativeHttpReqProc();

	}

}
