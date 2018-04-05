package traffic.signal.controller;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class TestThread {
    public static void main(String[] args) {
        long t = System.currentTimeMillis();
        System.out.println(0+" L S R");

        ExecutorService es = Executors.newCachedThreadPool();

        TSController tsc = new TSController(t);
        Thread td = new Thread(tsc);
        td.start();

        try {
        	String input;
            Random rand = new Random();
            int sl = rand.nextInt(5000)+5000;
            BufferedReader rd = new BufferedReader(new FileReader("input.txt"));

            while ((input = rd.readLine()) != null) {
                Thread.sleep(sl);
                es.execute(new Sensor(t,input));
            }
        } catch (IOException | InterruptedException e) {
        }

        es.shutdown();

        // Waiting for thread pool to be ended
        while(!es.isTerminated());
        tsc.isEndTrue();
        System.out.println("Finish");

    }
}
