package org.megatome.frame2.template.config;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.megatome.frame2.template.config.TemplateDef;


public class TestTemplateDef extends TestCase {
   
   private TemplateDef _templateDef;
   
   public TestTemplateDef() {
      super();
   }

   public TestTemplateDef(String name) {
      super(name);
   }

   /**
    * @see junit.framework.TestCase#setUp()
    */
   protected void setUp() throws Exception {
      super.setUp();
      _templateDef = new TemplateDef();
   }

   /**
    * @see junit.framework.TestCase#tearDown()
    */
   protected void tearDown() throws Exception {
      super.tearDown();
   }
   
   public void testTemplateDef()
   {
      _templateDef.setName("name");
      _templateDef.setPath("/WEB-INF/jsp/my.jsp");
      
      assertEquals("name", _templateDef.getName());
      assertEquals("/WEB-INF/jsp/my.jsp", _templateDef.getPath());
   }
   
   public void testTemplateDefPuts()
   {
      HashMap fakePuts = new HashMap();
      fakePuts.put("param1", "/WEB-INF/jsp/your.jsp");
      
      _templateDef.setPutParams(fakePuts);
      
      testTemplateDef();
      Map returnedParams = _templateDef.getPutParams();
      assertTrue(returnedParams.containsKey("param1"));
   }
   
   public void testTemplateDefGetParamByName()
   {
   	testTemplateDefPuts();
   	
   	String paramValue = _templateDef.getPutParam("param1");
   	assertNotNull(paramValue);
   	assertEquals(paramValue, "/WEB-INF/jsp/your.jsp");
   }

}
