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
					Frame2Plugin.getResourceString("Frame2Model.getValueNull"), Frame2Plugin.getResourceString("Frame2Model.eventDashName"), this); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	public void writeNode(Writer out, String nodeName, String indent)
			throws IOException {
		out.write(indent);
		out.write(Frame2Plugin.getResourceString("Frame2Model.tagStart")); //$NON-NLS-1$
		out.write(nodeName);
		out.write(Frame2Plugin.getResourceString("Frame2Model.tagFinishNoNewLine")); //$NON-NLS-1$
		if (this.value != null) {
			Frame2Config.writeXML(out, this.value, true);
		}
		out.write(Frame2Plugin.getResourceString("Frame2Model.endTagStart")); //$NON-NLS-1$
		out.write(nodeName);
		out.write(Frame2Plugin.getResourceString("Frame2Model.tagFinish")); //$NON-NLS-1$
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
