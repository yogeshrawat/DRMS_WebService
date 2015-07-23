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
		LibraryServer serverConcordia=new LibraryServer("Concordia",50001);
        new Thread(serverConcordia).start();
        
        LibraryServer serverOttawa=new LibraryServer("Ottawa",50002);
        new Thread(serverOttawa).start();
        
        LibraryServer serverWaterloo=new LibraryServer("Waterloo",50003);
        new Thread(serverWaterloo).start();
        
        //Add test data
        serverConcordia.addData();
        serverOttawa.addData();
        serverWaterloo.addData();
        
		Endpoint endpoint1=Endpoint.publish("http://localhost:50001/Concordia/ws", serverConcordia);
		Endpoint endpoint2=Endpoint.publish("http://localhost:50002/Ottawa/ws", serverOttawa);
		Endpoint endpoint3=Endpoint.publish("http://localhost:50003/Waterloo/ws", serverWaterloo);
		System.out.println("Concordia : "+ endpoint1.isPublished());
		System.out.println("Ottawa : "+ endpoint2.isPublished());
		System.out.println("Waterloo : "+ endpoint3.isPublished());
	}

}
