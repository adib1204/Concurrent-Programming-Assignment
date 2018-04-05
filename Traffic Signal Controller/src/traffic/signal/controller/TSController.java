package traffic.signal.controller;

import java.util.*;
import java.util.concurrent.*;

public class TSController {
    Sensor ss = new Sensor();
    private char current = 'N';
    private char next = 'N';
    
    
    public void initial(){
        System.out.println("0 L S R");
        System.out.println("0 L N G");
    }
    synchronized public void run() {
        initial();
        try {
            Thread.sleep(10);
            while(ss.isRunning()){
//                switch (ss.getDirection().charAt(next)) {
//                    case :
//                        
//                        break;
//                    default:
//                        throw new AssertionError();
//                }
System.out.print("");
            }
        } catch (InterruptedException e) {
        }
        
    }
    
    
}
