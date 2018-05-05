
import java.util.concurrent.*;

/**
 * Class to test the thread
 * Start the traffic light program
 */
public class TestThread {

    public static void main(String[] args) {

        long initial = System.currentTimeMillis();
        String nameFile;
        String[] direction = {"EWL", "N", "S", "EWR"};

        //To ensure data integrity, Other classes will use the same object instantiated from class Controller
        Controller ctrl = new Controller();//

        ExecutorService es = Executors.newCachedThreadPool();

        for (int i = 0; i < direction.length; i++) {
            nameFile = "input " + direction[i] + ".txt";
            es.execute(new Input(nameFile, direction[i], ctrl));
            es.execute(new Sensor(initial, nameFile, ctrl)); // Passing time, filename, controller instances
        }

        es.execute(new TrainSensor(initial, "TA-TD.txt", ctrl));
        es.execute(new Light(initial, ctrl));

        // Initate the first output
        System.out.println("Program started");
        System.out.println("Total vehicle: " + ctrl.getCounter());
        System.out.println("Note: Every 1 second during green light 1 vehicle passed");
        System.out.println("0 L N R");
        System.out.println("0 L S R");
        System.out.println("0 L EWR R");

        es.shutdown();
        
        //Ensure all threads are terminated before ending the program
        while(!es.isTerminated())
        System.out.println("Program finish");
    }
}
