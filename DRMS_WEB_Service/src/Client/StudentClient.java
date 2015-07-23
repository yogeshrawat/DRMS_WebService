package Client;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import Utility.ValidateInput;
import Interface.LibraryInterface;

public class StudentClient extends Client{

	static LibraryInterface ConcordiaServer;
	static LibraryInterface OttawaServer;
	static LibraryInterface WaterlooServer;
	static final String Concordia ="Concordia", Ottawa="Ottawa", Waterloo="Waterloo";
	static final  String portConcordia = "50001",portOttawa = "50002",portWaterloo = "50003";
	protected static String instituteName;

	public LibraryInterface ServerValidation(String portNumber,String strInstituteName) throws MalformedURLException
	{
		URL url = new URL("http://localhost:" +portNumber+"/"+strInstituteName+"/ws?wsdl");
		QName qname = new QName("http://server/","LibraryServerService");
		Service service = Service.create(url, qname);
		LibraryInterface store = service.getPort(LibraryInterface.class);
		return store;
	/*	Boolean valid = false;
		LibraryInterface server = null;
		
		while(!valid)
		{
			try{
				instituteName = strInstituteName;
				server = LocateServer(instituteName);
				if(server != null) {
					valid=true;
				}
				else {
					System.out.println("Invalid Institute Name");
					//keyboard.nextLine();
				}
			}
			catch(Exception e)
			{
				System.out.println("Invalid Institute Name");
				valid=false;
				//keyboard.nextLine();
			}
		}
		keyboard.nextLine();
		return server;
*/	}

	//Get Server Connection
	public static LibraryInterface LocateServer(String instituteName) {
		if(instituteName.equals(Concordia)) {
			return ConcordiaServer;
		}
		else if(instituteName.equals(Ottawa)) {
			return OttawaServer;
		}
		else if(instituteName.equals(Waterloo)) {
			return WaterlooServer;
		}
		return null;
	}

	//Menu to display actions that are need to perform by student
	public static void showMenu()
	{
		System.out.println("DRMS Student Client System \n");
		System.out.println("Please select an option");
		System.out.println("1. Create An Account.");
		System.out.println("2. Reserve a Book");
		System.out.println("3. Exit");
	}

	public static void main(String[] args)
	{
		try{
			//System.setProperty("java.security.policy","file:./security.policy");
			//initialize the connections to registry
			//objClient.InitializeServer();
			StudentClient objClient = new StudentClient();
			LibraryInterface objServer = null;
			Scanner keyboard = new Scanner(System.in);
			Integer userInput = 0;
			String portNumber = null;
			String userName = null, password = null, institution = null;
			boolean success = false;

			while(true)
			{
				showMenu();

				userInput = Integer.parseInt(objClient.InputStringValidation(keyboard));
				switch(userInput)
				{
				case 1: 
					System.out.println("First Name: ");
					String firstName = objClient.InputStringValidation(keyboard);
					System.out.println("Last Name: ");
					String lastName = objClient.InputStringValidation(keyboard);
					System.out.println("Email: ");
					String emailAddress = objClient.InputStringValidation(keyboard);

					System.out.println("Phone No: ");
					ValidateInput v = new ValidateInput();	
					String phoneNumber = v.validatePhNo(keyboard.nextLine().toString());
					System.out.println("User Name: ");
					userName = v.validateUserName(keyboard.nextLine().toString());
					System.out.println("Password: ");
					password = v.validate(keyboard.nextLine().toString());
					institution= getEducationalInstituteFromUser();
					portNumber = getPortNumber(institution);
					objServer = objClient.ServerValidation(portNumber,institution);
					success = objServer.createAccount(firstName, lastName, emailAddress, phoneNumber, userName, password, institution);
					if(success){
						System.out.println("Success");
						File fi = new File(".\\logs\\students\\"+userName+".txt");
						FileWriter fw=new FileWriter(fi);
						objClient.setLogger(userName,".\\logs\\students\\"+userName+".txt");
						objClient.logger.info("Account created successfully for user "+userName);
						fw.close();
					}
					else{
						File fi = new File(".\\logs\\students\\"+userName+".txt");
						FileWriter fw=new FileWriter(fi);
						objClient.setLogger(userName, ".\\logs\\students\\"+userName+".txt");
						objClient.logger.info("Account already exist with username as : "+userName);
						fw.close();
					}					
					break;
				case 2: 
					boolean flag = true;
					String bookName = "";
					String authorName = "";
					ValidateInput v2 = new ValidateInput();	
					while(flag)
					{
						System.out.println("User Name: ");
						userName = v2.validateUserName(keyboard.nextLine().toString());
						System.out.println("Password: ");
						password = v2.validate(keyboard.nextLine().toString());
						System.out.println("Book Name: ");
						bookName = objClient.InputStringValidation(keyboard);
						System.out.println("Author: ");
						authorName = objClient.InputStringValidation(keyboard);
						institution= getEducationalInstituteFromUser();
						portNumber = getPortNumber(institution);
						objServer = objClient.ServerValidation(portNumber,institution);
						int loginResult = objServer.checkUser(userName, password, institution);
						switch(loginResult)
						{
						case 1: System.out.println("Redirecting to "+institution+" server");flag = false; break;

						case 0: System.out.println("You are not registered with "+institution);
						System.out.println("Press 1 to continue 0 to Exit.");
						int in1 = objClient.InputIntValidation(keyboard);
						if(in1==1)
						{							
							break;
						}
						else
							System.exit(0);

						case 2: System.out.println("Invalid Password. Please try again");
						System.out.println("Press 1 to continue 0 to Exit.");
						int in2 = objClient.InputIntValidation(keyboard);
						if(in2 == 1)
						{
							keyboard=null;
							break;
						}

						else
							System.exit(0);
						}
					}
					success = objServer.reserveBook(userName, password, bookName, authorName);
					if(success){
						System.out.println("Success");
						objClient.setLogger(userName, ".\\logs\\students\\"+userName+".txt");
						objClient.logger.info("Book reserved successfully for user "+userName);
					}
					else{
						objClient.setLogger(userName, ".\\logs\\students\\"+userName+".txt");
						objClient.logger.info("Book could not be reserved for : "+userName);

					}					
					break;
				case 3: 
					System.out.println("Have a nice day!");
					keyboard.close();
					System.exit(0);
				default:
					System.out.println("Invalid input");
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
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
}
