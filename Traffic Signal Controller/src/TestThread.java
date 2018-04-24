import java.io.*;
import java.util.*;
import java.util.concurrent.*;
/**
 * Class ni untuk start thread sahaja
 * Jangan tambah apa2 variable/method yang boleh affect operation program ni
 */
public class TestThread {
    public static void main(String[] args) {

        long initial = System.currentTimeMillis();
        String[] direction = {"N","W","S","E"};
        String nameFile;
        Controller ct = new Controller();

        System.out.println("0 L N R");
        System.out.println("0 L W R");
        System.out.println("0 L S R");
        System.out.println("0 L E R");

        Light lit = new Light(initial);
        Thread td = new Thread(lit);
        td.start();

        ExecutorService es = Executors.newCachedThreadPool();

        for (int i = 0; i < direction.length; i++) {
            nameFile = "input "+direction[i]+".txt";
            es.execute(new Input(nameFile,direction[i]));
            es.execute(new Sensor(initial,(nameFile)));
        }

        es.shutdown();

        while(!es.isTerminated());
        lit.isEndTrue();
        while(td.isAlive());
        System.out.println("Program finish");

    }
}
