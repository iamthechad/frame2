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

import java.util.Enumeration;
import java.util.ResourceBundle;

import org.megatome.frame2.Globals;
import org.megatome.frame2.log.LoggerFactory;
import org.megatome.frame2.log.impl.Log4jLogger;
import org.megatome.frame2.util.ResourceLocator;
import org.megatome.frame2.util.TestResourceLocator;

import servletunit.frame2.MockFrame2TestCase;

/**
 * TestFrame2ContextListener.java
 */
public class TestFrame2ContextListener extends MockFrame2TestCase {

	/**
	 * Constructor for TestConfiguration.
	 * 
	 * @param name
	 */
	public TestFrame2ContextListener(String name) {
		super(name);
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() {
		ResourceLocator.setBasename(null);
	}

	public void testSetConfigFile() {
		String path = "/org/megatome/frame2/front/test-config.xml"; //$NON-NLS-1$

		assertEquals(Globals.DEFAULT_CONFIG_FILE, ConfigFactory
				.getConfigFilePath());

		sendContextInitializedEvent(Globals.CONFIG_FILE, path);

		assertEquals(path, ConfigFactory.getConfigFilePath());

		try {
			assertNotNull(ConfigFactory.instance());
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage()); //$NON-NLS-1$
		}
	}

	public void testSetConfigFile_Invalid() {
		sendContextInitializedEvent(Globals.CONFIG_FILE, "foo"); //$NON-NLS-1$

		assertEquals("foo", ConfigFactory.getConfigFilePath()); //$NON-NLS-1$

		try {
			ConfigFactory.instance();
			fail();
		} catch (ConfigException expected) {
			// expected
		}
	}

	public void testSetConfigFile_Negative_Plugin() {
		sendContextInitializedEvent(Globals.CONFIG_FILE,
				"/org/megatome/frame2/front/config/pluginTag-Single-Param-1_1.xml"); //$NON-NLS-1$

		try {
			ConfigFactory.instance();
			fail();
		} catch (ConfigException expected) {
			// expected
		}
	}

	public void testSetLoggerType() {
		// assertTrue(LoggerFactory.instance("foo") instanceof StandardLogger);

		sendContextInitializedEvent(Globals.LOGGER_TYPE,
				"org.megatome.frame2.log.impl.Log4jLogger"); //$NON-NLS-1$

		assertTrue(LoggerFactory.instance("bar") instanceof Log4jLogger); //$NON-NLS-1$
	}

	public void testSetLoggerType_Invalid() {

		try {
			sendContextInitializedEvent(Globals.LOGGER_TYPE,
					"org.megatome.frame2.log.impl.BogusLogger"); //$NON-NLS-1$
			fail();
		} catch (RuntimeException expected) {
			// expected
		}
	}

	public void testSetResourceBundle_NoFlag() {
		assertEmptyBundle(ResourceLocator.getBundle());

		sendContextInitializedEvent(null, null);

		assertEmptyBundle(ResourceLocator.getBundle());
	}

	public void testSetResourceBundle_Frame2Flag() {
		assertEmptyBundle(ResourceLocator.getBundle());

		sendContextInitializedEvent(Globals.RESOURCE_BUNDLE, "frame2-resource"); //$NON-NLS-1$

		ResourceBundle bundle = ResourceLocator.getBundle();

		TestResourceLocator.assertHasHasNot(bundle,
				"tag.question", "alt.tag.question"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void testSetResourceBundle_JstlFmtFlag() {
		assertEmptyBundle(ResourceLocator.getBundle());

		sendContextInitializedEvent(
				Frame2ContextListener.JSTL_CONTEXT_BUNDLE_PARAM, "alt-resource"); //$NON-NLS-1$

		ResourceBundle bundle = ResourceLocator.getBundle();

		TestResourceLocator.assertHasHasNot(bundle,
				"alt.tag.question", "tag.question"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void testSetResourceBundle_Frame2TakesPrecedence() {
		assertEmptyBundle(ResourceLocator.getBundle());

		sendContextInitializedEvent(
				Globals.RESOURCE_BUNDLE,
				"frame2-resource", Frame2ContextListener.JSTL_CONTEXT_BUNDLE_PARAM, "alt-resource"); //$NON-NLS-1$ //$NON-NLS-2$

		ResourceBundle bundle = ResourceLocator.getBundle();

		TestResourceLocator.assertHasHasNot(bundle,
				"tag.question", "alt.tag.question"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private void assertEmptyBundle(ResourceBundle bundle) {
		assertNotNull(bundle);
		Enumeration<String> keys = bundle.getKeys();
		assertFalse(keys.hasMoreElements());
	}
}
