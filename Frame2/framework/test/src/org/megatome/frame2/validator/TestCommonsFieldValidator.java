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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Locale;

import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.ValidatorResources;
import org.junit.After;
import org.junit.Test;
import org.megatome.frame2.Globals;
import org.megatome.frame2.errors.Error;
import org.megatome.frame2.errors.Errors;
import org.megatome.frame2.errors.TestError;
import org.megatome.frame2.errors.impl.ErrorsFactory;
import org.megatome.frame2.util.ResourceLocator;

import servletunit.frame2.MockFrame2TestCase;

public class TestCommonsFieldValidator extends MockFrame2TestCase {

	private CommonsValidatorBean testBean;
	private ValidatorResources validatorResources;
	static String DEF_FILE_PATH = "/org/megatome/frame2/validator/config"; //$NON-NLS-1$
	static String DEF_MAPPINGS_FILE = "test-all-mappings.xml"; //$NON-NLS-1$
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

	@Override
	public void setUp() throws Exception {
		super.setUp();
		this.validatorResources = buildValidatorResources();
		ResourceLocator.setBasename("frame2-resource"); //$NON-NLS-1$
		this.testBean = new CommonsValidatorBean();
	}

	@After
	public void tearDown() throws Exception {
		this.validatorResources = null;
	}

	private ValidatorResources buildValidatorResources() {
		return buildValidatorResources(DEF_FILE_PATH, DEF_MAPPINGS_FILE);
	}

	private ValidatorResources buildValidatorResources(final String filePath,
			final String mappingsFile) {
		ValidatorResources resources = null;
		try {
			CommonsValidatorWrapper.setFilePath(filePath);
			CommonsValidatorWrapper.setMappingsFile(mappingsFile);
			// CommonsValidatorWrapper.setRulesFile(rulesFile);
			CommonsValidatorWrapper.load(getContext());
			resources = CommonsValidatorWrapper.getValidatorResources();
			assertNotNull(resources);
		} catch (final CommonsValidatorException e) {
			fail();
		}
		return resources;
	}

	private boolean setupEmailTest(final String emailAddr) {
		this.testBean.setEmail(emailAddr);
		this.testBean.setEmpty("dude"); //$NON-NLS-1$
		final Errors errors = setupAndRunValidate(this.testBean,
				EMAIL_BEAN_NAME);
		return validateErrors(errors);
	}

	private boolean validateErrors(final Errors errors) {
		return validateErrors(errors, TestError.PREPEND_MSG,
				TestError.FRAME2_MSG, "tag.question"); //$NON-NLS-1$
	}

	private boolean validateErrors(final Errors errors, final String msg,
			final String errorKey) {
		return validateErrors(errors, TestError.PREPEND_MSG, msg, errorKey);
	}

	private boolean validateErrors(final Errors errors,
			final String prependMsg, final String msg, final String errorKey) {

		boolean foundValidationlError = false;
		boolean foundSeedError = false;
		for (final Error error : errors.get()) {
			if (error.getKey().equals("seed")) { //$NON-NLS-1$
				foundSeedError = true;
			}
			if (error.getKey().equals(errorKey)) {
				final String errorMsg = error.getMessage(Locale.US);
				String expectedMsg = ""; //$NON-NLS-1$
				if (prependMsg != null) {
					expectedMsg += prependMsg;
				}
				if (msg != null) {
					expectedMsg += msg;
				}
				/*
				 * if (appendMsg != null) { appendMsg += appendMsg; }
				 */

				assertEquals(errorMsg, expectedMsg);
				foundValidationlError = true;
			}
		}

		assertTrue(foundSeedError);

		return foundValidationlError;
	}

	private Errors setupAndRunValidate(final CommonsValidatorBean testBean,
			final String fieldBeanName) {
		// Add an error to verify the error is passed in..
		final Errors errors = ErrorsFactory.newInstance();
		errors.add("seed", "dude"); //$NON-NLS-1$ //$NON-NLS-2$

		final Validator validator = new Validator(this.validatorResources,
				fieldBeanName);
		// add the name bean to the validator as a resource
		// for the validations to be performed on.
		validator.setParameter(Validator.BEAN_PARAM, testBean);
		validator.setParameter(Globals.ERRORS_KEY, errors);

		// Get results of the validation.
		try {
			validator.validate();
		} catch (final ValidatorException e) {
			fail();
		}
		return errors;
	}

	@Test
	public void testEmail() {
		assertFalse(setupEmailTest("Yabba@hotmail.com")); //$NON-NLS-1$
	}

	@Test
	public void testNegativeEmail() {
		assertTrue(setupEmailTest("Yahotmail.com")); //$NON-NLS-1$
	}

