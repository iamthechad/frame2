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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.megatome.frame2.Frame2Plugin;

public class EventMapping extends XMLCommentPreserver {

   private String _EventName;

   private String _InputView;

   private String _CancelView;

   private String _Validate;

   private List _Handler = new ArrayList(); // List<Handler>

   private List _View = new ArrayList(); // List<View>

   private Security _Security;

   public EventMapping() {
      _EventName = ""; //$NON-NLS-1$
      clearComments();
   }

   // Deep copy
   public EventMapping(EventMapping source) {
      _EventName = source._EventName;
      _InputView = source._InputView;
      _CancelView = source._CancelView;
      _Validate = source._Validate;
      for (Iterator it = source._Handler.iterator(); it.hasNext();) {
         _Handler.add(new Handler((Handler) it.next()));
      }
      for (Iterator it = source._View.iterator(); it.hasNext();) {
         _View.add(new View((View) it.next()));
      }
      setComments(source.getCommentMap());
      _Security = new Security(source._Security);
   }

   // This attribute is mandatory
   public void setEventName(String value) {
      _EventName = value;
   }

   public String getEventName() {
      return _EventName;
   }

   // This attribute is optional
   public void setInputView(String value) {
      _InputView = value;
   }

   public String getInputView() {
      return _InputView;
   }

   // This attribute is optional
   public void setCancelView(String value) {
      _CancelView = value;
   }

   public String getCancelView() {
      return _CancelView;
   }

   // This attribute is optional
   public void setValidate(String value) {
      _Validate = value;
   }

   public String getValidate() {
      return _Validate;
   }

   // This attribute is an array, possibly empty
   public void setHandler(Handler[] value) {
      if (value == null) value = new Handler[0];
      _Handler.clear();
      for (int i = 0; i < value.length; ++i) {
         _Handler.add(value[i]);
      }
   }

   public void setHandler(int index, Handler value) {
      _Handler.set(index, value);
   }

   public Handler[] getHandler() {
      Handler[] arr = new Handler[_Handler.size()];
      return (Handler[]) _Handler.toArray(arr);
   }

   public List fetchHandlerList() {
      return _Handler;
   }

   public Handler getHandler(int index) {
      return (Handler) _Handler.get(index);
   }

   // Return the number of handler
   public int sizeHandler() {
      return _Handler.size();
   }

   public int addHandler(Handler value) {
      _Handler.add(value);
      return _Handler.size() - 1;
   }

   // Search from the end looking for @param value, and then remove it.
   public int removeHandler(Handler value) {
      int pos = _Handler.indexOf(value);
      if (pos >= 0) {
         _Handler.remove(pos);
      }
      return pos;
   }

   // This attribute is an array, possibly empty
   public void setView(View[] value) {
      if (value == null) value = new View[0];
      _View.clear();
      for (int i = 0; i < value.length; ++i) {
         _View.add(value[i]);
      }
   }

   public void setView(int index, View value) {
      _View.set(index, value);
   }

   public View[] getView() {
      View[] arr = new View[_View.size()];
      return (View[]) _View.toArray(arr);
   }

   public List fetchViewList() {
      return _View;
   }

   public View getView(int index) {
      return (View) _View.get(index);
   }

   // Return the number of view
   public int sizeView() {
      return _View.size();
   }

   public int addView(View value) {
      _View.add(value);
      return _View.size() - 1;
   }

   // Search from the end looking for @param value, and then remove it.
   public int removeView(View value) {
      int pos = _View.indexOf(value);
      if (pos >= 0) {
         _View.remove(pos);
      }
      return pos;
   }

   // This attribute is optional
   public void setSecurity(Security value) {
      _Security = value;
   }

   public Security getSecurity() {
      return _Security;
   }

