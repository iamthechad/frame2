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
package org.megatome.frame2.taglib.html.el;

import javax.servlet.jsp.JspException;

import org.megatome.frame2.taglib.html.Constants;
import org.megatome.frame2.tagsupport.util.HTMLHelpers;

// NIT: Some of these are IE specific and need to be documented as such.

/**
 *
 */
public class ImgTag extends BaseHtmlTag {

	private static final long serialVersionUID = 6141962834372744724L;
	private String _align;
    private String _border;
    private String _datafld;
    private String _datasrc;
    private String _dynsrc;
    private String _galleryimg;
    private String _height;
    private String _hspace;
    private String _ismap;
    private String _longdesc;
    private String _loop;
    private String _lowsrc;
    private String _src;
    private String _start;
    private String _suppress;
    private String _usemap;
    private String _vspace;
    private String _width;

    @Override
	protected void setType() {
        this._type = Constants.IMG;
    }

    @Override
	protected void setTagName() {
        this.tagName = Constants.IMG;
    }


    /**
     * Sets the align.
     * @param align The align to set
     */
    public void setAlign(String align) {
        this._align = align;
    }

    /**
     * Sets the border.
     * @param border The border to set
     */
    public void setBorder(String border) {
        this._border = border;
    }

    /**
     * Sets the datafld.
     * @param datafld The datafld to set
     */
    public void setDatafld(String datafld) {
        this._datafld = datafld;
    }

    /**
     * Sets the datasrc.
     * @param datasrc The datasrc to set
     */
    public void setDatasrc(String datasrc) {
        this._datasrc = datasrc;
    }

    /**
     * Sets the dynsrc.
     * @param dynsrc The datasrc to set
     */
    public void setDynsrc(String dynsrc) {
        this._dynsrc = dynsrc;
    }
    /**
     * Sets the galleryimage.
     * @param galleryimage The galleryimage to set
     */
    public void setGalleryimg(String galleryimage) {
        this._galleryimg = galleryimage;
    }

    /**
     * Sets the height.
     * @param height The height to set
     */
    public void setHeight(String height) {
        this._height = height;
    }

    /**
     * Sets the hspace.
     * @param hspace The hspace to set
     */
    public void setHspace(String hspace) {
        this._hspace = hspace;
    }

    /**
     * Sets the ismap.
     * @param ismap The ismap to set
     */
    public void setIsmap(String ismap) {
        this._ismap = ismap;
    }

    /**
     * Sets the longdesc.
     * @param longdesc The longdesc to set
     */
    public void setLongdesc(String longdesc) {
        this._longdesc = longdesc;
    }

    /**
     * Sets the loop.
     * @param loop The loop to set
     */
    public void setLoop(String loop) {
        this._loop = loop;
    }

    /**
     * Sets the lowsrc.
     * @param lowsrc The lowsrc to set
     */
    public void setLowsrc(String lowsrc) {
        this._lowsrc = lowsrc;
    }

    /**
     * Sets the src.
     * @param src The src to set
     */
    public void setSrc(String src) {
        this._src = src;
    }

    /**
     * Sets the start.
     * @param start The start to set
     */
    public void setStart(String start) {
        this._start = start;
    }

    /**
     * Sets the suppress.
     * @param suppress The suppress to set
     */
    public void setSuppress(String suppress) {
        this._suppress = suppress;
    }

    /**
     * Sets the usemap.
     * @param usemap The usemap to set
     */
    public void setUsemap(String usemap) {
        this._usemap = usemap;
    }

    /**
     * Sets the vspace.
     * @param vspace The vspace to set
     */
    public void setVspace(String vspace) {
        this._vspace = vspace;
    }

    /**
     * Sets the width.
     * @param width The width to set
     */
    public void setWidth(String width) {
        this._width = width;
    }

