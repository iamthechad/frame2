/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2005 Megatome Technologies.  All rights
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
import org.eclipse.core.runtime.FileLocator;
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
import org.osgi.framework.Bundle;

public class Frame2ProjectWizard extends Wizard implements INewWizard {
	private Frame2ProjectWizardPage1 page;
	private ISelection selection;

	private final static String WEBINF = Frame2Plugin
			.getResourceString("Frame2ProjectWizard.WEB-INF"); //$NON-NLS-1$
	private final static String CLASSES_DIR = Frame2Plugin
			.getResourceString("Frame2ProjectWizard.classes"); //$NON-NLS-1$
	private final static String LIB_DIR = Frame2Plugin
			.getResourceString("Frame2ProjectWizard.lib"); //$NON-NLS-1$
	private final static String COMMONS_DIR = Frame2Plugin
			.getResourceString("Frame2ProjectWizard.commonsvalidator"); //$NON-NLS-1$
	private final static String SRC_DIR = Frame2Plugin
			.getResourceString("Frame2ProjectWizard.src"); //$NON-NLS-1$

	public Frame2ProjectWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		this.page = new Frame2ProjectWizardPage1(this.selection);
		addPage(this.page);
	}

	@Override
	public boolean performFinish() {
		final IProject project = this.page.getProjectHandle();
		final IPath projectPath = this.page.useDefaults() ? null : this.page
				.getLocationPath();
		final boolean enableServices = this.page.enableWebServices();

		final IRunnableWithProgress op = new IRunnableWithProgress() {
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
		} catch (final InterruptedException e) {
			return false;
		} catch (final InvocationTargetException e) {
			final Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), Frame2Plugin
					.getResourceString("Frame2ProjectWizard.ErrorTitle"), //$NON-NLS-1$
					realException.getMessage());
			return false;
		}
		return true;
	}

	void doFinish(final IProject project, final IPath projectPath,
			final boolean enableServices, final IProgressMonitor monitor)
			throws CoreException {
		// create a sample file
		monitor
				.beginTask(
						Frame2Plugin
								.getResourceString("Frame2ProjectWizard.CreatingWebApp_Status"), 5); //$NON-NLS-1$
		// Create new project
		final IProject newProject = createFrame2Project(project, projectPath,
				monitor);
		monitor.worked(1);
		// Create Folders
		createFolders(newProject, monitor);
		monitor.worked(1);
		// Copy files
		copyFiles(newProject, enableServices, monitor);
		monitor.worked(1);
		// Configure Folders
		final IJavaProject jProject = configureFolders(newProject, monitor);
		monitor.worked(1);
		// Configure Classpaths
		configureClasspath(jProject, enableServices, monitor);
		monitor.worked(1);
	}

	private IProject createFrame2Project(final IProject project,
			final IPath projectPath, final IProgressMonitor monitor)
			throws CoreException {
		// get a project handle
		final IProject newProjectHandle = project;

		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProjectDescription description = workspace
				.newProjectDescription(newProjectHandle.getName());
		description.setLocation(projectPath);

		try {
			newProjectHandle.create(description, monitor);

			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}

			newProjectHandle.open(monitor);
		} catch (final CoreException e) {
			throwCoreException(Frame2Plugin
					.getResourceString("Frame2ProjectWizard.CreateProjectError")); //$NON-NLS-1$
		}

		return newProjectHandle;
	}

	private void createFolders(final IProject newProject,
			final IProgressMonitor monitor) throws CoreException {
		final String[] folders = new String[] { CLASSES_DIR, LIB_DIR,
				COMMONS_DIR, SRC_DIR, };

		try {
			final IFolder infFolder = newProject.getFolder(WEBINF);
			infFolder.create(true, true, monitor);

			for (int i = 0; i < folders.length; i++) {
				final IFolder f = infFolder.getFolder(folders[i]);
				f.create(true, true, monitor);
			}
		} catch (final CoreException e) {
			throwCoreException(Frame2Plugin
					.getResourceString("Frame2ProjectWizard.CreateProjectError") + e.getMessage()); //$NON-NLS-1$
		}
	}

	private void copyFiles(final IProject newProject,
			final boolean enableServices, final IProgressMonitor monitor)
			throws CoreException {
		final IFolder infFolder = newProject.getFolder(WEBINF);

		// Copy WEB-INF files
		copyProjectFiles(infFolder, getInfFiles(enableServices), monitor);

		// Copy commons validator files
		copyProjectFiles(infFolder.getFolder(COMMONS_DIR), getCommonsFiles(),
				monitor);

		// Copy jar files
		copyProjectFiles(infFolder.getFolder(LIB_DIR),
				getLibFiles(enableServices), monitor);
	}

	private IJavaProject configureFolders(final IProject newProject,
			final IProgressMonitor monitor) throws CoreException {
		final IFolder infFolder = newProject.getFolder(WEBINF);

		setJavaNatureOnProject(newProject, monitor);

		final IJavaProject jProject = JavaCore.create(newProject);

		jProject.setOutputLocation(infFolder.getFolder(CLASSES_DIR)
				.getFullPath(), monitor);

		final IFolder binDir = newProject.getFolder(Frame2Plugin
				.getResourceString("Frame2ProjectWizard.bin")); //$NON-NLS-1$
		if (binDir.exists()) {
			binDir.delete(true, monitor);
		}

		final IClasspathEntry[] entries = new IClasspathEntry[2];
		entries[0] = JavaRuntime.getDefaultJREContainerEntry();
		entries[1] = JavaCore.newSourceEntry(infFolder.getFolder(SRC_DIR)
				.getFullPath());
		jProject.setRawClasspath(entries, monitor);

		return jProject;
	}

	private void configureClasspath(final IJavaProject jProject,
			final boolean enableServices, @SuppressWarnings("unused")
			final IProgressMonitor monitor) throws CoreException {
		final Map<String, String> libFiles = getLibFiles(enableServices);
		final IProject rootProject = jProject.getProject();
		final String prefix = WEBINF + "/" + LIB_DIR + "/"; //$NON-NLS-1$//$NON-NLS-2$

		final IClasspathEntry[] entries = jProject.getRawClasspath();
		final List<IClasspathEntry> cp = new ArrayList<IClasspathEntry>(
				entries.length + 1);
		cp.addAll(Arrays.asList(entries));

		for (final Iterator<String> i = libFiles.keySet().iterator(); i
				.hasNext();) {
			final IResource fileResource = rootProject.findMember(prefix
					+ i.next());
			final IClasspathEntry cpEntry = JavaCore.newLibraryEntry(
					fileResource.getFullPath(), null, null);
			cp.add(cpEntry);
		}

		jProject.setRawClasspath(cp.toArray(new IClasspathEntry[cp.size()]),
				null);
	}

	private void setJavaNatureOnProject(final IProject newProject,
			final IProgressMonitor monitor) throws CoreException {
		final IProjectDescription description = newProject.getDescription();
		final String[] prevNatures = description.getNatureIds();

		int natureIndex = -1;
		for (int i = 0; i < prevNatures.length; i++) {
			if (prevNatures[i].equals(JavaCore.NATURE_ID)) {
				natureIndex = i;
				i = prevNatures.length;
			}
		}

		// Add nature only if it is not already there
		if (natureIndex == -1) {
			final String[] newNatures = new String[prevNatures.length + 1];
			System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
			newNatures[prevNatures.length] = JavaCore.NATURE_ID;
			description.setNatureIds(newNatures);
			newProject.setDescription(description, monitor);
		}
	}

	private void copyProjectFiles(final IFolder destination,
			final Map<String, String> fileNames, final IProgressMonitor monitor)
			throws CoreException {
		for (java.util.Map.Entry<String, String> entry : fileNames.entrySet()) {
			final String srcFileName = entry.getKey();
			final String destFileName = entry.getValue();
			copyProjectFile(destination, srcFileName, destFileName, monitor);
		}
	}

	private void copyProjectFile(final IFolder destination,
			final String srcFileName, final String destFileName,
			final IProgressMonitor monitor) throws CoreException {

		final IFile destFile = destination.getFile(destFileName);
		try {
			destFile.create(loadPluginFile(srcFileName), true, monitor);
		} catch (final CoreException e) {
			throwCoreException(Frame2Plugin
					.getResourceString("Frame2ProjectWizard.CopyFileError") + e.getMessage()); //$NON-NLS-1$
		}
	}

	private InputStream loadPluginFile(final String fileName)
			throws CoreException {
		InputStream is = null;
		try {
			final Bundle bundle = Frame2Plugin.getDefault().getBundle();
			is = FileLocator.openStream(bundle, new Path(Frame2Plugin
					.getResourceString("Frame2ProjectWizard.templates") //$NON-NLS-1$
					+ fileName), false);
		} catch (final IOException e) {
			throwCoreException(Frame2Plugin
					.getResourceString("Frame2ProjectWizard.CopyFileError") + e.getMessage()); //$NON-NLS-1$
		}

		return is;
	}

	private Map<String, String> getInfFiles(final boolean copyServiceFiles) {
		final Map<String, String> files = new HashMap<String, String>();

		if (copyServiceFiles) {
			files
					.put(
							Frame2Plugin
									.getResourceString("Frame2ProjectWizard.services-web_xml"), Frame2Plugin.getResourceString("Frame2ProjectWizard.web_xml")); //$NON-NLS-1$ //$NON-NLS-2$
			files
					.put(
							Frame2Plugin
									.getResourceString("Frame2ProjectWizard.server-config_wsdd"), Frame2Plugin.getResourceString("Frame2ProjectWizard.server-config_wsdd")); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			files
					.put(
							Frame2Plugin
									.getResourceString("Frame2ProjectWizard.web_xml"), Frame2Plugin.getResourceString("Frame2ProjectWizard.web_xml")); //$NON-NLS-1$ //$NON-NLS-2$
		}

		files
				.put(
						Frame2Plugin
								.getResourceString("Frame2ProjectWizard.frame2-config_xml"), Frame2Plugin.getResourceString("Frame2ProjectWizard.frame2-config_xml")); //$NON-NLS-1$ //$NON-NLS-2$

		return files;
	}

	private Map<String, String> getCommonsFiles() {
		final Map<String, String> files = new HashMap<String, String>();

		files
				.put(
						Frame2Plugin
								.getResourceString("Frame2ProjectWizard.commons-validation-mappings_xml"), //$NON-NLS-1$
						Frame2Plugin
								.getResourceString("Frame2ProjectWizard.commons-validation-mappings_xml")); //$NON-NLS-1$
		files
				.put(
						Frame2Plugin
								.getResourceString("Frame2ProjectWizard.commons-validator-rules_xml"), Frame2Plugin.getResourceString("Frame2ProjectWizard.commons-validator-rules_xml")); //$NON-NLS-1$ //$NON-NLS-2$

		return files;
	}

	private Map<String, String> getLibFiles(final boolean copyServiceJars) {
		final Map<String, String> files = new HashMap<String, String>();

		files
				.put(
						Frame2Plugin
								.getResourceString("Frame2ProjectWizard.commons-beanutils_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.commons-beanutils_jar")); //$NON-NLS-1$ //$NON-NLS-2$
		files
				.put(
						Frame2Plugin
								.getResourceString("Frame2ProjectWizard.commons-collections_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.commons-collections_jar")); //$NON-NLS-1$ //$NON-NLS-2$
		files
				.put(
						Frame2Plugin
								.getResourceString("Frame2ProjectWizard.commons-digester_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.commons-digester_jar")); //$NON-NLS-1$ //$NON-NLS-2$
		files
				.put(
						Frame2Plugin
								.getResourceString("Frame2ProjectWizard.commons-fileupload_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.commons-fileupload_jar")); //$NON-NLS-1$ //$NON-NLS-2$
		files
				.put(
						Frame2Plugin
								.getResourceString("Frame2ProjectWizard.commons-logging_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.commons-logging_jar")); //$NON-NLS-1$ //$NON-NLS-2$
		files
				.put(
						Frame2Plugin
								.getResourceString("Frame2ProjectWizard.commons-validator_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.commons-validator_jar")); //$NON-NLS-1$ //$NON-NLS-2$
		files
				.put(
						Frame2Plugin
								.getResourceString("Frame2ProjectWizard.jakarta-oro_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.jakarta-oro_jar")); //$NON-NLS-1$ //$NON-NLS-2$
		// files.put("jaxen-full.jar", "jaxen-full.jar");
		files
				.put(
						Frame2Plugin
								.getResourceString("Frame2ProjectWizard.jstl_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.jstl_jar")); //$NON-NLS-1$ //$NON-NLS-2$
		files
				.put(
						Frame2Plugin
								.getResourceString("Frame2ProjectWizard.log4j_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.log4j_jar")); //$NON-NLS-1$ //$NON-NLS-2$
		// files.put("saxpath.jar", "saxpath.jar");
		files
				.put(
						Frame2Plugin
								.getResourceString("Frame2ProjectWizard.standard_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.standard_jar")); //$NON-NLS-1$ //$NON-NLS-2$
		files
				.put(
						Frame2Plugin
								.getResourceString("Frame2ProjectWizard.template-taglib_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.template-taglib_jar")); //$NON-NLS-1$ //$NON-NLS-2$
		files
				.put(
						Frame2Plugin
								.getResourceString("Frame2ProjectWizard.frame2_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.frame2_jar")); //$NON-NLS-1$ //$NON-NLS-2$
		files
				.put(
						Frame2Plugin
								.getResourceString("Frame2ProjectWizard.frame2-taglib_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.frame2-taglib_jar")); //$NON-NLS-1$ //$NON-NLS-2$

		if (copyServiceJars) {
			files
					.put(
							Frame2Plugin
									.getResourceString("Frame2ProjectWizard.axis_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.axis_jar")); //$NON-NLS-1$ //$NON-NLS-2$
			files
					.put(
							Frame2Plugin
									.getResourceString("Frame2ProjectWizard.commons-discovery_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.commons-discovery_jar")); //$NON-NLS-1$ //$NON-NLS-2$
			files
					.put(
							Frame2Plugin
									.getResourceString("Frame2ProjectWizard.jaxb-api_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.jaxb-api_jar")); //$NON-NLS-1$ //$NON-NLS-2$
			files
					.put(
							Frame2Plugin
									.getResourceString("Frame2ProjectWizard.jaxb-libs_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.jaxb-libs_jar")); //$NON-NLS-1$ //$NON-NLS-2$
			files
					.put(
							Frame2Plugin
									.getResourceString("Frame2ProjectWizard.jaxb-ri_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.jaxb-ri_jar")); //$NON-NLS-1$ //$NON-NLS-2$
			files
					.put(
							Frame2Plugin
									.getResourceString("Frame2ProjectWizard.jaxb-xjc_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.jaxb-xjc_jar")); //$NON-NLS-1$ //$NON-NLS-2$
			files
					.put(
							Frame2Plugin
									.getResourceString("Frame2ProjectWizard.jaxp-api_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.jaxp-api_jar")); //$NON-NLS-1$ //$NON-NLS-2$
			files
					.put(
							Frame2Plugin
									.getResourceString("Frame2ProjectWizard.jax-qname_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.jax-qname_jar")); //$NON-NLS-1$ //$NON-NLS-2$
			files
					.put(
							Frame2Plugin
									.getResourceString("Frame2ProjectWizard.jaxrpc_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.jaxrpc_jar")); //$NON-NLS-1$ //$NON-NLS-2$
			files
					.put(
							Frame2Plugin
									.getResourceString("Frame2ProjectWizard.jaxrpc-api_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.jaxrpc-api_jar")); //$NON-NLS-1$ //$NON-NLS-2$
			files
					.put(
							Frame2Plugin
									.getResourceString("Frame2ProjectWizard.namespace_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.namespace_jar")); //$NON-NLS-1$ //$NON-NLS-2$
			files
					.put(
							Frame2Plugin
									.getResourceString("Frame2ProjectWizard.saaj-api_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.saaj-api_jar")); //$NON-NLS-1$ //$NON-NLS-2$
			files
					.put(
							Frame2Plugin
									.getResourceString("Frame2ProjectWizard.wsdl4j_jar"), Frame2Plugin.getResourceString("Frame2ProjectWizard.wsdl4j_jar")); //$NON-NLS-1$ //$NON-NLS-2$
		}

		return files;
	}

	private void throwCoreException(final String message) throws CoreException {
		final IStatus status = new Status(IStatus.ERROR, "org.megatome.frame2", //$NON-NLS-1$
				IStatus.OK, message, null);
		throw new CoreException(status);
	}

	public void init(@SuppressWarnings("unused")
	final IWorkbench workbench, @SuppressWarnings("hiding")
	final IStructuredSelection selection) {
		this.selection = selection;
		setDefaultPageImageDescriptor(Frame2WizardSupport.getFrame2Logo());
	}
}