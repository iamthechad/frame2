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

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.megatome.frame2.Frame2Plugin;

public class Security extends XMLCommentPreserver {

   private List _Role = new ArrayList(); // List<Role>

   public Security() {
      clearComments();
   }

   // Deep copy
   public Security(Security source) {
      for (Iterator it = source._Role.iterator(); it.hasNext();) {
         _Role.add(new Role((Role) it.next()));
      }

      setComments(source.getCommentMap());
   }

   // This attribute is an array containing at least one element
   public void setRole(Role[] value) {
      if (value == null) value = new Role[0];
      _Role.clear();
      for (int i = 0; i < value.length; ++i) {
         _Role.add(value[i]);
      }
   }

   public void setRole(int index, Role value) {
      _Role.set(index, value);
   }

   public Role[] getRole() {
      Role[] arr = new Role[_Role.size()];
      return (Role[]) _Role.toArray(arr);
   }

   public List fetchRoleList() {
      return _Role;
   }

   public Role getRole(int index) {
      return (Role) _Role.get(index);
   }

   // Return the number of role
   public int sizeRole() {
      return _Role.size();
   }

   public int addRole(Role value) {
      _Role.add(value);
      return _Role.size() - 1;
   }

   // Search from the end looking for @param value, and then remove it.
   public int removeRole(Role value) {
      int pos = _Role.indexOf(value);
      if (pos >= 0) {
         _Role.remove(pos);
      }
      return pos;
   }

   public void writeNode(Writer out, String nodeName, String indent)
         throws IOException {
      out.write(indent);
      out.write(Frame2Plugin.getResourceString("Frame2Model.tagStart")); //$NON-NLS-1$
      out.write(nodeName);
      out.write(Frame2Plugin.getResourceString("Frame2Model.tagFinish")); //$NON-NLS-1$
      String nextIndent = indent + Frame2Plugin.getResourceString("Frame2Model.indentTabValue"); //$NON-NLS-1$
      int index = 0;
      for (Iterator it = _Role.iterator(); it.hasNext();) {

         index = writeCommentsAt(out, indent, index);

         Role element = (Role) it.next();
         if (element != null) {
            element.writeNode(out, Frame2Plugin.getResourceString("Frame2Model.role"), nextIndent); //$NON-NLS-1$
         }
      }
      writeRemainingComments(out, indent);
      out.write(indent);
      out.write(Frame2Plugin.getResourceString("Frame2Model.endTagStart") + nodeName + Frame2Plugin.getResourceString("Frame2Model.tagFinish")); //$NON-NLS-1$ //$NON-NLS-2$
   }

   public void readNode(Node node) {
      NodeList children = node.getChildNodes();
      int elementCount = 0;
      for (int i = 0, size = children.getLength(); i < size; ++i) {
         Node childNode = children.item(i);
         String childNodeName = (childNode.getLocalName() == null ? childNode
               .getNodeName().intern() : childNode.getLocalName().intern());
         String childNodeValue = ""; //$NON-NLS-1$
         if (childNode.getFirstChild() != null) {
            childNodeValue = childNode.getFirstChild().getNodeValue();
         }
         if (childNodeName == Frame2Plugin.getResourceString("Frame2Model.role")) { //$NON-NLS-1$
            Role aRole = new Role();
            aRole.readNode(childNode);
            _Role.add(aRole);
            elementCount++;
         } else {
            // Found extra unrecognized childNode
            if (childNodeName == Frame2Plugin.getResourceString("Frame2Model.comment")) { //$NON-NLS-1$
               recordComment(childNode, elementCount++);
            }
         }
      }
   }

   public void validate() throws Frame2Config.ValidateException {
      boolean restrictionFailure = false;
      // Validating property role
      if (sizeRole() == 0) { throw new Frame2Config.ValidateException(
            Frame2Plugin.getResourceString("Frame2Model.sizeRoleZero"), Frame2Plugin.getResourceString("Frame2Model.role"), this); //$NON-NLS-1$ //$NON-NLS-2$
      }
      for (int _index = 0; _index < sizeRole(); ++_index) {
         Role element = getRole(_index);
         if (element != null) {
            element.validate();
         }
      }
   }

   public void changePropertyByName(String name, Object value) {
      if (name == null) return;
      name = name.intern();
      if (name == Frame2Plugin.getResourceString("Frame2Model.role")) //$NON-NLS-1$
         addRole((Role) value);
      else if (name == Frame2Plugin.getResourceString("Frame2Model.roleArray")) //$NON-NLS-1$
         setRole((Role[]) value);
      else
         throw new IllegalArgumentException(name
               + Frame2Plugin.getResourceString("Frame2Model.invalidSecurityProperty")); //$NON-NLS-1$
   }

   public Object fetchPropertyByName(String name) {
      if (name == Frame2Plugin.getResourceString("Frame2Model.roleArray")) return getRole(); //$NON-NLS-1$
      throw new IllegalArgumentException(name
            + Frame2Plugin.getResourceString("Frame2Model.invalidSecurityProperty")); //$NON-NLS-1$
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
      for (Iterator it = _Role.iterator(); it.hasNext();) {
         Role element = (Role) it.next();
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
      if (!(o instanceof Security)) return false;
      Security inst = (Security) o;
      if (sizeRole() != inst.sizeRole()) return false;
      // Compare every element.
      for (Iterator it = _Role.iterator(), it2 = inst._Role.iterator(); it
            .hasNext()
            && it2.hasNext();) {
         Role element = (Role) it.next();
         Role element2 = (Role) it2.next();
         if (!(element == null ? element2 == null : element.equals(element2)))
               return false;
      }
      return true;
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + (_Role == null ? 0 : _Role.hashCode());
      return result;
   }

}