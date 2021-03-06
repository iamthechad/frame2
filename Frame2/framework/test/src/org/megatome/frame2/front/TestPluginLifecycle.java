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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.megatome.frame2.Globals;
import org.megatome.frame2.plugin.MockPluginInterface;
import org.megatome.frame2.plugin.PluginInterface;

import servletunit.frame2.MockFrame2TestCase;

public class TestPluginLifecycle extends MockFrame2TestCase {

	/*@Override
	protected void setUp() throws Exception {
		super.setUp();
	}*/

	private PluginProxy initAndGetProxy(final String pluginName)
			throws Exception {
		sendContextInitializedEvent(Globals.CONFIG_FILE,
				"/org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$

		getServlet().init();
		Configuration config = getServlet().getConfiguration();
		assertNotNull(config);

		PluginProxy proxy = config.getPluginProxy(pluginName);
		assertNotNull(proxy);

		return proxy;
	}

	@Test
	public void testPluginInit() throws Exception {
		PluginProxy proxy = initAndGetProxy("mockPlugin"); //$NON-NLS-1$

		PluginInterface plugin = proxy.getPlugin();
		assertTrue(((MockPluginInterface) plugin).getState() == MockPluginInterface.STATE_INIT);
	}

	@Test
	public void testNegativePluginInitThrows() throws Exception {
		PluginProxy proxy = initAndGetProxy("mockPluginThrows"); //$NON-NLS-1$

		assertTrue(proxy.initThrewException());
	}

	@Test
	public void testPluginDestroy() throws Exception {
		PluginProxy proxy = initAndGetProxy("mockPlugin"); //$NON-NLS-1$

		PluginInterface plugin = proxy.getPlugin();
		assertTrue(((MockPluginInterface) plugin).getState() == MockPluginInterface.STATE_INIT);

		sendContextDestroyedEvent();
		assertTrue(((MockPluginInterface) plugin).getState() == MockPluginInterface.STATE_DESTROY);
	}

	@Test
	public void testNegativePluginDestroyNotCalled() throws Exception {
		PluginProxy proxy = initAndGetProxy("mockPluginThrows"); //$NON-NLS-1$

		assertTrue(proxy.initThrewException());
		sendContextDestroyedEvent();
		PluginInterface plugin = proxy.getPlugin();
		assertTrue(((MockPluginInterface) plugin).getState() == MockPluginInterface.STATE_NONE);
	}

	@Test
	public void testNegativePluginDestroyThrows() throws Exception {
		PluginProxy proxy = initAndGetProxy("mockPluginDestroyThrows"); //$NON-NLS-1$

		sendContextDestroyedEvent();
		PluginInterface plugin = proxy.getPlugin();
		assertTrue(((MockPluginInterface) plugin).getState() == MockPluginInterface.STATE_THROW);
		// should visually inspect that log message emitted.
	}
}
