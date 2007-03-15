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

import org.apache.cactus.WebRequest;
import org.apache.cactus.WebResponse;
import org.megatome.frame2.taglib.html.Constants;
import org.megatome.frame2.tagsupport.TagConstants;

public class TestOptionTag extends BaseHtmlTagTest {

	public TestOptionTag(String theName) {
		super(theName);
		this._type = TagConstants.QUOTE + Constants.OPTION + TagConstants.QUOTE;
		this._testJspName = "OptionTag.jsp"; //$NON-NLS-1$
		this._expectedLiveJsp = "<select name=\"select\">" + //$NON-NLS-1$
				"<option value=\"value\">" + //$NON-NLS-1$
				"displayValue</option>" + //$NON-NLS-1$
				"</select>"; //$NON-NLS-1$
	}

	@Override
	public BaseHtmlTag createTag() {
		return new OptionTag();
	}

	public void beginOptionTag(@SuppressWarnings("unused")
	WebRequest rquest) throws Exception {
		// noop
	}

	public void testOptionTag() throws Exception {
		OptionTag tag = (OptionTag) createTag();

		tag.setPageContext(this.pageContext);
		tag.doStartTag();
		tag.doAfterBody();
		tag.doEndTag();
	}

	public void endOptionTag(WebResponse webResponse) throws Exception {
		String expected = Constants.OPTION_TAG + TagConstants.RT_ANGLE;
		String actual = webResponse.getText();
		assertEquals(expected, actual);
	}

	public void testSelectedOptionTag() throws Exception {
		try {
			this.pageContext.forward(JSP_TEST_DIR + "OptionSelectedTag.jsp"); //$NON-NLS-1$
		} catch (Exception e) {
			fail("Unexpected failure"); //$NON-NLS-1$
		}
	}

	public void endSelectedOptionTag(WebResponse webResponse) throws Exception {
		String actual = webResponse.getText().trim();
		actual = actual.replaceAll("\r\n", ""); //$NON-NLS-1$ //$NON-NLS-2$

		String expected = "<select name=\"select\">" + //$NON-NLS-1$
				"<option selected value=\"value\">" + //$NON-NLS-1$
				"displayValue</option>" + //$NON-NLS-1$
				"</select>"; //$NON-NLS-1$
		assertEquals(expected, actual);
	}

	public void testXhtmlTerminator_withBody() throws Exception {
		OptionTag tag = (OptionTag) createTag();
		this.pageContext.setAttribute(Constants.XHTML_KEY, Constants.TRUE);
		tag.setPageContext(this.pageContext);
		tag.setValue("MyValue"); //$NON-NLS-1$
		tag.setDisplayvalue("MyDisplayvalue"); //$NON-NLS-1$
		tag.doStartTag();
		tag.doEndTag();
	}

	public void endXhtmlTerminator_withBody(WebResponse webResponse)
			throws Exception {
		String expected = "<option value=\"MyValue\">MyDisplayvalue</option>"; //$NON-NLS-1$
		String actual = webResponse.getText();
		assertEquals(expected, actual);
	}

	public void testXhtmlTerminator_withoutBody() throws Exception {
		OptionTag tag = (OptionTag) createTag();
		this.pageContext.setAttribute(Constants.XHTML_KEY, Constants.TRUE);
		tag.setPageContext(this.pageContext);
		tag.setValue("MyValue"); //$NON-NLS-1$
		tag.doStartTag();
		tag.doEndTag();
	}

	public void endXhtmlTerminator_withoutBody(WebResponse webResponse)
			throws Exception {
		String expected = "<option value=\"MyValue\"/>"; //$NON-NLS-1$
		String actual = webResponse.getText();
		assertEquals(expected, actual);
	}

}
