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
	private Configuration _config;

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
		_config =
			new Configuration("org/megatome/frame2/front/test-config.xml");
	}

	public void testEventBinding() {
		Event event = null;
		try {
			event = _config.getEventProxy("event1").getEvent();
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage());
		}

		assertNotNull(event);
	}

	public void testEventBinding_Empty() {
		assertGetEventFails(_config, "");
	}

	public void testEventBinding_WrongEventType() {
		assertGetEventFails(_config, "wrongEventType");
	}

	public void testEventBinding_TypeMissing() {
		assertGetEventFails(_config, "bogus1");
	}

	public void testEventBinding_TypeNotEvent() {
		assertGetEventFails(_config, "bogus2");
	}

	public void testEventBinding_NullName() {
		assertGetEventFails(_config, null);
	}

	public void testHandlerBinding() {
		List eventHandlers = null;
		try {
			eventHandlers = _config.getHandlers("event1");
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
			eventHandlers = _config.getHandlers("event2");
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
			eventHandlers = _config.getHandlers("event111");
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
				_config.resolveForward(
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
			eventHandlers = _config.getHandlers("event112");
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage());
		}

		assertNotNull(eventHandlers);

		EventHandlerProxy handler1 = (EventHandlerProxy)eventHandlers.get(0);
		assertNotNull(handler1);

		Forward xml = handler1.getDefinition().getXMLForward("local14");
		Forward xmlResponder =
			handler1.getDefinition().getXMLForward("listResponder");
		assertNotNull(xmlResponder);
		assertEquals("testLocalListResponder", xmlResponder.getPath());

		ForwardProxy fwd = null;
		try {
			fwd =
				_config.resolveForward(
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
			eventHandlers = _config.getHandlers("event112");
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage());
		}
		assertNotNull(eventHandlers);

		EventHandlerProxy handler1 = (EventHandlerProxy)eventHandlers.get(0);
		assertNotNull(handler1);
		// test xml responder does not exist in HTML Fowards map 
		Forward html = handler1.getDefinition().getHTMLForward("local14");
		Forward xmlResponderNotExist =
			handler1.getDefinition().getHTMLForward("listResponder");
		assertNull(xmlResponderNotExist);
	}

	public void testHandlerBinding_Cache() {
		try {
			List eventHandlers1 = _config.getHandlers("event1");
			List eventHandlers2 = _config.getHandlers("event1");

			assertSame(eventHandlers1, eventHandlers2);
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage());
		}
	}

	public void testHandlerBinding_UnimplementedHandler() {
		assertGetHandlerFails(_config, "event10");
	}

	public void testHandlerBinding_InvalidNames() {
		assertGetHandlerFails(_config, null);
		assertGetHandlerFails(_config, "");
		assertGetHandlerFails(_config, "bogusEventName");
	}

	public void testHandlerBinding_NoHandlerConfigured() {
		assertGetHandlerFails(_config, "event9");
	}

	public void testBothViewType() {
		String htmlForwardName = null;
		try {
			htmlForwardName =
				_config.getEventMappingView("event2", ViewType.HTML);
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage());
		}
		assertEquals("view4", htmlForwardName);
		String xmlForwardName = null;
		try {
			xmlForwardName =
				_config.getEventMappingView("event2", ViewType.XML);
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage());
		}
		assertEquals("view4", xmlForwardName);

		// test XML View
		ForwardProxy fwd = null;
		try {
			fwd = _config.resolveForward("view4", Configuration.XML_TOKEN);
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage());
		}
		assertFalse(fwd.isEventType());
		assertTrue(fwd.isResourceType());
		assertFalse(fwd.isResponderType());
		assertEquals("testXmlResource", fwd.getPath());

		// test HTML View
		try {
			fwd = _config.resolveForward("view4", Configuration.HTML_TOKEN);
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
				_config.getEventMappingView("event14", ViewType.XML);
			fail("We should have gotten an exception");
		} catch (ViewException e) {
			assertNull(xmlForwardName);
		}
	}

	public void testNonExistingXMLGlobalForwardType() {
		// test XML View
		ForwardProxy fwd = null;
		try {
			fwd = _config.resolveForward("event1", Configuration.XML_TOKEN);
			fail("We should have gotten an exception");
		} catch (ViewException e) {
			assertNull(fwd);
		}
	}

	public void testEventDefaultResolveAs() {
		try {
			EventProxy eProxy = _config.getEventProxy("event1");
			assertTrue(eProxy.isParent());
			assertFalse(eProxy.isChildren());
			assertFalse(eProxy.isPassThru());
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage());
		}
	}

	public void testEventResolveAsChildren() {
		try {
			EventProxy eProxy = _config.getEventProxy("event12");
			assertFalse(eProxy.isParent());
			assertTrue(eProxy.isChildren());
			assertFalse(eProxy.isPassThru());
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage());
		}
	}

	public void testEventResolveAsPassThru() {
		try {
			EventProxy eProxy = _config.getEventProxy("event2");
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
				_config.resolveException(
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
				_config.resolveException(
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
				_config.resolveException(
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
			fwd = _config.resolveForward("view1", Configuration.HTML_TOKEN);
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage());
		}
		assertFalse(fwd.isRedirect());
		try {
			fwd = _config.resolveForward("redirect", Configuration.HTML_TOKEN);
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage());
		}
		assertTrue(fwd.isRedirect());
	}

	public void testInputViewFor() {
		try {
			assertEquals(
				"/view3.jsp",
				_config.inputViewFor("event3", Configuration.HTML_TOKEN));
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage());
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage());
		}

		try {
			_config.inputViewFor("event111", Configuration.XML_TOKEN);
			fail();
		} catch (Exception e) {}
	}

	public void testCancelViewFor() {
		ForwardProxy fwd = null;
		try {
			fwd = _config.cancelViewFor("event3", Configuration.HTML_TOKEN);
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage());
		} catch (ViewException e) {
			fail("Unexpected ViewException: " + e.getMessage());
		}

		assertEquals("/view2.jsp", fwd.getPath());

		try {
			_config.cancelViewFor("event111", Configuration.XML_TOKEN);
			fail();
		} catch (Exception e) {}
	}

	public void testPluginProxy() {
		List proxies = _config.getPluginProxies();
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
			_config =
				new Configuration("org/megatome/frame2/front/test-negative-plugin-config.xml");
		} catch (ConfigException e) {
			return;
		}
		fail();
	}

	private void assertGetHandlerFails(
		Configuration config,
		String eventName) {
		try {
			config.getHandlers(eventName);
			fail();
		} catch (ConfigException e) {}
	}

	private void assertGetEventFails(Configuration config, String eventName) {
		try {
			Event event = config.getEventProxy(eventName).getEvent();
			fail();
		} catch (ConfigException e) {}
	}
}
