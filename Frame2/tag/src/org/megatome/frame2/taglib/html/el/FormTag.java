/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004 Megatome Technologies.  All rights
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
package org.megatome.frame2.taglib.html.el;

import org.megatome.frame2.taglib.html.Constants;

/**
 * 
 */
public class FormTag extends BaseBodyTag {

   protected void setType() {
      _type = Constants.FORM;
   }   
   
   public void setTagName() {
      _tagName = Constants.FORM_TAG;
   }
 
   public void setAction(String action) {
      setAttr(Constants.ACTION, action);
   }  

   public void setAccept(String accept) {
      setAttr(Constants.ACCEPT, accept);
   }  
   
   public void setAcceptcharset(String acceptcharset) {
      setAttr(Constants.ACCEPT_CHARSET, acceptcharset);
   }  
   
   public void setEnctype(String enctype) {
      setAttr(Constants.ENCTYPE, enctype);
   } 

   public void setMethod(String method) {
      setAttr(Constants.METHOD, method);
   } 
   
   public void setOnreset(String onreset) {
      setAttr(Constants.ON_RESET, onreset);
   }  

   public void setOnsubmit(String onsubmit) {
      setAttr(Constants.ON_SUBMIT, onsubmit);
   }    
   
   public void setTarget(String target) {
      setAttr(Constants.TARGET, target);
   }                  
   /**
    * Appends end Element Close
    * @param StringBuffer
    */
   public void getEndElementClose(StringBuffer buffer) {
      buffer.append(Constants.FORM_CLOSE);     
   }
}

