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

import org.apache.cactus.JspTestCase;
import org.megatome.frame2.taglib.template.TemplateConstants;
import org.megatome.frame2.taglib.template.TemplateHelper;
import org.megatome.frame2.template.TemplateConfigFactory;
import org.megatome.frame2.template.TemplateException;
import org.megatome.frame2.template.TemplatePlugin;
import org.megatome.frame2.template.config.TemplateDefI;

public class TestTemplateScope extends JspTestCase {

	private InsertTag _insertTag;

	private TemplateDefI _def = null;

	private static final String TEMPLATE1 = "template1"; //$NON-NLS-1$

	/**
	 * @param arg0
	 */
	public TestTemplateScope(String arg0) {
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
		TemplateHelper.clearPageContextDefinition(this.pageContext, TEMPLATE1);
		this._insertTag = new InsertTag();
		this._insertTag.setDefinition(TEMPLATE1);
	}

	private void configureTemplateDef() {
		try {
			this._def = TemplateConfigFactory.instance().getDefinition(
					TEMPLATE1);
			assertNotNull(this._def);
			this.pageContext.setAttribute(TemplateConstants.FRAME2_INSERT_KEY,
					this._def);
		} catch (TemplateException e) {
			fail();
		}
	}

	public void testNoOverrides() {
		configureTemplateDef();
		String getValue = getParameterValue("header"); //$NON-NLS-1$

		assertNotNull(getValue);
		assertEquals(TemplatePlugin.getConfigDir() + "yyy.jsp", getValue); //$NON-NLS-1$

		getValue = getParameterValue("nav"); //$NON-NLS-1$

		assertNotNull(getValue);
		assertEquals(TemplatePlugin.getConfigDir() + "yyy.jsp", getValue); //$NON-NLS-1$

		getValue = getParameterValue("footer"); //$NON-NLS-1$

		assertNotNull(getValue);
		assertEquals(TemplatePlugin.getConfigDir() + "yyy.jsp", getValue); //$NON-NLS-1$
	}

	public void testDefaultPutScope() {
		configureTemplateDef();
		PutTag putTag = new PutTag();
		putTag.setParent(this._insertTag);
		putTag.setPageContext(this.pageContext);

		putTag.setName("header"); //$NON-NLS-1$
		putTag.setPath("pageScope"); //$NON-NLS-1$

		try {
			putTag.doStartTag();
		} catch (JspException e) {
			fail();
		}

		String getValue = getParameterValue("header"); //$NON-NLS-1$

		assertNotNull(getValue);
		assertEquals(TemplatePlugin.getConfigDir() + "pageScope", getValue); //$NON-NLS-1$
	}

	public void testBadScope() {
		configureTemplateDef();
		PutTag putTag = new PutTag();
		putTag.setParent(this._insertTag);
		putTag.setPageContext(this.pageContext);

		putTag.setName("header"); //$NON-NLS-1$
		putTag.setPath("pageScope"); //$NON-NLS-1$
		putTag.setScope("badScope"); //$NON-NLS-1$

		try {
			putTag.doStartTag();
			fail();
		} catch (JspException expected) {
			// expected
		}
	}

	public void testNegativeOverrideAtPageScope() {
		configureTemplateDef();
		PutTag putTag = new PutTag();
		putTag.setParent(this._insertTag);
		putTag.setPageContext(this.pageContext);

		putTag.setName("header"); //$NON-NLS-1$
		putTag.setPath("pageScope"); //$NON-NLS-1$
		putTag.setScope("page"); //$NON-NLS-1$

		try {
			putTag.doStartTag();
			fail();
		} catch (JspException expected) {
			// expected
		}
	}

