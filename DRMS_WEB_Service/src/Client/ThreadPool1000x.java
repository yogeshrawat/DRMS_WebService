/*
 * 
 */
package Client;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TODO: Auto-generated Javadoc
/**
 * The Class ThreadPool1000x.
 */
public class ThreadPool1000x {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	public static void main(String[] args) throws MalformedURLException {

		ExecutorService executor = Executors.newFixedThreadPool(1000);
		for (int i = 0; i < 1000; i++) {
			Runnable worker = new WorkerThread("" + i);
			executor.execute(worker);
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		System.out.println("Finished all threads");
	}

}