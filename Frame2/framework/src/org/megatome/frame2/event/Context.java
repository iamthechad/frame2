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
package org.megatome.frame2.event;

import javax.servlet.ServletContext;

import org.megatome.frame2.errors.Errors;


/**
 * A context wraps request and session scope data in a protocol neutral fashion.  The event handler
 * can use this wrapper to place and retrieve data from the underlying protocol request and
 * session objects.  The context also provides access to the request Errors object. This object
 * accumulates error data regarding the request such that it may be provided back to the client.
 */

public interface Context {
   /**
    * Returns the initialization parameter for the event handler.  The initialization parameters
    * are specified for a specific handler in the configuration. The context will contain the
    * specific initialization parameters for a handler when the handler's handle  method is
    * called.
    *
    * @param key The key of the parameter as configured.  If no parameter has been configured
    * for the key then null is returned.
    *
    * @return String The configured value for that key.
    */
   public String getInitParameter(String key);

   /**
    * Returns the value of the named request attribute as an Object, or null if no attribute of the
    * given key exists.
    *
    * @param key The key of the request attribute to look up.
    *
    * @return The value associated with the key, or null if there is none.
    */
   public Object getRequestAttribute(String key);

   /**
    * Stores an attribute in the request. Attributes are reset between requests.  If an object of
    * the same name is already bound to the session, the object is replaced.
    *
    * @param key The key this attribute will be stored under.
    * @param value The value to associate with the key.
    */
   public void setRequestAttribute(String key, Object value);

   /**
    * Stores an attribute in the request and the key in the ContextImpl List.  Attributes are reset
    * between requests.  If an object of the same name  is already bound to the session, the
    * object is replaced.
    *
    * @param key The key this attribute will be stored under.
    * @param value The value to associate with the key.
    * @param redirectAttr If true, sets this key and value into the redirect attributes as well.
    */
   public void setRequestAttribute(String key, Object value, boolean redirectAttr);

   /**
    * Removes an attribute from the request.
    *
    * @param key The key of the object to remove.
    */
   public void removeRequestAttribute(String key);

   /**
    * Returns the value of the named session attribute as an Object, or null if no attribute of the
    * given key exists.
    *
    * @param key The key of the attribute to look up.
    *
    * @return The value associated with the key, or null if none found.
    */
   public Object getSessionAttribute(String key);

   /**
    * Stores an attribute in the session. If an object of the same name is already bound to the
    * session, the object is replaced.  requests.
    *
    * @param key The key the value is to be stored under.
    * @param value The value to be associated with the key.
    */
   public void setSessionAttribute(String key, Object value);

   /**
    * Removes an attribute from the session.
    *
    * @param key The key of the attribute to remove.
    */
   public void removeSessionAttribute(String key);

   /**
    * Returns the errors object for this request.
    *
    * @return An Errors object
    * @see org.megatome.frame2.errors.Errors
    */
   public Errors getRequestErrors();
   
   /**
    * Return the servlet context
    * 
    * @return The current servlet context
    * @see javax.servlet.ServletContext
    */
   public ServletContext getServletContext();
}
