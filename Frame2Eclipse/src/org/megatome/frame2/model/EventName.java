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
import org.megatome.frame2.model.Frame2Config.ValidateException;
import org.w3c.dom.Node;

public class EventName extends Frame2DomainObject {

	private String value;

	public EventName() {
		this.value = ""; //$NON-NLS-1$
	}

	@Override
	public EventName copy() {
		return new EventName(this);
	}

	private EventName(final EventName other) {
		this.value = other.value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public void readNode(Node node) {
		Node valueNode = node.getFirstChild();
		if (valueNode != null) {
			this.value = valueNode.getNodeValue();
		} else {
			this.value = null;
		}
	}

	public void validate() throws ValidateException {
		// Validating property name
		if (getValue() == null) {
			throw new Frame2Config.ValidateException(
					Frame2Plugin.getString("Frame2Model.getValueNull"), Frame2Plugin.getString("Frame2Model.eventDashName"), this); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	public void writeNode(Writer out, String nodeName, String indent)
			throws IOException {
		out.write(indent);
		out.write(Frame2Plugin.getString("Frame2Model.tagStart")); //$NON-NLS-1$
		out.write(nodeName);
		out.write(Frame2Plugin.getString("Frame2Model.tagFinishNoNewLine")); //$NON-NLS-1$
		if (this.value != null) {
			Frame2Config.writeXML(out, this.value, true);
		}
		out.write(Frame2Plugin.getString("Frame2Model.endTagStart")); //$NON-NLS-1$
		out.write(nodeName);
		out.write(Frame2Plugin.getString("Frame2Model.tagFinish")); //$NON-NLS-1$
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof EventName)) {
			return false;
		}
		if (o == this) {
			return true;
		}
		
		EventName en = (EventName)o;
		return this.value.equals(en.value);
	}

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}
}
