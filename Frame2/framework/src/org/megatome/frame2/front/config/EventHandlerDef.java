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

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * EventHandlerDef is an Object representation of the eventHandler element in the configuration file.
 * It is named EventHandlerDef and not EventHandler because the framework contains an interface called EventHandler.
 */
public class EventHandlerDef {
   private String _name = "";
   private String _type = "";
   private HashMap _initParams = new HashMap();
   private HashMap _HTMLForwards = new HashMap();
   private HashMap _XMLForwards = new HashMap();

   /**
    * Returns the name.
    *
    * @return String the name of the EventHandlerDef
    */
   public String getName() {
      return _name;
   }

   /**
    * Returns the type.
    *
    * @return String the type of the EventHandlerDef
    */
   public String getType() {
      return _type;
   }

   /**
    * Sets the name.
    *
    * @param name The name to set
    */
   public void setName(String name) {
      _name = name;
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
    * Returns an XML forward.
    *
    * @param name The name of the XML Forward to get
    *
    * @return XML Forward
    */
   public Forward getXMLForward(String name) {
      return (Forward) _XMLForwards.get(name);
   }

   /**
    * Sets the XML Forwards.
    *
    * @param forwards HashMap containing all the EventHandlerDef XML Forwards
    */
   public void setXMLForwards(HashMap forwards) {
      _XMLForwards = forwards;
   }

   /**
    * Returns an HTML forward.
    *
    * @param name The name of the HTML Forward to get
    *
    * @return HTML Forward
    */
   public Forward getHTMLForward(String name) {
      return (Forward) _HTMLForwards.get(name);
   }

   /**
    * Sets the HTML Forwards.
    *
    * @param forwards HashMap containing all the EventHandlerDef HTML Forwards
    */
   public void setHTMLForwards(HashMap forwards) {
      _HTMLForwards = forwards;
   }

   /**
    * Sets the EventHandlerDef Init Params.
    *
    * @param params HashMap containing all the EventHandlerDef Init Params.
    */
   public void setInitParams(HashMap params) {
      _initParams = params;
   }

   /**
    * Returns an Map of the Init Params.
    *
    * @return Map
    */
   public Map getInitParams() {
      return Collections.unmodifiableMap(_initParams);
   }

   /**
    * Returns an Init Param.
    *
    * @return String
    */
   public String getInitParam(String name) {
      return (String) _initParams.get(name);
   }

   /**
    * Adds an Init Param to the Map of Init Params.
    *
    * @param name String
    * @param name value
    */
   public void addInitParam(String name, String value) {
      _initParams.put(name, value);
   }

   public Object clone() {
      EventHandlerDef eh = new EventHandlerDef();

      eh.setName(_name);
      eh.setType(_type);
      eh.setInitParams((HashMap) _initParams.clone());
      eh.setHTMLForwards(getForwards(_HTMLForwards));
      eh.setXMLForwards(getForwards(_XMLForwards));

      return eh;
   }

   /**
    *
    * @param forwards HashMap
    * 
    * @return returns a clone of the Forwards Hashmap
    */
   private HashMap getForwards(HashMap forwards) {
      HashMap copy = new HashMap();
      Set keys = forwards.keySet();

      Iterator iter = keys.iterator();

      while (iter.hasNext()) {
         String name = (String) iter.next();
         Forward forward = (Forward) forwards.get(name);

         copy.put(name, forward.clone());
      }

      return copy;
   }
}
