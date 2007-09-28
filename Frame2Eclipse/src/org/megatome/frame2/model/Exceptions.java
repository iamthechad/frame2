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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Exceptions extends Frame2DomainObject {

	private final List<Frame2Exception> exceptions = new ArrayList<Frame2Exception>(); // List<Frame2Exception>

	public Exceptions() {
		clearComments();
	}

	@Override
	public Exceptions copy() {
		return new Exceptions(this);
	}
	
	// Deep copy
	private Exceptions(final Exceptions source) {
		for (Frame2Exception exception : source.exceptions) {
			this.exceptions.add(exception.copy());
		}

		setComments(source.getCommentMap());
	}

	// This attribute is an array, possibly empty
	public void setFrame2Exception(final Frame2Exception[] value) {
		this.exceptions.clear();
		if (value == null) {
			return;
		}
		for (int i = 0; i < value.length; ++i) {
			this.exceptions.add(value[i]);
		}
	}

	public void setFrame2Exception(final int index, final Frame2Exception value) {
		this.exceptions.set(index, value);
	}

	public Frame2Exception[] getFrame2Exception() {
		final Frame2Exception[] arr = new Frame2Exception[this.exceptions
				.size()];
		return this.exceptions.toArray(arr);
	}

	public List<Frame2Exception> fetchFrame2ExceptionList() {
		return this.exceptions;
	}

	public Frame2Exception getFrame2Exception(final int index) {
		return this.exceptions.get(index);
	}

	public int addFrame2Exception(final Frame2Exception value) {
		this.exceptions.add(value);
		return this.exceptions.size() - 1;
	}

	// Search from the end looking for @param value, and then remove it.
	public int removeFrame2Exception(final Frame2Exception value) {
		final int pos = this.exceptions.indexOf(value);
		if (pos >= 0) {
			this.exceptions.remove(pos);
		}
		return pos;
	}

	public void writeNode(final Writer out, final String nodeName,
			final String indent) throws IOException {
		out.write(indent);
		out.write(Frame2Plugin.getString("Frame2Model.tagStart")); //$NON-NLS-1$
		out.write(nodeName);
		out.write(Frame2Plugin.getString("Frame2Model.tagFinish")); //$NON-NLS-1$
		final String nextIndent = indent
				+ Frame2Plugin.getString("Frame2Model.indentTabValue"); //$NON-NLS-1$
		int index = 0;
		for (Frame2Exception element : this.exceptions) {
			index = writeCommentsAt(out, indent, index);
			if (element != null) {
				element
						.writeNode(
								out,
								Frame2Plugin
										.getString("Frame2Model.exception"), nextIndent); //$NON-NLS-1$
			}
		}

		writeRemainingComments(out, indent);
		out.write(indent);
		out
				.write(Frame2Plugin
						.getString("Frame2Model.endTagStart") + nodeName + Frame2Plugin.getString("Frame2Model.tagFinish")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void readNode(final Node node) {
		final NodeList children = node.getChildNodes();
		int elementCount = 0;
		for (int i = 0, size = children.getLength(); i < size; ++i) {
			final Node childNode = children.item(i);
			final String childNodeName = (childNode.getLocalName() == null ? childNode
					.getNodeName().intern()
					: childNode.getLocalName().intern());
			if (childNodeName.equals(Frame2Plugin
					.getString("Frame2Model.exception"))) { //$NON-NLS-1$
				final Frame2Exception aFrame2Exception = new Frame2Exception();
				aFrame2Exception.readNode(childNode);
				this.exceptions.add(aFrame2Exception);
				elementCount++;
			} else {
				// Found extra unrecognized childNode
				if (childNodeName.equals(Frame2Plugin
						.getString("Frame2Model.comment"))) { //$NON-NLS-1$
					recordComment(childNode, elementCount++);
				}
			}
		}
	}

	public void validate() throws Frame2Config.ValidateException {
		// Validating property Frame2Exception
		for (Frame2Exception element : this.exceptions) {
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
		if (!(o instanceof Exceptions)) {
			return false;
		}
		final Exceptions inst = (Exceptions) o;
		return (this.exceptions.equals(inst.exceptions));
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result
				+ (this.exceptions == null ? 0 : this.exceptions.hashCode());
		return result;
	}

}