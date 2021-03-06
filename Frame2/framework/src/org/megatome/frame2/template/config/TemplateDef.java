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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

public class TemplateDef implements Comparable<Object>, Serializable, TemplateDefI {
	private static final long serialVersionUID = -2825779862791803265L;
	private String name = ""; //$NON-NLS-1$
    private String path = ""; //$NON-NLS-1$
    private String configDir;
    private Map<String, String> putParams = new HashMap<String, String>();
    
    public void setConfigDir(final String configDir) {
    	this.configDir = configDir;
    }

    /* (non-Javadoc)
	 * @see org.megatome.frame2.template.config.TemplateDefI#getPutParams()
	 */
    public Map<String, String> getPutParams() {
        return this.putParams;
    }

    /* (non-Javadoc)
	 * @see org.megatome.frame2.template.config.TemplateDefI#getPutParam(java.lang.String)
	 */
    public String getPutParam(String paramName) {
        return this.putParams.get(paramName);
    }

    /* (non-Javadoc)
	 * @see org.megatome.frame2.template.config.TemplateDefI#getName()
	 */
    public String getName() {
        return this.name;
    }

    /* (non-Javadoc)
	 * @see org.megatome.frame2.template.config.TemplateDefI#getPath()
	 */
    public String getPath() {
        return this.path;
    }

    /* (non-Javadoc)
	 * @see org.megatome.frame2.template.config.TemplateDefI#getTemplateJspPath()
	 */
    public String getTemplateJspPath() {
        return this.configDir + getPath();
    }

    /* (non-Javadoc)
	 * @see org.megatome.frame2.template.config.TemplateDefI#setPutParams(java.util.Map)
	 */
    public void setPutParams(Map<String, String> map) {
        this.putParams = new HashMap<String, String>(map);
    }

    /* (non-Javadoc)
	 * @see org.megatome.frame2.template.config.TemplateDefI#setName(java.lang.String)
	 */
    public void setName(String name) {
        this.name = name;
    }

    /* (non-Javadoc)
	 * @see org.megatome.frame2.template.config.TemplateDefI#overridePutParam(java.lang.String, java.lang.String, javax.servlet.jsp.PageContext, int)
	 */
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

    /* (non-Javadoc)
	 * @see org.megatome.frame2.template.config.TemplateDefI#getPutParam(java.lang.String, javax.servlet.jsp.PageContext)
	 */
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
            paramValue = this.configDir + paramValue;
        }

        return paramValue;
    }

    /* (non-Javadoc)
	 * @see org.megatome.frame2.template.config.TemplateDefI#setPath(java.lang.String)
	 */
    public void setPath(String path) {
        this.path = path;
    }

    public int compareTo(Object other) {
        TemplateDef td = (TemplateDef)other;
        return this.name.compareTo(td.name);
    }

    public int compareTo(TemplateDefI other) {
        return this.name.compareTo(other.getName());
    }

    @Override
	public boolean equals(Object other) {
    	if (!(other instanceof TemplateDef)) {
    		return false;
    	}
    	
    	if (other == this) {
    		return true;
    	}
    	
    	TemplateDef td = (TemplateDef)other;
    	return this.name.equals(td.name);
    }
    
    @Override
	public int hashCode() {
    	return this.name.hashCode();
    }
}
