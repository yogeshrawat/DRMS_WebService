/*
 * 
 */
package Client;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TODO: Auto-generated Javadoc
/**
 * The Class ThreadPool10x.
 */
public class ThreadPool10x {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	public static void main(String[] args) throws MalformedURLException {

		ExecutorService executor = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 5; i++) {
			Runnable worker = new WorkerThread("" + i);
			executor.execute(worker);
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		System.out.println("Finished all threads");
	}

}