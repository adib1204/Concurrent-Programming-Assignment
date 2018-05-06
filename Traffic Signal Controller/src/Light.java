
/**
 * Class to operate the traffic light
 * Get the direction of the green light from Controller class
 */
public class Light implements Runnable {

    private long initial;
    Controller control;

    /**
     * Create an instance of the light class.
     * @param initial - The time at the start of the program
     * @param control - An object instantiated from class Controller 
     */
    public Light(long initial, Controller control) {
        this.initial = initial;
        this.control = control;
    }

    /**
     * Operate the green light (duration between 6 to 12 seconds).
     * Also print the timestamp at the output.
     * If a vehicle come from another direction, the green light will be stopped before it reach 12 second (minimum: 6 second).
     * Assume one vehicle passed the intersection for every one second the green light is operating.
     */
    public void changeToGreenLight() {
        int num = 0; //Number of car that has passed the intersection during greenLight
        long startTime = (System.currentTimeMillis() - initial) / 100 * 100;
        System.out.println(startTime + " L " + control.getCurrentDirection() + " G");
        long currentTime = System.currentTimeMillis() - initial;
        while ((currentTime - startTime) <= 12000) {
            currentTime = System.currentTimeMillis() - initial;
            if (((currentTime - startTime) % 1000) == 0) {
                boolean removed = control.removeVehicle(control.getCurrentDirection());
                if (removed) {
                    num++;
                }
            }

            if (((currentTime - startTime) >= 6000 && control.isInterrupt()) || control.trainIncoming()) {
                try {
                    //To make traffic light realistic before changing into yellow light (Changing from green light to yellow light takes 100 milliseconds)
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                break;
            }
        }
        System.out.println(num + " vehicle from " + control.getCurrentDirection() + " has passed during green light");
    }

    /**
     * Operate the green light (duration determined by the variable).
     * Also print the timestamp at the output.
     * Assume one vehicle passed the intersection for every one second the green light is operating.
     * If sensor has detected the train has departed (TD), this green light will be stopped and changed to yellow light.
     * @param i - Duration of the green light in milliseconds
     */
    public void changeToGreenLight(int i) {
        int num = 0; //Number of car that has passed the intersection during greenLight
        long startTime = (System.currentTimeMillis() - initial) / 100 * 100;
        System.out.println(startTime + " L " + control.getCurrentDirection() + " G");
        long currentTime = System.currentTimeMillis() - initial;
        while ((currentTime - startTime) <= i) {
            currentTime = System.currentTimeMillis() - initial;
            if (((currentTime - startTime) % 1000) == 0) {
                boolean removed = control.removeVehicle(control.getCurrentDirection());
                if (removed) {
                    num++;
                }
            }
            if (!control.trainIncoming()) {
                break; //Already read TD
            }
        }
        System.out.println(num + " vehicle from " + control.getCurrentDirection() + " has passed during green light");
    }

    /**
     * Operate the yellow light (duration is 6 seconds).
     * Also print the timestamp at the output.
     */
    public void changeToYellowLight() {
        long startTime = (System.currentTimeMillis() - initial) / 100 * 100;
        long currentTime = System.currentTimeMillis() - initial;
        System.out.println(startTime + " L " + control.getCurrentDirection() + " Y");
        while ((currentTime - startTime) <= 6000) {
            currentTime = System.currentTimeMillis() - initial;
        }
    }

    /**
     * Operate the red light.
     * Also print the timestamp at the output.
     */
    public void changeToRedLight() {
        long t = (System.currentTimeMillis() - initial) / 100 * 100;
        System.out.println(t + " L " + control.getCurrentDirection() + " R");
        control.setNoInterrupt();
        System.out.println("Vehicle left: " + control.getCounter());
    }

    /**
     * Manage the operation of the traffic light while train is arriving.
     * If sensor detect TA and current direction is not N, it will be changed to N first.
     * Then green light is operated at direction N for 10 seconds before changed to EWL.
     * After the train has departed (TD), it will be changed back to normal traffic light operation.
     * @param extra - Determine whether TA is read during green/yellow/red light. Is false if read during green light.
     */
    public void manageTrainOperation(boolean extra) {
        int i;
        if (control.getCurrentDirection().equals("N")) {
            i = 2;
        } else {
            i = 1;
        }
        switch (i) {
            case 1: {
                changeToYellowLight();
                changeToRedLight();
            }
            case 2: {
                control.changeDirectionForTrain("N");
                System.out.println("Northbound traffic given 10 second to clear traffic");
                changeToGreenLight(10000);
                changeToYellowLight();
                changeToRedLight();
                control.changeDirectionForTrain("EWL");
            }
        }//end switch
        control.notifyTrain();
        changeToGreenLight(Integer.MAX_VALUE);

        //If TA is read during yellow/red light, It will run the method below first
        if (extra) {  
            changeToYellowLight();
            changeToRedLight();
        }
    }

    @Override
    public void run() {
        try {
            //Run until all cars has passed the intersection
            while (control.getCounter() > 0) {
                do {
                    //If there is no vehicle EWL stays green
                    changeToGreenLight();
                } while (control.isNoVehicle() && control.getCurrentDirection().equals("EWL"));
                if (control.trainIncoming()) {
                    manageTrainOperation(false);
                }
                changeToYellowLight();
                changeToRedLight();
                if (control.trainIncoming()) {
                    manageTrainOperation(true);
                }

                control.changeDirection();
                // Give time to get next direction
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
        }
    }
}
