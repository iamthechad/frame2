package org.megatome.frame2.model;

import java.io.IOException;
import java.io.Writer;

import org.w3c.dom.Node;

public interface IDomainObject {

	public abstract void writeNode(final Writer out, final String nodeName,
			final String indent) throws IOException;

	public abstract void readNode(final Node node);

	public abstract void validate() throws Frame2Config.ValidateException;
}