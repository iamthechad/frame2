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
package org.megatome.frame2.introspector;

import org.megatome.frame2.Globals;



/**
 * Indicates an error when trying to map data to a bean property. When a failure occurs the
 * exception records the bean name (the fully qualified class name), the property name used to
 * access the property, the value that was mapped (if known), and the key.
 *
 * @see org.megatome.frame2.introspector.MappingException#getKey
 */
public class MappingException extends BeanException {
   private PropertyMapping _mapping;
   private Object _value = "[unknown]";

   MappingException(String message, PropertyMapping mapping) {
      super(message);
      _mapping = mapping;
   }

   MappingException(String message, PropertyMapping mapping, Throwable t) {
      super(message, t);
      _mapping = mapping;
   }

   MappingException(String message, PropertyMapping mapping, Object value) {
      super(message);
      _mapping = mapping;
      _value = value;
   }

   MappingException(String message, PropertyMapping mapping, Object value, Throwable t) {
      super(message, t);
      _mapping = mapping;
      _value = value;
   }

   /**
    * Returns the class name of the bean.
    * @return Bean name
    */
   public String getBeanName() {
      String result = null;

      if (_mapping != null) {
         result = _mapping.getBean().getClass().getName();
      }

      return result;
   }

   /**
    * Returns the name of the property that generated the error (for example, 'foo' for the bean
    * property <code>getFoo()</code>).
    * @return Property name
    */
   public String getProperty() {
      String result = null;

      if (_mapping != null) {
         result = _mapping.getKey();
      }

      return result;
   }

   /**
    * Returns the value that was used in trying to set the property.  If the value is not known or
    * relevent (for example, the error was generated in trying to locate the property on the bean)
    * than a string with value '[unknown]' is returned.
    * @return As above
    */
   public Object getValue() {
      return _value;
   }

   /**
    * Return the key for the property. The key's value is composed of a framework-determined
    * prefix, the bean name, and the property name. For example, if the framework is unable to map
    * a value to the property <code>foo</code> on the bean <code>com.mycompany.BigEvent</code>
    * then the key will be:<br>
    * <br>
    * <code>frame2.mapping.com.mycompany.BigEvent.foo</code>
    * @return As above
    */
   public String getKey() {
      if (_mapping != null) {
         StringBuffer result = new StringBuffer(Globals.MAPPING_KEY_PREFIX);

         result.append(getBeanName());
         result.append(".");
         result.append(_mapping.getKey());

         return result.toString();
      }
      
      return null;
   }
}
