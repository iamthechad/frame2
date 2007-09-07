package org.megatome.frame2.editors;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextViewer;

public class XMLDoubleClickStrategy implements ITextDoubleClickStrategy {
	protected ITextViewer fText;

	public void doubleClicked(final ITextViewer part) {
		final int pos = part.getSelectedRange().x;

		if (pos < 0) {
			return;
		}

		this.fText = part;

		if (!selectComment(pos)) {
			selectWord(pos);
		}
	}

	protected boolean selectComment(final int caretPos) {
		final IDocument doc = this.fText.getDocument();
		int startPos, endPos;

		try {
			int pos = caretPos;
			char c = ' ';

			while (pos >= 0) {
				c = doc.getChar(pos);
				if (c == '\\') {
					pos -= 2;
					continue;
				}
				if (c == Character.LINE_SEPARATOR || c == '\"') {
					break;
				}
				--pos;
			}

			if (c != '\"') {
				return false;
			}

			startPos = pos;

			pos = caretPos;
			final int length = doc.getLength();
			c = ' ';

			while (pos < length) {
				c = doc.getChar(pos);
				if (c == Character.LINE_SEPARATOR || c == '\"') {
					break;
				}
				++pos;
			}
			if (c != '\"') {
				return false;
			}

			endPos = pos;

			final int offset = startPos + 1;
			final int len = endPos - offset;
			this.fText.setSelectedRange(offset, len);
			return true;
		} catch (final BadLocationException x) {
			// Ignore
		}

		return false;
	}

	protected boolean selectWord(final int caretPos) {

		final IDocument doc = this.fText.getDocument();
		int startPos, endPos;

		try {

			int pos = caretPos;
			char c;

			while (pos >= 0) {
				c = doc.getChar(pos);
				if (!Character.isJavaIdentifierPart(c)) {
					break;
				}
				--pos;
			}

			startPos = pos;

			pos = caretPos;
			final int length = doc.getLength();

			while (pos < length) {
				c = doc.getChar(pos);
				if (!Character.isJavaIdentifierPart(c)) {
					break;
				}
				++pos;
			}

			endPos = pos;
			selectRange(startPos, endPos);
			return true;

		} catch (final BadLocationException x) {
			// Ignore
		}

		return false;
	}

	private void selectRange(final int startPos, final int stopPos) {
		final int offset = startPos + 1;
		final int length = stopPos - offset;
		this.fText.setSelectedRange(offset, length);
	}
}