/*
 * ====================================================================
 * 
 * Frame2 Open Source License
 * 
 * Copyright (c) 2004 Megatome Technologies. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * 3. The end-user documentation included with the redistribution, if any, must
 * include the following acknowlegement: "This product includes software
 * developed by Megatome Technologies." Alternately, this acknowlegement may
 * appear in the software itself, if and wherever such third-party
 * acknowlegements normally appear.
 * 
 * 4. The names "The Frame2 Project", and "Frame2", must not be used to endorse
 * or promote products derived from this software without prior written
 * permission. For written permission, please contact
 * iamthechad@sourceforge.net.
 * 
 * 5. Products derived from this software may not be called "Frame2" nor may
 * "Frame2" appear in their names without prior written permission of Megatome
 * Technologies.
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL MEGATOME
 * TECHNOLOGIES OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 */
package org.megatome.frame2.model;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.megatome.frame2.Frame2Plugin;

public class Frame2Config {

   private GlobalForwards _GlobalForwards;

   private Frame2Events _Frame2Events;

   private EventMappings _EventMappings;

   private EventHandlers _EventHandlers;

   private Exceptions _Exceptions;

   private Plugins _Plugins;

   private RequestProcessors _RequestProcessors;

   public Frame2Config() {
   }

   // Deep copy
   public Frame2Config(Frame2Config source) {
      _GlobalForwards = new GlobalForwards(source._GlobalForwards);
      _Frame2Events = new Frame2Events(source._Frame2Events);
      _EventMappings = new EventMappings(source._EventMappings);
      _EventHandlers = new EventHandlers(source._EventHandlers);
      _Exceptions = new Exceptions(source._Exceptions);
      _Plugins = new Plugins(source._Plugins);
      _RequestProcessors = new RequestProcessors(source._RequestProcessors);
   }

   // This attribute is optional
   public void setGlobalForwards(GlobalForwards value) {
      _GlobalForwards = value;
   }

   public GlobalForwards getGlobalForwards() {
      return _GlobalForwards;
   }

   // This attribute is optional
   public void setFrame2Events(Frame2Events value) {
      _Frame2Events = value;
   }

   public Frame2Events getFrame2Events() {
      return _Frame2Events;
   }

   // This attribute is optional
   public void setEventMappings(EventMappings value) {
      _EventMappings = value;
   }

   public EventMappings getEventMappings() {
      return _EventMappings;
   }

   // This attribute is optional
   public void setEventHandlers(EventHandlers value) {
      _EventHandlers = value;
   }

   public EventHandlers getEventHandlers() {
      return _EventHandlers;
   }

   // This attribute is optional
   public void setExceptions(Exceptions value) {
      _Exceptions = value;
   }

   public Exceptions getExceptions() {
      return _Exceptions;
   }

   // This attribute is optional
   public void setPlugins(Plugins value) {
      _Plugins = value;
   }

   public Plugins getPlugins() {
      return _Plugins;
   }

   // This attribute is optional
   public void setRequestProcessors(RequestProcessors value) {
      _RequestProcessors = value;
   }

   public RequestProcessors getRequestProcessors() {
      return _RequestProcessors;
   }

   public void write(OutputStream out) throws IOException {
      write(out, null);
   }

   public void write(OutputStream out, String encoding) throws IOException {
      Writer w;
      if (encoding == null) {
         encoding = Frame2Plugin.getResourceString("Frame2Model.utf8"); //$NON-NLS-1$
      }
      w = new BufferedWriter(new OutputStreamWriter(out, encoding));
      write(w, encoding);
      w.flush();
   }

