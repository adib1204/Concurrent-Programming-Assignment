import java.io.*;
import java.util.*;
/**
 * Generate input randomly
 * Up to 6 input per direction
 */
public class Input implements Runnable {
    private String fileName;
    char direction;

    public Input(String fileName, char direction) {
        this.fileName=fileName;
        this.direction=direction;
    }

    public void run() {
        Random rand = new Random();
        int counter = rand.nextInt(5)+1;
        
        try {
            PrintWriter wr = new PrintWriter(new FileOutputStream(fileName));
            
            for (int i = 0; i < counter; i++) {
                wr.println(direction);
            }
            
            wr.close();
        } catch (IOException e) {
        }
                
    }
    
}
