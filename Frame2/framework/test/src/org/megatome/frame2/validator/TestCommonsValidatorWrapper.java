package org.megatome.frame2.validator;

import java.util.Locale;

import org.apache.commons.validator.Form;
import org.apache.commons.validator.ValidatorResources;
import org.megatome.frame2.validator.CommonsValidatorException;
import org.megatome.frame2.validator.CommonsValidatorWrapper;

import servletunit.frame2.MockFrame2TestCase;

/**
 * @author hmilligan
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class TestCommonsValidatorWrapper extends MockFrame2TestCase {
  
   /**
    * Constructor for TestCommonsValidatorResources.
    * @param name
    */
   public TestCommonsValidatorWrapper(String name) {
      super(name);
   }
   
 /**
   * @see junit.framework.TestCase#setUp()
   */
   protected void setUp() throws Exception {
      super.setUp();      
   }

   /**
    * @see junit.framework.TestCase#tearDown()
    */
   protected void tearDown() throws Exception {
      super.tearDown();
   }
   
   
   public void testValidatorInitialization() {
                 
      ValidatorResources resources = null;
      try {       
         CommonsValidatorWrapper.setFilePath("/org/megatome/frame2/validator/config");
         CommonsValidatorWrapper.load(getContext());
         resources = CommonsValidatorWrapper.getValidatorResources();
         assertNotNull(resources);     
      } catch (CommonsValidatorException e) {
         fail();
      }   
      
      Form f = resources.get(new Locale("EN"), "ValidateBean");
      assertNotNull(f);                                                                                                              
            
   }


  public void testNegativeValidatorInitializationFileNotFound() {
                 
      ValidatorResources resources = null;
      try {       
         CommonsValidatorWrapper.setFilePath("/org/megatome/frame2/validator/config");
         CommonsValidatorWrapper.setMappingsFile("dude.xml");
         CommonsValidatorWrapper.load(getContext());
         resources = CommonsValidatorWrapper.getValidatorResources(); 
      } catch (CommonsValidatorException e) {
        return;
      }   
      fail();
            
   }
   
    
    
            
}
