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

import servletunit.frame2.MockFrame2TestCase;

/**
 * 
 */
public class TestRoleBasedSecurity extends MockFrame2TestCase {

   private Configuration config;

	public TestRoleBasedSecurity(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
      this.config = new Configuration("org/megatome/frame2/front/test-security-config.xml"); //$NON-NLS-1$
	}

   public void testSecurity_FailsNone() throws Throwable {
      assertFails(null);
   }

   public void testSecurity_FailsOther() throws Throwable {
      assertFails("bogus"); //$NON-NLS-1$
   }

   public void testSecurity_PassesAdmin() throws Throwable {
      assertPasses("/event1.sec","admin","/view1.jsp"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
   }

   public void testSecurity_PassesGuest() throws Throwable {
		assertPasses("/event1.sec","guest","/view1.jsp"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
   }
   
   public void testNoAuthorizationRequired() throws Throwable {
      assertPasses("/event2.sec","guest","/view1.jsp"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      assertPasses("/event2.sec",null,"/view1.jsp"); //$NON-NLS-1$ //$NON-NLS-2$
      assertPasses("/event2.sec","bogus","/view1.jsp"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
   }

	private void assertPasses(String path,String role,String view) throws Throwable {
		setServletPath(path);
		
		setUserRole(role);
      setRemoteUser("fred"); //$NON-NLS-1$
		
		HttpRequestProcessor request = (HttpRequestProcessor) RequestProcessorFactory.instance(this.config,getContext(),getRequest(),getResponse());
		
		request.processRequest();
		
		verifyForwardPath(view);
	}


   private void assertFails(String role) throws Throwable {
      setServletPath("/event1.sec"); //$NON-NLS-1$

      if ( role != null ) {
         setUserRole(role);
      }

      setRemoteUser("fred"); //$NON-NLS-1$

      HttpRequestProcessor request = (HttpRequestProcessor) RequestProcessorFactory.instance(this.config,getContext(),getRequest(),getResponse());
      
      try {
         request.processRequest();
         fail();
      } catch ( AuthorizationException expected ) {
    	  // expected
      }
   }
}
