package org.megatome.frame2.editors;

import java.util.Iterator;
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

public class XMLCompletionProcessor
	implements IContentAssistProcessor, IFrame2Syntax {

	protected Vector proposalList = new Vector();
	protected IContextInformationValidator fValidator = new Validator();

	public ICompletionProposal[] computeCompletionProposals(
		ITextViewer viewer,
		int documentOffset) {
		WordPartDetector wordPart =
			new WordPartDetector(viewer, documentOffset);

		// iterate over all the different categories
		for (int i = 0; i < allWords.length; i++) {
			String[] list = (String[]) allWords[i];
			// iterate over the current category
			for (int y = 0; y < list.length; y++) {
				if (list[y].startsWith(wordPart.getString().toUpperCase()))
					proposalList.add(list[y]);
			}
		}

		return turnProposalVectorIntoAdaptedArray(wordPart);

	}

	protected ICompletionProposal[] turnProposalVectorIntoAdaptedArray(WordPartDetector word) {
		ICompletionProposal[] result =
			new ICompletionProposal[proposalList.size()];

		int index = 0;

		for (Iterator i = proposalList.iterator(); i.hasNext();) {
			String keyWord = (String) i.next();

			IContextInformation info =
				new ContextInformation(keyWord, getContentInfoString(keyWord));
			//Creates a new completion proposal. 
				result[index] =
					new CompletionProposal(keyWord, //replacementString
		word.getOffset(),
			//replacementOffset the offset of the text to be replaced
		word.getString().length(),
			//replacementLength the length of the text to be replaced
		keyWord.length(),
			//cursorPosition the position of the cursor following the insert relative to replacementOffset
		null, //image to display
		keyWord, //displayString the string to be displayed for the proposal
		info,
			//contntentInformation the context information associated with this proposal
	getContentInfoString(keyWord));
			index++;
		}
		// System.out.println("result : " + result.length);
		proposalList.removeAllElements();
		return result;
	}

	private String getContentInfoString(String keyWord) {
		String resourceString;
		String resourceKey = "ContextString." + keyWord;
		resourceString =
			Frame2Plugin.getResourceString(resourceKey);
		if (resourceString == keyWord) {
			resourceString = "No Context Info String";
		}
		return resourceString;
	}

	public IContextInformation[] computeContextInformation(
		ITextViewer viewer,
		int documentOffset) {
		WordPartDetector wordPart =
			new WordPartDetector(viewer, documentOffset);

		IContextInformation[] result = new IContextInformation[2];
		result[0] =
			new ContextInformation(
				"contextDisplayString",
				"informationDisplayString");
		result[1] =
			new ContextInformation(
				"contextDisplayString2",
				"informationDisplayString2");

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
		return fValidator;
	}
	/**
	 * Simple content assist tip closer. The tip is valid in a range
	 * of 5 characters around its popup location.
	 */
	protected static class Validator
		implements IContextInformationValidator, IContextInformationPresenter {

		protected int fInstallOffset;

		/*
		 * @see IContextInformationValidator#isContextInformationValid(int)
		 */
		public boolean isContextInformationValid(int offset) {
			return Math.abs(fInstallOffset - offset) < 5;
		}

		/*
		 * @see IContextInformationValidator#install(IContextInformation, ITextViewer, int)
		 */
		public void install(
			IContextInformation info,
			ITextViewer viewer,
			int offset) {
			fInstallOffset = offset;
		}

		/*
		 * @see org.eclipse.jface.text.contentassist.IContextInformationPresenter#updatePresentation(int, TextPresentation)
		 */
		public boolean updatePresentation(
			int documentPosition,
			TextPresentation presentation) {
			return false;
		}
	};
}
