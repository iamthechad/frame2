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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.front.config.Forward;
import org.megatome.frame2.front.config.ViewType;

/**
 *
 */
public class TestConfiguration {
	private Configuration config;

	@Before
	public void setUp() throws Exception {
		this.config =
			new Configuration("org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$
	}

	@Test
	public void testEventBinding() {
		Event event = null;
		try {
			event = this.config.getEventProxy("event1").getEvent(); //$NON-NLS-1$
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage()); //$NON-NLS-1$
		}

		assertNotNull(event);
	}

	@Test
	public void testEventBinding_Empty() {
		assertGetEventFails(this.config, ""); //$NON-NLS-1$
	}

	@Test
	public void testEventBinding_WrongEventType() {
		assertGetEventFails(this.config, "wrongEventType"); //$NON-NLS-1$
	}

	@Test
	public void testEventBinding_TypeMissing() {
		assertGetEventFails(this.config, "bogus1"); //$NON-NLS-1$
	}

	@Test
	public void testEventBinding_TypeNotEvent() {
		assertGetEventFails(this.config, "bogus2"); //$NON-NLS-1$
	}

	@Test
	public void testEventBinding_NullName() {
		assertGetEventFails(this.config, null);
	}

	@SuppressWarnings({ "null", "boxing" })
	@Test
	public void testHandlerBinding() {
		List<EventHandlerProxy> eventHandlers = null;
		try {
			eventHandlers = this.config.getHandlers("event1"); //$NON-NLS-1$
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage()); //$NON-NLS-1$
		}

		assertNotNull(eventHandlers);
		assertEquals(2, eventHandlers.size());

		EventHandlerProxy handler1 = eventHandlers.get(0);
		assertNotNull(handler1);
		assertEquals("ev1handler1", handler1.getName()); //$NON-NLS-1$
		assertTrue(handler1.getHandler() instanceof Ev1handler1);

		EventHandlerProxy handler2 = eventHandlers.get(1);
		assertNotNull(handler2);
		assertEquals("ev1handler2", handler2.getName()); //$NON-NLS-1$
		assertTrue(handler2.getHandler() instanceof Ev1handler2);

