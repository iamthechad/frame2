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

public class Plugin extends XMLCommentPreserver {
	private java.lang.String _Name;
	private java.lang.String _Type;
	private java.util.List _InitParam = new java.util.ArrayList();	// List<InitParam>

	public Plugin() {
		_Name = "";
		_Type = "";
        clearComments();
	}

	// Deep copy
	public Plugin(org.megatome.frame2.model.Plugin source) {
		_Name = source._Name;
		_Type = source._Type;
		for (java.util.Iterator it = source._InitParam.iterator(); 
			it.hasNext(); ) {
			_InitParam.add(new org.megatome.frame2.model.InitParam((org.megatome.frame2.model.InitParam)it.next()));
		}
        
        setComments(source.getCommentMap());
	}

	// This attribute is mandatory
	public void setName(java.lang.String value) {
		_Name = value;
	}

	public java.lang.String getName() {
		return _Name;
	}

	// This attribute is mandatory
	public void setType(java.lang.String value) {
		_Type = value;
	}

	public java.lang.String getType() {
		return _Type;
	}

	// This attribute is an array, possibly empty
	public void setInitParam(org.megatome.frame2.model.InitParam[] value) {
		if (value == null)
			value = new InitParam[0];
		_InitParam.clear();
		for (int i = 0; i < value.length; ++i) {
			_InitParam.add(value[i]);
		}
	}

	public void setInitParam(int index, org.megatome.frame2.model.InitParam value) {
		_InitParam.set(index, value);
	}

	public org.megatome.frame2.model.InitParam[] getInitParam() {
		InitParam[] arr = new InitParam[_InitParam.size()];
		return (InitParam[]) _InitParam.toArray(arr);
	}

	public java.util.List fetchInitParamList() {
		return _InitParam;
	}

	public org.megatome.frame2.model.InitParam getInitParam(int index) {
		return (InitParam)_InitParam.get(index);
	}

	// Return the number of initParam
	public int sizeInitParam() {
		return _InitParam.size();
	}

	public int addInitParam(org.megatome.frame2.model.InitParam value) {
		_InitParam.add(value);
		return _InitParam.size()-1;
	}

	// Search from the end looking for @param value, and then remove it.
	public int removeInitParam(org.megatome.frame2.model.InitParam value) {
		int pos = _InitParam.indexOf(value);
		if (pos >= 0) {
			_InitParam.remove(pos);
		}
		return pos;
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
		for (java.util.Iterator it = _InitParam.iterator(); it.hasNext(); 
			) {
                
            index = writeCommentsAt(out, indent, index);
			org.megatome.frame2.model.InitParam element = (org.megatome.frame2.model.InitParam)it.next();
			if (element != null) {
				element.writeNode(out, "init-param", nextIndent);
			}
		}
        writeRemainingComments(out,indent);
		out.write(indent);
		out.write("</"+nodeName+">\n");
	}

	public void readNode(org.w3c.dom.Node node) {
		if (node.hasAttributes()) {
			org.w3c.dom.NamedNodeMap attrs = node.getAttributes();
			org.w3c.dom.Attr attr;
			attr = (org.w3c.dom.Attr) attrs.getNamedItem("name");
			if (attr != null) {
				_Name = attr.getValue();
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
			if (childNodeName == "init-param") {
				InitParam aInitParam = new org.megatome.frame2.model.InitParam();
				aInitParam.readNode(childNode);
				_InitParam.add(aInitParam);
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
		// Validating property name
		if (getName() == null) {
			throw new org.megatome.frame2.model.Frame2Config.ValidateException("getName() == null", "name", this);	// NOI18N
		}
		// Validating property type
		if (getType() == null) {
			throw new org.megatome.frame2.model.Frame2Config.ValidateException("getType() == null", "type", this);	// NOI18N
		}
		// Validating property initParam
		for (int _index = 0; _index < sizeInitParam(); ++_index) {
			org.megatome.frame2.model.InitParam element = getInitParam(_index);
			if (element != null) {
				element.validate();
			}
		}
	}

	public void changePropertyByName(String name, Object value) {
		if (name == null) return;
		name = name.intern();
		if (name == "name")
			setName((java.lang.String)value);
		else if (name == "type")
			setType((java.lang.String)value);
		else if (name == "initParam")
			addInitParam((InitParam)value);
		else if (name == "initParam[]")
			setInitParam((InitParam[]) value);
		else
			throw new IllegalArgumentException(name+" is not a valid property name for Plugin");
	}

	public Object fetchPropertyByName(String name) {
		if (name == "name")
			return getName();
		if (name == "type")
			return getType();
		if (name == "initParam[]")
			return getInitParam();
		throw new IllegalArgumentException(name+" is not a valid property name for Plugin");
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
		for (java.util.Iterator it = _InitParam.iterator(); it.hasNext(); 
			) {
			org.megatome.frame2.model.InitParam element = (org.megatome.frame2.model.InitParam)it.next();
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
		if (!(o instanceof org.megatome.frame2.model.Plugin))
			return false;
		org.megatome.frame2.model.Plugin inst = (org.megatome.frame2.model.Plugin) o;
		if (!(_Name == null ? inst._Name == null : _Name.equals(inst._Name)))
			return false;
		if (!(_Type == null ? inst._Type == null : _Type.equals(inst._Type)))
			return false;
		if (sizeInitParam() != inst.sizeInitParam())
			return false;
		// Compare every element.
		for (java.util.Iterator it = _InitParam.iterator(), it2 = inst._InitParam.iterator(); 
			it.hasNext() && it2.hasNext(); ) {
			org.megatome.frame2.model.InitParam element = (org.megatome.frame2.model.InitParam)it.next();
			org.megatome.frame2.model.InitParam element2 = (org.megatome.frame2.model.InitParam)it2.next();
			if (!(element == null ? element2 == null : element.equals(element2)))
				return false;
		}
		return true;
	}

	public int hashCode() {
		int result = 17;
		result = 37*result + (_Name == null ? 0 : _Name.hashCode());
		result = 37*result + (_Type == null ? 0 : _Type.hashCode());
		result = 37*result + (_InitParam == null ? 0 : _InitParam.hashCode());
		return result;
	}

}