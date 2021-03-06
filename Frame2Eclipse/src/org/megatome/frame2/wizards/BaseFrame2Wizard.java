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
package org.megatome.frame2.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.megatome.frame2.Frame2Plugin;
import org.megatome.frame2.model.Frame2Model;
import org.megatome.frame2.model.Frame2ModelException;

public abstract class BaseFrame2Wizard extends Wizard implements INewWizard {

	protected IStructuredSelection selection;
	protected IProject currentProject = null;

	private Frame2Model model = null;
	private IFile modelFile = null;
	
	public BaseFrame2Wizard() {
		super();
	}
	
	public BaseFrame2Wizard(final IStructuredSelection selection) {
		super();
		initialize(selection);
	}

	protected void throwCoreException(final String message)
			throws CoreException {
		final IStatus status = new Status(IStatus.ERROR,
				"org.megatome.frame2", IStatus.OK, message, null); //$NON-NLS-1$
		throw new CoreException(status);
	}

	public void init(@SuppressWarnings("unused")
	final IWorkbench workbench, @SuppressWarnings("hiding")
	final IStructuredSelection selection) {
		initialize(selection);
	}
	
	public abstract String getFrame2WizardTitle();
	
	private void initialize(final IStructuredSelection iss) {
		this.selection = iss;
		setWindowTitle(getFrame2WizardTitle());
		setDefaultPageImageDescriptor(Frame2WizardSupport.getFrame2Logo());
		try {
			this.model = initFrame2Model(iss);
		} catch (final Frame2ModelException e) {
			String errorMsg;
			if (e.getCause() != null) {
				errorMsg = e.getCause().getMessage();
			} else {
				errorMsg = e.getMessage();
			}
			final MultiStatus info = new MultiStatus(
					"org.megatome.frame2", //$NON-NLS-1$
					IStatus.ERROR,
					Frame2Plugin
							.getString("NewEventWizard.configReadError"), e); //$NON-NLS-1$
			final Status msg = new Status(IStatus.ERROR, "org.megatome.frame2", //$NON-NLS-1$
					IStatus.ERROR, errorMsg, e);
			info.add(msg);

			ErrorDialog.openError(getShell(), Frame2Plugin
					.getString("NewEventWizard.wizardInitError"), //$NON-NLS-1$
					null, info);
		}

		if (this.model == null) {
			// Error
			final String errorMsg = Frame2Plugin
					.getString("BaseFrame2Wizard.noFrame2ProjectSelected"); //$NON-NLS-1$
			final Exception e = new Exception(errorMsg);
			final MultiStatus info = new MultiStatus(
					"org.megatome.frame2", //$NON-NLS-1$
					IStatus.ERROR,
					Frame2Plugin
							.getString("NewEventWizard.configReadError"), e); //$NON-NLS-1$
			final Status msg = new Status(IStatus.ERROR, "org.megatome.frame2", //$NON-NLS-1$
					IStatus.ERROR, errorMsg, e);
			info.add(msg);

			ErrorDialog.openError(getShell(), Frame2Plugin
					.getString("NewEventWizard.wizardInitError"), //$NON-NLS-1$
					null, info);
		}
	}

	private Frame2Model initFrame2Model(@SuppressWarnings("hiding")
	final IStructuredSelection selection) throws Frame2ModelException {
		Frame2Model mod = null;
		IProject selected = null;
		if (selection != null && !selection.isEmpty()) {
			if (selection.size() > 1) {
				throw new Frame2ModelException(
						Frame2Plugin
								.getString("NewEventWizard.errorMultipleSelection")); //$NON-NLS-1$
			}

			final Object obj = selection.getFirstElement();
			if (obj instanceof IResource) {
				final IProject rootProject = ((IResource) obj).getProject();
				selected = rootProject;
			}
		} else {
			// In the case of no selection - need to look for the file
			// There is a possibility that more than one web application project
			// may be open
			final IWorkspace workspace = ResourcesPlugin.getWorkspace();

			final IProject[] allProjects = workspace.getRoot().getProjects();

			final List<IProject> f2Projects = new ArrayList<IProject>();
			for (int i = 0; i < allProjects.length; i++) {
				final IResource resource = allProjects[i]
						.findMember(Frame2Plugin
								.getString("NewEventWizard.fullConfigPath")); //$NON-NLS-1$
				if (resource != null) {
					f2Projects.add(allProjects[i]);
				}
			}

			IProject[] projects = new IProject[f2Projects.size()];
			projects = f2Projects.toArray(projects);

			// Do we need to handle 0 case?
			if (projects.length == 1) {
				selected = projects[0];
			} else if (projects.length > 1) {
				// Ask for selection
				final ElementListSelectionDialog dlg = new ElementListSelectionDialog(
						getShell(), new WorkbenchLabelProvider());
				dlg
						.setMessage(Frame2Plugin
								.getString("BaseFrame2Wizard.selectFrame2ProjectMessage")); //$NON-NLS-1$
				dlg
						.setTitle(Frame2Plugin
								.getString("BaseFrame2Wizard.selectFrame2ProjectTitle")); //$NON-NLS-1$
				dlg.setMultipleSelection(false);
				dlg.setElements(projects);
				dlg.setBlockOnOpen(true);
				final int retVal = dlg.open();
				if (retVal == SWT.OK) {
					// Open file
					final Object[] result = dlg.getResult();
					if (result != null) {
						selected = (IProject) result[0];
					}
				}
			}
		}

		if (selected != null) {
			mod = loadFrame2Model(selected);
		}
		
		this.currentProject = selected;

		return mod;
	}

	private Frame2Model loadFrame2Model(final IProject project)
			throws Frame2ModelException {
		final IResource resource = project.findMember(Frame2Plugin
				.getString("NewEventWizard.fullConfigPath")); //$NON-NLS-1$
		if (resource == null) {
			throw new Frame2ModelException(Frame2Plugin
					.getString("NewEventWizard.errorLocatingConfig")); //$NON-NLS-1$
		}
		IPath path = resource.getLocation();
		if (path == null) {
			throw new Frame2ModelException(
					Frame2Plugin
							.getString("NewEventWizard.errorLocatingConfigPath")); //$NON-NLS-1$
		}
		this.modelFile = (IFile) resource.getAdapter(IFile.class);
		return Frame2Model.getInstance(this.modelFile);
	}

	public Frame2Model getFrame2Model() {
		return this.model;
	}
	
	public IProject getCurrentProject() {
		return this.currentProject;
	}
	
	public void persistModel() throws Frame2ModelException {
		persistModel(null);
	}
	
	public void persistModel(IProgressMonitor monitor) throws Frame2ModelException {
		this.model.persistConfiguration(this.modelFile, monitor);
		refreshModelResource();
	}

	private void refreshModelResource() throws Frame2ModelException {
		try {
			if (this.modelFile != null) {
				this.modelFile.refreshLocal(IResource.DEPTH_ZERO, null);
			}
		} catch (CoreException e) {
			throw new Frame2ModelException("Error refreshing config file", e); //$NON-NLS-1$
		}
	}
}