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



   public void testPluginInit() throws Exception {
      sendContextInitializedEvent(Globals.CONFIG_FILE,"/org/megatome/frame2/front/test-config.xml");

      getServlet().init();
      Configuration config = getServlet().getConfiguration();
      assertNotNull(config);

      PluginProxy proxy = config.getPluginProxy("mockPlugin");      
      assertNotNull(proxy);
            
      PluginInterface plugin = proxy.getPlugin();
      assertTrue(((MockPluginInterface)plugin).getStartIndex() == 10);
   }
   public void testNegativePluginInitThrows() throws Exception {
      sendContextInitializedEvent(Globals.CONFIG_FILE,"/org/megatome/frame2/front/test-config.xml");

      getServlet().init();
      Configuration config = getServlet().getConfiguration();
      assertNotNull(config);

      PluginProxy proxy = config.getPluginProxy("mockPluginThrows");      
      assertNotNull(proxy);
      
      assertTrue(proxy.initThrewException());                      
   }
   
   public void testPluginDestroy() throws Exception {
      sendContextInitializedEvent(Globals.CONFIG_FILE,"/org/megatome/frame2/front/test-config.xml");

      getServlet().init();
      Configuration config = getServlet().getConfiguration();
      assertNotNull(config);

      PluginProxy proxy = config.getPluginProxy("mockPlugin");      
      assertNotNull(proxy);
            
      PluginInterface plugin = proxy.getPlugin();
      assertTrue(((MockPluginInterface)plugin).getStartIndex() == 10);
      
      sendContextDestroyedEvent();
      assertTrue(((MockPluginInterface)plugin).getStartIndex() == 20);
      
   }
   
   public void testNegativePluginDestroyNotCalled() throws Exception {
      sendContextInitializedEvent(Globals.CONFIG_FILE,"/org/megatome/frame2/front/test-config.xml");

      getServlet().init();
      Configuration config = getServlet().getConfiguration();
      assertNotNull(config);

      PluginProxy proxy = config.getPluginProxy("mockPluginThrows");      
      assertNotNull(proxy);
      
      assertTrue(proxy.initThrewException());
      sendContextDestroyedEvent();
      PluginInterface plugin = proxy.getPlugin();
      assertTrue(((MockPluginInterface)plugin).getStartIndex() == -1);                      
   }
   
   public void testNegativePluginDestroyThrows() throws Exception {
      sendContextInitializedEvent(Globals.CONFIG_FILE,"/org/megatome/frame2/front/test-config.xml");

      getServlet().init();
      Configuration config = getServlet().getConfiguration();
      assertNotNull(config);

      PluginProxy proxy = config.getPluginProxy("mockPluginDestroyThrows");      
      assertNotNull(proxy);
      
      sendContextDestroyedEvent();
      PluginInterface plugin = proxy.getPlugin();
      assertTrue(((MockPluginInterface)plugin).getStartIndex() == 999);  
      // should visually inspect that log message emitted.    
   }
}
