import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by marcello on 06/05/15.
 */
public class Main {

    public static void main(String[] args){
        Screen s = new Screen();
        ExecutorService es = Executors.newFixedThreadPool(1);

        es.submit(s);
        synchronized (s) {
            s.notify();
        }



    }


}