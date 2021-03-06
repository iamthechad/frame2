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
package org.megatome.frame2.taglib.html.el;

import java.util.ArrayList;
import java.util.List;

import org.apache.cactus.JspTestCase;
import org.apache.cactus.WebRequest;
import org.apache.cactus.WebResponse;
import org.megatome.frame2.taglib.html.Constants;
import org.megatome.frame2.tagsupport.util.HTMLHelpers;

public class TestOptionsTag extends JspTestCase {

	String _testJspName;
	String _expectedLiveJsp;
	static final String JSP_TEST_DIR = "/test/tags/"; //$NON-NLS-1$
	String _testMultJspName;

	public TestOptionsTag(String name) {
		super(name);
		this._testJspName = "OptionsTag.jsp"; //$NON-NLS-1$
		this._testMultJspName = "OptionsMultTag.jsp"; //$NON-NLS-1$
		this._expectedLiveJsp = "<option value=\"one\">one</option>" + //$NON-NLS-1$
				"<option value=\"two\">two</option>" + //$NON-NLS-1$
				"<option value=\"radioValue\">radioValue</option>" + //$NON-NLS-1$
				"<option value=\"me\">me</option>"; //$NON-NLS-1$
	}

	public BaseHtmlTag createTag() {
		return new OptionsTag();
	}

	public void beginOptionsTag(@SuppressWarnings("unused")
	WebRequest rquest) throws Exception {
		// noop
	}

	public void testOptionsTagList() throws Exception {
		OptionsTag tag = (OptionsTag) createTag();
		List<String> valueList = new ArrayList<String>();
		List<String> displayValueList = new ArrayList<String>();
		valueList.add("one"); //$NON-NLS-1$
		valueList.add("two"); //$NON-NLS-1$
		displayValueList.add("me"); //$NON-NLS-1$
		displayValueList.add("you"); //$NON-NLS-1$
		this.pageContext.setAttribute(Constants.VALUE, valueList);
		this.pageContext
				.setAttribute(Constants.DISPLAY_VALUE, displayValueList);
		tag.setPageContext(this.pageContext);
		tag.setValue(HTMLHelpers.buildExprAttr(Constants.VALUE));
		tag.setDisplayvalue(HTMLHelpers.buildExprAttr(Constants.DISPLAY_VALUE));
		tag.doStartTag();
		tag.doEndTag();

	}

	public void endOptionsTagList(WebResponse webResponse) throws Exception {
		String expected = Constants.OPTION_TAG
				+ " value=\"one\">me" + Constants.OPTION_CLOSE //$NON-NLS-1$
				+ Constants.OPTION_TAG
				+ " value=\"two\">you" + Constants.OPTION_CLOSE; //$NON-NLS-1$
		String actual = webResponse.getText();

		assertEquals(expected, actual);
	}

	public void testOptionsTagStringArray() throws Exception {
		OptionsTag tag = (OptionsTag) createTag();
		String[] valueArr = { "one", "two" }; //$NON-NLS-1$ //$NON-NLS-2$
		String[] displayValueArr = { "me", "you" }; //$NON-NLS-1$ //$NON-NLS-2$
		this.pageContext.setAttribute(Constants.VALUE, valueArr);
		this.pageContext.setAttribute(Constants.DISPLAY_VALUE, displayValueArr);
		tag.setPageContext(this.pageContext);
		tag.setValue(HTMLHelpers.buildExprAttr(Constants.VALUE));
		tag.setDisplayvalue(HTMLHelpers.buildExprAttr(Constants.DISPLAY_VALUE));
		tag.doStartTag();
		tag.doEndTag();

	}

	public void endOptionsTagStringArray(WebResponse webResponse)
			throws Exception {
		String expected = Constants.OPTION_TAG
				+ " value=\"one\">me" + Constants.OPTION_CLOSE //$NON-NLS-1$
				+ Constants.OPTION_TAG
				+ " value=\"two\">you" + Constants.OPTION_CLOSE; //$NON-NLS-1$
		String actual = webResponse.getText();

		assertEquals(expected, actual);
	}

	public void testXhtmlTerminator_withBody() throws Exception {
		OptionsTag tag = (OptionsTag) createTag();
		List<String> valueList = new ArrayList<String>();
		List<String> displayValueList = new ArrayList<String>();
		valueList.add("one"); //$NON-NLS-1$
		valueList.add("two"); //$NON-NLS-1$
		displayValueList.add("me"); //$NON-NLS-1$
		displayValueList.add("you"); //$NON-NLS-1$
		this.pageContext.setAttribute(Constants.VALUE, valueList);
		this.pageContext
				.setAttribute(Constants.DISPLAY_VALUE, displayValueList);
		this.pageContext.setAttribute(Constants.XHTML_KEY, Constants.TRUE);
		tag.setPageContext(this.pageContext);
		tag.setValue(HTMLHelpers.buildExprAttr(Constants.VALUE));
		tag.setDisplayvalue(HTMLHelpers.buildExprAttr(Constants.DISPLAY_VALUE));
		tag.doStartTag();
		tag.doEndTag();

	}

