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
import java.util.Iterator;
import java.util.List;

import org.megatome.frame2.Frame2Plugin;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Security extends Frame2DomainObject {

	private final List<Role> roles = new ArrayList<Role>(); // List<Role>

	public Security() {
		clearComments();
	}
	
	@Override
	public Security copy() {
		return new Security(this);
	}

	// Deep copy
	private Security(final Security source) {
		for (Role role : source.roles) {
			this.roles.add(role.copy());
		}

		setComments(source.getCommentMap());
	}

	// This attribute is an array containing at least one element
	public void setRole(final Role[] value) {
		this.roles.clear();
		if (value == null) {
			return;
		}
		for (int i = 0; i < value.length; ++i) {
			this.roles.add(value[i]);
		}
	}

	public void setRole(final int index, final Role value) {
		this.roles.set(index, value);
	}

	public Role[] getRole() {
		final Role[] arr = new Role[this.roles.size()];
		return this.roles.toArray(arr);
	}

	public List<Role> fetchRoleList() {
		return this.roles;
	}

	public Role getRole(final int index) {
		return this.roles.get(index);
	}

	// Return the number of role
	@Override
	public int size() {
		return this.roles.size();
	}

	public int addRole(final Role value) {
		this.roles.add(value);
		return this.roles.size() - 1;
	}

	// Search from the end looking for @param value, and then remove it.
	public int removeRole(final Role value) {
		final int pos = this.roles.indexOf(value);
		if (pos >= 0) {
			this.roles.remove(pos);
		}
		return pos;
	}

	public void writeNode(final Writer out, final String nodeName,
			final String indent) throws IOException {
		out.write(indent);
		out.write(Frame2Plugin.getResourceString("Frame2Model.tagStart")); //$NON-NLS-1$
		out.write(nodeName);
		out.write(Frame2Plugin.getResourceString("Frame2Model.tagFinish")); //$NON-NLS-1$
		final String nextIndent = indent
				+ Frame2Plugin.getResourceString("Frame2Model.indentTabValue"); //$NON-NLS-1$
		int index = 0;
		for (final Iterator<Role> it = this.roles.iterator(); it.hasNext();) {

			index = writeCommentsAt(out, indent, index);

			final Role element = it.next();
			if (element != null) {
				element.writeNode(out, Frame2Plugin
						.getResourceString("Frame2Model.role"), nextIndent); //$NON-NLS-1$
			}
		}
		writeRemainingComments(out, indent);
		out.write(indent);
		out
				.write(Frame2Plugin
						.getResourceString("Frame2Model.endTagStart") + nodeName + Frame2Plugin.getResourceString("Frame2Model.tagFinish")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void readNode(final Node node) {
		final NodeList children = node.getChildNodes();
		int elementCount = 0;
		for (int i = 0, size = children.getLength(); i < size; ++i) {
			final Node childNode = children.item(i);
			final String childNodeName = (childNode.getLocalName() == null ? childNode
					.getNodeName().intern()
					: childNode.getLocalName().intern());
			/*
			 * String childNodeValue = ""; //$NON-NLS-1$ if
			 * (childNode.getFirstChild() != null) { childNodeValue =
			 * childNode.getFirstChild().getNodeValue(); }
			 */
			if (childNodeName.equals(Frame2Plugin
					.getResourceString("Frame2Model.role"))) { //$NON-NLS-1$
				final Role aRole = new Role();
				aRole.readNode(childNode);
				this.roles.add(aRole);
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
		// boolean restrictionFailure = false;
		// Validating property role
		if (size() == 0) {
			throw new Frame2Config.ValidateException(
					Frame2Plugin.getResourceString("Frame2Model.sizeRoleZero"), Frame2Plugin.getResourceString("Frame2Model.role"), this); //$NON-NLS-1$ //$NON-NLS-2$
		}
		for (int _index = 0; _index < size(); ++_index) {
			final Role element = getRole(_index);
			if (element != null) {
				element.validate();
			}
		}
	}

	public void changePropertyByName(final String name, final Object value) {
		if (name == null) {
			return;
		}
		final String intName = name.intern();
		if (intName.equals(Frame2Plugin.getResourceString("Frame2Model.role"))) { //$NON-NLS-1$
			addRole((Role) value);
		} else if (intName.equals(Frame2Plugin
				.getResourceString("Frame2Model.roleArray"))) { //$NON-NLS-1$
			setRole((Role[]) value);
		} else {
			throw new IllegalArgumentException(
					intName
							+ Frame2Plugin
									.getResourceString("Frame2Model.invalidSecurityProperty")); //$NON-NLS-1$
		}
	}

	public Object fetchPropertyByName(final String name) {
		if (name
				.equals(Frame2Plugin.getResourceString("Frame2Model.roleArray"))) { //$NON-NLS-1$
			return getRole();
		}
		throw new IllegalArgumentException(
				name
						+ Frame2Plugin
								.getResourceString("Frame2Model.invalidSecurityProperty")); //$NON-NLS-1$
	}

	// Put all child beans into the beans list.
	public void childBeans(final boolean recursive, final List<Object> beans) {
		for (final Iterator<Role> it = this.roles.iterator(); it.hasNext();) {
			final Role element = it.next();
			if (element != null) {
				if (recursive) {
					element.childBeans(true, beans);
				}
				beans.add(element);
			}
		}
	}

	@Override
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Security)) {
			return false;
		}
		final Security inst = (Security) o;
		if (size() != inst.size()) {
			return false;
		}
		// Compare every element.
		for (Iterator<Role> it = this.roles.iterator(), it2 = inst.roles
				.iterator(); it.hasNext() && it2.hasNext();) {
			final Role element = it.next();
			final Role element2 = it2.next();
			if (!(element == null ? element2 == null : element.equals(element2))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.roles == null ? 0 : this.roles.hashCode());
		return result;
	}

}