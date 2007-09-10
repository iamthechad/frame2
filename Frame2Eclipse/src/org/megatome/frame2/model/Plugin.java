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
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Plugin extends Frame2DomainObject {

	private String pluginName;

	private String type;

	private final List<InitParam> initParams = new ArrayList<InitParam>(); // List<InitParam>

	public Plugin() {
		this.pluginName = ""; //$NON-NLS-1$
		this.type = ""; //$NON-NLS-1$
		clearComments();
	}
	
	@Override
	public Plugin copy() {
		return new Plugin(this);
	}

	// Deep copy
	private Plugin(final Plugin source) {
		this.pluginName = source.pluginName;
		this.type = source.type;
		for (InitParam param : source.initParams) {
			this.initParams.add(param.copy());
		}

		setComments(source.getCommentMap());
	}

	// This attribute is mandatory
	public void setName(final String value) {
		this.pluginName = value;
	}

	public String getName() {
		return this.pluginName;
	}

	// This attribute is mandatory
	public void setType(final String value) {
		this.type = value;
	}

	public String getType() {
		return this.type;
	}

	// This attribute is an array, possibly empty
	public void setInitParam(final InitParam[] value) {
		this.initParams.clear();
		if (value == null) {
			return;
		}
		for (int i = 0; i < value.length; ++i) {
			this.initParams.add(value[i]);
		}
	}

	public void setInitParam(final int index, final InitParam value) {
		this.initParams.set(index, value);
	}

	public InitParam[] getInitParam() {
		final InitParam[] arr = new InitParam[this.initParams.size()];
		return this.initParams.toArray(arr);
	}

	public List<InitParam> fetchInitParamList() {
		return this.initParams;
	}

	public InitParam getInitParam(final int index) {
		return this.initParams.get(index);
	}

	public int addInitParam(final InitParam value) {
		this.initParams.add(value);
		return this.initParams.size() - 1;
	}

	// Search from the end looking for @param value, and then remove it.
	public int removeInitParam(final InitParam value) {
		final int pos = this.initParams.indexOf(value);
		if (pos >= 0) {
			this.initParams.remove(pos);
		}
		return pos;
	}

	public void writeNode(final Writer out, final String nodeName,
			final String indent) throws IOException {
		out.write(indent);
		out.write(Frame2Plugin.getResourceString("Frame2Model.tagStart")); //$NON-NLS-1$
		out.write(nodeName);
		// name is an attribute
		if (this.pluginName != null) {
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.nameAttribute")); //$NON-NLS-1$
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.attributeValueStart")); //$NON-NLS-1$
			Frame2Config.writeXML(out, this.pluginName, true);
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
		for (InitParam element : this.initParams) {
			index = writeCommentsAt(out, indent, index);
			if (element != null) {
				element
						.writeNode(
								out,
								Frame2Plugin
										.getResourceString("Frame2Model.init-param"), nextIndent); //$NON-NLS-1$
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
					.getResourceString("Frame2Model.name")); //$NON-NLS-1$
			if (attr != null) {
				this.pluginName = attr.getValue();
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
					.getResourceString("Frame2Model.init-param"))) { //$NON-NLS-1$
				final InitParam aInitParam = new InitParam();
				aInitParam.readNode(childNode);
				this.initParams.add(aInitParam);
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
		// Validating property name
		if (getName() == null) {
			throw new Frame2Config.ValidateException(
					Frame2Plugin.getResourceString("Frame2Model.getNameNull"), Frame2Plugin.getResourceString("Frame2Model.name"), this); //$NON-NLS-1$ //$NON-NLS-2$
		}
		// Validating property type
		if (getType() == null) {
			throw new Frame2Config.ValidateException(
					Frame2Plugin.getResourceString("Frame2Model.getTypeNull"), Frame2Plugin.getResourceString("Frame2Model.type"), this); //$NON-NLS-1$ //$NON-NLS-2$
		}
		// Validating property initParam
		for (InitParam element : this.initParams) {
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
		if (!(o instanceof Plugin)) {
			return false;
		}
		final Plugin inst = (Plugin) o;
		if (!(this.pluginName == null ? inst.pluginName == null
				: this.pluginName.equals(inst.pluginName))) {
			return false;
		}
		if (!(this.type == null ? inst.type == null : this.type
				.equals(inst.type))) {
			return false;
		}
		
		return this.initParams.equals(inst.initParams);
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result
				+ (this.pluginName == null ? 0 : this.pluginName.hashCode());
		result = 37 * result + (this.type == null ? 0 : this.type.hashCode());
		result = 37 * result
				+ (this.initParams == null ? 0 : this.initParams.hashCode());
		return result;
	}

}