	@SuppressWarnings("boxing")
	@Test
	public void testNullEmailNotRequired() {
		this.validatorResources = buildValidatorResources();

		this.testBean.setEmpty("dude"); //$NON-NLS-1$

		final Errors errors = setupAndRunValidate(this.testBean,
				EMAIL_BEAN_NAME);
		assertEquals(DEF_ERRORS_SIZE, errors.size());

	}

	@SuppressWarnings("boxing")
	@Test
	public void testRequired() {

		this.validatorResources = buildValidatorResources();

		this.testBean.setRequired("required"); //$NON-NLS-1$

		final Errors errors = setupAndRunValidate(this.testBean,
				REQUIRED_BEAN_NAME);
		assertEquals(DEF_ERRORS_SIZE, errors.size());
	}

	@Test
	public void testNegativeRequired() {

		this.validatorResources = buildValidatorResources();

		final Errors errors = setupAndRunValidate(this.testBean,
				REQUIRED_BEAN_NAME);

		assertTrue(validateErrors(errors));
	}

	@SuppressWarnings("boxing")
	@Test
	public void testMask() {

		this.validatorResources = buildValidatorResources();

		this.testBean.setPhone("303-123-1234"); //$NON-NLS-1$

		final Errors errors = setupAndRunValidate(this.testBean, MASK_BEAN_NAME);
		assertEquals(DEF_ERRORS_SIZE, errors.size());
	}

	@Test
	public void testNegativeMask() {

		this.validatorResources = buildValidatorResources();

		this.testBean.setPhone("303-123"); //$NON-NLS-1$

		final Errors errors = setupAndRunValidate(this.testBean, MASK_BEAN_NAME);

		assertTrue(validateErrors(errors));
	}

	@SuppressWarnings("boxing")
	@Test
	public void testNullPhone() {

		this.validatorResources = buildValidatorResources();

		final Errors errors = setupAndRunValidate(this.testBean, MASK_BEAN_NAME);

		assertEquals(DEF_ERRORS_SIZE, errors.size());
	}

	@SuppressWarnings("boxing")
	private void runTest(final CommonsValidatorBean bean, final String beanName) {
		this.validatorResources = buildValidatorResources();

		final Errors errors = setupAndRunValidate(bean, beanName);
		assertEquals(DEF_ERRORS_SIZE, errors.size());
	}

	private void runTestNegative(final CommonsValidatorBean bean,
			final String beanName) {
		this.validatorResources = buildValidatorResources();
		final Errors errors = setupAndRunValidate(bean, beanName);
		assertTrue(validateErrors(errors));
	}

	private void runTestNegative(final CommonsValidatorBean bean,
			final String beanName, final String overRideDefErrorMessage,
			final String errorKey) {
		this.validatorResources = buildValidatorResources();
		final Errors errors = setupAndRunValidate(bean, beanName);
		assertTrue(validateErrors(errors, overRideDefErrorMessage, errorKey));
	}

	@Test
	public void testByte() {
		this.testBean.setByte("12"); //$NON-NLS-1$

		runTest(this.testBean, BYTE_BEAN_NAME);
	}

	@Test
	public void testNegativeByte() {
		this.testBean.setByte("30323"); //$NON-NLS-1$
		runTestNegative(this.testBean, BYTE_BEAN_NAME);
	}

	@Test
	public void testNullByte() {
		runTest(this.testBean, BYTE_BEAN_NAME);
	}

	@Test
	public void testShort() {
		this.testBean.setShort("12"); //$NON-NLS-1$
		runTest(this.testBean, SHORT_BEAN_NAME);

	}

	@Test
	public void testNegativeShort() {
		this.testBean.setShort("30323123454"); //$NON-NLS-1$

		runTestNegative(this.testBean, SHORT_BEAN_NAME);

	}

	@Test
	public void testNullShort() {
		runTest(this.testBean, SHORT_BEAN_NAME);

	}

	@Test
	public void testInteger() {
		this.testBean.setInteger("12"); //$NON-NLS-1$
		runTest(this.testBean, INT_BEAN_NAME);

	}

	@Test
	public void testNegativeInteger() {
		this.testBean.setInteger("A"); //$NON-NLS-1$

		runTestNegative(this.testBean, INT_BEAN_NAME);

	}

	@Test
	public void testNullInteger() {
		runTest(this.testBean, INT_BEAN_NAME);

	}

	@Test
	public void testLong() {
		this.testBean.setLong("12"); //$NON-NLS-1$
		runTest(this.testBean, LONG_BEAN_NAME);

	}

