package org.megatome.frame2.front;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.megatome.frame2.front.config.PluginDef;
import org.megatome.frame2.plugin.MockPluginInterface;
import org.megatome.frame2.plugin.PluginInterface;

/**
 * @author cjohnston
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TestPluginProxy extends TestCase {

   PluginProxy proxy = null;
   /**
    * Constructor for PluginProxyTest.
    * @param name
    */
   public TestPluginProxy(String name) {
      super(name);
   }

   /*
    * @see TestCase#setUp()
    */
   protected void setUp() throws Exception {
      super.setUp();
      PluginDef pluginDef = new PluginDef();
      pluginDef.setName("PluginName");
      pluginDef.setType("PluginType");
      HashMap testParams = new HashMap();
      testParams.put("param1", "value1");
      pluginDef.setInitParams(testParams);
      MockPluginInterface mockPlugin = new MockPluginInterface(100);
      
      proxy = new PluginProxy(pluginDef, mockPlugin);
   }

   /*
    * @see TestCase#tearDown()
    */
   protected void tearDown() throws Exception {
      super.tearDown();
   }

   public void testGetName() {
      assertTrue(proxy.getName().equals("PluginName"));
   }

   public void testGetType() {
      assertTrue(proxy.getType().equals("PluginType"));
   }

   public void testGetInitParams() {
      Map params = proxy.getInitParams();
      assertTrue(params.size() == 1);
      assertTrue(((String)params.get("param1")).equals("value1"));
   }

   public void testGetPlugin() {
      PluginInterface plugin = proxy.getPlugin();
      assertNotNull(plugin);
      assertTrue(plugin instanceof MockPluginInterface);
   }
   
   public void testInitThrewException() {
      assertFalse(proxy.initThrewException());
      proxy.setInitThrewException(true);
      assertTrue(proxy.initThrewException());
   }

}
