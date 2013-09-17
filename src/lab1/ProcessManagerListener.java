package lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: ankit
 * Date: 9/10/13
 * Time: 5:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcessManagerListener {

    private static Integer LISTEN_PORT = 69;
    private static ServerSocket LISTEN_SOCKET;
    private static ProcessManager PROCESS_MANAGER;

    public ProcessManagerListener() {
        try {
            LISTEN_SOCKET = new ServerSocket(LISTEN_PORT);
        } catch (IOException e) {
            System.err.println("Unable to listen on port " + LISTEN_PORT + ".Message: " + e);
            System.exit(1);
        }

        while(true) {
            Socket clientSocket = null;
            BufferedReader in = null;
            PrintWriter out = null;
            try {
                clientSocket = LISTEN_SOCKET.accept();
                PROCESS_MANAGER.receieveSerializedProcessAndResume(clientSocket);
            } catch (IOException e) {
                System.err.println("Unable to accept connection");
                System.exit(1);
            }
        }
    }
}
