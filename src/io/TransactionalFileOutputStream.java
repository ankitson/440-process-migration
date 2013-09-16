package io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * This class is not thread-safe. If multiple threads need to read from the
 * same InputStream, they should instantiate separate instances of the class.
 *
 * It is resilient to being interrupted by a migration.
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

    /*
     * Returns whether it is safe to migrate a process holding this instance
     * of the stream. It is not safe to migrate during writes, since that may
     * result in an inconsistent state between what has actually been written
     * and the offset into the stream.
    */
    public boolean isMigratable() {
        return migratable;
    }

}