   // Print this Java Bean to @param out including an XML header.
   // @param encoding is the encoding style that @param out was opened with.
   public void write(Writer out, String encoding) throws IOException {
      out.write(Frame2Plugin.getResourceString("Frame2Model.xmlTagStart")); //$NON-NLS-1$
      if (encoding != null) out.write(Frame2Plugin.getResourceString("Frame2Model.encodingAttribute") + encoding + Frame2Plugin.getResourceString("Frame2Model.attributeValueEnd")); //$NON-NLS-1$ //$NON-NLS-2$
      out.write(Frame2Plugin.getResourceString("Frame2Model.xmlTagEnd")); //$NON-NLS-1$

      // We need to manually output the DTD - sigh
      out.write(Frame2Plugin.getResourceString("Frame2Model.frame2DocTypeStart")); //$NON-NLS-1$
      out
            .write(Frame2Plugin.getResourceString("Frame2Model.frame2DocTypeSystemID")); //$NON-NLS-1$
      out
            .write(Frame2Plugin.getResourceString("Frame2Model.frame2DocTypePublicID")); //$NON-NLS-1$

      writeNode(out, Frame2Plugin.getResourceString("Frame2Model.frame2-config"), ""); //$NON-NLS-1$ //$NON-NLS-2$
   }

   public void writeNode(Writer out, String nodeName, String indent)
         throws IOException {
      out.write(indent);
      out.write(Frame2Plugin.getResourceString("Frame2Model.tagStart")); //$NON-NLS-1$
      out.write(nodeName);
      out.write(Frame2Plugin.getResourceString("Frame2Model.tagFinish")); //$NON-NLS-1$
      String nextIndent = indent + Frame2Plugin.getResourceString("Frame2Model.indentTabValue"); //$NON-NLS-1$
      if (_GlobalForwards != null) {
         _GlobalForwards.writeNode(out, Frame2Plugin.getResourceString("Frame2Model.global-forwards"), nextIndent); //$NON-NLS-1$
      }
      if (_Frame2Events != null) {
         _Frame2Events.writeNode(out, Frame2Plugin.getResourceString("Frame2Model.events"), nextIndent); //$NON-NLS-1$
      }
      if (_EventMappings != null) {
         _EventMappings.writeNode(out, Frame2Plugin.getResourceString("Frame2Model.event-mappings"), nextIndent); //$NON-NLS-1$
      }
      if (_EventHandlers != null) {
         _EventHandlers.writeNode(out, Frame2Plugin.getResourceString("Frame2Model.event-handlers"), nextIndent); //$NON-NLS-1$
      }
      if (_Exceptions != null) {
         _Exceptions.writeNode(out, Frame2Plugin.getResourceString("Frame2Model.exceptions"), nextIndent); //$NON-NLS-1$
      }
      if (_Plugins != null) {
         _Plugins.writeNode(out, Frame2Plugin.getResourceString("Frame2Model.plugins"), nextIndent); //$NON-NLS-1$
      }
      if (_RequestProcessors != null) {
         _RequestProcessors.writeNode(out, Frame2Plugin.getResourceString("Frame2Model.request-processors"), nextIndent); //$NON-NLS-1$
      }
      out.write(indent);
      out.write(Frame2Plugin.getResourceString("Frame2Model.endTagStart") + nodeName + Frame2Plugin.getResourceString("Frame2Model.tagFinish")); //$NON-NLS-1$ //$NON-NLS-2$
   }

   public static Frame2Config read(InputStream in)
         throws ParserConfigurationException, SAXException, IOException {
      return read(new InputSource(in), false, null, null);
   }

   // Warning: in readNoEntityResolver character and entity references will
   // not be read from any DTD in the XML source.
   // However, this way is faster since no DTDs are looked up
   // (possibly skipping network access) or parsed.
   public static Frame2Config readNoEntityResolver(InputStream in)
         throws ParserConfigurationException, SAXException, IOException {
      return read(new InputSource(in), false, new EntityResolver() {

         public InputSource resolveEntity(String publicId, String systemId) {
            ByteArrayInputStream bin = new ByteArrayInputStream(new byte[0]);
            return new InputSource(bin);
         }
      }, null);
   }

