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

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.megatome.frame2.Frame2Plugin;
import org.megatome.frame2.model.Frame2Event;
import org.megatome.frame2.model.Frame2ModelException;


public class NewEventWizard extends BaseFrame2Wizard  {
    private NewEventWizardPage1 page;

    public NewEventWizard() {
        super();
        setNeedsProgressMonitor(true);
    }

    public void addPages() {
        page = new NewEventWizardPage1(selection);
        addPage(page);
    }

    public boolean performFinish() {
        final String containerName = page.getPackageFragmentRootText();
        final String eventName = page.getEventName();
        final String eventType = page.getEventClassType();
        final String newEventType = page.getNewEventType();
        //final String fileName = page.getFileName();
        IRunnableWithProgress op = new IRunnableWithProgress() {
            public void run(IProgressMonitor monitor)
                throws InvocationTargetException {
                try {
                    doFinish(
                        containerName,
                        eventName,
                        eventType,
                        newEventType,
                        monitor);
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
            MessageDialog.openError(
                getShell(),
                Frame2Plugin.getResourceString("NewEventWizard.ErrorTitle"), //$NON-NLS-1$
                realException.getMessage());
            return false;
        }
        return true;
    }


    private void doFinish(
        String containerName,
        String eventName,
        String eventType,
        String newEventType,
        IProgressMonitor monitor)
        throws CoreException {

        if (newEventType.equals(NewEventWizardPage1.NEW_CLASS)) {
            // create a sample file
            monitor.beginTask(Frame2Plugin.getResourceString("NewEventWizard.creatingEventStatus"), 3); //$NON-NLS-1$
            IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
            IResource resource = root.findMember(new Path(containerName));
            if (!resource.exists() || !(resource instanceof IContainer)) {
                throwCoreException(
                    Frame2Plugin.getResourceString("NewEventWizard.containerMessagePre") + containerName + Frame2Plugin.getResourceString("NewEventWizard.containerMessagePost")); //$NON-NLS-1$ //$NON-NLS-2$
            }
            IContainer container = (IContainer)resource;

            try {
                page.createType(monitor);
            } catch (InterruptedException e1) {
                throwCoreException(
                    Frame2Plugin.getResourceString("NewEventWizard.classCreateError") + e1.getMessage()); //$NON-NLS-1$
            }
            IType type = page.getCreatedType();
            IPath typePath = type.getPath();
            IPath containerPath = container.getFullPath();
            
            int count = typePath.matchingFirstSegments(containerPath);
            IPath newTypePath = typePath.removeFirstSegments(count);
                        
            final IFile file = container.getFile(newTypePath);
            monitor.worked(1);
            monitor.setTaskName(Frame2Plugin.getResourceString("NewEventWizard.openingFileStatus")); //$NON-NLS-1$
            getShell().getDisplay().asyncExec(new Runnable() {
                public void run() {
                    IWorkbenchPage page =
                        PlatformUI
                            .getWorkbench()
                            .getActiveWorkbenchWindow()
                            .getActivePage();
                    try {
                       IDE.openEditor(page, file, true);
                    } catch (PartInitException e) {}
                }
            });
            monitor.worked(1);
        } else {
            monitor.beginTask(Frame2Plugin.getResourceString("NewEventWizard.addToConfigStatus"), 1); //$NON-NLS-1$
        }

        Frame2Event event = new Frame2Event();
        event.setName(eventName);
        if (!newEventType.equals(NewEventWizardPage1.NO_CLASS))
            event.setType(eventType);

        try {
            model.addEvent(event);
            model.persistConfiguration();
        } catch (Frame2ModelException e) {
            throwCoreException(
                Frame2Plugin.getResourceString("NewEventWizard.addToConfigError") + e.getMessage()); //$NON-NLS-1$
        }

        monitor.worked(1);
    }
}