/*
 * ====================================================================
 * 
 * Frame2 Open Source License
 * 
 * Copyright (c) 2004-2005 Megatome Technologies. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * 3. The end-user documentation included with the redistribution, if any, must
 * include the following acknowlegement: "This product includes software
 * developed by Megatome Technologies." Alternately, this acknowlegement may
 * appear in the software itself, if and wherever such third-party
 * acknowlegements normally appear.
 * 
 * 4. The names "The Frame2 Project", and "Frame2", must not be used to endorse
 * or promote products derived from this software without prior written
 * permission. For written permission, please contact
 * iamthechad@sourceforge.net.
 * 
 * 5. Products derived from this software may not be called "Frame2" nor may
 * "Frame2" appear in their names without prior written permission of Megatome
 * Technologies.
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL MEGATOME
 * TECHNOLOGIES OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 */
/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Common Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors: IBM Corporation - initial API and implementation
 ******************************************************************************/
package org.megatome.frame2.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.internal.ui.dialogs.StatusInfo;
import org.eclipse.jdt.ui.wizards.NewTypeWizardPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.megatome.frame2.model.Frame2Event;
import org.megatome.frame2.model.Frame2Model;
import org.megatome.frame2.Frame2Plugin;

public class NewEventWizardPage1 extends NewTypeWizardPage {

   private ISelection selection;
   private SelectionListener radioListener;
   private Button newClassRadio;
   private Button existingClassRadio;
   private Button noClassRadio;
   private Combo existingClassCombo;
   private Text eventNameText;
   private IStatus eventNameStatus;
   private IStatus selectedEventStatus;
   private StatusInfo badModelStatus = new StatusInfo();

   private Frame2Event[] definedEvents = new Frame2Event[0];

   private boolean initialized = false;

   private static final String PAGE_NAME = Frame2Plugin.getResourceString("NewEventWizardPage1.wizardName"); //$NON-NLS-1$
   private static final String NEW_CLASS_RADIO = Frame2Plugin.getResourceString("NewEventWizardPage1.newClassRadio"); //$NON-NLS-1$
   private static final String EXISTING_CLASS_RADIO = Frame2Plugin.getResourceString("NewEventWizardPage1.existingClassRadio"); //$NON-NLS-1$
   private static final String NO_CLASS_RADIO = Frame2Plugin.getResourceString("NewEventWizardPage1.noClassRadio"); //$NON-NLS-1$
   public static final String NEW_CLASS = Frame2Plugin.getResourceString("NewEventWizardPage1.newClass_type"); //$NON-NLS-1$
   public static final String EXISTING_CLASS = Frame2Plugin.getResourceString("NewEventWizardPage1.existingClass_type"); //$NON-NLS-1$
   public static final String NO_CLASS = Frame2Plugin.getResourceString("NewEventWizardPage1.noClass_type"); //$NON-NLS-1$

   public NewEventWizardPage1(ISelection selection) {
      super(true, PAGE_NAME);
      setTitle(Frame2Plugin.getResourceString("NewEventWizardPage1.pageTitle")); //$NON-NLS-1$
      setDescription(Frame2Plugin.getResourceString("NewEventWizardPage1.pageDescription")); //$NON-NLS-1$
      this.selection = selection;
   }

   public void createControl(Composite parent) {
      initializeDialogUnits(parent);

      Composite composite = new Composite(parent, SWT.NONE);

      int nColumns = 4;

      GridLayout layout = new GridLayout();
      layout.numColumns = nColumns;
      composite.setLayout(layout);

      // pick & choose the wanted UI components
      Label label = new Label(composite, SWT.NULL);
      label.setText(Frame2Plugin.getResourceString("NewEventWizardPage1.eventNameLabel")); //$NON-NLS-1$

      eventNameText = new Text(composite, SWT.BORDER | SWT.SINGLE);
      GridData gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      eventNameText.setLayoutData(gd);
      eventNameText.addModifyListener(new ModifyListener() {
         public void modifyText(ModifyEvent e) {
            dialogChanged();
         }
      });

      createSeparator(composite, nColumns);

      newClassRadio = new Button(composite, SWT.RADIO);
      newClassRadio.setText(NEW_CLASS_RADIO);
      newClassRadio.addSelectionListener(getRadioListener());
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 4;
      newClassRadio.setLayoutData(gd);
      newClassRadio.setSelection(true);

      createContainerControls(composite, nColumns);
      createPackageControls(composite, nColumns);
      createTypeNameControls(composite, nColumns);

      createSeparator(composite, nColumns);

      existingClassRadio = new Button(composite, SWT.RADIO);
      existingClassRadio.setText(EXISTING_CLASS_RADIO);
      existingClassRadio.addSelectionListener(getRadioListener());
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 4;
      existingClassRadio.setLayoutData(gd);

      label = new Label(composite, SWT.NULL);
      label.setText(Frame2Plugin.getResourceString("NewEventWizardPage1.eventLabel")); //$NON-NLS-1$

      existingClassCombo = new Combo(composite, SWT.BORDER | SWT.SINGLE
               | SWT.READ_ONLY);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      existingClassCombo.setLayoutData(gd);
      existingClassCombo.addModifyListener(new ModifyListener() {
         public void modifyText(ModifyEvent e) {
            dialogChanged();
         }
      });

      createSeparator(composite, nColumns);

      noClassRadio = new Button(composite, SWT.RADIO);
      noClassRadio.setText(NO_CLASS_RADIO);
      noClassRadio.addSelectionListener(getRadioListener());
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 4;
      noClassRadio.setLayoutData(gd);

      createSeparator(composite, nColumns);

      initialize();
      dialogChanged();
      setControl(composite);
      //Dialog.applyDialogFont(composite);
   }

