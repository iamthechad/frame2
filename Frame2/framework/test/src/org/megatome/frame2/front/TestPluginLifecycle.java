package org.megatome.frame2.front;

import org.megatome.frame2.Globals;
import org.megatome.frame2.plugin.MockPluginInterface;
import org.megatome.frame2.plugin.PluginInterface;

import servletunit.ServletContextSimulator;
import servletunit.frame2.MockFrame2TestCase;


public class TestPluginLifecycle extends MockFrame2TestCase {

   private ServletContextSimulator _context;

   /**
    * Constructor for TestHttpFrontController.
    *
    * @param name
    */
   public TestPluginLifecycle(String name) {
      super(name);
   }

   /**
    * @see junit.framework.TestCase#setUp()
    */
   protected void setUp() throws Exception {
      super.setUp();
      _context = (ServletContextSimulator) getContext();
   }

   /**
    * @see junit.framework.TestCase#tearDown()
    */
   protected void tearDown() throws Exception {
      super.tearDown();
   }

   private PluginProxy initAndGetProxy(final String pluginName) throws Exception {
      sendContextInitializedEvent(Globals.CONFIG_FILE,"/org/megatome/frame2/front/test-config.xml");

      getServlet().init();
      Configuration config = getServlet().getConfiguration();
      assertNotNull(config);

      PluginProxy proxy = config.getPluginProxy(pluginName);      
      assertNotNull(proxy);
      
      return proxy;
   }

   public void testPluginInit() throws Exception {
       PluginProxy proxy = initAndGetProxy("mockPlugin");
            
      PluginInterface plugin = proxy.getPlugin();
      assertTrue(((MockPluginInterface)plugin).getState() == MockPluginInterface.STATE_INIT);
   }
   
   public void testNegativePluginInitThrows() throws Exception {
       PluginProxy proxy = initAndGetProxy("mockPluginThrows");
      
      assertTrue(proxy.initThrewException());                      
   }
   
   public void testPluginDestroy() throws Exception {
       PluginProxy proxy = initAndGetProxy("mockPlugin");
            
      PluginInterface plugin = proxy.getPlugin();
      assertTrue(((MockPluginInterface)plugin).getState() == MockPluginInterface.STATE_INIT);
      
      sendContextDestroyedEvent();
      assertTrue(((MockPluginInterface)plugin).getState() == MockPluginInterface.STATE_DESTROY);
   }
   
   public void testNegativePluginDestroyNotCalled() throws Exception {
       PluginProxy proxy = initAndGetProxy("mockPluginThrows");
      
      assertTrue(proxy.initThrewException());
      sendContextDestroyedEvent();
      PluginInterface plugin = proxy.getPlugin();
      assertTrue(((MockPluginInterface)plugin).getState() == MockPluginInterface.STATE_NONE);                      
   }
   
   public void testNegativePluginDestroyThrows() throws Exception {
       PluginProxy proxy = initAndGetProxy("mockPluginDestroyThrows");

       sendContextDestroyedEvent();
      PluginInterface plugin = proxy.getPlugin();
      assertTrue(((MockPluginInterface)plugin).getState() == MockPluginInterface.STATE_THROW);  
      // should visually inspect that log message emitted.    
   }
}
