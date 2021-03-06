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

import java.util.HashMap;
import java.util.Map;

/**
 * EventHandlerDef is an Object representation of the eventHandler element in
 * the configuration file. It is named EventHandlerDef and not EventHandler
 * because the framework contains an interface called EventHandler.
 */
public class EventHandlerDef {
    private String name = ""; //$NON-NLS-1$
    private String type = ""; //$NON-NLS-1$

    private Map<String, String> initParams = new HashMap<String, String>();
    private Map<String, Forward> htmlForwards = new HashMap<String, Forward>();
    private Map<String, Forward> xmlForwards = new HashMap<String, Forward>();
    
    public EventHandlerDef() {
    	// Default ctor
    }
    
    public EventHandlerDef(EventHandlerDef eventHandler) {
        this.name = eventHandler.name;
        this.type = eventHandler.type;
        this.initParams = new HashMap<String, String>(eventHandler.initParams);
        this.htmlForwards = new HashMap<String, Forward>(eventHandler.htmlForwards);
        this.xmlForwards = new HashMap<String, Forward>(eventHandler.xmlForwards);
    }

    /**
     * Returns the name.
     * @return String the name of the EventHandlerDef
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the type.
     * @return String the type of the EventHandlerDef
     */
    public String getType() {
        return this.type;
    }

    /**
     * Sets the name.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the type.
     * @param type The type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns an XML forward.
     * @param eventName The name of the XML Forward to get
     * @return XML Forward
     */
    public Forward getXMLForward(String forwardName) {
        return this.xmlForwards.get(forwardName);
    }

    /**
     * Sets the XML Forwards.
     * @param forwards HashMap containing all the EventHandlerDef XML Forwards
     */
    public void setXMLForwards(Map<String, Forward> forwards) {
        this.xmlForwards = new HashMap<String, Forward>(forwards);
    }

    /**
     * Returns an HTML forward.
     * @param eventName The name of the HTML Forward to get
     * @return HTML Forward
     */
    public Forward getHTMLForward(String forwardName) {
        return this.htmlForwards.get(forwardName);
    }

    /**
     * Sets the HTML Forwards.
     * @param forwards HashMap containing all the EventHandlerDef HTML Forwards
     */
    public void setHTMLForwards(Map<String, Forward> forwards) {
        this.htmlForwards = new HashMap<String, Forward>(forwards);
    }

    /**
     * Sets the EventHandlerDef Init Params.
     * @param params HashMap containing all the EventHandlerDef Init Params.
     */
    public void setInitParams(Map<String, String> params) {
        this.initParams = new HashMap<String, String>(params);
    }

    /**
     * Returns an Map of the Init Params.
     * @return Map
     */
    public Map<String, String> getInitParams() {
        return this.initParams;
    }

    /**
     * Returns an Init Param.
     * @return String
     */
    public String getInitParam(String paramName) {
        return this.initParams.get(paramName);
    }

    /**
     * Adds an Init Param to the Map of Init Params.
     * @param eventName String
     * @param eventName value
     */
    public void addInitParam(String paramName, String value) {
        this.initParams.put(paramName, value);
    }
}