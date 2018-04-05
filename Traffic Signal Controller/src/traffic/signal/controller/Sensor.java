package traffic.signal.controller;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.io.*;
import java.util.*;

public class Sensor implements Runnable {

    private long timer;
    private String input;
    private long stamp;
    private static Lock lock = new ReentrantLock();
    private Condition isbusy = lock.newCondition();
    private static Stack st = new Stack();


    public Sensor() {
    }
    public Sensor(long timer, String input) {
        this.timer = timer;
        this.input = input;
    }

    public Object getDirection(){
        return st.pop();
    }

    public long getStamp() {
        return stamp;
    }

    public void run() {
       stamp = System.currentTimeMillis() - timer;
       System.out.println(stamp + " S " + input);
       TSController tsc = new TSController();
       lock.lock();
       try {
            st.push(input);
            tsc.manage(stamp);
        } catch (Exception e) {
        } finally { lock.unlock(); }
    }

}
