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

public class EventMappings extends XMLCommentPreserver {
	private java.util.List _EventMapping = new java.util.ArrayList();	// List<EventMapping>

	public EventMappings() {
        clearComments();
	}

	// Deep copy
	public EventMappings(org.megatome.frame2.model.EventMappings source) {
		for (java.util.Iterator it = source._EventMapping.iterator(); 
			it.hasNext(); ) {
			_EventMapping.add(new org.megatome.frame2.model.EventMapping((org.megatome.frame2.model.EventMapping)it.next()));
		}
        
        setComments(source.getCommentMap());
	}

	// This attribute is an array, possibly empty
	public void setEventMapping(org.megatome.frame2.model.EventMapping[] value) {
		if (value == null)
			value = new EventMapping[0];
		_EventMapping.clear();
		for (int i = 0; i < value.length; ++i) {
			_EventMapping.add(value[i]);
		}
	}

	public void setEventMapping(int index, org.megatome.frame2.model.EventMapping value) {
		_EventMapping.set(index, value);
	}

	public org.megatome.frame2.model.EventMapping[] getEventMapping() {
		EventMapping[] arr = new EventMapping[_EventMapping.size()];
		return (EventMapping[]) _EventMapping.toArray(arr);
	}

	public java.util.List fetchEventMappingList() {
		return _EventMapping;
	}

	public org.megatome.frame2.model.EventMapping getEventMapping(int index) {
		return (EventMapping)_EventMapping.get(index);
	}

	// Return the number of eventMapping
	public int sizeEventMapping() {
		return _EventMapping.size();
	}

	public int addEventMapping(org.megatome.frame2.model.EventMapping value) {
		_EventMapping.add(value);
		return _EventMapping.size()-1;
	}

	// Search from the end looking for @param value, and then remove it.
	public int removeEventMapping(org.megatome.frame2.model.EventMapping value) {
		int pos = _EventMapping.indexOf(value);
		if (pos >= 0) {
			_EventMapping.remove(pos);
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
		for (java.util.Iterator it = _EventMapping.iterator(); 
			it.hasNext(); ) {
                
            index = writeCommentsAt(out, indent, index);
            
			org.megatome.frame2.model.EventMapping element = (org.megatome.frame2.model.EventMapping)it.next();
			if (element != null) {
				element.writeNode(out, "event-mapping", nextIndent);
			}
		}
        
        writeRemainingComments(out, indent);
		out.write(indent);
		out.write("</"+nodeName+">\n");
	}

	public void readNode(org.w3c.dom.Node node) {
		org.w3c.dom.NodeList children = node.getChildNodes();
        int elementCount  =0;
		for (int i = 0, size = children.getLength(); i < size; ++i) {
			org.w3c.dom.Node childNode = children.item(i);
			String childNodeName = (childNode.getLocalName() == null ? childNode.getNodeName().intern() : childNode.getLocalName().intern());
			String childNodeValue = "";
			if (childNode.getFirstChild() != null) {
				childNodeValue = childNode.getFirstChild().getNodeValue();
			}
			if (childNodeName == "event-mapping") {
				EventMapping aEventMapping = new org.megatome.frame2.model.EventMapping();
				aEventMapping.readNode(childNode);
				_EventMapping.add(aEventMapping);
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
		// Validating property eventMapping
		for (int _index = 0; _index < sizeEventMapping(); ++_index) {
			org.megatome.frame2.model.EventMapping element = getEventMapping(_index);
			if (element != null) {
				element.validate();
			}
		}
	}

	public void changePropertyByName(String name, Object value) {
		if (name == null) return;
		name = name.intern();
		if (name == "eventMapping")
			addEventMapping((EventMapping)value);
		else if (name == "eventMapping[]")
			setEventMapping((EventMapping[]) value);
		else
			throw new IllegalArgumentException(name+" is not a valid property name for EventMappings");
	}

	public Object fetchPropertyByName(String name) {
		if (name == "eventMapping[]")
			return getEventMapping();
		throw new IllegalArgumentException(name+" is not a valid property name for EventMappings");
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
		for (java.util.Iterator it = _EventMapping.iterator(); 
			it.hasNext(); ) {
			org.megatome.frame2.model.EventMapping element = (org.megatome.frame2.model.EventMapping)it.next();
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
		if (!(o instanceof org.megatome.frame2.model.EventMappings))
			return false;
		org.megatome.frame2.model.EventMappings inst = (org.megatome.frame2.model.EventMappings) o;
		if (sizeEventMapping() != inst.sizeEventMapping())
			return false;
		// Compare every element.
		for (java.util.Iterator it = _EventMapping.iterator(), it2 = inst._EventMapping.iterator(); 
			it.hasNext() && it2.hasNext(); ) {
			org.megatome.frame2.model.EventMapping element = (org.megatome.frame2.model.EventMapping)it.next();
			org.megatome.frame2.model.EventMapping element2 = (org.megatome.frame2.model.EventMapping)it2.next();
			if (!(element == null ? element2 == null : element.equals(element2)))
				return false;
		}
		return true;
	}

	public int hashCode() {
		int result = 17;
		result = 37*result + (_EventMapping == null ? 0 : _EventMapping.hashCode());
		return result;
	}

}