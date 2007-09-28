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
						.getString("Frame2ConfigEditor.ContentAssistProposalPrefix"), //$NON-NLS-1$
				this, ISourceViewer.CONTENTASSIST_PROPOSALS);

		a
				.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
		setAction(
				Frame2Plugin
						.getString("Frame2ConfigEditor.ContentAssistProposal"), a); //$NON-NLS-1$
		a = new TextOperationAction(
				bundle,
				Frame2Plugin
						.getString("Frame2ConfigEditor.ContentAssistTipPrefix"), //$NON-NLS-1$
				this, ISourceViewer.CONTENTASSIST_CONTEXT_INFORMATION);

		a
				.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_CONTEXT_INFORMATION);
		setAction(Frame2Plugin
				.getString("Frame2ConfigEditor.ContentAssistTip"), a); //$NON-NLS-1$
		a = new TextOperationAction(
				bundle,
				Frame2Plugin
						.getString("Frame2ConfigEditor.ContentFormatProposalPrefix"), //$NON-NLS-1$
				this, ISourceViewer.FORMAT);
		setAction(
				Frame2Plugin
						.getString("Frame2ConfigEditor.ContentFormatProposal"), a); //$NON-NLS-1$
		final ResourceAction ra = new AddTaskAction(bundle, Frame2Plugin
				.getString("Frame2ConfigEditor.AddTask"), this); //$NON-NLS-1$
		ra.setHelpContextId(ITextEditorHelpContextIds.ADD_TASK_ACTION);
		ra.setActionDefinitionId(IWorkbenchActionDefinitionIds.ADD_TASK);
		setAction(IDEActionFactory.ADD_TASK.getId(), ra);

	}

}
