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

import org.megatome.frame2.Globals;
import org.megatome.frame2.util.Helper;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import servletunit.frame2.MockFrame2TestCase;

public class TestSoapFrontController extends MockFrame2TestCase {

	Element[] elements;

	/**
	 * Constructor for TestSoapFrontController.
	 * 
	 * @param name
	 */
	public TestSoapFrontController(String name) {
		super(name);
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.elements = createPOEventXML();
	}

	private Element[] createPOEventXML() {
		Element[] el = null;
		try {
			el = Helper.loadEvents("org/megatome/frame2/jaxb/po.xml", //$NON-NLS-1$
					getClass());
		} catch (Exception e) {
			fail();
		}
		return el;
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testInstantiateSoapRequestProcessor() throws Exception {
		sendContextInitializedEvent(Globals.CONFIG_FILE,
				"/org/megatome/frame2/front/test-wsconfig.xml"); //$NON-NLS-1$
		processSoapReqProc(true);
	}

	public void testNegativeInstantiateSoapRequestProcessor() throws Exception {
		sendContextInitializedEvent(Globals.CONFIG_FILE,
				"/org/megatome/frame2/front/test-negative-request-processor.xml"); //$NON-NLS-1$
		processSoapReqProc(false);
	}

	private void processSoapReqProc(boolean shouldSucceed) {
		SoapFrontControllerImpl controller = new SoapFrontControllerImpl();
		checkConfig();
		Element[] retVal = controller.processEvent(this.elements);
		assertNotNull(retVal);
		assertTrue(retVal.length == 1);
		assertTrue(shouldSucceed != isFaultMessage(retVal[0]));
	}

	private boolean isFaultMessage(Element el) {
		NodeList nl = el.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);
			if ("faultstring".equalsIgnoreCase(n.getNodeName())) { //$NON-NLS-1$
				return true;
			}
		}
		
		return false;
	}

	private void checkConfig() {
		try {
			ConfigFactory.instance();
		} catch (ConfigException e) {
			fail();
		}
	}

}
