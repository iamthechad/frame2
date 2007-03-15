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
package org.megatome.frame2.front.config;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.megatome.frame2.Frame2Exception;
import org.megatome.frame2.front.ForwardProxy;

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
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Method testSingleGlobalForward.
	 */
	public void testSingleGlobalForward() {

		String name = "xxx"; //$NON-NLS-1$
		String path = "xxxpath"; //$NON-NLS-1$

		EventConfigReader reader = getReader("org/megatome/frame2/front/config/globalForward-Single-config.xml"); //$NON-NLS-1$

		Map<String, Forward> forwards = reader.getGlobalHTMLForwards();
		assertTrue(forwards.size() == 1);
		Forward forward = forwards.get(name);
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
		EventConfigReader reader = getReader("org/megatome/frame2/front/config/globalForward-Multiple-config.xml"); //$NON-NLS-1$

		Map<String, Forward> forwards = reader.getGlobalHTMLForwards();
		assertTrue(forwards.size() == 3);

		Forward forward = forwards.get("xx1"); //$NON-NLS-1$
		ForwardProxy proxy = new ForwardProxy(forward);
		assertFalse(proxy.isEventType());
		assertEquals(forward.getName(), "xx1"); //$NON-NLS-1$
		assertEquals(forward.getType(), ForwardType.HTMLRESOURCE);
		assertEquals(forward.getPath(), "xxxpath1"); //$NON-NLS-1$

		forward = forwards.get("xx2"); //$NON-NLS-1$
		proxy = new ForwardProxy(forward);
		assertTrue(proxy.isEventType());
		assertEquals(forward.getName(), "xx2"); //$NON-NLS-1$
		assertEquals(forward.getType(), ForwardType.EVENT);
		assertEquals(forward.getPath(), "xxxpath2"); //$NON-NLS-1$

		forward = forwards.get("xx3"); //$NON-NLS-1$
		proxy = new ForwardProxy(forward);
		assertFalse(proxy.isEventType());
		assertEquals(forward.getName(), "xx3"); //$NON-NLS-1$
		assertEquals(forward.getType(), ForwardType.HTMLRESOURCE);
		assertEquals(forward.getPath(), "xxxpath3"); //$NON-NLS-1$

	}

	/**
	 * Method testEvents.
	 * @throws Exception
	 */
	public void testEvents() {
		EventConfigReader reader = getReader("org/megatome/frame2/front/config/events-config.xml"); //$NON-NLS-1$

		Map<String, Forward> forwards = reader.getGlobalHTMLForwards();
		assertTrue(forwards.size() == 3);
		Map<String, EventDef> events = reader.getEvents();
		assertTrue(events.size() == 3);
		assertEquals(
			events.get("event1").getType(), //$NON-NLS-1$
			"com.frame2.event1"); //$NON-NLS-1$
		assertEquals(
			events.get("event2").getType(), //$NON-NLS-1$
			"com.frame2.event2"); //$NON-NLS-1$
		assertEquals(
			events.get("event3").getType(), //$NON-NLS-1$
			"com.frame2.event3"); //$NON-NLS-1$
	}

	/**
	 * Method testEventMappings.
	 * @throws Exception
	 */
	public void testEventMappings() {
		EventConfigReader reader = getReader("org/megatome/frame2/front/config/eventMappings-config.xml"); //$NON-NLS-1$

		Map<String, Forward> forwards = reader.getGlobalHTMLForwards();

		assertTrue(forwards.size() == 3);
		Map<String, EventDef> events = reader.getEvents();
		assertTrue(events.size() == 3);
		// EventMappings
		Map<String, EventMapping> eventMappings = reader.getEventMappings();
		assertTrue(eventMappings.size() == 4);
		// Test first EventMapping
		EventMapping eventMapping =
			eventMappings.get("eventMapping1"); //$NON-NLS-1$
		assertNotNull(eventMapping);
		List<String> handlers = eventMapping.getHandlers();
		assertTrue(handlers.size() == 3);
		Iterator<String> iter = handlers.iterator();
		int count = 0;
		while (iter.hasNext()) {
			count++;
			String handler = iter.next();
			switch (count) {
				case 1 :
					assertEquals(handler, "handler1-eventMapping1"); //$NON-NLS-1$
					break;
				case 2 :
					assertEquals(handler, "handler2-eventMapping1"); //$NON-NLS-1$
					break;
				case 3 :
					assertEquals(handler, "handler3-eventMapping1"); //$NON-NLS-1$
					break;
			}
		}
		assertEquals(eventMapping.getInputView(), "inputView1"); //$NON-NLS-1$
		assertEquals(eventMapping.getCancelView(), "cancelView1"); //$NON-NLS-1$
		assertTrue(eventMapping.isValidate());
		assertEquals(
			eventMapping.getView(ViewType.HTML.toString()),
			"view1-eventMapping1"); //$NON-NLS-1$
		assertTrue(eventMapping.isUserInRole("admin")); //$NON-NLS-1$
		assertTrue(eventMapping.isUserInRole("guest")); //$NON-NLS-1$
		assertTrue(!eventMapping.isUserInRole("foo")); //$NON-NLS-1$

		String[] roles = eventMapping.getRoles();

		assertNotNull(roles);
		assertEquals(2, roles.length);
		assertEquals("admin", roles[0]); //$NON-NLS-1$
		assertEquals("guest", roles[1]); //$NON-NLS-1$

		// Test second EventMapping
		eventMapping = eventMappings.get("eventMapping2"); //$NON-NLS-1$
		assertNotNull(eventMapping);
		handlers = eventMapping.getHandlers();
		assertTrue(handlers.size() == 0);
		assertEquals(eventMapping.getInputView(), "inputView2"); //$NON-NLS-1$
		assertEquals(eventMapping.getCancelView(), null);
		assertFalse(eventMapping.isValidate());
		assertEquals(
			eventMapping.getView(ViewType.HTML.toString()),
			"view1-eventMapping2"); //$NON-NLS-1$
		assertTrue(!eventMapping.isUserInRole("admin")); //$NON-NLS-1$

		roles = eventMapping.getRoles();

		assertNotNull(roles);
		assertEquals(0, roles.length);

		// Test third EventMapping
		eventMapping = eventMappings.get("eventMapping3"); //$NON-NLS-1$
		assertNotNull(eventMapping);
		handlers = eventMapping.getHandlers();
		assertTrue(handlers.size() == 1);
		assertTrue(eventMapping.isValidate());
		assertNull(eventMapping.getView(ViewType.HTML.toString()));
		assertTrue(!eventMapping.isUserInRole("admin")); //$NON-NLS-1$

		// Test fourth EventMapping
		eventMapping = eventMappings.get("eventMapping4"); //$NON-NLS-1$
		assertNotNull(eventMapping);
		handlers = eventMapping.getHandlers();
		assertTrue(handlers.size() == 0);
		assertNull(eventMapping.getInputView());
		assertFalse(eventMapping.isValidate());
		assertNull(eventMapping.getView(ViewType.HTML.toString()));
		assertTrue(!eventMapping.isUserInRole("admin")); //$NON-NLS-1$
	}

	/**
	 * Method testEmptyTags.
	 * @throws Exception
	 */
	public void testEmptyTags() {
		EventConfigReader reader = getReader("org/megatome/frame2/front/config/emptyTags-config.xml"); //$NON-NLS-1$

		Map<String, Forward> forwards = reader.getGlobalHTMLForwards();
		assertTrue(forwards.size() == 0);
		Map<String, EventDef> events = reader.getEvents();
		assertTrue(events.size() == 0);
		Map<String, EventMapping> eventMappings = reader.getEventMappings();
		assertTrue(eventMappings.size() == 0);
		Map<String, EventHandlerDef> eventHandlers = reader.getEventHandlers();
		assertTrue(eventHandlers.size() == 0);

	}

	/**
	 * Method testEmptyTags.
	 * @throws Exception
	 */
	public void testEventHandlers() {
		EventConfigReader reader = getReader("org/megatome/frame2/front/config/eventHandlers-config.xml"); //$NON-NLS-1$

		Map<String, Forward> forwards = reader.getGlobalHTMLForwards();
		assertTrue(forwards.size() == 0);
		Map<String, EventDef> events = reader.getEvents();
		assertTrue(events.size() == 0);
		Map<String, EventMapping> eventMappings = reader.getEventMappings();
		assertTrue(eventMappings.size() == 0);
		Map<String, EventHandlerDef> eventHandlers = reader.getEventHandlers();
		assertTrue(eventHandlers.size() == 3);

		// handler1
		EventHandlerDef handler =
			eventHandlers.get("event-handler1"); //$NON-NLS-1$
		assertEquals(handler.getType(), "com.frame2.eventHandler1"); //$NON-NLS-1$
		assertNull(handler.getInitParam("test")); //$NON-NLS-1$
		Map<String, String> params = handler.getInitParams();
		assertTrue(params.size() == 0);

		assertEquals(handler.getHTMLForward("forward1").getPath(), "path1"); //$NON-NLS-1$ //$NON-NLS-2$

		// handler2
		handler = eventHandlers.get("event-handler2"); //$NON-NLS-1$
		assertEquals(handler.getType(), "com.frame2.eventHandler2"); //$NON-NLS-1$
		assertNull(handler.getInitParam("test")); //$NON-NLS-1$
		params = handler.getInitParams();
		assertTrue(params.size() == 0);
		assertEquals(handler.getHTMLForward("forward1").getPath(), "path1"); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(handler.getHTMLForward("forward2").getPath(), "path2"); //$NON-NLS-1$ //$NON-NLS-2$
		assertNull(handler.getHTMLForward("test1")); //$NON-NLS-1$

		// handler3
		handler = eventHandlers.get("event-handler3"); //$NON-NLS-1$
		assertEquals(handler.getType(), "com.frame2.eventHandler3"); //$NON-NLS-1$
		assertNull(handler.getInitParam("test")); //$NON-NLS-1$
		params = handler.getInitParams();
		assertTrue(params.size() == 2);
		assertEquals(handler.getInitParam("param1"), "val1"); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(handler.getInitParam("param2"), "val2"); //$NON-NLS-1$ //$NON-NLS-2$
		assertNull(handler.getHTMLForward("forward1")); //$NON-NLS-1$
	}
	/**
	 * Method testEmptyTags.
	 * @throws Exception
	 */
	public void testPluginSingle() {
		EventConfigReader reader = getReader("org/megatome/frame2/front/config/pluginTag-Single-1_1.xml"); //$NON-NLS-1$

		List<PluginDef> plugins = reader.getPlugins();
		assertTrue(plugins.size() == 1);
		Iterator<PluginDef> iter = plugins.iterator();
		while (iter.hasNext()) {
			PluginDef plugin = iter.next();
			assertTrue(plugin.getName().equals("PluginName")); //$NON-NLS-1$
			assertTrue(plugin.getType().equals("org.megatome.something")); //$NON-NLS-1$
		}
	}
	public void testPluginMultiple() {
		EventConfigReader reader = getReader("org/megatome/frame2/front/config/pluginTag-Mult-Param-1_1.xml"); //$NON-NLS-1$

		List<PluginDef> plugins = reader.getPlugins();
		assertTrue(plugins.size() == 3);
		PluginDef plugin = plugins.get(0);
		assertTrue(plugin.getName().equals("PluginName1")); //$NON-NLS-1$
		assertTrue(plugin.getType().equals("org.megatome.something1")); //$NON-NLS-1$
		Map<String, String> params = plugin.getInitParams();
		assertNotNull(params);
		String val = params.get("param1"); //$NON-NLS-1$
		assertTrue(val.equals("value1")); //$NON-NLS-1$

		plugin = plugins.get(1);
		assertTrue(plugin.getName().equals("PluginName2")); //$NON-NLS-1$
		assertTrue(plugin.getType().equals("org.megatome.something")); //$NON-NLS-1$
		params = plugin.getInitParams();
		assertNotNull(params);
		val = params.get("param1"); //$NON-NLS-1$
		assertTrue(val.equals("value1")); //$NON-NLS-1$
		val = params.get("param2"); //$NON-NLS-1$
		assertTrue(val.equals("value2")); //$NON-NLS-1$

		plugin = plugins.get(2);
		assertTrue(plugin.getName().equals("PluginName3")); //$NON-NLS-1$
		assertTrue(plugin.getType().equals("org.megatome.something")); //$NON-NLS-1$
		params = plugin.getInitParams();
		assertNotNull(params);
		assertTrue(params.isEmpty());
	}

	public void testPluginMultipleDuplicate() {
		EventConfigReader reader = getReader("org/megatome/frame2/front/config/pluginTag-Dup-Negative-Param-1_1.xml"); //$NON-NLS-1$

		List<PluginDef> plugins = reader.getPlugins();
		Iterator<PluginDef> iter = plugins.iterator();
		assertTrue(plugins.size() == 1);
		while (iter.hasNext()) {
			PluginDef plugin = iter.next();
			assertTrue(plugin.getName().equals("PluginName1")); //$NON-NLS-1$
			assertTrue(plugin.getType().equals("org.megatome.something1")); //$NON-NLS-1$
			Map<String, String> params = plugin.getInitParams();
			assertNotNull(params);
			String val = params.get("param1"); //$NON-NLS-1$
			assertTrue(val.equals("value1")); //$NON-NLS-1$
		}
	}

	public void testNegativeDuplicateInitParam() {
		String filename =
			"org/megatome/frame2/front/config/pluginTag-Single-Negative-DupParam-1_1.xml"; //$NON-NLS-1$

		EventConfigReader reader = new EventConfigReader(filename);
		try {
			reader.execute();
			fail();
		} catch (Frame2Exception expected) {
			// expected
		}
	}

	public void testReqProcEmpty() {
		EventConfigReader reader = getReader("org/megatome/frame2/front/config/requestProc-Empty-1_1.xml"); //$NON-NLS-1$

		RequestProcessorDef httpRequestProcessor =
			reader.getHttpReqProcHandler();
		assertNull(httpRequestProcessor);
		RequestProcessorDef soapRequestProcessor =
			reader.getSoapReqProcHandler();
		assertNull(soapRequestProcessor);

	}

	public void testTwoProcEmpty() {
		EventConfigReader reader = getReader("org/megatome/frame2/front/config/requestProc-SoapAndHttp-1_1.xml"); //$NON-NLS-1$

		RequestProcessorDef httpRequestProcessor =
			reader.getHttpReqProcHandler();
		assertEquals(httpRequestProcessor.getType(), "httpBaby"); //$NON-NLS-1$
		RequestProcessorDef soapRequestProcessor =
			reader.getSoapReqProcHandler();
		assertEquals(soapRequestProcessor.getType(), "soap"); //$NON-NLS-1$

	}
	
	private EventConfigReader getReader(final String fileName) {
		EventConfigReader reader = new EventConfigReader(fileName);
		try {
			reader.execute();
		} catch (Frame2Exception e) {
			fail("Unexpected Exception: " + e.getMessage()); //$NON-NLS-1$
		}
		
		return reader;
	}

}
