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
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ResourceListSelectionDialog;
import org.megatome.frame2.model.Frame2Event;
import org.megatome.frame2.model.Frame2Model;

public class EventHandlerWizardPage2 extends WizardPage {
    private Text nameText;
    private Text htmlResourceText;
    private Text xmlResourceText;
    private Label nameLabel;
    private Label typeLabel;
    private Label htmlResourceLabel;
    private Label xmlResourceLabel;
    private Label eventLabel;
    private Combo eventCombo;

    private Button htmlResourceRadio;
    private Button xmlResourceRadio;
    private Button xmlResponderRadio;
    private Button eventRadio;
    private Button addButton;

    private Button htmlResourceBrowse;

    private Table forwardTable;
    private Button removeButton;

    private SelectionListener radioListener;

    private ISelection selection;
    private IProject htmlResourceProject;
    
    private boolean badModel = false;

    private String[] localForwardNames = null;

    public EventHandlerWizardPage2(ISelection selection) {
        super("wizardPage");
        setTitle("Frame2 Local Forward");
        setDescription("Create any required local forwards for this Event Handler.");
        this.selection = selection;
    }

    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout();
        container.setLayout(layout);
        layout.numColumns = 4;
        //layout.verticalSpacing = 9;
        nameLabel = new Label(container, SWT.NULL);
        nameLabel.setText("&Name:");

