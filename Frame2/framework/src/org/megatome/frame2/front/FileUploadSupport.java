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
package org.megatome.frame2.front;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.megatome.frame2.Frame2Exception;
import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerFactory;


public final class FileUploadSupport {
    
    private FileUploadSupport() {}
    
    private static Logger getLogger() {
        return LoggerFactory.instance(FileUploadSupport.class.getName());
    } 
    
    public static Map processMultipartRequest(final HttpServletRequest request) throws Frame2Exception {
        Map parameters = new HashMap();

        DiskFileUpload fileUpload = new DiskFileUpload();
		fileUpload.setSizeMax(FileUploadConfig.getMaxFileSize());
		fileUpload.setSizeThreshold(FileUploadConfig.getBufferSize());
		fileUpload.setRepositoryPath(FileUploadConfig.getFileTempDir());
		
		List fileItems = null;
		try {
			fileItems = fileUpload.parseRequest(request);
		} catch (FileUploadException fue) {
		    getLogger().severe("File Upload Error", fue);
			throw new Frame2Exception("File Upload Exception", fue);
		}
		
		for (final Iterator i = fileItems.iterator(); i.hasNext(); ) {
		    FileItem fi = (FileItem)i.next();
			String fieldName = fi.getFieldName();
			
			if (fi.isFormField()) {
				if (parameters.containsKey(fieldName)) {
					List tmpArray = new ArrayList();
					if (parameters.get(fieldName) instanceof String[]) {
						String[] origValues = (String[])parameters.get(fieldName);
						for (int idx = 0; idx < origValues.length; idx++) {
							tmpArray.add(origValues[idx]);
						}
						tmpArray.add(fi.getString());
					} else {
						tmpArray.add(parameters.get(fieldName));
						tmpArray.add(fi.getString());
					}
					String[] newValues = new String[tmpArray.size()];
					newValues = (String[])tmpArray.toArray(newValues);
					parameters.put(fieldName, newValues);
				} else {
					parameters.put(fieldName, fi.getString());
				}
			} else {
				if (parameters.containsKey(fieldName)) {
					List tmpArray = new ArrayList();
					if (parameters.get(fieldName) instanceof FileItem[]) {
					    FileItem[] origValues = (FileItem[])parameters.get(fieldName);
						for (int idx = 0; idx < origValues.length; idx++) {
							tmpArray.add(origValues[idx]);
						}
						tmpArray.add(fi);
					} else {
						tmpArray.add(parameters.get(fieldName));
						tmpArray.add(fi);
					}						
					FileItem[] newValues = new FileItem[tmpArray.size()];
					newValues = (FileItem[])tmpArray.toArray(newValues);
					parameters.put(fieldName, newValues);
				} else {
					parameters.put(fieldName, fi);
				}
			}
		}
		
		return parameters;
    }

}