	public void testOverrideAtRequestScope() {
		configureTemplateDef();
		PutTag putTag = new PutTag();
		putTag.setParent(this._insertTag);
		putTag.setPageContext(this.pageContext);

		putTag.setName("header"); //$NON-NLS-1$
		putTag.setPath("requestScope"); //$NON-NLS-1$
		putTag.setScope("request"); //$NON-NLS-1$

		try {
			putTag.doStartTag();
		} catch (JspException e) {
			fail();
		}

		String getValue = getParameterValue("header"); //$NON-NLS-1$

		assertNotNull(getValue);
		assertEquals(TemplatePlugin.getConfigDir() + "requestScope", getValue); //$NON-NLS-1$
	}

	public void testOverrideAtSessionScope() {
		configureTemplateDef();
		PutTag putTag = new PutTag();
		putTag.setParent(this._insertTag);
		putTag.setPageContext(this.pageContext);

		putTag.setName("header"); //$NON-NLS-1$
		putTag.setPath("sessionScope"); //$NON-NLS-1$
		putTag.setScope("session"); //$NON-NLS-1$

		try {
			putTag.doStartTag();
		} catch (JspException e) {
			fail();
		}

		String getValue = getParameterValue("header"); //$NON-NLS-1$

		assertNotNull(getValue);
		assertEquals(TemplatePlugin.getConfigDir() + "sessionScope", getValue); //$NON-NLS-1$
	}

	public void testOverrideRequestSessionScope() {
		configureTemplateDef();
		PutTag putTag1 = new PutTag();
		putTag1.setParent(this._insertTag);
		putTag1.setPageContext(this.pageContext);

		putTag1.setName("header"); //$NON-NLS-1$
		putTag1.setPath("pageScope"); //$NON-NLS-1$
		putTag1.setScope("request"); //$NON-NLS-1$

		PutTag putTag2 = new PutTag();
		putTag2.setParent(this._insertTag);
		putTag2.setPageContext(this.pageContext);

		putTag2.setName("footer"); //$NON-NLS-1$
		putTag2.setPath("sessionScope"); //$NON-NLS-1$
		putTag2.setScope("session"); //$NON-NLS-1$

		try {
			putTag1.doStartTag();
			putTag2.doStartTag();
		} catch (JspException e) {
			fail();
		}

		String getHeaderValue = getParameterValue("header"); //$NON-NLS-1$
		String getFooterValue = getParameterValue("footer"); //$NON-NLS-1$

		assertNotNull(getHeaderValue);
		assertEquals(
				TemplatePlugin.getConfigDir() + "pageScope", getHeaderValue); //$NON-NLS-1$

		assertNotNull(getFooterValue);
		assertEquals(TemplatePlugin.getConfigDir() + "sessionScope", //$NON-NLS-1$
				getFooterValue);
	}

	public void testApplicationScope() {
		configureTemplateDef();
		PutTag putTag = new PutTag();
		putTag.setParent(this._insertTag);
		putTag.setPageContext(this.pageContext);

		putTag.setName("header"); //$NON-NLS-1$
		putTag.setPath("applicationScope"); //$NON-NLS-1$
		putTag.setScope("application"); //$NON-NLS-1$

		try {
			putTag.doStartTag();
		} catch (JspException e) {
			fail();
		}

		String getValue = getParameterValue("header"); //$NON-NLS-1$

		assertNotNull(getValue);
		assertEquals(TemplatePlugin.getConfigDir() + "applicationScope", //$NON-NLS-1$
				getValue);
	}

	public void testNegativeOverridePut() {
		configureTemplateDef();
		PutTag putTag = new PutTag();
		putTag.setParent(this._insertTag);
		putTag.setPageContext(this.pageContext);

		putTag.setName("badName"); //$NON-NLS-1$
		putTag.setPath("sessionScope"); //$NON-NLS-1$
		putTag.setScope("session"); //$NON-NLS-1$

		try {
			putTag.doStartTag();
			fail();
		} catch (JspException expected) {
			// expected
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
		TemplateHelper.clearPageContextDefinition(this.pageContext, TEMPLATE1);
	}

}
