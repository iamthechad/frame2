/*
 * ====================================================================
 * 
 * Frame2 Open Source License
 * 
 * Copyright (c) 2004-2005 Megatome Technologies. All rights reserved.
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
import org.megatome.frame2.Frame2Plugin;

public class Forward {

   private String _Name;

   private String _Type;

   private String _Path;

   public Forward() {
      _Name = ""; //$NON-NLS-1$
      _Type = ""; //$NON-NLS-1$
      _Path = ""; //$NON-NLS-1$
   }

   // Deep copy
   public Forward(Forward source) {
      _Name = source._Name;
      _Type = source._Type;
      _Path = source._Path;
   }

   // This attribute is mandatory
   public void setName(String value) {
      _Name = value;
   }

   public String getName() {
      return _Name;
   }

   // This attribute is mandatory
   public void setType(String value) {
      _Type = value;
   }

   public String getType() {
      return _Type;
   }

   // This attribute is mandatory
   public void setPath(String value) {
      _Path = value;
   }

   public String getPath() {
      return _Path;
   }

   public void writeNode(Writer out, String nodeName, String indent)
         throws IOException {
      out.write(indent);
      out.write(Frame2Plugin.getResourceString("Frame2Model.tagStart")); //$NON-NLS-1$
      out.write(nodeName);
      // name is an attribute
      if (_Name != null) {
         out.write(Frame2Plugin.getResourceString("Frame2Model.nameAttribute")); //$NON-NLS-1$
         out.write(Frame2Plugin.getResourceString("Frame2Model.attributeValueStart")); //$NON-NLS-1$
         Frame2Config.writeXML(out, _Name, true);
         out.write(Frame2Plugin.getResourceString("Frame2Model.attributeValueEnd")); //$NON-NLS-1$
      }
      // type is an attribute
      if (_Type != null) {
         out.write(Frame2Plugin.getResourceString("Frame2Model.typeAttribute")); //$NON-NLS-1$
         out.write(Frame2Plugin.getResourceString("Frame2Model.attributeValueStart")); //$NON-NLS-1$
         Frame2Config.writeXML(out, _Type, true);
         out.write(Frame2Plugin.getResourceString("Frame2Model.attributeValueEnd")); //$NON-NLS-1$
      }
      // path is an attribute
      if (_Path != null) {
         out.write(Frame2Plugin.getResourceString("Frame2Model.pathAttribute")); //$NON-NLS-1$
         out.write(Frame2Plugin.getResourceString("Frame2Model.attributeValueStart")); //$NON-NLS-1$
         Frame2Config.writeXML(out, _Path, true);
         out.write(Frame2Plugin.getResourceString("Frame2Model.attributeValueEnd")); //$NON-NLS-1$
      }
      out.write(Frame2Plugin.getResourceString("Frame2Model.endTagFinish")); //$NON-NLS-1$
      //String nextIndent = indent + " ";
      //out.write(indent);
      //out.write("</"+nodeName+">\n");
   }

   public void readNode(Node node) {
      if (node.hasAttributes()) {
         NamedNodeMap attrs = node.getAttributes();
         Attr attr;
         attr = (Attr) attrs.getNamedItem(Frame2Plugin.getResourceString("Frame2Model.name")); //$NON-NLS-1$
         if (attr != null) {
            _Name = attr.getValue();
         }
         attr = (Attr) attrs.getNamedItem(Frame2Plugin.getResourceString("Frame2Model.type")); //$NON-NLS-1$
         if (attr != null) {
            _Type = attr.getValue();
         }
         attr = (Attr) attrs.getNamedItem(Frame2Plugin.getResourceString("Frame2Model.path")); //$NON-NLS-1$
         if (attr != null) {
            _Path = attr.getValue();
         }
      }
      NodeList children = node.getChildNodes();
      for (int i = 0, size = children.getLength(); i < size; ++i) {
         Node childNode = children.item(i);
         String childNodeName = (childNode.getLocalName() == null ? childNode
               .getNodeName().intern() : childNode.getLocalName().intern());
         String childNodeValue = ""; //$NON-NLS-1$
         if (childNode.getFirstChild() != null) {
            childNodeValue = childNode.getFirstChild().getNodeValue();
         }
      }
   }

   public void validate() throws Frame2Config.ValidateException {
      boolean restrictionFailure = false;
      // Validating property name
      if (getName() == null) { throw new Frame2Config.ValidateException(
            Frame2Plugin.getResourceString("Frame2Model.forwardNameNull"), Frame2Plugin.getResourceString("Frame2Model.name"), this); //$NON-NLS-1$ //$NON-NLS-2$
      }
      // Validating property type
      if (getType() == null) { throw new Frame2Config.ValidateException(
            Frame2Plugin.getResourceString("Frame2Model.forwardTypeNull"), Frame2Plugin.getResourceString("Frame2Model.type"), this); //$NON-NLS-1$ //$NON-NLS-2$
      }
      // Validating property path
      if (getPath() == null) { throw new Frame2Config.ValidateException(
            Frame2Plugin.getResourceString("Frame2Model.forwardPathNull"), Frame2Plugin.getResourceString("Frame2Model.path"), this); //$NON-NLS-1$ //$NON-NLS-2$
      }
   }

   public void changePropertyByName(String name, Object value) {
      if (name == null) return;
      name = name.intern();
      if (name.equals(Frame2Plugin.getResourceString("Frame2Model.name"))) //$NON-NLS-1$
         setName((String) value);
      else if (name.equals(Frame2Plugin.getResourceString("Frame2Model.type"))) //$NON-NLS-1$
         setType((String) value);
      else if (name.equals(Frame2Plugin.getResourceString("Frame2Model.path"))) //$NON-NLS-1$
         setPath((String) value);
      else
         throw new IllegalArgumentException(name
               + Frame2Plugin.getResourceString("Frame2Model.invalidForwardProperty")); //$NON-NLS-1$
   }

   public Object fetchPropertyByName(String name) {
      if (name.equals(Frame2Plugin.getResourceString("Frame2Model.name"))) return getName(); //$NON-NLS-1$
      if (name.equals(Frame2Plugin.getResourceString("Frame2Model.type"))) return getType(); //$NON-NLS-1$
      if (name.equals(Frame2Plugin.getResourceString("Frame2Model.path"))) return getPath(); //$NON-NLS-1$
      throw new IllegalArgumentException(name
            + Frame2Plugin.getResourceString("Frame2Model.invalidForwardProperty")); //$NON-NLS-1$
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
      if (!(o instanceof Forward)) return false;
      Forward inst = (Forward) o;
      if (!(_Name == null ? inst._Name == null : _Name.equals(inst._Name)))
            return false;
      if (!(_Type == null ? inst._Type == null : _Type.equals(inst._Type)))
            return false;
      if (!(_Path == null ? inst._Path == null : _Path.equals(inst._Path)))
            return false;
      return true;
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + (_Name == null ? 0 : _Name.hashCode());
      result = 37 * result + (_Type == null ? 0 : _Type.hashCode());
      result = 37 * result + (_Path == null ? 0 : _Path.hashCode());
      return result;
   }

}