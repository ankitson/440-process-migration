package lab1.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;

/**
 * This class is not thread-safe. If multiple threads need to read from the
 * same InputStream, they should instantiate separate instances of the class.
 *
 * It is resilient to being interrupted by a migration. OutputStreams in
 * general do not allow seeks, so this class is restricted to operating on
 * files.
 */
public class TransactionalFileOutputStream extends OutputStream implements Serializable {

    private String fileName;
    private long offset;
    private boolean migratable;

    public TransactionalFileOutputStream(String fileName) {
        this.fileName = fileName;
        offset = 0;
        migratable = true;
    }

    public void write(int b) throws IOException {
        migratable = false;
        RandomAccessFile file = new RandomAccessFile(fileName, "rws");
        file.seek(offset);
        file.write(b);
        offset++;
        file.close();
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
