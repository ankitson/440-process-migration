package io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.concurrent.Semaphore;

/**
 * Created with IntelliJ IDEA.
 * User: ankit
 * Date: 9/12/13
 * Time: 6:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionalFileInputStream extends InputStream implements Serializable {

    private InputStream inputStream;
    long offset;
    private boolean migratable;

    public TransactionalFileInputStream(InputStream is) {
        inputStream = is;
        offset = 0;
        migratable = true;
    }

    /*
     * this one doesnt try to skop again iff skip returns < offset
     * opens and closes every single read rather than only before migration
     * no mutexes?
     */
    public int read() throws IOException {
        migratable = false;
        inputStream.skip(offset);
        int byteRead = inputStream.read();
        offset += 1;
        inputStream.close();
        migratable = true;
        return byteRead;
    }

    public boolean isMigratable() {
        return migratable;
    }

}
