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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EventMapping is an Object representation of the eventMapping element in the
 * configuration file.
 */
public class EventMapping {
    private List<String> handlers = new ArrayList<String>();
    private Map<String, String> view = new HashMap<String, String>();
    private Security security;
    private String eventName;
    private String inputView;
    private String cancelView;
    private boolean validate = false;

    /**
     * Constructs an EventMapping
     * @param name String
     */
    public EventMapping(String name) {
        this.eventName = name;
    }

    public EventMapping(EventMapping eventMapping) {
        this.handlers = new ArrayList<String>(eventMapping.handlers);
        this.view = new HashMap<String, String>(eventMapping.view);
        this.security = eventMapping.security == null ? null : new Security(
                eventMapping.security);
        this.eventName = eventMapping.eventName;
        this.inputView = eventMapping.inputView;
        this.cancelView = eventMapping.cancelView;
        this.validate = eventMapping.validate;
    }

    /**
     * Sets the eventMapping Handlers.
     * @param handlers ArrayList of all the eventHandlers for that eventMapping
     */
    public void setHandlers(List<String> handlers) {
        this.handlers = new ArrayList<String>(handlers);
    }

    /**
     * Returns a clone of the List of eventHandlers.
     * @return ArrayList List of eventHandlers
     */
    public List<String> getHandlers() {
        return this.handlers;
    }

    /**
     * Returns the view.
     * @param type The type of view to set (XML, HTML, Both)
     * @return String returns the view for that type
     */
    public String getView(String type) {
        return this.view.get(type);
    }

    /**
     * Sets the view.
     * @param type The type of view to set (XML, HTML, Both)
     * @param forward The global forward name to use
     */
    public void setView(String type, String forward) {
        this.view.put(type, forward);
    }

    /**
     * Sets the view.
     * @param map The HashMap of all the views for that eventMapping
     */
    public void setView(Map<String, String> map) {
        this.view = new HashMap<String, String>(map);
    }

    /**
     * Returns the security.
     * @return Security
     */
    private Security getSecurity() {
        return this.security;
    }

    /**
     * Sets the security.
     * @param security The security to set
     */
    public void setSecurity(Security security) {
        this.security = security;
    }

    /**
     * Returns whether the user has that role or not.
     * @return boolean User in that role
     */
    public boolean isUserInRole(String role) {
        boolean ret = false;

        if (getSecurity() != null) {
            ret = getSecurity().isUserInRole(role);
        }

        return ret;
    }

    /**
     * Returns the validate flag set in the config file is set to.
     * @return boolean the validate flag
     */
    public boolean isValidate() {
        return this.validate;
    }

    /**
     * Sets the validate.
     * @param validate The validate to set
     */
    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    /**
     * Returns the inputView.
     * @return String
     */
    public String getInputView() {
        return this.inputView;
    }

    /**
     * Sets the inputView.
     * @param inputView The inputView to set
     */
    public void setInputView(String inputView) {
        this.inputView = inputView;
    }

    /**
     * Returns an String array of all the roles allowed for that eventMapping.
     * @return String[] String array of roles
     */
    public String[] getRoles() {
        final String[] TYPE = new String[0];

        if (this.security != null) {
            return this.security.getRoles();
        }

        return TYPE;
    }

    /**
     * Returns the cancelView.
     * @return String
     */
    public String getCancelView() {
        return this.cancelView;
    }

    /**
     * Sets the cancelView.
     * @param cancelView The cancelView to set
     */
    public void setCancelView(String cancelView) {
        this.cancelView = cancelView;
    }
}