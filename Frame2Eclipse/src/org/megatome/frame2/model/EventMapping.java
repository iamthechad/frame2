/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004 Megatome Technologies.  All rights
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
package org.megatome.frame2.model;

public class EventMapping extends XMLCommentPreserver {
	private java.lang.String _EventName;
	private java.lang.String _InputView;
	private java.lang.String _CancelView;
	private java.lang.String _Validate;
	private java.util.List _Handler = new java.util.ArrayList();	// List<Handler>
	private java.util.List _View = new java.util.ArrayList();	// List<View>
	private Security _Security;

	public EventMapping() {
		_EventName = "";
        clearComments();
	}

	// Deep copy
	public EventMapping(org.megatome.frame2.model.EventMapping source) {
		_EventName = source._EventName;
		_InputView = source._InputView;
		_CancelView = source._CancelView;
		_Validate = source._Validate;
		for (java.util.Iterator it = source._Handler.iterator(); 
			it.hasNext(); ) {
			_Handler.add(new org.megatome.frame2.model.Handler((org.megatome.frame2.model.Handler)it.next()));
		}
		for (java.util.Iterator it = source._View.iterator(); 
			it.hasNext(); ) {
			_View.add(new org.megatome.frame2.model.View((org.megatome.frame2.model.View)it.next()));
		}
        setComments(source.getCommentMap());
		_Security = new org.megatome.frame2.model.Security(source._Security);
	}

	// This attribute is mandatory
	public void setEventName(java.lang.String value) {
		_EventName = value;
	}

	public java.lang.String getEventName() {
		return _EventName;
	}

	// This attribute is optional
	public void setInputView(java.lang.String value) {
		_InputView = value;
	}

	public java.lang.String getInputView() {
		return _InputView;
	}

	// This attribute is optional
	public void setCancelView(java.lang.String value) {
		_CancelView = value;
	}

	public java.lang.String getCancelView() {
		return _CancelView;
	}

	// This attribute is optional
	public void setValidate(java.lang.String value) {
		_Validate = value;
	}

	public java.lang.String getValidate() {
		return _Validate;
	}

	// This attribute is an array, possibly empty
	public void setHandler(org.megatome.frame2.model.Handler[] value) {
		if (value == null)
			value = new Handler[0];
		_Handler.clear();
		for (int i = 0; i < value.length; ++i) {
			_Handler.add(value[i]);
		}
	}

	public void setHandler(int index, org.megatome.frame2.model.Handler value) {
		_Handler.set(index, value);
	}

	public org.megatome.frame2.model.Handler[] getHandler() {
		Handler[] arr = new Handler[_Handler.size()];
		return (Handler[]) _Handler.toArray(arr);
	}

	public java.util.List fetchHandlerList() {
		return _Handler;
	}

	public org.megatome.frame2.model.Handler getHandler(int index) {
		return (Handler)_Handler.get(index);
	}

	// Return the number of handler
	public int sizeHandler() {
		return _Handler.size();
	}

	public int addHandler(org.megatome.frame2.model.Handler value) {
		_Handler.add(value);
		return _Handler.size()-1;
	}

	// Search from the end looking for @param value, and then remove it.
	public int removeHandler(org.megatome.frame2.model.Handler value) {
		int pos = _Handler.indexOf(value);
		if (pos >= 0) {
			_Handler.remove(pos);
		}
		return pos;
	}

	// This attribute is an array, possibly empty
	public void setView(org.megatome.frame2.model.View[] value) {
		if (value == null)
			value = new View[0];
		_View.clear();
		for (int i = 0; i < value.length; ++i) {
			_View.add(value[i]);
		}
	}

	public void setView(int index, org.megatome.frame2.model.View value) {
		_View.set(index, value);
	}

	public org.megatome.frame2.model.View[] getView() {
		View[] arr = new View[_View.size()];
		return (View[]) _View.toArray(arr);
	}

	public java.util.List fetchViewList() {
		return _View;
	}

	public org.megatome.frame2.model.View getView(int index) {
		return (View)_View.get(index);
	}

	// Return the number of view
	public int sizeView() {
		return _View.size();
	}

	public int addView(org.megatome.frame2.model.View value) {
		_View.add(value);
		return _View.size()-1;
	}

	// Search from the end looking for @param value, and then remove it.
	public int removeView(org.megatome.frame2.model.View value) {
		int pos = _View.indexOf(value);
		if (pos >= 0) {
			_View.remove(pos);
		}
		return pos;
	}

	// This attribute is optional
	public void setSecurity(org.megatome.frame2.model.Security value) {
		_Security = value;
	}

