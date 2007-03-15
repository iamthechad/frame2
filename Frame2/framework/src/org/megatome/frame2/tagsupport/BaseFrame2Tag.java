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
package org.megatome.frame2.tagsupport;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import org.megatome.frame2.tagsupport.util.EvalHelper;

/**
 * This is the base class used for all tags in the Frame2 framework. It provides
 * support for EL attributes, as well as storing tag attributes.
 */
public abstract class BaseFrame2Tag extends BodyTagSupport implements
		TryCatchFinally {
	/** Attributes of this tag */
	protected Map<String, String> attrs = new TreeMap<String, String>();

	/** This tag's name */
	protected String tagName;

	/** Indicate if this tag has a body */
	protected boolean tagHasBody;

	/**
	 * Constructor
	 */
	public BaseFrame2Tag() {
		setTagName();
	}

	/**
	 * Returns the tagName.
	 * 
	 * @return String
	 */
	public String getTagName() {
		return this.tagName;
	}

	/**
	 * Set the tagName.
	 */
	protected abstract void setTagName();

	/**
	 * Set an attribute for this tag
	 * 
	 * @param key
	 *            The key of the attribute
	 * @param value
	 *            The value of the attribute
	 */
	protected void setAttr(String key, String value) {
		this.attrs.put(key, value);
	}

	/**
	 * Remove an attribute from this tag
	 * 
	 * @param key
	 *            Attribute to remove
	 */
	protected void removeAttr(String key) {
		this.attrs.remove(key);
	}

	/**
	 * Get the attribute associated with a key.
	 * 
	 * @param key
	 *            The key
	 * @return Attribute value associated with the key, nor null if none found.
	 */
	protected String getAttr(String key) {
		return this.attrs.get(key);
	}

	/**
	 * Resets attribute values for tag reuse.
	 */
	@Override
	public void release() {
		super.release();
		this.attrs.clear();
	}

	/**
	 * Release not called until garbage collect.
	 * 
	 * @see javax.servlet.jsp.tagext.TryCatchFinally#doFinally()
	 */
	public void doFinally() {
		// free resources,
		this.attrs.clear();
		initializeAttributes();
	}

	/**
	 * Override to reset any attributes
	 */
	protected void initializeAttributes() {
		// override to reset any attrs.
	}

	/**
	 * Catch any exceptions thrown.
	 * 
	 * @param t
	 *            The exception to catch
	 * @see javax.servlet.jsp.tagext.TryCatchFinally#doCatch(Throwable)
	 */
	public void doCatch(Throwable t) throws Throwable {
		throw t;
	}

	/**
	 * Clear any resources held by this tag
	 */
	protected void clear() {
		// not implemented
	}

	/**
	 * Evaluates and returns a String given the input attribute name and
	 * attribute value.
	 * 
	 * @param attrName
	 *            attribute name being evaluated
	 * @param attrValue
	 *            String value of attribute to be evaluated using EL *
	 * @exception Exception
	 *                if either the <code>attrValue</code> was null, or the
	 *                resulting evaluated value was null.
	 * @return Resulting attribute value
	 */
	protected String evalStringAttr(String attrName, String attrValue)
			throws Exception {
		return (String) (EvalHelper.eval(getTagName(), attrName, attrValue,
				String.class, this, this.pageContext));
	}

	/**
	 * Evaluates and returns a HashMap given the input attribute name and
	 * attribute value.
	 * 
	 * @param attrName
	 *            Attribute name being evaluated
	 * @param attrValue
	 *            String value of attribute to be evaluated using EL
	 * @return Resulting HashMap
	 * @throws Exception
	 *             If either the <code>attrValue</code> is null, or the
	 *             resulting evaluated value is null.
	 */
	@SuppressWarnings("unchecked")
	protected HashMap<Object, Object> evalHashMapAttr(String attrName,
			String attrValue) throws Exception {
		return (HashMap<Object, Object>) (EvalHelper.eval(getTagName(),
				attrName, attrValue, HashMap.class, this, this.pageContext));
	}

	/**
	 * Evaluates and returns a Collection given the input attribute name and
	 * attribute value.
	 * 
	 * @param attrName
	 *            Attribute name being evaluated
	 * @param attrValue
	 *            String value of attribute to be evaluated using EL
	 * @return Resulting Collection
	 * @throws Exception
	 *             If either the <code>attrValue</code> is null, or the
	 *             resulting evaluated value is null.
	 */
	protected Collection<?> evalCollectionAttr(String attrName, String attrValue)
			throws Exception {
		return (Collection) (EvalHelper.eval(getTagName(), attrName, attrValue,
				Collection.class, this, this.pageContext));
	}

	/**
	 * Evaluates and returns an array given the input attribute name and
	 * attribute value.
	 * 
	 * @param attrName
	 *            Attribute name being evaluated
	 * @param attrValue
	 *            String value of attribute to be evaluated using EL
	 * @return Resulting array
	 * @throws Exception
	 *             If either the <code>attrValue</code> is null, or the
	 *             resulting evaluated value is null.
	 */
	protected Object[] evalArrayAttr(String attrName, String attrValue)
			throws Exception {
		return (Object[]) (EvalHelper.eval(getTagName(), attrName, attrValue,
				Object[].class, this, this.pageContext));
	}

	/**
	 * Evaluates and returns a String given the input attribute name.
	 * 
	 * @param attrName
	 *            Attribute name being evaluated
	 * @return Resulting value
	 * @throws JspException
	 *             If either the value associated with the <code>attrName</code>
	 *             is null, or the resulting evaluated value is null.
	 */
	protected String evaluateStringAttribute(String attrName)
			throws JspException {
		String attrValue = null;
		try {
			attrValue = evalStringAttr(attrName, getAttr(attrName));
		} catch (Exception e) {
			throw new JspException(
					" Evaluation attribute failed " + e.getMessage(), //$NON-NLS-1$
					e);
		}

		return attrValue;
	}
}
