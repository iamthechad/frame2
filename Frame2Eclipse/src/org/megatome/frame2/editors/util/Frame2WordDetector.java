package org.megatome.frame2.editors.util;

import org.eclipse.jface.text.rules.IWordDetector;
import org.megatome.frame2.editors.IFrame2Syntax;

public class Frame2WordDetector implements IWordDetector, IFrame2Syntax {

	public boolean isWordStart(char c) {
		for (int i = 0; i < reservedWords.length; i++)
			if (((String) reservedWords[i]).charAt(0) == c)
				return true;
		for (int i = 0; i < types.length; i++)
			if (((String) types[i]).charAt(0) == c)
				return true;

		for (int i = 0; i < constants.length; i++)
			if (((String) constants[i]).charAt(0) == c)
				return true;
		return false;
	}

	public boolean isWordPart(char c) {
		for (int i = 0; i < reservedWords.length; i++)
			if (((String) reservedWords[i]).indexOf(c) != -1)
				return true;
		for (int i = 0; i < types.length; i++)
			if (((String) types[i]).indexOf(c) != -1)
				return true;

		for (int i = 0; i < constants.length; i++)
			if (((String) constants[i]).indexOf(c) != -1)
				return true;

		return false;
	}

}
