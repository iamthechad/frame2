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

import javax.servlet.jsp.JspException;

import org.megatome.frame2.taglib.template.TemplateConstants;
import org.megatome.frame2.taglib.template.TemplateHelper;
import org.megatome.frame2.tagsupport.BaseFrame2Tag;
import org.megatome.frame2.template.TemplateConfigFactory;
import org.megatome.frame2.template.TemplateException;
import org.megatome.frame2.template.config.TemplateDef;

public class TestGetTag extends BaseTemplateTagTest {
	private static final String TEMPLATE1 = "template1"; //$NON-NLS-1$

	GetTag getTag = null;

	/**
	 * @param arg0
	 */
	public TestGetTag(String arg0) {
		super(arg0);
		this._testJspName = "GetTag.jsp"; //$NON-NLS-1$
		this._testNegativeJspName = "NegativeGetTag.jsp"; //$NON-NLS-1$
		this._expectedLiveJsp = "Header2.jsp<br>"; //$NON-NLS-1$
		this._expectedNegativeLiveJsp = "Could not access entry 'dude' for template \"template_negativeget_name\""; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.megatome.frame2.taglib.template.el.BaseTemplateTagTest#createTag()
	 */
	@Override
	public BaseFrame2Tag createTag() {
		return new GetTag();
	}

	public void testGetTag() {
		this.getTag.setName("some.jsp"); //$NON-NLS-1$

		assertEquals("some.jsp", this.getTag.getName()); //$NON-NLS-1$
	}

	public void testGetTagWithDef() {
		this.getTag.setName("header"); //$NON-NLS-1$
		try {
			TemplateDef def = TemplateConfigFactory.instance().getDefinition(
					"template1"); //$NON-NLS-1$
			assertNotNull(def);
			this.pageContext.setAttribute(TemplateConstants.FRAME2_INSERT_KEY,
					def);

			this.getTag.setPageContext(this.pageContext);

			this.getTag.doStartTag();
		} catch (TemplateException e) {
			fail();
		} catch (JspException e) {
			fail();
		}
	}

	public void testNegativeGetTagIsChild() {
		this.getTag.setName("some.jsp"); //$NON-NLS-1$

		assertEquals("some.jsp", this.getTag.getName()); //$NON-NLS-1$

		try {
			this.getTag.setPageContext(this.pageContext);
			this.getTag.doStartTag();
			fail();
		} catch (JspException expected) {
			// expected
		}
	}

	public void testNegativeGetTagNoTemplate() {
		this.getTag.setName("some.jsp"); //$NON-NLS-1$

		assertEquals("some.jsp", this.getTag.getName()); //$NON-NLS-1$

		InsertTag parent = new InsertTag();
		parent.setDefinition("definition"); //$NON-NLS-1$

		this.getTag.setParent(parent);
		this.getTag.setPageContext(this.pageContext);

		try {
			this.getTag.doStartTag();
			fail();
		} catch (JspException expected) {
			// expected
		}
	}

	public void testNegativeGetTag() {

		InsertTag insertTag = new InsertTag();
		insertTag.setDefinition("template1"); //$NON-NLS-1$

		this.getTag.setName("bar-none"); //$NON-NLS-1$

		this.getTag.setParent(insertTag);
		this.getTag.setPageContext(this.pageContext);
		try {
			this.getTag.doStartTag();
			fail();
		} catch (JspException expected) {
			// expected
		}
	}

	public void testNegativeGetTagNoDefinition() {

		InsertTag insertTag = new InsertTag();
		insertTag.setDefinition("nothing"); //$NON-NLS-1$

		this.getTag.setName("bar-put"); //$NON-NLS-1$

		this.getTag.setParent(insertTag);
		this.getTag.setPageContext(this.pageContext);
		try {
			this.getTag.doStartTag();
			fail();
		} catch (JspException expected) {
			// expected
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	public void setUp() throws Exception {
		super.setUp();
		TemplateHelper.clearPageContextDefinition(this.pageContext, TEMPLATE1);
		this.getTag = new GetTag();
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		TemplateHelper.clearPageContextDefinition(this.pageContext, TEMPLATE1);
	}

}
