package org.megatome.frame2.editors;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

public class XMLWhitespaceDetector implements IWhitespaceDetector {

	public boolean isWhitespace(final char c) {
		return (c == ' ' || c == '\t' || c == '\n' || c == '\r');
	}
}
