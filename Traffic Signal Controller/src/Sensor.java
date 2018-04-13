import java.util.concurrent.*;
import java.io.*;
import java.util.*;
/**
 * Class ni untuk read input daripada text file sahaja
 * Lepas dia dapat input hantar ke class Controller
 */
public class Sensor implements Runnable {

    private long initial;
    private long stamp;
    private String fileName;
    Controller ctrl = new Controller();

    public Sensor(long timer, String fileName) {
        this.initial = timer;
        this.fileName = fileName;
    }

    public void run() {
        String input;
        Random rand = new Random();
        int sleepTime = rand.nextInt(25000) + 5000;
        
        try {
            Thread.sleep(1000); // Give time to write input file
            BufferedReader rd = new BufferedReader(new FileReader(fileName));
            
            while ((input = rd.readLine()) != null) {
                Thread.sleep(sleepTime);
                stamp = (System.currentTimeMillis() - initial) / 100 * 100;
                System.out.println(stamp + " S " + input);
                ctrl.addVehicle(input.charAt(0));
                ctrl.manageLight(stamp);
            }
            
        } catch (IOException | InterruptedException e) {
        }
    }

}
