package servletunit.frame2;


import java.io.InputStream;
import java.io.IOException;
import javax.servlet.ServletInputStream;

public class RequestStream
    extends ServletInputStream {

    public RequestStream(int contentLength, InputStream is) {

        super();
        closed = false;
        count = 0;
        length = contentLength;
        stream = is;

    }

    protected boolean closed = false;


    protected int count = 0;


    protected int length = -1;

    protected InputStream stream = null;


    public void close() throws IOException {

        if (closed)
            throw new IOException("Stream already closed");

        if (length > 0) {
            while (count < length) {
                int b = read();
                if (b < 0)
                    break;
            }
        }

        closed = true;

    }

    public int read() throws IOException {

        // Has this stream been closed?
        if (closed)
            throw new IOException("The stream has been closed");

        // Have we read the specified content length already?
        if ((length >= 0) && (count >= length))
            return (-1);        // End of file indicator

        // Read and count the next byte, then return it
        int b = stream.read();
        if (b >= 0)
            count++;
        return (b);

    }


    public int read(byte b[]) throws IOException {

        return (read(b, 0, b.length));

    }


    public int read(byte b[], int off, int len) throws IOException {

        int toRead = len;
        if (length > 0) {
            if (count >= length)
                return (-1);
            if ((count + len) > length)
                toRead = length - count;
        }
        int actuallyRead = super.read(b, off, toRead);
        return (actuallyRead);

    }
}
