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
import org.eclipse.swt.widgets.Text;
import org.megatome.frame2.model.Forward;
import org.megatome.frame2.model.Frame2Exception;
import org.megatome.frame2.model.Frame2Model;
import org.megatome.frame2.Frame2Plugin;


public class ExceptionWizardPage1 extends WizardPage {
	private Text requestKeyText;
	private Text typeText;
    private Combo htmlViewCombo;
    private Combo xmlViewCombo;
    private ISelection selection;
    
    private boolean badModel = false;
    
    private Frame2Exception[] definedExceptions = new Frame2Exception[0];
    
    private static String noneString = Frame2Plugin.getResourceString("ExceptionWizardPage1.noneString"); //$NON-NLS-1$

	public ExceptionWizardPage1(ISelection selection) {
		super(Frame2Plugin.getResourceString("ExceptionWizardPage1.wizardName")); //$NON-NLS-1$
		setTitle(Frame2Plugin.getResourceString("ExceptionWizardPage1.pageTitle")); //$NON-NLS-1$
		setDescription(Frame2Plugin.getResourceString("ExceptionWizardPage1.pageDescription")); //$NON-NLS-1$
		this.selection = selection;
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.verticalSpacing = 9;
		Label label = new Label(container, SWT.NULL);
		label.setText(Frame2Plugin.getResourceString("ExceptionWizardPage1.requestKeyLabel")); //$NON-NLS-1$

		requestKeyText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        requestKeyText.setLayoutData(gd);
        requestKeyText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText(Frame2Plugin.getResourceString("ExceptionWizardPage1.exceptionTypeLabel")); //$NON-NLS-1$

		typeText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		typeText.setLayoutData(gd);
		typeText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
        
        label = new Label(container, SWT.NULL);
        label.setText(Frame2Plugin.getResourceString("ExceptionWizardPage1.htmlViewLabel")); //$NON-NLS-1$

        htmlViewCombo = new Combo(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        htmlViewCombo.setLayoutData(gd);
                
        label = new Label(container, SWT.NULL);
        label.setText(Frame2Plugin.getResourceString("ExceptionWizardPage1.xmlViewLabel")); //$NON-NLS-1$

        xmlViewCombo = new Combo(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        xmlViewCombo.setLayoutData(gd);
        
		initialize();
		//dialogChanged();
        setPageComplete(false);
        htmlViewCombo.setText(noneString);
        xmlViewCombo.setText(noneString);
        
        htmlViewCombo.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });
        
        xmlViewCombo.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });
        
        requestKeyText.setFocus();
        
		setControl(container);
	}
	
	
	private void initialize() {
        Frame2Model model = ((ExceptionWizard)getWizard()).getFrame2Model();
        
        if (model != null) {
            htmlViewCombo.add(noneString);
            xmlViewCombo.add(noneString);
            
            Forward[] forwards = model.getGlobalForwards();
            for (int i =0; i < forwards.length; i++) {
                String forwardType = forwards[i].getType();
                if (forwardType.equals(Frame2Plugin.getResourceString("ExceptionWizardPage1.htmlResource_type"))) { //$NON-NLS-1$
                    htmlViewCombo.add(forwards[i].getName());
                } else if (forwardType.equals(Frame2Plugin.getResourceString("ExceptionWizardPage1.xmlResource_type")) || //$NON-NLS-1$
                           forwardType.equals(Frame2Plugin.getResourceString("ExceptionWizardPage1.xmlResponse_type"))) { //$NON-NLS-1$
                    xmlViewCombo.add(forwards[i].getName());
                } else if (forwardType.equals(Frame2Plugin.getResourceString("ExceptionWizardPage1.event_internal_type"))) { //$NON-NLS-1$
                    htmlViewCombo.add(forwards[i].getName());
                    xmlViewCombo.add(forwards[i].getName());
                }
            }
            
            htmlViewCombo.setText(noneString);
            xmlViewCombo.setText(noneString);
            
            definedExceptions = model.getExceptions();
        } else {
            setPageComplete(false);
            badModel = true;
            dialogChanged();
        }
	}
	
	private void dialogChanged() {
        if (badModel) {
            updateStatus(Frame2Plugin.getResourceString("ExceptionWizardPage1.errorConfig")); //$NON-NLS-1$
            return;
        }
        
		String requestKey = getRequestKey();
		String exceptionType = getExceptionType();
        String htmlView = getHTMLView();
        String xmlView = getXMLView();

        
        
		if (requestKey.length() == 0) {
			updateStatus(Frame2Plugin.getResourceString("ExceptionWizardPage1.errorMissingRequestKey")); //$NON-NLS-1$
			return;
		}
		if (exceptionType.length() == 0) {
			updateStatus(Frame2Plugin.getResourceString("ExceptionWizardPage1.errorMissingExceptionType")); //$NON-NLS-1$
			return;
		}
        if (isDuplicateType()) {
            updateStatus(Frame2Plugin.getResourceString("ExceptionWizardPage1.errorDuplicateHandler")); //$NON-NLS-1$
            return;
        }
		
        if ((htmlView.length() == 0) &&
           (xmlView.length() == 0)) {
               updateStatus(Frame2Plugin.getResourceString("ExceptionWizardPage1.errorMissingView")); //$NON-NLS-1$
               return;
        }
        
		updateStatus(null);
	}
    
    private boolean isDuplicateType() {
        String exceptionType = getExceptionType();
        
        for (int i = 0; i < definedExceptions.length; i++) {
            if (definedExceptions[i].getType().equals(exceptionType)) {
                return true;
            }
        }
        
        return false;
    }

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getRequestKey() {
		return requestKeyText.getText();
	}
	public String getExceptionType() {
		return typeText.getText();
	}
    public String getHTMLView() {
        String viewText = htmlViewCombo.getText();
        if (viewText.equals(noneString)) {
            return ""; //$NON-NLS-1$
        }
        
        return viewText;
    }
    public String getXMLView() {
        String viewText = xmlViewCombo.getText();
        if (viewText.equals(noneString)) {
            return ""; //$NON-NLS-1$
        }
    
        return viewText;
    }

    public void dispose() {
        super.dispose();
        
        requestKeyText.dispose();
        typeText.dispose();
        htmlViewCombo.dispose();
        xmlViewCombo.dispose();
    }

}