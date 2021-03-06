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

import java.util.ArrayList;
import java.util.List;

import org.megatome.frame2.util.sax.ParserException;
import org.xml.sax.Attributes;

/**
 * ExceptionTagHandler handles the exception elements of the Configuration file.
 * It generates a HashMap of all the ExceptionDef.
 */
class ExceptionTagHandler extends ConfigElementHandler {
    public static final String REQUEST_KEY = "requestKey"; //$NON-NLS-1$
    public static final String TYPE = "type"; //$NON-NLS-1$

    private ViewTagHandler viewTagHandler;
    private List<ExceptionDef> exceptions = new ArrayList<ExceptionDef>();
    private ExceptionDef def = null;

    /**
     * Constructs an ExceptionTagHandler.
     * @param ViewTagHandler
     */
    public ExceptionTagHandler(ViewTagHandler viewTagHandler) {
        this.viewTagHandler = viewTagHandler;
    }

    @Override
	public void startElement(@SuppressWarnings("unused")
	String uri, @SuppressWarnings("unused")
	String localName, @SuppressWarnings("unused")
	String qName,
            Attributes attributes) throws ParserException {
        String requestKey = attributes.getValue(REQUEST_KEY);
        String type = attributes.getValue(TYPE);
        this.def = new ExceptionDef(requestKey, type);
        if (this.exceptions.contains(this.def)) {
            throw new ParserException(
                    "Exception tag Error, Duplicate type defined for type " //$NON-NLS-1$
                            + type);
        }
    }

    @Override
	public void endElement(@SuppressWarnings("unused")
	String uri, @SuppressWarnings("unused")
	String localName, @SuppressWarnings("unused")
	String qName) {
        // build event mapping here
        this.def.setView(ViewType.XML.toString(), this.viewTagHandler
                .getXMLForwardName());
        this.def.setView(ViewType.HTML.toString(), this.viewTagHandler
                .getHTMLForwardName());
        this.exceptions.add(this.def);
        this.def = null;
        this.viewTagHandler.clear();
    }

    public List<ExceptionDef> getExceptions() {
        return this.exceptions;
    }

    @Override
	public void clear() {
        this.exceptions.clear();
    }
}