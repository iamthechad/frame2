/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2005 Megatome Technologies.  All rights
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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
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

   protected ISelection selection;

   protected Frame2Model model = null;
   
   protected void throwCoreException(String message) throws CoreException {
		IStatus status =
			new Status(IStatus.ERROR, "org.megatome.frame2", IStatus.OK, message, null); //$NON-NLS-1$
		throw new CoreException(status);
	}

   public void init(IWorkbench workbench, IStructuredSelection selection) {
      this.selection = selection;
      setDefaultPageImageDescriptor(Frame2WizardSupport.getFrame2Logo());
      try {
         model = initFrame2Model(selection);
      } catch (Frame2ModelException e) {
         String errorMsg;
         if (e.getCause() != null) {
            errorMsg = e.getCause().getMessage();
         } else {
            errorMsg = e.getMessage();
         }
         MultiStatus info = new MultiStatus("org.megatome.frame2", //$NON-NLS-1$
               Status.ERROR, Frame2Plugin
                     .getResourceString("NewEventWizard.configReadError"), e); //$NON-NLS-1$
         Status msg = new Status(Status.ERROR, "org.megatome.frame2", //$NON-NLS-1$
               Status.ERROR, errorMsg, e); 
         info.add(msg);

         ErrorDialog
               .openError(getShell(), Frame2Plugin
                     .getResourceString("NewEventWizard.wizardInitError"), //$NON-NLS-1$
                     null, info); 
      }

      if (model == null) {
         // Error
         String errorMsg = Frame2Plugin.getResourceString("BaseFrame2Wizard.noFrame2ProjectSelected"); //$NON-NLS-1$
         Exception e = new Exception(errorMsg);
         MultiStatus info = new MultiStatus("org.megatome.frame2", //$NON-NLS-1$
               Status.ERROR, Frame2Plugin
                     .getResourceString("NewEventWizard.configReadError"), e); //$NON-NLS-1$
         Status msg = new Status(Status.ERROR, "org.megatome.frame2", //$NON-NLS-1$
               Status.ERROR, errorMsg, e); 
         info.add(msg);

         ErrorDialog
               .openError(getShell(), Frame2Plugin
                     .getResourceString("NewEventWizard.wizardInitError"), //$NON-NLS-1$
                     null, info); 
      }
   }

   private Frame2Model initFrame2Model(IStructuredSelection selection)
         throws Frame2ModelException {
      Frame2Model mod = null;
      IProject selected = null;
      if (selection != null && selection.isEmpty() == false) {
         if (selection.size() > 1) { throw new Frame2ModelException(
               Frame2Plugin
                     .getResourceString("NewEventWizard.errorMultipleSelection")); //$NON-NLS-1$
         }

         Object obj = selection.getFirstElement();
         if (obj instanceof IResource) {
            IProject rootProject = ((IResource) obj).getProject();
            selected = rootProject;
         }
      } else {
         // In the case of no selection - need to look for the file
         // There is a possibility that more than one web application project
         // may be open
         IWorkspace workspace = ResourcesPlugin.getWorkspace();

         IProject[] allProjects = workspace.getRoot().getProjects();

         List f2Projects = new ArrayList();
         for (int i = 0; i < allProjects.length; i++) {
            IResource resource = allProjects[i].findMember(Frame2Plugin
                  .getResourceString("NewEventWizard.fullConfigPath")); //$NON-NLS-1$
            if (resource != null) {
               f2Projects.add(allProjects[i]);
            }
         }

         IProject[] projects = new IProject[f2Projects.size()];
         projects = (IProject[]) f2Projects.toArray(projects);

         // Do we need to handle 0 case?
         if (projects.length == 1) {
            selected = projects[0];
         } else if (projects.length > 1) {
            // Ask for selection
            ElementListSelectionDialog dlg = new ElementListSelectionDialog(
                  getShell(), new WorkbenchLabelProvider());
            dlg.setMessage(Frame2Plugin.getResourceString("BaseFrame2Wizard.selectFrame2ProjectMessage")); //$NON-NLS-1$
            dlg.setTitle(Frame2Plugin.getResourceString("BaseFrame2Wizard.selectFrame2ProjectTitle")); //$NON-NLS-1$
            dlg.setMultipleSelection(false);
            dlg.setElements(projects);
            dlg.setBlockOnOpen(true);
            int retVal = dlg.open();
            if (retVal == SWT.OK) {
               // Open file
               Object[] result = dlg.getResult();
               if (result != null) {
                  selected = (IProject) result[0];
               }
            }
         }
      }

      if (selected != null) {
         mod = loadFrame2Model(selected);
      }

      return mod;
   }

   private Frame2Model loadFrame2Model(IProject project)
         throws Frame2ModelException {
      Frame2Model mod = null;
      IResource resource = project.findMember(Frame2Plugin
            .getResourceString("NewEventWizard.fullConfigPath")); //$NON-NLS-1$
      if (resource == null) { throw new Frame2ModelException(Frame2Plugin
            .getResourceString("NewEventWizard.errorLocatingConfig")); //$NON-NLS-1$
      }
      IPath path = resource.getLocation();
      if (path == null) { throw new Frame2ModelException(Frame2Plugin
            .getResourceString("NewEventWizard.errorLocatingConfigPath")); //$NON-NLS-1$
      }
      mod = Frame2Model.getInstance(path.toFile().getAbsolutePath());
      return mod;
   }

   public Frame2Model getFrame2Model() {
      return model;
   }
}