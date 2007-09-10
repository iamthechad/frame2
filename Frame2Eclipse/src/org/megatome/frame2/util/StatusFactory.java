package org.megatome.frame2.util;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.megatome.frame2.Frame2Plugin;

public final class StatusFactory {
	private StatusFactory() {
		//Not Public
	}
	
	public static IStatus error(final String message) {
		return new Status(IStatus.ERROR, Frame2Plugin.getPluginId(), message);
	}
	
	public static IStatus ok() {
		return new Status(IStatus.OK, Frame2Plugin.getPluginId(), ""); //$NON-NLS-1$
	}
}
