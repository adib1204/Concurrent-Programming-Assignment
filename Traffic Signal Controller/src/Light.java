
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
    private static boolean block = false;
    private long initial;
    Controller control = new Controller();
    TrainSensor TS = new TrainSensor();

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
                Random r = new Random();
                try {
                    Thread.sleep(100); //Bagi ada lag sikit. Baru real.
                } catch (InterruptedException e) {
                }
                break;
            }
        }
    }
 
    public void greenOpsTrain() {
        long t = (System.currentTimeMillis() - initial) / 100 * 100;
        long tf = t + 10000;
        System.out.println(t + " L " + control.getCurrent() + " G");
    }

    public void yellowOps() {
        block = true;
        long t = (System.currentTimeMillis() - initial) / 100 * 100;
        long tf = t + 6000;
        System.out.println(t + " L " + control.getCurrent() + " Y");
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
    
    public void manageTrain() {
    	int i ;
    	if(control.getCurrent() == 'N') {
    		i = 3;
    	}
    	else
    		i = 2;
    	
    	switch(i) {
    	case 2 :{
    		yellowOps();
    		redOps();
    		control.changeDirectionforTrain('N');
    	}
    	case 3: {
    		greenOpsTrain();
    		yellowOps();
    		redOps();
    		control.changeDirectionforTrain('E');
    	}
    }//end switch
    	while(TS.getCurrentCondition() == true) {
    		notifyAll();
    	}
    }
    

    public void run() {
        try {
            Thread.sleep(100);
            int i = 0;
            while (control.getCounter() != 1) {
                greenOps();
                yellowOps();
                redOps();
                control.changeDirection();
                Thread.sleep(100);// Give time to get next signal
            }
            //Run one more time to ensure no car
            greenOps();
            yellowOps();
            redOps();
        } catch (InterruptedException e) {
        }
    }
}
