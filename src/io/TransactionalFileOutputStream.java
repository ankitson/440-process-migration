package io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: ankit
 * Date: 9/13/13
 * Time: 9:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionalFileOutputStream extends OutputStream implements Serializable {

    private OutputStream outputStream;

    public TransactionalFileOutputStream(OutputStream os) {
        outputStream = os;
    }

    public void write(int b) throws IOException {
        outputStream.write(b);
    }
}
