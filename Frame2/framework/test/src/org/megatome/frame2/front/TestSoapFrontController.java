package org.megatome.frame2.front;

import javax.servlet.ServletException;

import org.megatome.frame2.Globals;
import org.megatome.frame2.util.Helper;
import org.w3c.dom.Element;

import servletunit.frame2.MockFrame2TestCase;



public class TestSoapFrontController extends MockFrame2TestCase {

   Element[] _elements;
   /**
    * Constructor for TestSoapFrontController.
    *
    * @param name
    */
   public TestSoapFrontController(String name) {
      super(name);     
   }

   /**
    * @see junit.framework.TestCase#setUp()
    */
   protected void setUp() throws Exception {
      super.setUp();
      _elements = createPOEventXML();
   }

   private Element[] createPOEventXML(){
    Element[] elements = null;
      try {
           elements = Helper.loadEvents("org/megatome/frame2/jaxb/po.xml", getClass());
      } catch (Exception e) {
         fail();
      }
      return elements;
   }

   /**
    * @see junit.framework.TestCase#tearDown()
    */
   protected void tearDown() throws Exception {
      super.tearDown();
   }

  
   public void testInstantiateSoapRequestProcessor () throws Exception {     
      sendContextInitializedEvent(Globals.CONFIG_FILE,"/org/megatome/frame2/front/test-wsconfig.xml");     
      processSoapReqProc();     
   }
   
   public void testNegativeInstantiateSoapRequestProcessor () throws Exception {
      sendContextInitializedEvent(Globals.CONFIG_FILE,"/org/megatome/frame2/front/test-negative-request-processor.xml");     
      processNegativeSoapReqProc();     
   }
   
   private void processSoapReqProc() {
       SoapFrontControllerImpl controller = new SoapFrontControllerImpl();
      checkConfig();
           
       try { 
          controller.processEvent(_elements);
       }
       catch (Exception e) {
          fail();         
       }
   }

   
   private void processNegativeSoapReqProc() throws ServletException {
      SoapFrontControllerImpl controller = new SoapFrontControllerImpl();
      checkConfig();
      try { 
         controller.processEvent(_elements);
      }
      catch (Exception e) {
         return;        
      }
      fail();
   }
   
   private void checkConfig() {
       try {
         Configuration config = ConfigFactory.instance();
      } catch (ConfigException e) {
         fail();
      }
   }
          
}
