
import java.util.concurrent.locks.*;
import java.util.*;

/**
 * Class ni akan manage traffic signal, update next signal Sensor.java akan
 * access class ni untuk bagitau sensor Pastu class ni bagitau kt Light.java
 * suruh tukar lampu
 */
public class Controller {

    private static Queue vehicle = new LinkedList();
    private static String currentDirection = "EWL";
    private static volatile int counter = 0; //Pastikan program betul2 habis
    private static volatile boolean interrupt = false;
    private static Lock lock = new ReentrantLock();

    public void addVehicle(String direction) {
        vehicle.offer(direction);
    }

    synchronized public static void incrementCounter(int num) {
        counter += num;
    }

    synchronized public static void decrementCounter() {
        counter--;
    }

    public String getCurrentDirection() {
        return currentDirection;
    }

    public int getCounter() {
        return counter;
    }

    public boolean isInterrupt() {
        return interrupt;
    }

    public boolean isEmpty() {
        return vehicle.isEmpty();
    }

    public void setNoInterrupt() {
        interrupt = false;
    }

    public void manageVehicle(long stamp) {
        lock.lock();
        try {
            String nextVehicle = (String) vehicle.peek();
            Light light = new Light();
            if (nextVehicle.equals(currentDirection) && light.isBlocked()) {
                System.out.println("Has to wait for next green light");
            } else {
                interrupt = true;
            }

        } catch (Exception e) {
        } finally {
            lock.unlock();
        }
    }

    public void removeVehicle(String direction) {
        if (vehicle.contains(direction)) {
            vehicle.remove(direction);
            decrementCounter();
        }
    }

    public void changeDirection() {
        if (!vehicle.isEmpty()) {
            currentDirection = (String) vehicle.peek();
            System.out.println("Next direction: " + currentDirection);
            System.out.println("Current queue: " + vehicle.toString());
        } else {
            currentDirection = "EWL";
        }
    }

    public void changeDirectionforTrain(String nextDir) {
    	currentDirection = nextDir;
    	System.out.println("Next direction: "+nextDir);
    }


}
