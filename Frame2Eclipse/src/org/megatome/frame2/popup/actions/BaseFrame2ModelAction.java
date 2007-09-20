package org.megatome.frame2.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public abstract class BaseFrame2ModelAction implements IObjectActionDelegate {
	private IStructuredSelection selection;

	public void setActivePart(@SuppressWarnings("unused")
	IAction action, @SuppressWarnings("unused")
	IWorkbenchPart targetPart) {
		//NOOP
	}

	public void run(@SuppressWarnings("unused")
	IAction action) {
		Shell shell = new Shell();
		new WizardDialog(shell, getWizard(this.selection)).open();
	}

	public void selectionChanged(@SuppressWarnings("unused")
	IAction action, @SuppressWarnings("hiding")
	ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			this.selection = (IStructuredSelection)selection;
		}
	}
	
	public abstract IWizard getWizard(IStructuredSelection sel);
}
