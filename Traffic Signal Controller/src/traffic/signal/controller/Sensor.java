package traffic.signal.controller;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.io.*;
import java.util.*;

public class Sensor implements Runnable {

    private static String direction;
    private static boolean running;
    private long timer;
    private String input;
    public Sensor() {
    }
    public Sensor(long timer) {
        this.timer = timer;
    }
    public boolean isRunning(){
        return running;
    }
    public String getDirection(){
        return input;
    }
    
    synchronized public void run() {
        running=true;
        Random rand = new Random();
        int sl = rand.nextInt(5000)+5000;
        try {
            BufferedReader rd = new BufferedReader(new FileReader("input.txt"));
            while ((input = rd.readLine()) != null) {
                Thread.sleep(sl);
                System.out.println((System.currentTimeMillis() - timer) + " S " + input);
            }
        } catch (IOException | InterruptedException e) {
        } finally {}
        running=false;
    }

}
