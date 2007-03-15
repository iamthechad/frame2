/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2007 Megatome Technologies.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by
 *        Megatome Technologies."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Frame2 Project", and "Frame2", 
 *    must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact iamthechad@sourceforge.net.
 *
 * 5. Products derived from this software may not be called "Frame2"
 *    nor may "Frame2" appear in their names without prior written
 *    permission of Megatome Technologies.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL MEGATOME TECHNOLOGIES OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package org.megatome.frame2.validator;

import java.util.Iterator;
import java.util.Locale;

import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.ValidatorResources;
import org.megatome.frame2.Globals;
import org.megatome.frame2.errors.Error;
import org.megatome.frame2.errors.Errors;
import org.megatome.frame2.errors.TestError;
import org.megatome.frame2.errors.impl.ErrorsFactory;
import org.megatome.frame2.util.ResourceLocator;

import servletunit.frame2.MockFrame2TestCase;

public class TestCommonsFieldValidator extends MockFrame2TestCase {

   ValidatorResources validatorResources;
   static String DEF_FILE_PATH = "/org/megatome/frame2/validator/config"; //$NON-NLS-1$
   static String DEF_MAPPINGS_FILE = "test-all-mappings.xml"; //$NON-NLS-1$
   static String DEF_RULES_FILE = "test-all-rules.xml"; //$NON-NLS-1$
   static int DEF_ERRORS_SIZE = 1;
   // There is always one default error to verify error object passed in is not
   // corrupted by the validation methods.
   static String EMAIL_BEAN_NAME = "testEmailFieldBean"; //$NON-NLS-1$
   static String REQUIRED_BEAN_NAME = "testRequiredFieldBean"; //$NON-NLS-1$
   static String MASK_BEAN_NAME = "testMaskFieldBean"; //$NON-NLS-1$
   static String BYTE_BEAN_NAME = "testByteFieldBean"; //$NON-NLS-1$
   static String SHORT_BEAN_NAME = "testShortFieldBean"; //$NON-NLS-1$
   static String INT_BEAN_NAME = "testIntegerFieldBean"; //$NON-NLS-1$
   static String LONG_BEAN_NAME = "testLongFieldBean"; //$NON-NLS-1$
   static String FLOAT_BEAN_NAME = "testFloatFieldBean"; //$NON-NLS-1$
   static String DOUBLE_BEAN_NAME = "testDoubleFieldBean"; //$NON-NLS-1$
   static String DATE_BEAN_NAME = "testDateFieldBean"; //$NON-NLS-1$
   static String STRICT_DATE_BEAN_NAME = "testStrictDateFieldBean"; //$NON-NLS-1$
   static String INT_RANGE_BEAN_NAME = "testIntRangeFieldBean"; //$NON-NLS-1$
   static String FLOAT_RANGE_BEAN_NAME = "testFloatRangeFieldBean"; //$NON-NLS-1$
   static String DOUBLE_RANGE_BEAN_NAME = "testDoubleRangeFieldBean"; //$NON-NLS-1$
   static String CREDIT_CARD_BEAN_NAME = "testCreditCardFieldBean"; //$NON-NLS-1$
   static String MIN_LEN_BEAN_NAME = "testMinLengthFieldBean"; //$NON-NLS-1$
   static String MAX_LEN_BEAN_NAME = "testMaxLengthFieldBean"; //$NON-NLS-1$
   static final String TWO_FIELD_BEAN_NAME = "testTwoFieldBean"; //$NON-NLS-1$
   static final String REQUIRED_IF_NULL_BEAN_NAME = "testRequiredIfNullBean"; //$NON-NLS-1$
   static final String REQUIRED_IF_NOT_NULL_BEAN_NAME = "testRequiredIfNotNullBean"; //$NON-NLS-1$
   static final String REQUIRED_IF_EQUAL_BEAN_NAME = "testRequiredIfEqualBean"; //$NON-NLS-1$

   /**
    * Constructor for TestCommonsFieldValidator.
    * @param name
    */
   public TestCommonsFieldValidator(String name) {
      super(name);
   }

