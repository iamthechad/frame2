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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.megatome.frame2.Frame2Plugin;
import org.megatome.frame2.model.Frame2Exception;
import org.megatome.frame2.model.Frame2ModelException;
import org.megatome.frame2.model.View;

public class ExceptionWizard extends BaseFrame2Wizard {
	private ExceptionWizardPage1 page;

	public ExceptionWizard() {
		super();
		setNeedsProgressMonitor(true);
	}
	
	public void addPages() {
		page = new ExceptionWizardPage1(selection);
		addPage(page);
	}

	public boolean performFinish() {
		final String requestKey = page.getRequestKey();
		final String exceptionType = page.getExceptionType();
        final String htmlView = page.getHTMLView();
        final String xmlView = page.getXMLView();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(requestKey, exceptionType, htmlView, xmlView, monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), Frame2Plugin.getResourceString("ExceptionWizard.ErrorTitle"), realException.getMessage()); //$NON-NLS-1$
			return false;
		}
		return true;
	}
	
	private void doFinish(
		String requestKey,
		String exceptionType,
        String htmlView,
        String xmlView,
		IProgressMonitor monitor)
		throws CoreException {
		// create a sample file
		monitor.beginTask(Frame2Plugin.getResourceString("ExceptionWizard.creatingEntryStatus"), 1); //$NON-NLS-1$
        
        Frame2Exception exception = new Frame2Exception();
        exception.setRequestKey(requestKey);
        exception.setType(exceptionType);
        
        if ((htmlView.length() != 0) && 
            (xmlView.length() != 0) && 
            htmlView.equals(xmlView)) {
            View view = new View();
            view.setForwardName(htmlView);
            view.setType(Frame2Plugin.getResourceString("ExceptionWizard.both_type")); //$NON-NLS-1$
            exception.addView(view);
        } else {
            if (htmlView.length() != 0) {
                View view = new View();
                view.setForwardName(htmlView);
                view.setType(Frame2Plugin.getResourceString("ExceptionWizard.html_type")); //$NON-NLS-1$
                exception.addView(view);
            } 
            if (xmlView.length() != 0) {
                View view = new View();
                view.setForwardName(xmlView);
                view.setType(Frame2Plugin.getResourceString("ExceptionWizard.xml_type")); //$NON-NLS-1$
                exception.addView(view);
            }
        }
        
        try {
            model.addFrame2Exception(exception);
            model.persistConfiguration();
        } catch (Frame2ModelException e) {
            throwCoreException(Frame2Plugin.getResourceString("ExceptionWizard.errorCreatingException") + e.getMessage()); //$NON-NLS-1$
        }
        
		monitor.worked(1);
	}
}