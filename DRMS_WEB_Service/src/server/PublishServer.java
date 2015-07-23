package server;

import java.io.IOException;
import javax.xml.ws.Endpoint;

public class PublishServer extends Thread{

	/**
	 * @param args
	 * @throws IOException 
	 */
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//@WebServiceRef(wsdlLocation="http://localhost:8080/cal?wsdl")
		LibraryServer webLib1=new LibraryServer("Concordia",50001);
        new Thread(webLib1).start();
        
        LibraryServer webLib2=new LibraryServer("Ottawa",50002);
        new Thread(webLib2).start();
        
        LibraryServer webLib3=new LibraryServer("Waterloo",50003);
        new Thread(webLib3).start();
        
		Endpoint endpoint1=Endpoint.publish("http://localhost:50001/Concordia/ws", webLib1);
		Endpoint endpoint2=Endpoint.publish("http://localhost:50002/Ottawa/ws", webLib2);
		Endpoint endpoint3=Endpoint.publish("http://localhost:50003/Waterloo/ws", webLib3);
		System.out.println("Concordia : "+ endpoint1.isPublished());
		System.out.println("Ottawa : "+ endpoint2.isPublished());
		System.out.println("Waterloo : "+ endpoint3.isPublished());
	}

}
