package lab1;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: ankit
 * Date: 9/10/13
 * Time: 6:18 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MigratableProcess extends Runnable, Serializable {
    /*
     * Suspend this process
     */
    public void suspend();

    public String toString();

    public boolean isReadyToMigrate();
}