   private void initialize() {
      Frame2Model model = ((NewEventWizard) getWizard()).getFrame2Model();

      badModelStatus = new StatusInfo();
      if (model != null) {
         definedEvents = model.getEvents();
         List uniqueEventNames = new ArrayList();
         for (int j = 0; j < definedEvents.length; j++) {
            String eventType = definedEvents[j].getType();
            if ((eventType != null) && (!eventType.equals(""))) {//$NON-NLS-1$
               if (!uniqueEventNames.contains(eventType)) {
                  uniqueEventNames.add(eventType);
               }
            }
         }

         existingClassCombo.setItems((String[]) uniqueEventNames.toArray(new String[uniqueEventNames.size()]));
      } else {
         setPageComplete(false);
         badModelStatus.setError(Frame2Plugin.getResourceString("NewEventWizardPage1.errorConfiguration")); //$NON-NLS-1$
      }

      if (selection != null && selection.isEmpty() == false
               && selection instanceof IStructuredSelection) {
         IStructuredSelection ssel = (IStructuredSelection) selection;
         if (ssel.size() > 1) return;
         IJavaElement jelem = getInitialJavaElement(ssel);
         initContainerPage(jelem);
         initTypePage(jelem);
      }

      initialized = true;
      setSuperClass(
               Frame2Plugin.getResourceString("NewEventWizardPage1.commonsValidatorEventSuperClass"), //$NON-NLS-1$
               true); 
   }

   private void dialogChanged() {
      String eventType = getNewEventType();

      eventNameStatus = getEventNameStatus();

      if (eventType.equals(EXISTING_CLASS)) {
         selectedEventStatus = getSelectedEventStatus();
      }

      doStatusUpdate();
   }

   protected void handleFieldChanged(String fieldName) {
      super.handleFieldChanged(fieldName);

      doStatusUpdate();
   }

   private IStatus getEventNameStatus() {
      StatusInfo status = new StatusInfo();
      String eventName = getEventName();

      if (eventName.length() == 0) {
         status.setError(Frame2Plugin.getResourceString("NewEventWizardPage1.errorMissingEventName")); //$NON-NLS-1$
         return status;
      }

      for (int i = 0; i < definedEvents.length; i++) {
         if (eventName.equals(definedEvents[i].getName())) {
            status.setError(Frame2Plugin.getResourceString("NewEventWizardPage1.errorDuplicateEventName")); //$NON-NLS-1$
            return status;
         }
      }

      return status;
   }

   private IStatus getSelectedEventStatus() {
      StatusInfo status = new StatusInfo();
      String eventClassType = getEventClassType();

      if (eventClassType.length() == 0) {
         status.setError(Frame2Plugin.getResourceString("NewEventWizardPage1.errorSelectExisting")); //$NON-NLS-1$
      }

      return status;
   }

   private void updateStatus(String message) {
      setErrorMessage(message);
      setPageComplete(message == null);
   }

   private SelectionListener getRadioListener() {
      if (radioListener == null) {
         radioListener = new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {}

            public void widgetSelected(SelectionEvent e) {
               dialogChanged();
            }
         };
      }

      return radioListener;
   }

   //  ------ validation --------
   private void doStatusUpdate() {
      // status of all used components
      if ((badModelStatus.getMessage() != null)
               && (!badModelStatus.getMessage().equals(""))) { //$NON-NLS-1$
         IStatus[] status = new IStatus[] {
            badModelStatus
         };
         updateStatus(status);
         return;
      }
      String eventType = getNewEventType();
      if (eventType.equals(NEW_CLASS)) {
         IStatus[] status = new IStatus[] {
                  badModelStatus, fContainerStatus, fPackageStatus,
                  fTypeNameStatus, getEventNameStatus(),
         };
         // the most severe status will be displayed and the ok button
         // enabled/disabled.
         updateStatus(status);
      } else if (eventType.equals(EXISTING_CLASS)) {
         IStatus[] status = new IStatus[] {
                  badModelStatus, getEventNameStatus(),
                  getSelectedEventStatus(),
         };
         updateStatus(status);
      } else if (eventType.equals(NO_CLASS)) {
         IStatus[] status = new IStatus[] {
                  badModelStatus, getEventNameStatus(),
         };
         updateStatus(status);
      }
   }

   public String getNewEventType() {
      if (newClassRadio.getSelection()) {
         return NEW_CLASS;
      } else if (existingClassRadio.getSelection()) {
         return EXISTING_CLASS;
      } else {
         return NO_CLASS;
      }
   }

   public String getEventName() {
      return eventNameText.getText();
   }

   public String getEventClassType() {
      String eventClass = ""; //$NON-NLS-1$
      if (newClassRadio.getSelection()) {
         eventClass = getPackageText();
         eventClass += "."; //$NON-NLS-1$
         eventClass += getTypeName();
      } else if (existingClassRadio.getSelection()) {
         eventClass = existingClassCombo.getText();
      }

      return eventClass;
   }

   protected void createTypeMembers(IType newType, ImportsManager imports, IProgressMonitor monitor) throws CoreException {

      imports.addImport(Frame2Plugin.getResourceString("NewEventWizardPage1.commonsValidatorEventSuperClass")); //$NON-NLS-1$

   }

   public void dispose() {
      super.dispose();

      newClassRadio.dispose();
      existingClassRadio.dispose();
      noClassRadio.dispose();
      existingClassCombo.dispose();
      eventNameText.dispose();
   }

}