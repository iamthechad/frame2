/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2006 Megatome Technologies.  All rights
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
package org.megatome.frame2.errors;

import java.util.Locale;

import junit.framework.TestCase;

import org.megatome.frame2.errors.impl.ErrorFactory;
import org.megatome.frame2.util.ResourceLocator;

public class TestError extends TestCase {

    private static final String TAG_QUESTION_WITH_TWO_PARMS = "tag.question.with.two.parms";
    private static final String TAG_QUESTION_WITH_PARM = "tag.question.with.parm";
    private static final String TAG_APPEND = "tag.append";
    private static final String TAG_QUESTION = "tag.question";
    private static final String DIFFVALUE = "diffValue";
    private static final String DIFFERENT_TAG = "different.tag";
    private static final String VALUE3 = "value3";
    private static final String VALUE2 = "value2";
    private static final String VALUE1 = "value1";
    private static final String TAG_PREPEND = "tag.prepend";
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

    public void testEqualityNoValues() {
        Error error1 = ErrorFactory.createError(TAG_PREPEND);
        Error error2 = ErrorFactory.createError(TAG_PREPEND);
        Error error3 = ErrorFactory.createError(TAG_PREPEND);
        Error error4 = ErrorFactory.createError(TAG_PREPEND, VALUE1);
        Error error5 = ErrorFactory.createError(TAG_PREPEND, VALUE1, VALUE2);
        Error error6 = ErrorFactory.createError(TAG_PREPEND, VALUE1, VALUE2, VALUE3);
        Error error7 = ErrorFactory.createError(DIFFERENT_TAG);

        // Reflexive
        assertTrue(error1.equals(error1));
        // Symmetric
        assertTrue(error1.equals(error2));
        assertTrue(error2.equals(error1));
        // Transitive
        assertTrue(error2.equals(error3));
        assertTrue(error1.equals(error3));
        // Null
        assertTrue(!error1.equals(null));

        assertTrue(!error1.equals(error4));
        assertTrue(!error1.equals(error5));
        assertTrue(!error1.equals(error6));

        assertTrue(!error1.equals(error7));
    }

    public void testEqualityOneValue() {
        Error error1 = ErrorFactory.createError(TAG_PREPEND, VALUE1);
        Error error2 = ErrorFactory.createError(TAG_PREPEND, VALUE1);
        Error error3 = ErrorFactory.createError(TAG_PREPEND, VALUE1);
        Error error4 = ErrorFactory.createError(TAG_PREPEND);
        Error error5 = ErrorFactory.createError(TAG_PREPEND, VALUE1, VALUE2);
        Error error6 = ErrorFactory.createError(TAG_PREPEND, VALUE1, VALUE2, VALUE3);
        Error error7 = ErrorFactory.createError(DIFFERENT_TAG, VALUE1);
        Error error8 = ErrorFactory.createError(TAG_PREPEND, DIFFVALUE);

        // Reflexive
        assertTrue(error1.equals(error1));
        // Symmetric
        assertTrue(error1.equals(error2));
        assertTrue(error2.equals(error1));
        // Transitive
        assertTrue(error2.equals(error3));
        assertTrue(error1.equals(error3));
        // Null
        assertTrue(!error1.equals(null));

        assertTrue(!error1.equals(error4));
        assertTrue(!error1.equals(error5));
        assertTrue(!error1.equals(error6));

        assertTrue(!error1.equals(error7));
        assertTrue(!error1.equals(error8));
    }

    public void testEqualityTwoValues() {
        Error error1 = ErrorFactory.createError(TAG_PREPEND, VALUE1, VALUE2);
        Error error2 = ErrorFactory.createError(TAG_PREPEND, VALUE1, VALUE2);
        Error error3 = ErrorFactory.createError(TAG_PREPEND, VALUE1, VALUE2);
        Error error4 = ErrorFactory.createError(TAG_PREPEND, VALUE1);
        Error error5 = ErrorFactory.createError(TAG_PREPEND);
        Error error6 = ErrorFactory.createError(TAG_PREPEND, VALUE1, VALUE2, VALUE3);
        Error error7 = ErrorFactory.createError(DIFFERENT_TAG, VALUE1, VALUE2);
        Error error8 = ErrorFactory.createError(TAG_PREPEND, DIFFVALUE, VALUE2);
        Error error9 = ErrorFactory.createError(TAG_PREPEND, VALUE1, DIFFVALUE);

        // Reflexive
        assertTrue(error1.equals(error1));
        // Symmetric
        assertTrue(error1.equals(error2));
        assertTrue(error2.equals(error1));
        // Transitive
        assertTrue(error2.equals(error3));
        assertTrue(error1.equals(error3));
        // Null
        assertTrue(!error1.equals(null));

        assertTrue(!error1.equals(error4));
        assertTrue(!error1.equals(error5));
        assertTrue(!error1.equals(error6));

        assertTrue(!error1.equals(error7));
        assertTrue(!error1.equals(error8));
        assertTrue(!error1.equals(error9));
    }

