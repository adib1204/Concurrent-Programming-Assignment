
import java.io.*;
import java.util.*;

/**
 * Class to read direction from input file (Example: N.txt)
 * Print the timestamp of sensor at the output
 * Notify the controller to make decision
 */
public class RoadSensor implements Runnable {

    private long initial;
    private long stamp;
    private String fileName;
    Controller ctrl;

    /**
     * Create an instance of class Sensor.
     * @param timer - The initial time which is the time program start 
     * @param fileName - Name of the file to read (*.txt)
     * @param ctrl - An object instantiated from class Controller
     */
    public RoadSensor(long timer, String fileName, Controller ctrl) {
        this.initial = timer;
        this.fileName = fileName;
        this.ctrl = ctrl;
    }

    @Override
    public void run() {
        String input;
        Random rand = new Random();
        int sleepTime;
        
        try {
            // Give time for class Input to write into the input file
            Thread.sleep(100); 
            BufferedReader rd = new BufferedReader(new FileReader(fileName));

            while ((input = rd.readLine()) != null) {
                sleepTime = rand.nextInt(15000) + 1000;
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
