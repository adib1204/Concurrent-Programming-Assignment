package traffic.signal.controller;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class TestThread {
    public static void main(String[] args) {
        long t = System.currentTimeMillis();
        ExecutorService es = Executors.newCachedThreadPool();
        try {
            Random rand = new Random();
            int sl = rand.nextInt(5000)+5000;
            String input;
            BufferedReader rd = new BufferedReader(new FileReader("input.txt"));
            while ((input = rd.readLine()) != null) {
                Thread.sleep(sl);
                es.execute(new Sensor(t,input));
            }
        } catch (IOException | InterruptedException e) {
        }
        
        es.shutdown();
        while(!es.isTerminated());
        System.out.println("Finish");
        
    }
    
}
