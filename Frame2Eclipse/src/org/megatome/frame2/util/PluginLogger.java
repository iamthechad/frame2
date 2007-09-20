package org.megatome.frame2.util;

import org.eclipse.core.runtime.IStatus;
import org.megatome.frame2.Frame2Plugin;

public final class PluginLogger {
	private PluginLogger() {
		// Not Public
	}
	
	public static void info(final String message) {
		log(StatusFactory.info(message));
	}
	
	public static void warning(final String message) {
		log(StatusFactory.warning(message));
	}
	
	public static void error(final String message) {
		log(StatusFactory.error(message));
	}
	
	public static void error(final String message, final Throwable t) {
		log(StatusFactory.error(message, t));
	}
	
	private static void log(final IStatus status) {
		Frame2Plugin.getDefault().getLog().log(status);
	}
}