    public void testEqualityThreeValues() {
        Error error1 = ErrorFactory.createError(TAG_PREPEND, VALUE1, VALUE2, VALUE3);
        Error error2 = ErrorFactory.createError(TAG_PREPEND, VALUE1, VALUE2, VALUE3);
        Error error3 = ErrorFactory.createError(TAG_PREPEND, VALUE1, VALUE2, VALUE3);
        Error error4 = ErrorFactory.createError(TAG_PREPEND, VALUE1);
        Error error5 = ErrorFactory.createError(TAG_PREPEND, VALUE1, VALUE2);
        Error error6 = ErrorFactory.createError(TAG_PREPEND);
        Error error7 = ErrorFactory.createError(DIFFERENT_TAG, VALUE1, VALUE2, VALUE3);
        Error error8 = ErrorFactory.createError(TAG_PREPEND, DIFFVALUE, VALUE2, VALUE3);
        Error error9 = ErrorFactory.createError(TAG_PREPEND, VALUE1, DIFFVALUE, VALUE3);
        Error error10 = ErrorFactory.createError(TAG_PREPEND, VALUE1, VALUE2, DIFFVALUE);

        // Reflexive
        assertTrue(error1.equals(error1));
        // Symmetric
        assertTrue(error1.equals(error2));
        assertTrue(error2.equals(error1));
        // Transitive
        assertTrue(error2.equals(error3));
        assertTrue(error1.equals(error3));
        // Null
        assertTrue(!error1.equals(null));

        assertTrue(!error1.equals(error4));
        assertTrue(!error1.equals(error5));
        assertTrue(!error1.equals(error6));

        assertTrue(!error1.equals(error7));
        assertTrue(!error1.equals(error8));
        assertTrue(!error1.equals(error9));
        assertTrue(!error1.equals(error10));
    }

    public void testSingleError() {
        Error error = ErrorFactory.createError(TAG_PREPEND);
        String msg = error.getMessage(Locale.US);
        assertEquals(PREPEND_MSG, msg);
    }

    public void testErrorWithOneParam() {
        Error prependError = ErrorFactory.createError(TAG_PREPEND);
        //String prependMsg = prependError.getMessage(Locale.US);
        Error error = ErrorFactory.createError(TAG_QUESTION, prependError);
        String msg = error.getMessage(Locale.US);
        assertEquals(PREPEND_MSG + FRAME2_MSG, msg);
    }

    public void testErrorWithOneParamExpanded() {
        Error prependError = ErrorFactory.createError(TAG_PREPEND);
        String prependMsg = prependError.getMessage(Locale.US);
        Error error = ErrorFactory.createError(TAG_QUESTION, prependMsg);
        String msg = error.getMessage(Locale.US);
        assertEquals(PREPEND_MSG + FRAME2_MSG, msg);
    }

    public void testErrorWithTwoParams() {
        Error prependError = ErrorFactory.createError(TAG_PREPEND);
        Error paramError = ErrorFactory.createError(TAG_APPEND);
        Error error = ErrorFactory.createError(TAG_QUESTION_WITH_PARM, prependError,
                paramError);
        String msg = error.getMessage(Locale.US);
        assertEquals(PREPEND_MSG + MSG_WITH_PARAMS, msg);
    }

    public void testErrorWithTwoParamsExpanded() {
        Error prependError = ErrorFactory.createError(TAG_PREPEND);
        String prependMsg = prependError.getMessage(Locale.US);
        Error paramError = ErrorFactory.createError(TAG_APPEND);
        String paramMsg = paramError.getMessage(Locale.US);
        Error error = ErrorFactory.createError(TAG_QUESTION_WITH_PARM, prependMsg,
                paramMsg);
        String msg = error.getMessage(Locale.US);
        assertEquals(PREPEND_MSG + MSG_WITH_PARAMS, msg);
    }

    public void testErrorWithThreeParams() {
        Error prependError = ErrorFactory.createError(TAG_PREPEND);
        Error paramError = ErrorFactory.createError(TAG_APPEND);
        Error paramError2 = ErrorFactory.createError(TAG_APPEND);
        Error error = ErrorFactory.createError(TAG_QUESTION_WITH_TWO_PARMS, prependError,
                paramError, paramError2);
        String msg = error.getMessage(Locale.US);
        assertEquals(PREPEND_MSG + MSG_WITH_PARAMS + APPEND_MSG, msg);
    }

    public void testErrorWithThreeParamsExpanded() {
        Error prependError = ErrorFactory.createError(TAG_PREPEND);
        String prependMsg = prependError.getMessage(Locale.US);
        Error paramError = ErrorFactory.createError(TAG_APPEND);
        String paramMsg = paramError.getMessage(Locale.US);
        Error paramError2 = ErrorFactory.createError(TAG_APPEND);
        String paramMsg2 = paramError2.getMessage(Locale.US);
        Error error = ErrorFactory.createError(TAG_QUESTION_WITH_TWO_PARMS, prependMsg,
                paramMsg, paramMsg2);
        String msg = error.getMessage(Locale.US);
        assertEquals(PREPEND_MSG + MSG_WITH_PARAMS + APPEND_MSG, msg);
    }
}