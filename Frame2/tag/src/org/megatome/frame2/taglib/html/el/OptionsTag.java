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

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.megatome.frame2.taglib.html.Constants;
import org.megatome.frame2.tagsupport.TagConstants;
import org.megatome.frame2.tagsupport.util.HTMLHelpers;

/**
 * OptionsTag.java
 */
public class OptionsTag extends BaseOptionTag {
	private static final long serialVersionUID = -1288010744940723077L;

	private Collection<?> _selectedCollection;

	private Object[] _selectedArray;

	private String _selectedString;

	private Collection<?> _displayValueCollection;

	private Object[] _displayValueArray;

	private Collection<?> _valueCollection;

	private Object[] _valueArray;

	@Override
	protected void setType() {
		this._type = Constants.OPTIONS;
	}

	@Override
	protected StringBuffer buildStartTag() {
		StringBuffer buffer = new StringBuffer();

		Object[] value = null;
		if (this._valueCollection != null) {
			value = this._valueCollection.toArray();
		} else if (this._valueArray != null) {
			value = this._valueArray;
		}

		Object[] displayValue = null;
		int displayLen = -1;
		if (this._displayValueCollection != null) {
			displayValue = this._displayValueCollection.toArray();
			displayLen = displayValue.length;
		} else if (this._displayValueArray != null) {
			displayValue = this._displayValueArray;
			displayLen = displayValue.length;
		}

		int len = value.length;
		for (int i = 0; i < len; i++) {
			String dispValue = null;
			if (displayLen != -1 && i < displayLen) {
				// dispValue = (String)displayValue[i];
				dispValue = String.valueOf(displayValue[i]);
			}
			// addOptionTag(buffer,(String)value[i], dispValue);
			addOptionTag(buffer, String.valueOf(value[i]), dispValue);
		}

		return buffer;
	}

	// should be collection or object[]
	protected void handleValueAttr() throws JspException {
		String valueExpr = getAttr(Constants.VALUE);
		// see if this is a collection
		try {
			this._valueCollection = evalCollectionAttr(Constants.VALUE,
					valueExpr);
		} catch (Exception e) {
			// ignore, try array next;
		}

		if (this._valueCollection == null) {
			try {
				this._valueArray = evalArrayAttr(Constants.VALUE, valueExpr);
			} catch (Exception e) {
				throw new JspException(
						" Evaluation attribute failed, Collection or Object[] expected " + e.getMessage(), e); //$NON-NLS-1$
			}
		}
	}

	// override this if you want to handle an attribute
	@Override
	protected void specialAttrHandler() throws JspException {
		super.specialAttrHandler();
		handleValueAttr();
	}

	// for OPTIONS tag, expect display value to be collection or [].
	// as iterate over selected(From SELECT_KEY), get
	// display value.
	@Override
	protected void handleDisplayValueAttr() throws JspException {
		if (this._displayExpr == null || this._displayExpr == "") { //$NON-NLS-1$
			// Evaluate the remainder of this page
			return;
		}

		try {
			this._displayValueCollection = evalCollectionAttr(
					Constants.DISPLAY_VALUE, this._displayExpr);
		} catch (Exception e) {
			// ignore, try array next;
		}

		if (this._displayValueCollection == null) {
			try {
				this._displayValueArray = evalArrayAttr(
						Constants.DISPLAY_VALUE, this._displayExpr);
			} catch (Exception e) {
				throw new JspException(
						" Evaluation attribute failed, Collection or Object[] expected " + e.getMessage(), e); //$NON-NLS-1$
			}
		}
	}

	@Override
	protected void handleSelectedAttr() throws JspException {
		// get thr select attr
		String selectExpr = (String) this.pageContext
				.getAttribute(Constants.SELECT_KEY);
		// String selectExpr = _selected;

		if ((selectExpr == null) || (selectExpr == "")) { //$NON-NLS-1$
			selectExpr = this._selected;
		}

		// now get value
		String valueExpr = getAttr(Constants.VALUE);
		if (valueExpr == null || valueExpr == "" || //$NON-NLS-1$
				selectExpr == null || selectExpr == "") { //$NON-NLS-1$
			return;
		}

		// see if this is a collection
		try {
			this._selectedCollection = evalCollectionAttr(Constants.SELECTED,
					selectExpr);
		} catch (Exception e) {
			// ignore, try array next;
		}

		if (this._selectedCollection == null) {
			try {
				this._selectedArray = evalArrayAttr(Constants.SELECTED,
						selectExpr);
			} catch (Exception e) {
				// ignore, try string next;
			}
		}

		// init "" for cmp with valueval later
		// String checkval = "";
		if (this._selectedCollection == null && this._selectedArray == null) {
			try {
				// checkval = evalStringAttr(Constants.SELECTED, selectExpr);
				this._selectedString = evalStringAttr(Constants.SELECTED,
						selectExpr);
			} catch (Exception e) {
				throw new JspException(
						" Evaluation attribute failed " + e.getMessage(), e); //$NON-NLS-1$
			}
		}
	}

	protected boolean isSelected(String valueval) {
		boolean selected = false;
		if (this._selectedCollection != null) {
			Iterator<?> iter = this._selectedCollection.iterator();
			while (iter.hasNext()) {
				// NIT make work for all primatives
				// String val = (String)iter.next();
				String val = String.valueOf(iter.next());
				if (val.equals(valueval)) {
					selected = true;
					break;
				}
			}
		} else if (this._selectedArray != null) {
			int len = this._selectedArray.length;
			for (int i = 0; i < len; i++) {
				// NIT make work for all primatives
				// String val = (String)_selectedArray[i];
				String val = String.valueOf(this._selectedArray[i]);
				if (val.equals(valueval)) {
					selected = true;
					break;
				}
			}
		} else {
			if (this._selectedString != null) {
				selected = this._selectedString.equals(valueval);
			}
		}
		return selected;
	}

	protected void addOptionTag(StringBuffer buf, String value,
			String displayValue) {
		buf.append(getTagName());
		if (isSelected(value)) {
			// buf.append(HTMLEncoder.encode(HTMLHelpers.buildHtmlAttr(Constants.SELECTED,Constants.TRUE)));
			// buf.append(HTMLHelpers.buildHtmlAttr(Constants.SELECTED,Constants.TRUE));
			buf.append(HTMLHelpers.buildHtmlAttr(Constants.SELECTED));
		}
		// buf.append(HTMLEncoder.encode(HTMLHelpers.buildHtmlAttr(Constants.VALUE,value)));
		buf.append(HTMLHelpers.buildHtmlAttr(Constants.VALUE, value));
		if (displayValue != null) {
			buf.append(TagConstants.RT_ANGLE);
			buf.append(displayValue + Constants.OPTION_CLOSE);
		} else {
			buf.append(getElementClose());
		}
		// is NL needed? NIT
	}

	@Override
	public void getStartElementClose(@SuppressWarnings("unused")
	StringBuffer buffer) {
		//noop
	}

	@Override
	public void release() {
		super.release();
		clear();
	}

	@Override
	protected void clear() {
		super.clear();
		if (this._selectedCollection != null) {
			this._selectedCollection.clear();
		}
		if (this._displayValueCollection != null) {
			this._displayValueCollection.clear();
		}
		if (this._valueCollection != null) {
			this._valueCollection.clear();
		}

		this._selectedArray = null;
		this._selectedString = null;
		this._displayValueCollection = null;
		this._displayValueArray = null;
		this._valueCollection = null;
		this._valueArray = null;
	}

}
