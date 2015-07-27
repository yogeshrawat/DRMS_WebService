/*
 * 
 */
package Client;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


// TODO: Auto-generated Javadoc
/**
 * The Class ThreadPool100x.
 */
public class ThreadPool100x {
 
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws MalformedURLException the malformed url exception
	 */
	public static void main(String[] args) throws MalformedURLException {
    	
        ExecutorService executor = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            Runnable worker = new WorkerThread("" + i);
            executor.execute(worker);
          }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }
 
}