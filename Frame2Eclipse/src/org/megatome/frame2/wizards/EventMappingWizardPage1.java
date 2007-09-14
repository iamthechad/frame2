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

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.megatome.frame2.Frame2Plugin;
import org.megatome.frame2.model.EventMapping;
import org.megatome.frame2.model.Forward;
import org.megatome.frame2.model.Frame2Event;
import org.megatome.frame2.model.Frame2Model;

public class EventMappingWizardPage1 extends WizardPage {
	private Combo eventCombo;
	private Combo inputViewCombo;
	private Combo cancelViewCombo;

	private boolean badModel = false;

	private final String noneString = Frame2Plugin
			.getResourceString("EventMappingWizardPage1.noneString"); //$NON-NLS-1$

	public EventMappingWizardPage1() {
		super(Frame2Plugin
				.getResourceString("EventMappingWizardPage1.wizardName")); //$NON-NLS-1$
		setTitle(Frame2Plugin
				.getResourceString("EventMappingWizardPage1.pageTitle")); //$NON-NLS-1$
		setDescription(Frame2Plugin
				.getResourceString("EventMappingWizardPage1.pageDescription")); //$NON-NLS-1$
	}

	public void createControl(final Composite parent) {
		final Composite container = new Composite(parent, SWT.NULL);
		final GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		Label label = new Label(container, SWT.NULL);
		label.setText(Frame2Plugin
				.getResourceString("EventMappingWizardPage1.eventLabel")); //$NON-NLS-1$

		this.eventCombo = new Combo(container, SWT.BORDER | SWT.SINGLE
				| SWT.READ_ONLY);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		this.eventCombo.setLayoutData(gd);
		this.eventCombo.addModifyListener(new ModifyListener() {
			public void modifyText(@SuppressWarnings("unused")
			final ModifyEvent e) {
				dialogChanged();
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText(Frame2Plugin
				.getResourceString("EventMappingWizardPage1.inputViewLabel")); //$NON-NLS-1$

		this.inputViewCombo = new Combo(container, SWT.BORDER | SWT.SINGLE
				| SWT.READ_ONLY);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		this.inputViewCombo.setLayoutData(gd);

		label = new Label(container, SWT.NULL);
		label.setText(Frame2Plugin
				.getResourceString("EventMappingWizardPage1.cancelViewLabel")); //$NON-NLS-1$

		this.cancelViewCombo = new Combo(container, SWT.BORDER | SWT.SINGLE
				| SWT.READ_ONLY);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		this.cancelViewCombo.setLayoutData(gd);

		initialize();

		this.inputViewCombo.addModifyListener(new ModifyListener() {
			public void modifyText(@SuppressWarnings("unused")
			final ModifyEvent e) {
				dialogChanged();
			}
		});

		this.cancelViewCombo.addModifyListener(new ModifyListener() {
			public void modifyText(@SuppressWarnings("unused")
			final ModifyEvent e) {
				dialogChanged();
			}
		});

		// dialogChanged();
		setPageComplete(false);
		setControl(container);
	}

	private void initialize() {
		final Frame2Model model = ((EventMappingWizard) getWizard())
				.getFrame2Model();

		if (model != null) {
			final Frame2Event[] events = model.getEvents();
			final EventMapping[] mappings = model.getEventMappings();
			for (int i = 0; i < events.length; i++) {
				boolean isEventUsed = false;
				for (int j = 0; j < mappings.length; j++) {
					if (mappings[j].getEventName().equals(events[i].getName())) {
						isEventUsed = true;
						break;
					}
				}

				if (!isEventUsed) {
					this.eventCombo.add(events[i].getName());
				}
			}

			this.inputViewCombo.add(this.noneString);
			this.cancelViewCombo.add(this.noneString);
			final Forward[] forwards = model.getGlobalForwards();
			for (int i = 0; i < forwards.length; i++) {
				this.inputViewCombo.add(forwards[i].getName());
				this.cancelViewCombo.add(forwards[i].getName());
			}

			this.inputViewCombo.setText(this.noneString);
			this.cancelViewCombo.setText(this.noneString);
		} else {
			setPageComplete(false);
			this.badModel = true;
			dialogChanged();
		}
	}

	void dialogChanged() {
		if (this.badModel) {
			updateStatus(Frame2Plugin
					.getResourceString("EventMappingWizardPage1.errorConfig")); //$NON-NLS-1$
			return;
		}

		final String eventName = getEventName();

		if (eventName.length() == 0) {
			updateStatus(Frame2Plugin
					.getResourceString("EventMappingWizardPage1.errorMissingEvent")); //$NON-NLS-1$
			return;
		}

		updateStatus(null);
	}

	private void updateStatus(final String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getEventName() {
		return this.eventCombo.getText();
	}

	public String getInputView() {
		final String inputView = this.inputViewCombo.getText();
		if (inputView.equals(this.noneString)) {
			return ""; //$NON-NLS-1$
		}

		return inputView;
	}

	public String getCancelView() {
		final String cancelView = this.cancelViewCombo.getText();
		if (cancelView.equals(this.noneString)) {
			return ""; //$NON-NLS-1$
		}

		return cancelView;
	}

	@Override
	public void dispose() {
		super.dispose();

		this.eventCombo.dispose();
		this.inputViewCombo.dispose();
		this.cancelViewCombo.dispose();
	}

}