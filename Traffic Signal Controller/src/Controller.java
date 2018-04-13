import java.util.concurrent.locks.*;
import java.util.*;

/**
 * Class ni akan manage traffic signal, update next signal
 * Sensor.java akan access class ni untuk bagitau sensor
 * Pastu class ni bagitau kt Light.java suruh tukar lampu
 */
public class Controller {

    private static Queue vehicle = new LinkedList();   
    private static char currentDirection = 'E';
    private static int counter=0; //Pastikan program betul2 habis
    private static volatile boolean interrupt = false;
    private static Lock lock = new ReentrantLock();

    public void addVehicle(char direction) {
        vehicle.offer(direction);
    }

    public char getCurrent() {
        return currentDirection;
    }

    public int getCounter() {
        return counter;
    }

    public boolean isInterrupt() {
        return interrupt;
    }
    
    public boolean isEmpty(){
        return vehicle.isEmpty();
    }
    
    public void setNoInterrupt() {
        interrupt = false;
    }

    public void manageLight(long stamp) {
         
        lock.lock();
        try {
            char nextVehicle = (char) vehicle.peek();
            Light light = new Light();
            if (nextVehicle == currentDirection) {
                vehicle.poll();
                if (!light.isBlocked()) {
                    System.out.println("Passed since light is green");
                } else {
                    System.out.println("Has to wait for next green light");
                    vehicle.offer(nextVehicle); //Masukkan balik dalam queue since x sempat
                }
                
            } else {
                interrupt = true;
            }
            
        } catch (Exception e) {
        } finally {lock.unlock();}      
    }

    public void changeDirection() {
        if(!vehicle.isEmpty()){
            currentDirection=(char)vehicle.poll();
            while(vehicle.contains(currentDirection)) vehicle.remove(currentDirection);
            System.out.println("Next direction: "+currentDirection);
            System.out.println("Current queue: "+vehicle.toString());
            if(vehicle.isEmpty()) counter++;
        }
        else currentDirection='E';
    }
}
