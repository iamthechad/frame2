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

import org.megatome.frame2.Frame2Plugin;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class Frame2Event extends Frame2DomainObject {

	private String eventName;

	private String resolveAs;

	private String type;

	public Frame2Event() {
		this.eventName = ""; //$NON-NLS-1$
	}
	
	@Override
	public Frame2Event copy() {
		return new Frame2Event(this);
	}

	// Deep copy
	private Frame2Event(final Frame2Event source) {
		this.eventName = source.eventName;
		this.resolveAs = source.resolveAs;
		this.type = source.type;
	}

	// This attribute is mandatory
	public void setName(final String value) {
		this.eventName = value;
	}

	public String getName() {
		return this.eventName;
	}

	// This attribute is optional
	public void setResolveAs(final String value) {
		this.resolveAs = value;
	}

	public String getResolveAs() {
		return this.resolveAs;
	}

	// This attribute is optional
	public void setType(final String value) {
		this.type = value;
	}

	public String getType() {
		return this.type;
	}

	public void writeNode(final Writer out, final String nodeName,
			final String indent) throws IOException {
		out.write(indent);
		out.write(Frame2Plugin.getResourceString("Frame2Model.tagStart")); //$NON-NLS-1$
		out.write(nodeName);
		// name is an attribute
		if (this.eventName != null) {
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.nameAttribute")); //$NON-NLS-1$
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.attrbiuteValueStart")); //$NON-NLS-1$
			Frame2Config.writeXML(out, this.eventName, true);
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.attributeValueEnd")); //$NON-NLS-1$
		}
		// resolveAs is an attribute
		if (this.resolveAs != null) {
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.resolveAsAttribute")); //$NON-NLS-1$
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.attributeValueStart")); //$NON-NLS-1$
			Frame2Config.writeXML(out, this.resolveAs, true);
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
		out.write(Frame2Plugin.getResourceString("Frame2Model.endTagFinish")); //$NON-NLS-1$
	}

	public void readNode(final Node node) {
		if (node.hasAttributes()) {
			final NamedNodeMap attrs = node.getAttributes();
			Attr attr;
			attr = (Attr) attrs.getNamedItem(Frame2Plugin
					.getResourceString("Frame2Model.name")); //$NON-NLS-1$
			if (attr != null) {
				this.eventName = attr.getValue();
			}
			attr = (Attr) attrs.getNamedItem(Frame2Plugin
					.getResourceString("Frame2Model.resolveAs")); //$NON-NLS-1$
			if (attr != null) {
				this.resolveAs = attr.getValue();
			}
			attr = (Attr) attrs.getNamedItem(Frame2Plugin
					.getResourceString("Frame2Model.type")); //$NON-NLS-1$
			if (attr != null) {
				this.type = attr.getValue();
			}
		}
	}

	public void validate() throws Frame2Config.ValidateException {
		// Validating property name
		if (getName() == null) {
			throw new Frame2Config.ValidateException(
					Frame2Plugin.getResourceString("Frame2Model.getNameNull"), Frame2Plugin.getResourceString("Frame2Model.name"), this); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	@Override
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Frame2Event)) {
			return false;
		}
		final Frame2Event inst = (Frame2Event) o;
		if (!(this.eventName == null ? inst.eventName == null : this.eventName
				.equals(inst.eventName))) {
			return false;
		}
		if (!(this.resolveAs == null ? inst.resolveAs == null : this.resolveAs
				.equals(inst.resolveAs))) {
			return false;
		}
		if (!(this.type == null ? inst.type == null : this.type
				.equals(inst.type))) {
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
				+ (this.resolveAs == null ? 0 : this.resolveAs.hashCode());
		result = 37 * result + (this.type == null ? 0 : this.type.hashCode());
		return result;
	}

}