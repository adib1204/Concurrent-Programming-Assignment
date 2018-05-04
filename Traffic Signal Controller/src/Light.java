
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

/**
 * Class ni untuk jalankan tukar lampu je Lepas habis dia akan tukar ke next
 * direction melalui Controller class
 */
public class Light implements Runnable {

    Queue roundRobin = new LinkedList();
    private static boolean block = false;
    private long initial;
    Controller control ;
    TrainSensor TS = new TrainSensor();


    public Light() {
    }

    public Light(long initial, Controller control) {
        this.initial = initial;
        this.control = control;
    }

    public boolean isBlocked() {
        return block;
    }

    public void greenOps() {
        long startTime = (System.currentTimeMillis() - initial) / 100 * 100;
        System.out.println(startTime + " L " + control.getCurrentDirection() + " G");
        long currentTime = System.currentTimeMillis() - initial;
        while ((currentTime - startTime) <= 12000) {
            currentTime = System.currentTimeMillis() - initial;
            if (((currentTime - startTime) % 1000) == 0) {
                control.removeVehicle(control.getCurrentDirection());
            }

            if (((currentTime - startTime) >= 6000 && control.isInterrupt()) || TS.getCurrentCondition()) {
                try {
                    Thread.sleep(100); //Bagi ada lag sikit. Baru real.
                } catch (InterruptedException e) {
                }
                break;
            }

        }
    }

    public void greenOpsTrain() {
        long startTime = (System.currentTimeMillis() - initial) / 100 * 100;
        System.out.println(startTime + " L " + control.getCurrentDirection() + " G");

        long currentTime = System.currentTimeMillis() - initial;
        while ((currentTime - startTime) <= 10000) {
            currentTime = System.currentTimeMillis() - initial;
            if (((currentTime - startTime) % 1000) == 0) {
                control.removeVehicle(control.getCurrentDirection());
            }

        }

    }

    public void yellowOps() {
        block = true;
        long startTime = (System.currentTimeMillis() - initial) / 100 * 100;
        long currentTime = System.currentTimeMillis() - initial;
        System.out.println(startTime + " L " + control.getCurrentDirection() + " Y");
        while ((currentTime - startTime) <= 6000) {
            currentTime = System.currentTimeMillis() - initial;
        }
    }

    public void redOps() {
        long t = (System.currentTimeMillis() - initial) / 100 * 100;
        System.out.println(t + " L " + control.getCurrentDirection() + " R");
        control.setNoInterrupt();
        block = false;
    }

    public void manageTrain() {
    	int i ;

    	if(control.getCurrentDirection() == "N") {
    		i = 2;
    	}
    	else
    		i = 1;

    	switch(i) {
	    	case 1 : {
	    		yellowOps();
	    		redOps();
	    		control.changeDirectionforTrain("N");
	    	}
	    	case 2 : {
	    		greenOpsTrain();
	    		yellowOps();
	    		redOps();
	    		control.changeDirectionforTrain("EWL");
    	}
    }//end switch
        long startTime = (System.currentTimeMillis() - initial) / 100 * 100;
    	System.out.println(startTime + " L " + control.getCurrentDirection() + " G");
    	notifyAll();
    	while(TS.getCurrentCondition() == true);
    }


    public void run() {
        try {
            Thread.sleep(100);
            while (control.getCounter() > 0) {
                greenOps();
                if(TS.getCurrentCondition()) {
                	manageTrain();
                }
                yellowOps();
                redOps();
                control.changeDirection();
                Thread.sleep(100);// Give time to get next signal
            }
        } catch (InterruptedException e) {
        }
    }
}
