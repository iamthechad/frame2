package org.megatome.frame2.editors;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;

public class TagRule extends MultiLineRule {

	public TagRule(final IToken token) {
		super("<", ">", token); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	protected boolean sequenceDetected(final ICharacterScanner scanner,
			final char[] sequence, final boolean eofAllowed) {
		final int c = scanner.read();
		if (sequence[0] == '<') {
			if (c == '?') {
				// processing instruction - abort
				scanner.unread();
				return false;
			}
			if (c == '!') {
				scanner.unread();
				// comment - abort
				return false;
			}
		} else if (sequence[0] == '>') {
			scanner.unread();
		}
		return super.sequenceDetected(scanner, sequence, eofAllowed);
	}
}
