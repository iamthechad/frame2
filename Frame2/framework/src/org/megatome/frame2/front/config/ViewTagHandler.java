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
package org.megatome.frame2.front.config;

import org.megatome.frame2.util.sax.ParserException;
import org.xml.sax.Attributes;

/**
 * ViewTagHandler handles the view elements in the configuration file.
 */
class ViewTagHandler extends ConfigElementHandler {
    public static final String FORWARD_NAME = "forwardName"; //$NON-NLS-1$
    public static final String TYPE = "type"; //$NON-NLS-1$
    public static final String INVALID_TYPE = "Error: Invalid view type "; //$NON-NLS-1$

    private String htmlForwardName;
    private String xmlForwardName;

    @Override
	public void startElement(@SuppressWarnings("unused")
	String uri, @SuppressWarnings("unused")
	String localName, @SuppressWarnings("unused")
	String qName,
            Attributes attributes) throws ParserException {
        String forwardName = attributes.getValue(FORWARD_NAME);
        String viewattr = attributes.getValue(TYPE);
        ViewType vtype = ViewType.fromString(viewattr);

        if (vtype == null) {
            throw new ParserException(INVALID_TYPE + forwardName + " type " //$NON-NLS-1$
                    + viewattr);
        }

        if (vtype.equals(ViewType.BOTH)) {
            this.htmlForwardName = forwardName;
            this.xmlForwardName = forwardName;
        } else if (vtype.equals(ViewType.HTML)) {
            this.htmlForwardName = forwardName;
        } else if (vtype.equals(ViewType.XML)) {
            this.xmlForwardName = forwardName;
        }
    }

    public String getXMLForwardName() {
        return this.xmlForwardName;
    }

    public String getHTMLForwardName() {
        return this.htmlForwardName;
    }

    @Override
	public void clear() {
        this.xmlForwardName = null;
        this.htmlForwardName = null;
    }
}