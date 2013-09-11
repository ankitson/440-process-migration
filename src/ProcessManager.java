import java.io.*;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: ankit
 * Date: 9/10/13
 * Time: 6:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcessManager {

    public void startProcess(MigratableProcess p) {
        p.run();
    }

    public int migrate(MigratableProcess p, String hostname, int port) {
        p.suspend();
        try {
            Socket sendSocket = new Socket(hostname, port);
            ObjectOutputStream outputStream = new ObjectOutputStream(sendSocket.getOutputStream());
            outputStream.writeObject(p);
        } catch (IOException e) {
            System.err.println("Unable to open socket for writing");
            return -1;
        }
        return 0;
    }

    public int receieveSerializedProcessAndResume(Socket clientSocket) {
        try (ObjectInputStream inputObject = new ObjectInputStream(clientSocket.getInputStream());) {
            MigratableProcess process = (MigratableProcess) inputObject.readObject();
            process.resume();
            return 0;
        } catch (IOException e) {
            System.err.println("Unable to receive serialized process:  " + e);
            return -1;
        } catch (ClassNotFoundException e) {
            System.err.println("Only MigratableProcesses can be sent: " + e);
            return -1;
        }
    }
}
