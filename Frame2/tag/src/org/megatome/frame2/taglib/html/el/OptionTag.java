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

import javax.servlet.jsp.JspException;

import org.megatome.frame2.taglib.html.Constants;

/**
 * OptionTag.java
 */
public class OptionTag extends BaseOptionTag {

	private static final long serialVersionUID = 318231321674991160L;

	@Override
	public void setType() {
        this._type = Constants.OPTION;
    }

    // NIT same as radiotag, refactor
    @Override
	protected void handleDisplayValueAttr() throws JspException {
        if (this._displayExpr == null || this._displayExpr == "") { //$NON-NLS-1$
            // Evaluate the remainder of this page
            return;
        }

        try {
            this._displayValue = evalStringAttr(Constants.DISPLAY_VALUE,
                    this._displayExpr);
        } catch (Exception e) {
            throw new JspException(" Evaluation attribute failed " //$NON-NLS-1$
                    + e.getMessage(), e);
        }
    }

    // nit get this into helper.
    @Override
	protected void handleSelectedAttr() throws JspException {
        // get thr select attr
        String selectExpr = (String)this.pageContext
                .getAttribute(Constants.SELECT_KEY);

        if ((selectExpr == null) || (selectExpr == "")) { //$NON-NLS-1$
            selectExpr = this._selected;
        }

        // now get value
        String valueExpr = getAttr(Constants.VALUE);
        if (valueExpr == null || valueExpr == "" || selectExpr == null //$NON-NLS-1$
                || selectExpr == "") { //$NON-NLS-1$
            return;
        }

        String valueval = null; // init diff of checkval
        try {
            valueval = evalStringAttr(Constants.VALUE, valueExpr);
        } catch (Exception e) {
            throw new JspException(" Evaluation attribute failed " //$NON-NLS-1$
                    + e.getMessage(), e);
        }

        // see if this is a collection
        Collection<?> collection = null;
        try {
            collection = evalCollectionAttr(Constants.SELECTED, selectExpr);
        } catch (Exception e) {
            // ignore, try array next;
        }

        Object[] array = null;
        if (collection == null) {
            try {
                array = evalArrayAttr(Constants.SELECTED, selectExpr);
            } catch (Exception e) {
                // ignore, try string next;
            }
        }

        // init "" for cmp with valueval later
        String checkval = ""; //$NON-NLS-1$
        if (collection == null && array == null) {
            try {
                checkval = evalStringAttr(Constants.SELECTED, selectExpr);
            } catch (Exception e) {
                throw new JspException(" Evaluation attribute failed " //$NON-NLS-1$
                        + e.getMessage(), e);
            }
        }

        if (collection != null) {
        	for (Object obj : collection) {
                // NIT make work for all primitives
                String val = (String)obj;
                if (val.equals(valueval)) {
                    setAttr(Constants.SELECTED, Constants.NULL_VALUE);
                    break;
                }
            }
        } else if (array != null) {
            int len = array.length;
            for (int i = 0; i < len; i++) {
                // NIT make work for all primitives
                String val = (String)array[i];
                if (val.equals(valueval)) {
                    setAttr(Constants.SELECTED, Constants.NULL_VALUE);
                    break;
                }
            }
        } else {
            if (checkval.equals(valueval)) {
                setAttr(Constants.SELECTED, Constants.NULL_VALUE);
            }
        }
    }

    /**
     * Returns if tag has body or not
     * @return boolean
     */
    @Override
	public boolean evalTagBody() {
        if (this._displayValue == null || this._displayValue.equals("")) { //$NON-NLS-1$
            return false;
        }
        return true;
    }

    /**
     * Appends _displayValue String
     * @param StringBuffer
     */
    @Override
	public void getBody(StringBuffer buffer) {
        if (this._displayValue != null) {
            buffer.append(this._displayValue);
        }
    }

    /**
     * Appends end Element Close
     * @param StringBuffer
     */
    @Override
	public void getEndElementClose(StringBuffer buffer) {
        buffer.append(Constants.OPTION_CLOSE);
    }

}

