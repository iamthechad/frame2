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
 package org.megatome.frame2.front.config;

import java.util.ArrayList;


/**
 * This file is mainly used as an enumeration for the ResolveType (parent, children, passthru)
 */
public class ResolveType {
   private static Class __class = ResolveType.class;
   private static int __instance;
   private static ArrayList __set = new ArrayList();

   // enumeration value declarations
   public static ResolveType PARENT = new ResolveType(__instance++, "parent", "");
   public static ResolveType CHILDREN = new ResolveType(__instance++, "children", "");
   public static ResolveType PASSTHRU = new ResolveType(__instance++, "passthru", "");

   //////
   //  INSTANCE VARIABLES
   //////
   private String _mapping;
   private int _ordinal; // unique ordinal value per instance
   private String _value;

   //////
   //  CONSTRUCTORS
   //////
   private ResolveType(int ordinal, String value, String mapping) {
      _ordinal = ordinal;
      _value = value;
      _mapping = mapping;
      __set.add(ordinal, this);
   }

   //////
   //  CLASS METHODS
   //////

   /**
    * Method <code>getSize</code> returns total number of values in enum.
    *
    * @return <code>int</code> size of enumeration set
    */
   public static int getSize() {
      return __instance;
   }

   /**
    * Method <code>getValueByOrdinal</code> returns enum instance that matches ordinal value.
    *
    * @param ordinal a value of type <code>int</code>
    *
    * @return <code>ResolveType</code> value, possibly null if ordinal out of range
    */
   public static ResolveType getValueByOrdinal(int ordinal) {
      ResolveType result = null;

      if ((ordinal >= 0) && (ordinal < __instance)) {
         result = (ResolveType) __set.get(ordinal);
      }

      return result;
   }

   /**
    * Method <code>getValueByMapping</code> returns enum instance that matches mapping value.
    *
    * @param mapping a value of type <code>String</code>
    *
    * @return <code>ResolveType</code> value, possibly null if mapping value not matched
    */
   public static ResolveType getValueByMapping(String mapping) {
      ResolveType result = null;

      for (int i = 0; i < __set.size(); i++) {
         ResolveType current = (ResolveType) __set.get(i);

         if (current.getMapping().equals(mapping)) {
            result = current;

            break;
         }
      }

      return result;
   }

   /**
    * Method <code>getValueByString</code> returns enum instance that matches String value.
    *
    * @param value a value of type <code>String</code>
    *
    * @return <code>ResolveType</code> value, possibly null if String value not matched
    */
   public static ResolveType getValueByString(String value) {
      ResolveType result = null;

      for (int i = 0; i < __set.size(); i++) {
         ResolveType current = (ResolveType) __set.get(i);

         if (current.toString().equals(value)) {
            result = current;

            break;
         }
      }

      return result;
   }

   //////
   //  ACCESSORS
   //////

   /**
    * Method <code>equals</code> determines if this enumeration value is equal to the supplied
    * object.
    *
    * @param target a value of type <code>Object</code>
    *
    * @return <code>boolean</code> value
    */
   public boolean equals(Object target) {
      boolean result = false;

      if (__class.isAssignableFrom(target.getClass())) {
         result = equals((ResolveType) target);
      }

      return result;
   }

   /**
    * Method <code>equals</code> determines if two enum instances are equal.
    *
    * @param enum a value of type <code>ResolveType</code>
    *
    * @return <code>boolean</code> value
    */
   public boolean equals(ResolveType enum) {
      return (_ordinal == enum._ordinal);
   }

   public String getMapping() {
      return _mapping;
   }

   /**
    * Method <code>getOrdinal</code> returns ordinal of enum value.
    *
    * @return <code>int</code> ordinal value
    */
   public int getOrdinal() {
      return _ordinal;
   }

   public String toString() {
      return _value;
   }
}


// end of class ResolveType
