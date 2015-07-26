package Client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import Interface.LibraryInterface;

public class AdminClient extends Client {

	static final String Concordia = "Concordia", Ottawa = "Ottawa",Waterloo = "Waterloo";
	static final  String portConcordia = "50001",portOttawa = "50002",portWaterloo = "50003";
	protected static String instituteName;

	public LibraryInterface getService(String portNumber,String strInstituteName) throws MalformedURLException
	{
		
		URL url = new URL("http://localhost:" +portNumber+"/"+strInstituteName+"/ws?wsdl");
		QName qname = new QName("http://server/","LibraryServerService");
		Service service = Service.create(url, qname);
		LibraryInterface store = service.getPort(LibraryInterface.class);
		return store;
	}

	// Get Server Connection
	public static void showMenu() {
		System.out.println("DRMS Admin Client System \n");
		System.out.println("Please select an option");
		System.out.println("1. Get Non-Returner.");
		System.out.println("2. Exit");
	}

	public static void main(String[] args)
	{
		Scanner keyboard = new Scanner(System.in);
		String institution = null,portNumber = null, userName = null, password = null;
		try{
			AdminClient objClient = new AdminClient();
			LibraryInterface objServer = null;
			
			//to which server you want to connect
			institution= getEducationalInstituteFromUser();
			portNumber = getPortNumber(institution);
			objServer = objClient.getService(portNumber,institution);
			Integer userInput = 0;
			showMenu();
			objClient.setLogger("admin", "logs/admin/admin.txt");
			objClient.logger.info("admin login");

			userInput = Integer.parseInt(objClient.InputStringValidation(keyboard));

			while(true)
			{
				switch(userInput)
				{
				case 1: 
					System.out.println("Admin userName: ");
					userName = objClient.InputStringValidation(keyboard);
					System.out.println("Password: ");
					password = objClient.InputStringValidation(keyboard);
					System.out.println("No Of Days: ");
					int numOfDays = objClient.InputIntValidation(keyboard);
					
					objClient.logger.info("Non Returner retrieved on :"+ System.currentTimeMillis());
					String result = "";
					result = objServer.getNonReturners(userName, password, objServer.toString(), numOfDays);
					File directory=new File("logs\\admin");
					if(!directory.exists()){
						directory.mkdir();
					}
					File nonReturners=new File(".\\logs\\admin\\NonReturnersFile.txt");
					BufferedWriter bw=new BufferedWriter(new FileWriter(nonReturners));
					bw.write(result.trim());
					bw.flush();
					bw.close();
					System.out.println("NonReturners File Written");

					showMenu();
					break;
				case 2:
					System.out.println("Have a nice day!");
					keyboard.close();
					System.exit(0);
				default:
					System.out.println("Invalid Input, please try again.");
				}
			}
		
	}catch(Exception e){
		e.printStackTrace();
	}
}
	private static String getEducationalInstituteFromUser() throws IOException
	{
		Integer ans = 0;
		System.out.println("Institution Name: ");
		while(true)
		{
			System.out.println("Please select a valid option:");
			System.out.println("1 for Concordia");
			System.out.println("2 for Ottawa");
			System.out.println("3 for Waterloo");
			Client objClient = new StudentClient();
			Scanner keyboard = new Scanner(System.in);
			ans = Integer.parseInt(objClient.InputStringValidation(keyboard));
			if(ans == 1||ans==2||ans==3)
			{
				break;
			}
			else
			{
				System.out.println("Invalid input!");
			}
		}
		switch(ans)
		{
		case 1: return Concordia;
		case 2: return Ottawa;
		case 3: return Waterloo;					
		}
		return null;
	}
private static String getPortNumber(String institution) {
		
		switch(institution)
		{
		case Concordia: return portConcordia;
		case Ottawa: return portOttawa;
		case Waterloo: return portWaterloo;
		default : return null;
		}
	}
}
