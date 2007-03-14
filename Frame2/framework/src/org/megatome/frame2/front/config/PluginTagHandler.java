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

import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerFactory;
import org.xml.sax.Attributes;

class PluginTagHandler extends ConfigElementHandler {
    private static Logger LOGGER = LoggerFactory
            .instance(PluginTagHandler.class.getName());

    public static final String NAME = "name"; //$NON-NLS-1$
    public static final String TYPE = "type"; //$NON-NLS-1$

    private InitParamTagHandler inputParamTagHandler;
    private List<PluginDef> plugins = new ArrayList<PluginDef>();
    private PluginDef pluginDef;

    public PluginTagHandler(InitParamTagHandler inputParamTagHandler) {
        this.inputParamTagHandler = inputParamTagHandler;
    }

    @Override
	public void startElement(@SuppressWarnings("unused")
	String uri, @SuppressWarnings("unused")
	String localName, @SuppressWarnings("unused")
	String qName,
            Attributes attributes) {
        this.pluginDef = new PluginDef();
        this.pluginDef.setName(attributes.getValue(NAME));
        this.pluginDef.setType(attributes.getValue(TYPE));
    }

    @Override
	public void endElement(@SuppressWarnings("unused")
	String uri, @SuppressWarnings("unused")
	String localName, @SuppressWarnings("unused")
	String qName) {
        this.pluginDef.setInitParams(this.inputParamTagHandler.getParams());
        this.inputParamTagHandler.clear();
        if (this.plugins.contains(this.pluginDef)) {
            // do not add duplicate named plugin defs to list.
            LOGGER.severe("Error: Duplicate plugin definition " //$NON-NLS-1$
                    + this.pluginDef.getName());
        } else {
            this.plugins.add(this.pluginDef);
        }
    }

    public List<PluginDef> getPlugins() {
        return this.plugins;
    }

    @Override
	public void clear() {
        this.plugins.clear();
    }
}