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

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerFactory;

/**
 * Executes introspective sets and gets on simple (i.e. not indexed) bean properties.
 */
class SimpleCommand extends BeanCommand {
   private static Logger LOGGER = LoggerFactory.instance(SimpleCommand.class.getName());

	void set(PropertyMapping mapping, Object value) throws BeanException {
		PropertyDescriptor descriptor = getDescriptor(mapping.getBean(), mapping.getKey());

		if (descriptor != null) {
			Method setMethod = descriptor.getWriteMethod();
			Class type = descriptor.getPropertyType();

			try {
				Object convertedValue = null;
				Class arrayRef = type.getComponentType();
				if (value instanceof Object[]) {
					Object[] values = (Object[])value;
					if (arrayRef == null) {
						convertedValue = Converter.convertValueToType(values[0], type);
					} else {
						for (int i = 0; i < values.length; i++) {
								values[i] = Converter.convertValueToType(values[i], type);
						}
						convertedValue = values;
					}
				} else {
					if (arrayRef == null) {
							convertedValue = Converter.convertValueToType(value, type);
					} else {
						convertedValue = Converter.convertValueToArrayType(value, type);
					}
				}
				
				setMethod.invoke(mapping.getBean(), new Object[] { convertedValue });
			} catch (Exception e) {
				throw new MappingException("Unable to set property", mapping, value, e);
			}
		} else {
         LOGGER.info("Unable to locate descriptor for property " + mapping.getKey());
		}
	}

	Object get(PropertyMapping mapping) throws BeanException {
		PropertyDescriptor descriptor = getDescriptor(mapping.getBean(), mapping.getKey());

		if (descriptor != null) {
			try {
				Method getMethod = descriptor.getReadMethod();

				return getMethod.invoke(mapping.getBean(), null);
			} catch (Exception e) {
				throw new MappingException("Unable to get property", mapping, e);
			}
		} else {
         LOGGER.info("Unable to locate descriptor for property " + mapping.getKey());
			return null;
		}
	}
}
