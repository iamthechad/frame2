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

public class EventHandlers extends XMLCommentPreserver {

   private List _EventHandler = new ArrayList(); // List<EventHandler>

   public EventHandlers() {
      clearComments();
   }

   // Deep copy
   public EventHandlers(EventHandlers source) {
      for (Iterator it = source._EventHandler.iterator(); it.hasNext();) {
         _EventHandler.add(new EventHandler((EventHandler) it.next()));
      }

      setComments(source.getCommentMap());
   }

   // This attribute is an array, possibly empty
   public void setEventHandler(EventHandler[] value) {
      if (value == null) value = new EventHandler[0];
      _EventHandler.clear();
      for (int i = 0; i < value.length; ++i) {
         _EventHandler.add(value[i]);
      }
   }

   public void setEventHandler(int index, EventHandler value) {
      _EventHandler.set(index, value);
   }

   public EventHandler[] getEventHandler() {
      EventHandler[] arr = new EventHandler[_EventHandler.size()];
      return (EventHandler[]) _EventHandler.toArray(arr);
   }

   public List fetchEventHandlerList() {
      return _EventHandler;
   }

   public EventHandler getEventHandler(int index) {
      return (EventHandler) _EventHandler.get(index);
   }

   // Return the number of eventHandler
   public int sizeEventHandler() {
      return _EventHandler.size();
   }

   public int addEventHandler(EventHandler value) {
      _EventHandler.add(value);
      return _EventHandler.size() - 1;
   }

   // Search from the end looking for @param value, and then remove it.
   public int removeEventHandler(EventHandler value) {
      int pos = _EventHandler.indexOf(value);
      if (pos >= 0) {
         _EventHandler.remove(pos);
      }
      return pos;
   }

   public void writeNode(Writer out, String nodeName, String indent)
         throws IOException {
      out.write(indent);
      out.write("<");
      out.write(nodeName);
      out.write(">\n");
      String nextIndent = indent + "	";
      int index = 0;
      for (Iterator it = _EventHandler.iterator(); it.hasNext();) {

         index = writeCommentsAt(out, indent, index);

         EventHandler element = (EventHandler) it.next();
         if (element != null) {
            element.writeNode(out, "event-handler", nextIndent);
         }
      }

      writeRemainingComments(out, indent);
      out.write(indent);
      out.write("</" + nodeName + ">\n");
   }

   public void readNode(Node node) {
      NodeList children = node.getChildNodes();
      int elementCount = 0;
      for (int i = 0, size = children.getLength(); i < size; ++i) {
         Node childNode = children.item(i);
         String childNodeName = (childNode.getLocalName() == null ? childNode
               .getNodeName().intern() : childNode.getLocalName().intern());
         String childNodeValue = "";
         if (childNode.getFirstChild() != null) {
            childNodeValue = childNode.getFirstChild().getNodeValue();
         }
         if (childNodeName == "event-handler") {
            EventHandler aEventHandler = new EventHandler();
            aEventHandler.readNode(childNode);
            _EventHandler.add(aEventHandler);
            elementCount++;
         } else {
            // Found extra unrecognized childNode
            if (childNodeName == "#comment") {
               recordComment(childNode, elementCount++);
            }
         }
      }
   }

   public void validate() throws Frame2Config.ValidateException {
      boolean restrictionFailure = false;
      // Validating property eventHandler
      for (int _index = 0; _index < sizeEventHandler(); ++_index) {
         EventHandler element = getEventHandler(_index);
         if (element != null) {
            element.validate();
         }
      }
   }

   public void changePropertyByName(String name, Object value) {
      if (name == null) return;
      name = name.intern();
      if (name == "eventHandler")
         addEventHandler((EventHandler) value);
      else if (name == "eventHandler[]")
         setEventHandler((EventHandler[]) value);
      else
         throw new IllegalArgumentException(name
               + " is not a valid property name for EventHandlers");
   }

   public Object fetchPropertyByName(String name) {
      if (name == "eventHandler[]") return getEventHandler();
      throw new IllegalArgumentException(name
            + " is not a valid property name for EventHandlers");
   }

   // Return an array of all of the properties that are beans and are set.
   public java.lang.Object[] childBeans(boolean recursive) {
      List children = new LinkedList();
      childBeans(recursive, children);
      Object[] result = new Object[children.size()];
      return (Object[]) children.toArray(result);
   }

   // Put all child beans into the beans list.
   public void childBeans(boolean recursive, java.util.List beans) {
      for (java.util.Iterator it = _EventHandler.iterator(); it.hasNext();) {
         EventHandler element = (EventHandler) it.next();
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
      if (!(o instanceof EventHandlers)) return false;
      EventHandlers inst = (EventHandlers) o;
      if (sizeEventHandler() != inst.sizeEventHandler()) return false;
      // Compare every element.
      for (Iterator it = _EventHandler.iterator(), it2 = inst._EventHandler
            .iterator(); it.hasNext() && it2.hasNext();) {
         EventHandler element = (EventHandler) it.next();
         EventHandler element2 = (EventHandler) it2.next();
         if (!(element == null ? element2 == null : element.equals(element2)))
               return false;
      }
      return true;
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result
            + (_EventHandler == null ? 0 : _EventHandler.hashCode());
      return result;
   }

}