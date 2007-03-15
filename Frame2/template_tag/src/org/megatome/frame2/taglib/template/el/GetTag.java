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

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.megatome.frame2.taglib.template.ServletResponseIncludeWrapper;
import org.megatome.frame2.taglib.template.TemplateConstants;
import org.megatome.frame2.tagsupport.BaseFrame2Tag;
import org.megatome.frame2.template.config.TemplateDef;

public class GetTag extends BaseFrame2Tag {

	private static final long serialVersionUID = 4634100289586083056L;

	/**
	 * 
	 */
	public GetTag() {
		super();
	}

	@Override
	protected void setTagName() {
		this.tagName = TemplateConstants.GET_TAG;
	}

	/**
	 * @return
	 */
	public String getName() {
		return getAttr(TemplateConstants.NAME);
	}

	/**
	 * @param string
	 */
	public void setName(String name) {
		setAttr(TemplateConstants.NAME, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {

		TemplateDef def = (TemplateDef) this.pageContext
				.findAttribute(TemplateConstants.FRAME2_INSERT_KEY);

		if (def == null) {
			throw new JspException(
					"Error GetTag could not access definition for template "); //$NON-NLS-1$
		}

		String getName = evaluateStringAttribute(TemplateConstants.NAME);
		String getPath = def.getPutParam(getName, this.pageContext);
		if (getPath == null) {
			throw new JspException("Could not access entry '" //$NON-NLS-1$
					+ getName + "' for template \"" //$NON-NLS-1$
					+ def.getName() + "\""); //$NON-NLS-1$
		}

		try {
			ServletResponseIncludeWrapper wrapper = new ServletResponseIncludeWrapper(
					this.pageContext.getResponse(), this.pageContext.getOut());

			RequestDispatcher rd = this.pageContext.getRequest()
					.getRequestDispatcher(getPath);
			rd.include(this.pageContext.getRequest(), wrapper);
		} catch (ServletException e) {
			throw new JspException(e);
		} catch (IOException e) {
			throw new JspException(e);
		}

		return Tag.SKIP_BODY;
	}
}