   /**
    * @see junit.framework.TestCase#setUp()
    */
   @Override
   protected void setUp() throws Exception {
      super.setUp();
      this.validatorResources = buildValidatorResources();
      ResourceLocator.setBasename("frame2-resource"); //$NON-NLS-1$
   }

   /**
    * @see junit.framework.TestCase#tearDown()
    */
   @Override
   protected void tearDown() throws Exception {
      super.tearDown();
      this.validatorResources = null;
   }

   private ValidatorResources buildValidatorResources() {
      return buildValidatorResources(
         DEF_FILE_PATH,
         DEF_MAPPINGS_FILE,
         DEF_RULES_FILE);
   }

   private ValidatorResources buildValidatorResources(
      String filePath,
      String mappingsFile,
      String rulesFile) {
      ValidatorResources resources = null;
      try {
         CommonsValidatorWrapper.setFilePath(filePath);
         CommonsValidatorWrapper.setMappingsFile(mappingsFile);
         CommonsValidatorWrapper.setRulesFile(rulesFile);
         CommonsValidatorWrapper.load(getContext());
         resources = CommonsValidatorWrapper.getValidatorResources();
         assertNotNull(resources);
      } catch (CommonsValidatorException e) {
         fail();
      }
      return resources;
   }

   private boolean setupEmailTest(String emailAddr) {
      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setEmail(emailAddr);

      testBean.setEmpty("dude"); //$NON-NLS-1$

      Errors errors = setupAndRunValidate(testBean, EMAIL_BEAN_NAME);

      return validateErrors(errors);

   }


   private boolean validateErrors(Errors errors) {
      return validateErrors(
         errors,
         TestError.PREPEND_MSG,
         TestError.FRAME2_MSG,
         "tag.question"); //$NON-NLS-1$
   }
   
   private boolean validateErrors(Errors errors, String msg, String errorKey ) {
      return validateErrors(
         errors,
         TestError.PREPEND_MSG,
         msg,
         errorKey);
   }

   private boolean validateErrors(
      Errors errors,
      String prependMsg,
      String msg,
      String errorKey) {
      Iterator<Error> iter = errors.iterator();

      boolean foundValidationlError = false;
      boolean foundSeedError = false;
      while (iter.hasNext()) {
         Error error = iter.next();
         if (error.getKey().equals("seed")) { //$NON-NLS-1$
            foundSeedError = true;
         }
         if (error.getKey().equals(errorKey)) {
            String errorMsg = error.getMessage(Locale.US);
            String expectedMsg = ""; //$NON-NLS-1$
            if (prependMsg != null) {
               expectedMsg += prependMsg;
            }
            if (msg != null) {
               expectedMsg += msg;
            }
            /*if (appendMsg != null) {
               appendMsg += appendMsg;
            }*/

            assertEquals(errorMsg, expectedMsg);
            foundValidationlError = true;
         }
      }

      assertTrue(foundSeedError);

      return foundValidationlError;
   }

   private Errors setupAndRunValidate(
      CommonsValidatorBean testBean,
      String fieldBeanName) {
      // Add an error to verify the error is passed in..
      Errors errors = ErrorsFactory.newInstance();
      errors.add("seed", "dude"); //$NON-NLS-1$ //$NON-NLS-2$

      Validator validator = new Validator(this.validatorResources, fieldBeanName);
      // add the name bean to the validator as a resource
      // for the validations to be performed on.
      validator.addResource(Validator.BEAN_KEY, testBean);
      validator.addResource(Globals.ERRORS_KEY, errors);

      // Get results of the validation.
      try {
         validator.validate();
      } catch (ValidatorException e) {
         fail();
      }
      return errors;
   }

   public void testEmail() {
      assertFalse(setupEmailTest("Yabba@hotmail.com")); //$NON-NLS-1$
   }

   public void testNegativeEmail() {
      assertTrue(setupEmailTest("Yahotmail.com")); //$NON-NLS-1$
   }

   public void testNullEmailNotRequired() {
      this.validatorResources = buildValidatorResources();

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setEmpty("dude"); //$NON-NLS-1$

      Errors errors = setupAndRunValidate(testBean, EMAIL_BEAN_NAME);
      assertEquals(DEF_ERRORS_SIZE, errors.size());

   }

