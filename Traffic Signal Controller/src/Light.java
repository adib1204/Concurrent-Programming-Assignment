
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

/**
 * Class ni untuk jalankan tukar lampu je Lepas habis dia akan tukar ke next
 * direction melalui Controller class
 */
public class Light implements Runnable {

    Queue roundRobin = new LinkedList();
    private static boolean block = false;
    private long initial;
    Controller control;

    public Light() {
    }

    public Light(long initial, Controller control) {
        this.initial = initial;
        this.control = control;
    }

    public boolean isBlocked() {
        return block;
    }

    public void greenOps() {
        long startTime = (System.currentTimeMillis() - initial) / 100 * 100;
        System.out.println(startTime + " L " + control.getCurrentDirection() + " G");
        long currentTime = System.currentTimeMillis() - initial;
        while ((currentTime - startTime) <= 12000) {
            currentTime = System.currentTimeMillis() - initial;
            if (((currentTime - startTime) % 1000) == 0) {
                control.removeVehicle(control.getCurrentDirection());
            }

            if ((currentTime - startTime) >= 6000 && control.isInterrupt()) {
                try {
                    Thread.sleep(100); //Bagi ada lag sikit. Baru real.
                } catch (InterruptedException e) {
                }
                break;
            }

        }
    }

    public void yellowOps() {
        block = true;
        long startTime = (System.currentTimeMillis() - initial) / 100 * 100;
        long currentTime = System.currentTimeMillis() - initial;
        System.out.println(startTime + " L " + control.getCurrentDirection() + " Y");
        while ((currentTime - startTime) <= 6000) {
            currentTime = System.currentTimeMillis() - initial;
        }
    }

    public void redOps() {
        long t = (System.currentTimeMillis() - initial) / 100 * 100;
        System.out.println(t + " L " + control.getCurrentDirection() + " R");
        control.setNoInterrupt();
        block = false;
    }

    public void run() {
        try {
            Thread.sleep(100);
            while (control.getCounter() > 0) {
                greenOps();
                yellowOps();
                redOps();
                control.changeDirection();
                Thread.sleep(100);// Give time to get next signal
            }
        } catch (InterruptedException e) {
        }
    }
}
