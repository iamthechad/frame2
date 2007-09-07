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
import java.util.List;

import org.megatome.frame2.Frame2Plugin;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class Role extends Frame2DomainObject {

	private String roleName;

	public Role() {
		this.roleName = ""; //$NON-NLS-1$
	}
	
	@Override
	public Role copy() {
		return new Role(this);
	}

	// Deep copy
	private Role(final Role source) {
		this.roleName = source.roleName;
	}

	// This attribute is mandatory
	public void setName(final String value) {
		this.roleName = value;
	}

	public String getName() {
		return this.roleName;
	}

	public void writeNode(final Writer out, final String nodeName,
			final String indent) throws IOException {
		out.write(indent);
		out.write(Frame2Plugin.getResourceString("Frame2Model.tagStart")); //$NON-NLS-1$
		out.write(nodeName);
		// name is an attribute
		if (this.roleName != null) {
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.nameAttribute")); //$NON-NLS-1$
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.attributeValueStart")); //$NON-NLS-1$
			Frame2Config.writeXML(out, this.roleName, true);
			out.write(Frame2Plugin
					.getResourceString("Frame2Model.attributeValueEnd")); //$NON-NLS-1$
		}
		out.write(Frame2Plugin.getResourceString("Frame2Model.endTagFinish")); //$NON-NLS-1$
		// String nextIndent = indent + " ";
		// out.write(indent);
		// out.write("</"+nodeName+">\n");
	}

	public void readNode(final Node node) {
		if (node.hasAttributes()) {
			final NamedNodeMap attrs = node.getAttributes();
			Attr attr;
			attr = (Attr) attrs.getNamedItem(Frame2Plugin
					.getResourceString("Frame2Model.name")); //$NON-NLS-1$
			if (attr != null) {
				this.roleName = attr.getValue();
			}
		}
		/*
		 * NodeList children = node.getChildNodes(); for (int i = 0, size =
		 * children.getLength(); i < size; ++i) { Node childNode =
		 * children.item(i); String childNodeName = (childNode.getLocalName() ==
		 * null ? childNode .getNodeName().intern() :
		 * childNode.getLocalName().intern()); String childNodeValue = "";
		 * //$NON-NLS-1$ if (childNode.getFirstChild() != null) { childNodeValue =
		 * childNode.getFirstChild().getNodeValue(); } }
		 */
	}

	public void validate() throws Frame2Config.ValidateException {
		// boolean restrictionFailure = false;
		// Validating property name
		if (getName() == null) {
			throw new Frame2Config.ValidateException(
					Frame2Plugin.getResourceString("Frame2Model.getNameNull"), Frame2Plugin.getResourceString("Frame2Model.name"), this); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	public void changePropertyByName(final String name, final Object value) {
		if (name == null) {
			return;
		}
		final String intName = name.intern();
		if (intName.equals(Frame2Plugin.getResourceString("Frame2Model.name"))) { //$NON-NLS-1$
			setName((String) value);
		} else {
			throw new IllegalArgumentException(
					intName
							+ Frame2Plugin
									.getResourceString("Frame2Model.invalidRoleProperty")); //$NON-NLS-1$
		}
	}

	public Object fetchPropertyByName(final String name) {
		if (name.equals(Frame2Plugin.getResourceString("Frame2Model.name"))) { //$NON-NLS-1$
			return getName();
		}
		throw new IllegalArgumentException(name
				+ Frame2Plugin
						.getResourceString("Frame2Model.invalidRoleProperty")); //$NON-NLS-1$
	}

	// Put all child beans into the beans list.
	public void childBeans(@SuppressWarnings("unused")
	final boolean recursive, @SuppressWarnings("unused")
	final List<Object> beans) {
		// NOOP
	}

	@Override
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Role)) {
			return false;
		}
		final Role inst = (Role) o;
		if (!(this.roleName == null ? inst.roleName == null : this.roleName
				.equals(inst.roleName))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result
				+ (this.roleName == null ? 0 : this.roleName.hashCode());
		return result;
	}

}