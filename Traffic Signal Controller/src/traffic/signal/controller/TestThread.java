package traffic.signal.controller;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class TestThread {
    public static void main(String[] args) {
        long t = System.currentTimeMillis();
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new TSController());
        es.execute(new Sensor(t));
        es.shutdown();
        while(!es.isTerminated());
        System.out.println("Finish");
        
    }
    
}