   public void testRequired() {

      this.validatorResources = buildValidatorResources();

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setRequired("required"); //$NON-NLS-1$

      Errors errors = setupAndRunValidate(testBean, REQUIRED_BEAN_NAME);
      assertEquals(DEF_ERRORS_SIZE, errors.size());
   }

   public void testNegativeRequired() {

      this.validatorResources = buildValidatorResources();

      CommonsValidatorBean testBean = new CommonsValidatorBean();

      Errors errors = setupAndRunValidate(testBean, REQUIRED_BEAN_NAME);

      assertTrue(validateErrors(errors));
   }

   public void testMask() {

      this.validatorResources = buildValidatorResources();

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setPhone("303-123-1234"); //$NON-NLS-1$

      Errors errors = setupAndRunValidate(testBean, MASK_BEAN_NAME);
      assertEquals(DEF_ERRORS_SIZE, errors.size());
   }

   public void testNegativeMask() {

      this.validatorResources = buildValidatorResources();

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setPhone("303-123"); //$NON-NLS-1$

      Errors errors = setupAndRunValidate(testBean, MASK_BEAN_NAME);

      assertTrue(validateErrors(errors));
   }

   public void testNullPhone() {

      this.validatorResources = buildValidatorResources();

      CommonsValidatorBean testBean = new CommonsValidatorBean();

      Errors errors = setupAndRunValidate(testBean, MASK_BEAN_NAME);

      assertEquals(DEF_ERRORS_SIZE, errors.size());
   }

   private void runTest(CommonsValidatorBean testBean, String beanName) {
      this.validatorResources = buildValidatorResources();

      Errors errors = setupAndRunValidate(testBean, beanName);
      assertEquals(DEF_ERRORS_SIZE, errors.size());
   }

   private void runTestNegative(
      CommonsValidatorBean testBean,
      String beanName) {
      this.validatorResources = buildValidatorResources();
      Errors errors = setupAndRunValidate(testBean, beanName);
      assertTrue(validateErrors(errors));
   }
   
   private void runTestNegative(
      CommonsValidatorBean testBean,
      String beanName, String overRideDefErrorMessage, String errorKey) {
      this.validatorResources = buildValidatorResources();
      Errors errors = setupAndRunValidate(testBean, beanName);
      assertTrue(validateErrors(errors, overRideDefErrorMessage, errorKey));
   }

   public void testByte() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setByte("12"); //$NON-NLS-1$

