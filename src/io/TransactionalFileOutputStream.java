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
    private boolean migratable;

    public TransactionalFileOutputStream(OutputStream os) {
        outputStream = os;
        migratable = true;
    }

    public void write(int b) throws IOException {
        migratable = false;
        outputStream.write(b);
        outputStream.close();
        migratable = true;
        return;
    }

}
