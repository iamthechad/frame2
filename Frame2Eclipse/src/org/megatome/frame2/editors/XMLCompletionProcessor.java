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
package org.megatome.frame2.editors;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.megatome.frame2.Frame2Plugin;
import org.megatome.frame2.editors.util.WordPartDetector;


public class XMLCompletionProcessor implements IContentAssistProcessor,
      IFrame2Syntax {

   protected Vector proposalList = new Vector();
   
   public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
         int documentOffset) {
      WordPartDetector wordPart = new WordPartDetector(viewer, documentOffset);
      
      for (int i = 0; i < allWords.length; i++) {
         String[] list = (String[])allWords[i];
         for (int y = 0; y < list.length; y++) {
            if (list[y].startsWith(wordPart.getString().toUpperCase())) {
               proposalList.add(list[y]);
            }
         }
      }
      return turnProposalVectorIntoAdaptedArray(wordPart);
   }

   public IContextInformation[] computeContextInformation(ITextViewer viewer,
         int documentOffset) {
      return null;
   }

   public char[] getCompletionProposalAutoActivationCharacters() {
      return null;
   }

   public char[] getContextInformationAutoActivationCharacters() {
      return null;
   }

   public String getErrorMessage() {
      return null;
   }

   public IContextInformationValidator getContextInformationValidator() {
      return null;
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

}
