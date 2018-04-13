
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

/**
 * Class ni untuk jalankan tukar lampu je Lepas habis dia akan tukar ke next
 * direction melalui Controller class
 */
public class Light implements Runnable {

    Queue roundRobin = new LinkedList();
    private boolean isEnd = false;
    private boolean block;
    private long initial;
    Controller control = new Controller();

    public Light() {
    }
    public Light(long initial) {
        this.initial = initial;
    }

    public void isEndTrue() {
        isEnd = true;
    }

    public boolean isBlocked() {
        return block;
    }

    public void greenOps() {
        long t = (System.currentTimeMillis() - initial) / 100 * 100;
        long tf = t + 12000;
        System.out.println(t + " L " + control.getCurrent() + " G");
        while ((tf - t) >= 0) {
            t = System.currentTimeMillis() - initial;
            if ((tf - t) <= 6000 && control.isInterrupt()) {
                break;
            }
        }
    }

    public void yellowOps() {
        long t = (System.currentTimeMillis() - initial) / 100 * 100;
        long tf = t + 6000;
        System.out.println(t + " L " + control.getCurrent() + " Y");
        block = true;
        while ((tf - t) >= 0) {
            t = System.currentTimeMillis() - initial;
        }
    }

    public void redOps() {
        long t = (System.currentTimeMillis() - initial) / 100 * 100;
        System.out.println(t + " L " + control.getCurrent() + " R");
        control.setNoInterrupt();
        block = false;
    }

    public void run() {
        try {
            while (true && isEnd == false) {
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
