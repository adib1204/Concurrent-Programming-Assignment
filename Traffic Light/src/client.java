public class client {

	public static void main(String[] args){
		Thread tls = new TLSController();
		Thread ew = new EWSensor(); 
		
		System.out.println("Running TLS program....\n");
		
		tls.start();
		ew.start(); 	
	}

}
