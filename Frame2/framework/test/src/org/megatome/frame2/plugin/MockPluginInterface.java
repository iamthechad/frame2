/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2006 Megatome Technologies.  All rights
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
package org.megatome.frame2.plugin;

import java.util.Map;

import javax.servlet.ServletContext;

import org.megatome.frame2.plugin.PluginException;
import org.megatome.frame2.plugin.PluginInterface;

public class MockPluginInterface implements PluginInterface {
    
    public static final int STATE_INIT = 1;
    public static final int STATE_DESTROY = 2;
    public static final int STATE_THROW = 3;
    public static final int STATE_NONE = -1;
    
   private int state;
   
   public MockPluginInterface(){
      state = STATE_NONE;
   }
   
   public MockPluginInterface(int startIndex)
   {
       state = startIndex;
   }

	public void destroy(ServletContext context, Map initParams) throws PluginException {
     if (context == null) throw new PluginException("Plugin Init Exception");
     if (initParams.get("throwsDestroyParam") != null){
         state = STATE_THROW; // value if throw
         throw new PluginException("got throwsDestoryParam, throw for test");
     }
     state = STATE_DESTROY;
	}

	public void init(ServletContext context, Map initParams) throws PluginException {
      if (context == null) throw new PluginException("Plugin Init Exception");
      if (initParams.get("throwsParam") != null){
         throw new PluginException("got throwsParam, throw for test");
      }
      
      state = STATE_INIT;
	}

    /**
     * @return
     */
    public int getState() {
    	return state;
    }

}
