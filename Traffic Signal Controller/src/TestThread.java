
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

        Controller ctrl = new Controller();

        ExecutorService es = Executors.newCachedThreadPool();

        for (int i = 0; i < direction.length; i++) {
            nameFile = "input " + direction[i] + ".txt";
            es.execute(new Input(nameFile, direction[i], ctrl));
            es.execute(new Sensor(initial, nameFile, ctrl)); // Passing time, filename, controller instances
        }

        es.execute(new TrainSensor(initial, "TA-TD.txt", ctrl));

        // Threading the light class
        Light lit = new Light(initial, ctrl);
        Thread td = new Thread(lit);
        td.start();

        // Initate the first output
        System.out.println("Program started");
        System.out.println("Total vehicle: " + ctrl.getCounter());
        System.out.println("Note: Every 1 second during green light 1 vehicle passed");
        System.out.println("0 L N R");
        System.out.println("0 L S R");
        System.out.println("0 L EWR R");

        es.shutdown();
        while (td.isAlive());//Ensure light thread finishes before ending the program
        System.out.println("Program finish");
    }
}
