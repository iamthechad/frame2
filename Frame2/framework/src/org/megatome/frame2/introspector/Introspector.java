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

import java.util.Map;


/**
 * Introspector defines the interface for mapping property data (in the form of String keys and
 * data) to a bean.  The introspector maps both single and indexed properties, as defined in the
 * JavaBeans specification.  An example of a single property mapping is the key/value pair:<br>
 * <br>
 * key <code>'foo'</code>, value <code>'15'</code><br>
 * <br>
 * can be mapped through the <code>setFoo(String val)</code> setter on a bean.  Any type on the
 * setter is valid as long as the value can be successfully parsed (for example, '56.77' can be
 * parsed to an double or float but 'Ad4 b' can not).<br>
 * <br>
 * Indexed properties are also supported.  The property must be of the form <code>bar[i]</code> where
 * <code>i</code> is a valid index for the property <code>bar</code>.<br>
 * <br>
 * The introspector also supports nested properties, so that the key may be formed as <code>foo.bar[i].name</code>
 * to correspond to the bean property <code>bean.getFoo().getBar(i).getName()</code>.
 */
public interface Introspector {
   /**
    * Map a set of properties to a bean. The fromMap contains property names and values; any that
    * correspond to properties on the bean are mapped.  If a map entry does not correspond the
    * introspector skips it.  Conversely, there is no check that all properties on the bean are
    * mapped.  If an error occurs that is specific to the mapping of a single property, it will be
    * reported as part of a MappingsException.  This includes conversion errors.
    *
    * @param fromMap A map of property names and values.
    * @param toBean
    *
    * @throws IntrospectorException
    * @throws MappingsException
    */
   public void mapProperties(Map fromMap, Object toBean)
      throws IntrospectorException, MappingsException;
}
