package org.megatome.frame2.template.config;

import java.util.HashMap;

import junit.framework.TestCase;

import org.megatome.frame2.template.config.TemplateConfiguration;
import org.megatome.frame2.template.config.TemplateConfigurationInterface;
import org.megatome.frame2.template.config.TemplateDef;


public class TestTemplateConfiguration extends TestCase {

   TemplateConfigurationInterface _config;
   
   public TestTemplateConfiguration() {
      super();
   }


   public TestTemplateConfiguration(String name) {
      super(name);
   }

   /**
    * @see junit.framework.TestCase#setUp()
    */
   protected void setUp() throws Exception {
      super.setUp();
      _config = new TemplateConfiguration();
   }

   /**
    * @see junit.framework.TestCase#tearDown()
    */
   protected void tearDown() throws Exception {
      super.tearDown();
   }
   
   public void testSingleDefNoPut(){
      TemplateDef def1 = new TemplateDef();
      def1.setName("def1");
      def1.setPath("path1");
      HashMap map = new HashMap();
      map.put("def1",def1);
      _config.setDefinitions(map);
      
      assertNotNull(_config.getDefinition("def1"));
      TemplateDef retDef = _config.getDefinition("def1");
      assertTrue(retDef.getName().equals("def1"));
      assertTrue(retDef.getPath().equals("path1"));
      assertTrue(retDef.getPutParams().isEmpty());
   }
   
   public void testSingleDefWithPut(){
      TemplateDef def1 = new TemplateDef();
      def1.setName("def1");
      def1.setPath("path1");
      HashMap puts = new HashMap();
      puts.put("put1","val1");
      def1.setPutParams(puts);
      HashMap map = new HashMap();
      map.put("def1",def1);
      _config.setDefinitions(map);
      
      assertNotNull(_config.getDefinition("def1"));
      TemplateDef retDef = _config.getDefinition("def1");
      assertTrue(retDef.getName().equals("def1"));
      assertTrue(retDef.getPath().equals("path1"));
      assertTrue(retDef.getPutParams().size() == 1);
   }
   
   public void testMultDef(){
      HashMap map = new HashMap();
      TemplateDef def1 = new TemplateDef();
      def1.setName("def1");
      def1.setPath("path1");
        
      TemplateDef def2 = new TemplateDef();
      def2.setName("def2");
      def2.setPath("path2");
         
      map.put("def1",def1);
      map.put("def2",def2);
      _config.setDefinitions(map);      
      
      assertNotNull(_config.getDefinition("def1"));
      TemplateDef retDef = _config.getDefinition("def1");
      assertTrue(retDef.getName().equals("def1"));
      assertTrue(retDef.getPath().equals("path1"));
      assertTrue(retDef.getPutParams().isEmpty());
      
      assertNotNull(_config.getDefinition("def2"));
      TemplateDef retDef2 = _config.getDefinition("def2");
      assertTrue(retDef2.getName().equals("def2"));
      assertTrue(retDef2.getPath().equals("path2"));
      assertTrue(retDef2.getPutParams().isEmpty());
   }
   
   public void testNullOnUndefinedDef(){
      assertNull(_config.getDefinition("def1"));
   }

}