	public void endXhtmlTerminator_withBody(WebResponse webResponse)
			throws Exception {
		String expected = Constants.OPTION_TAG
				+ " value=\"one\">me" + Constants.OPTION_CLOSE //$NON-NLS-1$
				+ Constants.OPTION_TAG
				+ " value=\"two\">you" + Constants.OPTION_CLOSE; //$NON-NLS-1$
		String actual = webResponse.getText();

		assertEquals(expected, actual);
	}

	public void testXhtmlTerminator_withoutBody() throws Exception {
		OptionsTag tag = (OptionsTag) createTag();
		List<String> valueList = new ArrayList<String>();
		valueList.add("one"); //$NON-NLS-1$
		valueList.add("two"); //$NON-NLS-1$
		this.pageContext.setAttribute(Constants.VALUE, valueList);
		this.pageContext.setAttribute(Constants.XHTML_KEY, Constants.TRUE);
		tag.setPageContext(this.pageContext);
		tag.setValue(HTMLHelpers.buildExprAttr(Constants.VALUE));
		tag.doStartTag();
		tag.doEndTag();

	}

	public void endXhtmlTerminator_withoutBody(WebResponse webResponse)
			throws Exception {
		String expected = Constants.OPTION_TAG + " value=\"one\">one" + //$NON-NLS-1$
				Constants.OPTION_CLOSE +
				Constants.OPTION_TAG + " value=\"two\">two" + //$NON-NLS-1$
				Constants.OPTION_CLOSE;
		String actual = webResponse.getText();

		assertEquals(expected, actual);
	}

	// Test out tag in a real live jsp.
	public void testLiveJsp() throws Exception {
		this.pageContext.forward(JSP_TEST_DIR + this._testJspName);
	}

	public void endLiveJsp(WebResponse webResponse) throws Exception {
		String actual = webResponse.getText().trim();

		assertEquals(this._expectedLiveJsp, actual);

	}

	/*
	 * This test verifies a bug fix. The bug involved the options tag not
	 * resetting content and having it available to subsequent uses of the tag.
	 */
	public void testNegativeLiveJsp() throws Exception {

		try {
			this.pageContext.forward(JSP_TEST_DIR + this._testMultJspName);
			fail("Second options tag should throw exception"); //$NON-NLS-1$
		} catch (Exception e) {
			assertNotNull(e.getCause());
			assertTrue(e.getCause().getMessage().indexOf("Evaluation attribute failed") != -1); //$NON-NLS-1$
		}
	}

	/*
	 * This test verifies a bug fix. The bug involved the value and displayvalue
	 * attributes only accepting String values.
	 */
	public void testLiveJspNonStringCollections() throws Exception {

		try {
			this.pageContext.forward(JSP_TEST_DIR + "OptionsTagIntValues.jsp"); //$NON-NLS-1$
		} catch (Exception e) {
			fail("Options tag failed to parse collection of non-String values"); //$NON-NLS-1$
		}
	}

	public void endLiveJspNonStringCollections(WebResponse webResponse)
			throws Exception {
		String actual = webResponse.getText().trim();
		actual = actual.replaceAll("\r\n", ""); //$NON-NLS-1$ //$NON-NLS-2$

		String expected = "<option value=\"1\">one</option>" + //$NON-NLS-1$
				"<option value=\"2\">two</option>" + //$NON-NLS-1$
				"<option value=\"3\">three</option>"; //$NON-NLS-1$
		assertEquals(expected, actual);

	}

	/*
	 * This test verifies a bug fix. The bug involved the selected attribute for
	 * single values not being emitted, and being constructed incorrectly.
	 */
	public void testLiveJspSelectedValue() throws Exception {
		try {
			this.pageContext.forward(JSP_TEST_DIR
					+ "OptionsTagSelectedValue.jsp"); //$NON-NLS-1$
		} catch (Exception e) {
			fail("Unexpected error parsing tag"); //$NON-NLS-1$
		}
	}

	public void endLiveJspSelectedValue(WebResponse webResponse)
			throws Exception {
		String actual = webResponse.getText().trim();
		actual = actual.replaceAll("\r\n", ""); //$NON-NLS-1$ //$NON-NLS-2$

		String expected = "<option value=\"1\">one</option>" + //$NON-NLS-1$
				"<option selected value=\"2\">two</option>" + //$NON-NLS-1$
				"<option value=\"3\">three</option>"; //$NON-NLS-1$
		assertEquals(expected, actual);
	}