      runTest(testBean, BYTE_BEAN_NAME);
   }

   public void testNegativeByte() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setByte("30323"); //$NON-NLS-1$
      runTestNegative(testBean, BYTE_BEAN_NAME);
   }

   public void testNullByte() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      runTest(testBean, BYTE_BEAN_NAME);
   }

   public void testShort() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setShort("12"); //$NON-NLS-1$
      runTest(testBean, SHORT_BEAN_NAME);

   }

   public void testNegativeShort() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setShort("30323123454"); //$NON-NLS-1$

      runTestNegative(testBean, SHORT_BEAN_NAME);

   }

   public void testNullShort() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      runTest(testBean, SHORT_BEAN_NAME);

   }

   public void testInteger() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setInteger("12"); //$NON-NLS-1$
      runTest(testBean, INT_BEAN_NAME);

   }

   public void testNegativeInteger() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setInteger("A"); //$NON-NLS-1$

      runTestNegative(testBean, INT_BEAN_NAME);

   }

   public void testNullInteger() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      runTest(testBean, INT_BEAN_NAME);

   }

   public void testLong() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setLong("12"); //$NON-NLS-1$
      runTest(testBean, LONG_BEAN_NAME);

   }

   public void testNegativeLong() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setLong("A"); //$NON-NLS-1$

      runTestNegative(testBean, LONG_BEAN_NAME);

   }

   public void testNullLong() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      runTest(testBean, LONG_BEAN_NAME);

   }

   public void testFloat() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setFloat("12"); //$NON-NLS-1$
      runTest(testBean, FLOAT_BEAN_NAME);

   }

   public void testNegativeFloat() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setFloat("A"); //$NON-NLS-1$

      runTestNegative(testBean, FLOAT_BEAN_NAME);

   }

   public void testNullFloat() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      runTest(testBean, FLOAT_BEAN_NAME);

   }

   public void testDouble() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setDouble("12"); //$NON-NLS-1$
      runTest(testBean, DOUBLE_BEAN_NAME);

   }

   public void testNegativeDouble() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setDouble("A"); //$NON-NLS-1$

      runTestNegative(testBean, DOUBLE_BEAN_NAME);

   }

   public void testNullDouble() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      runTest(testBean, DOUBLE_BEAN_NAME);

   }

   public void testDate() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setDate("2/22/1999"); //$NON-NLS-1$
      runTest(testBean, DATE_BEAN_NAME);
   }

   public void testNegativeDate() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setDate("A"); //$NON-NLS-1$

      runTestNegative(testBean, DATE_BEAN_NAME);

   }

   public void testNullDate() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      runTest(testBean, DATE_BEAN_NAME);

   }

   public void testStrictDate() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setDate("02/22/1999"); //$NON-NLS-1$
      runTest(testBean, STRICT_DATE_BEAN_NAME);
   }

   public void testNegativeStrictDate() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setDate("A"); //$NON-NLS-1$

      runTestNegative(testBean, STRICT_DATE_BEAN_NAME);

   }

   public void testNullStrictDate() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      runTest(testBean, STRICT_DATE_BEAN_NAME);

   }

   public void testIntRange() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setInteger("15"); //$NON-NLS-1$
      runTest(testBean, INT_RANGE_BEAN_NAME);
   }

   public void testNegativeIntRange() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setInteger("30"); //$NON-NLS-1$

      runTestNegative(testBean, INT_RANGE_BEAN_NAME,"Is this 5 Frame2?20", "tag.question.with.two.parms"); //$NON-NLS-1$ //$NON-NLS-2$

   }

   public void testNullIntRange() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      runTest(testBean, INT_RANGE_BEAN_NAME);

   }

   public void testFloatRange() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setFloat("2.37"); //$NON-NLS-1$
      runTest(testBean, FLOAT_RANGE_BEAN_NAME);
   }

   public void testNegativeFloatRange() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setFloat("155.00"); //$NON-NLS-1$

      runTestNegative(testBean, FLOAT_RANGE_BEAN_NAME,"Is this -1.00 Frame2?37.88", "tag.question.with.two.parms"); //$NON-NLS-1$ //$NON-NLS-2$

   }

   public void testNullFloatRange() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      runTest(testBean, FLOAT_RANGE_BEAN_NAME);

   }

   public void testDoubleRange() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setDouble("2.37"); //$NON-NLS-1$
      runTest(testBean, DOUBLE_RANGE_BEAN_NAME);
   }

   public void testNegativeDoubleRange() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setDouble("155.00"); //$NON-NLS-1$

      runTestNegative(testBean, DOUBLE_RANGE_BEAN_NAME,"Is this -1.00 Frame2?37.88", "tag.question.with.two.parms"); //$NON-NLS-1$ //$NON-NLS-2$

   }

   public void testNullDoubleRange() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      runTest(testBean, DOUBLE_RANGE_BEAN_NAME);

   }

   public void testCreditCard() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();    
      testBean.setCreditCard("4111111111111111"); //$NON-NLS-1$
      runTest(testBean, CREDIT_CARD_BEAN_NAME);
   }

   public void testNegativeCreditCard() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setCreditCard("1234-1234-1234-12"); //$NON-NLS-1$

      runTestNegative(testBean, CREDIT_CARD_BEAN_NAME);

   }

   public void testNullCreditCard() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      runTest(testBean, CREDIT_CARD_BEAN_NAME);

   }
   
   
   public void testMinLength() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setMinLength("1234"); //$NON-NLS-1$
      runTest(testBean, MIN_LEN_BEAN_NAME);
   }

   public void testNegativeMinLength() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setMinLength("12"); //$NON-NLS-1$
      
      runTestNegative(testBean, MIN_LEN_BEAN_NAME, "Is this 3 Frame2?", "tag.question.with.parm");     //$NON-NLS-1$ //$NON-NLS-2$
   }

   public void testNullMinLength() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      runTest(testBean, MIN_LEN_BEAN_NAME);

   }
   
   public void testMaxLength() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setMaxLength("123"); //$NON-NLS-1$
      runTest(testBean, MAX_LEN_BEAN_NAME);
   }

   public void testNegativeMaxLength() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setMaxLength("dudasdfdsfsadfdsfsdafsdf"); //$NON-NLS-1$

      runTestNegative(testBean, MAX_LEN_BEAN_NAME, "Is this 5 Frame2?", "tag.question.with.parm"); //$NON-NLS-1$ //$NON-NLS-2$

   }

   public void testNullMaxLength() {

      CommonsValidatorBean testBean = new CommonsValidatorBean();
      runTest(testBean, MAX_LEN_BEAN_NAME);

   }
   
   public void testTwoFields() {
      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setTwoField1("value"); //$NON-NLS-1$
      testBean.setTwoField2("value"); //$NON-NLS-1$
      runTest(testBean, TWO_FIELD_BEAN_NAME);
   }
   
   public void testNegativeTwoFields() {
      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setTwoField1("value"); //$NON-NLS-1$
      testBean.setTwoField2("not_value"); //$NON-NLS-1$
      runTestNegative(testBean, TWO_FIELD_BEAN_NAME, "Is this append Frame2?", "tag.question.with.parm"); //$NON-NLS-1$ //$NON-NLS-2$
   }
   
   public void testNullTwoFields() {
      CommonsValidatorBean testBean = new CommonsValidatorBean();
      runTest(testBean, TWO_FIELD_BEAN_NAME);
   }
   
   public void testRequiredIfNull() {
      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setRequired("value"); //$NON-NLS-1$
      runTest(testBean, REQUIRED_IF_NULL_BEAN_NAME);
      
      testBean.setTwoField1("something"); //$NON-NLS-1$
      testBean.setRequired(null);
      runTest(testBean, REQUIRED_IF_NULL_BEAN_NAME);
   }
   
   public void testNegativeRequiredIfNull() {
      CommonsValidatorBean testBean = new CommonsValidatorBean();
      runTestNegative(testBean, REQUIRED_IF_NULL_BEAN_NAME, "Is this Frame2?", "tag.question"); //$NON-NLS-1$ //$NON-NLS-2$
   }
   
   public void testRequiredIfNotNull() {
      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setTwoField1("Something"); //$NON-NLS-1$
      testBean.setRequired("value"); //$NON-NLS-1$
      runTest(testBean, REQUIRED_IF_NOT_NULL_BEAN_NAME);
      
      testBean.setTwoField1(null);
      testBean.setRequired(null);
      runTest(testBean, REQUIRED_IF_NOT_NULL_BEAN_NAME);
   }
   
   public void testNegativeRequiredIfNotNull() {
      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setTwoField1("Something"); //$NON-NLS-1$
      runTestNegative(testBean, REQUIRED_IF_NOT_NULL_BEAN_NAME, "Is this Frame2?", "tag.question"); //$NON-NLS-1$ //$NON-NLS-2$
   }
   
   public void testRequiredIfEqual() {
      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setTwoField1("value"); //$NON-NLS-1$
      testBean.setRequired("value"); //$NON-NLS-1$
      runTest(testBean, REQUIRED_IF_EQUAL_BEAN_NAME);
      
      testBean.setTwoField1("not_equal_value"); //$NON-NLS-1$
      testBean.setRequired(null);
      runTest(testBean, REQUIRED_IF_EQUAL_BEAN_NAME);
   }
   
   public void testNegativeRequiredIfEqual() {
      CommonsValidatorBean testBean = new CommonsValidatorBean();
      testBean.setTwoField1("value"); //$NON-NLS-1$
      runTestNegative(testBean, REQUIRED_IF_EQUAL_BEAN_NAME, "Is this Frame2?", "tag.question"); //$NON-NLS-1$ //$NON-NLS-2$
   }

}
