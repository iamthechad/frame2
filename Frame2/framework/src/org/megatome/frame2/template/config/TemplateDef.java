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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.megatome.frame2.template.TemplatePlugin;

public class TemplateDef implements Comparable<Object> {
    private String name = ""; //$NON-NLS-1$
    private String path = ""; //$NON-NLS-1$
    private Map<String, String> putParams = new HashMap<String, String>();

    /**
     * @return
     */
    public Map<String, String> getPutParams() {
        return this.putParams;
    }

    public String getPutParam(String paramName) {
        return this.putParams.get(paramName);
    }

    /**
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return
     */
    public String getPath() {
        return this.path;
    }

    public String getTemplateJspPath() {
        return TemplatePlugin.getConfigDir() + getPath();
    }

    /**
     * @param map
     */
    public void setPutParams(Map<String, String> map) {
        this.putParams = new HashMap<String, String>(map);
    }

    /**
     * @param string
     */
    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("unchecked")
	public void overridePutParam(String putName, String putValue,
            PageContext context, int scope) {
        Map<String, String> putMap = null;
        putMap = (Map<String, String>)context.getAttribute(this.name, scope);

        if (putMap != null) {
            putMap.put(putName, putValue);
        } else {
            putMap = new HashMap<String, String>();
            putMap.put(putName, putValue);
            context.setAttribute(this.name, putMap, scope);
        }
    }

    @SuppressWarnings("unchecked")
	public String getPutParam(String paramName, PageContext context) {
        int[] scopeValues = { PageContext.PAGE_SCOPE,
                PageContext.REQUEST_SCOPE, PageContext.SESSION_SCOPE,
                PageContext.APPLICATION_SCOPE };

        String paramValue = null;
        for (int i = 0; i < scopeValues.length; i++) {
            Map<String, String> pp = (Map<String, String>)context.getAttribute(this.name, scopeValues[i]);
            if (pp != null) {
                paramValue = pp.get(paramName);
                if (paramValue != null)
                    break;
            }
        }

        if (paramValue == null) {
            paramValue = getPutParam(paramName);
        }

        if (paramValue != null) {
            paramValue = TemplatePlugin.getConfigDir() + paramValue;
        }

        return paramValue;
    }

    /**
     * @param string
     */
    public void setPath(String path) {
        this.path = path;
    }

    public int compareTo(Object other) {
        return compareTo((TemplateDef)other);
    }

    public int compareTo(TemplateDef other) {
        return this.name.compareTo(other.getName());
    }

    @Override
	public boolean equals(Object other) {
        return (other instanceof TemplateDef) && equals((TemplateDef)other);
    }

    public boolean equals(TemplateDef other) {
        return this.name.equals(other.getName());
    }

}
