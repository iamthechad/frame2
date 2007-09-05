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
import static org.junit.Assert.fail;

import javax.servlet.ServletException;

import org.junit.Test;
import org.megatome.frame2.Globals;

import servletunit.HttpServletRequestSimulator;
import servletunit.HttpServletResponseSimulator;
import servletunit.frame2.MockFrame2TestCase;

/**
 * This test is for the case when CommonsValidator jars are not in
 * the classpath. If the plugin is not specified, this should not be an
 * error.
 * 
 * To successfully run these tests, you must remove commons-validator.jar from
 * the classpath.
 */
public class TestMissingCommonsValidation extends MockFrame2TestCase {

	private HttpServletRequestSimulator request;
	private HttpServletResponseSimulator response;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		this.request = (HttpServletRequestSimulator)getRequest();
		this.response = (HttpServletResponseSimulator)getResponse();
	}

	private void postHttpReqProc() {
		HttpFrontController servlet = initializeServlet();

		try {
			servlet.doPost(this.request, this.response);
		} catch (Exception e) {
		    String msg = "Unexpected Exception: " + e.getMessage(); //$NON-NLS-1$
		    if (e.getCause() != null) {
		        msg += ": " + e.getCause().getMessage(); //$NON-NLS-1$
		    }
			fail(msg);
		}
	}
	
	private void postNegativeHttpReqProc() {
		HttpFrontController servlet = initializeServlet();

		try {
			servlet.doPost(this.request, this.response);
			fail("Failed to catch expected exception"); //$NON-NLS-1$
		} catch (Exception expected) {
		   //Expected
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

		this.request.setServletPath("http://localhost/validateEvent.f2"); //$NON-NLS-1$

		return servlet;
	}
	
	@Test
	public void testPrerequisites() {
	   try {
          Class.forName("org.apache.commons.validator.ValidatorResources"); //$NON-NLS-1$
          fail("Did not catch expected ClassDef exception"); //$NON-NLS-1$
	   } catch (ClassNotFoundException expected) {
	      // This is the expected result
	   } catch (Exception e) {
	      fail("Unexpected Exception"); //$NON-NLS-1$
	   }
	}

	@Test
	public void testInvokeValidateWithPlugin() {

		sendContextInitializedEvent(
			Globals.CONFIG_FILE,
			"/org/megatome/frame2/front/test-validator-config.xml"); //$NON-NLS-1$

		postNegativeHttpReqProc();
	}
	
	@Test
	public void testInvokeValidateWithoutPlugin() {

		sendContextInitializedEvent(
			Globals.CONFIG_FILE,
			"/org/megatome/frame2/front/test-missing-validator-config.xml"); //$NON-NLS-1$

		postHttpReqProc();
	}
}
