package org.megatome.frame2.template;

import java.util.HashMap;
import java.util.Map;

import org.megatome.frame2.plugin.PluginException;
import org.megatome.frame2.template.TemplateConfigFactory;
import org.megatome.frame2.template.TemplateException;
import org.megatome.frame2.template.TemplatePlugin;
import org.megatome.frame2.template.config.TemplateConfiguration;
import org.megatome.frame2.template.config.TemplateDef;

import servletunit.frame2.MockFrame2ServletContextSimulator;
import servletunit.frame2.MockFrame2TestCase;

public class TestTemplatePluginLifecycle extends MockFrame2TestCase {

   private MockFrame2ServletContextSimulator _context;
   private TemplatePlugin _plugin;

   /**
    * Constructor for TestHttpFrontController.
    *
    * @param name
    */
   public TestTemplatePluginLifecycle(String name) {
      super(name);
   }

   /**
    * @see junit.framework.TestCase#setUp()
    */
   protected void setUp() throws Exception {
      super.setUp();
      _context = (MockFrame2ServletContextSimulator) getContext();
      _plugin = new TemplatePlugin();
   }

   /**
    * @see junit.framework.TestCase#tearDown()
    */
   protected void tearDown() throws Exception {
      super.tearDown();
   }

   public void testTemplatePluginSingleLoadDefaultDir() {
      TemplateConfiguration config = testTemplatePlugin(null);

      TemplateDef def = config.getDefinition("foo");
      assertNotNull(def);
      assertTrue(def.getPath().equals("xxx.jsp"));
      assertTrue(def.getPutParams().isEmpty());

      def = config.getDefinition("bar");
      assertNotNull(def);
      assertTrue(def.getPath().equals("xxx.jsp"));
      Map params = def.getPutParams();
      assertTrue(params.size() == 1);
      assertNotNull(params.get("bar-put"));
      assertTrue(((String) params.get("bar-put")).equals("yyy.jsp"));

      def = config.getDefinition("baz");
      assertNotNull(def);
      assertTrue(def.getPath().equals("xxx.jsp"));
      assertTrue(def.getPutParams().isEmpty());
   }

   public void testTemplatePluginSingleLoad() {
      TemplateConfiguration config =
         testTemplatePlugin("/templates/good/single/");

      TemplateDef def = config.getDefinition("foo");
      assertNotNull(def);
      assertTrue(def.getPath().equals("xxx.jsp"));

      Map params = def.getPutParams();
      assertTrue(params.size() == 1);
      assertNotNull(params.get("yyy"));
      assertTrue(((String) params.get("yyy")).equals("yyy.jsp"));
   }

   public void testTemplatePluginDestroy() {
      testTemplatePluginSingleLoad();
      try {
         _plugin.destroy(_context, new HashMap());
      } catch (PluginException e) {
         fail();
      }

      try {
         TemplateConfigFactory.instance();
      } catch (TemplateException e) {
         return;
      }
      fail();
   }

   public void testNegativeTemplatePluginSingleLoad() {
      testNegativeTemplatePlugin("/templates/bad/single/");
   }

   public void testNegativeTemplatePluginTemplatePath() {
      String dir = "/templates/bad/validateTemplatePaths/";
      String exceptionMsg = testNegativeTemplatePlugin(dir);
      assertEquals(
         TemplatePlugin.PLUGIN_INIT_ERROR +
         TemplateConfiguration.TEMPLATE_PATH_EXCEPTION_MSG + dir + "badpath",
         exceptionMsg);
   }
  
   public void testNegativeTemplatePluginPutPath() {
      String dir = "/templates/bad/validateTemplatePutPaths/";
      String exceptionMsg = testNegativeTemplatePlugin(dir);
      assertEquals(
         TemplatePlugin.PLUGIN_INIT_ERROR +
         TemplateConfiguration.TEMPLATE_PUT_PATH_EXCEPTION_MSG + dir + "badpath",
         exceptionMsg);
   }
 

   public String testNegativeTemplatePlugin(String dir) {
      _plugin.setConfigDir(dir);
      try {
         _plugin.init(_context, new HashMap());
      } catch (PluginException e) {
         return e.getMessage();
      }
      fail();
      return null;
   }

   private TemplateConfiguration testTemplatePlugin(String dir) {

      if (dir != null) {
         _plugin.setConfigDir(dir);
      }

      try {
         _plugin.init(_context, new HashMap());
      } catch (PluginException e) {
         e.printStackTrace();
         fail();
      }

      TemplateConfiguration config = null;
      try {
         config = TemplateConfigFactory.instance();
      } catch (TemplateException e1) {
         fail();
      }
      return config;
   }

}
