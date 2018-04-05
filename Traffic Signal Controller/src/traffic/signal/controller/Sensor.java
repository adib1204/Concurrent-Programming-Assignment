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
    public Sensor(long timer, String input) {
        this.timer = timer;
        this.input = input;
    }
    public boolean isRunning(){
        return running;
    }
    public String getDirection(){
        return input;
    }
    
    synchronized public void run() {   
       System.out.println((System.currentTimeMillis() - timer) + " S " + input);
    }

}
