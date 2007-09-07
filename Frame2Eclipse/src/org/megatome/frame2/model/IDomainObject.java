package org.megatome.frame2.model;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.w3c.dom.Node;

public interface IDomainObject {

	public abstract int size();

	public abstract void writeNode(final Writer out, final String nodeName,
			final String indent) throws IOException;

	public abstract void readNode(final Node node);

	public abstract void validate() throws Frame2Config.ValidateException;

	public abstract void changePropertyByName(final String name,
			final Object value);

	public abstract Object fetchPropertyByName(final String name);

	// Return an array of all of the properties that are beans and are set.
	public abstract Object[] childBeans(final boolean recursive);

	// Put all child beans into the beans list.
	public abstract void childBeans(final boolean recursive,
			final List<Object> beans);

}