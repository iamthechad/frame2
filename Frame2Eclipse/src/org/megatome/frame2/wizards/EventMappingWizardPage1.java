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

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.megatome.frame2.model.EventMapping;
import org.megatome.frame2.model.Forward;
import org.megatome.frame2.model.Frame2Event;
import org.megatome.frame2.model.Frame2Model;
import org.megatome.frame2.Frame2Plugin;

public class EventMappingWizardPage1 extends WizardPage {
	private Combo eventCombo;
	private Combo inputViewCombo;
    private Combo cancelViewCombo;
	private ISelection selection;
    
    private boolean badModel = false;
    
    private final String noneString = Frame2Plugin.getResourceString("EventMappingWizardPage1.noneString"); //$NON-NLS-1$

	public EventMappingWizardPage1(ISelection selection) {
		super(Frame2Plugin.getResourceString("EventMappingWizardPage1.wizardName")); //$NON-NLS-1$
		setTitle(Frame2Plugin.getResourceString("EventMappingWizardPage1.pageTitle")); //$NON-NLS-1$
		setDescription(Frame2Plugin.getResourceString("EventMappingWizardPage1.pageDescription")); //$NON-NLS-1$
		this.selection = selection;
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		Label label = new Label(container, SWT.NULL);
		label.setText(Frame2Plugin.getResourceString("EventMappingWizardPage1.eventLabel")); //$NON-NLS-1$

		eventCombo = new Combo(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        eventCombo.setLayoutData(gd);
        eventCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText(Frame2Plugin.getResourceString("EventMappingWizardPage1.inputViewLabel")); //$NON-NLS-1$

		inputViewCombo = new Combo(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        inputViewCombo.setLayoutData(gd);
        
        
        label = new Label(container, SWT.NULL);
        label.setText(Frame2Plugin.getResourceString("EventMappingWizardPage1.cancelViewLabel")); //$NON-NLS-1$

        cancelViewCombo = new Combo(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        cancelViewCombo.setLayoutData(gd);
        
		initialize();
        
        inputViewCombo.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });
        
        cancelViewCombo.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });
        
		//dialogChanged();
        setPageComplete(false);
		setControl(container);
	}

	
	private void initialize() {
        Frame2Model model = ((EventMappingWizard)getWizard()).getFrame2Model();
        
        if (model != null) {
            Frame2Event[] events = model.getEvents();
            EventMapping[] mappings = model.getEventMappings();
            for (int i = 0; i < events.length; i++) {
                boolean isEventUsed = false;
                for (int j = 0; j < mappings.length; j++) {
                    if (mappings[j].getEventName().equals(events[i].getName())) {
                        isEventUsed = true;
                        break;
                    }
                }
                
                if (!isEventUsed) {
                    eventCombo.add(events[i].getName());
                }
            }
            
            inputViewCombo.add(noneString);
            cancelViewCombo.add(noneString);
            Forward[] forwards = model.getGlobalForwards();
            for (int i =0; i < forwards.length; i++) {
                inputViewCombo.add(forwards[i].getName());
                cancelViewCombo.add(forwards[i].getName());
            }
            
            inputViewCombo.setText(noneString);
            cancelViewCombo.setText(noneString);
        } else {
            setPageComplete(false);
            badModel = true;
            dialogChanged();
        }
	}
	

	private void dialogChanged() {
        if (badModel) {
            updateStatus(Frame2Plugin.getResourceString("EventMappingWizardPage1.errorConfig")); //$NON-NLS-1$
            return;
        }
        
		String eventName = getEventName();

		if (eventName.length() == 0) {
			updateStatus(Frame2Plugin.getResourceString("EventMappingWizardPage1.errorMissingEvent")); //$NON-NLS-1$
			return;
		}
        
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getEventName() {
		return eventCombo.getText();
	}
	public String getInputView() {
        String inputView = inputViewCombo.getText();
        if (inputView.equals(noneString)) {
            return ""; //$NON-NLS-1$
        }
        
		return inputView;
	}
    public String getCancelView() {
        String cancelView = cancelViewCombo.getText();
        if (cancelView.equals(noneString)) {
            return ""; //$NON-NLS-1$
        }
        
        return cancelView;
    }

    public void dispose() {
        super.dispose();
        
        eventCombo.dispose();
        inputViewCombo.dispose();
        cancelViewCombo.dispose();
    }

}