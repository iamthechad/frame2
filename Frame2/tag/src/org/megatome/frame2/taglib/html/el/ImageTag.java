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
package org.megatome.frame2.taglib.html.el;

import javax.servlet.jsp.JspException;

import org.megatome.frame2.taglib.html.Constants;
import org.megatome.frame2.tagsupport.TagConstants;
import org.megatome.frame2.tagsupport.util.HTMLHelpers;

/**
 * 
 */
public class ImageTag extends BaseInputTag {

	private String _align;
	private String _border;
	private String _datafld;
	private String _datasrc;
	private String _dynsrc;
	private String _height;
	private String _width;
	private String _hspace;
	private String _vspace;
	private String _ismap;
	private String _loop;
	private String _lowsrc;
	private String _src;
	private String _start;
	private String _usemap;

	protected void setType() {
		_type = Constants.IMAGE;
	}

	/**
	 * Sets the align.
	 * @param align The align to set
	 */
	public void setAlign(String align) {
		_align = align;
	}

	/**
	 * Sets the border.
	 * @param border The border to set
	 */
	public void setBorder(String border) {
		_border = border;
	}

	/**
	 * Sets the datafld.
	 * @param datafld The datafld to set
	 */
	public void setDatafld(String datafld) {
		_datafld = datafld;
	}

	/**
	 * Sets the datasrc.
	 * @param datasrc The datasrc to set
	 */
	public void setDatasrc(String datasrc) {
		_datasrc = datasrc;
	}

	/**
	 * Sets the dynsrc.
	 * @param dynsrc The dynsrc to set
	 */
	public void setDynsrc(String dynsrc) {
		_dynsrc = dynsrc;
	}

	/**
	 * Sets the height.
	 * @param height The height to set
	 */
	public void setHeight(String height) {
		_height = height;
	}

	/**
	 * Sets the hspace.
	 * @param hspace The hspace to set
	 */
	public void setHspace(String hspace) {
		_hspace = hspace;
	}

	/**
	 * Sets the ismap.
	 * @param ismap The ismap to set
	 */
	public void setIsmap(String ismap) {
		_ismap = ismap;
	}

	/**
	 * Sets the loop.
	 * @param loop The loop to set
	 */
	public void setLoop(String loop) {
		_loop = loop;
	}

	/**
	 * Sets the lowsrc.
	 * @param lowsrc The lowsrc to set
	 */
	public void setLowsrc(String lowsrc) {
		_lowsrc = lowsrc;
	}

	/**
	 * Sets the src.
	 * @param src The src to set
	 */
	public void setSrc(String src) {
		_src = src;
	}

	/**
	 * Sets the start.
	 * @param start The start to set
	 */
	public void setStart(String start) {
		_start = start;
	}

	/**
	 * Sets the usemap.
	 * @param usemap The usemap to set
	 */
	public void setUsemap(String usemap) {
		_usemap = usemap;
	}

	/**
	 * Sets the vspace.
	 * @param vspace The vspace to set
	 */
	public void setVspace(String vspace) {
		_vspace = vspace;
	}

	/**
	 * Sets the width.
	 * @param width The width to set
	 */
	public void setWidth(String width) {
		_width = width;
	}

	protected void specialAttrHandler() throws JspException {
		super.specialAttrHandler();
		_align = handleAttr(Constants.ALIGN, _align);
		_border = handleAttr(Constants.BORDER, _border);
		_datafld = handleAttr(Constants.DATAFLD, _datafld);
		_datasrc = handleAttr(Constants.DATASRC, _datasrc);
		_dynsrc = handleAttr(Constants.DYNSRC, _dynsrc);
		_height = handleAttr(Constants.HEIGHT, _height);
		_hspace = handleAttr(Constants.HSPACE, _hspace);
		_ismap = handleAttr(Constants.ISMAP, _ismap);
		_loop = handleAttr(Constants.LOOP, _loop);
		_lowsrc = handleAttr(Constants.LOWSRC, _lowsrc);
		_src = handleAttr(Constants.SRC, _src);
		_start = handleAttr(Constants.START, _start);
		_usemap = handleAttr(Constants.USEMAP, _usemap);
		_vspace = handleAttr(Constants.VSPACE, _vspace);
		_width = handleAttr(Constants.WIDTH, _width);
	}

	protected StringBuffer buildStartTag() throws JspException {
		StringBuffer result = new StringBuffer();
		result.append(Constants.INPUT_TYPE + TagConstants.QUOTE);
		result.append(getType());
		result.append(TagConstants.QUOTE);
		result.append(genTagAttrs());
		result.append(HTMLHelpers.buildHtmlAttr(Constants.ALIGN, _align));
		result.append(HTMLHelpers.buildHtmlAttr(Constants.BORDER, _border));
		result.append(HTMLHelpers.buildHtmlAttr(Constants.DATAFLD, _datafld));
		result.append(HTMLHelpers.buildHtmlAttr(Constants.DATASRC, _datasrc));
		result.append(HTMLHelpers.buildHtmlAttr(Constants.DYNSRC, _dynsrc));
		result.append(HTMLHelpers.buildHtmlAttr(Constants.HEIGHT, _height));
		result.append(HTMLHelpers.buildHtmlAttr(Constants.HSPACE, _hspace));
		result.append(HTMLHelpers.buildHtmlAttr(Constants.ISMAP, _ismap));
		result.append(HTMLHelpers.buildHtmlAttr(Constants.LOOP, _loop));
		result.append(HTMLHelpers.buildHtmlAttr(Constants.LOWSRC, _lowsrc));
		result.append(HTMLHelpers.buildHtmlAttr(Constants.SRC, _src));
		result.append(HTMLHelpers.buildHtmlAttr(Constants.START, _start));
		result.append(HTMLHelpers.buildHtmlAttr(Constants.USEMAP, _usemap));
		result.append(HTMLHelpers.buildHtmlAttr(Constants.VSPACE, _vspace));
		result.append(HTMLHelpers.buildHtmlAttr(Constants.WIDTH, _width));
		return result;
	}

   protected void clear() {
      super.clear();
      _align = null;
      _border = null;
      _datafld = null;
      _datasrc = null;
      _dynsrc = null;
      _height = null;
      _width = null;
      _hspace = null;
      _vspace = null;
      _ismap = null;
      _loop = null;
      _lowsrc = null;
      _src = null;
      _start = null;
      _usemap = null;
   }

   public void release() {
      super.release();
      clear();
   }

}
