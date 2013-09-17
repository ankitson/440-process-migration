package lab1.processes;

import lab1.MigratableProcess;
import lab1.io.TransactionalFileInputStream;
import lab1.io.TransactionalFileOutputStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Utility to zip a single file
 */
public class ZipUtility implements MigratableProcess {

    private String fileName;
    private volatile boolean suspended;

    TransactionalFileOutputStream fos;
    ZipOutputStream zos;
    ZipEntry ze;
    TransactionalFileInputStream fis;


    public ZipUtility(String[] args) throws FileNotFoundException {
        if (args.length < 2) {
            System.out.println("Usage: ZipUtility <filename>");
            throw new IllegalArgumentException();
        }
        suspended = false;
        fileName = args[1];
        fos = new TransactionalFileOutputStream(fileName + ".zip");
        zos = new ZipOutputStream(fos);
        ze = new ZipEntry(fileName);
        fis = new TransactionalFileInputStream(new FileInputStream(fileName));
    }

    public void run() {
        suspended = false;
        try {
            int nextByte = fis.read();
            while (!suspended && nextByte != -1) {
                zos.write(nextByte);
                nextByte = fis.read();
            }
            fis.close();
            zos.closeEntry();
            zos.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e);
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            System.exit(-1);
        }
        System.out.println("Done compressing " + fileName +" !");
    }

    public boolean isReadyToMigrate() {
        return fos.isMigratable() && fis.isMigratable();
    }

    public void suspend() {
        suspended = true;
    }

}
