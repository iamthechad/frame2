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
package org.megatome.frame2.taglib.template.el;

import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.cactus.WebResponse;
import org.megatome.frame2.tagsupport.BaseFrame2Tag;

public class TestParamTag extends BaseTemplateTagTest {

	ParamTag paramTag = null;

	/**
	 * @param arg0
	 */
	public TestParamTag(String arg0) {
		super(arg0);
		this._testJspName = "ParamTag.jsp"; //$NON-NLS-1$
		this._testNegativeJspName = "NegativeParamTag.jsp"; //$NON-NLS-1$
		this._expectedLiveJsp = "value<br>"; //$NON-NLS-1$
		this._expectedNegativeLiveJsp = "Parameter name in param cannot be empty"; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	public void setUp() throws Exception {
		super.setUp();
		this.paramTag = new ParamTag();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testParamTag() {
		this.paramTag.setName("paramName"); //$NON-NLS-1$
		this.paramTag.setValue("paramValue"); //$NON-NLS-1$

		assertEquals("paramName", this.paramTag.getName()); //$NON-NLS-1$
		assertEquals("paramValue", this.paramTag.getValue()); //$NON-NLS-1$

		InsertTag parent = new InsertTag();

		this.paramTag.setParent(parent);

		try {
			this.paramTag.doStartTag();
		} catch (JspException e) {
			fail();
		}

		Map<String, String[]> inputParams = parent.getParameterMap();
		assertNotNull(inputParams);
		assertTrue(inputParams.containsKey("paramName")); //$NON-NLS-1$
		assertEquals("paramValue", parent.getParameter("paramName")); //$NON-NLS-1$ //$NON-NLS-2$

		String[] paramValues = parent.getParameterValues("paramName"); //$NON-NLS-1$
		assertNotNull(paramValues);
		assertTrue(paramValues.length == 1);
	}

	public void testMultiParamTag() {
		this.paramTag.setName("paramName"); //$NON-NLS-1$
		this.paramTag.setValue("paramValue"); //$NON-NLS-1$

		assertEquals("paramName", this.paramTag.getName()); //$NON-NLS-1$
		assertEquals("paramValue", this.paramTag.getValue()); //$NON-NLS-1$

		ParamTag paramTag2 = new ParamTag();

		paramTag2.setName("paramName2"); //$NON-NLS-1$
		paramTag2.setValue("paramValue2"); //$NON-NLS-1$

		assertEquals("paramName2", paramTag2.getName()); //$NON-NLS-1$
		assertEquals("paramValue2", paramTag2.getValue()); //$NON-NLS-1$

		InsertTag parent = new InsertTag();

		this.paramTag.setParent(parent);
		paramTag2.setParent(parent);

		try {
			this.paramTag.doStartTag();
			paramTag2.doStartTag();
		} catch (JspException e) {
			fail();
		}

		Map<String, String[]> inputParams = parent.getParameterMap();
		assertNotNull(inputParams);
		assertTrue(inputParams.containsKey("paramName")); //$NON-NLS-1$
		assertEquals("paramValue", parent.getParameter("paramName")); //$NON-NLS-1$ //$NON-NLS-2$

		String[] paramValues = parent.getParameterValues("paramName"); //$NON-NLS-1$
		assertNotNull(paramValues);
		assertTrue(paramValues.length == 1);

		assertTrue(inputParams.containsKey("paramName2")); //$NON-NLS-1$
		assertEquals("paramValue2", parent.getParameter("paramName2")); //$NON-NLS-1$ //$NON-NLS-2$

		paramValues = parent.getParameterValues("paramName2"); //$NON-NLS-1$
		assertNotNull(paramValues);
		assertTrue(paramValues.length == 1);
	}

	public void testMultiValueParamTag() {
		this.paramTag.setName("paramName"); //$NON-NLS-1$
		this.paramTag.setValue("paramValue"); //$NON-NLS-1$

		assertEquals("paramName", this.paramTag.getName()); //$NON-NLS-1$
		assertEquals("paramValue", this.paramTag.getValue()); //$NON-NLS-1$

		ParamTag paramTag2 = new ParamTag();

		paramTag2.setName("paramName"); //$NON-NLS-1$
		paramTag2.setValue("paramValue2"); //$NON-NLS-1$

		assertEquals("paramName", paramTag2.getName()); //$NON-NLS-1$
		assertEquals("paramValue2", paramTag2.getValue()); //$NON-NLS-1$

		InsertTag parent = new InsertTag();

		this.paramTag.setParent(parent);
		paramTag2.setParent(parent);

		try {
			this.paramTag.doStartTag();
			paramTag2.doStartTag();
		} catch (JspException e) {
			fail();
		}

		Map<String, String[]> inputParams = parent.getParameterMap();
		assertNotNull(inputParams);
		assertTrue(inputParams.containsKey("paramName")); //$NON-NLS-1$
		assertEquals("paramValue", parent.getParameter("paramName")); //$NON-NLS-1$ //$NON-NLS-2$

		String[] paramValues = parent.getParameterValues("paramName"); //$NON-NLS-1$
		assertNotNull(paramValues);
		assertTrue(paramValues.length == 2);
		assertEquals(paramValues[0], "paramValue"); //$NON-NLS-1$
		assertEquals(paramValues[1], "paramValue2"); //$NON-NLS-1$
	}

	public void testParamTagIsChild() {
		this.paramTag.setName("paramName"); //$NON-NLS-1$
		this.paramTag.setValue("paramValue"); //$NON-NLS-1$

		assertEquals("paramName", this.paramTag.getName()); //$NON-NLS-1$
		assertEquals("paramValue", this.paramTag.getValue()); //$NON-NLS-1$

		InsertTag parent = new InsertTag();

		this.paramTag.setParent(parent);

		try {
			this.paramTag.doStartTag();
		} catch (JspException e) {
			fail();
		}
	}

	public void testNegativeParamTagIsChild() {
		this.paramTag.setName("paramName"); //$NON-NLS-1$
		this.paramTag.setValue("paramValue"); //$NON-NLS-1$

		assertEquals("paramName", this.paramTag.getName()); //$NON-NLS-1$
		assertEquals("paramValue", this.paramTag.getValue()); //$NON-NLS-1$

		try {
			this.paramTag.doStartTag();
			fail();
		} catch (JspException expected) {
			// expected
		}
	}

	// Test for creating a valid yahoo.quote with some params.
	public void testLiveJspMultiParams() throws Exception {
		this.pageContext.forward(JSP_TEST_DIR + "MultiParamTag.jsp"); //$NON-NLS-1$
	}

	public void endLiveJspMultiParams(WebResponse webResponse) throws Exception {
		String actual = webResponse.getText().trim();

		String expected = "value<br>value2<br>value3<br>"; //$NON-NLS-1$

		assertEquals(expected, actual);

	}

	// Test for creating a valid yahoo.quote with some params.
	public void testLiveJspMultiParamNames() throws Exception {
		this.pageContext.forward(JSP_TEST_DIR + "ParamTagMultiNames.jsp"); //$NON-NLS-1$
	}

	public void endLiveJspMultiParamNames(WebResponse webResponse)
			throws Exception {
		String actual = webResponse.getText().trim();
		actual = actual.replaceAll("\\n", "");
		actual = actual.replaceAll("\\r", "");
		String expected = "value<br>dude_value<br>"; //$NON-NLS-1$

		assertEquals(expected, actual);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.megatome.frame2.taglib.template.el.BaseTemplateTagTest#createTag()
	 */
	@Override
	public BaseFrame2Tag createTag() {
		return null;
	}

}