	public org.megatome.frame2.model.Security getSecurity() {
		return _Security;
	}

	public void writeNode(java.io.Writer out, String nodeName, String indent) throws java.io.IOException {
		out.write(indent);
		out.write("<");
		out.write(nodeName);
		// eventName is an attribute
		if (_EventName != null) {
			out.write(" eventName");	// NOI18N
			out.write("='");	// NOI18N
			org.megatome.frame2.model.Frame2Config.writeXML(out, _EventName, true);
			out.write("'");	// NOI18N
		}
		// inputView is an attribute
		if (_InputView != null) {
			out.write(" inputView");	// NOI18N
			out.write("='");	// NOI18N
			org.megatome.frame2.model.Frame2Config.writeXML(out, _InputView, true);
			out.write("'");	// NOI18N
		}
		// cancelView is an attribute
		if (_CancelView != null) {
			out.write(" cancelView");	// NOI18N
			out.write("='");	// NOI18N
			org.megatome.frame2.model.Frame2Config.writeXML(out, _CancelView, true);
			out.write("'");	// NOI18N
		}
		// validate is an attribute
		if (_Validate != null) {
			out.write(" validate");	// NOI18N
			out.write("='");	// NOI18N
			org.megatome.frame2.model.Frame2Config.writeXML(out, _Validate, true);
			out.write("'");	// NOI18N
		}
		out.write(">\n");
		String nextIndent = indent + "	";
		for (java.util.Iterator it = _Handler.iterator(); it.hasNext(); ) {
			org.megatome.frame2.model.Handler element = (org.megatome.frame2.model.Handler)it.next();
			if (element != null) {
				element.writeNode(out, "handler", nextIndent);
			}
		}
		for (java.util.Iterator it = _View.iterator(); it.hasNext(); ) {
			org.megatome.frame2.model.View element = (org.megatome.frame2.model.View)it.next();
			if (element != null) {
				element.writeNode(out, "view", nextIndent);
			}
		}
        
        writeRemainingComments(out, indent);
        
		if (_Security != null) {
			_Security.writeNode(out, "security", nextIndent);
		}
		out.write(indent);
		out.write("</"+nodeName+">\n");
	}

	public void readNode(org.w3c.dom.Node node) {
		if (node.hasAttributes()) {
			org.w3c.dom.NamedNodeMap attrs = node.getAttributes();
			org.w3c.dom.Attr attr;
			attr = (org.w3c.dom.Attr) attrs.getNamedItem("eventName");
			if (attr != null) {
				_EventName = attr.getValue();
			}
			attr = (org.w3c.dom.Attr) attrs.getNamedItem("inputView");
			if (attr != null) {
				_InputView = attr.getValue();
			}
			attr = (org.w3c.dom.Attr) attrs.getNamedItem("cancelView");
			if (attr != null) {
				_CancelView = attr.getValue();
			}
			attr = (org.w3c.dom.Attr) attrs.getNamedItem("validate");
			if (attr != null) {
				_Validate = attr.getValue();
			}
		}
		org.w3c.dom.NodeList children = node.getChildNodes();
		for (int i = 0, size = children.getLength(); i < size; ++i) {
			org.w3c.dom.Node childNode = children.item(i);
			String childNodeName = (childNode.getLocalName() == null ? childNode.getNodeName().intern() : childNode.getLocalName().intern());
			String childNodeValue = "";
			if (childNode.getFirstChild() != null) {
				childNodeValue = childNode.getFirstChild().getNodeValue();
			}
			if (childNodeName == "handler") {
				Handler aHandler = new org.megatome.frame2.model.Handler();
				aHandler.readNode(childNode);
				_Handler.add(aHandler);
			}
			else if (childNodeName == "view") {
				View aView = new org.megatome.frame2.model.View();
				aView.readNode(childNode);
				_View.add(aView);
			}
			else if (childNodeName == "security") {
				_Security = new org.megatome.frame2.model.Security();
				_Security.readNode(childNode);
			}
			else {
				// Found extra unrecognized childNode
                if (childNodeName == "#comment") {
                    recordComment(childNode, i);
                }
			}
		}
	}

