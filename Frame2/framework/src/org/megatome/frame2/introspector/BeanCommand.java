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
package org.megatome.frame2.introspector;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;


/**
 * Defines basic operations for operating on a bean.
 */
abstract class BeanCommand {
   private static BeanCommand SIMPLE_COMMAND = new SimpleCommand();
   private static BeanCommand INDEXED_COMMAND = new IndexedCommand();

   /**
    * Provide the appropriate instance of a command depending on the key.
    *
    * @param key may represent a simple or indexed property
    *
    * @return BeanCommand
    */
   static BeanCommand instance(String key) {
      if (KeyHelper.isIndexed(key)) {
         return INDEXED_COMMAND;
      } 
      
      return SIMPLE_COMMAND;
   }

   abstract void set(PropertyMapping mapping, Object value)
      throws BeanException;

   abstract Object get(PropertyMapping mapping) throws BeanException;

   /**
    * Method getDescriptors returns all property descriptors for the bean.
    *
    * @param fromBean
    *
    * @return PropertyDescriptor[]
    *
    * @throws BeanException
    */
   protected PropertyDescriptor[] getDescriptors(Object fromBean)
      throws BeanException {
      PropertyDescriptor[] result = null;

      try {
         if (fromBean != null) {
            BeanInfo beanInfo = Introspector.getBeanInfo(fromBean.getClass());

            result = beanInfo.getPropertyDescriptors();
         }
      } catch (IntrospectionException e) {
         String type = "null";
         if ( fromBean != null ) {
            type = fromBean.getClass().getName();
         }
         throw new BeanException("Unable to get property descriptors",type, e);
      }

      return result;
   }

   /**
    * Method getDescriptor returns a specific property descriptor for the property that corresponds
    * to the property name.
    *
    * @param fromBean
    * @param name
    *
    * @return PropertyDescriptor
    *
    * @throws BeanException
    */

   // NIT: Should probably cache the descriptor, need to get some performance
   // measures for the lookup.
   protected PropertyDescriptor getDescriptor(Object fromBean, String name)
      throws BeanException {
      PropertyDescriptor[] descriptors = getDescriptors(fromBean);

      if (KeyHelper.isIndexed(name)) {
         name = name.substring(0, name.length() - 3);
      }

      if (descriptors != null) {
         for (int i = 0; i < descriptors.length; i++) {
            if (name.equals(descriptors[i].getName())) {
               return descriptors[i];
            }
         }
      }

      return null;
   }
}
