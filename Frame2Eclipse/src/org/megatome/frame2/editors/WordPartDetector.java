package org.megatome.frame2.editors;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITextViewer;
 
public class WordPartDetector {
	String wordPart = ""; //$NON-NLS-1$
	int docOffset;
	
	public WordPartDetector(ITextViewer viewer, int documentOffset) {
		docOffset = documentOffset - 1;		
		try {
			while (((docOffset) >= viewer.getTopIndexStartOffset())   && Character.isLetterOrDigit(viewer.getDocument().getChar(docOffset))) {
				docOffset--;
			}
			//we've been one step too far : increase the offset
			docOffset++;
			wordPart = viewer.getDocument().get(docOffset, documentOffset - docOffset);
		} catch (BadLocationException e) {
			// do nothing
		}
	}
	
	public String getString() {
		return wordPart;
	}
	
	public int getOffset() {
		return docOffset;
	}

}
