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
 package org.megatome.frame2.front.config;

import java.util.HashMap;


/**
 * ExceptionDef is an Object representation of the exception element in the configuration file.
 * It is named ExceptionDef because Exception is already reserved.
 */

public class ExceptionDef implements Comparable {

   private String _key;
   private String _type;
   private HashMap _view = new HashMap();

   /**
    * Constructs an ExceptionDef
    *
    * @param key String
    * @param type String representing the class to instantiate
    *
    */
   public ExceptionDef(String key, String type) {
      _key = key;
      _type = type;
   }

   /**
    * Returns the type.
    *
    * @return String
    */
   public String getType() {
      return _type;
   }

   /**
    * Sets the type.
    *
    * @param type The type to set
    */
   public void setType(String type) {
      _type = type;
   }

   /**
    * Returns the key.
    *
    * @return String
    */
   public String getKey() {
      return _key;
   }

   /**
    * Sets the key.
    *
    * @param key The key to set
    */
   public void setKey(String key) {
      _key = key;
   }

   /**
    * Returns the view.
    *
    * @param type The type (HTML, XML, or Both) to get the view for 
    * 
    * @return String global forward name to return
    */
   public String getView(String type) {
      return (String) _view.get(type);
   }

   /**
    * Sets the view.
    *
    * @param type The type (HTML, XML, or Both) to set the view for 
    * @param forward The forward name to set the view for
    * 
    */
   public void setView(String type, String forward) {
      _view.put(type, forward);
   }

   /**
    * Sets the view.
    *
    * @param map a HashMap of type, forward name to set
    * 
    */
   public void setView(HashMap map) {
      _view = map;
   }

   public Object clone() {
      ExceptionDef def = new ExceptionDef(getKey(), getType());

      def.setView((HashMap) _view.clone());

      return def;
   }

    public int compareTo(Object other) {
        return compareTo((ExceptionDef)other);
    }

    public int compareTo(ExceptionDef other) {
        return _type.compareTo(other.getType( ));
    }

    public boolean equals(Object other) {
        return (other instanceof ExceptionDef) && equals((ExceptionDef)other);
    }

    public boolean equals(ExceptionDef other) {
        return _type.equals(other.getType( ));
    }
}