	public void validate() throws org.megatome.frame2.model.Frame2Config.ValidateException {
		boolean restrictionFailure = false;
		// Validating property eventName
		if (getEventName() == null) {
			throw new org.megatome.frame2.model.Frame2Config.ValidateException("getEventName() == null", "eventName", this);	// NOI18N
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
			org.megatome.frame2.model.Handler element = getHandler(_index);
			if (element != null) {
				element.validate();
			}
		}
		// Validating property view
		for (int _index = 0; _index < sizeView(); ++_index) {
			org.megatome.frame2.model.View element = getView(_index);
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
		if (name == "eventName")
			setEventName((java.lang.String)value);
		else if (name == "inputView")
			setInputView((java.lang.String)value);
		else if (name == "cancelView")
			setCancelView((java.lang.String)value);
		else if (name == "validate")
			setValidate((java.lang.String)value);
		else if (name == "handler")
			addHandler((Handler)value);
		else if (name == "handler[]")
			setHandler((Handler[]) value);
		else if (name == "view")
			addView((View)value);
		else if (name == "view[]")
			setView((View[]) value);
		else if (name == "security")
			setSecurity((Security)value);
		else
			throw new IllegalArgumentException(name+" is not a valid property name for EventMapping");
	}

	public Object fetchPropertyByName(String name) {
		if (name == "eventName")
			return getEventName();
		if (name == "inputView")
			return getInputView();
		if (name == "cancelView")
			return getCancelView();
		if (name == "validate")
			return getValidate();
		if (name == "handler[]")
			return getHandler();
		if (name == "view[]")
			return getView();
		if (name == "security")
			return getSecurity();
		throw new IllegalArgumentException(name+" is not a valid property name for EventMapping");
	}

	// Return an array of all of the properties that are beans and are set.
	public java.lang.Object[] childBeans(boolean recursive) {
		java.util.List children = new java.util.LinkedList();
		childBeans(recursive, children);
		java.lang.Object[] result = new java.lang.Object[children.size()];
		return (java.lang.Object[]) children.toArray(result);
	}

	// Put all child beans into the beans list.
	public void childBeans(boolean recursive, java.util.List beans) {
		for (java.util.Iterator it = _Handler.iterator(); it.hasNext(); ) {
			org.megatome.frame2.model.Handler element = (org.megatome.frame2.model.Handler)it.next();
			if (element != null) {
				if (recursive) {
					element.childBeans(true, beans);
				}
				beans.add(element);
			}
		}
		for (java.util.Iterator it = _View.iterator(); it.hasNext(); ) {
			org.megatome.frame2.model.View element = (org.megatome.frame2.model.View)it.next();
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
		if (o == this)
			return true;
		if (!(o instanceof org.megatome.frame2.model.EventMapping))
			return false;
		org.megatome.frame2.model.EventMapping inst = (org.megatome.frame2.model.EventMapping) o;
		if (!(_EventName == null ? inst._EventName == null : _EventName.equals(inst._EventName)))
			return false;
		if (!(_InputView == null ? inst._InputView == null : _InputView.equals(inst._InputView)))
			return false;
		if (!(_CancelView == null ? inst._CancelView == null : _CancelView.equals(inst._CancelView)))
			return false;
		if (!(_Validate == null ? inst._Validate == null : _Validate.equals(inst._Validate)))
			return false;
		if (sizeHandler() != inst.sizeHandler())
			return false;
		// Compare every element.
		for (java.util.Iterator it = _Handler.iterator(), it2 = inst._Handler.iterator(); 
			it.hasNext() && it2.hasNext(); ) {
			org.megatome.frame2.model.Handler element = (org.megatome.frame2.model.Handler)it.next();
			org.megatome.frame2.model.Handler element2 = (org.megatome.frame2.model.Handler)it2.next();
			if (!(element == null ? element2 == null : element.equals(element2)))
				return false;
		}
		if (sizeView() != inst.sizeView())
			return false;
		// Compare every element.
		for (java.util.Iterator it = _View.iterator(), it2 = inst._View.iterator(); 
			it.hasNext() && it2.hasNext(); ) {
			org.megatome.frame2.model.View element = (org.megatome.frame2.model.View)it.next();
			org.megatome.frame2.model.View element2 = (org.megatome.frame2.model.View)it2.next();
			if (!(element == null ? element2 == null : element.equals(element2)))
				return false;
		}
		if (!(_Security == null ? inst._Security == null : _Security.equals(inst._Security)))
			return false;
		return true;
	}

	public int hashCode() {
		int result = 17;
		result = 37*result + (_EventName == null ? 0 : _EventName.hashCode());
		result = 37*result + (_InputView == null ? 0 : _InputView.hashCode());
		result = 37*result + (_CancelView == null ? 0 : _CancelView.hashCode());
		result = 37*result + (_Validate == null ? 0 : _Validate.hashCode());
		result = 37*result + (_Handler == null ? 0 : _Handler.hashCode());
		result = 37*result + (_View == null ? 0 : _View.hashCode());
		result = 37*result + (_Security == null ? 0 : _Security.hashCode());
		return result;
	}

}