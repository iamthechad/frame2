package org.megatome.frame2.editors;

import java.util.ResourceBundle;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.ITextEditorHelpContextIds;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.IDEActionFactory;
import org.eclipse.ui.texteditor.AddTaskAction;
import org.eclipse.ui.texteditor.DefaultRangeIndicator;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;
import org.eclipse.ui.texteditor.ResourceAction;
import org.eclipse.ui.texteditor.TextOperationAction;
import org.megatome.frame2.Frame2Plugin;

public class Frame2ConfigEditor extends TextEditor {

	private final ColorManager colorManager;

	public Frame2ConfigEditor() {
		super();
		this.colorManager = new ColorManager();
		setSourceViewerConfiguration(new XMLConfiguration(this.colorManager));
		setDocumentProvider(new XMLDocumentProvider());
		setRangeIndicator(new DefaultRangeIndicator());
	}

	@Override
	public void dispose() {
		this.colorManager.dispose();
		super.dispose();
	}

	@Override
	protected void createActions() {
		super.createActions();

		final ResourceBundle bundle = Frame2Plugin.getDefault()
				.getResourceBundle();
		IAction a = new TextOperationAction(
				bundle,
				Frame2Plugin
						.getResourceString("Frame2ConfigEditor.ContentAssistProposalPrefix"), //$NON-NLS-1$
				this, ISourceViewer.CONTENTASSIST_PROPOSALS);

		a
				.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
		setAction(
				Frame2Plugin
						.getResourceString("Frame2ConfigEditor.ContentAssistProposal"), a); //$NON-NLS-1$
		a = new TextOperationAction(
				bundle,
				Frame2Plugin
						.getResourceString("Frame2ConfigEditor.ContentAssistTipPrefix"), //$NON-NLS-1$
				this, ISourceViewer.CONTENTASSIST_CONTEXT_INFORMATION);

		a
				.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_CONTEXT_INFORMATION);
		setAction(Frame2Plugin
				.getResourceString("Frame2ConfigEditor.ContentAssistTip"), a); //$NON-NLS-1$
		a = new TextOperationAction(
				bundle,
				Frame2Plugin
						.getResourceString("Frame2ConfigEditor.ContentFormatProposalPrefix"), //$NON-NLS-1$
				this, ISourceViewer.FORMAT);
		setAction(
				Frame2Plugin
						.getResourceString("Frame2ConfigEditor.ContentFormatProposal"), a); //$NON-NLS-1$
		final ResourceAction ra = new AddTaskAction(bundle, Frame2Plugin
				.getResourceString("Frame2ConfigEditor.AddTask"), this); //$NON-NLS-1$
		ra.setHelpContextId(ITextEditorHelpContextIds.ADD_TASK_ACTION);
		ra.setActionDefinitionId(IWorkbenchActionDefinitionIds.ADD_TASK);
		setAction(IDEActionFactory.ADD_TASK.getId(), ra);

	}

}
