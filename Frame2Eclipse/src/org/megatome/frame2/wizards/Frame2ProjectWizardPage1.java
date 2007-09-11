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

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.megatome.frame2.Frame2Plugin;

public class Frame2ProjectWizardPage1 extends WizardNewProjectCreationPage {
	private Button enableServicesButton;
	private boolean canUseServices = false;

	// private ISelection selection;

	public Frame2ProjectWizardPage1(@SuppressWarnings("unused")
	final ISelection selection, boolean canUseServices) {
		super(Frame2Plugin
				.getResourceString("Frame2ProjectWizardPage1.wizardName")); //$NON-NLS-1$
		setTitle(Frame2Plugin
				.getResourceString("Frame2ProjectWizardPage1.pageTitle")); //$NON-NLS-1$
		setDescription(Frame2Plugin
				.getResourceString("Frame2ProjectWizardPage1.pageDescription")); //$NON-NLS-1$
		// this.selection = selection;
		this.canUseServices = canUseServices;
	}

	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);
		final Control c = getControl();
		final Composite container = new Composite((Composite) c, SWT.NULL);
		final GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.verticalSpacing = 10;

		this.enableServicesButton = new Button(container, SWT.CHECK);
		this.enableServicesButton
				.setText(Frame2Plugin
						.getResourceString("Frame2ProjectWizardPage1.enableWebServicesCtl")); //$NON-NLS-1$
		this.enableServicesButton.setEnabled(this.canUseServices);
	}

	public boolean enableWebServices() {
		return this.enableServicesButton.getSelection();
	}

	@Override
	public void dispose() {
		super.dispose();

		this.enableServicesButton.dispose();
	}

}