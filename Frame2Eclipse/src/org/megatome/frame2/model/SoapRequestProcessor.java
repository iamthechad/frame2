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

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SoapRequestProcessor {

   private String _Type;

   public SoapRequestProcessor() {
      _Type = "";
   }

   // Deep copy
   public SoapRequestProcessor(SoapRequestProcessor source) {
      _Type = source._Type;
   }

   // This attribute is mandatory
   public void setType(String value) {
      _Type = value;
   }

   public String getType() {
      return _Type;
   }

   public void writeNode(Writer out, String nodeName, String indent)
         throws IOException {
      out.write(indent);
      out.write("<");
      out.write(nodeName);
      // type is an attribute
      if (_Type != null) {
         out.write(" type"); // NOI18N
         out.write("='"); // NOI18N
         Frame2Config.writeXML(out, _Type, true);
         out.write("'"); // NOI18N
      }
      out.write("/>\n");
      //String nextIndent = indent + " ";
      //out.write(indent);
      //out.write("</"+nodeName+">\n");
   }

   public void readNode(Node node) {
      if (node.hasAttributes()) {
         NamedNodeMap attrs = node.getAttributes();
         Attr attr;
         attr = (Attr) attrs.getNamedItem("type");
         if (attr != null) {
            _Type = attr.getValue();
         }
      }
      NodeList children = node.getChildNodes();
      for (int i = 0, size = children.getLength(); i < size; ++i) {
         Node childNode = children.item(i);
         String childNodeName = (childNode.getLocalName() == null ? childNode
               .getNodeName().intern() : childNode.getLocalName().intern());
         String childNodeValue = "";
         if (childNode.getFirstChild() != null) {
            childNodeValue = childNode.getFirstChild().getNodeValue();
         }
      }
   }

   public void validate() throws Frame2Config.ValidateException {
      boolean restrictionFailure = false;
      // Validating property type
      if (getType() == null) { throw new Frame2Config.ValidateException(
            "getType() == null", "type", this); // NOI18N
      }
   }

   public void changePropertyByName(String name, Object value) {
      if (name == null) return;
      name = name.intern();
      if (name == "type")
         setType((String) value);
      else
         throw new IllegalArgumentException(name
               + " is not a valid property name for SoapRequestProcessor");
   }

   public Object fetchPropertyByName(String name) {
      if (name == "type") return getType();
      throw new IllegalArgumentException(name
            + " is not a valid property name for SoapRequestProcessor");
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
   }

   public boolean equals(Object o) {
      if (o == this) return true;
      if (!(o instanceof SoapRequestProcessor)) return false;
      SoapRequestProcessor inst = (SoapRequestProcessor) o;
      if (!(_Type == null ? inst._Type == null : _Type.equals(inst._Type)))
            return false;
      return true;
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + (_Type == null ? 0 : _Type.hashCode());
      return result;
   }

}