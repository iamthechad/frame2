/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2006 Megatome Technologies.  All rights
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

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.megatome.frame2.event.Event;
import org.megatome.frame2.front.config.Forward;
import org.megatome.frame2.front.config.ViewType;

/**
 *
 */
public class TestConfiguration extends TestCase {
	private Configuration config;

	/**
	* Constructor for TestConfiguration.
	*/
	public TestConfiguration() {
		super();
	}

	/**
	* Constructor for TestConfiguration.
	* @param name
	*/
	public TestConfiguration(String name) {
		super(name);
	}

	/**
	* @see junit.framework.TestCase#setUp()
	*/
	protected void setUp() throws Exception {
		super.setUp();
		config =
			new Configuration("org/megatome/frame2/front/test-config.xml");
	}

	public void testEventBinding() {
		Event event = null;
		try {
			event = config.getEventProxy("event1").getEvent();
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage());
		}

		assertNotNull(event);
	}

	public void testEventBinding_Empty() {
		assertGetEventFails(config, "");
	}

	public void testEventBinding_WrongEventType() {
		assertGetEventFails(config, "wrongEventType");
	}

	public void testEventBinding_TypeMissing() {
		assertGetEventFails(config, "bogus1");
	}

	public void testEventBinding_TypeNotEvent() {
		assertGetEventFails(config, "bogus2");
	}

	public void testEventBinding_NullName() {
		assertGetEventFails(config, null);
	}

	public void testHandlerBinding() {
		List eventHandlers = null;
		try {
			eventHandlers = config.getHandlers("event1");
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage());
		}

		assertNotNull(eventHandlers);
		assertEquals(2, eventHandlers.size());

		EventHandlerProxy handler1 = (EventHandlerProxy)eventHandlers.get(0);
		assertNotNull(handler1);
		assertEquals("ev1handler1", handler1.getName());
		assertTrue(handler1.getHandler() instanceof Ev1handler1);

		EventHandlerProxy handler2 = (EventHandlerProxy)eventHandlers.get(1);
		assertNotNull(handler2);
		assertEquals("ev1handler2", handler2.getName());
		assertTrue(handler2.getHandler() instanceof Ev1handler2);

		try {
			eventHandlers = config.getHandlers("event2");
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage());
		}
		assertEquals(2, eventHandlers.size());

		handler1 = (EventHandlerProxy)eventHandlers.get(0);
		assertNotNull(handler1);
		assertEquals("ev1handler1", handler1.getName());
		assertTrue(handler1.getHandler() instanceof Ev1handler1);

		handler2 = (EventHandlerProxy)eventHandlers.get(1);
		assertNotNull(handler2);
		assertEquals("ev2handler1", handler2.getName());
		assertTrue(handler2.getHandler() instanceof Ev2handler1);
	}

	public void testXMLHandlerBinding() {
		List eventHandlers = null;
		try {
			eventHandlers = config.getHandlers("event111");
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage());
		}

		assertNotNull(eventHandlers);

		EventHandlerProxy handler1 = (EventHandlerProxy)eventHandlers.get(0);
		assertNotNull(handler1);
		// test local fwd
		Forward html = handler1.getDefinition().getHTMLForward("local14");
		Forward xml = handler1.getDefinition().getXMLForward("local14");
		assertEquals("test.jsp", html.getPath());
		assertEquals("testclass1", xml.getPath());

		// test a global
		xml = handler1.getDefinition().getXMLForward("view15");
		assertNull(xml);

		ForwardProxy fwd = null;
		try {
			fwd =
				config.resolveForward(
					handler1,
					"view15",
					Configuration.XML_TOKEN);
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage());
		}
		assertTrue(fwd.isResourceType());
		assertFalse(fwd.isEventType());
	}

	public void testXMLResponder() {
		List eventHandlers = null;
		try {
			eventHandlers = config.getHandlers("event112");
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage());
		}

		assertNotNull(eventHandlers);

		EventHandlerProxy handler1 = (EventHandlerProxy)eventHandlers.get(0);
		assertNotNull(handler1);

		Forward xml = handler1.getDefinition().getXMLForward("local14");
        assertNotNull(xml);
		Forward xmlResponder =
			handler1.getDefinition().getXMLForward("listResponder");
		assertNotNull(xmlResponder);
		assertEquals("testLocalListResponder", xmlResponder.getPath());

		ForwardProxy fwd = null;
		try {
			fwd =
				config.resolveForward(
					handler1,
					"listResponder",
					Configuration.XML_TOKEN);
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage());
		}
		assertFalse(fwd.isEventType());
		assertFalse(fwd.isResourceType());
		assertTrue(fwd.isResponderType());
	}

	public void testXMLResponderNotInHTMLForwardsMap() {
		List eventHandlers = null;
		try {
			eventHandlers = config.getHandlers("event112");
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage());
		}
		assertNotNull(eventHandlers);

		EventHandlerProxy handler1 = (EventHandlerProxy)eventHandlers.get(0);
		assertNotNull(handler1);
		// test xml responder does not exist in HTML Fowards map 
		Forward html = handler1.getDefinition().getHTMLForward("local14");
        assertNotNull(html);
		Forward xmlResponderNotExist =
			handler1.getDefinition().getHTMLForward("listResponder");
		assertNull(xmlResponderNotExist);
	}

	public void testHandlerBinding_Cache() {
		try {
			List eventHandlers1 = config.getHandlers("event1");
			List eventHandlers2 = config.getHandlers("event1");

			assertSame(eventHandlers1, eventHandlers2);
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage());
		}
	}

	public void testHandlerBinding_UnimplementedHandler() {
		assertGetHandlerFails(config, "event10");
	}

	public void testHandlerBinding_InvalidNames() {
		assertGetHandlerFails(config, null);
		assertGetHandlerFails(config, "");
		assertGetHandlerFails(config, "bogusEventName");
	}

	public void testHandlerBinding_NoHandlerConfigured() {
		assertGetHandlerFails(config, "event9");
	}

	public void testBothViewType() {
		String htmlForwardName = null;
		try {
			htmlForwardName =
				config.getEventMappingView("event2", ViewType.HTML);
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage());
		}
		assertEquals("view4", htmlForwardName);
		String xmlForwardName = null;
		try {
			xmlForwardName =
				config.getEventMappingView("event2", ViewType.XML);
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage());
		}
		assertEquals("view4", xmlForwardName);

		// test XML View
		ForwardProxy fwd = null;
		try {
			fwd = config.resolveForward("view4", Configuration.XML_TOKEN);
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage());
		}
		assertFalse(fwd.isEventType());
		assertTrue(fwd.isResourceType());
		assertFalse(fwd.isResponderType());
		assertEquals("testXmlResource", fwd.getPath());

		// test HTML View
		try {
			fwd = config.resolveForward("view4", Configuration.HTML_TOKEN);
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage());
		}
		assertFalse(fwd.isEventType());
		assertTrue(fwd.isResourceType());
		assertFalse(fwd.isResponderType());
		assertEquals("/view4.jsp", fwd.getPath());
	}

	public void testNonExistingXMLViewType() {
		String xmlForwardName = null;
		try {
			xmlForwardName =
				config.getEventMappingView("event14", ViewType.XML);
			fail("We should have gotten an exception");
		} catch (ViewException e) {
			assertNull(xmlForwardName);
		}
	}

	public void testNonExistingXMLGlobalForwardType() {
		// test XML View
		ForwardProxy fwd = null;
		try {
			fwd = config.resolveForward("event1", Configuration.XML_TOKEN);
			fail("We should have gotten an exception");
		} catch (ViewException e) {
			assertNull(fwd);
		}
	}

	public void testEventDefaultResolveAs() {
		try {
			EventProxy eProxy = config.getEventProxy("event1");
			assertTrue(eProxy.isParent());
			assertFalse(eProxy.isChildren());
			assertFalse(eProxy.isPassThru());
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage());
		}
	}

	public void testEventResolveAsChildren() {
		try {
			EventProxy eProxy = config.getEventProxy("event12");
			assertFalse(eProxy.isParent());
			assertTrue(eProxy.isChildren());
			assertFalse(eProxy.isPassThru());
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage());
		}
	}

	public void testEventResolveAsPassThru() {
		try {
			EventProxy eProxy = config.getEventProxy("event2");
			assertFalse(eProxy.isParent());
			assertFalse(eProxy.isChildren());
			assertTrue(eProxy.isPassThru());
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage());
		}
	}

	public void testExceptionProxy() {
		ConfigTestException ex = new ConfigTestException();
		ExceptionProxy exception = null;
		try {
			exception =
				config.resolveException(
					ex,
					Configuration.HTML_TOKEN,
					ViewType.HTML);
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage());
			e.printStackTrace();
		}
		assertNotNull(exception);
	}

	public void testExceptionProxyHTMLFowardPath() {
		ConfigTestException ex = new ConfigTestException();
		ExceptionProxy exception = null;
		try {
			exception =
				config.resolveException(
					ex,
					Configuration.HTML_TOKEN,
					ViewType.HTML);
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage());
		}
		assertNotNull(exception);
		String htmlForwardPath = exception.getPath();
		assertEquals("/view4.jsp", htmlForwardPath);
	}

	public void testExceptionProxyXMLFowardPath() {
		ConfigTestException ex = new ConfigTestException();
		ExceptionProxy exception = null;
		try {
			exception =
				config.resolveException(
					ex,
					Configuration.XML_TOKEN,
					ViewType.XML);
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage());
		}
		assertNotNull(exception);
		String xmlForwardPath = exception.getPath();
		assertEquals("testViewClass", xmlForwardPath);
	}

	public void testRedirect() {
		ForwardProxy fwd = null;
		try {
			fwd = config.resolveForward("view1", Configuration.HTML_TOKEN);
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage());
		}
		assertFalse(fwd.isRedirect());
		try {
			fwd = config.resolveForward("redirect", Configuration.HTML_TOKEN);
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage());
		}
		assertTrue(fwd.isRedirect());
	}

	public void testInputViewFor() {
		try {
			assertEquals(
				"/view3.jsp",
				config.inputViewFor("event3", Configuration.HTML_TOKEN));
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage());
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage());
		}

		try {
			config.inputViewFor("event111", Configuration.XML_TOKEN);
			fail();
		} catch (Exception e) {}
	}

	public void testCancelViewFor() {
		ForwardProxy fwd = null;
		try {
			fwd = config.cancelViewFor("event3", Configuration.HTML_TOKEN);
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage());
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage());
		}

		assertEquals("/view2.jsp", fwd.getPath());

		try {
			config.cancelViewFor("event111", Configuration.XML_TOKEN);
			fail();
		} catch (Exception e) {}
	}

	public void testPluginProxy() {
		List proxies = config.getPluginProxies();
		assertTrue(proxies.size() == 5);

		PluginProxy proxy = (PluginProxy)proxies.get(0);
		assertTrue(proxy.getName().equals("PluginName1"));
		assertNotNull(proxy.getPlugin());

		proxy = (PluginProxy)proxies.get(1);
		assertTrue(proxy.getName().equals("PluginName2"));
		assertNotNull(proxy.getPlugin());
		Map params = proxy.getInitParams();
		assertTrue(params.size() == 1);
		assertTrue(((String)params.get("param1")).equals("value1"));
	}

	public void testNegativePluginProxy() {
		try {
			config =
				new Configuration("org/megatome/frame2/front/test-negative-plugin-config.xml");
		} catch (ConfigException e) {
			return;
		}
		fail();
	}

	private void assertGetHandlerFails(
		Configuration cfg,
		String eventName) {
		try {
			cfg.getHandlers(eventName);
			fail();
		} catch (ConfigException e) {}
	}

	private void assertGetEventFails(Configuration cfg, String eventName) {
		try {
			cfg.getEventProxy(eventName).getEvent();
			fail();
		} catch (ConfigException expected) {}
	}
}
