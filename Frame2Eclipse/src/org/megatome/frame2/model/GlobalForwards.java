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

public class GlobalForwards extends XMLCommentPreserver {
	private java.util.List _Forward = new java.util.ArrayList();	// List<Forward>

	public GlobalForwards() {
        clearComments();
	}

	// Deep copy
	public GlobalForwards(org.megatome.frame2.model.GlobalForwards source) {
		for (java.util.Iterator it = source._Forward.iterator(); 
			it.hasNext(); ) {
			_Forward.add(new org.megatome.frame2.model.Forward((org.megatome.frame2.model.Forward)it.next()));
		}
        
        setComments(source.getCommentMap());
	}

	// This attribute is an array, possibly empty
	public void setForward(org.megatome.frame2.model.Forward[] value) {
		if (value == null)
			value = new Forward[0];
		_Forward.clear();
		for (int i = 0; i < value.length; ++i) {
			_Forward.add(value[i]);
		}
	}

	public void setForward(int index, org.megatome.frame2.model.Forward value) {
		_Forward.set(index, value);
	}

	public org.megatome.frame2.model.Forward[] getForward() {
		Forward[] arr = new Forward[_Forward.size()];
		return (Forward[]) _Forward.toArray(arr);
	}

	public java.util.List fetchForwardList() {
		return _Forward;
	}

	public org.megatome.frame2.model.Forward getForward(int index) {
		return (Forward)_Forward.get(index);
	}

	// Return the number of forward
	public int sizeForward() {
		return _Forward.size();
	}

	public int addForward(org.megatome.frame2.model.Forward value) {
		_Forward.add(value);
		return _Forward.size()-1;
	}

	// Search from the end looking for @param value, and then remove it.
	public int removeForward(org.megatome.frame2.model.Forward value) {
		int pos = _Forward.indexOf(value);
		if (pos >= 0) {
			_Forward.remove(pos);
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
		for (java.util.Iterator it = _Forward.iterator(); it.hasNext(); ) {
            
            index = writeCommentsAt(out, indent, index);
            
			org.megatome.frame2.model.Forward element = (org.megatome.frame2.model.Forward)it.next();
			if (element != null) {
				element.writeNode(out, "forward", nextIndent);
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
			if (childNodeName == "forward") {
				Forward aForward = new org.megatome.frame2.model.Forward();
				aForward.readNode(childNode);
				_Forward.add(aForward);
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
		// Validating property forward
		for (int _index = 0; _index < sizeForward(); ++_index) {
			org.megatome.frame2.model.Forward element = getForward(_index);
			if (element != null) {
				element.validate();
			}
		}
	}

	public void changePropertyByName(String name, Object value) {
		if (name == null) return;
		name = name.intern();
		if (name == "forward")
			addForward((Forward)value);
		else if (name == "forward[]")
			setForward((Forward[]) value);
		else
			throw new IllegalArgumentException(name+" is not a valid property name for GlobalForwards");
	}

	public Object fetchPropertyByName(String name) {
		if (name == "forward[]")
			return getForward();
		throw new IllegalArgumentException(name+" is not a valid property name for GlobalForwards");
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
		for (java.util.Iterator it = _Forward.iterator(); it.hasNext(); ) {
			org.megatome.frame2.model.Forward element = (org.megatome.frame2.model.Forward)it.next();
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
		if (!(o instanceof org.megatome.frame2.model.GlobalForwards))
			return false;
		org.megatome.frame2.model.GlobalForwards inst = (org.megatome.frame2.model.GlobalForwards) o;
		if (sizeForward() != inst.sizeForward())
			return false;
		// Compare every element.
		for (java.util.Iterator it = _Forward.iterator(), it2 = inst._Forward.iterator(); 
			it.hasNext() && it2.hasNext(); ) {
			org.megatome.frame2.model.Forward element = (org.megatome.frame2.model.Forward)it.next();
			org.megatome.frame2.model.Forward element2 = (org.megatome.frame2.model.Forward)it2.next();
			if (!(element == null ? element2 == null : element.equals(element2)))
				return false;
		}
		return true;
	}

	public int hashCode() {
		int result = 17;
		result = 37*result + (_Forward == null ? 0 : _Forward.hashCode());
		return result;
	}

}