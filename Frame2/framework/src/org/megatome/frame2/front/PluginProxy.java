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
package org.megatome.frame2.front;

import java.util.Map;

import org.megatome.frame2.front.config.PluginDef;
import org.megatome.frame2.plugin.PluginInterface;

public class PluginProxy implements Comparable<Object> {
    private PluginDef pluginDef;
    private PluginInterface plugin;
    private boolean initThrewException = false;

    public PluginProxy(PluginDef pluginDef, PluginInterface plugin) {
        this.pluginDef = pluginDef;
        this.plugin = plugin;
    }

    public String getName() {
        return this.pluginDef.getName();
    }

    public String getType() {
        return this.pluginDef.getType();
    }

    public Map<String, String> getInitParams() {
        return this.pluginDef.getInitParams();
    }

    public PluginInterface getPlugin() {
        return this.plugin;
    }

    public void setInitThrewException(boolean initThrewException) {
        this.initThrewException = initThrewException;
    }

    public boolean initThrewException() {
        return this.initThrewException;
    }

    public int compareTo(Object other) {
        return this.pluginDef.compareTo(other);
    }

    @Override
	public boolean equals(Object other) {
        if ((other == null) || (!(other instanceof PluginProxy))) {
            return false;
        }
        
        if (other == this) {
            return true;
        }
        
        return this.pluginDef.equals(other);
    }

    @Override
	public int hashCode() {
        return this.pluginDef.hashCode();
    }
}