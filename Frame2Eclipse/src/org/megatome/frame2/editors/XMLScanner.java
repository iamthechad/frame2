/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004 Megatome Technologies.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by
 *        Megatome Technologies."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Frame2 Project", and "Frame2", 
 *    must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact iamthechad@sourceforge.net.
 *
 * 5. Products derived from this software may not be called "Frame2"
 *    nor may "Frame2" appear in their names without prior written
 *    permission of Megatome Technologies.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL MEGATOME TECHNOLOGIES OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.megatome.frame2.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.*;
import org.eclipse.jface.text.*;
import org.eclipse.swt.SWT;
import org.megatome.frame2.Frame2Plugin;
import org.megatome.frame2.editors.util.ColorProvider;
import org.megatome.frame2.editors.util.Frame2WordDetector;

public class XMLScanner extends RuleBasedScanner implements IFrame2Syntax {

	public XMLScanner() {
	   /*
		IToken procInstr =
			new Token(
				new TextAttribute(
					manager.getColor(IXMLColorConstants.PROC_INSTR)));

		IRule[] rules = new IRule[2];
		//Add rule for processing instructions
		rules[0] = new SingleLineRule("<?", "?>", procInstr);
		// Add generic whitespace rule.
		rules[1] = new WhitespaceRule(new XMLWhitespaceDetector());

		setRules(rules);
		*/
	   
	   ColorProvider provider =
			Frame2Plugin.getDefault().getColorProvider();
		IToken keyword =
			new Token(
				new TextAttribute(
					provider.getColor(ColorProvider.KEYWORD),
					provider.getColor(ColorProvider.BACKGROUND),
					SWT.BOLD));
		IToken type =
			new Token(
				new TextAttribute(
					provider.getColor(ColorProvider.TYPE),
					provider.getColor(ColorProvider.BACKGROUND),
					SWT.BOLD));
		IToken string =
			new Token(
				new TextAttribute(provider.getColor(ColorProvider.STRING)));
		IToken comment =
			new Token(
				new TextAttribute(
					provider.getColor(ColorProvider.SINGLE_LINE_COMMENT)));
		IToken other =
			new Token(
				new TextAttribute(provider.getColor(ColorProvider.DEFAULT)));
		IToken doctype = 
		   new Token(
		      new TextAttribute(provider.getColor(ColorProvider.DOCTYPE)));

		setDefaultReturnToken(other);
		List rules = new ArrayList();
		
		// Add rule for double quotes
		rules.add(new SingleLineRule("\"", "\"", string, '\\'));
		// Add a rule for single quotes
		rules.add(new SingleLineRule("'", "'", string, '\\'));
		
		IToken procInstr =
			new Token(
				new TextAttribute(
					provider.getColor(IXMLColorConstants.PROC_INSTR)));

		//Add rule for processing instructions
		rules.add(new SingleLineRule("<?", "?>", procInstr));

		rules.add(new MultiLineRule("<!DOCTYPE", "\">", doctype));
		
		for (int i = 0; i < reservedWords.length; i++) {
		   String ruleStartOpen = "<" + reservedWords[i];
		   String ruleStartClose = "</" + reservedWords[i];
			rules.add(new MultiLineRule(ruleStartOpen, ">", keyword));
			rules.add(new MultiLineRule(ruleStartClose, ">", keyword));
		}
		
		for (int i = 0; i < types.length; i++) {
		   String ruleStartOpen = "<" + types[i];
		   String ruleStartClose = "</" + types[i];
			rules.add(new MultiLineRule(ruleStartOpen, ">", type));
			rules.add(new MultiLineRule(ruleStartClose, ">", type));
		}

		// Add generic whitespace rule.
		rules.add(new WhitespaceRule(new XMLWhitespaceDetector()));

		// Add word rule for keywords, types, and constants.
		WordRule wordRule = new WordRule(new Frame2WordDetector(), other);
		for (int i = 0; i < reservedWords.length; i++)
			wordRule.addWord(reservedWords[i], keyword);
		for (int i = 0; i < types.length; i++)
			wordRule.addWord(types[i], type);
		for (int i = 0; i < constants.length; i++)
			wordRule.addWord(constants[i], type);
		rules.add(wordRule);

		IRule[] result = new IRule[rules.size()];
		rules.toArray(result);
		setRules(result);
	}
}
