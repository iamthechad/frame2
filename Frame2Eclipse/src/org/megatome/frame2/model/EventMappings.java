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

public class EventMappings extends XMLCommentPreserver {

   private List _EventMapping = new ArrayList(); // List<EventMapping>

   public EventMappings() {
      clearComments();
   }

   // Deep copy
   public EventMappings(EventMappings source) {
      for (Iterator it = source._EventMapping.iterator(); it.hasNext();) {
         _EventMapping.add(new EventMapping((EventMapping) it.next()));
      }

      setComments(source.getCommentMap());
   }

   // This attribute is an array, possibly empty
   public void setEventMapping(EventMapping[] value) {
      if (value == null) value = new EventMapping[0];
      _EventMapping.clear();
      for (int i = 0; i < value.length; ++i) {
         _EventMapping.add(value[i]);
      }
   }

   public void setEventMapping(int index, EventMapping value) {
      _EventMapping.set(index, value);
   }

   public EventMapping[] getEventMapping() {
      EventMapping[] arr = new EventMapping[_EventMapping.size()];
      return (EventMapping[]) _EventMapping.toArray(arr);
   }

   public List fetchEventMappingList() {
      return _EventMapping;
   }

   public EventMapping getEventMapping(int index) {
      return (EventMapping) _EventMapping.get(index);
   }

   // Return the number of eventMapping
   public int sizeEventMapping() {
      return _EventMapping.size();
   }

   public int addEventMapping(EventMapping value) {
      _EventMapping.add(value);
      return _EventMapping.size() - 1;
   }

   // Search from the end looking for @param value, and then remove it.
   public int removeEventMapping(EventMapping value) {
      int pos = _EventMapping.indexOf(value);
      if (pos >= 0) {
         _EventMapping.remove(pos);
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
      for (Iterator it = _EventMapping.iterator(); it.hasNext();) {

         index = writeCommentsAt(out, indent, index);

         EventMapping element = (EventMapping) it.next();
         if (element != null) {
            element.writeNode(out, Frame2Plugin.getResourceString("Frame2Model.event-mapping"), nextIndent); //$NON-NLS-1$
         }
      }

      writeRemainingComments(out, indent);
      out.write(indent);
      out.write(Frame2Plugin.getResourceString("Frame2Model.engTagStart") + nodeName + Frame2Plugin.getResourceString("Frame2Model.tagFinish")); //$NON-NLS-1$ //$NON-NLS-2$
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
         if (childNodeName == Frame2Plugin.getResourceString("Frame2Model.event-mapping")) { //$NON-NLS-1$
            EventMapping aEventMapping = new EventMapping();
            aEventMapping.readNode(childNode);
            _EventMapping.add(aEventMapping);
            elementCount++;
         } else {
            // Found extra unrecognized childNode
            if (childNodeName == Frame2Plugin.getResourceString("Frame2Model.comment")) { //$NON-NLS-1$
               recordComment(childNode, elementCount++);
            }
         }
      }
   }

   public void validate()
         throws org.megatome.frame2.model.Frame2Config.ValidateException {
      boolean restrictionFailure = false;
      // Validating property eventMapping
      for (int _index = 0; _index < sizeEventMapping(); ++_index) {
         EventMapping element = getEventMapping(_index);
         if (element != null) {
            element.validate();
         }
      }
   }

   public void changePropertyByName(String name, Object value) {
      if (name == null) return;
      name = name.intern();
      if (name == Frame2Plugin.getResourceString("Frame2Model.eventMapping")) //$NON-NLS-1$
         addEventMapping((EventMapping) value);
      else if (name == Frame2Plugin.getResourceString("Frame2Model.eventMappingArray")) //$NON-NLS-1$
         setEventMapping((EventMapping[]) value);
      else
         throw new IllegalArgumentException(name
               + Frame2Plugin.getResourceString("Frame2Model.invalidEventMappingsProperty")); //$NON-NLS-1$
   }

   public Object fetchPropertyByName(String name) {
      if (name == Frame2Plugin.getResourceString("Frame2Model.eventMappingArray")) return getEventMapping(); //$NON-NLS-1$
      throw new IllegalArgumentException(name
            + Frame2Plugin.getResourceString("Frame2Model.invalidEventMappingsProperty")); //$NON-NLS-1$
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
      for (Iterator it = _EventMapping.iterator(); it.hasNext();) {
         EventMapping element = (EventMapping) it.next();
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
      if (!(o instanceof EventMappings)) return false;
      EventMappings inst = (EventMappings) o;
      if (sizeEventMapping() != inst.sizeEventMapping()) return false;
      // Compare every element.
      for (Iterator it = _EventMapping.iterator(), it2 = inst._EventMapping
            .iterator(); it.hasNext() && it2.hasNext();) {
         EventMapping element = (EventMapping) it.next();
         EventMapping element2 = (EventMapping) it2.next();
         if (!(element == null ? element2 == null : element.equals(element2)))
               return false;
      }
      return true;
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result
            + (_EventMapping == null ? 0 : _EventMapping.hashCode());
      return result;
   }

}