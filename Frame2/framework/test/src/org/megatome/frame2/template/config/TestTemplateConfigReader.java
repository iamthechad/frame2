package org.megatome.frame2.template.config;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.megatome.frame2.template.TemplateException;
import org.megatome.frame2.template.config.TemplateConfigReader;
import org.megatome.frame2.template.config.TemplateDef;

public class TestTemplateConfigReader extends TestCase {

   /**
    * Constructor for TestTemplateConfigReader.
    */
   public TestTemplateConfigReader() {
      super();
   }

   /**
    * Constructor for TestTemplateConfigReader.
    * @param name
    */
   public TestTemplateConfigReader(String name) {
      super(name);
   }
   
   public void testConfigurationSingleDefinition(){
      String filename = "org/megatome/frame2/template/config/singleTag-Template.xml"; 
      Map definitions = readConfig(filename);

      assertTrue(definitions.size() == 1);
      assertNotNull(definitions.get("xxx"));
      TemplateDef def = (TemplateDef)definitions.get("xxx");
      assertTrue(def.getPath().equals("xxx"));
   }
   
   public void testConfigurationSingleDefinitionPut(){
      String filename = "org/megatome/frame2/template/config/singleTag-Put-Template.xml"; 
      Map definitions = readConfig(filename);

      assertTrue(definitions.size() == 1);
      assertNotNull(definitions.get("xxx"));
      TemplateDef def = (TemplateDef)definitions.get("xxx");
      assertTrue(def.getPath().equals("xxx"));
      Map map = def.getPutParams();
      assertTrue(map.size() ==1);
      assertNotNull(map.get("yyy"));
      String path = (String)map.get("yyy");
      assertTrue(path.equals("yyy"));
   }
   
   public void testConfigurationMultiDefinitionPut(){
      String filename = "org/megatome/frame2/template/config/multTag-Put-Template.xml"; 
      Map definitions = readConfig(filename);

      assertTrue(definitions.size() == 3);
      assertNotNull(definitions.get("xxx1"));
      TemplateDef def = (TemplateDef)definitions.get("xxx1");
      assertTrue(def.getPath().equals("xxx.jsp"));
      Map map = def.getPutParams();
      assertTrue(map.size() ==2);
      assertNotNull(map.get("yyy1"));
      String path = (String)map.get("yyy1");
      assertTrue(path.equals("yyy.jsp"));
      
      assertNotNull(map.get("yyy2"));
      path = (String)map.get("yyy2");
      assertTrue(path.equals("yyy.jsp"));
      
      assertNotNull(definitions.get("xxx2"));
      def = (TemplateDef)definitions.get("xxx2");
      assertTrue(def.getPath().equals("xxx.jsp"));
      map = def.getPutParams();
      assertTrue(map.isEmpty());     

      assertNotNull(definitions.get("xxx3"));
      def = (TemplateDef)definitions.get("xxx3");
      assertTrue(def.getPath().equals("xxx.jsp"));
      map = def.getPutParams();
      assertTrue(map.size() == 1);
      map = def.getPutParams();
      assertNotNull(map.get("yyy1"));
      path = (String)map.get("yyy1");
      assertTrue(path.equals("yyy.jsp"));     
   }
   
   public void testNegativeConfigurationFile(){
      String filename = "org/megatome/frame2/template/config/badfile.xml"; 
      readNegativeConfig(filename);
   }  
   
   public void testNegativeDuplicateConfigurationFile(){
      String filename = "org/megatome/frame2/template/config/duplicateTag-Put-Template.xml"; 
      readNegativeConfig(filename);
   }  
   
   public void testNegativeDuplicatePutConfigurationFile(){
      String filename = "org/megatome/frame2/template/config/singleTag-Negative-DupPut-Template.xml"; 
      readNegativeConfig(filename);
   }    
   
   public void testMultNegativeNameConfigurationFile(){
      String filename = "org/megatome/frame2/template/config/multTag-NegativeName-Template.xml"; 
      readNegativeConfig(filename);
   }  
   
   public void testMultNegativePathConfigurationFile(){
      String filename = "org/megatome/frame2/template/config/multTag-NegativePath-Template.xml"; 
      readNegativeConfig(filename);
   }  
   
   public void testConfigurationEmptyTagDefinition(){
      String filename = "org/megatome/frame2/template/config/emptyTags-Template.xml"; 
      Map definitions = readConfig(filename);
      assertTrue(definitions.isEmpty());
   }
   
   public void testEmptyTagsNegativeConfigurationFile(){
      String filename = "org/megatome/frame2/template/config/emptyTags-Negative-Template.xml"; 
      readNegativeConfig(filename);
   }  
   
   public Map readConfig(String filename){
      HashMap definitions = new HashMap();
      TemplateConfigReader reader = new TemplateConfigReader(definitions);
      try {
         InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
         reader.execute(is);
      } catch (TemplateException ex) {
         fail();
      }
      return definitions;     
   }   
   
   public void readNegativeConfig(String filename){
      TemplateConfigReader reader = new TemplateConfigReader(new HashMap());
      try {
         InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
         reader.execute(is);
      } catch (TemplateException ex) {
         return;
      }
      fail();
   }   
}
   
