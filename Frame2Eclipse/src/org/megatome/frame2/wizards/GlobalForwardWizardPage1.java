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

import java.util.ArrayList;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ResourceListSelectionDialog;
import org.megatome.frame2.model.Forward;
import org.megatome.frame2.model.Frame2Event;
import org.megatome.frame2.model.Frame2Model;
import org.megatome.frame2.Frame2Plugin;


public class GlobalForwardWizardPage1 extends WizardPage {
    private Text nameText;
    private Text htmlResourceText;
    private Text xmlResourceText;
    private Text xmlResponderText;
    private Label nameLabel;
    private Label typeLabel;
    private Label htmlResourceLabel;
    private Label xmlResourceLabel;
    private Label xmlResponderLabel;
    private Label eventLabel;
    private Combo eventCombo;

    private Button htmlResourceRadio;
    private Button xmlResourceRadio;
    private Button xmlResponderRadio;
    private Button eventRadio;

    private Button htmlResourceBrowse;

    private SelectionListener radioListener;

    private ISelection selection;
    private IProject rootProject;
    
    private boolean badModel = false;

     public GlobalForwardWizardPage1(ISelection selection) {
        super(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.wizardName")); //$NON-NLS-1$
        setTitle(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.pageTitle")); //$NON-NLS-1$
        setDescription(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.pageDescription")); //$NON-NLS-1$
        this.selection = selection;
    }

    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout();
        container.setLayout(layout);
        layout.numColumns = 3;
        layout.verticalSpacing = 9;
        nameLabel = new Label(container, SWT.NULL);
        nameLabel.setText(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.nameCtl")); //$NON-NLS-1$

        nameText = new Text(container, SWT.BORDER | SWT.SINGLE);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        nameText.setLayoutData(gd);
        nameText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });

        nameLabel.setEnabled(false);
        nameText.setEnabled(false);

        typeLabel = new Label(container, SWT.NULL);
        typeLabel.setText(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.typeCtl")); //$NON-NLS-1$

        typeLabel.setEnabled(false);

        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 3;
        typeLabel.setLayoutData(gd);

        htmlResourceRadio = new Button(container, SWT.RADIO);
        htmlResourceRadio.setText(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.htmlResource_type")); //$NON-NLS-1$
        htmlResourceRadio.setSelection(true);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 3;
        htmlResourceRadio.setLayoutData(gd);
        htmlResourceRadio.addSelectionListener(getRadioListener());

        htmlResourceLabel = new Label(container, SWT.NULL);
        htmlResourceLabel.setText(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.pathCtl")); //$NON-NLS-1$

        htmlResourceText = new Text(container, SWT.BORDER | SWT.SINGLE);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        htmlResourceText.setLayoutData(gd);
        htmlResourceText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });

        htmlResourceBrowse = new Button(container, SWT.PUSH);
        htmlResourceBrowse.setText(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.browseCtl")); //$NON-NLS-1$
        gd = new GridData(GridData.END);
        htmlResourceBrowse.setLayoutData(gd);
        htmlResourceBrowse.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                handleBrowse();
            }
        });

        htmlResourceRadio.setEnabled(false);
        htmlResourceLabel.setEnabled(false);
        htmlResourceText.setEnabled(false);
        htmlResourceBrowse.setEnabled(false);

        xmlResourceRadio = new Button(container, SWT.RADIO);
        xmlResourceRadio.setText(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.xmlResource_type")); //$NON-NLS-1$
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 3;
        xmlResourceRadio.setLayoutData(gd);
        xmlResourceRadio.addSelectionListener(getRadioListener());

        xmlResourceLabel = new Label(container, SWT.NULL);
        xmlResourceLabel.setText(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.pathCtl")); //$NON-NLS-1$

        xmlResourceText = new Text(container, SWT.BORDER | SWT.SINGLE);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        xmlResourceText.setLayoutData(gd);
        xmlResourceText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });

        xmlResourceRadio.setEnabled(false);
        xmlResourceLabel.setEnabled(false);
        xmlResourceText.setEnabled(false);

        xmlResponderRadio = new Button(container, SWT.RADIO);
        xmlResponderRadio.setText(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.xmlResponder_type")); //$NON-NLS-1$
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 3;
        xmlResponderRadio.setLayoutData(gd);
        xmlResponderRadio.addSelectionListener(getRadioListener());

        xmlResponderLabel = new Label(container, SWT.NULL);
        xmlResponderLabel.setText(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.pathCtl")); //$NON-NLS-1$

        xmlResponderText = new Text(container, SWT.BORDER | SWT.SINGLE);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        xmlResponderText.setLayoutData(gd);
        xmlResponderText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });

        xmlResponderRadio.setEnabled(false);
        xmlResponderLabel.setEnabled(false);
        xmlResponderText.setEnabled(false);

        eventRadio = new Button(container, SWT.RADIO);
        eventRadio.setText(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.event_type")); //$NON-NLS-1$
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 3;
        eventRadio.setLayoutData(gd);
        eventRadio.addSelectionListener(getRadioListener());

        eventLabel = new Label(container, SWT.NULL);
        eventLabel.setText(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.pathCtl")); //$NON-NLS-1$

        eventCombo = new Combo(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        eventCombo.setLayoutData(gd);
        eventCombo.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });

        eventRadio.setEnabled(false);
        eventLabel.setEnabled(false);
        eventCombo.setEnabled(false);

        initialize();
        //dialogChanged();
        setPageComplete(false);
        setControl(container);
    }

    private void initialize() {
        Frame2Model model = ((GlobalForwardWizard)getWizard()).getFrame2Model();
        
        if (model != null) {
            nameLabel.setEnabled(true);
            nameText.setEnabled(true);

            typeLabel.setEnabled(true);
            htmlResourceRadio.setEnabled(true);
            xmlResourceRadio.setEnabled(true);
            xmlResponderRadio.setEnabled(true);
            eventRadio.setEnabled(true);

            htmlResourceText.setEnabled(true);
            htmlResourceBrowse.setEnabled(true);

            Frame2Event[] events = model.getEvents();
            for (int i = 0; i < events.length; i++) {
                eventCombo.add(events[i].getName());
            }
        } else {
            setPageComplete(false);
            badModel = true;
            dialogChanged();
        }
        
        if (selection != null && selection.isEmpty() == false
        && selection instanceof IStructuredSelection) {
            IStructuredSelection ssel = (IStructuredSelection)selection;
            if (ssel.size() > 1)
                return;
                
            Object obj = ssel.getFirstElement();
            if (obj instanceof IResource) {
                rootProject = ((IResource)obj).getProject();
            }
        }
    }

    private void handleBrowse() {
        IResource[] forwardElements = findForwardResources();
        ResourceListSelectionDialog dialog =
            new ResourceListSelectionDialog(
                getShell(),
                forwardElements);
        dialog.setInitialSelections(forwardElements);
        if (dialog.open() == ResourceListSelectionDialog.OK) {
            Object[] results = dialog.getResult();
            if (results.length == 1) {
                htmlResourceText.setText("/" + ((IFile)results[0]).getProjectRelativePath().toString()); //$NON-NLS-1$
                dialogChanged();
            }
        }
    }
    
    private IResource[] findForwardResources() {
        ArrayList filteredResources = new ArrayList();
        
        filterResources(rootProject, filteredResources);
        
        IResource[] allFiltered = new IResource[0];
        allFiltered = (IResource[])filteredResources.toArray(allFiltered);
        
        return allFiltered;
    }
    
    private void filterResources(IContainer container, ArrayList resourceList) {
        IResource[] members;
        try {
            members = container.members();
        } catch (CoreException e) {
            return;
        }
        
        for (int i = 0; i < members.length; i++) {
            if (members[i] instanceof IContainer) {
                filterResources((IContainer)members[i], resourceList);
            }
            
            String fileExt = members[i].getFileExtension();
            if ((fileExt != null) && 
               (fileExt.equalsIgnoreCase(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.jsp_file_type")) || //$NON-NLS-1$
                fileExt.equalsIgnoreCase(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.htm_file_type")) || //$NON-NLS-1$
                fileExt.equalsIgnoreCase(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.html_file_type")))) { //$NON-NLS-1$
                    resourceList.add(members[i]);
                } 
        }
    }

    private SelectionListener getRadioListener() {
        if (radioListener == null) {
            radioListener = new SelectionListener() {
                public void widgetDefaultSelected(SelectionEvent e) {}
                public void widgetSelected(SelectionEvent e) {
                    Button source = (Button)e.getSource();
                    String buttonText = source.getText();

                    if (buttonText.equals(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.htmlResource_type"))) { //$NON-NLS-1$
                        htmlResourceLabel.setEnabled(true);
                        htmlResourceText.setEnabled(true);
                        htmlResourceBrowse.setEnabled(true);

                        xmlResourceLabel.setEnabled(false);
                        xmlResourceText.setEnabled(false);

                        xmlResponderLabel.setEnabled(false);
                        xmlResponderText.setEnabled(false);

                        eventLabel.setEnabled(false);
                        eventCombo.setEnabled(false);
                    } else if (buttonText.equals(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.xmlResource_type"))) { //$NON-NLS-1$
                        htmlResourceLabel.setEnabled(false);
                        htmlResourceText.setEnabled(false);
                        htmlResourceBrowse.setEnabled(false);

                        xmlResourceLabel.setEnabled(true);
                        xmlResourceText.setEnabled(true);

                        xmlResponderLabel.setEnabled(false);
                        xmlResponderText.setEnabled(false);

                        eventLabel.setEnabled(false);
                        eventCombo.setEnabled(false);
                    } else if (buttonText.equals(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.xmlResponder_type"))) { //$NON-NLS-1$
                        htmlResourceLabel.setEnabled(false);
                        htmlResourceText.setEnabled(false);
                        htmlResourceBrowse.setEnabled(false);

                        xmlResourceLabel.setEnabled(false);
                        xmlResourceText.setEnabled(false);

                        xmlResponderLabel.setEnabled(true);
                        xmlResponderText.setEnabled(true);

                        eventLabel.setEnabled(false);
                        eventCombo.setEnabled(false);
                    } else if (buttonText.equals(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.event_type"))) { //$NON-NLS-1$
                        htmlResourceLabel.setEnabled(false);
                        htmlResourceText.setEnabled(false);
                        htmlResourceBrowse.setEnabled(false);

                        xmlResourceLabel.setEnabled(false);
                        xmlResourceText.setEnabled(false);

                        xmlResponderLabel.setEnabled(false);
                        xmlResponderText.setEnabled(false);

                        eventLabel.setEnabled(true);
                        eventCombo.setEnabled(true);
                    }

                    dialogChanged();
                }
            };
        }

        return radioListener;
    }


    private void dialogChanged() {
        if (badModel) {
            updateStatus(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.configError")); //$NON-NLS-1$
            return;
        }
        
        String name = getForwardName();
        String type = getForwardType();
        String path = getForwardPath();

        if (name.length() == 0) {
            updateStatus(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.missingNameMessage")); //$NON-NLS-1$
            return;
        }
        if (type.length() == 0) {
            updateStatus(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.missingTypeMessage")); //$NON-NLS-1$
            return;
        }
        if (path.length() == 0) {
            updateStatus(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.missingPathMessage")); //$NON-NLS-1$
            return;
        }

        if (!validateForwardName()) {
            return;
        }
        
        if (!validateForwardPath()) {
            return;
        }

        updateStatus(null);
    }

    private void updateStatus(String message) {
        setErrorMessage(message);
        setPageComplete(message == null);
    }

    public String getForwardName() {
        return nameText.getText();
    }
    public String getForwardType() {
        if (htmlResourceRadio.getSelection()) {
            return Frame2Plugin.getResourceString("GlobalForwardWizardPage1.htmlResource_type"); //$NON-NLS-1$
        } else if (xmlResourceRadio.getSelection()) {
            return Frame2Plugin.getResourceString("GlobalForwardWizardPage1.xmlResource_type"); //$NON-NLS-1$
        } else if (xmlResponderRadio.getSelection()) {
            return Frame2Plugin.getResourceString("GlobalForwardWizardPage1.xmlResponder_type"); //$NON-NLS-1$
        } else if (eventRadio.getSelection()) {
            return Frame2Plugin.getResourceString("GlobalForwardWizardPage1.event_internal_type"); //$NON-NLS-1$
        }

        return ""; //$NON-NLS-1$
    }
    public String getForwardPath() {
        if (htmlResourceRadio.getSelection()) {
            return htmlResourceText.getText();
        } else if (xmlResourceRadio.getSelection()) {
            return xmlResourceText.getText();
        } else if (xmlResponderRadio.getSelection()) {
            return xmlResponderText.getText();
        } else if (eventRadio.getSelection()) {
            return eventCombo.getText();
        }

        return ""; //$NON-NLS-1$
    }

    private boolean validateForwardName() {
        String forwardName = getForwardName();
        Forward[] allForwards = ((GlobalForwardWizard)getWizard()).getFrame2Model().getGlobalForwards();
        for (int i = 0; i < allForwards.length; i++) {
            if (forwardName.equals(allForwards[i].getName())) {
                updateStatus(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.errorDuplicateName")); //$NON-NLS-1$
                return false;
            }
        }
        
        return true;
    }

    private boolean validateForwardPath() {
        String forwardType = getForwardType();
        String forwardPath = getForwardPath();
        
        if (forwardType.equals(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.htmlResource_type"))) { //$NON-NLS-1$
            
            if (forwardPath.indexOf(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.uriPrefix")) != -1) { //$NON-NLS-1$
                return true;
            }
            
            IResource filePath = rootProject.findMember(forwardPath);
            if (filePath != null) {
                if (!filePath.exists() || (!(filePath instanceof IFile))) {
                    updateStatus(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.missingForwardFile")); //$NON-NLS-1$
                    return false;
                }
            } else {
                updateStatus(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.missingForwardFile")); //$NON-NLS-1$
                return false;
            }
                
        }
        
        return true;
    }

    public void dispose() {
        super.dispose();
        nameText.dispose();
        htmlResourceText.dispose();
        xmlResourceText.dispose();
        xmlResponderText.dispose();
        nameLabel.dispose();
        typeLabel.dispose();
        htmlResourceLabel.dispose();
        xmlResourceLabel.dispose();
        xmlResponderLabel.dispose();
        eventLabel.dispose();
        eventCombo.dispose();
    
        htmlResourceRadio.dispose();
        xmlResourceRadio.dispose();
        xmlResponderRadio.dispose();
        eventRadio.dispose();
    
        htmlResourceBrowse.dispose();
    }

}