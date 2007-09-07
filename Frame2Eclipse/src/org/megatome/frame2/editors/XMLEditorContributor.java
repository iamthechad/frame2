package org.megatome.frame2.editors;

import java.util.ResourceBundle;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.editors.text.TextEditorActionContributor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.RetargetTextEditorAction;
import org.megatome.frame2.Frame2Plugin;

public class XMLEditorContributor extends TextEditorActionContributor {

	protected RetargetTextEditorAction fContentAssistProposal;
	protected RetargetTextEditorAction fContentAssistTip;
	protected RetargetTextEditorAction fContentFormatProposal;

	public XMLEditorContributor() {
		super();
		final ResourceBundle bundle = Frame2Plugin.getDefault()
				.getResourceBundle();

		this.fContentAssistProposal = new RetargetTextEditorAction(bundle,
				"ContentAssistProposal."); //$NON-NLS-1$
		// Added this call for 2.1 changes
		// New to 2.1 - CTRL+Space key doesn't work without making this call

		this.fContentAssistProposal
				.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);

		this.fContentFormatProposal = new RetargetTextEditorAction(bundle,
				"ContentFormatProposal."); //$NON-NLS-1$
		this.fContentAssistTip = new RetargetTextEditorAction(bundle,
				"ContentAssistTip."); //$NON-NLS-1$
		this.fContentAssistTip
				.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_CONTEXT_INFORMATION);

	}

	@Override
	public void contributeToMenu(final IMenuManager mm) {
		final IMenuManager editMenu = mm
				.findMenuUsingPath(IWorkbenchActionConstants.M_EDIT);
		if (editMenu != null) {
			editMenu.add(new Separator());
			editMenu.add(this.fContentAssistProposal);
			editMenu.add(this.fContentFormatProposal);
			editMenu.add(this.fContentAssistTip);
		}
	}

	@Override
	public void setActiveEditor(final IEditorPart part) {

		super.setActiveEditor(part);

		ITextEditor editor = null;
		if (part instanceof ITextEditor) {
			editor = (ITextEditor) part;
		}

		this.fContentAssistProposal.setAction(getAction(editor,
				"ContentAssistProposal")); //$NON-NLS-1$
		this.fContentFormatProposal.setAction(getAction(editor,
				"ContentFormatProposal")); //$NON-NLS-1$
		this.fContentAssistTip.setAction(getAction(editor, "ContentAssistTip")); //$NON-NLS-1$

	}

	@Override
	public void contributeToToolBar(final IToolBarManager tbm) {
		tbm.add(new Separator());
	}

}
