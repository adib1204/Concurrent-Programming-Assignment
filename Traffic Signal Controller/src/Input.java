import java.io.*;
import java.util.*;

/**
 * Generate input randomly Up to 6 input per direction
 */
public class Input implements Runnable {
	private String fileName;
	String direction;
	Controller ctrl;

	public Input(String fileName, String direction, Controller ctrl) {
		this.fileName = fileName;
		this.direction = direction;
		this.ctrl = ctrl;
	}

	public void run() {
		Random rand = new Random();
		int counter = rand.nextInt(5) + 5;
		ctrl.incrementCounter(counter);
		try {
			PrintWriter wr = new PrintWriter(new FileOutputStream(fileName)); // Creating new file base on the input and Counter

			for (int i = 0; i < counter; i++) {
				wr.println(direction);
			}

			wr.close();
		} catch (IOException e) {
		}

	}

}
