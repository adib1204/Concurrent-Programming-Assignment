
import java.io.*;
import java.util.*;

/**
 * Class ni untuk read input daripada text file sahaja Lepas dia dapat input
 * hantar ke class Controller
 */
public class Sensor implements Runnable {

    private long initial;
    private long stamp;
    private String fileName;
    Controller ctrl;

    public Sensor(long timer, String fileName, Controller ctrl) {
        this.initial = timer;
        this.fileName = fileName;
        this.ctrl = ctrl;
    }

    @Override
    public void run() {
        String input;
        Random rand = new Random();
        int sleepTime = rand.nextInt(15000) + 1000;

        try {
            Thread.sleep(100); // Give time to write input file
            BufferedReader rd = new BufferedReader(new FileReader(fileName));

            while ((input = rd.readLine()) != null) {
                Thread.sleep(sleepTime);
                stamp = (System.currentTimeMillis() - initial) / 100 * 100;
                System.out.println(stamp + " S " + input);
                ctrl.addVehicle(input);
                ctrl.manageVehicle();
            }

        } catch (IOException | InterruptedException e) {
        }
    }

}
