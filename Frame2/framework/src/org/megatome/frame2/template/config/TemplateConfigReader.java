/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2005 Megatome Technologies.  All rights
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
import java.util.HashMap;

import org.megatome.frame2.template.TemplateException;
import org.megatome.frame2.util.sax.ParserException;
import org.megatome.frame2.util.sax.Frame2SAXReader;

/**
 * EventConfigReader parses the Event Configuration file. It uses the Sax
 * Parser. It creates Handlers for all the elements in the config file, from
 * which run-time configuration objects are extracted.
 */

public class TemplateConfigReader {
    public static final String PUT = "put";

    public static final String TEMPLATE = "template";

    public static final String TEMPLATES = "templates";

    public static final String TEMPLATE_CONFIG = "template-config";

    private Frame2SAXReader reader;

    private PutParamTagHandler _putTagHandler;

    private TemplateTagHandler _templateTagHandler;

    public TemplateConfigReader(HashMap definitions) {
        _putTagHandler = new PutParamTagHandler();
        _templateTagHandler = new TemplateTagHandler(_putTagHandler,
                definitions);
        reader = new Frame2SAXReader();

        reader.setElementHandler(TEMPLATE, _templateTagHandler);
        reader.setElementHandler(PUT, _putTagHandler);

        // Now set the Elements which do not have handlers
        reader.setElement(TEMPLATES);
        reader.setElement(TEMPLATE_CONFIG);
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
                        "Error finding template definition file ");
            }
            reader.parse(is);
        } catch (ParserException e) {
            e.printStackTrace();
            throw new TemplateException("Unable to load template definition: "
                    + e.getMessage(), e);
        }
    }
}