	public void testLiveJspNullValues() throws Exception {
		try {
			this.pageContext.forward(JSP_TEST_DIR
					+ "OptionsTagNullValues.jsp"); //$NON-NLS-1$
			fail("Did not catch expected exception"); //$NON-NLS-1$
		} catch (Exception expected) {
			assertTrue(expected.getMessage().indexOf("TLD") != -1); //$NON-NLS-1$
		}
	}
	
	public void testLiveJspNullDisplayValues() throws Exception {
		try {
			this.pageContext.forward(JSP_TEST_DIR
					+ "OptionsTagNullDisplayValues.jsp"); //$NON-NLS-1$
		} catch (Exception e) {
			fail("Unexpected exception"); //$NON-NLS-1$
		}
	}
	
	public void endLiveJspNullDisplayValues(WebResponse webResponse)
	throws Exception {
		String actual = webResponse.getText().trim();

		assertEquals(this._expectedLiveJsp, actual);
	}


	public void testLiveJspSelectedCollectionValue() throws Exception {
		try {
			this.pageContext.forward(JSP_TEST_DIR
					+ "OptionsTagSelectedCollectionValue.jsp"); //$NON-NLS-1$
		} catch (Exception e) {
			fail("Unexpected error parsing tag: " + e.getMessage()); //$NON-NLS-1$
		}
	}

	public void endLiveJspSelectedCollectionValue(WebResponse webResponse)
			throws Exception {
		String actual = webResponse.getText().trim();
		actual = actual.replaceAll("\r\n", ""); //$NON-NLS-1$ //$NON-NLS-2$

		String expected = "<option value=\"1\">one</option>" + //$NON-NLS-1$
				"<option selected value=\"2\">two</option>" + //$NON-NLS-1$
				"<option selected value=\"3\">three</option>"; //$NON-NLS-1$
		assertEquals(expected, actual);
	}

	public void testLiveJspSelectedCollectionValue2() throws Exception {
		try {
			this.pageContext.forward(JSP_TEST_DIR
					+ "OptionsTagSelectedCollectionValue2.jsp"); //$NON-NLS-1$
		} catch (Exception e) {
			fail("Unexpected error parsing tag: " + e.getMessage()); //$NON-NLS-1$
		}
	}

	public void endLiveJspSelectedCollectionValue2(WebResponse webResponse)
			throws Exception {
		String actual = webResponse.getText().trim();
		actual = actual.replaceAll("\r\n", ""); //$NON-NLS-1$ //$NON-NLS-2$

		String expected = "<option value=\"1\">one</option>" + //$NON-NLS-1$
				"<option selected value=\"2\">two</option>" + //$NON-NLS-1$
				"<option selected value=\"3\">three</option>"; //$NON-NLS-1$
		assertEquals(expected, actual);
	}

	public void testLiveJspSelectedArrayValue() throws Exception {
		try {
			this.pageContext.forward(JSP_TEST_DIR
					+ "OptionsTagSelectedArrayValue.jsp"); //$NON-NLS-1$
		} catch (Exception e) {
			fail("Unexpected error parsing tag: " + e.getMessage()); //$NON-NLS-1$
		}
	}

	public void endLiveJspSelectedArrayValue(WebResponse webResponse)
			throws Exception {
		String actual = webResponse.getText().trim();
		actual = actual.replaceAll("\r\n", ""); //$NON-NLS-1$ //$NON-NLS-2$

		String expected = "<option value=\"1\">one</option>" + //$NON-NLS-1$
				"<option selected value=\"2\">two</option>" + //$NON-NLS-1$
				"<option selected value=\"3\">three</option>"; //$NON-NLS-1$
		assertEquals(expected, actual);
	}

	public void testLiveJspSelectedArrayValue2() throws Exception {
		try {
			this.pageContext.forward(JSP_TEST_DIR
					+ "OptionsTagSelectedArrayValue2.jsp"); //$NON-NLS-1$
		} catch (Exception e) {
			fail("Unexpected error parsing tag: " + e.getMessage()); //$NON-NLS-1$
		}
	}

	public void endLiveJspSelectedArrayValue2(WebResponse webResponse)
			throws Exception {
		String actual = webResponse.getText().trim();
		actual = actual.replaceAll("\r\n", ""); //$NON-NLS-1$ //$NON-NLS-2$

		String expected = "<option value=\"1\">one</option>" + //$NON-NLS-1$
				"<option selected value=\"2\">two</option>" + //$NON-NLS-1$
				"<option selected value=\"3\">three</option>"; //$NON-NLS-1$
		assertEquals(expected, actual);
	}

}
