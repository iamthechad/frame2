package org.megatome.frame2.front.config;

import junit.framework.TestCase;

import org.megatome.frame2.front.config.RequestProcessorDef;

/**
 * @author cjohnston
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TestRequestProcessorDef extends TestCase {

   /**
    * 
    */
   public TestRequestProcessorDef() {
      super();
   }

   /**
    * @param name
    */
   public TestRequestProcessorDef(String name) {
      super(name);
   }
   
   public void testRequestProcessorDef() {
      RequestProcessorDef def = new RequestProcessorDef();
      
      def.setType("org.megatome.something");
      
      assertEquals("org.megatome.something", def.getType());
   }

}
