
import java.io.*;
import java.util.Random;

public class TrainSensor implements Runnable {

    private long initial;
    private long stamp;
    private String fileName;
    private Controller ctrl;

    public TrainSensor() {

    }

    public TrainSensor(long timer, String fileName, Controller ctrl) {
        this.initial = timer;
        this.fileName = fileName;
        this.ctrl = ctrl;
    }

    @Override
    public void run() {
        String input;
        Random rand = new Random();
        int sleepTime = rand.nextInt(10000) + 20000;
        try {
            BufferedReader rd = new BufferedReader(new FileReader(fileName));
            while ((input = rd.readLine()) != null) {
                Thread.sleep(sleepTime);
                stamp = (System.currentTimeMillis() - initial) / 100 * 100;
                System.out.println(stamp + " S " + input);
                ctrl.manageTrain(input);
            }

        } catch (IOException | InterruptedException e) {
        }
    }
}
