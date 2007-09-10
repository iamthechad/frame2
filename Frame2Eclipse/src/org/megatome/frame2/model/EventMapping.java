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
import org.megatome.frame2.model.Frame2Config.ValidateException;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EventMapping extends Frame2DomainObject {

	private String eventName;

	private String inputView;

	private String cancelView;

	private String validate;

	private final List<Handler> handlers = new ArrayList<Handler>(); // List<Handler>

	private final List<View> views = new ArrayList<View>(); // List<View>

	private Security security;

	public EventMapping() {
		this.eventName = ""; //$NON-NLS-1$
		clearComments();
	}

	@Override
	public EventMapping copy() {
		return new EventMapping(this);
	}

	// Deep copy
	private EventMapping(final EventMapping source) {
		this.eventName = source.eventName;
		this.inputView = source.inputView;
		this.cancelView = source.cancelView;
		this.validate = source.validate;
		for (Handler handler : source.handlers) {
			this.handlers.add(handler.copy());
		}
		for (View view : source.views) {
			this.views.add(view.copy());
		}
		setComments(source.getCommentMap());
		this.security = source.security.copy();
	}

	// This attribute is mandatory
	public void setEventName(final String value) {
		this.eventName = value;
	}

	public String getEventName() {
		return this.eventName;
	}

	// This attribute is optional
	public void setInputView(final String value) {
		this.inputView = value;
	}

	public String getInputView() {
		return this.inputView;
	}

	// This attribute is optional
	public void setCancelView(final String value) {
		this.cancelView = value;
	}

	public String getCancelView() {
		return this.cancelView;
	}

	// This attribute is optional
	public void setValidate(final String value) {
		this.validate = value;
	}

	public String getValidate() {
		return this.validate;
	}

	// This attribute is an array, possibly empty
	public void setHandler(final Handler[] value) {
		this.handlers.clear();
		if (value == null) {
			return;
		}
		for (int i = 0; i < value.length; ++i) {
			this.handlers.add(value[i]);
		}
	}

	public void setHandler(final int index, final Handler value) {
		this.handlers.set(index, value);
	}

	public Handler[] getHandler() {
		final Handler[] arr = new Handler[this.handlers.size()];
		return this.handlers.toArray(arr);
	}

	public List<Handler> fetchHandlerList() {
		return this.handlers;
	}

	public Handler getHandler(final int index) {
		return this.handlers.get(index);
	}

	public int addHandler(final Handler value) {
		this.handlers.add(value);
		return this.handlers.size() - 1;
	}

	// Search from the end looking for @param value, and then remove it.
	public int removeHandler(final Handler value) {
		final int pos = this.handlers.indexOf(value);
		if (pos >= 0) {
			this.handlers.remove(pos);
		}
		return pos;
	}

	// This attribute is an array, possibly empty
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

	// This attribute is optional
	public void setSecurity(final Security value) {
		this.security = value;
	}

	public Security getSecurity() {
		return this.security;
	}

	/**
	 * @param out
	 * @param nodeName
	 * @param indent
	 * @throws IOException
	 * @see org.megatome.frame2.model.IDomainObject#writeNode(java.io.Writer, java.lang.String, java.lang.String)
	 */
	public void writeNode(final Writer out, final String nodeName,
			final String indent) throws IOException {
		out.write(indent);
		out.write(Frame2Plugin.getResourceString("Frame2Model.tagStart")); //$NON-NLS-1$
		out.write(nodeName);
		// eventName is an attribute
		if (this.eventName != null) {
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.eventNameAttribute")); //$NON-NLS-1$
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.attributeValueStart")); //$NON-NLS-1$
			Frame2Config.writeXML(out, this.eventName, true);
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.attributeValueEnd")); //$NON-NLS-1$
		}
		// inputView is an attribute
		if (this.inputView != null) {
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.inputViewAttribute")); //$NON-NLS-1$
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.attributeValueStart")); //$NON-NLS-1$
			Frame2Config.writeXML(out, this.inputView, true);
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.attributeValueEnd")); //$NON-NLS-1$
		}
		// cancelView is an attribute
		if (this.cancelView != null) {
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.cancelViewAttribute")); //$NON-NLS-1$
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.attributeValueStart")); //$NON-NLS-1$
			Frame2Config.writeXML(out, this.cancelView, true);
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.attributeValueEnd")); //$NON-NLS-1$
		}
		// validate is an attribute
		if (this.validate != null) {
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.validateAttribute")); //$NON-NLS-1$
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.attributeValueStart")); //$NON-NLS-1$
			Frame2Config.writeXML(out, this.validate, true);
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.attributeValueEnd")); //$NON-NLS-1$
		}
		out.write(Frame2Plugin.getResourceString("Frame2Model.tagFinish")); //$NON-NLS-1$
		final String nextIndent = indent
				+ Frame2Plugin.getResourceString("Frame2Model.indentTabValue"); //$NON-NLS-1$
		for (Handler element : this.handlers) {
			if (element != null) {
				element.writeNode(out, Frame2Plugin
						.getResourceString("Frame2Model.handler"), nextIndent); //$NON-NLS-1$
			}
		}
		for (View element : this.views) {
			if (element != null) {
				element.writeNode(out, Frame2Plugin
						.getResourceString("Frame2Model.view"), nextIndent); //$NON-NLS-1$
			}
		}

		writeRemainingComments(out, indent);

		if (this.security != null) {
			this.security.writeNode(out, Frame2Plugin
					.getResourceString("Frame2Model.security"), nextIndent); //$NON-NLS-1$
		}
		out.write(indent);
		out
				.write(Frame2Plugin
						.getResourceString("Frame2Model.endTagStart") + nodeName + Frame2Plugin.getResourceString("Frame2Model.tagFinish")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @param node
	 * @see org.megatome.frame2.model.IDomainObject#readNode(org.w3c.dom.Node)
	 */
	public void readNode(final Node node) {
		if (node.hasAttributes()) {
			final NamedNodeMap attrs = node.getAttributes();
			Attr attr;
			attr = (Attr) attrs.getNamedItem(Frame2Plugin
					.getResourceString("Frame2Model.eventName")); //$NON-NLS-1$
			if (attr != null) {
				this.eventName = attr.getValue();
			}
			attr = (Attr) attrs.getNamedItem(Frame2Plugin
					.getResourceString("Frame2Model.inputView")); //$NON-NLS-1$
			if (attr != null) {
				this.inputView = attr.getValue();
			}
			attr = (Attr) attrs.getNamedItem(Frame2Plugin
					.getResourceString("Frame2Model.cancelView")); //$NON-NLS-1$
			if (attr != null) {
				this.cancelView = attr.getValue();
			}
			attr = (Attr) attrs.getNamedItem(Frame2Plugin
					.getResourceString("Frame2Model.validate")); //$NON-NLS-1$
			if (attr != null) {
				this.validate = attr.getValue();
			}
		}
		final NodeList children = node.getChildNodes();
		for (int i = 0, size = children.getLength(); i < size; ++i) {
			final Node childNode = children.item(i);
			final String childNodeName = (childNode.getLocalName() == null ? childNode
					.getNodeName().intern()
					: childNode.getLocalName().intern());
			if (childNodeName.equals(Frame2Plugin
					.getResourceString("Frame2Model.handler"))) { //$NON-NLS-1$
				final Handler aHandler = new Handler();
				aHandler.readNode(childNode);
				this.handlers.add(aHandler);
			} else if (childNodeName.equals(Frame2Plugin
					.getResourceString("Frame2Model.view"))) { //$NON-NLS-1$
				final View aView = new View();
				aView.readNode(childNode);
				this.views.add(aView);
			} else if (childNodeName.equals(Frame2Plugin
					.getResourceString("Frame2Model.security"))) { //$NON-NLS-1$
				this.security = new Security();
				this.security.readNode(childNode);
			} else {
				// Found extra unrecognized childNode
				if (childNodeName.equals(Frame2Plugin
						.getResourceString("Frame2Model.comment"))) { //$NON-NLS-1$
					recordComment(childNode, i);
				}
			}
		}
	}

	/**
	 * @throws ValidateException
	 * @see org.megatome.frame2.model.IDomainObject#validate()
	 */
	public void validate() throws Frame2Config.ValidateException {
		// Validating property eventName
		if (getEventName() == null) {
			throw new Frame2Config.ValidateException(
					Frame2Plugin.getResourceString("Frame2Model.eventNameNull"), Frame2Plugin.getResourceString("Frame2Model.eventName"), this); //$NON-NLS-1$ //$NON-NLS-2$
		}
		// Validating property handler
		for (Handler element : this.handlers) {
			if (element != null) {
				element.validate();
			}
		}
		// Validating property view
		for (View element : this.views) {
			if (element != null) {
				element.validate();
			}
		}
		// Validating property security
		if (getSecurity() != null) {
			getSecurity().validate();
		}
	}

	@Override
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof EventMapping)) {
			return false;
		}
		final EventMapping inst = (EventMapping) o;
		if (!(this.eventName == null ? inst.eventName == null : this.eventName
				.equals(inst.eventName))) {
			return false;
		}
		if (!(this.inputView == null ? inst.inputView == null : this.inputView
				.equals(inst.inputView))) {
			return false;
		}
		if (!(this.cancelView == null ? inst.cancelView == null
				: this.cancelView.equals(inst.cancelView))) {
			return false;
		}
		if (!(this.validate == null ? inst.validate == null : this.validate
				.equals(inst.validate))) {
			return false;
		}
		if (!this.handlers.equals(inst.handlers)) {
			return false;
		}
		if (!this.views.equals(inst.views)) {
			return false;
		}
		if (!(this.security == null ? inst.security == null : this.security
				.equals(inst.security))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result
				+ (this.eventName == null ? 0 : this.eventName.hashCode());
		result = 37 * result
				+ (this.inputView == null ? 0 : this.inputView.hashCode());
		result = 37 * result
				+ (this.cancelView == null ? 0 : this.cancelView.hashCode());
		result = 37 * result
				+ (this.validate == null ? 0 : this.validate.hashCode());
		result = 37 * result
				+ (this.handlers == null ? 0 : this.handlers.hashCode());
		result = 37 * result + (this.views == null ? 0 : this.views.hashCode());
		result = 37 * result
				+ (this.security == null ? 0 : this.security.hashCode());
		return result;
	}

}