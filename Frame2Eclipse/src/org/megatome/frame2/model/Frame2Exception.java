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

public class Frame2Exception extends XMLCommentPreserver {
	private java.lang.String _RequestKey;
	private java.lang.String _Type;
	private java.util.List _View = new java.util.ArrayList();	// List<View>

	public Frame2Exception() {
		_RequestKey = "";
		_Type = "";
        clearComments();
	}

	// Deep copy
	public Frame2Exception(org.megatome.frame2.model.Frame2Exception source) {
		_RequestKey = source._RequestKey;
		_Type = source._Type;
		for (java.util.Iterator it = source._View.iterator(); 
			it.hasNext(); ) {
			_View.add(new org.megatome.frame2.model.View((org.megatome.frame2.model.View)it.next()));
		}
        setComments(source.getCommentMap());
	}

	// This attribute is mandatory
	public void setRequestKey(java.lang.String value) {
		_RequestKey = value;
	}

	public java.lang.String getRequestKey() {
		return _RequestKey;
	}

	// This attribute is mandatory
	public void setType(java.lang.String value) {
		_Type = value;
	}

	public java.lang.String getType() {
		return _Type;
	}

	// This attribute is an array containing at least one element
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

	public void writeNode(java.io.Writer out, String nodeName, String indent) throws java.io.IOException {
		out.write(indent);
		out.write("<");
		out.write(nodeName);
		// requestKey is an attribute
		if (_RequestKey != null) {
			out.write(" requestKey");	// NOI18N
			out.write("='");	// NOI18N
			org.megatome.frame2.model.Frame2Config.writeXML(out, _RequestKey, true);
			out.write("'");	// NOI18N
		}
		// type is an attribute
		if (_Type != null) {
			out.write(" type");	// NOI18N
			out.write("='");	// NOI18N
			org.megatome.frame2.model.Frame2Config.writeXML(out, _Type, true);
			out.write("'");	// NOI18N
		}
		out.write(">\n");
		String nextIndent = indent + "	";
        int index = 0;
		for (java.util.Iterator it = _View.iterator(); it.hasNext(); ) {
            
            index = writeCommentsAt(out, indent, index);
			org.megatome.frame2.model.View element = (org.megatome.frame2.model.View)it.next();
			if (element != null) {
				element.writeNode(out, "view", nextIndent);
			}
		}
        
        writeRemainingComments(out, indent);
		out.write(indent);
		out.write("</"+nodeName+">\n");
	}

	public void readNode(org.w3c.dom.Node node) {
		if (node.hasAttributes()) {
			org.w3c.dom.NamedNodeMap attrs = node.getAttributes();
			org.w3c.dom.Attr attr;
			attr = (org.w3c.dom.Attr) attrs.getNamedItem("requestKey");
			if (attr != null) {
				_RequestKey = attr.getValue();
			}
			attr = (org.w3c.dom.Attr) attrs.getNamedItem("type");
			if (attr != null) {
				_Type = attr.getValue();
			}
		}
		org.w3c.dom.NodeList children = node.getChildNodes();
        int elementCount = 0;
		for (int i = 0, size = children.getLength(); i < size; ++i) {
			org.w3c.dom.Node childNode = children.item(i);
			String childNodeName = (childNode.getLocalName() == null ? childNode.getNodeName().intern() : childNode.getLocalName().intern());
			String childNodeValue = "";
			if (childNode.getFirstChild() != null) {
				childNodeValue = childNode.getFirstChild().getNodeValue();
			}
			if (childNodeName == "view") {
				View aView = new org.megatome.frame2.model.View();
				aView.readNode(childNode);
				_View.add(aView);
                elementCount ++;
			}
			else {
				// Found extra unrecognized childNode
                if (childNodeName == "#comment") {
                    recordComment(childNode, elementCount ++);
                }
			}
		}
	}

	public void validate() throws org.megatome.frame2.model.Frame2Config.ValidateException {
		boolean restrictionFailure = false;
		// Validating property requestKey
		if (getRequestKey() == null) {
			throw new org.megatome.frame2.model.Frame2Config.ValidateException("getRequestKey() == null", "requestKey", this);	// NOI18N
		}
		// Validating property type
		if (getType() == null) {
			throw new org.megatome.frame2.model.Frame2Config.ValidateException("getType() == null", "type", this);	// NOI18N
		}
		// Validating property view
		if (sizeView() == 0) {
			throw new org.megatome.frame2.model.Frame2Config.ValidateException("sizeView() == 0", "view", this);	// NOI18N
		}
		for (int _index = 0; _index < sizeView(); ++_index) {
			org.megatome.frame2.model.View element = getView(_index);
			if (element != null) {
				element.validate();
			}
		}
	}

	public void changePropertyByName(String name, Object value) {
		if (name == null) return;
		name = name.intern();
		if (name == "requestKey")
			setRequestKey((java.lang.String)value);
		else if (name == "type")
			setType((java.lang.String)value);
		else if (name == "view")
			addView((View)value);
		else if (name == "view[]")
			setView((View[]) value);
		else
			throw new IllegalArgumentException(name+" is not a valid property name for Frame2Exception");
	}

	public Object fetchPropertyByName(String name) {
		if (name == "requestKey")
			return getRequestKey();
		if (name == "type")
			return getType();
		if (name == "view[]")
			return getView();
		throw new IllegalArgumentException(name+" is not a valid property name for Frame2Exception");
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
		for (java.util.Iterator it = _View.iterator(); it.hasNext(); ) {
			org.megatome.frame2.model.View element = (org.megatome.frame2.model.View)it.next();
			if (element != null) {
				if (recursive) {
					element.childBeans(true, beans);
				}
				beans.add(element);
			}
		}
	}

	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof org.megatome.frame2.model.Frame2Exception))
			return false;
		org.megatome.frame2.model.Frame2Exception inst = (org.megatome.frame2.model.Frame2Exception) o;
		if (!(_RequestKey == null ? inst._RequestKey == null : _RequestKey.equals(inst._RequestKey)))
			return false;
		if (!(_Type == null ? inst._Type == null : _Type.equals(inst._Type)))
			return false;
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
		return true;
	}

	public int hashCode() {
		int result = 17;
		result = 37*result + (_RequestKey == null ? 0 : _RequestKey.hashCode());
		result = 37*result + (_Type == null ? 0 : _Type.hashCode());
		result = 37*result + (_View == null ? 0 : _View.hashCode());
		return result;
	}

}