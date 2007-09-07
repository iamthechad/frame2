package org.megatome.frame2.editors;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITextViewer;

public class WordPartDetector {
	String wordPart = ""; //$NON-NLS-1$
	int docOffset;

	public WordPartDetector(final ITextViewer viewer, final int documentOffset) {
		this.docOffset = documentOffset - 1;
		try {
			while (((this.docOffset) >= viewer.getTopIndexStartOffset())
					&& Character.isLetterOrDigit(viewer.getDocument().getChar(
							this.docOffset))) {
				this.docOffset--;
			}
			// we've been one step too far : increase the offset
			this.docOffset++;
			this.wordPart = viewer.getDocument().get(this.docOffset,
					documentOffset - this.docOffset);
		} catch (final BadLocationException e) {
			// do nothing
		}
	}

	public String getString() {
		return this.wordPart;
	}

	public int getOffset() {
		return this.docOffset;
	}

}
