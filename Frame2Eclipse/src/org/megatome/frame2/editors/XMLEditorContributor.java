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
		ResourceBundle bundle =
			Frame2Plugin.getDefault().getResourceBundle();

		fContentAssistProposal =
			new RetargetTextEditorAction(bundle, "ContentAssistProposal.");
		//		Added this call for 2.1 changes
		// 		New to 2.1 - CTRL+Space key doesn't work without making this call 	

		fContentAssistProposal.setActionDefinitionId(
			ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);

		fContentFormatProposal =
			new RetargetTextEditorAction(bundle, "ContentFormatProposal.");
		fContentAssistTip =
			new RetargetTextEditorAction(bundle, "ContentAssistTip.");
		fContentAssistTip.setActionDefinitionId(
			ITextEditorActionDefinitionIds.CONTENT_ASSIST_CONTEXT_INFORMATION);

	}
	public void contributeToMenu(IMenuManager mm) {
		IMenuManager editMenu =
			mm.findMenuUsingPath(IWorkbenchActionConstants.M_EDIT);
		if (editMenu != null) {
			editMenu.add(new Separator());
			editMenu.add(fContentAssistProposal);
			editMenu.add(fContentFormatProposal);
			editMenu.add(fContentAssistTip);
		}
	}
	public void setActiveEditor(IEditorPart part) {

		super.setActiveEditor(part);

		ITextEditor editor = null;
		if (part instanceof ITextEditor)
			editor = (ITextEditor) part;

		fContentAssistProposal.setAction(
			getAction(editor, "ContentAssistProposal"));
		fContentFormatProposal.setAction(
			getAction(editor, "ContentFormatProposal"));
		fContentAssistTip.setAction(getAction(editor, "ContentAssistTip"));

	}
	public void contributeToToolBar(IToolBarManager tbm) {
		tbm.add(new Separator());
	}

}
