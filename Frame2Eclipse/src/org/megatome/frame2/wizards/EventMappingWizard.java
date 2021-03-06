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
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.megatome.frame2.Frame2Plugin;
import org.megatome.frame2.model.EventMapping;
import org.megatome.frame2.model.Frame2ModelException;
import org.megatome.frame2.model.Handler;
import org.megatome.frame2.model.Role;
import org.megatome.frame2.model.Security;
import org.megatome.frame2.model.View;

public class EventMappingWizard extends BaseFrame2Wizard {
	private EventMappingWizardPage1 page1;
	private EventMappingWizardPage2 page2;
	private EventMappingWizardPage3 page3;

	public EventMappingWizard() {
		super();
		setNeedsProgressMonitor(true);
	}
	
	public EventMappingWizard(final IStructuredSelection selection) {
		super(selection);
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		this.page1 = new EventMappingWizardPage1();
		this.page2 = new EventMappingWizardPage2();
		this.page3 = new EventMappingWizardPage3();
		addPage(this.page1);
		addPage(this.page2);
		addPage(this.page3);
	}
	
	@Override
	public String getFrame2WizardTitle() {
		return Frame2Plugin.getString("EventMappingWizard.windowTitle"); //$NON-NLS-1$
	}

	@Override
	public boolean performFinish() {
		final String eventName = this.page1.getEventName();
		final String inputView = this.page1.getInputView();
		final String cancelView = this.page1.getCancelView();
		final List<String> handlers = this.page2.getSelectedHandlers();
		final String htmlView = this.page3.getHTMLView();
		final String xmlView = this.page3.getXMLView();
		final List<String> roles = this.page3.getSecurityRoles();
		final IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					doFinish(eventName, inputView, cancelView, handlers,
							htmlView, xmlView, roles, monitor);
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
			MessageDialog
					.openError(
							getShell(),
							Frame2Plugin
									.getString("EventMappingWizard.ErrorTitle"), realException.getMessage()); //$NON-NLS-1$
			return false;
		}
		return true;
	}

	void doFinish(final String eventName, final String inputView,
			final String cancelView, final List<String> handlers,
			final String htmlView, final String xmlView,
			final List<String> roles, final IProgressMonitor monitor)
			throws CoreException {
		monitor
				.beginTask(
						Frame2Plugin
								.getString("EventMappingWizard.creatingMappingStatus"), 1); //$NON-NLS-1$

		final EventMapping mapping = new EventMapping();
		mapping.setEventName(eventName);

		if (inputView.length() > 0) {
			mapping.setInputView(inputView);
			mapping.setValidate(Frame2Plugin
					.getString("EventMappingWizard.true_value")); //$NON-NLS-1$
		}

		if (cancelView.length() > 0) {
			mapping.setCancelView(cancelView);
		}

		for (String handlerName : handlers) {
			final Handler handler = new Handler();
			handler.setName(handlerName);
			mapping.addHandler(handler);
		}

		if ((htmlView.length() != 0) && (xmlView.length() != 0)
				&& htmlView.equals(xmlView)) {
			final View view = new View();
			view.setForwardName(htmlView);
			view.setType(Frame2Plugin
					.getString("EventMappingWizard.both_type")); //$NON-NLS-1$
			mapping.addView(view);
		} else {
			if (htmlView.length() != 0) {
				final View view = new View();
				view.setForwardName(htmlView);
				view.setType(Frame2Plugin
						.getString("EventMappingWizard.html_type")); //$NON-NLS-1$
				mapping.addView(view);
			}
			if (xmlView.length() != 0) {
				final View view = new View();
				view.setForwardName(xmlView);
				view.setType(Frame2Plugin
						.getString("EventMappingWizard.xml_type")); //$NON-NLS-1$
				mapping.addView(view);
			}
		}

		if (roles.size() > 0) {
			final Security security = new Security();
			for (String roleName : roles) {
				final Role role = new Role();
				role.setName(roleName);
				security.addRole(role);
			}
			mapping.setSecurity(security);
		}

		try {
			this.getFrame2Model().addEventMapping(mapping);
			this.persistModel(monitor);
		} catch (final Frame2ModelException e) {
			throwCoreException(Frame2Plugin
					.getString("EventMappingWizard.createMappingError") + e.getMessage()); //$NON-NLS-1$
		}

		monitor.worked(1);
	}
}