package org.megatome.frame2.errors;

import java.util.Locale;

import junit.framework.TestCase;

import org.megatome.frame2.errors.Error;
import org.megatome.frame2.util.ResourceLocator;

public class TestError extends TestCase { 

   public static final String FRAME2_MSG = "Is this Frame2?";
   public static final String PREPEND_MSG = "prepend";
   public static final String APPEND_MSG = "append";
   
   static final String MSG_WITH_PARAMS = "Is this append Frame2?";
 
   /**
    * Constructor for TestError.
    */
   public TestError() {
      super();
   }

   /**
    * Constructor for TestError.
    * @param name
    */
   public TestError(String name) {
      super(name);
   }

   /**
    * @see junit.framework.TestCase#setUp()
    */
   protected void setUp() throws Exception {
      ResourceLocator.setBasename("frame2-resource");
      super.setUp();
   }

   /**
    * @see junit.framework.TestCase#tearDown()
    */
   protected void tearDown() throws Exception {
      super.tearDown();
   }
   
   public void testSingleError() {
      Error error = new Error("tag.prepend");
      String msg = error.getMessage(Locale.US);
      assertEquals(PREPEND_MSG, msg);
   }
   
   public void testErrorWithOneParam() {
      Error prependError = new Error("tag.prepend");
      //String prependMsg = prependError.getMessage(Locale.US);
      Error error = new Error("tag.question", prependError);
      String msg = error.getMessage(Locale.US);
      assertEquals(PREPEND_MSG + FRAME2_MSG, msg);
   }
   
   public void testErrorWithOneParamExpanded() {
      Error prependError = new Error("tag.prepend");
      String prependMsg = prependError.getMessage(Locale.US);
      Error error = new Error("tag.question", prependMsg);
      String msg = error.getMessage(Locale.US);
      assertEquals(PREPEND_MSG + FRAME2_MSG, msg);
   }
   
   public void testErrorWithTwoParams() {
      Error prependError = new Error("tag.prepend");
      Error paramError = new Error("tag.append");
      Error error = new Error("tag.question.with.parm", prependError, paramError);
      String msg = error.getMessage(Locale.US);
      assertEquals(PREPEND_MSG + MSG_WITH_PARAMS, msg);
   }
   
   public void testErrorWithTwoParamsExpanded() {
      Error prependError = new Error("tag.prepend");
      String prependMsg = prependError.getMessage(Locale.US);
      Error paramError = new Error("tag.append");
      String paramMsg = paramError.getMessage(Locale.US);
      Error error = new Error("tag.question.with.parm", prependMsg, paramMsg);
      String msg = error.getMessage(Locale.US);
      assertEquals(PREPEND_MSG + MSG_WITH_PARAMS, msg);
   }
      
   public void testErrorWithThreeParams() {
      Error prependError = new Error("tag.prepend");
      Error paramError = new Error("tag.append");
      Error paramError2 = new Error("tag.append");
      Error error = new Error("tag.question.with.two.parms", prependError, paramError, paramError2);
      String msg = error.getMessage(Locale.US);
      assertEquals(PREPEND_MSG + MSG_WITH_PARAMS + APPEND_MSG, msg);
   }
   
   public void testErrorWithThreeParamsExpanded() {
      Error prependError = new Error("tag.prepend");
      String prependMsg = prependError.getMessage(Locale.US);
      Error paramError = new Error("tag.append");
      String paramMsg = paramError.getMessage(Locale.US);
      Error paramError2 = new Error("tag.append");
      String paramMsg2 = paramError2.getMessage(Locale.US);
      Error error = new Error("tag.question.with.two.parms", prependMsg, paramMsg, paramMsg2);
      String msg = error.getMessage(Locale.US);
      assertEquals(PREPEND_MSG + MSG_WITH_PARAMS + APPEND_MSG, msg);
   }
}
