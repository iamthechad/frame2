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

public class InitParam {
	private java.lang.String _Name;
	private java.lang.String _Value;

	public InitParam() {
		_Name = "";
		_Value = "";
	}

	// Deep copy
	public InitParam(org.megatome.frame2.model.InitParam source) {
		_Name = source._Name;
		_Value = source._Value;
	}

	// This attribute is mandatory
	public void setName(java.lang.String value) {
		_Name = value;
	}

	public java.lang.String getName() {
		return _Name;
	}

	// This attribute is mandatory
	public void setValue(java.lang.String value) {
		_Value = value;
	}

	public java.lang.String getValue() {
		return _Value;
	}

	public void writeNode(java.io.Writer out, String nodeName, String indent) throws java.io.IOException {
		out.write(indent);
		out.write("<");
		out.write(nodeName);
		// name is an attribute
		if (_Name != null) {
			out.write(" name");	// NOI18N
			out.write("='");	// NOI18N
			org.megatome.frame2.model.Frame2Config.writeXML(out, _Name, true);
			out.write("'");	// NOI18N
		}
		// value is an attribute
		if (_Value != null) {
			out.write(" value");	// NOI18N
			out.write("='");	// NOI18N
			org.megatome.frame2.model.Frame2Config.writeXML(out, _Value, true);
			out.write("'");	// NOI18N
		}
		out.write("/>\n");
		//String nextIndent = indent + "	";
		//out.write(indent);
		//out.write("</"+nodeName+">\n");
	}

	public void readNode(org.w3c.dom.Node node) {
		if (node.hasAttributes()) {
			org.w3c.dom.NamedNodeMap attrs = node.getAttributes();
			org.w3c.dom.Attr attr;
			attr = (org.w3c.dom.Attr) attrs.getNamedItem("name");
			if (attr != null) {
				_Name = attr.getValue();
			}
			attr = (org.w3c.dom.Attr) attrs.getNamedItem("value");
			if (attr != null) {
				_Value = attr.getValue();
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
		}
	}

	public void validate() throws org.megatome.frame2.model.Frame2Config.ValidateException {
		boolean restrictionFailure = false;
		// Validating property name
		if (getName() == null) {
			throw new org.megatome.frame2.model.Frame2Config.ValidateException("getName() == null", "name", this);	// NOI18N
		}
		// Validating property value
		if (getValue() == null) {
			throw new org.megatome.frame2.model.Frame2Config.ValidateException("getValue() == null", "value", this);	// NOI18N
		}
	}

	public void changePropertyByName(String name, Object value) {
		if (name == null) return;
		name = name.intern();
		if (name == "name")
			setName((java.lang.String)value);
		else if (name == "value")
			setValue((java.lang.String)value);
		else
			throw new IllegalArgumentException(name+" is not a valid property name for InitParam");
	}

	public Object fetchPropertyByName(String name) {
		if (name == "name")
			return getName();
		if (name == "value")
			return getValue();
		throw new IllegalArgumentException(name+" is not a valid property name for InitParam");
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
	}

	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof org.megatome.frame2.model.InitParam))
			return false;
		org.megatome.frame2.model.InitParam inst = (org.megatome.frame2.model.InitParam) o;
		if (!(_Name == null ? inst._Name == null : _Name.equals(inst._Name)))
			return false;
		if (!(_Value == null ? inst._Value == null : _Value.equals(inst._Value)))
			return false;
		return true;
	}

	public int hashCode() {
		int result = 17;
		result = 37*result + (_Name == null ? 0 : _Name.hashCode());
		result = 37*result + (_Value == null ? 0 : _Value.hashCode());
		return result;
	}

}