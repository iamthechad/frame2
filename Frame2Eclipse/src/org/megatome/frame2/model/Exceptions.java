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

public class Exceptions extends XMLCommentPreserver {
	private java.util.List _Frame2Exception = new java.util.ArrayList();	// List<Frame2Exception>

	public Exceptions() {
        clearComments();
	}

	// Deep copy
	public Exceptions(org.megatome.frame2.model.Exceptions source) {
		for (java.util.Iterator it = source._Frame2Exception.iterator(); 
			it.hasNext(); ) {
			_Frame2Exception.add(new org.megatome.frame2.model.Frame2Exception((org.megatome.frame2.model.Frame2Exception)it.next()));
		}
        
        setComments(source.getCommentMap());
	}

	// This attribute is an array, possibly empty
	public void setFrame2Exception(org.megatome.frame2.model.Frame2Exception[] value) {
		if (value == null)
			value = new Frame2Exception[0];
		_Frame2Exception.clear();
		for (int i = 0; i < value.length; ++i) {
			_Frame2Exception.add(value[i]);
		}
	}

	public void setFrame2Exception(int index, org.megatome.frame2.model.Frame2Exception value) {
		_Frame2Exception.set(index, value);
	}

	public org.megatome.frame2.model.Frame2Exception[] getFrame2Exception() {
		Frame2Exception[] arr = new Frame2Exception[_Frame2Exception.size()];
		return (Frame2Exception[]) _Frame2Exception.toArray(arr);
	}

	public java.util.List fetchFrame2ExceptionList() {
		return _Frame2Exception;
	}

	public org.megatome.frame2.model.Frame2Exception getFrame2Exception(int index) {
		return (Frame2Exception)_Frame2Exception.get(index);
	}

	// Return the number of Frame2Exception
	public int sizeFrame2Exception() {
		return _Frame2Exception.size();
	}

	public int addFrame2Exception(org.megatome.frame2.model.Frame2Exception value) {
		_Frame2Exception.add(value);
		return _Frame2Exception.size()-1;
	}

	// Search from the end looking for @param value, and then remove it.
	public int removeFrame2Exception(org.megatome.frame2.model.Frame2Exception value) {
		int pos = _Frame2Exception.indexOf(value);
		if (pos >= 0) {
			_Frame2Exception.remove(pos);
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
		for (java.util.Iterator it = _Frame2Exception.iterator(); 
			it.hasNext(); ) {
                
            index = writeCommentsAt(out, indent, index);
            
			org.megatome.frame2.model.Frame2Exception element = (org.megatome.frame2.model.Frame2Exception)it.next();
			if (element != null) {
				element.writeNode(out, "exception", nextIndent);
			}
		}
        
        writeRemainingComments(out, indent);
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
			if (childNodeName == "exception") {
				Frame2Exception aFrame2Exception = new org.megatome.frame2.model.Frame2Exception();
				aFrame2Exception.readNode(childNode);
				_Frame2Exception.add(aFrame2Exception);
                elementCount ++;
			}
			else {
				// Found extra unrecognized childNode
                if (childNodeName == "#comment") {
                    recordComment(childNode, elementCount++);
                }
			}
		}
	}

	public void validate() throws org.megatome.frame2.model.Frame2Config.ValidateException {
		boolean restrictionFailure = false;
		// Validating property Frame2Exception
		for (int _index = 0; _index < sizeFrame2Exception(); ++_index) {
			org.megatome.frame2.model.Frame2Exception element = getFrame2Exception(_index);
			if (element != null) {
				element.validate();
			}
		}
	}

	public void changePropertyByName(String name, Object value) {
		if (name == null) return;
		name = name.intern();
		if (name == "Frame2Exception")
			addFrame2Exception((Frame2Exception)value);
		else if (name == "Frame2Exception[]")
			setFrame2Exception((Frame2Exception[]) value);
		else
			throw new IllegalArgumentException(name+" is not a valid property name for Exceptions");
	}

	public Object fetchPropertyByName(String name) {
		if (name == "Frame2Exception[]")
			return getFrame2Exception();
		throw new IllegalArgumentException(name+" is not a valid property name for Exceptions");
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
		for (java.util.Iterator it = _Frame2Exception.iterator(); 
			it.hasNext(); ) {
			org.megatome.frame2.model.Frame2Exception element = (org.megatome.frame2.model.Frame2Exception)it.next();
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
		if (!(o instanceof org.megatome.frame2.model.Exceptions))
			return false;
		org.megatome.frame2.model.Exceptions inst = (org.megatome.frame2.model.Exceptions) o;
		if (sizeFrame2Exception() != inst.sizeFrame2Exception())
			return false;
		// Compare every element.
		for (java.util.Iterator it = _Frame2Exception.iterator(), it2 = inst._Frame2Exception.iterator(); 
			it.hasNext() && it2.hasNext(); ) {
			org.megatome.frame2.model.Frame2Exception element = (org.megatome.frame2.model.Frame2Exception)it.next();
			org.megatome.frame2.model.Frame2Exception element2 = (org.megatome.frame2.model.Frame2Exception)it2.next();
			if (!(element == null ? element2 == null : element.equals(element2)))
				return false;
		}
		return true;
	}

	public int hashCode() {
		int result = 17;
		result = 37*result + (_Frame2Exception == null ? 0 : _Frame2Exception.hashCode());
		return result;
	}

}