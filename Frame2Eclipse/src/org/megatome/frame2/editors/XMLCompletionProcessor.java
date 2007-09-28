/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2007 Megatome Technologies.  All rights
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
package org.megatome.frame2.editors;

import java.util.Vector;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationPresenter;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.megatome.frame2.Frame2Plugin;

public class XMLCompletionProcessor implements IContentAssistProcessor,
		IFrame2Syntax {

	protected Vector<String> proposalList = new Vector<String>();
	protected IContextInformationValidator fValidator = new Validator();

	public ICompletionProposal[] computeCompletionProposals(
			final ITextViewer viewer, final int documentOffset) {
		final WordPartDetector wordPart = new WordPartDetector(viewer,
				documentOffset);

		// iterate over all the different categories
		for (int i = 0; i < allWords.length; i++) {
			final String[] list = (String[]) allWords[i];
			// iterate over the current category
			for (int y = 0; y < list.length; y++) {
				if (list[y].startsWith(wordPart.getString().toUpperCase())) {
					this.proposalList.add(list[y]);
				}
			}
		}

		return turnProposalVectorIntoAdaptedArray(wordPart);

	}

	protected ICompletionProposal[] turnProposalVectorIntoAdaptedArray(
			final WordPartDetector word) {
		final ICompletionProposal[] result = new ICompletionProposal[this.proposalList
				.size()];

		int index = 0;

		for (String keyWord : this.proposalList) {
			final IContextInformation info = new ContextInformation(keyWord,
					getContentInfoString(keyWord));
			// Creates a new completion proposal.
			result[index] = new CompletionProposal(keyWord, // replacementString
					word.getOffset(),
					// replacementOffset the offset of the text to be replaced
					word.getString().length(),
					// replacementLength the length of the text to be replaced
					keyWord.length(),
					// cursorPosition the position of the cursor following the
					// insert relative to replacementOffset
					null, // image to display
					keyWord, // displayString the string to be displayed for
								// the proposal
					info,
					// contntentInformation the context information associated
					// with this proposal
					getContentInfoString(keyWord));
			index++;
		}
		// System.out.println("result : " + result.length);
		this.proposalList.removeAllElements();
		return result;
	}

	private String getContentInfoString(final String keyWord) {
		String resourceString;
		final String resourceKey = "ContextString." + keyWord; //$NON-NLS-1$
		resourceString = Frame2Plugin.getString(resourceKey);
		if (resourceString.equals(keyWord)) {
			resourceString = "No Context Info String"; //$NON-NLS-1$
		}
		return resourceString;
	}

	public IContextInformation[] computeContextInformation(
			final ITextViewer viewer, final int documentOffset) {
		// WordPartDetector wordPart =
		new WordPartDetector(viewer, documentOffset);

		final IContextInformation[] result = new IContextInformation[2];
		result[0] = new ContextInformation("contextDisplayString", //$NON-NLS-1$
				"informationDisplayString"); //$NON-NLS-1$
		result[1] = new ContextInformation("contextDisplayString2", //$NON-NLS-1$
				"informationDisplayString2"); //$NON-NLS-1$

		return result;
	}

	/**
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getCompletionProposalAutoActivationCharacters()
	 */
	public char[] getCompletionProposalAutoActivationCharacters() {
		return null;
	}

	/**
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationAutoActivationCharacters()
	 */
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	/**
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getErrorMessage()
	 */
	public String getErrorMessage() {
		return null;
	}

	/**
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationValidator()
	 */
	public IContextInformationValidator getContextInformationValidator() {
		return this.fValidator;
	}

	/**
	 * Simple content assist tip closer. The tip is valid in a range of 5
	 * characters around its popup location.
	 */
	protected static class Validator implements IContextInformationValidator,
			IContextInformationPresenter {

		protected int fInstallOffset;

		/*
		 * @see IContextInformationValidator#isContextInformationValid(int)
		 */
		public boolean isContextInformationValid(final int offset) {
			return Math.abs(this.fInstallOffset - offset) < 5;
		}

		/*
		 * @see IContextInformationValidator#install(IContextInformation,
		 *      ITextViewer, int)
		 */
		public void install(@SuppressWarnings("unused")
		final IContextInformation info, @SuppressWarnings("unused")
		final ITextViewer viewer, final int offset) {
			this.fInstallOffset = offset;
		}

		/*
		 * @see org.eclipse.jface.text.contentassist.IContextInformationPresenter#updatePresentation(int,
		 *      TextPresentation)
		 */
		public boolean updatePresentation(@SuppressWarnings("unused")
		final int documentPosition, @SuppressWarnings("unused")
		final TextPresentation presentation) {
			return false;
		}
	}
}
