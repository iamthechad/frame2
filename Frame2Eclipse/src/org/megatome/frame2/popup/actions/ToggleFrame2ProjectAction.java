package org.megatome.frame2.popup.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.megatome.frame2.Frame2Plugin;
import org.megatome.frame2.builder.Frame2Nature;
import org.megatome.frame2.util.PluginLogger;

public class ToggleFrame2ProjectAction implements IObjectActionDelegate {
	private IStructuredSelection sel;

	public ToggleFrame2ProjectAction() {
		super();
	}

	public void setActivePart(@SuppressWarnings("unused")
	IAction action, @SuppressWarnings("unused")
	IWorkbenchPart targetPart) {
		//NOOP
	}

	public void run(@SuppressWarnings("unused")
	IAction action) {
		if (this.sel != null) {
			// Should only be one selected item due to the action config
			Object o = this.sel.getFirstElement();
			if (o instanceof IProject) {
				IProject project = (IProject)o;
				if (!setFrame2NatureOnProject(project)) {
					Shell shell = new Shell();
					MessageDialog.openInformation(
						shell,
						Frame2Plugin.getString("ToggleFrame2ProjectAction.MessageTitle"), //$NON-NLS-1$
						Frame2Plugin.getString("ToggleFrame2ProjectAction.ErrorMessage")); //$NON-NLS-1$
				}
			}
		}
	}

	public void selectionChanged(@SuppressWarnings("unused")
	IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			this.sel = (IStructuredSelection)selection;
		}
	}
	
	private boolean setFrame2NatureOnProject(final IProject project) {
		try {
			final IProjectDescription description = project.getDescription();
			final String[] prevNatures = description.getNatureIds();

			for (int i = 0; i < prevNatures.length; i++) {
				if (Frame2Nature.NATURE_ID.equals(prevNatures[i])) {
					PluginLogger.info("Selected project already has the Frame2 nature."); //$NON-NLS-1$
					return true;
				}
			}

			// Add nature only if it is not already there
			final String[] newNatures = new String[prevNatures.length + 1];
			System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
			newNatures[prevNatures.length] = Frame2Nature.NATURE_ID;
			description.setNatureIds(newNatures);
			project.setDescription(description, null);
		} catch (CoreException e) {
			PluginLogger.error("Could not set Frame2 nature", e); //$NON-NLS-1$
			return false;
		}
		
		return true;
	}

}
