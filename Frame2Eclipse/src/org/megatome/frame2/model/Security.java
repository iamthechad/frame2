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

public class Security extends XMLCommentPreserver {
	private java.util.List _Role = new java.util.ArrayList();	// List<Role>

	public Security() {
        clearComments();
	}

	// Deep copy
	public Security(org.megatome.frame2.model.Security source) {
		for (java.util.Iterator it = source._Role.iterator(); 
			it.hasNext(); ) {
			_Role.add(new org.megatome.frame2.model.Role((org.megatome.frame2.model.Role)it.next()));
		}
        
        setComments(source.getCommentMap());
	}

	// This attribute is an array containing at least one element
	public void setRole(org.megatome.frame2.model.Role[] value) {
		if (value == null)
			value = new Role[0];
		_Role.clear();
		for (int i = 0; i < value.length; ++i) {
			_Role.add(value[i]);
		}
	}

	public void setRole(int index, org.megatome.frame2.model.Role value) {
		_Role.set(index, value);
	}

	public org.megatome.frame2.model.Role[] getRole() {
		Role[] arr = new Role[_Role.size()];
		return (Role[]) _Role.toArray(arr);
	}

	public java.util.List fetchRoleList() {
		return _Role;
	}

	public org.megatome.frame2.model.Role getRole(int index) {
		return (Role)_Role.get(index);
	}

	// Return the number of role
	public int sizeRole() {
		return _Role.size();
	}

	public int addRole(org.megatome.frame2.model.Role value) {
		_Role.add(value);
		return _Role.size()-1;
	}

	// Search from the end looking for @param value, and then remove it.
	public int removeRole(org.megatome.frame2.model.Role value) {
		int pos = _Role.indexOf(value);
		if (pos >= 0) {
			_Role.remove(pos);
		}
		return pos;
	}

	public void writeNode(java.io.Writer out, String nodeName, String indent) throws java.io.IOException {
		out.write(indent);
		out.write("<");
		out.write(nodeName);
		out.write(">\n");
		String nextIndent = indent + "	";
        int index = 0;
		for (java.util.Iterator it = _Role.iterator(); it.hasNext(); ) {
            
            index = writeCommentsAt(out, indent, index);
            
			org.megatome.frame2.model.Role element = (org.megatome.frame2.model.Role)it.next();
			if (element != null) {
				element.writeNode(out, "role", nextIndent);
			}
		}
        writeRemainingComments(out,indent);
		out.write(indent);
		out.write("</"+nodeName+">\n");
	}

	public void readNode(org.w3c.dom.Node node) {
		org.w3c.dom.NodeList children = node.getChildNodes();
        int elementCount = 0;
		for (int i = 0, size = children.getLength(); i < size; ++i) {
			org.w3c.dom.Node childNode = children.item(i);
			String childNodeName = (childNode.getLocalName() == null ? childNode.getNodeName().intern() : childNode.getLocalName().intern());
			String childNodeValue = "";
			if (childNode.getFirstChild() != null) {
				childNodeValue = childNode.getFirstChild().getNodeValue();
			}
			if (childNodeName == "role") {
				Role aRole = new org.megatome.frame2.model.Role();
				aRole.readNode(childNode);
				_Role.add(aRole);
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
		// Validating property role
		if (sizeRole() == 0) {
			throw new org.megatome.frame2.model.Frame2Config.ValidateException("sizeRole() == 0", "role", this);	// NOI18N
		}
		for (int _index = 0; _index < sizeRole(); ++_index) {
			org.megatome.frame2.model.Role element = getRole(_index);
			if (element != null) {
				element.validate();
			}
		}
	}

	public void changePropertyByName(String name, Object value) {
		if (name == null) return;
		name = name.intern();
		if (name == "role")
			addRole((Role)value);
		else if (name == "role[]")
			setRole((Role[]) value);
		else
			throw new IllegalArgumentException(name+" is not a valid property name for Security");
	}

	public Object fetchPropertyByName(String name) {
		if (name == "role[]")
			return getRole();
		throw new IllegalArgumentException(name+" is not a valid property name for Security");
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
		for (java.util.Iterator it = _Role.iterator(); it.hasNext(); ) {
			org.megatome.frame2.model.Role element = (org.megatome.frame2.model.Role)it.next();
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
		if (!(o instanceof org.megatome.frame2.model.Security))
			return false;
		org.megatome.frame2.model.Security inst = (org.megatome.frame2.model.Security) o;
		if (sizeRole() != inst.sizeRole())
			return false;
		// Compare every element.
		for (java.util.Iterator it = _Role.iterator(), it2 = inst._Role.iterator(); 
			it.hasNext() && it2.hasNext(); ) {
			org.megatome.frame2.model.Role element = (org.megatome.frame2.model.Role)it.next();
			org.megatome.frame2.model.Role element2 = (org.megatome.frame2.model.Role)it2.next();
			if (!(element == null ? element2 == null : element.equals(element2)))
				return false;
		}
		return true;
	}

	public int hashCode() {
		int result = 17;
		result = 37*result + (_Role == null ? 0 : _Role.hashCode());
		return result;
	}

}