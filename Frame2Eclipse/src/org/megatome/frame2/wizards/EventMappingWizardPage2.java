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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.megatome.frame2.Frame2Plugin;
import org.megatome.frame2.model.EventHandler;
import org.megatome.frame2.model.Frame2Model;


public class EventMappingWizardPage2 extends WizardPage {
	private Table availableHandlersTable;
	private Table selectedHandlersTable;
    private Button addSingleButton;
    private Button addAllButton;
    private Button removeSingleButton;
    private Button removeAllButton;
    private Button moveUpButton;
    private Button moveDownButton;
	private ISelection selection;
    private boolean badModel = false;
    
	public EventMappingWizardPage2(ISelection selection) {
		super("wizardPage");
		setTitle("Event Mapping Wizard");
		setDescription("Indicate any handlers to use in this mapping by adding them to the 'Selected' table and ordering the handlers appropriately as order matters.");
		this.selection = selection;
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 6;
		layout.verticalSpacing = 9;

        availableHandlersTable = new Table(container, SWT.SINGLE | SWT.FULL_SELECTION);
        availableHandlersTable.setHeaderVisible(true);
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 2;
        availableHandlersTable.setLayoutData(gd);
        availableHandlersTable.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                enableMovementButtons();
            }
        });

        TableColumn tc = new TableColumn(availableHandlersTable, SWT.NULL);
        tc.setText("Available");
        tc.setWidth(200);

        Composite buttonContainer = new Composite(container, SWT.NULL);
        RowLayout rl = new RowLayout();
        rl.type = SWT.VERTICAL;
        rl.pack = false;
        buttonContainer.setLayout(rl);

        addSingleButton = new Button(buttonContainer, SWT.PUSH);
        addSingleButton.setImage(getImage("arrowright.gif"));
        addSingleButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                addHandlerToSelected();
            }
        });
        
        removeSingleButton = new Button(buttonContainer, SWT.PUSH);
        removeSingleButton.setImage(getImage("arrowleft.gif"));
        removeSingleButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                removeHandlerFromSelected();
            }
        });
        
        addAllButton = new Button(buttonContainer, SWT.PUSH);
        addAllButton.setImage(getImage("doublearrowright.gif"));
        addAllButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                addAllHandlersToSelected();
            }
        });
                
        removeAllButton = new Button(buttonContainer, SWT.PUSH);
        removeAllButton.setImage(getImage("doublearrowleft.gif"));
        removeAllButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                removeAllHandlersFromSelected();
            }
        });
                
        gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gd.horizontalSpan = 1;
        buttonContainer.setLayoutData(gd);
        
        selectedHandlersTable = new Table(container, SWT.SINGLE | SWT.FULL_SELECTION);
        selectedHandlersTable.setHeaderVisible(true);
        gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 2;
        selectedHandlersTable.setLayoutData(gd);
        selectedHandlersTable.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                enableMovementButtons();
            }
        });

        tc = new TableColumn(selectedHandlersTable, SWT.NULL);
        tc.setText("Selected");
        tc.setWidth(200);
        
        Composite buttonContainer2 = new Composite(container, SWT.NULL);
        RowLayout rl2 = new RowLayout();
        rl2.type = SWT.VERTICAL;
        rl2.pack = false;
        rl2.spacing = 6;
        buttonContainer2.setLayout(rl2);
        
        moveUpButton = new Button(buttonContainer2, SWT.PUSH);
        moveUpButton.setImage(getImage("arrowtop.gif"));
        moveUpButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                moveSelectedHandler(true);
            }
        });
        
        moveDownButton = new Button(buttonContainer2, SWT.PUSH);
        moveDownButton.setImage(getImage("arrowbottom.gif"));
        moveDownButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                moveSelectedHandler(false);
            }
        });
        
        gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gd.horizontalSpan = 1;
        buttonContainer2.setLayoutData(gd);
        
        addSingleButton.setEnabled(false);
        addAllButton.setEnabled(false);
        removeSingleButton.setEnabled(false);
        removeAllButton.setEnabled(false);
        
        moveUpButton.setEnabled(false);
        moveDownButton.setEnabled(false);

        initialize();
        enableMovementButtons();
		setControl(container);
	}
	
	private void initialize() {
        Frame2Model model = ((EventMappingWizard)getWizard()).getFrame2Model();
        
        if (model != null) {
            EventHandler[] handlers = model.getEventHandlers();
            for (int i = 0; i < handlers.length; i++) {
                TableItem item = new TableItem(availableHandlersTable, SWT.NULL);
                item.setText(handlers[i].getName());
            }
        } else {
            setPageComplete(false);
            badModel = true;
            updateStatus("This wizard cannot complete due to an error with the Frame2 configuration.");
        }
	}
    
    private void setHandlerPageValid() {
        IWizardPage page = getNextPage();
        
        if ((page != null) && 
            (page instanceof EventMappingWizardPage3)) {
                EventMappingWizardPage3 viewPage = (EventMappingWizardPage3)page;
                viewPage.setHandlersSelected(selectedHandlersTable.getItemCount() > 0);
                // Force wizard to update enabled/disabled buttons
                if (!badModel) setPageComplete(true);
            }
    }
    
    private void enableMovementButtons() {
        int availSel = availableHandlersTable.getSelectionIndex();
        int selectedSel = selectedHandlersTable.getSelectionIndex();
        
        addSingleButton.setEnabled(availSel != -1);
        removeSingleButton.setEnabled(selectedSel != -1);
        
        addAllButton.setEnabled(availableHandlersTable.getItemCount() > 0);
        removeAllButton.setEnabled(selectedHandlersTable.getItemCount() > 0);
        
        moveUpButton.setEnabled(selectedSel > 0);
        moveDownButton.setEnabled((selectedSel != -1) && (selectedSel < selectedHandlersTable.getItemCount() -1));
    }
    
    private void addHandlerToSelected() {
        int selIndex = availableHandlersTable.getSelectionIndex();
        
        if (selIndex == -1) {
            return;
        }
        
        moveItemToTable(availableHandlersTable, selectedHandlersTable, selIndex);
        enableMovementButtons();
        setHandlerPageValid();
    }
    
    private void removeHandlerFromSelected() {
        int selIndex = selectedHandlersTable.getSelectionIndex();
        
        if (selIndex == -1) {
            return;
        }

        moveItemToTable(selectedHandlersTable, availableHandlersTable, selIndex);
        enableMovementButtons();
        setHandlerPageValid();
    }
    
    private void addAllHandlersToSelected() {
        int itemCount = availableHandlersTable.getItemCount();
        
        if (itemCount == 0) {
            return;
        }
        
        for (int i = 0; i < itemCount; i++) {
            moveItemToTable(availableHandlersTable, selectedHandlersTable, 0);
        }
        enableMovementButtons();
        setHandlerPageValid();
    }
    
    private void removeAllHandlersFromSelected() {
        int itemCount = selectedHandlersTable.getItemCount();
    
        if (itemCount == 0) {
            return;
        }
    
        for (int i = 0; i < itemCount; i++) {
            moveItemToTable(selectedHandlersTable, availableHandlersTable, 0);
        }
        enableMovementButtons();
        setHandlerPageValid();
    }
    
    private void moveItemToTable(Table srcTable, Table destTable, int index) {
        TableItem item = srcTable.getItem(index);
        TableItem newItem = new TableItem(destTable, SWT.NULL);
        newItem.setText(item.getText());
        srcTable.remove(index);
    }
    
    private void moveSelectedHandler(boolean moveUp) {
        int selIndex = selectedHandlersTable.getSelectionIndex();
        
        if (selIndex == -1) {
            return;
        }
        
        int newIndex = -1;
        if (moveUp) {
            if (selIndex == 0) {
                return;
            }
         
            newIndex = selIndex - 1;   
        } else {
            if (selIndex == (selectedHandlersTable.getItemCount() -1)) {
                return;
            }
            
            newIndex = selIndex + 1;
        }
        
        TableItem item1 = selectedHandlersTable.getItem(selIndex);
        TableItem item2 = selectedHandlersTable.getItem(newIndex);
        String text1 = item1.getText();
        String text2 = item2.getText();
        item1.setText(text2);
        item2.setText(text1);
        selectedHandlersTable.select(newIndex);
        enableMovementButtons();
    }
	
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public ArrayList getSelectedHandlers() {
        ArrayList selected = new ArrayList();
        
        int selCount = selectedHandlersTable.getItemCount();
        for (int i = 0; i < selCount; i++) {
            TableItem item = selectedHandlersTable.getItem(i);
            selected.add(item.getText());
        }
        
        return selected;
	}
    
    private Image getImage(String imageName) {
        String iconPath = "icons/";
        ImageDescriptor id = null;
        try {
            Frame2Plugin plugin = Frame2Plugin.getDefault();
            URL installURL = plugin.getDescriptor().getInstallURL();
            URL url = new URL(installURL, iconPath + imageName);
            id = ImageDescriptor.createFromURL(url);
        } catch (MalformedURLException e) {}
        
        return id.createImage();
    }

    public void dispose() {
        super.dispose();
        
        availableHandlersTable.dispose();
        selectedHandlersTable.dispose();
        addSingleButton.dispose();
        addAllButton.dispose();
        removeSingleButton.dispose();
        removeAllButton.dispose();
        moveUpButton.dispose();
        moveDownButton.dispose();
    }

}