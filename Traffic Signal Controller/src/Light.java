
/**
 * Class ni untuk jalankan tukar lampu je Lepas habis dia akan tukar ke next
 * direction melalui Controller class
 */
public class Light implements Runnable {

    private long initial;
    Controller control;

    public Light(long initial, Controller control) {
        this.initial = initial;
        this.control = control;
    }

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
                    Thread.sleep(100); //Bagi ada lag sikit. Baru real.
                } catch (InterruptedException e) {
                }
                break;
            }
        }
        System.out.println(num + " vehicle from " + control.getCurrentDirection() + " has passed during green light");
    }

    //Duration of green light can be determined by user
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

    public void changeToYellowLight() {
        long startTime = (System.currentTimeMillis() - initial) / 100 * 100;
        long currentTime = System.currentTimeMillis() - initial;
        System.out.println(startTime + " L " + control.getCurrentDirection() + " Y");
        while ((currentTime - startTime) <= 6000) {
            currentTime = System.currentTimeMillis() - initial;
        }
    }

    public void changeToRedLight() {
        long t = (System.currentTimeMillis() - initial) / 100 * 100;
        System.out.println(t + " L " + control.getCurrentDirection() + " R");
        control.setNoInterrupt();
        System.out.println("Vehicle left: " + control.getCounter());
    }

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
        changeToGreenLight(Integer.MAX_VALUE);//Selagi x read TD EWL hijau

        if (extra) {  //Kalau read TA masa yellow/red light, dia akan run bawah ni dulu
            changeToYellowLight();
            changeToRedLight();
        }
    }

    @Override
    public void run() {
        try {
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
                Thread.sleep(100);// Give time to get next signal
            }
        } catch (InterruptedException e) {
        }
    }
}
