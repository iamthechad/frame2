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
package org.megatome.frame2.introspector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerFactory;

/**
 * Implements the Introspector interface.
 */

// DOC: Need to document the nesting conventions and the indexing conventions,
// with emphasis on the bean's conformance to the indexed property JavaBean convention
// i.e. externalized via array... (or is that true, i.e. maybe the introspector only
// uses the individual indexed property, not the aggregate...)
class IntrospectorImpl implements Introspector {
   private static Logger LOGGER = LoggerFactory.instance(IntrospectorImpl.class.getName());

   /**
    * Method mapProperties. Copy values from the map into the bean object.
    *
    * @param fromMap Map of property names and values. The property names are assumed to be String
    *        objects.
    * @param toBean Bean object to
    */
   public void mapProperties(Map fromMap, Object toBean) throws IntrospectorException {
      if ((fromMap == null) || (toBean == null)) {
         return;
      }

      Iterator propertyKeys = fromMap.keySet().iterator();

      Collection mapexcs = null;

      while (propertyKeys.hasNext()) {
         String key = (String) propertyKeys.next();

         if (key != null) {
            try {
               setProperty(toBean, key, fromMap.get(key));
            } catch (MappingException e) {
               if (mapexcs == null) {
                  mapexcs = new ArrayList();
               }

               mapexcs.add(e);
            }
         }
      }

      if (mapexcs != null) {
         LOGGER.severe("Unable to perform mapping due to bean errors:");
         for (Iterator i = mapexcs.iterator(); i.hasNext();) {
            MappingException me = (MappingException)i.next();
            LOGGER.severe("Mapping error for bean [" + me.getBeanName() + "." + me.getProperty() +"]: " + me.getMessage(), me);
         }
         throw new MappingsException("Unable to perform mapping due to bean errors", mapexcs);
      }
   }

   private void setProperty(Object inBean, String key, Object value) throws IntrospectorException {

      PropertyMapping mapping = getPropertyMapping(inBean, key);

      if (mapping != null) {
         BeanCommand command = BeanCommand.instance(mapping.getKey());

         //value = extractFromArray(value);

         if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(
               "Setting property [" + key + "," + value + "] in " + inBean.getClass().getName());
         }

         command.set(mapping, value);
      } else {
         LOGGER.debug("Property " + key + " not available in bean");
      }
   }
/*
   private Object extractFromArray(Object value) {
      if (value instanceof Object[]) {
         Object[] array = (Object[]) value;
         if (array.length > 0) {
            value = array[0];
         }
      }
      return value;
   }
*/
   private PropertyMapping getPropertyMapping(Object bean, String key)
      throws IntrospectorException {
      PropertyMapping result = null;

      // NIT: it would be nice to push the nested dependence out
      if (KeyHelper.isNested(key)) {
         String topKey = KeyHelper.getTopKey(key);
         BeanCommand command = BeanCommand.instance(topKey);
         Object nextBean = command.get(getPropertyMapping(bean, topKey));

         result = getPropertyMapping(nextBean, KeyHelper.getRemainderKey(key));
      } else {
         result = new PropertyMapping(bean, key);
      }

      return result;
   }
}
