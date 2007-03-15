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
import javax.servlet.jsp.tagext.Tag;

import org.megatome.frame2.taglib.template.TemplateConstants;
import org.megatome.frame2.tagsupport.BaseFrame2Tag;
import org.megatome.frame2.template.config.TemplateDef;

public class PutTag extends BaseFrame2Tag {

	private static final long serialVersionUID = 820760806446858411L;
	private static Map<String, Integer> scopeValues;

	static {
		scopeValues = new HashMap<String, Integer>();

		scopeValues.put(TemplateConstants.REQUEST_SCOPE, new Integer(
				PageContext.REQUEST_SCOPE));
		scopeValues.put(TemplateConstants.SESSION_SCOPE, new Integer(
				PageContext.SESSION_SCOPE));
		scopeValues.put(TemplateConstants.APPLICATION_SCOPE, new Integer(
				PageContext.APPLICATION_SCOPE));
	}

	/**
	 * 
	 */
	public PutTag() {
		super();
		initializeAttributes();
	}

	@Override
	protected void initializeAttributes() {
		// override to reset any attrs.
		setAttr(TemplateConstants.SCOPE, TemplateConstants.REQUEST_SCOPE);
	}

	@Override
	protected void setTagName() {
		this.tagName = TemplateConstants.PUT_TAG;

	}

	/**
	 * @return
	 */
	public String getName() {
		return getAttr(TemplateConstants.NAME);
	}

	/**
	 * @return
	 */
	public String getPath() {
		return getAttr(TemplateConstants.PATH);
	}

	/**
	 * @return
	 */
	public String getScope() {
		return getAttr(TemplateConstants.SCOPE);
	}

	/**
	 * @param string
	 */
	public void setName(String name) {
		setAttr(TemplateConstants.NAME, name);
	}

	/**
	 * @param string
	 */
	public void setPath(String path) {
		setAttr(TemplateConstants.PATH, path);
	}

	/**
	 * @param string
	 */
	public void setScope(String scope) {
		setAttr(TemplateConstants.SCOPE, scope);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		Tag parentTag = getParent();
		if ((parentTag == null) || !(parentTag instanceof InsertTag)) {
			throw new JspException(
					"Put Tag must be contained within an Insert Tag"); //$NON-NLS-1$
		}

		TemplateDef def = (TemplateDef) this.pageContext
				.findAttribute(TemplateConstants.FRAME2_INSERT_KEY);

		if (def == null) {
			throw new JspException(
					"Error PutTag could not access definition for template "); //$NON-NLS-1$
		}

		String putName = evaluateStringAttribute(TemplateConstants.NAME);
		if (def.getPutParam(putName) == null) {
			throw new JspException("Cannot override param " + putName //$NON-NLS-1$
					+ ": does not exist in template definition " //$NON-NLS-1$
					+ def.getName());
		}

		String putPath = evaluateStringAttribute(TemplateConstants.PATH);
		if (putPath.equals("")) { //$NON-NLS-1$
			throw new JspException("Cannot override param " + putName //$NON-NLS-1$
					+ ": cannot have an empty path"); //$NON-NLS-1$
		}

		def.overridePutParam(putName, putPath, this.pageContext, getScopeValue());

		return Tag.SKIP_BODY;
	}

	private int getScopeValue() throws JspException {
		String putScope = evaluateStringAttribute(TemplateConstants.SCOPE);
		String trimmedScope = putScope.trim().toLowerCase();

		Integer scopeValue = scopeValues.get(trimmedScope);
		if (scopeValue == null) {
			throw new JspException("Scope '" + putScope + "' is invalid"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		return scopeValue.intValue();
	}

}
