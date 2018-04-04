
public class TLSController extends Thread{
	
	private final long EWgreenLight = 12000;//The green light of the East-West direction lasts for 20 seconds
	private final long amberLight = 6000;//The amber light of the between switches from green to red/red to green which lasts for 3 seconds

	//No-arg constructor
	public TLSController(){
		
	}
	//Function to print out North-South direction with green light and East-West direction with red light
	public void NSGreenLight(){
		System.out.println("\nNorth-South Lights: GREEN");
		System.out.println("East-West Lights: RED");
	}
	//Function to print out North-South direction with green light and East-West direction with red light
	public void EWGreenLight(){ 
		System.out.println("\nNorth-South Lights: RED");
		System.out.println("East-West Lights: GREEN");
		System.out.println("20 seconds until light changes");
	}
	//Function to print out the change of green light to amber light 
	public void amberLight(){	
		System.out.println("\n...GREEN LIGHT CHANGING TO AMBER LIGHT...\n3 seconds until light changes"); 
	}
	
	public synchronized void run(){
		try {
			NSGreenLight();
			while(true){//Loops forever
				while(EWSensor.occupied == true){//This while loop executes only when there is a car in the East-West direction
					if(EWSensor.seconds < 30){//If a car arrives in less than 30 seconds
						this.wait((30 - EWSensor.seconds) * 1000);//Wait for the remaining seconds
					}
					amberLight();//Amber light
					this.wait(amberLight);//Wait 3 seconds
					EWGreenLight();//East-West direction has green light
					this.wait(EWgreenLight);//Wait 20 seconds
					amberLight(); //Amber light
					this.wait(amberLight);//Wait 3 seconds
					NSGreenLight(); //North-South direction has green light
					EWSensor.occupied = false;//East-West direction has no more car(s) 	
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