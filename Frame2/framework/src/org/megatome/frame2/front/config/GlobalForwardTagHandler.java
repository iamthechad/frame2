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

import org.megatome.frame2.util.sax.ElementHandler;
import org.megatome.frame2.util.sax.ParserException;
import org.xml.sax.Attributes;


/**
 * GlobalForwardTagHandler handles the Global Forward elements of the Configuration file.
 */
class GlobalForwardTagHandler implements ElementHandler {
   private ForwardTagHandler _forwardTagHandler;
   private HashMap _XMLForwards = new HashMap();
   private HashMap _HTMLForwards = new HashMap();

   /**
    * Constructs an GlobalForwardTagHandler.
    *
    * @param ForwardTagHandler 
    *
    */

   GlobalForwardTagHandler(ForwardTagHandler forwardTagHandler) {
      _forwardTagHandler = forwardTagHandler;
   }

   public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws ParserException {
   }

   public void endElement(String uri, String localName, String qName)
      throws ParserException {
      _XMLForwards = _forwardTagHandler.getXMLForwards();
      _HTMLForwards = _forwardTagHandler.getHTMLForwards();
      _forwardTagHandler.clear();
   }

   public void characters(char[] ch, int start, int length)
      throws ParserException {
   }

   /**
    * @return returns a HashMap of the XML Forwards
    */
   public HashMap getXMLForwards() {
      return _XMLForwards;
   }

   /**
    * @return returns a HashMap of the HTML Forwards
    */
   public HashMap getHTMLForwards() {
      return _HTMLForwards;
   }

   /**
    * clear the Forwards List
    */
   public void clear() {
      _XMLForwards.clear();
      _HTMLForwards.clear();
      _forwardTagHandler.clear();
   }
}