	@Test
	public void testNegativeLong() {
		this.testBean.setLong("A"); //$NON-NLS-1$

		runTestNegative(this.testBean, LONG_BEAN_NAME);

	}

	@Test
	public void testNullLong() {
		runTest(this.testBean, LONG_BEAN_NAME);

	}

	@Test
	public void testFloat() {
		this.testBean.setFloat("12"); //$NON-NLS-1$
		runTest(this.testBean, FLOAT_BEAN_NAME);

	}

	@Test
	public void testNegativeFloat() {
		this.testBean.setFloat("A"); //$NON-NLS-1$

		runTestNegative(this.testBean, FLOAT_BEAN_NAME);

	}

	@Test
	public void testNullFloat() {
		runTest(this.testBean, FLOAT_BEAN_NAME);

	}

	@Test
	public void testDouble() {
		this.testBean.setDouble("12"); //$NON-NLS-1$
		runTest(this.testBean, DOUBLE_BEAN_NAME);

	}

	@Test
	public void testNegativeDouble() {
		this.testBean.setDouble("A"); //$NON-NLS-1$

		runTestNegative(this.testBean, DOUBLE_BEAN_NAME);

	}

	@Test
	public void testNullDouble() {
		runTest(this.testBean, DOUBLE_BEAN_NAME);

	}

	@Test
	public void testDate() {
		this.testBean.setDate("2/22/1999"); //$NON-NLS-1$
		runTest(this.testBean, DATE_BEAN_NAME);
	}

	@Test
	public void testNegativeDate() {
		this.testBean.setDate("A"); //$NON-NLS-1$

		runTestNegative(this.testBean, DATE_BEAN_NAME);

	}

	@Test
	public void testNullDate() {
		runTest(this.testBean, DATE_BEAN_NAME);
	}

	@Test
	public void testStrictDate() {
		this.testBean.setDate("02/22/1999"); //$NON-NLS-1$
		runTest(this.testBean, STRICT_DATE_BEAN_NAME);
	}

	@Test
	public void testNegativeStrictDate() {
		this.testBean.setDate("A"); //$NON-NLS-1$

		runTestNegative(this.testBean, STRICT_DATE_BEAN_NAME);

	}

	@Test
	public void testNullStrictDate() {
		runTest(this.testBean, STRICT_DATE_BEAN_NAME);

	}

	@Test
	public void testIntRange() {
		this.testBean.setInteger("15"); //$NON-NLS-1$
		runTest(this.testBean, INT_RANGE_BEAN_NAME);
	}

	@Test
	public void testNegativeIntRange() {
		this.testBean.setInteger("30"); //$NON-NLS-1$

		runTestNegative(this.testBean, INT_RANGE_BEAN_NAME,
				"Is this 5 Frame2?20", "tag.question.with.two.parms"); //$NON-NLS-1$ //$NON-NLS-2$

	}

	@Test
	public void testNullIntRange() {
		runTest(this.testBean, INT_RANGE_BEAN_NAME);

	}

	@Test
	public void testFloatRange() {
		this.testBean.setFloat("2.37"); //$NON-NLS-1$
		runTest(this.testBean, FLOAT_RANGE_BEAN_NAME);
	}

	@Test
	public void testNegativeFloatRange() {
		this.testBean.setFloat("155.00"); //$NON-NLS-1$

		runTestNegative(this.testBean, FLOAT_RANGE_BEAN_NAME,
				"Is this -1.00 Frame2?37.88", "tag.question.with.two.parms"); //$NON-NLS-1$ //$NON-NLS-2$

	}

	@Test
	public void testNullFloatRange() {
		runTest(this.testBean, FLOAT_RANGE_BEAN_NAME);

	}

	@Test
	public void testDoubleRange() {
		this.testBean.setDouble("2.37"); //$NON-NLS-1$
		runTest(this.testBean, DOUBLE_RANGE_BEAN_NAME);
	}

	@Test
	public void testNegativeDoubleRange() {
		this.testBean.setDouble("155.00"); //$NON-NLS-1$

		runTestNegative(this.testBean, DOUBLE_RANGE_BEAN_NAME,
				"Is this -1.00 Frame2?37.88", "tag.question.with.two.parms"); //$NON-NLS-1$ //$NON-NLS-2$

	}

	@Test
	public void testNullDoubleRange() {
		runTest(this.testBean, DOUBLE_RANGE_BEAN_NAME);

	}

	@Test
	public void testCreditCard() {
		this.testBean.setCreditCard("4111111111111111"); //$NON-NLS-1$
		runTest(this.testBean, CREDIT_CARD_BEAN_NAME);
	}

