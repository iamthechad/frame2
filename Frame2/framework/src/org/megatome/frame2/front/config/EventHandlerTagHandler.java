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
 * EventHandlerTagHandler handles the eventHandler elements of the Configuration file.
 */
class EventHandlerTagHandler implements ElementHandler {
   public static final String NAME = "name";
   public static final String TYPE = "type";
   private InitParamTagHandler _inputParamTagHandler;
   private ForwardTagHandler _forwardTagHandler;
   private HashMap _eventHandlers = new HashMap();
   private EventHandlerDef _eventHandler;

   /**
    * Constructs an EventHandlerTagHandler.
    *
    * @param InitParamTagHandler 
    * @param ForwardTagHandler 
    *
    */

   EventHandlerTagHandler(InitParamTagHandler inputParamTagHandler,
      ForwardTagHandler forwardTagHandler) {
      _inputParamTagHandler = inputParamTagHandler;
      _forwardTagHandler = forwardTagHandler;
   }

   public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws ParserException {
      _eventHandler = new EventHandlerDef();
      _eventHandler.setName(attributes.getValue(NAME));
      _eventHandler.setType(attributes.getValue(TYPE));
   }

   public void endElement(String uri, String localName, String qName)
      throws ParserException {
      _eventHandler.setXMLForwards(_forwardTagHandler.getXMLForwards());
      _eventHandler.setHTMLForwards(_forwardTagHandler.getHTMLForwards());
      _forwardTagHandler.clear();
      _eventHandler.setInitParams(_inputParamTagHandler.getParams());
      _inputParamTagHandler.clear();
      _eventHandlers.put(_eventHandler.getName(), _eventHandler);
   }

   public void characters(char[] ch, int start, int length)
      throws ParserException {
   }

   /**
    * @return returns a clone of the EventHandlers List
    */
   public HashMap getEventHandlers() {
      HashMap copy = new HashMap();
      Set keys = _eventHandlers.keySet();

      Iterator iter = keys.iterator();

      while (iter.hasNext()) {
         String name = (String) iter.next();
         EventHandlerDef eventHandler = (EventHandlerDef) _eventHandlers.get(name);

         copy.put(name, eventHandler.clone());
      }

      return copy;
   }

   /**
    * clear the EventHandlerDef List
    */
   public void clear() {
      _eventHandlers.clear();
   }
}
