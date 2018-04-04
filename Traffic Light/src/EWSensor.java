import java.util.Random;

public class EWSensor extends Thread{
	private Random ran = new Random();//Random instance to get random time
	public static boolean occupied; 
	public static int carTime;
	public static int seconds; 
	
	public EWSensor(){
		occupied = false; 
	}
	public synchronized void run(){
		try {
			while(true){//Loop forever
				while(occupied == false){//This while loop executes only when there is no car(s) in the East-West direction
					carTime = ran.nextInt(60000); //Get a random time from 0 - 60 seconds for when a car arrives
					//Thread.sleep(carTime);
					this.wait(carTime);//Wait for that random time
					seconds = (carTime/1000);//Convert milliseconds to seconds
					System.out.format("\nA car has been detected in %d seconds.\n", seconds);
					if(seconds < 12){
						System.out.println((12 - seconds) + " seconds until light changes"); 
					}
					occupied = true;  
				}
			notifyAll();//Notify all waiting threads
			}
	
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Thread.currentThread().interrupt();
		} 
	}
	
	
}