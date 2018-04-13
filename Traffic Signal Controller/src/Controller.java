import java.util.concurrent.locks.*;
import java.util.*;

/**
 * Class ni akan manage traffic signal, update next signal
 * Sensor.java akan access class ni untuk bagitau sensor
 * Pastu class ni bagitau kt Light.java suruh tukar lampu
 */
public class Controller {

    private static Queue vehicle = new LinkedList(); 
    private char[] direction = {'N', 'W', 'S', 'E'};    
    private static char currentDirection = 'E';
    private static volatile boolean interrupt = false;
    private static Lock lock = new ReentrantLock();

    public void addVehicle(char direction) {
        vehicle.offer(direction);
    }

    public char getCurrent() {
        return currentDirection;
    }

    public boolean isInterrupt() {
        return interrupt;
    }

    public void setNoInterrupt() {
        interrupt = false;
    }

    public void manageLight(long stamp) {
        Light light = new Light(); 
        lock.lock();
        try {
            char nextVehicle = (char) vehicle.peek();
            
            if (nextVehicle == currentDirection) {
                vehicle.poll();
                if (!light.isBlocked()) {
                    System.out.println("Passed since light is green");
                } else {
                    System.out.println("Has to wait for next green light");
                }
                
            } else {
                interrupt = true;
            }
            
        } catch (Exception e) {
        } finally {lock.unlock();}      
    }

    public void changeDirection() {
        if(!vehicle.isEmpty()) currentDirection=(char)vehicle.poll();
        else currentDirection='E';
    }
}
