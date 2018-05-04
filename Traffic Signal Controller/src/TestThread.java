
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Class ni untuk start thread sahaja Jangan tambah apa2 variable method yang
 * boleh affect operation program ni
 */
public class TestThread {

    public static void main(String[] args) {

        long initial = System.currentTimeMillis();
        String nameFile;
        String[] direction = {"EWL", "N", "S", "EWR"};

        // Initate the first output
        System.out.println("0 L EWL R");
        System.out.println("0 L N R");
        System.out.println("0 L S R");
        System.out.println("0 L EWR R");

        Controller ctrl = new Controller();

        // Threading the light class
        Light lit = new Light(initial, ctrl);
        Thread td = new Thread(lit);
        td.start();

        ExecutorService es = Executors.newCachedThreadPool();

        for (int i = 0; i < direction.length; i++) {
            nameFile = "input " + direction[i] + ".txt";
            es.execute(new Input(nameFile, direction[i], ctrl));
            es.execute(new Sensor(initial, nameFile, ctrl)); // Passing time, filename, controller instances
        }

        es.execute(new TrainSensor(initial, "TA-TD.txt", ctrl));

        es.shutdown();

        while (!es.isTerminated());
        while (td.isAlive());
        System.out.println("Program finish");
    }
}
