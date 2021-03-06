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
package servletunit.frame2;


import java.io.InputStream;
import java.io.IOException;
import javax.servlet.ServletInputStream;

public class RequestStream
    extends ServletInputStream {

    public RequestStream(int contentLength, InputStream is) {

        super();
        this.closed = false;
        this.count = 0;
        this.length = contentLength;
        this.stream = is;

    }

    protected boolean closed = false;


    protected int count = 0;


    protected int length = -1;

    protected InputStream stream = null;


    @Override
	public void close() throws IOException {

        if (this.closed)
            throw new IOException("Stream already closed"); //$NON-NLS-1$

        if (this.length > 0) {
            while (this.count < this.length) {
                int b = read();
                if (b < 0)
                    break;
            }
        }

        this.closed = true;

    }

    @Override
	public int read() throws IOException {

        // Has this stream been closed?
        if (this.closed)
            throw new IOException("The stream has been closed"); //$NON-NLS-1$

        // Have we read the specified content length already?
        if ((this.length >= 0) && (this.count >= this.length))
            return (-1);        // End of file indicator

        // Read and count the next byte, then return it
        int b = this.stream.read();
        if (b >= 0)
            this.count++;
        return (b);

    }


    @Override
	public int read(byte b[]) throws IOException {

        return (read(b, 0, b.length));

    }


    @Override
	public int read(byte b[], int off, int len) throws IOException {

        int toRead = len;
        if (this.length > 0) {
            if (this.count >= this.length)
                return (-1);
            if ((this.count + len) > this.length)
                toRead = this.length - this.count;
        }
        int actuallyRead = super.read(b, off, toRead);
        return (actuallyRead);

    }
}
