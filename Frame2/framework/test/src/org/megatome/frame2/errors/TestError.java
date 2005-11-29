package org.megatome.frame2.errors;

import java.util.Locale;

import junit.framework.TestCase;

import org.megatome.frame2.errors.Error;
import org.megatome.frame2.errors.impl.ErrorImpl;
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
        Error error1 = new ErrorImpl(TAG_PREPEND);
        Error error2 = new ErrorImpl(TAG_PREPEND);
        Error error3 = new ErrorImpl(TAG_PREPEND);
        Error error4 = new ErrorImpl(TAG_PREPEND, VALUE1);
        Error error5 = new ErrorImpl(TAG_PREPEND, VALUE1, VALUE2);
        Error error6 = new ErrorImpl(TAG_PREPEND, VALUE1, VALUE2, VALUE3);
        Error error7 = new ErrorImpl(DIFFERENT_TAG);

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
        Error error1 = new ErrorImpl(TAG_PREPEND, VALUE1);
        Error error2 = new ErrorImpl(TAG_PREPEND, VALUE1);
        Error error3 = new ErrorImpl(TAG_PREPEND, VALUE1);
        Error error4 = new ErrorImpl(TAG_PREPEND);
        Error error5 = new ErrorImpl(TAG_PREPEND, VALUE1, VALUE2);
        Error error6 = new ErrorImpl(TAG_PREPEND, VALUE1, VALUE2, VALUE3);
        Error error7 = new ErrorImpl(DIFFERENT_TAG, VALUE1);
        Error error8 = new ErrorImpl(TAG_PREPEND, DIFFVALUE);

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
        Error error1 = new ErrorImpl(TAG_PREPEND, VALUE1, VALUE2);
        Error error2 = new ErrorImpl(TAG_PREPEND, VALUE1, VALUE2);
        Error error3 = new ErrorImpl(TAG_PREPEND, VALUE1, VALUE2);
        Error error4 = new ErrorImpl(TAG_PREPEND, VALUE1);
        Error error5 = new ErrorImpl(TAG_PREPEND);
        Error error6 = new ErrorImpl(TAG_PREPEND, VALUE1, VALUE2, VALUE3);
        Error error7 = new ErrorImpl(DIFFERENT_TAG, VALUE1, VALUE2);
        Error error8 = new ErrorImpl(TAG_PREPEND, DIFFVALUE, VALUE2);
        Error error9 = new ErrorImpl(TAG_PREPEND, VALUE1, DIFFVALUE);

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
        Error error1 = new ErrorImpl(TAG_PREPEND, VALUE1, VALUE2, VALUE3);
        Error error2 = new ErrorImpl(TAG_PREPEND, VALUE1, VALUE2, VALUE3);
        Error error3 = new ErrorImpl(TAG_PREPEND, VALUE1, VALUE2, VALUE3);
        Error error4 = new ErrorImpl(TAG_PREPEND, VALUE1);
        Error error5 = new ErrorImpl(TAG_PREPEND, VALUE1, VALUE2);
        Error error6 = new ErrorImpl(TAG_PREPEND);
        Error error7 = new ErrorImpl(DIFFERENT_TAG, VALUE1, VALUE2, VALUE3);
        Error error8 = new ErrorImpl(TAG_PREPEND, DIFFVALUE, VALUE2, VALUE3);
        Error error9 = new ErrorImpl(TAG_PREPEND, VALUE1, DIFFVALUE, VALUE3);
        Error error10 = new ErrorImpl(TAG_PREPEND, VALUE1, VALUE2, DIFFVALUE);

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
        Error error = new ErrorImpl(TAG_PREPEND);
        String msg = error.getMessage(Locale.US);
        assertEquals(PREPEND_MSG, msg);
    }

    public void testErrorWithOneParam() {
        Error prependError = new ErrorImpl(TAG_PREPEND);
        //String prependMsg = prependError.getMessage(Locale.US);
        Error error = new ErrorImpl(TAG_QUESTION, prependError);
        String msg = error.getMessage(Locale.US);
        assertEquals(PREPEND_MSG + FRAME2_MSG, msg);
    }

    public void testErrorWithOneParamExpanded() {
        Error prependError = new ErrorImpl(TAG_PREPEND);
        String prependMsg = prependError.getMessage(Locale.US);
        Error error = new ErrorImpl(TAG_QUESTION, prependMsg);
        String msg = error.getMessage(Locale.US);
        assertEquals(PREPEND_MSG + FRAME2_MSG, msg);
    }

    public void testErrorWithTwoParams() {
        Error prependError = new ErrorImpl(TAG_PREPEND);
        Error paramError = new ErrorImpl(TAG_APPEND);
        Error error = new ErrorImpl(TAG_QUESTION_WITH_PARM, prependError,
                paramError);
        String msg = error.getMessage(Locale.US);
        assertEquals(PREPEND_MSG + MSG_WITH_PARAMS, msg);
    }

    public void testErrorWithTwoParamsExpanded() {
        Error prependError = new ErrorImpl(TAG_PREPEND);
        String prependMsg = prependError.getMessage(Locale.US);
        Error paramError = new ErrorImpl(TAG_APPEND);
        String paramMsg = paramError.getMessage(Locale.US);
        Error error = new ErrorImpl(TAG_QUESTION_WITH_PARM, prependMsg,
                paramMsg);
        String msg = error.getMessage(Locale.US);
        assertEquals(PREPEND_MSG + MSG_WITH_PARAMS, msg);
    }

    public void testErrorWithThreeParams() {
        Error prependError = new ErrorImpl(TAG_PREPEND);
        Error paramError = new ErrorImpl(TAG_APPEND);
        Error paramError2 = new ErrorImpl(TAG_APPEND);
        Error error = new ErrorImpl(TAG_QUESTION_WITH_TWO_PARMS, prependError,
                paramError, paramError2);
        String msg = error.getMessage(Locale.US);
        assertEquals(PREPEND_MSG + MSG_WITH_PARAMS + APPEND_MSG, msg);
    }

    public void testErrorWithThreeParamsExpanded() {
        Error prependError = new ErrorImpl(TAG_PREPEND);
        String prependMsg = prependError.getMessage(Locale.US);
        Error paramError = new ErrorImpl(TAG_APPEND);
        String paramMsg = paramError.getMessage(Locale.US);
        Error paramError2 = new ErrorImpl(TAG_APPEND);
        String paramMsg2 = paramError2.getMessage(Locale.US);
        Error error = new ErrorImpl(TAG_QUESTION_WITH_TWO_PARMS, prependMsg,
                paramMsg, paramMsg2);
        String msg = error.getMessage(Locale.US);
        assertEquals(PREPEND_MSG + MSG_WITH_PARAMS + APPEND_MSG, msg);
    }
}