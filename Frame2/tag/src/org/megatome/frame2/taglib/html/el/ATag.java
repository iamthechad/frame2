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

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.megatome.frame2.taglib.html.Constants;
import org.megatome.frame2.util.URLHelper;

public class ATag extends BaseHtmlTag {

	private static final long serialVersionUID = -1477790313281946730L;

	String _displayValue;

    Map<String, Object> _paramMap;

    public void setCharset(String charset) {

        setAttr(Constants.CHARSET, charset);
    }

    @Override
	public void setStyleClass(String aclass) {

        setAttr(Constants.CLASS, aclass);
    }

    public void setDisplayvalue(String displayValue) {

        setAttr(Constants.DISPLAY_VALUE, displayValue);
    }

    public void setHref(String href) {

        setAttr(Constants.HREF, href);
    }

    public void setHreflang(String hreflang) {

        setAttr(Constants.HREF_LANG, hreflang);
    }

    @Override
	public void setId(String id) {

        setAttr(Constants.ID, id);
    }

    public void setQueryparams(String queryParams) {
        setAttr(Constants.QUERY_PARAMS, queryParams);
    }

    public void setRel(String rel) {

        setAttr(Constants.REL, rel);
    }

    public void setRev(String rev) {

        setAttr(Constants.REV, rev);
    }

    public void setShape(String shape) {

        setAttr(Constants.SHAPE, shape);
    }

    public void setTarget(String target) {

        setAttr(Constants.TARGET, target);
    }

    public void setType(String type) {

        setAttr(Constants.TYPE, type);
    }

    public void setUrn(String urn) {

        setAttr(Constants.URN, urn);
    }

    @Override
	protected void setTagName() {
        this.tagName = Constants.A;
    }

    @Override
	protected void setType() {
        this._type = Constants.A;
    }

    String _bodyContent = null;

    @Override
	protected StringBuffer buildStartTag() {
        StringBuffer results = new StringBuffer();
        results.append(Constants.LINK_OPEN);
        return results;
    }

    // override ths if you want to handle an attribute
    @Override
	protected void specialAttrHandler() throws JspException {
        handleValueAttr();
    }

    @Override
	protected void specialEndAttrHandler() throws JspException {
        handleHrefAndQueryParamsAttr();
    }

    /**
     * Process the displayvalue attribute so it will be rendered inbetween the
     * start and end tag.
     * @throws JspException thrown if the displayvalue attribute fails
     *         evaluation.
     */
    private void handleValueAttr() throws JspException {
        this._displayValue = getAttr(Constants.DISPLAY_VALUE);
        if (this._displayValue != null) {
            try {
                this._displayValue = evalStringAttr(Constants.DISPLAY_VALUE,
                        this._displayValue);
            } catch (Exception e) {
                throw new JspException(" Evaluation attribute failed " //$NON-NLS-1$
                        + e.getMessage(), e);
            }
            // now remove this attr from map
            // so not output in genHTML()
            removeAttr(Constants.DISPLAY_VALUE);
        }

    }

    /**
     * Process the href and queryparams attributes. If both attributes are not
     * null, append the query parameters to the href attribute.
     * @throws JspException
     */
    private void handleHrefAndQueryParamsAttr() throws JspException {

        String queryParams = getAttr(Constants.QUERY_PARAMS);
        Map<String, Object> params = this._paramMap;
        // If queryparams are not null and map is not null, error
        if ((queryParams != null) && (params != null)) {
            throw new JspException(
                    "Only one of queryparams or <queryparam> may be used"); //$NON-NLS-1$
        }

        // If queryParams is not null, then href must not be null.
        if ((queryParams != null) || (params != null)) {
            String href = getAttr(Constants.HREF);
            if (href != null) {
                try {
                    href = evalStringAttr(Constants.DISPLAY_VALUE, href);
                    if (params == null) {
                        params = evalHashMapAttr(Constants.QUERY_PARAMS,
                                queryParams);
                    }
                } catch (Exception e) {
                    throw new JspException(" Evaluation attribute failed " //$NON-NLS-1$
                            + e.getMessage(), e);
                }

                // now remove the QUERY_PARAMS attr from map
                // so not output in genHTML()
                removeAttr(Constants.QUERY_PARAMS);

                // Add query params to the end of href.
                String newHref;
                try {
                    newHref = URLHelper.appendQueryParams(href, params);
                } catch (UnsupportedEncodingException e) {
                    throw new JspException(
                            " Adding Query params to href has failed " //$NON-NLS-1$
                                    + e.getMessage(), e);
                }

                // set hrefs value to the href+queryParams value
                setAttr(Constants.HREF, newHref);

            } else {
                throw new JspException("The " + Constants.QUERY_PARAMS //$NON-NLS-1$
                        + " attribute was set, there must also be " //$NON-NLS-1$
                        + "an href attribute set. "); //$NON-NLS-1$
            }

        } // else process href as any other attribute when query params is null.

    }

    /**
     * Add a parameter to the map. If the parameter name already exists, the
     * value is appended to an array of values.
     * @param paramName
     * @param paramValue
     */
    public void addParam(String paramName, String paramValue) {
        if (this._paramMap == null) {
            this._paramMap = new HashMap<String, Object>();
        }

        Object valueObject = paramValue;
        // Need to map into an array somehow
        if (this._paramMap.containsKey(paramName)) {
            Object pValue = this._paramMap.get(paramName);
            String[] newValues;
            if (pValue instanceof String[]) {
                String[] values = (String[])pValue;
                newValues = new String[values.length + 1];
                System.arraycopy(values, 0, newValues, 0, values.length);
                newValues[newValues.length - 1] = paramValue;
            } else {
                newValues = new String[2];
                newValues[0] = (String)pValue;
                newValues[1] = paramValue;
            }
            valueObject = newValues;
        }

        this._paramMap.put(paramName, valueObject);
    }

    /**
     * Returns if tag has body or not
     * @return boolean
     */
    @Override
	public boolean evalTagBody() {
        if (this._displayValue != null
                || (this.bodyContent != null && !this.bodyContent.getString().equals(""))) { //$NON-NLS-1$
            return true;
        }

        return false;
    }

    /**
     * Appends _displayValue String
     * @param StringBuffer
     */
    @Override
	public void getBody(StringBuffer buffer) {
        // actual body content overrides
        // any display value.
        if (this.bodyContent != null && !this.bodyContent.getString().equals("")) { //$NON-NLS-1$
            buffer.append(this.bodyContent.getString());
        } else if (this._displayValue != null) {
            buffer.append(this._displayValue);
        }
    }

    /**
     * Appends end Element Close
     * @param StringBuffer
     */
    @Override
	public void getEndElementClose(StringBuffer buffer) {
        buffer.append(Constants.LINK_CLOSE);
    }

    @Override
	protected void clear() {
        super.clear();
        this._displayValue = null;
        this._bodyContent = null;
        this._paramMap = null;
    }

    @Override
	public void release() {
        super.release();
        clear();
    }

    @Override
	public StringBuffer buildEndTag() throws JspException {
        StringBuffer results = new StringBuffer();
        results.append(genTagAttrs());
        return results.append(super.buildEndTag());
    }

}