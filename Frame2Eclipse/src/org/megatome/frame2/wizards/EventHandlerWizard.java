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
import java.util.Iterator;
import java.util.List;

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
import org.megatome.frame2.model.EventHandler;
import org.megatome.frame2.model.Forward;
import org.megatome.frame2.model.Frame2ModelException;
import org.megatome.frame2.model.InitParam;

public class EventHandlerWizard extends BaseFrame2Wizard {
	private EventHandlerWizardPage1 page;
	private EventHandlerWizardPage2 page2;

	public EventHandlerWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		this.page = new EventHandlerWizardPage1(this.selection);
		this.page2 = new EventHandlerWizardPage2(this.selection);
		addPage(this.page);
		addPage(this.page2);
	}

	@Override
	public boolean performFinish() {
		final String containerName = this.page.getPackageFragmentRootText();
		final String handlerName = this.page.getHandlerName();
		final String handlerClass = this.page.getHandlerType();
		final List<String[]> initParams = this.page.getInitParams();
		final List<String[]> localForwards = this.page2.getLocalForwards();
		final IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					doFinish(containerName, handlerName, handlerClass,
							initParams, localForwards, monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (final InterruptedException e) {
			return false;
		} catch (final InvocationTargetException e) {
			final Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), Frame2Plugin
					.getResourceString("EventHandlerWizard.ErrorTitle"), //$NON-NLS-1$
					realException.getMessage());
			return false;
		}
		return true;
	}

	void doFinish(final String containerName, final String handlerName,
			final String handlerClass, final List<String[]> initParams,
			final List<String[]> localForwards, final IProgressMonitor monitor)
			throws CoreException {
		monitor
				.beginTask(
						Frame2Plugin
								.getResourceString("EventHandlerWizard.createHandlerStatus"), 3); //$NON-NLS-1$
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		final IResource resource = root.findMember(new Path(containerName));
		if (!resource.exists() || !(resource instanceof IContainer)) {
			throwCoreException(Frame2Plugin
					.getResourceString("EventHandlerWizard.containerPre") + containerName + Frame2Plugin.getResourceString("EventHandlerWizard.containerPost")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		final IContainer container = (IContainer) resource;

		try {
			this.page.createType(monitor);
		} catch (final InterruptedException e1) {
			throwCoreException(Frame2Plugin
					.getResourceString("EventHandlerWizard.createClassError") + e1.getMessage()); //$NON-NLS-1$
		}
		final IType type = this.page.getCreatedType();
		final IPath typePath = type.getPath();
		final IPath containerPath = container.getFullPath();

		final int count = typePath.matchingFirstSegments(containerPath);
		final IPath newTypePath = typePath.removeFirstSegments(count);

		final IFile file = container.getFile(newTypePath);
		monitor.worked(1);
		monitor.setTaskName(Frame2Plugin
				.getResourceString("EventHandlerWizard.openFileStatus")); //$NON-NLS-1$
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				final IWorkbenchPage iwpage = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(iwpage, file, true);
				} catch (final PartInitException e) {
					// Ignore
				}
			}
		});
		monitor.worked(1);

		final EventHandler handler = new EventHandler();
		handler.setName(handlerName);
		handler.setType(handlerClass);

		for (final Iterator<String[]> i = initParams.iterator(); i.hasNext();) {
			final String[] param = i.next();
			final InitParam ip = new InitParam();
			ip.setName(param[0]);
			ip.setValue(param[1]);
			handler.addInitParam(ip);
		}

		for (final Iterator<String[]> i = localForwards.iterator(); i.hasNext();) {
			final String[] forward = i.next();
			final Forward f = new Forward();
			f.setName(forward[0]);
			f.setType(forward[1]);
			f.setPath(forward[2]);
			handler.addForward(f);
		}

		try {
			this.model.addEventHandler(handler);
			this.model.persistConfiguration();
		} catch (final Frame2ModelException e) {
			throwCoreException(Frame2Plugin
					.getResourceString("EventHandlerWizard.errorAddingToConfig") + e.getMessage()); //$NON-NLS-1$
		}

		monitor.worked(1);
	}
}