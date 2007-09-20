package org.megatome.frame2.popup.actions;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.megatome.frame2.wizards.GlobalForwardWizard;

public class GlobalForwardAction extends BaseFrame2ModelAction {

	@Override
	public IWizard getWizard(IStructuredSelection sel) {
		return new GlobalForwardWizard(sel);
	}
}
