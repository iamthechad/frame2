package org.megatome.frame2.decorators;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.megatome.frame2.Frame2Plugin;
import org.megatome.frame2.builder.Frame2Nature;
import org.megatome.frame2.util.PluginLogger;

public class Frame2ProjectDecorator implements ILightweightLabelDecorator {
	private ImageDescriptor decorator = null;

	public Frame2ProjectDecorator() {
		this.decorator = loadDecoratorImage();
	}

	public void decorate(Object element, IDecoration decoration) {
		if (element instanceof IProject) {
			IProject project = (IProject)element;
			if ((this.decorator != null) && (hasFrame2Nature(project))) {
				decoration.addOverlay(this.decorator, IDecoration.BOTTOM_LEFT);
			}
		}
	}

	public void addListener(@SuppressWarnings("unused")
	ILabelProviderListener listener) {
		//NOOP
	}

	public void dispose() {
		//NOOP
	}

	public boolean isLabelProperty(@SuppressWarnings("unused")
	Object element, @SuppressWarnings("unused")
	String property) {
		return false;
	}

	public void removeListener(@SuppressWarnings("unused")
	ILabelProviderListener listener) {
		//NOOP
	}

	private ImageDescriptor loadDecoratorImage() {
		final String imageFile = Frame2Plugin.getResourceString("Frame2ProjectDecorator.projectDecoratorIcon"); //$NON-NLS-1$

		try {
			final Frame2Plugin plugin = Frame2Plugin.getDefault();
			final URL installURL = plugin.getBundle().getEntry("/"); //$NON-NLS-1$
			final URL url = new URL(installURL, imageFile);
			return ImageDescriptor.createFromURL(url);
		} catch (final MalformedURLException e) {
			return null;
		}
	}
	
	private boolean hasFrame2Nature(final IProject project) {
		try {
			IProjectDescription description = project.getDescription();
			String[] natureIds = description.getNatureIds();
			for (String id : natureIds) {
				if (Frame2Nature.NATURE_ID.equals(id)) {
					return true;
				}
			}
		} catch (CoreException e) {
			PluginLogger.error("Could not get project description", e); //$NON-NLS-1$
		}
		
		return false;
	}
}
