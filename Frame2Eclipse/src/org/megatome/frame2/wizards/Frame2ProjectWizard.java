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

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.megatome.frame2.Frame2Plugin;

public class Frame2ProjectWizard extends Wizard implements INewWizard {
    private Frame2ProjectWizardPage1 page;
    private ISelection selection;

    private final static String WEBINF = "WEB-INF";
    private final static String CLASSES_DIR = "classes";
    private final static String LIB_DIR = "lib";
    private final static String COMMONS_DIR = "commonsvalidator";
    private final static String SRC_DIR = "src";

    public Frame2ProjectWizard() {
        super();
        setNeedsProgressMonitor(true);
    }

    public void addPages() {
        page = new Frame2ProjectWizardPage1(selection);
        addPage(page);
    }

    public boolean performFinish() {
        final IProject project = page.getProjectHandle();
        final IPath projectPath = page.useDefaults() ? null : page.getLocationPath();
        final boolean enableServices = page.enableWebServices();
        
        IRunnableWithProgress op = new IRunnableWithProgress() {
            public void run(IProgressMonitor monitor)
                throws InvocationTargetException {
                try {
                    doFinish(project, projectPath, enableServices, monitor);
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
            MessageDialog.openError(
                getShell(),
                "Error",
                realException.getMessage());
            return false;
        }
        return true;
    }

    private void doFinish(
        IProject project,
        IPath projectPath,
        boolean enableServices,
        IProgressMonitor monitor)
        throws CoreException {
        // create a sample file
        monitor.beginTask("Creating Web Application", 5);
        // Create new project
        IProject newProject = createFrame2Project(project, projectPath, monitor);
        monitor.worked(1);
        // Create Folders
        createFolders(newProject, monitor);
        monitor.worked(1);
        // Copy files
        copyFiles(newProject, enableServices, monitor);
        monitor.worked(1);
        // Configure Folders
        IJavaProject jProject = configureFolders(newProject, monitor);
        monitor.worked(1);
        // Configure Classpaths
        configureClasspath(jProject, enableServices, monitor);
        monitor.worked(1);
    }

    private IProject createFrame2Project(
        IProject project,
        IPath projectPath,
        IProgressMonitor monitor)
        throws CoreException {
            // get a project handle
        final IProject newProjectHandle = project;

        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IProjectDescription description =
            workspace.newProjectDescription(newProjectHandle.getName());
        description.setLocation(projectPath);
        
        try {
            newProjectHandle.create(
                description,
                monitor);

            if (monitor.isCanceled())
                throw new OperationCanceledException();

            newProjectHandle.open(monitor);
        } catch (CoreException e) {
            throwCoreException("Error creating Frame2 project");
        }
        
        return newProjectHandle;
    }
    
    private void createFolders(IProject newProject, IProgressMonitor monitor)
        throws CoreException {
        String[] folders =
            new String[] { CLASSES_DIR, LIB_DIR, COMMONS_DIR, SRC_DIR, };

        try {
            IFolder infFolder = newProject.getFolder(WEBINF);
            infFolder.create(true, true, monitor);

            for (int i = 0; i < folders.length; i++) {
                IFolder f = infFolder.getFolder(folders[i]);
                f.create(true, true, monitor);
            }
        } catch (CoreException e) {
            throwCoreException(
                "Error creating Frame2 project: " + e.getMessage());
        }
    }

    private void copyFiles(
        IProject newProject,
        boolean enableServices,
        IProgressMonitor monitor)
        throws CoreException {
        IFolder infFolder = newProject.getFolder(WEBINF);

        // Copy WEB-INF files
        copyProjectFiles(infFolder, getInfFiles(enableServices), monitor);

        // Copy commons validator files
        copyProjectFiles(
            infFolder.getFolder(COMMONS_DIR),
            getCommonsFiles(),
            monitor);

        // Copy jar files
        copyProjectFiles(
            infFolder.getFolder(LIB_DIR),
            getLibFiles(enableServices),
            monitor);
    }

    private IJavaProject configureFolders(
        IProject newProject,
        IProgressMonitor monitor)
        throws CoreException {
        IFolder infFolder = newProject.getFolder(WEBINF);

        setJavaNatureOnProject(newProject, monitor);

        IJavaProject jProject = JavaCore.create(newProject);

        jProject.setOutputLocation(
            infFolder.getFolder(CLASSES_DIR).getFullPath(),
            monitor);

        IFolder binDir = newProject.getFolder("bin");
        if (binDir.exists()) {
            binDir.delete(true, monitor);
        }

        IClasspathEntry[] entries = new IClasspathEntry[2];
        entries[0] = JavaRuntime.getDefaultJREContainerEntry();
        entries[1] =
            JavaCore.newSourceEntry(infFolder.getFolder(SRC_DIR).getFullPath());
        jProject.setRawClasspath(entries, monitor);

        return jProject;
    }

    private void configureClasspath(
        IJavaProject jProject,
        boolean enableServices,
        IProgressMonitor monitor)
        throws CoreException {
        Map libFiles = getLibFiles(enableServices);
        IProject rootProject = jProject.getProject();
        String prefix = "WEB-INF/lib/";

        IClasspathEntry[] entries = jProject.getRawClasspath();
        List cp = new ArrayList(entries.length + 1);
        cp.addAll(Arrays.asList(entries));

        for (Iterator i = libFiles.keySet().iterator(); i.hasNext();) {
            IResource fileResource =
                rootProject.findMember(prefix + (String)i.next());
            IClasspathEntry cpEntry =
                JavaCore.newLibraryEntry(
                    fileResource.getFullPath(),
                    null,
                    null);
            cp.add(cpEntry);
        }

        jProject.setRawClasspath(
            (IClasspathEntry[])cp.toArray(new IClasspathEntry[cp.size()]),
            null);
    }

    private void setJavaNatureOnProject(
        IProject newProject,
        IProgressMonitor monitor)
        throws CoreException {
        IProjectDescription description = newProject.getDescription();
        String[] prevNatures = description.getNatureIds();

        int natureIndex = -1;
        for (int i = 0; i < prevNatures.length; i++) {
            if (prevNatures[i].equals(JavaCore.NATURE_ID)) {
                natureIndex = i;
                i = prevNatures.length;
            }
        }

        // Add nature only if it is not already there
        if (natureIndex == -1) {
            String[] newNatures = new String[prevNatures.length + 1];
            System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
            newNatures[prevNatures.length] = JavaCore.NATURE_ID;
            description.setNatureIds(newNatures);
            newProject.setDescription(description, monitor);
        }
    }

    private void copyProjectFiles(
        IFolder destination,
        Map fileNames,
        IProgressMonitor monitor)
        throws CoreException {
        for (Iterator i = fileNames.keySet().iterator(); i.hasNext();) {
            String srcFileName = (String)i.next();
            String destFileName = (String)fileNames.get(srcFileName);
            copyProjectFile(destination, srcFileName, destFileName, monitor);
        }
    }

    private void copyProjectFile(
        IFolder destination,
        String srcFileName,
        String destFileName,
        IProgressMonitor monitor)
        throws CoreException {

        IFile destFile = destination.getFile(destFileName);
        try {
            destFile.create(loadPluginFile(srcFileName), true, monitor);
        } catch (CoreException e) {
            throwCoreException(
                "Error copying required file: " + e.getMessage());
        }
    }

    private InputStream loadPluginFile(String fileName) throws CoreException {
        InputStream is = null;
        try {
            is =
               Frame2Plugin.getDefault().openStream(
                    new Path("templates/" + fileName));
        } catch (IOException e) {
            throwCoreException(
                "Could not copy required file: " + e.getMessage());
        }

        return is;
    }

    private Map getInfFiles(boolean copyServiceFiles) {
        HashMap files = new HashMap();

        if (copyServiceFiles) {
            files.put("services-web.xml", "web.xml");
            files.put("server-config.wsdd", "server-config.wsdd");
        } else {
            files.put("web.xml", "web.xml");
        }

        files.put("frame2-config.xml", "frame2-config.xml");

        return files;
    }

    private Map getCommonsFiles() {
        HashMap files = new HashMap();

        files.put(
            "commons-validation-mappings.xml",
            "commons-validation-mappings.xml");
        files.put("commons-validator-rules.xml", "commons-validator-rules.xml");

        return files;
    }

    private Map getLibFiles(boolean copyServiceJars) {
        HashMap files = new HashMap();

        files.put("commons-beanutils.jar", "commons-beanutils.jar");
        files.put("commons-collections.jar", "commons-collections.jar");
        files.put("commons-digester.jar", "commons-digester.jar");
        files.put("commons-fileupload.jar", "commons-fileupload.jar");
        files.put("commons-logging.jar", "commons-logging.jar");
        files.put("commons-validator.jar", "commons-validator.jar");
        files.put("jakarta-oro.jar", "jakarta-oro.jar");
        //files.put("jaxen-full.jar", "jaxen-full.jar");
        files.put("jstl.jar", "jstl.jar");
        files.put("log4j.jar", "log4j.jar");
        //files.put("saxpath.jar", "saxpath.jar");
        files.put("standard.jar", "standard.jar");
        files.put("template-taglib.jar", "template-taglib.jar");
        files.put("frame2.jar", "frame2.jar");
        files.put("frame2-taglib.jar", "frame2-taglib.jar");

        if (copyServiceJars) {
            files.put("axis.jar", "axis.jar");
            files.put("commons-discovery.jar", "commons-discovery.jar");
            files.put("jaxb-api.jar", "jaxb-api.jar");
            files.put("jaxb-libs.jar", "jaxb-libs.jar");
            files.put("jaxb-ri.jar", "jaxb-ri.jar");
            files.put("jaxb-xjc.jar", "jaxb-xjc.jar");
            files.put("jaxp-api.jar", "jaxp-api.jar");
            files.put("jax-qname.jar", "jax-qname.jar");
            files.put("jaxrpc.jar", "jaxrpc.jar");
            files.put("jaxrpc-api.jar", "jaxrpc-api.jar");
            files.put("namespace.jar", "namespace.jar");
            files.put("saaj-api.jar", "saaj-api.jar");
            files.put("wsdl4j.jar", "wsdl4j.jar");
        }

        return files;
    }

    private void throwCoreException(String message) throws CoreException {
        IStatus status =
            new Status(
                IStatus.ERROR,
                "org.megatome.frame2",
                IStatus.OK,
                message,
                null);
        throw new CoreException(status);
    }

    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.selection = selection;
        setDefaultPageImageDescriptor(Frame2WizardSupport.getFrame2Logo());
    }
}