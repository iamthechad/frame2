/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2007 Megatome Technologies.  All rights
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

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.megatome.frame2.Frame2Plugin;
import org.megatome.frame2.model.Frame2Config.ValidateException;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SchemaMapping extends Frame2DomainObject {

	private String schemaLocation;

	private final List<EventName> eventNames = new ArrayList<EventName>();

	public SchemaMapping() {
		this.schemaLocation = ""; //$NON-NLS-1$
		clearComments();
	}

	@Override
	public SchemaMapping copy() {
		return new SchemaMapping(this);
	}

	private SchemaMapping(final SchemaMapping source) {
		this.schemaLocation = source.schemaLocation;
		for (EventName en : source.eventNames) {
			this.eventNames.add(en.copy());
		}
		setComments(source.getCommentMap());
	}

	public void setSchemaLocation(final String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}

	public String getSchemaLocation() {
		return this.schemaLocation;
	}

	// This attribute is an array, possibly empty
	public void setEventName(final EventName[] value) {
		this.eventNames.clear();
		if (value == null) {
			return;
		}
		for (int i = 0; i < value.length; ++i) {
			this.eventNames.add(value[i]);
		}
	}

	public void setEventName(final int index, final EventName value) {
		this.eventNames.set(index, value);
	}

	public EventName[] getEventName() {
		final EventName[] arr = new EventName[this.eventNames.size()];
		return this.eventNames.toArray(arr);
	}

	public List<EventName> fetchEventNameList() {
		return this.eventNames;
	}

	public EventName getEventName(final int index) {
		return this.eventNames.get(index);
	}

	public int addEventName(final EventName value) {
		this.eventNames.add(value);
		return this.eventNames.size() - 1;
	}

	// Search from the end looking for @param value, and then remove it.
	public int removeEventName(final EventName value) {
		final int pos = this.eventNames.indexOf(value);
		if (pos >= 0) {
			this.eventNames.remove(pos);
		}
		return pos;
	}

	public void readNode(Node node) {
		if (node.hasAttributes()) {
			final NamedNodeMap attrs = node.getAttributes();
			Attr attr;
			attr = (Attr) attrs.getNamedItem(Frame2Plugin
					.getResourceString("Frame2Model.schemaLocation")); //$NON-NLS-1$
			if (attr != null) {
				this.schemaLocation = attr.getValue();
			}
		}
		final NodeList children = node.getChildNodes();
		for (int i = 0, size = children.getLength(); i < size; ++i) {
			final Node childNode = children.item(i);
			final String childNodeName = (childNode.getLocalName() == null ? childNode
					.getNodeName().intern()
					: childNode.getLocalName().intern());
			if (childNodeName.equals(Frame2Plugin
					.getResourceString("Frame2Model.eventDashName"))) { //$NON-NLS-1$
				final EventName aEventName = new EventName();
				aEventName.readNode(childNode);
				this.eventNames.add(aEventName);
			} else {
				// Found extra unrecognized childNode
				if (childNodeName.equals(Frame2Plugin
						.getResourceString("Frame2Model.comment"))) { //$NON-NLS-1$
					recordComment(childNode, i);
				}
			}
		}
	}

	public void validate() throws ValidateException {
		if (getSchemaLocation() == null) {
			throw new Frame2Config.ValidateException(
					Frame2Plugin
							.getResourceString("Frame2Model.schemaLocationNull"), Frame2Plugin.getResourceString("Frame2Model.schemaLocation"), this); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (this.eventNames.isEmpty()) {
			throw new Frame2Config.ValidateException(
					Frame2Plugin
							.getResourceString("Frame2Model.sizeEventNameZero"), Frame2Plugin.getResourceString("Frame2Model.eventDashName"), this); //$NON-NLS-1$ //$NON-NLS-2$
		}
		for (EventName element : this.eventNames) {
			if (element != null) {
				element.validate();
			}
		}
	}

	public void writeNode(Writer out, String nodeName, String indent)
			throws IOException {
		out.write(indent);
		out.write(Frame2Plugin.getResourceString("Frame2Model.tagStart")); //$NON-NLS-1$
		out.write(nodeName);
		if (this.schemaLocation != null) {
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.schemaLocationAttribute")); //$NON-NLS-1$
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.attributeValueStart")); //$NON-NLS-1$
			Frame2Config.writeXML(out, this.schemaLocation, true);
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.attributeValueEnd")); //$NON-NLS-1$
		}
		out.write(Frame2Plugin.getResourceString("Frame2Model.tagFinish")); //$NON-NLS-1$
		final String nextIndent = indent
				+ Frame2Plugin.getResourceString("Frame2Model.indentTabValue"); //$NON-NLS-1$
		for (EventName element : this.eventNames) {
			if (element != null) {
				element.writeNode(out, Frame2Plugin
						.getResourceString("Frame2Model.eventDashName"), nextIndent); //$NON-NLS-1$
			}
		}

		writeRemainingComments(out, indent);
		out.write(indent);
		out
				.write(Frame2Plugin
						.getResourceString("Frame2Model.endTagStart") + nodeName + Frame2Plugin.getResourceString("Frame2Model.tagFinish")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SchemaMapping)) {
			return false;
		}
		
		if (o == this) {
			return true;
		}
		
		SchemaMapping sm = (SchemaMapping)o;
		return (this.schemaLocation.equals(sm.schemaLocation) &&
				this.eventNames.equals(sm.eventNames));
	}

	@Override
	public int hashCode() {
		int retVal = 17;
		retVal = 37 * retVal + this.schemaLocation.hashCode();
		retVal = 37 * retVal + this.eventNames.hashCode();
		return retVal;
	}
}
