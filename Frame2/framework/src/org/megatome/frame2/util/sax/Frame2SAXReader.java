/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2007 Megatome Technologies.  All rights
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
package org.megatome.frame2.util.sax;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;


public class Frame2SAXReader extends DefaultHandler {
   private static Logger LOGGER = LoggerFactory.instance(Frame2SAXReader.class.getName());
   private Map<String, ElementHandler> handlers = new HashMap<String, ElementHandler>();
   private List<String> elementsWithNoHandlers = new ArrayList<String>();
   private Stack<ElementHandler> stack = new Stack<ElementHandler>();

   public void setElementHandler(String name, ElementHandler handler) {
      this.handlers.put(name, handler);
   }

   public void setElement(String name) {
      this.elementsWithNoHandlers.add(name);
   }

   public void parse(InputStream io) throws ParserException {
      try {
         SAXParserFactory factory = SAXParserFactory.newInstance();
         factory.setValidating(true);
         factory.setNamespaceAware(true);
         SAXParser parser = factory.newSAXParser();
         XMLReader reader = parser.getXMLReader();

         reader.setContentHandler(this);
         reader.setErrorHandler(new Frame2ParseErrorHandler());
         reader.setEntityResolver(new Frame2EntityResolver());

         InputSource is = new InputSource(io);

         reader.parse(is);
      } catch (ParserConfigurationException ex) {
         throw new ParserException(ex);
      } catch (SAXException ex) {
         throw new ParserException(ex);
      } catch (IOException ex) {
         throw new ParserException(ex);
      }
   }

   @Override
public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws ParserException {
      ElementHandler handler = this.handlers.get(qName);

      if (handler == null) {
         if (!this.elementsWithNoHandlers.contains(qName)) {
            LOGGER.severe("Frame2XMLReader Handler not found " + qName); //$NON-NLS-1$

            return;
         }
      }

      this.stack.push(handler);

      if (handler != null) {
         handler.startElement(uri, localName, qName, attributes);
      }
   }

   @Override
public void endElement(String uri, String localName, String qName)
      throws ParserException {
      ElementHandler handler = this.stack.pop();

      if (handler != null) {
         try {
            handler.endElement(uri, localName, qName);
         } catch (SAXException ex) {
            throw new ParserException(ex);
         }
      }
   }

   @Override
public void characters(char[] ch, int start, int length)
      throws ParserException {
      ElementHandler handler = this.stack.peek();

      if (handler != null) {
         try {
            handler.characters(ch, start, length);
         } catch (SAXException ex) {
            throw new ParserException(ex);
         }
      }
   }
}
