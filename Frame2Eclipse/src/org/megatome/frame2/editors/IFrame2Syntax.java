package org.megatome.frame2.editors;

import org.megatome.frame2.Frame2Plugin;

public interface IFrame2Syntax {
	public final static String[] reservedWords = {
			Frame2Plugin.getResourceString("Frame2Model.frame2-config"), //$NON-NLS-1$
			Frame2Plugin.getResourceString("Frame2Model.global-forwards") //$NON-NLS-1$
	};

	Object[] allWords = { reservedWords };
}
