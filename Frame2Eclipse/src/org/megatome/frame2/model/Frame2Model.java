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
package org.megatome.frame2.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.megatome.frame2.model.Frame2Config.ValidateException;
import org.megatome.frame2.util.Frame2EntityResolver;
import org.xml.sax.InputSource;
import org.megatome.frame2.Frame2Plugin;

public class Frame2Model {

	private static Frame2Config config = null;

	private static Frame2Model instance = null;

	private static String fileName = null;

	private Frame2Model(final String configFile) throws Frame2ModelException {
		fileName = configFile;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(fileName);
		} catch (final FileNotFoundException e) {
			throw new Frame2ModelException(
					Frame2Plugin
							.getResourceString("Frame2Model.errorLoadingConfig") + configFile, e); //$NON-NLS-1$
		}
		try {
			config = Frame2Config.read(new InputSource(fis), true, new Frame2EntityResolver(),
					new Frame2ErrorHandler());
		} catch (final Exception e1) {
			throw new Frame2ModelException(Frame2Plugin
					.getResourceString("Frame2Model.errorLoadingConfig"), e1); //$NON-NLS-1$
		}
	}

	public static Frame2Model getInstance(final String configFile)
			throws Frame2ModelException {
		instance = new Frame2Model(configFile);
		return instance;
	}

	public static Frame2Model getInstance() {
		return instance;
	}

	public void persistConfiguration() throws Frame2ModelException {
		try {
			config.validate();
			final FileOutputStream fos = new FileOutputStream(fileName);
			config.write(fos);
		} catch (final Exception e) {
			throw new Frame2ModelException(
					Frame2Plugin
							.getResourceString("Frame2Model.errorPersistingConfiguration"), e); //$NON-NLS-1$
		}
	}

	public Frame2Event[] getEvents() {
		return config.getFrame2Events().getFrame2Event();
	}

	public EventHandler[] getEventHandlers() {
		return config.getEventHandlers().getEventHandler();
	}

	public EventMapping[] getEventMappings() {
		return config.getEventMappings().getEventMapping();
	}

	public Frame2Exception[] getExceptions() {
		return config.getExceptions().getFrame2Exception();
	}

	public Forward[] getGlobalForwards() {
		return config.getGlobalForwards().getForward();
	}

	public Plugin[] getPlugins() {
		return config.getPlugins().getPlugin();
	}

	public HttpRequestProcessor getHttpRequestProcessor() {
		return config.getRequestProcessors().getHttpRequestProcessor();
	}

	public SoapRequestProcessor getSoapRequestProcessor() {
		return config.getRequestProcessors().getSoapRequestProcessor();
	}

	public void addEventHandler(final EventHandler handler)
			throws Frame2ModelException {
		final EventHandlers handlers = config.getEventHandlers();
		handlers.addEventHandler(handler);
		try {
			handlers.validate();
		} catch (final ValidateException e) {
			throw new Frame2ModelException(
					Frame2Plugin
							.getResourceString("Frame2Model.errorAddingEventHandler"), e); //$NON-NLS-1$
		}
	}

	public void addEventMapping(final EventMapping mapping)
			throws Frame2ModelException {
		final EventMappings mappings = config.getEventMappings();
		mappings.addEventMapping(mapping);
		try {
			mappings.validate();
		} catch (final ValidateException e) {
			throw new Frame2ModelException(
					Frame2Plugin
							.getResourceString("Frame2Model.errorAddingEventMapping"), e); //$NON-NLS-1$
		}
	}

	public void addEvent(final Frame2Event event) throws Frame2ModelException {
		final Frame2Events events = config.getFrame2Events();
		events.addFrame2Event(event);
		try {
			events.validate();
		} catch (final ValidateException e) {
			throw new Frame2ModelException(Frame2Plugin
					.getResourceString("Frame2Model.errorAddingEvent"), e); //$NON-NLS-1$
		}
	}

	public void addFrame2Exception(final Frame2Exception exception)
			throws Frame2ModelException {
		final Exceptions exceptions = config.getExceptions();
		exceptions.addFrame2Exception(exception);
		try {
			exceptions.validate();
		} catch (final ValidateException e) {
			throw new Frame2ModelException(Frame2Plugin
					.getResourceString("Frame2Model.errorAddingException"), e); //$NON-NLS-1$
		}
	}

	public void addGlobalForward(final Forward forward)
			throws Frame2ModelException {
		final GlobalForwards forwards = config.getGlobalForwards();
		forwards.addForward(forward);
		try {
			forwards.validate();
		} catch (final ValidateException e) {
			throw new Frame2ModelException(
					Frame2Plugin
							.getResourceString("Frame2Model.errorAddingGlobalForward"), e); //$NON-NLS-1$
		}
	}

	public void addPlugin(final Plugin plugin) {
		final Plugins plugins = config.getPlugins();
		plugins.addPlugin(plugin);
		config.setPlugins(plugins);
	}

	public void setHttpRequestProcessor(final HttpRequestProcessor processor) {
		final RequestProcessors processors = config.getRequestProcessors();
		processors.setHttpRequestProcessor(processor);
		config.setRequestProcessors(processors);
	}

	public void setSoapRequestProcessor(final SoapRequestProcessor processor) {
		final RequestProcessors processors = config.getRequestProcessors();
		processors.setSoapRequestProcessor(processor);
		config.setRequestProcessors(processors);
	}

}