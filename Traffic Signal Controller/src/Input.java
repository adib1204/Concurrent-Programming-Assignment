
import java.io.*;
import java.util.*;

/**
 * Write 5-10 input into the text file
 */
public class Input implements Runnable {

    private final String fileName;
    private final String direction;
    private final int num;
    Controller ctrl;

    /**
     * Create an instance of class Input.
     * @param fileName - Name of the file to write into
     * @param direction - The string to be written into the text file
     * @param ctrl - An object instantiated from class Controller
     * @param num - number of input need to be writtenn
     */
    public Input(String fileName, String direction, Controller ctrl, int num) {
        this.fileName = fileName;
        this.direction = direction;
        this.ctrl = ctrl;
        this.num=num;
    }

    @Override
    public void run() {
        //Random rand = new Random();
        //int num = rand.nextInt(5) + 5;
        //Controller.incrementCounter(num);
        
        try {
            // Creating new file base on the input and Counter
            PrintWriter wr = new PrintWriter(new FileOutputStream(fileName)); 
            for (int i = 0; i < num; i++) {
                wr.println(direction);
            }
            wr.close();
        } catch (IOException e) {
        }
    }

}