    @Override
	protected void specialAttrHandler() throws JspException {
        super.specialAttrHandler();
        this._align = handleAttr(Constants.ALIGN, this._align);
        this._border = handleAttr(Constants.BORDER, this._border);
        this._datafld = handleAttr(Constants.DATAFLD, this._datafld);
        this._datasrc = handleAttr(Constants.DATASRC, this._datasrc);
        this._dynsrc = handleAttr(Constants.DYNSRC, this._dynsrc);
        this._galleryimg = handleAttr(Constants.GALLERYIMG, this._galleryimg);
        this._height = handleAttr(Constants.HEIGHT, this._height);
        this._hspace = handleAttr(Constants.HSPACE, this._hspace);
        this._ismap = handleAttr(Constants.ISMAP, this._ismap);
        this._longdesc = handleAttr(Constants.LONGDESC, this._longdesc);
        this._loop = handleAttr(Constants.LOOP, this._loop);
        this._lowsrc = handleAttr(Constants.LOWSRC, this._lowsrc);
        this._src = handleAttr(Constants.SRC, this._src);
        this._start = handleAttr(Constants.START, this._start);
        this._suppress = handleAttr(Constants.SUPPRESS, this._suppress);
        this._usemap = handleAttr(Constants.USEMAP, this._usemap);
        this._vspace = handleAttr(Constants.VSPACE, this._vspace);
        this._width = handleAttr(Constants.WIDTH, this._width);
    }

    @Override
	protected StringBuffer buildStartTag() throws JspException {
        StringBuffer result = new StringBuffer();
        result.append(Constants.IMG_TAG);
        result.append(genTagAttrs());
        result.append(HTMLHelpers.buildHtmlAttr(Constants.ALIGN,this._align));
        result.append(HTMLHelpers.buildHtmlAttr(Constants.BORDER, this._border));
        result.append(HTMLHelpers.buildHtmlAttr(Constants.DATAFLD, this._datafld));
        result.append(HTMLHelpers.buildHtmlAttr(Constants.DATASRC, this._datasrc));
        result.append(HTMLHelpers.buildHtmlAttr(Constants.DYNSRC, this._dynsrc));
        result.append(HTMLHelpers.buildHtmlAttr(Constants.GALLERYIMG, this._galleryimg));
        result.append(HTMLHelpers.buildHtmlAttr(Constants.HEIGHT, this._height));
        result.append(HTMLHelpers.buildHtmlAttr(Constants.HSPACE, this._hspace));
        result.append(HTMLHelpers.buildHtmlAttr(Constants.ISMAP, this._ismap));
        result.append(HTMLHelpers.buildHtmlAttr(Constants.LONGDESC, this._longdesc));
        result.append(HTMLHelpers.buildHtmlAttr(Constants.LOOP, this._loop));
        result.append(HTMLHelpers.buildHtmlAttr(Constants.LOWSRC, this._lowsrc));
        result.append(HTMLHelpers.buildHtmlAttr(Constants.SRC, this._src));
        result.append(HTMLHelpers.buildHtmlAttr(Constants.START, this._start));
        result.append(HTMLHelpers.buildHtmlAttr(Constants.SUPPRESS, this._suppress));
        result.append(HTMLHelpers.buildHtmlAttr(Constants.USEMAP, this._usemap));
        result.append(HTMLHelpers.buildHtmlAttr(Constants.VSPACE, this._vspace));
        result.append(HTMLHelpers.buildHtmlAttr(Constants.WIDTH, this._width));

        return result;
    }

   @Override
   protected void clear() {
      super.clear();
      this._align = null;
      this._border = null;
      this._datafld = null;
      this._datasrc = null;
      this._dynsrc = null;
      this._galleryimg = null;
      this._height = null;
      this._hspace = null;
      this._ismap = null;
      this._longdesc = null;
      this._loop = null;
      this._lowsrc = null;
      this._src = null;
      this._start = null;
      this._suppress = null;
      this._usemap = null;
      this._vspace = null;
      this._width = null;
   }

   @Override
   public void release() {
      super.release();
      clear();
   }

}
