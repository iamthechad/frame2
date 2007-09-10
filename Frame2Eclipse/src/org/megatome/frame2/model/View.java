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

import org.megatome.frame2.Frame2Plugin;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class View extends Frame2DomainObject {

	private String type;

	private String forwardName;

	public View() {
		this.type = ""; //$NON-NLS-1$
		this.forwardName = ""; //$NON-NLS-1$
	}
	
	@Override
	public View copy() {
		return new View(this);
	}

	// Deep copy
	private View(final View source) {
		this.type = source.type;
		this.forwardName = source.forwardName;
	}

	// This attribute is mandatory
	public void setType(final String value) {
		this.type = value;
	}

	public String getType() {
		return this.type;
	}

	// This attribute is mandatory
	public void setForwardName(final String value) {
		this.forwardName = value;
	}

	public String getForwardName() {
		return this.forwardName;
	}

	public void writeNode(final Writer out, final String nodeName,
			final String indent) throws IOException {
		out.write(indent);
		out.write(Frame2Plugin.getResourceString("Frame2Model.tagStart")); //$NON-NLS-1$
		out.write(nodeName);
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
		// forwardName is an attribute
		if (this.forwardName != null) {
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.forwardNameAttribute")); //$NON-NLS-1$
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.attributeValueStart")); //$NON-NLS-1$
			Frame2Config.writeXML(out, this.forwardName, true);
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
					.getResourceString("Frame2Model.type")); //$NON-NLS-1$
			if (attr != null) {
				this.type = attr.getValue();
			}
			attr = (Attr) attrs.getNamedItem(Frame2Plugin
					.getResourceString("Frame2Model.forwardName")); //$NON-NLS-1$
			if (attr != null) {
				this.forwardName = attr.getValue();
			}
		}
	}

	public void validate() throws Frame2Config.ValidateException {
		if (getType() == null) {
			throw new Frame2Config.ValidateException(
					Frame2Plugin.getResourceString("Frame2Model.getTypeNull"), Frame2Plugin.getResourceString("Frame2Model.type"), this); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (getForwardName() == null) {
			throw new Frame2Config.ValidateException(
					Frame2Plugin
							.getResourceString("Frame2Model.getForwardNameNull"), Frame2Plugin.getResourceString("Frame2Model.forwardName"), this); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	@Override
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof View)) {
			return false;
		}
		final View inst = (View) o;
		if (!(this.type == null ? inst.type == null : this.type
				.equals(inst.type))) {
			return false;
		}
		if (!(this.forwardName == null ? inst.forwardName == null
				: this.forwardName.equals(inst.forwardName))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.type == null ? 0 : this.type.hashCode());
		result = 37 * result
				+ (this.forwardName == null ? 0 : this.forwardName.hashCode());
		return result;
	}

}