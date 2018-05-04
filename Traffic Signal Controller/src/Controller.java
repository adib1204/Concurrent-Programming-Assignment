
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;

/**
 * Class ni akan manage traffic signal, update next signal Sensor.java akan
 * access class ni untuk bagitau sensor Pastu class ni bagitau kt Light.java
 * suruh tukar lampu
 */
public class Controller {

    private static Queue<String> vehicle = new LinkedList<String>();
    private static String currentDirection = "EWL";
    private static volatile int counter = 0; //Pastikan program betul2 habis
    private static volatile boolean interrupt = false;
    private static volatile boolean trainArriving = false;
    private final Semaphore available = new Semaphore(1, true);
    private static Lock lock = new ReentrantLock();
    private static Condition isRunning = lock.newCondition();

    public void addVehicle(String direction) {
        vehicle.offer(direction);
    }

    synchronized public static void incrementCounter(int num) {
        counter += num;
    }

    public void decrementCounter() {
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

    public boolean isNoVehicle() {
        return vehicle.isEmpty();
    }

    public boolean trainIncoming() {
        return trainArriving;
    }

    public void setNoInterrupt() {
        interrupt = false;
    }

    public void notifyTrain() {
        lock.lock();
        try {
            isRunning.signalAll();
        } finally {
            lock.unlock();
        }

    }

    public void manageVehicle() {
        try {
            available.acquire();
            String nextVehicle = vehicle.peek();
            if (!nextVehicle.equals(currentDirection)) {
                interrupt = true;
            }
        } catch (InterruptedException e) {
        } finally {
            available.release();
        }
    }

    public void manageTrain(String input) {
        lock.lock();
        try {
            if (input.equals("TA")) {
                trainArriving = true;
                System.out.println("Train incoming");
                isRunning.await();
            } else {
                trainArriving = false;
                System.out.println("Train has departed");
            }
        } catch (InterruptedException e) {
        } finally {
            lock.unlock();
        }

    }

    public boolean removeVehicle(String direction) {
        if (vehicle.contains(direction)) {
            vehicle.remove(direction);
            decrementCounter();
            return true;
        }
        return false;
    }

    public void changeDirection() {
        if (!vehicle.isEmpty()) {
            currentDirection = vehicle.peek();
            System.out.println("Next direction: " + currentDirection);
            System.out.println("Current queue: " + vehicle.toString());
        } else {
            currentDirection = "EWL";
            System.out.println("Current queue: " + vehicle.toString());
        }
    }

    public void changeDirectionForTrain(String nextDirection) {
        currentDirection = nextDirection;
        System.out.println("Next direction: " + nextDirection);
        System.out.println("Current queue: " + vehicle.toString());
    }
}