   public static Frame2Config read(InputSource in, boolean validate,
         EntityResolver er, ErrorHandler eh)
         throws ParserConfigurationException, SAXException, IOException {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setValidating(validate);
      DocumentBuilder db = dbf.newDocumentBuilder();
      if (er != null) db.setEntityResolver(er);
      if (eh != null) db.setErrorHandler(eh);
      Document doc = db.parse(in);
      return read(doc);
   }

   public static Frame2Config read(Document document) {
      Frame2Config aFrame2Config = new Frame2Config();
      aFrame2Config.readNode(document.getDocumentElement());
      return aFrame2Config;
   }

   public void readNode(Node node) {
      NodeList children = node.getChildNodes();
      for (int i = 0, size = children.getLength(); i < size; ++i) {
         Node childNode = children.item(i);
         String childNodeName = (childNode.getLocalName() == null ? childNode
               .getNodeName().intern() : childNode.getLocalName().intern());
         String childNodeValue = ""; //$NON-NLS-1$
         if (childNode.getFirstChild() != null) {
            childNodeValue = childNode.getFirstChild().getNodeValue();
         }
         if (childNodeName == Frame2Plugin.getResourceString("Frame2Model.global-forwards")) { //$NON-NLS-1$
            _GlobalForwards = new GlobalForwards();
            _GlobalForwards.readNode(childNode);
         } else if (childNodeName == Frame2Plugin.getResourceString("Frame2Model.events")) { //$NON-NLS-1$
            _Frame2Events = new Frame2Events();
            _Frame2Events.readNode(childNode);
         } else if (childNodeName == Frame2Plugin.getResourceString("Frame2Model.event-mappings")) { //$NON-NLS-1$
            _EventMappings = new EventMappings();
            _EventMappings.readNode(childNode);
         } else if (childNodeName == Frame2Plugin.getResourceString("Frame2Model.event-handlers")) { //$NON-NLS-1$
            _EventHandlers = new EventHandlers();
            _EventHandlers.readNode(childNode);
         } else if (childNodeName == Frame2Plugin.getResourceString("Frame2Model.exceptions")) { //$NON-NLS-1$
            _Exceptions = new Exceptions();
            _Exceptions.readNode(childNode);
         } else if (childNodeName == Frame2Plugin.getResourceString("Frame2Model.plugins")) { //$NON-NLS-1$
            _Plugins = new Plugins();
            _Plugins.readNode(childNode);
         } else if (childNodeName == Frame2Plugin.getResourceString("Frame2Model.request-processors")) { //$NON-NLS-1$
            _RequestProcessors = new RequestProcessors();
            _RequestProcessors.readNode(childNode);
         } else {
            // Found extra unrecognized childNode
         }
      }
   }

   // Takes some text to be printed into an XML stream and escapes any
   // characters that might make it invalid XML (like '<').
   public static void writeXML(Writer out, String msg) throws IOException {
      writeXML(out, msg, true);
   }

   public static void writeXML(Writer out, String msg, boolean attribute)
         throws IOException {
      if (msg == null) return;
      int msgLength = msg.length();
      for (int i = 0; i < msgLength; ++i) {
         char c = msg.charAt(i);
         writeXML(out, c, attribute);
      }
   }

   public static void writeXML(Writer out, char msg, boolean attribute)
         throws IOException {
      if (msg == '&')
         out.write(Frame2Plugin.getResourceString("Frame2Model.ampersandEntity")); //$NON-NLS-1$
      else if (msg == '<')
         out.write(Frame2Plugin.getResourceString("Frame2Model.ltEntity")); //$NON-NLS-1$
      else if (msg == '>')
         out.write(Frame2Plugin.getResourceString("Frame2Model.gtEntity")); //$NON-NLS-1$
      else if (attribute && msg == '"')
         out.write(Frame2Plugin.getResourceString("Frame2Model.quoteEntity")); //$NON-NLS-1$
      else if (attribute && msg == '\'')
         out.write(Frame2Plugin.getResourceString("Frame2Model.apostropheEntity")); //$NON-NLS-1$
      else if (attribute && msg == '\n')
         out.write(Frame2Plugin.getResourceString("Frame2Model.newlineEntity")); //$NON-NLS-1$
      else if (attribute && msg == '\t')
         out.write(Frame2Plugin.getResourceString("Frame2Model.tabEntity")); //$NON-NLS-1$
      else
         out.write(msg);
   }

