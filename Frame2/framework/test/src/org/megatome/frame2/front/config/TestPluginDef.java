package org.megatome.frame2.front.config;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.megatome.frame2.front.config.PluginDef;

/**
 * @author cjohnston
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TestPluginDef extends TestCase {
   
   private PluginDef _pluginDef;
   
   public TestPluginDef() {
      super();
   }

   public TestPluginDef(String name) {
      super(name);
   }

   /**
    * @see junit.framework.TestCase#setUp()
    */
   protected void setUp() throws Exception {
      super.setUp();
      _pluginDef = new PluginDef();
   }

   /**
    * @see junit.framework.TestCase#tearDown()
    */
   protected void tearDown() throws Exception {
      super.tearDown();
   }
   
   public void testPluginDef()
   {
      _pluginDef.setName("pluginName");
      _pluginDef.setType("org.megatome.something");
      
      assertEquals("pluginName", _pluginDef.getName());
      assertEquals("org.megatome.something", _pluginDef.getType());
   }
   
   public void testPluginDefParams()
   {
      HashMap fakeParams = new HashMap();
      fakeParams.put("param1", "value1");
      
      _pluginDef.setInitParams(fakeParams);
      
      testPluginDef();
      Map returnedParams = _pluginDef.getInitParams();
      assertTrue(returnedParams.containsKey("param1"));
   }

}
