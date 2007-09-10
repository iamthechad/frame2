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
import java.util.HashMap;
import java.util.Map;

import org.megatome.frame2.Frame2Plugin;
import org.w3c.dom.Node;

public abstract class XMLCommentPreserver {

	private Map<Integer, Node> commentMap = new HashMap<Integer, Node>();

	protected void clearComments() {
		this.commentMap.clear();
	}

	protected void recordComment(final Node commentNode, final int commentIndex) {
		this.commentMap.put(Integer.valueOf(commentIndex), commentNode);
	}

	protected int writeCommentsAt(final Writer out, final String indent,
			final int index) throws IOException {
		int commentIndex = index;

		while (writeCommentAt(out, indent, commentIndex++)) {
			// commentIndex ++;
			// Sit and Spin
		}

		return (commentIndex == index) ? (commentIndex + 1) : commentIndex;
	}

	private boolean writeCommentAt(final Writer out, final String indent,
			final int index) throws IOException {
		final Node n = this.commentMap.remove(Integer.valueOf(index));
		if (n == null) {
			return false;
		}

		writeComment(out, indent, n);

		return true;
	}

	protected void writeRemainingComments(final Writer out, final String indent)
			throws IOException {
		for (Node n : this.commentMap.values()) {
			writeComment(out, indent, n);
		}

		this.commentMap.clear();
	}

	private void writeComment(final Writer out, final String indent,
			final Node comment) throws IOException {
		final String commentIndent = indent
				+ Frame2Plugin.getResourceString("Frame2Model.indentTabValue"); //$NON-NLS-1$
		out.write(commentIndent);
		out.write(Frame2Plugin.getResourceString("Frame2Model.commentStart")); //$NON-NLS-1$
		out.write(comment.getNodeValue().trim());
		out.write(Frame2Plugin.getResourceString("Frame2Model.commentEnd")); //$NON-NLS-1$
	}

	protected Map<Integer, Node> getCommentMap() {
		return new HashMap<Integer, Node>(this.commentMap);
	}

	protected void setComments(final Map<Integer, Node> commentMap) {
		this.commentMap = new HashMap<Integer, Node>(commentMap);
	}
}