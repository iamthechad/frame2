package org.megatome.frame2.template.config;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.megatome.frame2.util.sax.Frame2EntityResolver;
import org.megatome.frame2.util.sax.Frame2ParseErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * TestWAMConfigReader.java
 */
public class TestTemplate_DTD extends TestCase {
    
   private DocumentBuilder _documentBuilder = null;
   /**
    * Constructor for TestTemplate_DTD
    */
   public TestTemplate_DTD() {
      super();
   }

   /**
    * Constructor for TestTemplate_DTD
    * @param name
    */
   public TestTemplate_DTD(String name) {
      super(name);
   }

   /**
    * @see junit.framework.TestCase#setUp()
    */
   protected void setUp() throws Exception {
      super.setUp();
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setValidating(true);
      
      _documentBuilder = dbf.newDocumentBuilder();
      _documentBuilder.setErrorHandler(new Frame2ParseErrorHandler());
      _documentBuilder.setEntityResolver(new Frame2EntityResolver());
   }

   /**
    * @see junit.framework.TestCase#tearDown()
    */
   protected void tearDown() throws Exception {
      super.tearDown();
   }

   public void testDTD()
   {
      parseConfig("org/megatome/frame2/template/config/emptyTags-Template.xml"); 
   }    
   
   public void testNegativeDTD()
   {
      parseNegativeConfig("org/megatome/frame2/template/config/emptyConfigTag-Negative-Template.xml");
   }
   
   public void testNegativeConfigParentTag()
   {
      parseNegativeConfig("org/megatome/frame2/template/config/emptyTags-NegativeConfig-Template.xml");
   } 
   
   public void testNegativeConfigTemplatesTag()
   {
      parseNegativeConfig("org/megatome/frame2/template/config/emptyTags-Negative-Template.xml");
   }   
   
   public void testSingleTemplate()
   {
      parseConfig("org/megatome/frame2/template/config/singleTag-Template.xml");
   }
   
   public void testNegativeNameSingleTemplate()
   {
      parseNegativeConfig("org/megatome/frame2/template/config/singleTag-NegativeName-Template.xml");
   }
   
   public void testNegativePathSingleTemplate()
   {
      parseNegativeConfig("org/megatome/frame2/template/config/singleTag-NegativePath-Template.xml");
   }
   
   public void testSingleTemplatePut()
   {
      parseConfig("org/megatome/frame2/template/config/singleTag-Put-Template.xml");
   }
   
   public void testNegativeSingleTemplatePutName()
   {
      parseNegativeConfig("org/megatome/frame2/template/config/singleTag-NegativePutName-Template.xml");
   }
   
   public void testNegativeSingleTemplatePutPath()
   {
      parseNegativeConfig("org/megatome/frame2/template/config/singleTag-NegativePutPath-Template.xml");
   }
   
   public void testMultipleTemplate()
   {
      parseConfig("org/megatome/frame2/template/config/multTag-Template.xml");
   }
   
   public void testMultipleTemplatePut()
   {
      parseConfig("org/megatome/frame2/template/config/multTag-Put-Template.xml");
   }   
   
   public void testNegativeNameMultipleTemplate()
   {
      parseNegativeConfig("org/megatome/frame2/template/config/multTag-NegativeName-Template.xml");
   }   
      
   public void testNegativePathMultipleTemplate()
   {
      parseNegativeConfig("org/megatome/frame2/template/config/multTag-NegativePath-Template.xml");
   }      
   
   private void parseConfig(String configFile)
   {
      try {
        InputStream is = getClass().getClassLoader().getResourceAsStream(configFile);
        _documentBuilder.parse(is);
      } catch (IOException ioe) {
          ioe.printStackTrace();
          fail("Unexpected IOException");
      } catch (SAXParseException spe) {
         spe.printStackTrace();
         fail("Unexpected SAXParseException");
      } catch (SAXException se) {
          se.printStackTrace();
          fail("Unexpected SAXException");
      } 
   }  
   
   private void parseNegativeConfig(String configFile)
   {
      try {
        InputStream is = getClass().getClassLoader().getResourceAsStream(configFile);
        _documentBuilder.parse(is);
      } catch (IOException ioe) {
         fail("Unexpected IO Exception");
      } catch (SAXParseException spe) {
         return;
      } catch (SAXException se) {
         return;
      } 
      
      fail("Expected SAXException did not occur");
   }
}
