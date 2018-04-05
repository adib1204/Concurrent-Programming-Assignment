package traffic.signal.controller;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class TSController implements Runnable {

    private static String current = "EWL";
    private static String next = "EWL";
    private Lock lock = new ReentrantLock();
    Condition inProcess = lock.newCondition();
    private static Sensor sense = new Sensor();
    private static boolean interrupt = false;
    private static boolean isEnd = false;
    private long initial;

    public TSController() {
    }
    public TSController(long initial){
        this.initial=initial;
    }

    synchronized public void manage(long stamp) {
        next = (String) sense.getDirection();
        if (next.equals(current)) {
            System.out.println("Vehicles from " + stamp + " " + next + " Has passed before light turn red");
        } else {
            interrupt = true;
        }
    }

    public void isEndTrue() {
    	isEnd = true;
    }
    public void run() {
        try {
            lock.lock();
            while (true && isEnd == false) {
                long t = System.currentTimeMillis() - initial;
                long tf = t + 11980;

                System.out.println(t + " L " + current + " G");

                while ((tf - t) >= 0) {
                    Thread.sleep(1000);
                    t = System.currentTimeMillis() - initial;
                    if ((tf - t) <= 6000 && interrupt) {
                        break;
                    }
                }
                t = System.currentTimeMillis() - initial;
                System.out.println(t + " L " + current + " Y");
                Thread.sleep(6000);
                t = System.currentTimeMillis() - initial;
                System.out.println(t + " L " + current + " R");
                //Thread.sleep(10);
//                current = next;
                if(current=="EWL")
                	current="S";
                else
                	current="EWL";
            }
        } catch (InterruptedException e) {
        } finally { lock.unlock(); }

    }

}
