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
import java.util.Iterator;
import java.util.Set;

import org.megatome.frame2.util.sax.ElementHandler;
import org.megatome.frame2.util.sax.ParserException;
import org.xml.sax.Attributes;


/**
 * ForwardTagHandler handles the forward elements of the Configuration file.
 */
class ForwardTagHandler implements ElementHandler {
   // attribute consts
   public static final String NAME = "name";
   public static final String TYPE = "type";
   public static final String PATH = "path";
   public static final String INVALID_TYPE = "Error: Invalid forward type ";

   // 2 maps needed, one for XML forwards
   // 1 for html forwards, so each forward name
   // can be identical for both.  Events are stored
   // in both for ease of access.
   HashMap _forwards = new HashMap();
   HashMap _XMLForwards = new HashMap();
   Forward _forward;

   public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws ParserException {
      String name = attributes.getValue(NAME);
      String type = attributes.getValue(TYPE);
      ForwardType ftype = ForwardType.getValueByString(type);

      if (ftype == null) {
         throw new ParserException(INVALID_TYPE + name + " type " + type);
      }

      Forward _forward = new Forward(name, ftype, attributes.getValue(PATH));

      if (ftype.equals(ForwardType.HTMLRESOURCE)) {
         _forwards.put(name, _forward);
      } else if (ftype.equals(ForwardType.XMLRESOURCE) || ftype.equals(ForwardType.XMLRESPONDER)) {
         _XMLForwards.put(name, _forward);
      } else if (ftype.equals(ForwardType.EVENT)) {
         // put event in both maps 
         // for easier lookup by type.
         _forwards.put(name, _forward);
         _XMLForwards.put(name, _forward);
      }
   }

   public void endElement(String uri, String localName, String qName)
      throws ParserException {
   }

   public void characters(char[] ch, int start, int length)
      throws ParserException {
   }

   /**
    * @return returns a clone of the Forwards List
    */
   public HashMap getForwards(HashMap map) {
      HashMap copy = new HashMap();
      Set keys = map.keySet();

      Iterator iter = keys.iterator();

      while (iter.hasNext()) {
         String name = (String) iter.next();
         Forward forward = (Forward) map.get(name);

         copy.put(name, forward.clone());
      }

      return copy;
   }

   /**
    * @return returns a HashMap of the XMLForwards
    */
   public HashMap getXMLForwards() {
      return getForwards(_XMLForwards);
   }

   /**
    * @return returns a HashMap of the HTMLForwards
    */
   public HashMap getHTMLForwards() {
      return getForwards(_forwards);
   }

   /**
    * clear the Forwards List
    */
   public void clear() {
      _forwards.clear();
      _XMLForwards.clear();
   }
}