   public static class ValidateException extends Exception {

      private Object failedBean;

      private String failedPropertyName;

      public ValidateException(String msg, String failedPropertyName,
            Object failedBean) {
         super(msg);
         this.failedBean = failedBean;
         this.failedPropertyName = failedPropertyName;
      }

      public String getFailedPropertyName() {
         return failedPropertyName;
      }

      public Object getFailedBean() {
         return failedBean;
      }
   }

   public void validate() throws Frame2Config.ValidateException {
      boolean restrictionFailure = false;
      // Validating property globalForwards
      if (getGlobalForwards() != null) {
         getGlobalForwards().validate();
      }
      // Validating property Frame2Events
      if (getFrame2Events() != null) {
         getFrame2Events().validate();
      }
      // Validating property eventMappings
      if (getEventMappings() != null) {
         getEventMappings().validate();
      }
      // Validating property eventHandlers
      if (getEventHandlers() != null) {
         getEventHandlers().validate();
      }
      // Validating property exceptions
      if (getExceptions() != null) {
         getExceptions().validate();
      }
      // Validating property plugins
      if (getPlugins() != null) {
         getPlugins().validate();
      }
      // Validating property requestProcessors
      if (getRequestProcessors() != null) {
         getRequestProcessors().validate();
      }
   }

   public void changePropertyByName(String name, Object value) {
      if (name == null) return;
      name = name.intern();
      if (name == Frame2Plugin.getResourceString("Frame2Model.globalForwards")) //$NON-NLS-1$
         setGlobalForwards((GlobalForwards) value);
      else if (name == Frame2Plugin.getResourceString("Frame2Model.Frame2Events")) //$NON-NLS-1$
         setFrame2Events((Frame2Events) value);
      else if (name == Frame2Plugin.getResourceString("Frame2Model.eventMappings")) //$NON-NLS-1$
         setEventMappings((EventMappings) value);
      else if (name == Frame2Plugin.getResourceString("Frame2Model.eventHandlers")) //$NON-NLS-1$
         setEventHandlers((EventHandlers) value);
      else if (name == Frame2Plugin.getResourceString("Frame2Model.exceptions")) //$NON-NLS-1$
         setExceptions((Exceptions) value);
      else if (name == Frame2Plugin.getResourceString("Frame2Model.plugins")) //$NON-NLS-1$
         setPlugins((Plugins) value);
      else if (name == Frame2Plugin.getResourceString("Frame2Model.requestProcessors")) //$NON-NLS-1$
         setRequestProcessors((RequestProcessors) value);
      else
         throw new IllegalArgumentException(name
               + Frame2Plugin.getResourceString("Frame2Model.invalidFrame2ConfigProperty")); //$NON-NLS-1$
   }

   public Object fetchPropertyByName(String name) {
      if (name == Frame2Plugin.getResourceString("Frame2Model.globalForwards")) return getGlobalForwards(); //$NON-NLS-1$
      if (name == Frame2Plugin.getResourceString("Frame2Model.Frame2Events")) return getFrame2Events(); //$NON-NLS-1$
      if (name == Frame2Plugin.getResourceString("Frame2Model.eventMappings")) return getEventMappings(); //$NON-NLS-1$
      if (name == Frame2Plugin.getResourceString("Frame2Model.eventHandlers")) return getEventHandlers(); //$NON-NLS-1$
      if (name == Frame2Plugin.getResourceString("Frame2Model.exceptions")) return getExceptions(); //$NON-NLS-1$
      if (name == Frame2Plugin.getResourceString("Frame2Model.plugins")) return getPlugins(); //$NON-NLS-1$
      if (name == Frame2Plugin.getResourceString("Frame2Model.requestProcessors")) return getRequestProcessors(); //$NON-NLS-1$
      throw new IllegalArgumentException(name
            + Frame2Plugin.getResourceString("Frame2Model.invalidFrame2ConfigProperty")); //$NON-NLS-1$
   }

