/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004 Megatome Technologies.  All rights
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
/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.megatome.frame2.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.megatome.frame2.Frame2Plugin;
import org.megatome.frame2.model.Forward;
import org.megatome.frame2.model.Frame2Model;
import org.megatome.frame2.model.Frame2ModelException;


public class GlobalForwardWizard extends BaseFrame2Wizard /*Wizard implements INewWizard*/ {
	private GlobalForwardWizardPage1 page;
	//private ISelection selection;
    
    //private Frame2Model model;

	public GlobalForwardWizard() {
		super();
		setNeedsProgressMonitor(true);
	}
    
	public void addPages() {
		page = new GlobalForwardWizardPage1(selection);
		addPage(page);
	}

	public boolean performFinish() {
		final String forwardName = page.getForwardName();
		final String forwardType = page.getForwardType();
        final String forwardPath = page.getForwardPath();
        
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
                    monitor.beginTask(Frame2Plugin.getResourceString("GlobalForwardWizard.creatingStatus"), 1); //$NON-NLS-1$
                    Frame2Model instance = Frame2Model.getInstance();
                    if (instance != null) {
                        Forward forward = new Forward();
                        forward.setName(forwardName);
                        forward.setType(forwardType);
                        forward.setPath(forwardPath);

                        try {
                            instance.addGlobalForward(forward);
                            instance.persistConfiguration();
                        } catch (Frame2ModelException e) {
                            throw new InvocationTargetException(e);
                        }
                    }
                    monitor.worked(1);
				} /*catch (CoreException e) {
					throw new InvocationTargetException(e);
				} */finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), Frame2Plugin.getResourceString("GlobalForwardWizard.ErrorTitle"), realException.getMessage()); //$NON-NLS-1$
			return false;
		}
		return true;
	}
/*
	private void throwCoreException(String message) throws CoreException {
		IStatus status =
			new Status(IStatus.ERROR, "org.megatome.frame2", IStatus.OK, message, null); //$NON-NLS-1$
		throw new CoreException(status);
	}

    private Frame2Model initFrame2Model(IStructuredSelection selection) throws Frame2ModelException {
        Frame2Model mod = null;
        if (selection != null && selection.isEmpty() == false) {
            if (selection.size() > 1) {
                throw new Frame2ModelException(Frame2Plugin.getResourceString("GlobalForwardWizard.errorMultSelection")); //$NON-NLS-1$
            }
                
            Object obj = selection.getFirstElement();
            if (obj instanceof IResource) {
                IProject rootProject = ((IResource)obj).getProject();

                IResource resource =
                    rootProject.findMember(Frame2Plugin.getResourceString("GlobalForwardWizard.fullFrame2ConfigLocation")); //$NON-NLS-1$
                if (resource == null) {
                    throw new Frame2ModelException(Frame2Plugin.getResourceString("GlobalForwardWizard.ErrorNoConfigFound")); //$NON-NLS-1$
                }
                IPath path = resource.getLocation();
                if (path == null) {
                    throw new Frame2ModelException(Frame2Plugin.getResourceString("GlobalForwardWizard.ConfigFindError")); //$NON-NLS-1$
                }
                mod = Frame2Model.getInstance(path.toFile().getAbsolutePath());
            }
        }
        
        return mod;
    }
    
    public Frame2Model getFrame2Model() {
        return model;
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
            MultiStatus info = new MultiStatus("org.megatome.frame2", Status.ERROR, Frame2Plugin.getResourceString("GlobalForwardWizard.ConfigReadError"), e);  //$NON-NLS-1$//$NON-NLS-2$
            Status msg = new Status(Status.ERROR, "org.megatome.frame2", Status.ERROR, errorMsg, e); //$NON-NLS-1$
            info.add(msg);
            
            ErrorDialog.openError(getShell(), Frame2Plugin.getResourceString("GlobalForwardWizard.wizardInitError"), null, info);             //$NON-NLS-1$
        }           
	}
*/
}