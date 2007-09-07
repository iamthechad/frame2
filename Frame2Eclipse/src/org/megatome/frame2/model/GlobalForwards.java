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

public class GlobalForwards extends Frame2DomainObject {

	private final List<Forward> forwards = new ArrayList<Forward>(); // List<Forward>

	public GlobalForwards() {
		clearComments();
	}
	
	@Override
	public GlobalForwards copy() {
		return new GlobalForwards(this);
	}

	// Deep copy
	private GlobalForwards(final GlobalForwards source) {
		for (Forward forward : source.forwards) {
			this.forwards.add(forward.copy());
		}

		setComments(source.getCommentMap());
	}

	// This attribute is an array, possibly empty
	public void setForward(final Forward[] value) {
		this.forwards.clear();
		if (value == null) {
			return;
		}
		for (int i = 0; i < value.length; ++i) {
			this.forwards.add(value[i]);
		}
	}

	public void setForward(final int index, final Forward value) {
		this.forwards.set(index, value);
	}

	public Forward[] getForward() {
		final Forward[] arr = new Forward[this.forwards.size()];
		return this.forwards.toArray(arr);
	}

	public List<Forward> fetchForwardList() {
		return this.forwards;
	}

	public Forward getForward(final int index) {
		return this.forwards.get(index);
	}

	// Return the number of forward
	@Override
	public int size() {
		return this.forwards.size();
	}

	public int addForward(final Forward value) {
		this.forwards.add(value);
		return this.forwards.size() - 1;
	}

	// Search from the end looking for @param value, and then remove it.
	public int removeForward(final Forward value) {
		final int pos = this.forwards.indexOf(value);
		if (pos >= 0) {
			this.forwards.remove(pos);
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
		for (final Iterator<Forward> it = this.forwards.iterator(); it
				.hasNext();) {

			index = writeCommentsAt(out, indent, index);

			final Forward element = it.next();
			if (element != null) {
				element.writeNode(out, Frame2Plugin
						.getResourceString("Frame2Model.forward"), nextIndent); //$NON-NLS-1$
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
					.getResourceString("Frame2Model.forward"))) { //$NON-NLS-1$
				final Forward aForward = new Forward();
				aForward.readNode(childNode);
				this.forwards.add(aForward);
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
		// Validating property forward
		for (int _index = 0; _index < size(); ++_index) {
			final Forward element = getForward(_index);
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
		if (intName.equals(Frame2Plugin
				.getResourceString("Frame2Model.forward"))) { //$NON-NLS-1$
			addForward((Forward) value);
		} else if (intName.equals(Frame2Plugin
				.getResourceString("Frame2Model.forwardArray"))) { //$NON-NLS-1$
			setForward((Forward[]) value);
		} else {
			throw new IllegalArgumentException(
					intName
							+ Frame2Plugin
									.getResourceString("Frame2Model.invalidGlobalForwardsProperty")); //$NON-NLS-1$
		}
	}

	public Object fetchPropertyByName(final String name) {
		if (name.equals(Frame2Plugin
				.getResourceString("Frame2Model.forwardArray"))) { //$NON-NLS-1$
			return getForward();
		}
		throw new IllegalArgumentException(
				name
						+ Frame2Plugin
								.getResourceString("Frame2Model.invalidGlobalForwardsProperty")); //$NON-NLS-1$
	}

	// Put all child beans into the beans list.
	public void childBeans(final boolean recursive, final List<Object> beans) {
		for (final Iterator<Forward> it = this.forwards.iterator(); it
				.hasNext();) {
			final Forward element = it.next();
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
		if (!(o instanceof GlobalForwards)) {
			return false;
		}
		final GlobalForwards inst = (GlobalForwards) o;
		if (size() != inst.size()) {
			return false;
		}
		// Compare every element.
		for (Iterator<Forward> it = this.forwards.iterator(), it2 = inst.forwards
				.iterator(); it.hasNext() && it2.hasNext();) {
			final Forward element = it.next();
			final Forward element2 = it2.next();
			if (!(element == null ? element2 == null : element.equals(element2))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result
				+ (this.forwards == null ? 0 : this.forwards.hashCode());
		return result;
	}

}