		try {
			eventHandlers = this.config.getHandlers("event2"); //$NON-NLS-1$
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage()); //$NON-NLS-1$
		}
		assertNotNull(eventHandlers);
		assertEquals(2, eventHandlers.size());

		handler1 = eventHandlers.get(0);
		assertNotNull(handler1);
		assertEquals("ev1handler1", handler1.getName()); //$NON-NLS-1$
		assertTrue(handler1.getHandler() instanceof Ev1handler1);

		handler2 = eventHandlers.get(1);
		assertNotNull(handler2);
		assertEquals("ev2handler1", handler2.getName()); //$NON-NLS-1$
		assertTrue(handler2.getHandler() instanceof Ev2handler1);
	}

	@SuppressWarnings("null")
	@Test
	public void testXMLHandlerBinding() {
		List<EventHandlerProxy> eventHandlers = null;
		try {
			eventHandlers = this.config.getHandlers("event111"); //$NON-NLS-1$
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage()); //$NON-NLS-1$
		}

		assertNotNull(eventHandlers);

		EventHandlerProxy handler1 = eventHandlers.get(0);
		assertNotNull(handler1);
		// test local fwd
		Forward html = handler1.getDefinition().getHTMLForward("local14"); //$NON-NLS-1$
		Forward xml = handler1.getDefinition().getXMLForward("local14"); //$NON-NLS-1$
		assertEquals("test.jsp", html.getPath()); //$NON-NLS-1$
		assertEquals("testclass1", xml.getPath()); //$NON-NLS-1$

		// test a global
		xml = handler1.getDefinition().getXMLForward("view15"); //$NON-NLS-1$
		assertNull(xml);

		ForwardProxy fwd = null;
		try {
			fwd =
				this.config.resolveForward(
					handler1,
					"view15", //$NON-NLS-1$
					Configuration.XML_TOKEN);
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage()); //$NON-NLS-1$
		}
		assertNotNull(fwd);
		assertTrue(fwd.isResourceType());
		assertFalse(fwd.isEventType());
	}

	@SuppressWarnings("null")
	@Test
	public void testXMLResponder() {
		List<EventHandlerProxy> eventHandlers = null;
		try {
			eventHandlers = this.config.getHandlers("event112"); //$NON-NLS-1$
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage()); //$NON-NLS-1$
		}

		assertNotNull(eventHandlers);

		EventHandlerProxy handler1 = eventHandlers.get(0);
		assertNotNull(handler1);

		Forward xml = handler1.getDefinition().getXMLForward("local14"); //$NON-NLS-1$
        assertNotNull(xml);
		Forward xmlResponder =
			handler1.getDefinition().getXMLForward("listResponder"); //$NON-NLS-1$
		assertNotNull(xmlResponder);
		assertEquals("testLocalListResponder", xmlResponder.getPath()); //$NON-NLS-1$

		ForwardProxy fwd = null;
		try {
			fwd =
				this.config.resolveForward(
					handler1,
					"listResponder", //$NON-NLS-1$
					Configuration.XML_TOKEN);
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage()); //$NON-NLS-1$
		}
		assertFalse(fwd.isEventType());
		assertFalse(fwd.isResourceType());
		assertTrue(fwd.isResponderType());
	}

	@SuppressWarnings("null")
	@Test
	public void testXMLResponderNotInHTMLForwardsMap() {
		List<EventHandlerProxy> eventHandlers = null;
		try {
			eventHandlers = this.config.getHandlers("event112"); //$NON-NLS-1$
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage()); //$NON-NLS-1$
		}
		assertNotNull(eventHandlers);

		EventHandlerProxy handler1 = eventHandlers.get(0);
		assertNotNull(handler1);
		// test xml responder does not exist in HTML Fowards map 
		Forward html = handler1.getDefinition().getHTMLForward("local14"); //$NON-NLS-1$
        assertNotNull(html);
		Forward xmlResponderNotExist =
			handler1.getDefinition().getHTMLForward("listResponder"); //$NON-NLS-1$
		assertNull(xmlResponderNotExist);
	}

	@Test
	public void testHandlerBinding_Cache() {
		try {
			List<EventHandlerProxy> eventHandlers1 = this.config.getHandlers("event1"); //$NON-NLS-1$
			List<EventHandlerProxy> eventHandlers2 = this.config.getHandlers("event1"); //$NON-NLS-1$

			assertSame(eventHandlers1, eventHandlers2);
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage()); //$NON-NLS-1$
		}
	}

	@Test
	public void testHandlerBinding_UnimplementedHandler() {
		assertGetHandlerFails(this.config, "event10"); //$NON-NLS-1$
	}

	@Test
	public void testHandlerBinding_InvalidNames() {
		assertGetHandlerFails(this.config, null);
		assertGetHandlerFails(this.config, ""); //$NON-NLS-1$
		assertGetHandlerFails(this.config, "bogusEventName"); //$NON-NLS-1$
	}

	@Test
	public void testHandlerBinding_NoHandlerConfigured() {
		assertGetHandlerFails(this.config, "event9"); //$NON-NLS-1$
	}

	@SuppressWarnings("null")
	@Test
	public void testBothViewType() {
		String htmlForwardName = null;
		try {
			htmlForwardName =
				this.config.getEventMappingView("event2", ViewType.HTML); //$NON-NLS-1$
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage()); //$NON-NLS-1$
		}
		assertEquals("view4", htmlForwardName); //$NON-NLS-1$
		String xmlForwardName = null;
		try {
			xmlForwardName =
				this.config.getEventMappingView("event2", ViewType.XML); //$NON-NLS-1$
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage()); //$NON-NLS-1$
		}
		assertEquals("view4", xmlForwardName); //$NON-NLS-1$

		// test XML View
		ForwardProxy fwd = null;
		try {
			fwd = this.config.resolveForward("view4", Configuration.XML_TOKEN); //$NON-NLS-1$
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage()); //$NON-NLS-1$
		}
		assertFalse(fwd.isEventType());
		assertTrue(fwd.isResourceType());
		assertFalse(fwd.isResponderType());
		assertEquals("testXmlResource", fwd.getPath()); //$NON-NLS-1$

		// test HTML View
		try {
			fwd = this.config.resolveForward("view4", Configuration.HTML_TOKEN); //$NON-NLS-1$
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage()); //$NON-NLS-1$
		}
		assertFalse(fwd.isEventType());
		assertTrue(fwd.isResourceType());
		assertFalse(fwd.isResponderType());
		assertEquals("/view4.jsp", fwd.getPath()); //$NON-NLS-1$
	}

	@Test
	public void testNonExistingXMLViewType() {
		try {
			this.config.getEventMappingView("event14", ViewType.XML); //$NON-NLS-1$
			fail("We should have gotten an exception"); //$NON-NLS-1$
		} catch (ViewException expected) {
			//Expected
		}
	}

	@Test
	public void testNonExistingXMLGlobalForwardType() {
		// test XML View
		try {
			this.config.resolveForward("event1", Configuration.XML_TOKEN); //$NON-NLS-1$
			fail("We should have gotten an exception"); //$NON-NLS-1$
		} catch (ViewException expected) {
			//Expected
		}
	}

	@Test
	public void testEventDefaultResolveAs() {
		try {
			EventProxy eProxy = this.config.getEventProxy("event1"); //$NON-NLS-1$
			assertTrue(eProxy.isParent());
			assertFalse(eProxy.isChildren());
			assertFalse(eProxy.isPassThru());
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage()); //$NON-NLS-1$
		}
	}

	@Test
	public void testEventResolveAsChildren() {
		try {
			EventProxy eProxy = this.config.getEventProxy("event12"); //$NON-NLS-1$
			assertFalse(eProxy.isParent());
			assertTrue(eProxy.isChildren());
			assertFalse(eProxy.isPassThru());
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage()); //$NON-NLS-1$
		}
	}

	@Test
	public void testEventResolveAsPassThru() {
		try {
			EventProxy eProxy = this.config.getEventProxy("event2"); //$NON-NLS-1$
			assertFalse(eProxy.isParent());
			assertFalse(eProxy.isChildren());
			assertTrue(eProxy.isPassThru());
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage()); //$NON-NLS-1$
		}
	}

	@Test
	public void testExceptionProxy() {
		ConfigTestException ex = new ConfigTestException();
		ExceptionProxy exception = null;
		try {
			exception =
				this.config.resolveException(
					ex,
					Configuration.HTML_TOKEN,
					ViewType.HTML);
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage()); //$NON-NLS-1$
			e.printStackTrace();
		}
		assertNotNull(exception);
	}

	@SuppressWarnings("null")
	@Test
	public void testExceptionProxyHTMLFowardPath() {
		ConfigTestException ex = new ConfigTestException();
		ExceptionProxy exception = null;
		try {
			exception =
				this.config.resolveException(
					ex,
					Configuration.HTML_TOKEN,
					ViewType.HTML);
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage()); //$NON-NLS-1$
		}
		assertNotNull(exception);
		String htmlForwardPath = exception.getPath();
		assertEquals("/view4.jsp", htmlForwardPath); //$NON-NLS-1$
	}

	@SuppressWarnings("null")
	@Test
	public void testExceptionProxyXMLFowardPath() {
		ConfigTestException ex = new ConfigTestException();
		ExceptionProxy exception = null;
		try {
			exception =
				this.config.resolveException(
					ex,
					Configuration.XML_TOKEN,
					ViewType.XML);
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage()); //$NON-NLS-1$
		}
		assertNotNull(exception);
		String xmlForwardPath = exception.getPath();
		assertEquals("testViewClass", xmlForwardPath); //$NON-NLS-1$
	}

	@SuppressWarnings("null")
	@Test
	public void testRedirect() {
		ForwardProxy fwd = null;
		try {
			fwd = this.config.resolveForward("view1", Configuration.HTML_TOKEN); //$NON-NLS-1$
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage()); //$NON-NLS-1$
		}
		assertFalse(fwd.isRedirect());
		try {
			fwd = this.config.resolveForward("redirect", Configuration.HTML_TOKEN); //$NON-NLS-1$
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage()); //$NON-NLS-1$
		}
		assertTrue(fwd.isRedirect());
	}

	@Test
	public void testInputViewFor() {
		try {
			ForwardProxy proxy = this.config.inputViewFor("event3", Configuration.HTML_TOKEN); //$NON-NLS-1$
			assertNotNull(proxy);
			assertEquals("/view3.jsp",proxy.getPath()); //$NON-NLS-1$
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage()); //$NON-NLS-1$
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage()); //$NON-NLS-1$
		}

		try {
			this.config.inputViewFor("event111", Configuration.XML_TOKEN); //$NON-NLS-1$
			fail();
		} catch (Exception expected) {
			//expected
		}
	}

	@SuppressWarnings("null")
	@Test
	public void testCancelViewFor() {
		ForwardProxy fwd = null;
		try {
			fwd = this.config.cancelViewFor("event3", Configuration.HTML_TOKEN); //$NON-NLS-1$
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage()); //$NON-NLS-1$
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage()); //$NON-NLS-1$
		}

		assertEquals("/view2.jsp", fwd.getPath()); //$NON-NLS-1$

		try {
			this.config.cancelViewFor("event111", Configuration.XML_TOKEN); //$NON-NLS-1$
			fail();
		} catch (Exception expected) {
			//expected
		}
	}

	@Test
	public void testPluginProxy() {
		List<PluginProxy> proxies = this.config.getPluginProxies();
		assertTrue(proxies.size() == 5);

		PluginProxy proxy = proxies.get(0);
		assertTrue(proxy.getName().equals("PluginName1")); //$NON-NLS-1$
		assertNotNull(proxy.getPlugin());

		proxy = proxies.get(1);
		assertTrue(proxy.getName().equals("PluginName2")); //$NON-NLS-1$
		assertNotNull(proxy.getPlugin());
		Map<String, String> params = proxy.getInitParams();
		assertTrue(params.size() == 1);
		assertTrue(params.get("param1").equals("value1")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testNegativePluginProxy() {
		try {
			this.config =
				new Configuration("org/megatome/frame2/front/test-negative-plugin-config.xml"); //$NON-NLS-1$
			fail();
		} catch (ConfigException expected) {
			//Expected
		}
	}

	private void assertGetHandlerFails(
		Configuration cfg,
		String eventName) {
		try {
			cfg.getHandlers(eventName);
			fail();
		} catch (ConfigException expected) {
			//expected
		}
	}

	private void assertGetEventFails(Configuration cfg, String eventName) {
		try {
			cfg.getEventProxy(eventName).getEvent();
			fail();
		} catch (ConfigException expected) {
			//expected
		}
	}
}
