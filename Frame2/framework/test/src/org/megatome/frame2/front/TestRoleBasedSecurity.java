package org.megatome.frame2.front;

import servletunit.frame2.MockFrame2TestCase;

/**
 * 
 */
public class TestRoleBasedSecurity extends MockFrame2TestCase {

   private Configuration _config;

	public TestRoleBasedSecurity(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
      _config = new Configuration("org/megatome/frame2/front/test-security-config.xml");
	}

   public void testSecurity_FailsNone() throws Throwable {
      assertFails(null);
   }

   public void testSecurity_FailsOther() throws Throwable {
      assertFails("bogus");
   }

   public void testSecurity_PassesAdmin() throws Throwable {
      assertPasses("/event1.sec","admin","/view1.jsp");
   }

   public void testSecurity_PassesGuest() throws Throwable {
		assertPasses("/event1.sec","guest","/view1.jsp");
   }
   
   public void testNoAuthorizationRequired() throws Throwable {
      assertPasses("/event2.sec","guest","/view1.jsp");
      assertPasses("/event2.sec",null,"/view1.jsp");
      assertPasses("/event2.sec","bogus","/view1.jsp");
   }

	private void assertPasses(String path,String role,String view) throws Throwable {
		setServletPath(path);
		
		setUserRole(role);
      setRemoteUser("fred");
		
		HttpRequestProcessor request = (HttpRequestProcessor) RequestProcessorFactory.instance(_config,getContext(),getRequest(),getResponse());
		
		request.processRequest();
		
		verifyForwardPath(view);
	}


   private void assertFails(String role) throws Throwable {
      setServletPath("/event1.sec");

      if ( role != null ) {
         setUserRole(role);
      }

      setRemoteUser("fred");

      HttpRequestProcessor request = (HttpRequestProcessor) RequestProcessorFactory.instance(_config,getContext(),getRequest(),getResponse());
      
      try {
         request.processRequest();
         fail();
      } catch ( AuthorizationException e ) {
      }
   }
}