	@Test
	public void testNegativeCreditCard() {
		this.testBean.setCreditCard("1234-1234-1234-12"); //$NON-NLS-1$

		runTestNegative(this.testBean, CREDIT_CARD_BEAN_NAME);

	}

	@Test
	public void testNullCreditCard() {
		runTest(this.testBean, CREDIT_CARD_BEAN_NAME);

	}

	@Test
	public void testMinLength() {
		this.testBean.setMinLength("1234"); //$NON-NLS-1$
		runTest(this.testBean, MIN_LEN_BEAN_NAME);
	}

	@Test
	public void testNegativeMinLength() {
		this.testBean.setMinLength("12"); //$NON-NLS-1$

		runTestNegative(this.testBean, MIN_LEN_BEAN_NAME,
				"Is this 3 Frame2?", "tag.question.with.parm"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testNullMinLength() {
		runTest(this.testBean, MIN_LEN_BEAN_NAME);

	}

	@Test
	public void testMaxLength() {
		this.testBean.setMaxLength("123"); //$NON-NLS-1$
		runTest(this.testBean, MAX_LEN_BEAN_NAME);
	}

	@Test
	public void testNegativeMaxLength() {
		this.testBean.setMaxLength("dudasdfdsfsadfdsfsdafsdf"); //$NON-NLS-1$

		runTestNegative(this.testBean, MAX_LEN_BEAN_NAME,
				"Is this 5 Frame2?", "tag.question.with.parm"); //$NON-NLS-1$ //$NON-NLS-2$

	}

	@Test
	public void testNullMaxLength() {
		runTest(this.testBean, MAX_LEN_BEAN_NAME);

	}

	@Test
	public void testTwoFields() {
		this.testBean.setTwoField1("value"); //$NON-NLS-1$
		this.testBean.setTwoField2("value"); //$NON-NLS-1$
		runTest(this.testBean, TWO_FIELD_BEAN_NAME);
	}

	@Test
	public void testNegativeTwoFields() {
		this.testBean.setTwoField1("value"); //$NON-NLS-1$
		this.testBean.setTwoField2("not_value"); //$NON-NLS-1$
		runTestNegative(this.testBean, TWO_FIELD_BEAN_NAME,
				"Is this append Frame2?", "tag.question.with.parm"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testNullTwoFields() {
		runTest(this.testBean, TWO_FIELD_BEAN_NAME);
	}

	@Test
	public void testRequiredIfNull() {
		this.testBean.setRequired("value"); //$NON-NLS-1$
		runTest(this.testBean, REQUIRED_IF_NULL_BEAN_NAME);

		this.testBean.setTwoField1("something"); //$NON-NLS-1$
		this.testBean.setRequired(null);
		runTest(this.testBean, REQUIRED_IF_NULL_BEAN_NAME);
	}

	@Test
	public void testNegativeRequiredIfNull() {
		runTestNegative(this.testBean, REQUIRED_IF_NULL_BEAN_NAME,
				"Is this Frame2?", "tag.question"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testRequiredIfNotNull() {
		this.testBean.setTwoField1("Something"); //$NON-NLS-1$
		this.testBean.setRequired("value"); //$NON-NLS-1$
		runTest(this.testBean, REQUIRED_IF_NOT_NULL_BEAN_NAME);

		this.testBean.setTwoField1(null);
		this.testBean.setRequired(null);
		runTest(this.testBean, REQUIRED_IF_NOT_NULL_BEAN_NAME);
	}

	@Test
	public void testNegativeRequiredIfNotNull() {
		this.testBean.setTwoField1("Something"); //$NON-NLS-1$
		runTestNegative(this.testBean, REQUIRED_IF_NOT_NULL_BEAN_NAME,
				"Is this Frame2?", "tag.question"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testRequiredIfEqual() {
		this.testBean.setTwoField1("value"); //$NON-NLS-1$
		this.testBean.setRequired("value"); //$NON-NLS-1$
		runTest(this.testBean, REQUIRED_IF_EQUAL_BEAN_NAME);

		this.testBean.setTwoField1("not_equal_value"); //$NON-NLS-1$
		this.testBean.setRequired(null);
		runTest(this.testBean, REQUIRED_IF_EQUAL_BEAN_NAME);
	}

	@Test
	public void testNegativeRequiredIfEqual() {
		this.testBean.setTwoField1("value"); //$NON-NLS-1$
		runTestNegative(this.testBean, REQUIRED_IF_EQUAL_BEAN_NAME,
				"Is this Frame2?", "tag.question"); //$NON-NLS-1$ //$NON-NLS-2$
	}

}
