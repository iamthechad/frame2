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
package org.megatome.app.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.megatome.frame2.errors.Errors;
import org.megatome.frame2.event.CommonsValidatorEvent;

public class TestTagEvent extends CommonsValidatorEvent {
	private String _textTag;

	private String _passwordTag;

	private String _checkedValue;

	private String _textareaTag;

	private String _htmlXhtmlValue = "htmlRadio"; //$NON-NLS-1$

	private String _xhtml;

	private List<String> _list = new ArrayList<String>();

	private List<String> _selectedList = new ArrayList<String>();

	public TestTagEvent() {
		super();
		this._list.add("one"); //$NON-NLS-1$
		this._list.add("two"); //$NON-NLS-1$
		this._list.add("four"); //$NON-NLS-1$
		this._list.add("me"); //$NON-NLS-1$

		this._selectedList.add("xxx"); //$NON-NLS-1$
		this._selectedList.add("two"); //$NON-NLS-1$
		this._selectedList.add("vvv"); //$NON-NLS-1$
		this._selectedList.add("yyy"); //$NON-NLS-1$
	}

	public Collection<String> getList() {
		return this._list;
	}

	public Collection<String> getSelectedList() {
		return this._selectedList;
	}

	/*
	 * public Object[] getList() { return (Object[])_list.toArray(); }
	 */
	/**
	 * Returns the textTag.
	 * 
	 * @return String
	 */
	public String getTextTag() {
		return this._textTag;
	}

	/**
	 * Sets the textTag.
	 * 
	 * @param textTag
	 *            The textTag to set
	 */
	public void setTextTag(String textTag) {
		this._textTag = textTag;
	}

	@Override
	public boolean validate(@SuppressWarnings("unused")
	Errors errors) {
		return true;
	}

	/**
	 * Returns the passwordTag.
	 * 
	 * @return String
	 */
	public String getPasswordTag() {
		return this._passwordTag;
	}

	/**
	 * Sets the passwordTag.
	 * 
	 * @param passwordTag
	 *            The passwordTag to set
	 */
	public void setPasswordTag(String passwordTag) {
		this._passwordTag = passwordTag;
	}

	/**
	 * Returns the checkedValue.
	 * 
	 * @return String
	 */
	public String getCheckedValue() {
		return this._checkedValue;
	}

	/**
	 * Sets the checkedValue.
	 * 
	 * @param checkedValue
	 *            The checkedValue to set
	 */
	public void setCheckedValue(String checkedValue) {
		this._checkedValue = checkedValue;
	}

	/**
	 * Returns the passwordTag.
	 * 
	 * @return String
	 */
	public String getHtmlXhtmlRadioTag() {
		return this._htmlXhtmlValue;
	}

	/**
	 * Sets the passwordTag.
	 * 
	 * @param passwordTag
	 *            The passwordTag to set
	 */
	public void setHtmlXhtmlRadioTag(String htmlXhtmlValue) {
		this._htmlXhtmlValue = htmlXhtmlValue;
		if (this._htmlXhtmlValue.equals("xhtmlRadio")) { //$NON-NLS-1$
			setXhtml("true"); //$NON-NLS-1$
		} else {
			setXhtml("false"); //$NON-NLS-1$
		}

	}

	/**
	 * Returns the _xhtml.
	 * 
	 * @return String
	 */
	public String getXhtml() {
		return this._xhtml;
	}

	/**
	 * Sets the _xhtml.
	 * 
	 * @param xhtml
	 */
	private void setXhtml(String xhtml) {
		this._xhtml = xhtml;
	}

	/**
	 * Returns the textareaTag.
	 * 
	 * @return String
	 */
	public String getTextareaTag() {
		return this._textareaTag;
	}

	/**
	 * Sets the textareaTag.
	 * 
	 * @param textareaTag
	 *            The textareaTag to set
	 */
	public void setTextareaTag(String textareaTag) {
		this._textareaTag = textareaTag;
	}

}
