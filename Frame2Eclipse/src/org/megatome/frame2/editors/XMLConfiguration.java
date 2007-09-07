package org.megatome.frame2.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.graphics.RGB;

public class XMLConfiguration extends SourceViewerConfiguration {
	private XMLDoubleClickStrategy doubleClickStrategy;
	private XMLTagScanner tagScanner;
	private XMLScanner scanner;
	private XMLCompletionProcessor completionProcessor;
	private final ColorManager colorManager;

	public XMLConfiguration(final ColorManager colorManager) {
		this.colorManager = colorManager;
	}

	@Override
	public String[] getConfiguredContentTypes(@SuppressWarnings("unused")
	final ISourceViewer sourceViewer) {
		return new String[] { IDocument.DEFAULT_CONTENT_TYPE,
				XMLPartitionScanner.XML_COMMENT, XMLPartitionScanner.XML_TAG };
	}

	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy(
			@SuppressWarnings("unused")
			final ISourceViewer sourceViewer, @SuppressWarnings("unused")
			final String contentType) {
		if (this.doubleClickStrategy == null) {
			this.doubleClickStrategy = new XMLDoubleClickStrategy();
		}
		return this.doubleClickStrategy;
	}

	protected XMLScanner getXMLScanner() {
		if (this.scanner == null) {
			this.scanner = new XMLScanner(this.colorManager);
			this.scanner.setDefaultReturnToken(new Token(new TextAttribute(
					this.colorManager.getColor(IXMLColorConstants.DEFAULT))));
		}
		return this.scanner;
	}

	protected XMLTagScanner getXMLTagScanner() {
		if (this.tagScanner == null) {
			this.tagScanner = new XMLTagScanner(this.colorManager);
			this.tagScanner.setDefaultReturnToken(new Token(new TextAttribute(
					this.colorManager.getColor(IXMLColorConstants.TAG))));
		}
		return this.tagScanner;
	}

	protected XMLCompletionProcessor getXMLCompletionProcessor() {
		if (this.completionProcessor == null) {
			this.completionProcessor = new XMLCompletionProcessor();
		}
		return this.completionProcessor;
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(
			@SuppressWarnings("unused")
			final ISourceViewer sourceViewer) {
		final PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(
				getXMLTagScanner());
		reconciler.setDamager(dr, XMLPartitionScanner.XML_TAG);
		reconciler.setRepairer(dr, XMLPartitionScanner.XML_TAG);

		dr = new DefaultDamagerRepairer(getXMLScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		final NonRuleBasedDamagerRepairer ndr = new NonRuleBasedDamagerRepairer(
				new TextAttribute(this.colorManager
						.getColor(IXMLColorConstants.XML_COMMENT)));
		reconciler.setDamager(ndr, XMLPartitionScanner.XML_COMMENT);
		reconciler.setRepairer(ndr, XMLPartitionScanner.XML_COMMENT);

		return reconciler;
	}

	@Override
	public IContentAssistant getContentAssistant(@SuppressWarnings("unused")
	final ISourceViewer sourceViewer) {
		final ContentAssistant assistant = new ContentAssistant();
		assistant.setContentAssistProcessor(getXMLCompletionProcessor(),
				IDocument.DEFAULT_CONTENT_TYPE);
		assistant.setContentAssistProcessor(getXMLCompletionProcessor(),
				XMLPartitionScanner.XML_TAG);
		assistant.enableAutoActivation(true);
		assistant.setAutoActivationDelay(500);

		assistant
				.setProposalPopupOrientation(IContentAssistant.CONTEXT_INFO_BELOW);
		assistant
				.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_BELOW);
		assistant.setContextInformationPopupBackground(this.colorManager
				.getColor(new RGB(0, 191, 255)));
		return assistant;
	}
}