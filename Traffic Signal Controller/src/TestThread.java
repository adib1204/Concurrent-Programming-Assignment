
import java.util.concurrent.*;
import java.util.*;

/**
 * Class to test the thread Start the traffic light program
 */
public class TestThread {

    public static void main(String[] args) {

        String nameFile;
        String[] direction = {"EWL", "N", "S", "EWR"};
        int[] numInput = new int[4];
        long initial;
        Scanner kb = new Scanner(System.in);
         //To ensure data integrity, Other classes will use the same object instantiated from class Controller
        Controller ctrl = new Controller();
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < numInput.length; i++) {
            System.out.println("Please input of number of vehicle you want to generate for direction " + direction[i] + 
                    " (Input must be integer between 1-15):");
            try {
                numInput[i] = kb.nextInt();
                if (numInput[i] < 1 || numInput[i] > 15) {
                    System.out.println("Please input integer between 1-15 only");
                    i--;
                }
                else{
                    Controller.incrementCounter(numInput[i]);
                }
            } catch (InputMismatchException e) {
                System.out.println("Please input integer not float or string");
                kb.next();
                i--;
            }
        }
        
        initial = System.currentTimeMillis();

        for (int i = 0; i < direction.length; i++) {
            nameFile = "input " + direction[i] + ".txt";
            es.execute(new Input(nameFile, direction[i], ctrl,numInput[i]));
            es.execute(new RoadSensor(initial, nameFile, ctrl)); // Passing time, filename, controller instances
        }

        es.execute(new TrainSensor(initial, "TA-TD.txt", ctrl));
        es.execute(new Light(initial, ctrl));

        // Initate the first output
        System.out.println("Program started");
        System.out.println("Total vehicle: "+ctrl.getCounter());
        System.out.println("Note: Every 1 second during green light 1 vehicle passed");
        System.out.println("0 L N R");
        System.out.println("0 L S R");
        System.out.println("0 L EWR R");

        es.shutdown();

        //Ensure all threads are terminated before ending the program
        while (!es.isTerminated());
        System.out.println("Program finish");
    }
}
