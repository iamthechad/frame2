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

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RequestProcessors extends XMLCommentPreserver {

   private HttpRequestProcessor _HttpRequestProcessor;

   private SoapRequestProcessor _SoapRequestProcessor;

   public RequestProcessors() {
      clearComments();
   }

   // Deep copy
   public RequestProcessors(RequestProcessors source) {
      _HttpRequestProcessor = new HttpRequestProcessor(
            source._HttpRequestProcessor);
      _SoapRequestProcessor = new SoapRequestProcessor(
            source._SoapRequestProcessor);
      setComments(source.getCommentMap());
   }

   // This attribute is optional
   public void setHttpRequestProcessor(HttpRequestProcessor value) {
      _HttpRequestProcessor = value;
   }

   public HttpRequestProcessor getHttpRequestProcessor() {
      return _HttpRequestProcessor;
   }

   // This attribute is optional
   public void setSoapRequestProcessor(SoapRequestProcessor value) {
      _SoapRequestProcessor = value;
   }

   public SoapRequestProcessor getSoapRequestProcessor() {
      return _SoapRequestProcessor;
   }

   public void writeNode(Writer out, String nodeName, String indent)
         throws IOException {
      out.write(indent);
      out.write("<");
      out.write(nodeName);
      out.write(">\n");
      String nextIndent = indent + "	";
      if (_HttpRequestProcessor != null) {
         _HttpRequestProcessor.writeNode(out, "http-request-processor",
               nextIndent);
      }
      if (_SoapRequestProcessor != null) {
         _SoapRequestProcessor.writeNode(out, "soap-request-processor",
               nextIndent);
      }

      writeRemainingComments(out, indent);
      out.write(indent);
      out.write("</" + nodeName + ">\n");
   }

   public void readNode(Node node) {
      NodeList children = node.getChildNodes();
      for (int i = 0, size = children.getLength(); i < size; ++i) {
         Node childNode = children.item(i);
         String childNodeName = (childNode.getLocalName() == null ? childNode
               .getNodeName().intern() : childNode.getLocalName().intern());
         String childNodeValue = "";
         if (childNode.getFirstChild() != null) {
            childNodeValue = childNode.getFirstChild().getNodeValue();
         }
         if (childNodeName == "http-request-processor") {
            _HttpRequestProcessor = new HttpRequestProcessor();
            _HttpRequestProcessor.readNode(childNode);
         } else if (childNodeName == "soap-request-processor") {
            _SoapRequestProcessor = new SoapRequestProcessor();
            _SoapRequestProcessor.readNode(childNode);
         } else {
            // Found extra unrecognized childNode
            if (childNodeName == "#comment") {
               recordComment(childNode, i);
            }
         }
      }
   }

   public void validate() throws Frame2Config.ValidateException {
      boolean restrictionFailure = false;
      // Validating property httpRequestProcessor
      if (getHttpRequestProcessor() != null) {
         getHttpRequestProcessor().validate();
      }
      // Validating property soapRequestProcessor
      if (getSoapRequestProcessor() != null) {
         getSoapRequestProcessor().validate();
      }
   }

   public void changePropertyByName(String name, Object value) {
      if (name == null) return;
      name = name.intern();
      if (name == "httpRequestProcessor")
         setHttpRequestProcessor((HttpRequestProcessor) value);
      else if (name == "soapRequestProcessor")
         setSoapRequestProcessor((SoapRequestProcessor) value);
      else
         throw new IllegalArgumentException(name
               + " is not a valid property name for RequestProcessors");
   }

   public Object fetchPropertyByName(String name) {
      if (name == "httpRequestProcessor") return getHttpRequestProcessor();
      if (name == "soapRequestProcessor") return getSoapRequestProcessor();
      throw new IllegalArgumentException(name
            + " is not a valid property name for RequestProcessors");
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
      if (_HttpRequestProcessor != null) {
         if (recursive) {
            _HttpRequestProcessor.childBeans(true, beans);
         }
         beans.add(_HttpRequestProcessor);
      }
      if (_SoapRequestProcessor != null) {
         if (recursive) {
            _SoapRequestProcessor.childBeans(true, beans);
         }
         beans.add(_SoapRequestProcessor);
      }
   }

   public boolean equals(Object o) {
      if (o == this) return true;
      if (!(o instanceof RequestProcessors)) return false;
      RequestProcessors inst = (RequestProcessors) o;
      if (!(_HttpRequestProcessor == null ? inst._HttpRequestProcessor == null
            : _HttpRequestProcessor.equals(inst._HttpRequestProcessor)))
            return false;
      if (!(_SoapRequestProcessor == null ? inst._SoapRequestProcessor == null
            : _SoapRequestProcessor.equals(inst._SoapRequestProcessor)))
            return false;
      return true;
   }

   public int hashCode() {
      int result = 17;
      result = 37
            * result
            + (_HttpRequestProcessor == null ? 0 : _HttpRequestProcessor
                  .hashCode());
      result = 37
            * result
            + (_SoapRequestProcessor == null ? 0 : _SoapRequestProcessor
                  .hashCode());
      return result;
   }

}