package org.megatome.frame2.editors;

import org.eclipse.ui.editors.text.TextEditor;

public class Frame2ConfigEditor extends TextEditor {

	private ColorManager colorManager;

	public Frame2ConfigEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new XMLConfiguration(colorManager));
		setDocumentProvider(new XMLDocumentProvider());
	}
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
