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

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.graphics.RGB;
import org.megatome.frame2.Frame2Plugin;
import org.megatome.frame2.editors.util.ColorProvider;


public class XMLEditorSourceViewerConfiguration extends
      SourceViewerConfiguration {

   public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
      ContentAssistant assistant = new ContentAssistant();
      
      assistant.setContentAssistProcessor(
            new XMLCompletionProcessor(),
            IDocument.DEFAULT_CONTENT_TYPE);
      assistant.setContentAssistProcessor(
            new XMLCompletionProcessor(),
            XMLPartitionScanner.XML_TAG);
      assistant.enableAutoActivation(true);
      assistant.setAutoActivationDelay(500);
      
      assistant.setProposalPopupOrientation(IContentAssistant.CONTEXT_INFO_BELOW);
      
      assistant.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_BELOW);
      
      assistant.setContextInformationPopupBackground(
            Frame2Plugin.getDefault().getColorProvider().getColor(
                  new RGB(0, 191, 255)));
      
      return assistant;
   }

   public ITextDoubleClickStrategy getDoubleClickStrategy(
         ISourceViewer sourceViewer, String contentType) {
      	return new XMLDoubleClickStrategy();
   }
   
   public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {

		PresentationReconciler reconciler= new PresentationReconciler();

		// rule for default text
		DefaultDamagerRepairer dr= new DefaultDamagerRepairer(XMLEditor.getXMLScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		// rule for multiline comments
		// We just need a scanner that does nothing but returns a token with the corrresponding text attributes
		RuleBasedScanner multiLineScanner = new RuleBasedScanner();
		multiLineScanner.setDefaultReturnToken(new Token(new TextAttribute(Frame2Plugin.getDefault().getColorProvider().getColor(ColorProvider.MULTI_LINE_COMMENT))));
		dr= new DefaultDamagerRepairer(multiLineScanner);
		reconciler.setDamager(dr, XMLPartitionScanner.XML_COMMENT);
		reconciler.setRepairer(dr, XMLPartitionScanner.XML_COMMENT);

		dr= new DefaultDamagerRepairer(XMLEditor.getXMLScanner());
		reconciler.setDamager(dr, XMLPartitionScanner.XML_TAG);
		reconciler.setRepairer(dr, XMLPartitionScanner.XML_TAG);
		
		return reconciler;
	}
}