   public void writeNode(Writer out, String nodeName, String indent)
         throws IOException {
      out.write(indent);
      out.write(Frame2Plugin.getResourceString("Frame2Model.tagStart")); //$NON-NLS-1$
      out.write(nodeName);
      // eventName is an attribute
      if (_EventName != null) {
         out.write(Frame2Plugin.getResourceString("Frame2Model.eventNameAttribute")); //$NON-NLS-1$
         out.write(Frame2Plugin.getResourceString("Frame2Model.attributeValueStart")); //$NON-NLS-1$
         Frame2Config.writeXML(out, _EventName, true);
         out.write(Frame2Plugin.getResourceString("Frame2Model.attributeValueEnd")); //$NON-NLS-1$
      }
      // inputView is an attribute
      if (_InputView != null) {
         out.write(Frame2Plugin.getResourceString("Frame2Model.inputViewAttribute")); //$NON-NLS-1$
         out.write(Frame2Plugin.getResourceString("Frame2Model.attributeValueStart")); //$NON-NLS-1$
         Frame2Config.writeXML(out, _InputView, true);
         out.write(Frame2Plugin.getResourceString("Frame2Model.attributeValueEnd")); //$NON-NLS-1$
      }
      // cancelView is an attribute
      if (_CancelView != null) {
         out.write(Frame2Plugin.getResourceString("Frame2Model.cancelViewAttribute")); //$NON-NLS-1$
         out.write(Frame2Plugin.getResourceString("Frame2Model.attributeValueStart")); //$NON-NLS-1$
         Frame2Config.writeXML(out, _CancelView, true);
         out.write(Frame2Plugin.getResourceString("Frame2Model.attributeValueEnd")); //$NON-NLS-1$
      }
      // validate is an attribute
      if (_Validate != null) {
         out.write(Frame2Plugin.getResourceString("Frame2Model.validateAttribute")); //$NON-NLS-1$
         out.write(Frame2Plugin.getResourceString("Frame2Model.attributeValueStart")); //$NON-NLS-1$
         Frame2Config.writeXML(out, _Validate, true);
         out.write(Frame2Plugin.getResourceString("Frame2Model.attributeValueEnd")); //$NON-NLS-1$
      }
      out.write(Frame2Plugin.getResourceString("Frame2Model.tagFinish")); //$NON-NLS-1$
      String nextIndent = indent + Frame2Plugin.getResourceString("Frame2Model.indentTabValue"); //$NON-NLS-1$
      for (Iterator it = _Handler.iterator(); it.hasNext();) {
         Handler element = (Handler) it.next();
         if (element != null) {
            element.writeNode(out, Frame2Plugin.getResourceString("Frame2Model.handler"), nextIndent); //$NON-NLS-1$
         }
      }
      for (Iterator it = _View.iterator(); it.hasNext();) {
         View element = (View) it.next();
         if (element != null) {
            element.writeNode(out, Frame2Plugin.getResourceString("Frame2Model.view"), nextIndent); //$NON-NLS-1$
         }
      }

      writeRemainingComments(out, indent);

      if (_Security != null) {
         _Security.writeNode(out, Frame2Plugin.getResourceString("Frame2Model.security"), nextIndent); //$NON-NLS-1$
      }
      out.write(indent);
      out.write(Frame2Plugin.getResourceString("Frame2Model.endTagStart") + nodeName + Frame2Plugin.getResourceString("Frame2Model.tagFinish")); //$NON-NLS-1$ //$NON-NLS-2$
   }

