/*
 * 
 */
package Client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import Interface.LibraryInterface;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkerThread.
 */
class WorkerThread implements Runnable {

	/** The i. */
	static int i = 0;

	/** The message. */
	private String message;

	/**
	 * Instantiates a new worker thread.
	 *
	 * @param s
	 *            the s
	 */
	public WorkerThread(String s) {
		this.message = s;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		System.out.println(Thread.currentThread().getName()
				+ " (Start) message = " + message);
		URL url = null;
		try {
			url = new URL("http://localhost:50001/Concordia" + "/ws?wsdl");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QName qname = new QName("http://server/", "LibraryServerService");
		Service service = Service.create(url, qname);
		LibraryInterface store = service.getPort(LibraryInterface.class);
		LibraryInterface objServer = store;
		objServer.createAccount("morgan" + message, "schneiderlin" + message,
				"morgan", "5145571540", "morgan" + message, "morgan" + message,
				"Concordia");

		objServer
				.createAccount("vijay" + message, "patil" + message, "vj15",
						"5145571540", "vijay" + message, "vijay" + message,
						"Concordia");
		objServer.reserveBook("vijay" + message, "vijay" + message, "Book1",
				"Author1");
		i++;
		processmessage();
		System.out.println(Thread.currentThread().getName() + " (End)");
	}

	/**
	 * Processmessage.
	 */
	private void processmessage() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
