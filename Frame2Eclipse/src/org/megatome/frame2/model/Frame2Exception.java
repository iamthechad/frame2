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
import java.util.List;

import org.megatome.frame2.Frame2Plugin;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Frame2Exception extends Frame2DomainObject {

	private String requestKey;

	private String type;

	private final List<View> views = new ArrayList<View>(); // List<View>

	public Frame2Exception() {
		this.requestKey = ""; //$NON-NLS-1$
		this.type = ""; //$NON-NLS-1$
		clearComments();
	}
	
	@Override
	public Frame2Exception copy() {
		return new Frame2Exception(this);
	}

	// Deep copy
	private Frame2Exception(final Frame2Exception source) {
		this.requestKey = source.requestKey;
		this.type = source.type;
		for (View view : source.views) {
			this.views.add(view.copy());
		}
		setComments(source.getCommentMap());
	}

	// This attribute is mandatory
	public void setRequestKey(final String value) {
		this.requestKey = value;
	}

	public String getRequestKey() {
		return this.requestKey;
	}

	// This attribute is mandatory
	public void setType(final String value) {
		this.type = value;
	}

	public String getType() {
		return this.type;
	}

	// This attribute is an array containing at least one element
	public void setView(final View[] value) {
		this.views.clear();
		if (value == null) {
			return;
		}
		for (int i = 0; i < value.length; ++i) {
			this.views.add(value[i]);
		}
	}

	public void setView(final int index, final View value) {
		this.views.set(index, value);
	}

	public View[] getView() {
		final View[] arr = new View[this.views.size()];
		return this.views.toArray(arr);
	}

	public List<View> fetchViewList() {
		return this.views;
	}

	public View getView(final int index) {
		return this.views.get(index);
	}

	public int addView(final View value) {
		this.views.add(value);
		return this.views.size() - 1;
	}

	// Search from the end looking for @param value, and then remove it.
	public int removeView(final View value) {
		final int pos = this.views.indexOf(value);
		if (pos >= 0) {
			this.views.remove(pos);
		}
		return pos;
	}

	public void writeNode(final Writer out, final String nodeName,
			final String indent) throws IOException {
		out.write(indent);
		out.write(Frame2Plugin.getResourceString("Frame2Model.tagStart")); //$NON-NLS-1$
		out.write(nodeName);
		// requestKey is an attribute
		if (this.requestKey != null) {
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.requestKeyAttribute")); //$NON-NLS-1$
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.attributeValueStart")); //$NON-NLS-1$
			Frame2Config.writeXML(out, this.requestKey, true);
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.attributeValueEnd")); //$NON-NLS-1$
		}
		// type is an attribute
		if (this.type != null) {
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.typeAttribute")); //$NON-NLS-1$
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.attributeValueStart")); //$NON-NLS-1$
			Frame2Config.writeXML(out, this.type, true);
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.attributeValueEnd")); //$NON-NLS-1$
		}
		out.write(Frame2Plugin.getResourceString("Frame2Model.tagFinish")); //$NON-NLS-1$
		final String nextIndent = indent
				+ Frame2Plugin.getResourceString("Frame2Model.indentTabValue"); //$NON-NLS-1$
		int index = 0;
		for (View element : this.views) {
			index = writeCommentsAt(out, indent, index);
			if (element != null) {
				element.writeNode(out, Frame2Plugin
						.getResourceString("Frame2Model.view"), nextIndent); //$NON-NLS-1$
			}
		}

		writeRemainingComments(out, indent);
		out.write(indent);
		out
				.write(Frame2Plugin
						.getResourceString("Frame2Model.endTagStart") + nodeName + Frame2Plugin.getResourceString("Frame2Model.tagFinish")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void readNode(final Node node) {
		if (node.hasAttributes()) {
			final NamedNodeMap attrs = node.getAttributes();
			Attr attr;
			attr = (Attr) attrs.getNamedItem(Frame2Plugin
					.getResourceString("Frame2Model.requestKey")); //$NON-NLS-1$
			if (attr != null) {
				this.requestKey = attr.getValue();
			}
			attr = (Attr) attrs.getNamedItem(Frame2Plugin
					.getResourceString("Frame2Model.type")); //$NON-NLS-1$
			if (attr != null) {
				this.type = attr.getValue();
			}
		}
		final NodeList children = node.getChildNodes();
		int elementCount = 0;
		for (int i = 0, size = children.getLength(); i < size; ++i) {
			final Node childNode = children.item(i);
			final String childNodeName = (childNode.getLocalName() == null ? childNode
					.getNodeName().intern()
					: childNode.getLocalName().intern());
			if (childNodeName.equals(Frame2Plugin
					.getResourceString("Frame2Model.view"))) { //$NON-NLS-1$
				final View aView = new View();
				aView.readNode(childNode);
				this.views.add(aView);
				elementCount++;
			} else {
				// Found extra unrecognized childNode
				if (childNodeName.equals(Frame2Plugin
						.getResourceString("Frame2Model.comment"))) { //$NON-NLS-1$
					recordComment(childNode, elementCount++);
				}
			}
		}
	}

	public void validate() throws Frame2Config.ValidateException {
		// Validating property requestKey
		if (getRequestKey() == null) {
			throw new Frame2Config.ValidateException(
					Frame2Plugin
							.getResourceString("Frame2Model.getRequestKeyNull"), Frame2Plugin.getResourceString("Frame2Model.requestKey"), this); //$NON-NLS-1$ //$NON-NLS-2$
		}
		// Validating property type
		if (getType() == null) {
			throw new Frame2Config.ValidateException(
					Frame2Plugin.getResourceString("Frame2Model.getTypeNull"), Frame2Plugin.getResourceString("Frame2Model.type"), this); //$NON-NLS-1$ //$NON-NLS-2$
		}
		// Validating property view
		if (this.views.isEmpty()) {
			throw new Frame2Config.ValidateException(
					Frame2Plugin.getResourceString("Frame2Model.sizeViewZero"), Frame2Plugin.getResourceString("Frame2Model.view"), this); //$NON-NLS-1$ //$NON-NLS-2$
		}
		for (View element : this.views) {
			if (element != null) {
				element.validate();
			}
		}
	}

	@Override
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Frame2Exception)) {
			return false;
		}
		final Frame2Exception inst = (Frame2Exception) o;
		if (!(this.requestKey == null ? inst.requestKey == null
				: this.requestKey.equals(inst.requestKey))) {
			return false;
		}
		if (!(this.type == null ? inst.type == null : this.type
				.equals(inst.type))) {
			return false;
		}
		
		return (this.views.equals(inst.views));
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result
				+ (this.requestKey == null ? 0 : this.requestKey.hashCode());
		result = 37 * result + (this.type == null ? 0 : this.type.hashCode());
		result = 37 * result + (this.views == null ? 0 : this.views.hashCode());
		return result;
	}

}