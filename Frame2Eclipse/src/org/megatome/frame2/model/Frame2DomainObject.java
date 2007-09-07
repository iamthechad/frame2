package org.megatome.frame2.model;

import java.util.LinkedList;
import java.util.List;


public abstract class Frame2DomainObject extends XMLCommentPreserver implements
		IDomainObject {

	public abstract Frame2DomainObject copy();
	/**
	 * @param recursive
	 * @return
	 * @see org.megatome.frame2.model.IDomainObject#childBeans(boolean)
	 */
	public Object[] childBeans(final boolean recursive) {
		final List<Object> children = new LinkedList<Object>();
		childBeans(recursive, children);
		final Object[] result = new Object[children.size()];
		return children.toArray(result);
	}

	public int size() {
		return 0;
	}
}
