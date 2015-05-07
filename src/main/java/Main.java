import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by marcello on 06/05/15.
 */
public class Main {

    protected static final int nScreens = 1;
    protected static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args){
        Screen[] screens = new Screen[nScreens];
        ExecutorService es = Executors.newFixedThreadPool(nScreens);

        for(int i = 0; i < nScreens; ++i){
            screens[i] = new Screen(null, null);
            es.submit(screens[i]);
        }


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        //es.shutdown();

        WebSocketServer s = new WebSocketServer();
        s.runServer();


    }


}