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
package org.megatome.frame2.template.config;

import java.io.InputStream;
import java.util.Map;

import org.megatome.frame2.template.TemplateException;
import org.megatome.frame2.util.sax.Frame2SAXReader;
import org.megatome.frame2.util.sax.ParserException;

/**
 * EventConfigReader parses the Event Configuration file. It uses the Sax
 * Parser. It creates Handlers for all the elements in the config file, from
 * which run-time configuration objects are extracted.
 */

public class TemplateConfigReader {
    public static final String PUT = "put"; //$NON-NLS-1$

    public static final String TEMPLATE = "template"; //$NON-NLS-1$

    public static final String TEMPLATES = "templates"; //$NON-NLS-1$

    public static final String TEMPLATE_CONFIG = "template-config"; //$NON-NLS-1$

    private Frame2SAXReader reader;

    private PutParamTagHandler putTagHandler;

    private TemplateTagHandler templateTagHandler;

    public TemplateConfigReader(Map<String, TemplateDefI> definitions) {
        this.putTagHandler = new PutParamTagHandler();
        this.templateTagHandler = new TemplateTagHandler(this.putTagHandler,
                definitions);
        this.reader = new Frame2SAXReader();

        this.reader.setElementHandler(TEMPLATE, this.templateTagHandler);
        this.reader.setElementHandler(PUT, this.putTagHandler);

        // Now set the Elements which do not have handlers
        this.reader.setElement(TEMPLATES);
        this.reader.setElement(TEMPLATE_CONFIG);
    }

    /**
     * Saves the Elements handlers used for parsing the configuration file and
     * then parses the file.
     * @exception TemplateException
     */
    public void execute(InputStream is) throws TemplateException {

        try {
            if (is == null) {
                throw new ParserException(
                        "Error finding template definition file "); //$NON-NLS-1$
            }
            this.reader.parse(is);
        } catch (ParserException e) {
            e.printStackTrace();
            throw new TemplateException("Unable to load template definition: " //$NON-NLS-1$
                    + e.getMessage(), e);
        }
    }
}