   public void readNode(Node node) {
      if (node.hasAttributes()) {
         NamedNodeMap attrs = node.getAttributes();
         Attr attr;
         attr = (Attr) attrs.getNamedItem(Frame2Plugin.getResourceString("Frame2Model.eventName")); //$NON-NLS-1$
         if (attr != null) {
            _EventName = attr.getValue();
         }
         attr = (Attr) attrs.getNamedItem(Frame2Plugin.getResourceString("Frame2Model.inputView")); //$NON-NLS-1$
         if (attr != null) {
            _InputView = attr.getValue();
         }
         attr = (Attr) attrs.getNamedItem(Frame2Plugin.getResourceString("Frame2Model.cancelView")); //$NON-NLS-1$
         if (attr != null) {
            _CancelView = attr.getValue();
         }
         attr = (Attr) attrs.getNamedItem(Frame2Plugin.getResourceString("Frame2Model.validate")); //$NON-NLS-1$
         if (attr != null) {
            _Validate = attr.getValue();
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
         if (childNodeName.equals(Frame2Plugin.getResourceString("Frame2Model.handler"))) { //$NON-NLS-1$
            Handler aHandler = new Handler();
            aHandler.readNode(childNode);
            _Handler.add(aHandler);
         } else if (childNodeName.equals(Frame2Plugin.getResourceString("Frame2Model.view"))) { //$NON-NLS-1$
            View aView = new View();
            aView.readNode(childNode);
            _View.add(aView);
         } else if (childNodeName.equals(Frame2Plugin.getResourceString("Frame2Model.security"))) { //$NON-NLS-1$
            _Security = new Security();
            _Security.readNode(childNode);
         } else {
            // Found extra unrecognized childNode
            if (childNodeName.equals(Frame2Plugin.getResourceString("Frame2Model.comment"))) { //$NON-NLS-1$
               recordComment(childNode, i);
            }
         }
      }
   }

   public void validate() throws Frame2Config.ValidateException {
      boolean restrictionFailure = false;
      // Validating property eventName
      if (getEventName() == null) { throw new Frame2Config.ValidateException(
            Frame2Plugin.getResourceString("Frame2Model.eventNameNull"), Frame2Plugin.getResourceString("Frame2Model.eventName"), this); //$NON-NLS-1$ //$NON-NLS-2$
      }
      // Validating property inputView
      if (getInputView() != null) {
      }
      // Validating property cancelView
      if (getCancelView() != null) {
      }
      // Validating property validate
      if (getValidate() != null) {
      }
      // Validating property handler
      for (int _index = 0; _index < sizeHandler(); ++_index) {
         Handler element = getHandler(_index);
         if (element != null) {
            element.validate();
         }
      }
      // Validating property view
      for (int _index = 0; _index < sizeView(); ++_index) {
         View element = getView(_index);
         if (element != null) {
            element.validate();
         }
      }
      // Validating property security
      if (getSecurity() != null) {
         getSecurity().validate();
      }
   }

   public void changePropertyByName(String name, Object value) {
      if (name == null) return;
      name = name.intern();
      if (name.equals(Frame2Plugin.getResourceString("Frame2Model.eventName"))) //$NON-NLS-1$
         setEventName((String) value);
      else if (name.equals(Frame2Plugin.getResourceString("Frame2Model.inputView"))) //$NON-NLS-1$
         setInputView((String) value);
      else if (name.equals(Frame2Plugin.getResourceString("Frame2Model.cancelView"))) //$NON-NLS-1$
         setCancelView((String) value);
      else if (name.equals(Frame2Plugin.getResourceString("Frame2Model.validate"))) //$NON-NLS-1$
         setValidate((String) value);
      else if (name.equals(Frame2Plugin.getResourceString("Frame2Model.handler"))) //$NON-NLS-1$
         addHandler((Handler) value);
      else if (name.equals(Frame2Plugin.getResourceString("Frame2Model.handlerArray"))) //$NON-NLS-1$
         setHandler((Handler[]) value);
      else if (name.equals(Frame2Plugin.getResourceString("Frame2Model.view"))) //$NON-NLS-1$
         addView((View) value);
      else if (name.equals(Frame2Plugin.getResourceString("Frame2Model.viewArray"))) //$NON-NLS-1$
         setView((View[]) value);
      else if (name.equals(Frame2Plugin.getResourceString("Frame2Model.security"))) //$NON-NLS-1$
         setSecurity((Security) value);
      else
         throw new IllegalArgumentException(name
               + Frame2Plugin.getResourceString("Frame2Model.invalidEventMappingProperty")); //$NON-NLS-1$
   }

   public Object fetchPropertyByName(String name) {
      if (name.equals(Frame2Plugin.getResourceString("Frame2Model.eventName"))) return getEventName(); //$NON-NLS-1$
      if (name.equals(Frame2Plugin.getResourceString("Frame2Model.inputView"))) return getInputView(); //$NON-NLS-1$
      if (name.equals(Frame2Plugin.getResourceString("Frame2Model.cancelView"))) return getCancelView(); //$NON-NLS-1$
      if (name.equals(Frame2Plugin.getResourceString("Frame2Model.validate"))) return getValidate(); //$NON-NLS-1$
      if (name.equals(Frame2Plugin.getResourceString("Frame2Model.handlerArray"))) return getHandler(); //$NON-NLS-1$
      if (name.equals(Frame2Plugin.getResourceString("Frame2Model.viewArray"))) return getView(); //$NON-NLS-1$
      if (name.equals(Frame2Plugin.getResourceString("Frame2Model.security"))) return getSecurity(); //$NON-NLS-1$
      throw new IllegalArgumentException(name
            + Frame2Plugin.getResourceString("Frame2Model.invalidEventMappingProperty")); //$NON-NLS-1$
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
      for (Iterator it = _Handler.iterator(); it.hasNext();) {
         Handler element = (Handler) it.next();
         if (element != null) {
            if (recursive) {
               element.childBeans(true, beans);
            }
            beans.add(element);
         }
      }
      for (Iterator it = _View.iterator(); it.hasNext();) {
         View element = (View) it.next();
         if (element != null) {
            if (recursive) {
               element.childBeans(true, beans);
            }
            beans.add(element);
         }
      }
      if (_Security != null) {
         if (recursive) {
            _Security.childBeans(true, beans);
         }
         beans.add(_Security);
      }
   }

   public boolean equals(Object o) {
      if (o == this) return true;
      if (!(o instanceof EventMapping)) return false;
      EventMapping inst = (EventMapping) o;
      if (!(_EventName == null ? inst._EventName == null : _EventName
            .equals(inst._EventName))) return false;
      if (!(_InputView == null ? inst._InputView == null : _InputView
            .equals(inst._InputView))) return false;
      if (!(_CancelView == null ? inst._CancelView == null : _CancelView
            .equals(inst._CancelView))) return false;
      if (!(_Validate == null ? inst._Validate == null : _Validate
            .equals(inst._Validate))) return false;
      if (sizeHandler() != inst.sizeHandler()) return false;
      // Compare every element.
      for (Iterator it = _Handler.iterator(), it2 = inst._Handler.iterator(); it
            .hasNext()
            && it2.hasNext();) {
         Handler element = (Handler) it.next();
         Handler element2 = (Handler) it2.next();
         if (!(element == null ? element2 == null : element.equals(element2)))
               return false;
      }
      if (sizeView() != inst.sizeView()) return false;
      // Compare every element.
      for (Iterator it = _View.iterator(), it2 = inst._View.iterator(); it
            .hasNext()
            && it2.hasNext();) {
         View element = (View) it.next();
         View element2 = (View) it2.next();
         if (!(element == null ? element2 == null : element.equals(element2)))
               return false;
      }
      if (!(_Security == null ? inst._Security == null : _Security
            .equals(inst._Security))) return false;
      return true;
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + (_EventName == null ? 0 : _EventName.hashCode());
      result = 37 * result + (_InputView == null ? 0 : _InputView.hashCode());
      result = 37 * result + (_CancelView == null ? 0 : _CancelView.hashCode());
      result = 37 * result + (_Validate == null ? 0 : _Validate.hashCode());
      result = 37 * result + (_Handler == null ? 0 : _Handler.hashCode());
      result = 37 * result + (_View == null ? 0 : _View.hashCode());
      result = 37 * result + (_Security == null ? 0 : _Security.hashCode());
      return result;
   }

}