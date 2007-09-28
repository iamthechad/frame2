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

public class InitParam extends Frame2DomainObject {

	private String paramName;

	private String paramValue;

	public InitParam() {
		this.paramName = ""; //$NON-NLS-1$
		this.paramValue = ""; //$NON-NLS-1$
	}
	
	@Override
	public InitParam copy() {
		return new InitParam(this);
	}

	// Deep copy
	private InitParam(final InitParam source) {
		this.paramName = source.paramName;
		this.paramValue = source.paramValue;
	}

	// This attribute is mandatory
	public void setName(final String value) {
		this.paramName = value;
	}

	public String getName() {
		return this.paramName;
	}

	// This attribute is mandatory
	public void setValue(final String value) {
		this.paramValue = value;
	}

	public String getValue() {
		return this.paramValue;
	}

	public void writeNode(final Writer out, final String nodeName,
			final String indent) throws IOException {
		out.write(indent);
		out.write(Frame2Plugin.getString("Frame2Model.tagStart")); //$NON-NLS-1$
		out.write(nodeName);
		// name is an attribute
		if (this.paramName != null) {
			out.write(Frame2Plugin
					.getString("Frame2Model.nameAttribute")); //$NON-NLS-1$
			out.write(Frame2Plugin
					.getString("Frame2Model.attributeValueStart")); //$NON-NLS-1$
			Frame2Config.writeXML(out, this.paramName, true);
			out.write(Frame2Plugin
					.getString("Frame2Model.attributeValueEnd")); //$NON-NLS-1$
		}
		// value is an attribute
		if (this.paramValue != null) {
			out.write(Frame2Plugin
					.getString("Frame2Model.valueAttribute")); //$NON-NLS-1$
			out.write(Frame2Plugin
					.getString("Frame2Model.attributeValueStart")); //$NON-NLS-1$
			Frame2Config.writeXML(out, this.paramValue, true);
			out.write(Frame2Plugin
					.getString("Frame2Model.attributeValueEnd")); //$NON-NLS-1$
		}
		out.write(Frame2Plugin.getString("Frame2Model.endTagFinish")); //$NON-NLS-1$
	}

	public void readNode(final Node node) {
		if (node.hasAttributes()) {
			final NamedNodeMap attrs = node.getAttributes();
			Attr attr;
			attr = (Attr) attrs.getNamedItem(Frame2Plugin
					.getString("Frame2Model.name")); //$NON-NLS-1$
			if (attr != null) {
				this.paramName = attr.getValue();
			}
			attr = (Attr) attrs.getNamedItem(Frame2Plugin
					.getString("Frame2Model.value")); //$NON-NLS-1$
			if (attr != null) {
				this.paramValue = attr.getValue();
			}
		}
	}

	public void validate() throws Frame2Config.ValidateException {
		// Validating property name
		if (getName() == null) {
			throw new Frame2Config.ValidateException(
					Frame2Plugin.getString("Frame2Model.getNameNull"), Frame2Plugin.getString("Frame2Model.name"), this); //$NON-NLS-1$ //$NON-NLS-2$
		}
		// Validating property value
		if (getValue() == null) {
			throw new Frame2Config.ValidateException(
					Frame2Plugin.getString("Frame2Model.getValueNull"), Frame2Plugin.getString("Frame2Model.value"), this); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	@Override
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof InitParam)) {
			return false;
		}
		final InitParam inst = (InitParam) o;
		if (!(this.paramName == null ? inst.paramName == null : this.paramName
				.equals(inst.paramName))) {
			return false;
		}
		if (!(this.paramValue == null ? inst.paramValue == null
				: this.paramValue.equals(inst.paramValue))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result
				+ (this.paramName == null ? 0 : this.paramName.hashCode());
		result = 37 * result
				+ (this.paramValue == null ? 0 : this.paramValue.hashCode());
		return result;
	}

}