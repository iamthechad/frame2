/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2007 Megatome Technologies.  All rights
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
package org.megatome.frame2;

public final class Globals {
	private Globals() {
		//Not Public
	}
	
	/**
     * The public ID to be used with the Frame2 Configuration DTD.
     */
    public static final String FRAME2_DTD_PUBLIC_ID = "-//Megatome Technologies//DTD Frame2 Configuration 1.1//EN"; //$NON-NLS-1$

    /**
     * The system ID to be used with the Frame2 1.0 Configuration DTD
     */
    public static final String FRAME2_DTD_SYSTEM_ID_1_0 = "http://frame2.sourceforge.net/dtds/frame2-config_1_0.dtd"; //$NON-NLS-1$
    
    /**
     * The public ID to be used with the Frame2 1.0 Configuration DTD.
     */
    public static final String FRAME2_DTD_PUBLIC_ID_1_0 = "-//Megatome Technologies//DTD Frame2 Configuration 1.0//EN"; //$NON-NLS-1$

    /**
     * The system ID to be used with the Frame2 Configuration DTD
     */
    public static final String FRAME2_DTD_SYSTEM_ID = "http://frame2.sourceforge.net/dtds/frame2-config_1_1.dtd"; //$NON-NLS-1$

    /**
     * The public ID to be used with the Frame2 Template DTD
     */
    public static final String FRAME2_TEMPLATE_DTD_PUBLIC_ID = "-//Megatome Technologies//DTD Frame2 Template Plugin 1.0//EN"; //$NON-NLS-1$

    /**
     * The system ID to be used with the Frame2 Template DTD
     */
    public static final String FRAME2_TEMPLATE_DTD_SYSTEM_ID = "http://frame2.sourceforge.net/dtds/frame2-template_1_0.dtd"; //$NON-NLS-1$

    /**
     * The file name of the Frame2 Configuration DTD
     */
    public static final String FRAME2_DTD_FILE = "frame2-config_1_1.dtd"; //$NON-NLS-1$
    
    /**
     * The file name of the Frame2 1.0 Configuration DTD
     */
    public static final String FRAME2_DTD_FILE_1_0 = "frame2-config_1_0.dtd"; //$NON-NLS-1$

    /**
     * The file name of the Frame2 Template DTD
     */
    public static final String FRAME2_TEMPLATE_DTD_FILE = "frame2-template_1_0.dtd"; //$NON-NLS-1$
}
