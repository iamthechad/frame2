package org.megatome.frame2.front.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.megatome.frame2.Frame2Exception;
import org.megatome.frame2.front.ForwardProxy;
import org.megatome.frame2.front.config.EventConfigReader;
import org.megatome.frame2.front.config.EventDef;
import org.megatome.frame2.front.config.EventHandlerDef;
import org.megatome.frame2.front.config.EventMapping;
import org.megatome.frame2.front.config.Forward;
import org.megatome.frame2.front.config.ForwardType;
import org.megatome.frame2.front.config.PluginDef;
import org.megatome.frame2.front.config.RequestProcessorDef;
import org.megatome.frame2.front.config.ViewType;

/**
 * TestWAMConfigReader.java
 */
public class TestEventConfigReader extends TestCase {
	/**
	 * Constructor for TestEventConfigReader.
	 */
	public TestEventConfigReader() {
		super();
	}

	/**
	 * Constructor for TestEventConfigReader.
	 * @param name
	 */
	public TestEventConfigReader(String name) {
		super(name);
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Method testSingleGlobalForward.
	 */
	public void testSingleGlobalForward() {

		String filename =
			"org/megatome/frame2/front/config/globalForward-Single-config.xml";
		String name = "xxx";
		String path = "xxxpath";

		EventConfigReader reader = new EventConfigReader(filename);
		try {
			reader.execute();
		} catch (Frame2Exception e) {
			fail("Unexpected Exception: " + e.getMessage());
		}

		Map forwards = reader.getGlobalHTMLForwards();
		assertTrue(forwards.size() == 1);
		Forward forward = (Forward)forwards.get(name);
		ForwardProxy proxy = new ForwardProxy(forward);
		assertTrue(proxy.isEventType());
		assertEquals(forward.getName(), name);
		assertEquals(forward.getType(), ForwardType.EVENT);
		assertEquals(forward.getPath(), path);

	}

	/**
	 * Method testMultipleGlobalForward.
	 * @throws Exception
	 */
	public void testMultipleGlobalForward() {

		String filename =
			"org/megatome/frame2/front/config/globalForward-Multiple-config.xml";

		EventConfigReader reader = new EventConfigReader(filename);
		try {
			reader.execute();
		} catch (Frame2Exception e) {
			fail("Unexpected Exception: " + e.getMessage());
		}

		Map forwards = reader.getGlobalHTMLForwards();
		assertTrue(forwards.size() == 3);

		Forward forward = (Forward)forwards.get("xx1");
		ForwardProxy proxy = new ForwardProxy(forward);
		assertFalse(proxy.isEventType());
		assertEquals(forward.getName(), "xx1");
		assertEquals(forward.getType(), ForwardType.HTMLRESOURCE);
		assertEquals(forward.getPath(), "xxxpath1");

		forward = (Forward)forwards.get("xx2");
		proxy = new ForwardProxy(forward);
		assertTrue(proxy.isEventType());
		assertEquals(forward.getName(), "xx2");
		assertEquals(forward.getType(), ForwardType.EVENT);
		assertEquals(forward.getPath(), "xxxpath2");

		forward = (Forward)forwards.get("xx3");
		proxy = new ForwardProxy(forward);
		assertFalse(proxy.isEventType());
		assertFalse(proxy.isEventType());
		assertEquals(forward.getName(), "xx3");
		assertEquals(forward.getType(), ForwardType.HTMLRESOURCE);
		assertEquals(forward.getPath(), "xxxpath3");

	}

	/**
	 * Method testEvents.
	 * @throws Exception
	 */
	public void testEvents() {

		String filename = "org/megatome/frame2/front/config/events-config.xml";

		EventConfigReader reader = new EventConfigReader(filename);
		try {
			reader.execute();
		} catch (Frame2Exception e) {
			fail("Unexpected Exception: " + e.getMessage());
		}

		Map forwards = reader.getGlobalHTMLForwards();
		assertTrue(forwards.size() == 3);
		Map events = reader.getEvents();
		assertTrue(events.size() == 3);
		assertEquals(
			((EventDef)events.get("event1")).getType(),
			"com.frame2.event1");
		assertEquals(
			((EventDef)events.get("event2")).getType(),
			"com.frame2.event2");
		assertEquals(
			((EventDef)events.get("event3")).getType(),
			"com.frame2.event3");
	}

	/**
	 * Method testEventMappings.
	 * @throws Exception
	 */
	public void testEventMappings() {

		String filename =
			"org/megatome/frame2/front/config/eventMappings-config.xml";

		EventConfigReader reader = new EventConfigReader(filename);
		try {
			reader.execute();
		} catch (Frame2Exception e) {
			fail("Unexpected Exception: " + e.getMessage());
		}

		Map forwards = reader.getGlobalHTMLForwards();

		assertTrue(forwards.size() == 3);
		Map events = reader.getEvents();
		assertTrue(events.size() == 3);
		// EventMappings
		Map eventMappings = reader.getEventMappings();
		assertTrue(eventMappings.size() == 4);
		// Test first EventMapping
		EventMapping eventMapping =
			(EventMapping)eventMappings.get("eventMapping1");
		assertNotNull(eventMapping);
		ArrayList handlers = eventMapping.getHandlers();
		assertTrue(handlers.size() == 3);
		Iterator iter = handlers.iterator();
		int count = 0;
		while (iter.hasNext()) {
			count++;
			String handler = (String)iter.next();
			switch (count) {
				case 1 :
					assertEquals(handler, "handler1-eventMapping1");
					break;
				case 2 :
					assertEquals(handler, "handler2-eventMapping1");
					break;
				case 3 :
					assertEquals(handler, "handler3-eventMapping1");
					break;
			}
		}
		assertEquals(eventMapping.getInputView(), "inputView1");
		assertEquals(eventMapping.getCancelView(), "cancelView1");
		assertTrue(eventMapping.isValidate());
		assertEquals(
			eventMapping.getView(ViewType.HTML.toString()),
			"view1-eventMapping1");
		assertTrue(eventMapping.isUserInRole("admin"));
		assertTrue(eventMapping.isUserInRole("guest"));
		assertTrue(!eventMapping.isUserInRole("foo"));

		String[] roles = eventMapping.getRoles();

		assertNotNull(roles);
		assertEquals(2, roles.length);
		assertEquals("admin", roles[0]);
		assertEquals("guest", roles[1]);

		// Test second EventMapping
		eventMapping = (EventMapping)eventMappings.get("eventMapping2");
		assertNotNull(eventMapping);
		handlers = eventMapping.getHandlers();
		assertTrue(handlers.size() == 0);
		assertEquals(eventMapping.getInputView(), "inputView2");
		assertEquals(eventMapping.getCancelView(), null);
		assertFalse(eventMapping.isValidate());
		assertEquals(
			eventMapping.getView(ViewType.HTML.toString()),
			"view1-eventMapping2");
		assertTrue(!eventMapping.isUserInRole("admin"));

		roles = eventMapping.getRoles();

		assertNotNull(roles);
		assertEquals(0, roles.length);

		// Test third EventMapping
		eventMapping = (EventMapping)eventMappings.get("eventMapping3");
		assertNotNull(eventMapping);
		handlers = eventMapping.getHandlers();
		assertTrue(handlers.size() == 1);
		assertTrue(eventMapping.isValidate());
		assertNull(eventMapping.getView(ViewType.HTML.toString()));
		assertTrue(!eventMapping.isUserInRole("admin"));

		// Test fourth EventMapping
		eventMapping = (EventMapping)eventMappings.get("eventMapping4");
		assertNotNull(eventMapping);
		handlers = eventMapping.getHandlers();
		assertTrue(handlers.size() == 0);
		assertNull(eventMapping.getInputView());
		assertFalse(eventMapping.isValidate());
		assertNull(eventMapping.getView(ViewType.HTML.toString()));
		assertTrue(!eventMapping.isUserInRole("admin"));
	}

	/**
	 * Method testEmptyTags.
	 * @throws Exception
	 */
	public void testEmptyTags() {
		String filename =
			"org/megatome/frame2/front/config/emptyTags-config.xml";

		EventConfigReader reader = new EventConfigReader(filename);
		try {
			reader.execute();
		} catch (Frame2Exception e) {
			fail("Unexpected Exception: " + e.getMessage());
		}

		Map forwards = reader.getGlobalHTMLForwards();
		assertTrue(forwards.size() == 0);
		Map events = reader.getEvents();
		assertTrue(events.size() == 0);
		Map eventMappings = reader.getEventMappings();
		assertTrue(eventMappings.size() == 0);
		Map eventHandlers = reader.getEventHandlers();
		assertTrue(eventHandlers.size() == 0);

	}

	/**
	 * Method testEmptyTags.
	 * @throws Exception
	 */
	public void testEventHandlers() {
		String filename =
			"org/megatome/frame2/front/config/eventHandlers-config.xml";

		EventConfigReader reader = new EventConfigReader(filename);
		try {
			reader.execute();
		} catch (Frame2Exception e) {
			fail("Unexpected Exception: " + e.getMessage());
		}

		Map forwards = reader.getGlobalHTMLForwards();
		assertTrue(forwards.size() == 0);
		Map events = reader.getEvents();
		assertTrue(events.size() == 0);
		Map eventMappings = reader.getEventMappings();
		assertTrue(eventMappings.size() == 0);
		Map eventHandlers = reader.getEventHandlers();
		assertTrue(eventHandlers.size() == 3);

		// handler1
		EventHandlerDef handler =
			(EventHandlerDef)eventHandlers.get("event-handler1");
		assertEquals(handler.getType(), "com.frame2.eventHandler1");
		assertNull(handler.getInitParam("test"));
		Map params = handler.getInitParams();
		assertTrue(params.size() == 0);

		assertEquals(handler.getHTMLForward("forward1").getPath(), "path1");

		// handler2
		handler = (EventHandlerDef)eventHandlers.get("event-handler2");
		assertEquals(handler.getType(), "com.frame2.eventHandler2");
		assertNull(handler.getInitParam("test"));
		params = handler.getInitParams();
		assertTrue(params.size() == 0);
		assertEquals(handler.getHTMLForward("forward1").getPath(), "path1");
		assertEquals(handler.getHTMLForward("forward2").getPath(), "path2");
		assertNull(handler.getHTMLForward("test1"));

		// handler3
		handler = (EventHandlerDef)eventHandlers.get("event-handler3");
		assertEquals(handler.getType(), "com.frame2.eventHandler3");
		assertNull(handler.getInitParam("test"));
		params = handler.getInitParams();
		assertTrue(params.size() == 2);
		assertEquals(handler.getInitParam("param1"), "val1");
		assertEquals(handler.getInitParam("param2"), "val2");
		assertNull(handler.getHTMLForward("forward1"));
	}
	/**
	 * Method testEmptyTags.
	 * @throws Exception
	 */
	public void testPluginSingle() {
		String filename =
			"org/megatome/frame2/front/config/pluginTag-Single-1_1.xml";

		EventConfigReader reader = new EventConfigReader(filename);
		try {
			reader.execute();
		} catch (Frame2Exception e) {
			fail("Unexpected Exception: " + e.getMessage());
		}

		List plugins = reader.getPlugins();
		assertTrue(plugins.size() == 1);
		Iterator iter = plugins.iterator();
		while (iter.hasNext()) {
			PluginDef plugin = (PluginDef)iter.next();
			assertTrue(plugin.getName().equals("PluginName"));
			assertTrue(plugin.getType().equals("org.megatome.something"));
		}
	}
	public void testPluginMultiple() {
		String filename =
			"org/megatome/frame2/front/config/pluginTag-Mult-Param-1_1.xml";

		EventConfigReader reader = new EventConfigReader(filename);
		try {
			reader.execute();
		} catch (Frame2Exception e) {
			fail("Unexpected Exception: " + e.getMessage());
		}

		List plugins = reader.getPlugins();
		assertTrue(plugins.size() == 3);
		PluginDef plugin = (PluginDef)plugins.get(0);
		assertTrue(plugin.getName().equals("PluginName1"));
		assertTrue(plugin.getType().equals("org.megatome.something1"));
		Map params = plugin.getInitParams();
		assertNotNull(params);
		String val = (String)params.get("param1");
		assertTrue(val.equals("value1"));

		plugin = (PluginDef)plugins.get(1);
		assertTrue(plugin.getName().equals("PluginName2"));
		assertTrue(plugin.getType().equals("org.megatome.something"));
		params = plugin.getInitParams();
		assertNotNull(params);
		val = (String)params.get("param1");
		assertTrue(val.equals("value1"));
		val = (String)params.get("param2");
		assertTrue(val.equals("value2"));

		plugin = (PluginDef)plugins.get(2);
		assertTrue(plugin.getName().equals("PluginName3"));
		assertTrue(plugin.getType().equals("org.megatome.something"));
		params = plugin.getInitParams();
		assertNotNull(params);
		assertTrue(params.isEmpty());
	}

	public void testPluginMultipleDuplicate() {
		String filename =
			"org/megatome/frame2/front/config/pluginTag-Dup-Negative-Param-1_1.xml";

		EventConfigReader reader = new EventConfigReader(filename);
		try {
			reader.execute();
		} catch (Frame2Exception e) {
			fail("Unexpected Exception: " + e.getMessage());
		}

		List plugins = reader.getPlugins();
		Iterator iter = plugins.iterator();
		assertTrue(plugins.size() == 1);
		while (iter.hasNext()) {
			PluginDef plugin = (PluginDef)iter.next();
			assertTrue(plugin.getName().equals("PluginName1"));
			assertTrue(plugin.getType().equals("org.megatome.something1"));
			Map params = plugin.getInitParams();
			assertNotNull(params);
			String val = (String)params.get("param1");
			assertTrue(val.equals("value1"));
		}
	}

	public void testNegativeDuplicateInitParam() {
		String filename =
			"org/megatome/frame2/front/config/pluginTag-Single-Negative-DupParam-1_1.xml";

		EventConfigReader reader = new EventConfigReader(filename);
		try {
			reader.execute();
		} catch (Frame2Exception e) {
			return;
		}

		fail();
	}

	public void testReqProcEmpty() {
		String filename =
			"org/megatome/frame2/front/config/requestProc-Empty-1_1.xml";

		EventConfigReader reader = new EventConfigReader(filename);
		try {
			reader.execute();
		} catch (Frame2Exception e) {
			fail("Unexpected Exception: " + e.getMessage());
		}

		RequestProcessorDef httpRequestProcessor =
			reader.getHttpReqProcHandler();
		assertNull(httpRequestProcessor);
		RequestProcessorDef soapRequestProcessor =
			reader.getSoapReqProcHandler();
		assertNull(soapRequestProcessor);

	}

	public void testTwoProcEmpty() {
		String filename =
			"org/megatome/frame2/front/config/requestProc-SoapAndHttp-1_1.xml";

		EventConfigReader reader = new EventConfigReader(filename);
		try {
			reader.execute();
		} catch (Frame2Exception e) {
			fail("Unexpected Exception: " + e.getMessage());
		}

		RequestProcessorDef httpRequestProcessor =
			reader.getHttpReqProcHandler();
		assertEquals(httpRequestProcessor.getType(), "httpBaby");
		RequestProcessorDef soapRequestProcessor =
			reader.getSoapReqProcHandler();
		assertEquals(soapRequestProcessor.getType(), "soap");

	}

}
