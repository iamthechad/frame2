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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EventHandler extends XMLCommentPreserver {

   private String _Name;

   private String _Type;

   private List _InitParam = new ArrayList(); // List<InitParam>

   private List _Forward = new ArrayList(); // List<Forward>

   public EventHandler() {
      _Name = "";
      _Type = "";
      clearComments();
   }

   // Deep copy
   public EventHandler(EventHandler source) {
      _Name = source._Name;
      _Type = source._Type;
      for (Iterator it = source._InitParam.iterator(); it.hasNext();) {
         _InitParam.add(new InitParam((InitParam) it.next()));
      }
      for (Iterator it = source._Forward.iterator(); it.hasNext();) {
         _Forward.add(new Forward((Forward) it.next()));
      }
      setComments(source.getCommentMap());
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

   // This attribute is an array, possibly empty
   public void setInitParam(InitParam[] value) {
      if (value == null) value = new InitParam[0];
      _InitParam.clear();
      for (int i = 0; i < value.length; ++i) {
         _InitParam.add(value[i]);
      }
   }

   public void setInitParam(int index, InitParam value) {
      _InitParam.set(index, value);
   }

   public InitParam[] getInitParam() {
      InitParam[] arr = new InitParam[_InitParam.size()];
      return (InitParam[]) _InitParam.toArray(arr);
   }

   public List fetchInitParamList() {
      return _InitParam;
   }

   public InitParam getInitParam(int index) {
      return (InitParam) _InitParam.get(index);
   }

   // Return the number of initParam
   public int sizeInitParam() {
      return _InitParam.size();
   }

   public int addInitParam(InitParam value) {
      _InitParam.add(value);
      return _InitParam.size() - 1;
   }

   // Search from the end looking for @param value, and then remove it.
   public int removeInitParam(InitParam value) {
      int pos = _InitParam.indexOf(value);
      if (pos >= 0) {
         _InitParam.remove(pos);
      }
      return pos;
   }

   // This attribute is an array, possibly empty
   public void setForward(Forward[] value) {
      if (value == null) value = new Forward[0];
      _Forward.clear();
      for (int i = 0; i < value.length; ++i) {
         _Forward.add(value[i]);
      }
   }

   public void setForward(int index, Forward value) {
      _Forward.set(index, value);
   }

   public Forward[] getForward() {
      Forward[] arr = new Forward[_Forward.size()];
      return (Forward[]) _Forward.toArray(arr);
   }

   public List fetchForwardList() {
      return _Forward;
   }

   public Forward getForward(int index) {
      return (Forward) _Forward.get(index);
   }

   // Return the number of forward
   public int sizeForward() {
      return _Forward.size();
   }

   public int addForward(Forward value) {
      _Forward.add(value);
      return _Forward.size() - 1;
   }

   // Search from the end looking for @param value, and then remove it.
   public int removeForward(Forward value) {
      int pos = _Forward.indexOf(value);
      if (pos >= 0) {
         _Forward.remove(pos);
      }
      return pos;
   }

   public void writeNode(Writer out, String nodeName, String indent)
         throws IOException {
      out.write(indent);
      out.write("<");
      out.write(nodeName);
      // name is an attribute
      if (_Name != null) {
         out.write(" name"); // NOI18N
         out.write("='"); // NOI18N
         Frame2Config.writeXML(out, _Name, true);
         out.write("'"); // NOI18N
      }
      // type is an attribute
      if (_Type != null) {
         out.write(" type"); // NOI18N
         out.write("='"); // NOI18N
         Frame2Config.writeXML(out, _Type, true);
         out.write("'"); // NOI18N
      }
      out.write(">\n");
      String nextIndent = indent + "	";
      for (Iterator it = _InitParam.iterator(); it.hasNext();) {
         InitParam element = (InitParam) it.next();
         if (element != null) {
            element.writeNode(out, "init-param", nextIndent);
         }
      }
      for (Iterator it = _Forward.iterator(); it.hasNext();) {
         Forward element = (Forward) it.next();
         if (element != null) {
            element.writeNode(out, "forward", nextIndent);
         }
      }

      writeRemainingComments(out, indent);
      out.write(indent);
      out.write("</" + nodeName + ">\n");
   }

   public void readNode(Node node) {
      if (node.hasAttributes()) {
         NamedNodeMap attrs = node.getAttributes();
         Attr attr;
         attr = (Attr) attrs.getNamedItem("name");
         if (attr != null) {
            _Name = attr.getValue();
         }
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
         if (childNodeName == "init-param") {
            InitParam aInitParam = new InitParam();
            aInitParam.readNode(childNode);
            _InitParam.add(aInitParam);
         } else if (childNodeName == "forward") {
            Forward aForward = new Forward();
            aForward.readNode(childNode);
            _Forward.add(aForward);
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
      // Validating property name
      if (getName() == null) { throw new Frame2Config.ValidateException(
            "getName() == null", "name", this); // NOI18N
      }
      // Validating property type
      if (getType() == null) { throw new Frame2Config.ValidateException(
            "getType() == null", "type", this); // NOI18N
      }
      // Validating property initParam
      for (int _index = 0; _index < sizeInitParam(); ++_index) {
         InitParam element = getInitParam(_index);
         if (element != null) {
            element.validate();
         }
      }
      /*
       * int orCount = 0; if (sizeInitParam() > 0) { ++orCount; }
       */
      // Validating property forward
      for (int _index = 0; _index < sizeForward(); ++_index) {
         Forward element = getForward(_index);
         if (element != null) {
            element.validate();
         }
      }
   }

   public void changePropertyByName(String name, Object value) {
      if (name == null) return;
      name = name.intern();
      if (name == "name")
         setName((String) value);
      else if (name == "type")
         setType((String) value);
      else if (name == "initParam")
         addInitParam((InitParam) value);
      else if (name == "initParam[]")
         setInitParam((InitParam[]) value);
      else if (name == "forward")
         addForward((Forward) value);
      else if (name == "forward[]")
         setForward((Forward[]) value);
      else
         throw new IllegalArgumentException(name
               + " is not a valid property name for EventHandler");
   }

   public Object fetchPropertyByName(String name) {
      if (name == "name") return getName();
      if (name == "type") return getType();
      if (name == "initParam[]") return getInitParam();
      if (name == "forward[]") return getForward();
      throw new IllegalArgumentException(name
            + " is not a valid property name for EventHandler");
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
      for (Iterator it = _InitParam.iterator(); it.hasNext();) {
         InitParam element = (InitParam) it.next();
         if (element != null) {
            if (recursive) {
               element.childBeans(true, beans);
            }
            beans.add(element);
         }
      }
      for (Iterator it = _Forward.iterator(); it.hasNext();) {
         Forward element = (Forward) it.next();
         if (element != null) {
            if (recursive) {
               element.childBeans(true, beans);
            }
            beans.add(element);
         }
      }
   }

   public boolean equals(Object o) {
      if (o == this) return true;
      if (!(o instanceof EventHandler)) return false;
      EventHandler inst = (EventHandler) o;
      if (!(_Name == null ? inst._Name == null : _Name.equals(inst._Name)))
            return false;
      if (!(_Type == null ? inst._Type == null : _Type.equals(inst._Type)))
            return false;
      if (sizeInitParam() != inst.sizeInitParam()) return false;
      // Compare every element.
      for (Iterator it = _InitParam.iterator(), it2 = inst._InitParam
            .iterator(); it.hasNext() && it2.hasNext();) {
         InitParam element = (InitParam) it.next();
         InitParam element2 = (InitParam) it2.next();
         if (!(element == null ? element2 == null : element.equals(element2)))
               return false;
      }
      if (sizeForward() != inst.sizeForward()) return false;
      // Compare every element.
      for (Iterator it = _Forward.iterator(), it2 = inst._Forward.iterator(); it
            .hasNext()
            && it2.hasNext();) {
         Forward element = (Forward) it.next();
         Forward element2 = (Forward) it2.next();
         if (!(element == null ? element2 == null : element.equals(element2)))
               return false;
      }
      return true;
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + (_Name == null ? 0 : _Name.hashCode());
      result = 37 * result + (_Type == null ? 0 : _Type.hashCode());
      result = 37 * result + (_InitParam == null ? 0 : _InitParam.hashCode());
      result = 37 * result + (_Forward == null ? 0 : _Forward.hashCode());
      return result;
   }

}