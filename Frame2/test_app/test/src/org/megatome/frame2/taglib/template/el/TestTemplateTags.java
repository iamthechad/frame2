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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.cactus.JspTestCase;
import org.megatome.frame2.taglib.template.TemplateConstants;
import org.megatome.frame2.taglib.template.TemplateHelper;
import org.megatome.frame2.template.TemplateConfigFactory;
import org.megatome.frame2.template.TemplateException;
import org.megatome.frame2.template.TemplatePlugin;
import org.megatome.frame2.template.config.TemplateDefI;

public class TestTemplateTags extends JspTestCase {

	private InsertTag _insertTag;

	private TemplateDefI _def = null;

	static private String TEMPLATE2 = "template2"; //$NON-NLS-1$

	/**
	 * @param arg0
	 */
	public TestTemplateTags(String arg0) {
		super(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		TemplateHelper.clearPageContextDefinition(this.pageContext, TEMPLATE2);
		this._insertTag = new InsertTag();
		this._insertTag.setDefinition(TEMPLATE2);
		this._insertTag.setPageContext(this.pageContext);
	}

	private void configureTemplateDef() {
		try {
			this._def = TemplateConfigFactory.instance().getDefinition(
					TEMPLATE2);
			assertNotNull(this._def);
		} catch (TemplateException e) {
			fail();
		}
	}

	public void testNoOverrides() {
		try {
			this._insertTag.doStartTag();
			assertNotNull(this.pageContext.getAttribute(
					TemplateConstants.FRAME2_INSERT_KEY,
					PageContext.REQUEST_SCOPE));
			this._insertTag.doEndTag();

			configureTemplateDef();
			String getValue = getParameterValue("header"); //$NON-NLS-1$

			assertNotNull(getValue);
			assertEquals(
					TemplatePlugin.getConfigDir() + "header2.jsp", getValue); //$NON-NLS-1$

			getValue = getParameterValue("nav"); //$NON-NLS-1$

			assertNotNull(getValue);
			assertEquals(TemplatePlugin.getConfigDir() + "nav2.jsp", getValue); //$NON-NLS-1$

			getValue = getParameterValue("footer"); //$NON-NLS-1$

			assertNotNull(getValue);
			assertEquals(
					TemplatePlugin.getConfigDir() + "footer2.jsp", getValue); //$NON-NLS-1$

			this._insertTag.doFinally();
			assertNull(this.pageContext.getAttribute(
					TemplateConstants.FRAME2_INSERT_KEY,
					PageContext.REQUEST_SCOPE));
			assertNull(this.pageContext
					.findAttribute(TemplateConstants.FRAME2_INSERT_KEY));
		} catch (JspException e) {
			fail();
		}

	}

	@SuppressWarnings("unchecked")
	public void testDefaultPutScope() {
		try {
			this._insertTag.doStartTag();
			assertNotNull(this.pageContext.getAttribute(
					TemplateConstants.FRAME2_INSERT_KEY,
					PageContext.REQUEST_SCOPE));

			PutTag putTag = new PutTag();
			putTag.setParent(this._insertTag);
			putTag.setPageContext(this.pageContext);

			putTag.setName("header"); //$NON-NLS-1$
			putTag.setPath("requestScope.jsp"); //$NON-NLS-1$

			try {
				putTag.doStartTag();
			} catch (JspException e) {
				fail();
			}
			this._insertTag.doEndTag();

			configureTemplateDef();

			Map<Object, Object> map = (HashMap) this.pageContext.getAttribute(TEMPLATE2,
					PageContext.REQUEST_SCOPE);
			assertNotNull(map);
			String value = (String) map.get("header"); //$NON-NLS-1$
			assertNotNull(value);
			assertEquals(value, "requestScope.jsp"); //$NON-NLS-1$

			String getValue = getParameterValue("header"); //$NON-NLS-1$

			assertNotNull(getValue);
			assertEquals(TemplatePlugin.getConfigDir() + "requestScope.jsp", //$NON-NLS-1$
					getValue);

			getValue = getParameterValue("nav"); //$NON-NLS-1$

			assertNotNull(getValue);
			assertEquals(TemplatePlugin.getConfigDir() + "nav2.jsp", getValue); //$NON-NLS-1$

			getValue = getParameterValue("footer"); //$NON-NLS-1$

			assertNotNull(getValue);
			assertEquals(
					TemplatePlugin.getConfigDir() + "footer2.jsp", getValue); //$NON-NLS-1$

			this._insertTag.doFinally();
			assertNull(this.pageContext.getAttribute(
					TemplateConstants.FRAME2_INSERT_KEY,
					PageContext.REQUEST_SCOPE));
			assertNull(this.pageContext
					.findAttribute(TemplateConstants.FRAME2_INSERT_KEY));
		} catch (JspException e) {
			fail();
		}

	}

	public void testParamsInScope() {

		try {
			this._insertTag.doStartTag();
			assertNotNull(this.pageContext.getAttribute(
					TemplateConstants.FRAME2_INSERT_KEY,
					PageContext.REQUEST_SCOPE));

			ParamTag paramTag = new ParamTag();
			paramTag.setParent(this._insertTag);
			paramTag.setPageContext(this.pageContext);

			paramTag.setName("paramName"); //$NON-NLS-1$
			paramTag.setValue("paramValue"); //$NON-NLS-1$
			try {
				paramTag.doStartTag();
				paramTag.doEndTag();
			} catch (JspException e) {
				fail();
			}
			this._insertTag.doEndTag();

			String[] value = (String[]) this.pageContext
					.findAttribute("paramName"); //$NON-NLS-1$

			assertNotNull(value);
			assertEquals("paramValue", value[0]); //$NON-NLS-1$
			this._insertTag.doFinally();
			assertNull(this.pageContext.getAttribute(
					TemplateConstants.FRAME2_INSERT_KEY,
					PageContext.REQUEST_SCOPE));
			assertNull(this.pageContext
					.findAttribute(TemplateConstants.FRAME2_INSERT_KEY));
		} catch (JspException e) {
			fail();
		}
	}

	@SuppressWarnings("null")
	private String getParameterValue(String name) {

		String insertDef = this._insertTag.getDefinition();
		TemplateDefI def = null;
		try {
			def = TemplateConfigFactory.instance().getDefinition(insertDef);
		} catch (TemplateException e) {
			// We'll throw an error in just a minute, so an empty catch is OK
		}

		if (def == null) {
			fail();
		}

		return def.getPutParam(name, this.pageContext);
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		TemplateHelper.clearPageContextDefinition(this.pageContext, TEMPLATE2);
	}

}
