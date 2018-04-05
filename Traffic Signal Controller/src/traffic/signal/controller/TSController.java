package traffic.signal.controller;

import java.util.*;
import java.util.concurrent.*;

public class TSController implements Runnable {

    private static String current = "EWL";
    private static String next = "EWL";
    private static long GL = 12000;
    private static long YL = 3000;
    private static Sensor sense = new Sensor();
    private static boolean interrupt = false;
    private static long initial = System.currentTimeMillis();

    public TSController() {
    }

    synchronized public static void manage(long stamp) {
        next = (String) sense.getDirection();
        if (next.equals(current)) {
            System.out.println("Vehicles from " + stamp + " " + next + " Has passed before light turn red");
        } else {
            interrupt = true;
        }
    }

    synchronized public void run() {
        try {
            while (true) {
                long t = System.currentTimeMillis() - initial;
                System.out.println(t + " L " + current + " G");
                while ((System.currentTimeMillis() - t) <= 12000) {
                    Thread.sleep(100);
                    if ((System.currentTimeMillis() - t) >= 6000 && interrupt) {
                        break;
                    }
                }
                t = System.currentTimeMillis() - initial;
                System.out.println(t + " L " + current + " Y");
                Thread.sleep(6000);
                t = System.currentTimeMillis() - initial;
                System.out.println(t + " L " + current + " R");
                current = next;
            }
        } catch (InterruptedException e) {
        }

    }

}