   // Return an array of all of the properties that are beans and are set.
   public Object[] childBeans(boolean recursive) {
      List children = new LinkedList();
      childBeans(recursive, children);
      Object[] result = new Object[children.size()];
      return (Object[]) children.toArray(result);
   }

   // Put all child beans into the beans list.
   public void childBeans(boolean recursive, List beans) {
      if (_GlobalForwards != null) {
         if (recursive) {
            _GlobalForwards.childBeans(true, beans);
         }
         beans.add(_GlobalForwards);
      }
      if (_Frame2Events != null) {
         if (recursive) {
            _Frame2Events.childBeans(true, beans);
         }
         beans.add(_Frame2Events);
      }
      if (_EventMappings != null) {
         if (recursive) {
            _EventMappings.childBeans(true, beans);
         }
         beans.add(_EventMappings);
      }
      if (_EventHandlers != null) {
         if (recursive) {
            _EventHandlers.childBeans(true, beans);
         }
         beans.add(_EventHandlers);
      }
      if (_Exceptions != null) {
         if (recursive) {
            _Exceptions.childBeans(true, beans);
         }
         beans.add(_Exceptions);
      }
      if (_Plugins != null) {
         if (recursive) {
            _Plugins.childBeans(true, beans);
         }
         beans.add(_Plugins);
      }
      if (_RequestProcessors != null) {
         if (recursive) {
            _RequestProcessors.childBeans(true, beans);
         }
         beans.add(_RequestProcessors);
      }
   }

   public boolean equals(Object o) {
      if (o == this) return true;
      if (!(o instanceof Frame2Config)) return false;
      Frame2Config inst = (Frame2Config) o;
      if (!(_GlobalForwards == null ? inst._GlobalForwards == null
            : _GlobalForwards.equals(inst._GlobalForwards))) return false;
      if (!(_Frame2Events == null ? inst._Frame2Events == null : _Frame2Events
            .equals(inst._Frame2Events))) return false;
      if (!(_EventMappings == null ? inst._EventMappings == null
            : _EventMappings.equals(inst._EventMappings))) return false;
      if (!(_EventHandlers == null ? inst._EventHandlers == null
            : _EventHandlers.equals(inst._EventHandlers))) return false;
      if (!(_Exceptions == null ? inst._Exceptions == null : _Exceptions
            .equals(inst._Exceptions))) return false;
      if (!(_Plugins == null ? inst._Plugins == null : _Plugins
            .equals(inst._Plugins))) return false;
      if (!(_RequestProcessors == null ? inst._RequestProcessors == null
            : _RequestProcessors.equals(inst._RequestProcessors)))
            return false;
      return true;
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result
            + (_GlobalForwards == null ? 0 : _GlobalForwards.hashCode());
      result = 37 * result
            + (_Frame2Events == null ? 0 : _Frame2Events.hashCode());
      result = 37 * result
            + (_EventMappings == null ? 0 : _EventMappings.hashCode());
      result = 37 * result
            + (_EventHandlers == null ? 0 : _EventHandlers.hashCode());
      result = 37 * result + (_Exceptions == null ? 0 : _Exceptions.hashCode());
      result = 37 * result + (_Plugins == null ? 0 : _Plugins.hashCode());
      result = 37 * result
            + (_RequestProcessors == null ? 0 : _RequestProcessors.hashCode());
      return result;
   }

}