        nameText = new Text(container, SWT.BORDER | SWT.SINGLE);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 3;
        nameText.setLayoutData(gd);
        nameText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });

        nameLabel.setEnabled(false);
        nameText.setEnabled(false);

        typeLabel = new Label(container, SWT.NULL);
        typeLabel.setText("&Type:");

        typeLabel.setEnabled(false);

        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 4;
        typeLabel.setLayoutData(gd);

        htmlResourceRadio = new Button(container, SWT.RADIO);
        htmlResourceRadio.setText("HTMLResource");
        htmlResourceRadio.setSelection(true);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 4;
        htmlResourceRadio.setLayoutData(gd);
        htmlResourceRadio.addSelectionListener(getRadioListener());

        htmlResourceLabel = new Label(container, SWT.NULL);
        htmlResourceLabel.setText("&Path:");

        htmlResourceText = new Text(container, SWT.BORDER | SWT.SINGLE);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        htmlResourceText.setLayoutData(gd);
        htmlResourceText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });

        htmlResourceBrowse = new Button(container, SWT.PUSH);
        htmlResourceBrowse.setText("Browse...");
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
        xmlResourceRadio.setText("XMLResource");
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        xmlResourceRadio.setLayoutData(gd);
        xmlResourceRadio.addSelectionListener(getRadioListener());

        xmlResponderRadio = new Button(container, SWT.RADIO);
        xmlResponderRadio.setText("XMLResponder");
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        xmlResponderRadio.setLayoutData(gd);
        xmlResponderRadio.addSelectionListener(getRadioListener());

        xmlResourceLabel = new Label(container, SWT.NULL);
        xmlResourceLabel.setText("&Path:");

        xmlResourceText = new Text(container, SWT.BORDER | SWT.SINGLE);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 3;
        xmlResourceText.setLayoutData(gd);
        xmlResourceText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });

        xmlResourceRadio.setEnabled(false);
        xmlResponderRadio.setEnabled(false);
        xmlResourceLabel.setEnabled(false);
        xmlResourceText.setEnabled(false);

        eventRadio = new Button(container, SWT.RADIO);
        eventRadio.setText("Event");
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 4;
        eventRadio.setLayoutData(gd);
        eventRadio.addSelectionListener(getRadioListener());

        eventLabel = new Label(container, SWT.NULL);
        eventLabel.setText("&Path:");

        eventCombo = new Combo(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 3;
        eventCombo.setLayoutData(gd);
        eventCombo.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });

        eventRadio.setEnabled(false);
        eventLabel.setEnabled(false);
        eventCombo.setEnabled(false);

        addButton = new Button(container, SWT.PUSH);
        addButton.setText("Add Forward to Table");
        gd = new GridData(GridData.BEGINNING);
        gd.horizontalSpan = 2;
        addButton.setLayoutData(gd);
        addButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                addForwardToTable();
            }
        });
        addButton.setEnabled(false);

        removeButton = new Button(container, SWT.PUSH);
        removeButton.setText("Remove Forward");
        gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
        gd.horizontalSpan = 2;
        removeButton.setLayoutData(gd);
        removeButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                removeForwardFromTable();
            }
        });
        removeButton.setEnabled(false);

        forwardTable = new Table(container, SWT.SINGLE | SWT.FULL_SELECTION);
        forwardTable.setLinesVisible(true);
        forwardTable.setHeaderVisible(true);
        gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 4;
        gd.verticalSpan = 3;
        forwardTable.setLayoutData(gd);

        TableColumn tc1 = new TableColumn(forwardTable, SWT.NULL);
        tc1.setText("Forward Name");
        tc1.setWidth(120);

        TableColumn tc2 = new TableColumn(forwardTable, SWT.NULL);
        tc2.setText("Forward Type");
        tc2.setWidth(120);

        TableColumn tc3 = new TableColumn(forwardTable, SWT.NULL);
        tc3.setText("Forward Path");
        tc3.setWidth(120);

        forwardTable.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                removeButton.setEnabled(true);
            }

            public void widgetDefaultSelected(SelectionEvent e) {}
        });

        initialize();
        //dialogChanged();
        setPageComplete(true);
        setControl(container);
    }

    private void initialize() {
        Frame2Model model = ((EventHandlerWizard)getWizard()).getFrame2Model();
        
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
    }

    private void addForwardToTable() {
        String forwardName = getForwardName();
        String forwardType = getForwardType();
        String forwardPath = getForwardPath();

        TableItem item = new TableItem(forwardTable, SWT.NONE);
        String[] itemData =
            new String[] { forwardName, forwardType, forwardPath, };
        item.setText(itemData);

        nameText.setText("");
        htmlResourceText.setText("");
        xmlResourceText.setText("");

        if (localForwardNames == null) {
            localForwardNames = new String[] { forwardName };
        } else {
            String[] newForwardNames = new String[localForwardNames.length + 1];
            System.arraycopy(
                localForwardNames,
                0,
                newForwardNames,
                0,
                localForwardNames.length);
            newForwardNames[newForwardNames.length - 1] = forwardName;
            localForwardNames = newForwardNames;
        }

        addButton.setEnabled(false);
    }

    private void removeForwardFromTable() {
        int selIndex = forwardTable.getSelectionIndex();
        if (selIndex == -1) {
            removeButton.setEnabled(false);
            return;
        }

        String forwardName = forwardTable.getItem(selIndex).getText(0);
        forwardTable.remove(selIndex);

        int nameIndex = -1;
        for (int i = 0; i < localForwardNames.length; i++) {
            if (localForwardNames[i].equals(forwardName)) {
                nameIndex = i;
            }
        }

        if (nameIndex == -1) {
            return;
        }

        String[] newForwards = new String[localForwardNames.length - 1];
        System.arraycopy(localForwardNames, 0, newForwards, 0, nameIndex);
        System.arraycopy(
            localForwardNames,
            nameIndex + 1,
            newForwards,
            nameIndex,
            localForwardNames.length - nameIndex - 1);
        localForwardNames = newForwards;

        removeButton.setEnabled(false);
    }

    private void handleBrowse() {
        IResource[] forwardElements = findForwardResources();
        ResourceListSelectionDialog dialog =
            new ResourceListSelectionDialog(getShell(), forwardElements);
        dialog.setInitialSelections(forwardElements);
        if (dialog.open() == ResourceListSelectionDialog.OK) {
            Object[] results = dialog.getResult();
            if (results.length == 1) {
                IFile f = (IFile)results[0];
                htmlResourceText.setText(
                    "/" + f.getProjectRelativePath().toString());
                htmlResourceProject = f.getProject();
                dialogChanged();
            }
        }
    }

    private IResource[] findForwardResources() {
        ArrayList filteredResources = new ArrayList();

        IWorkspace myWorkspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot myRoot = myWorkspace.getRoot();

        IProject[] allProjects = myRoot.getProjects();
        for (int i = 0; i < allProjects.length; i++) {
            if (allProjects[i].isOpen()) {
                filterResources(allProjects[i], filteredResources);
            }
        }

        //filterResources(rootProject, filteredResources);

        IResource[] allFiltered = new IResource[0];
        allFiltered = (IResource[])filteredResources.toArray(allFiltered);

        return allFiltered;
    }

    private void filterResources(
        IContainer container,
        ArrayList resourceList) {
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
            if ((fileExt != null)
                && (fileExt.equalsIgnoreCase("jsp")
                    || fileExt.equalsIgnoreCase("htm")
                    || fileExt.equalsIgnoreCase("html"))) {
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

                    if (buttonText.equals("HTMLResource")) {
                        htmlResourceLabel.setEnabled(true);
                        htmlResourceText.setEnabled(true);
                        htmlResourceBrowse.setEnabled(true);

                        xmlResourceLabel.setEnabled(false);
                        xmlResourceText.setEnabled(false);

                        eventLabel.setEnabled(false);
                        eventCombo.setEnabled(false);
                    } else if (buttonText.equals("XMLResource")) {
                        htmlResourceLabel.setEnabled(false);
                        htmlResourceText.setEnabled(false);
                        htmlResourceBrowse.setEnabled(false);

                        xmlResourceLabel.setEnabled(true);
                        xmlResourceText.setEnabled(true);

                        eventLabel.setEnabled(false);
                        eventCombo.setEnabled(false);
                    } else if (buttonText.equals("XMLResponder")) {
                        htmlResourceLabel.setEnabled(false);
                        htmlResourceText.setEnabled(false);
                        htmlResourceBrowse.setEnabled(false);

                        xmlResourceLabel.setEnabled(true);
                        xmlResourceText.setEnabled(true);

                        eventLabel.setEnabled(false);
                        eventCombo.setEnabled(false);
                    } else if (buttonText.equals("Event")) {
                        htmlResourceLabel.setEnabled(false);
                        htmlResourceText.setEnabled(false);
                        htmlResourceBrowse.setEnabled(false);

                        xmlResourceLabel.setEnabled(false);
                        xmlResourceText.setEnabled(false);

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
            setErrorMessage("This wizard cannot complete due to an error with the Frame2 configuration.");
            setPageComplete(false);
            return;
        }
        
        String name = getForwardName();
        String type = getForwardType();
        String path = getForwardPath();

        if ((name.length() == 0)
            || (type.length() == 0)
            || (path.length() == 0)
            || (!validateForwardName())
            || (!validateForwardPath())) {
            addButton.setEnabled(false);
            return;
        }

        addButton.setEnabled(true);
    }

    private String getForwardName() {
        return nameText.getText();
    }
    private String getForwardType() {
        if (htmlResourceRadio.getSelection()) {
            return "HTMLResource";
        } else if (xmlResourceRadio.getSelection()) {
            return "XMLResource";
        } else if (xmlResponderRadio.getSelection()) {
            return "XMLResponder";
        } else if (eventRadio.getSelection()) {
            return "event";
        }

        return "";
    }
    private String getForwardPath() {
        if (htmlResourceRadio.getSelection()) {
            return htmlResourceText.getText();
        } else if (xmlResourceRadio.getSelection()) {
            return xmlResourceText.getText();
        } else if (xmlResponderRadio.getSelection()) {
            return xmlResourceText.getText();
        } else if (eventRadio.getSelection()) {
            return eventCombo.getText();
        }

        return "";
    }

    private boolean validateForwardName() {
        String forwardName = getForwardName();

        if (localForwardNames == null) {
            return true;
        }

        for (int i = 0; i < localForwardNames.length; i++) {
            if (localForwardNames[i].equals(forwardName)) {
                return false;
            }
        }

        return true;
    }

    private boolean validateForwardPath() {
        String forwardType = getForwardType();
        String forwardPath = getForwardPath();

        if (forwardType.equals("HTMLResource")) {
            if (forwardPath.indexOf("://") != -1) {
                return true;
            }

            if (htmlResourceProject != null) {
                IResource filePath =
                    htmlResourceProject.findMember(forwardPath);
                if (filePath != null) {
                    if (!filePath.exists() || (!(filePath instanceof IFile))) {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                IWorkspace myWorkspace = ResourcesPlugin.getWorkspace();
                IWorkspaceRoot myRoot = myWorkspace.getRoot();
        
                IProject[] allProjects = myRoot.getProjects();
                for (int i = 0; i < allProjects.length; i++) {
                    IResource filePath = allProjects[i].findMember(forwardPath);
                    if (filePath != null) {
                        if (filePath.exists() && (filePath instanceof IFile)) {
                            return true;
                        }
                    } 
                }
                
                return false;
            }
        }

        return true;
    }

    public ArrayList getLocalForwards() {
        ArrayList localForwards = new ArrayList();

        int forwardCount = forwardTable.getItemCount();

        for (int i = 0; i < forwardCount; i++) {
            TableItem item = forwardTable.getItem(i);
            String[] forwardValues =
                new String[] {
                    item.getText(0),
                    item.getText(1),
                    item.getText(2),
                    };
            localForwards.add(forwardValues);
        }

        return localForwards;
    }

    public void dispose() {
        super.dispose();
        nameText.dispose();
        htmlResourceText.dispose();
        xmlResourceText.dispose();
        nameLabel.dispose();
        typeLabel.dispose();
        htmlResourceLabel.dispose();
        xmlResourceLabel.dispose();
        eventLabel.dispose();
        eventCombo.dispose();
    
        htmlResourceRadio.dispose();
        xmlResourceRadio.dispose();
        xmlResponderRadio.dispose();
        eventRadio.dispose();
        addButton.dispose();
    
        htmlResourceBrowse.dispose();
    
        forwardTable.dispose();
        removeButton.dispose();
    }

}