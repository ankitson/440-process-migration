/**
 * Created with IntelliJ IDEA.
 * User: ankit
 * Date: 9/10/13
 * Time: 7:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class CountingProcess implements MigratableProcess {

    private int counter = 0;
    private volatile boolean suspended;

    public CountingProcess() {
        suspended = false;
    }

    public void run() {
        while (true) {
            while (!suspended) {
                System.out.println(counter + " bottles of beer on the allah wall");
                counter++;
                if (counter == 1000) {
                    System.out.println("done running");
                    return;
                }
            }
            suspended = false;
        }
    }

    public void suspend() {
        suspended = true;
    }

    public void resume() {
        suspended = false;
    }

    public static void main(String[] args) throws InterruptedException {
        CountingProcess p = new CountingProcess();
        new Thread(p).start();

        Thread.sleep(1);

        p.suspend();
        System.out.println("Current state: " + p);
        p.resume();
    }

    public String toString() {
        return "Counting process at: " + counter;
    }
}
