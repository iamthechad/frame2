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



import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.megatome.frame2.model.Frame2Exception;
import org.megatome.frame2.model.Frame2Model;
import org.megatome.frame2.model.Frame2ModelException;
import org.megatome.frame2.model.View;

public class ExceptionWizard extends Wizard implements INewWizard {
	private ExceptionWizardPage1 page;
	private ISelection selection;
    
    private Frame2Model model = null;

	public ExceptionWizard() {
		super();
		setNeedsProgressMonitor(true);
	}
	
	public void addPages() {
		page = new ExceptionWizardPage1(selection);
		addPage(page);
	}

	public boolean performFinish() {
		final String requestKey = page.getRequestKey();
		final String exceptionType = page.getExceptionType();
        final String htmlView = page.getHTMLView();
        final String xmlView = page.getXMLView();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(requestKey, exceptionType, htmlView, xmlView, monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
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
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}
		return true;
	}
	
	private void doFinish(
		String requestKey,
		String exceptionType,
        String htmlView,
        String xmlView,
		IProgressMonitor monitor)
		throws CoreException {
		// create a sample file
		monitor.beginTask("Creating Frame2 Exception Entry", 1);
        
        Frame2Exception exception = new Frame2Exception();
        exception.setRequestKey(requestKey);
        exception.setType(exceptionType);
        
        if ((htmlView.length() != 0) && 
            (xmlView.length() != 0) && 
            htmlView.equals(xmlView)) {
            View view = new View();
            view.setForwardName(htmlView);
            view.setType("Both");
            exception.addView(view);
        } else {
            if (htmlView.length() != 0) {
                View view = new View();
                view.setForwardName(htmlView);
                view.setType("HTML");
                exception.addView(view);
            } 
            if (xmlView.length() != 0) {
                View view = new View();
                view.setForwardName(xmlView);
                view.setType("XML");
                exception.addView(view);
            }
        }
        
        try {
            model.addFrame2Exception(exception);
            model.persistConfiguration();
        } catch (Frame2ModelException e) {
            throwCoreException("Failed to create Frame2 Exception: " + e.getMessage());
        }
        
		monitor.worked(1);
	}
	
	private void throwCoreException(String message) throws CoreException {
		IStatus status =
			new Status(IStatus.ERROR, "org.megatome.frame2", IStatus.OK, message, null);
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
            MultiStatus info = new MultiStatus("org.megatome.frame2", Status.ERROR, "There was an error reading the Frame2 configuration file to initialize the wizard.", e);
            Status msg = new Status(Status.ERROR, "org.megatome.frame2", Status.ERROR, errorMsg, e);
            info.add(msg);
            
            ErrorDialog.openError(getShell(), "Error Initializing Wizard", null, info);            
        }  
	}
    
    private Frame2Model initFrame2Model(IStructuredSelection selection) throws Frame2ModelException {
        Frame2Model mod = null;
        if (selection != null && selection.isEmpty() == false) {
            if (selection.size() > 1) {
                throw new Frame2ModelException("Cannot operate wizard on multiple selections");
            }
                
            Object obj = selection.getFirstElement();
            if (obj instanceof IResource) {
                IProject rootProject = ((IResource)obj).getProject();

                IResource resource =
                    rootProject.findMember("WEB-INF/frame2-config.xml");
                if (resource == null) {
                    throw new Frame2ModelException("Could not locate a Frame2 configuration file");
                }
                IPath path = resource.getLocation();
                if (path == null) {
                    throw new Frame2ModelException("Could not find the path for a Frame2 configuration file");
                }
                mod = Frame2Model.getInstance(path.toFile().getAbsolutePath());
            }
        }
        
        return mod;
    }
    
    public Frame2Model getFrame2Model() {
        return model;
    }
}