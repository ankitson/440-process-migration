package io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * This class is not thread-safe. If multiple threads need to read from the
 * same InputStream, they should instantiate separate instances of the class.
 *
 * It is resilient to being interrupted by a migration.
 */
public class TransactionalFileInputStream extends InputStream implements Serializable {

    private InputStream inputStream;
    private int offset;
    private boolean migratable;

    public TransactionalFileInputStream(InputStream is) {
        inputStream = is;
        offset = 0;
        migratable = true;
    }

    /*
     * this one doesnt try to skop again iff skip returns < offset
     * opens and closes every single read rather than only before migration
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

    /*
     * Returns whether it is safe to migrate a process holding this instance
     * of the stream. It is not safe to migrate during reads, since that may
     * result in an inconsistent state between what has actually been read and
     * the offset into the stream.
     */
    public boolean isMigratable() {
        return migratable;
    }

}