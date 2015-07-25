package server;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import Interface.LibraryInterface;
import LibraryObjects.*;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

@WebService(endpointInterface="Interface.LibraryInterface")
@SOAPBinding(style=Style.RPC)
public class LibraryServer extends Thread implements LibraryInterface{

	private HashMap<Character, ArrayList<Student>> tableStudents = new HashMap<Character, ArrayList<Student>>();
	//private static ArrayList<List<Student>> listStudents = new ArrayList<List<Student>>(); 
	private HashMap<String, Book> tableBooks   = new HashMap<String, Book>();
	private String instituteName;
	//private int udpPort;
	static final String Concordia ="Concordia", Ottawa="Ottawa", Waterloo="Waterloo";
	static final  String portConcordia = "50001",portOttawa = "50002",portWaterloo = "50003";
	private static String[] ServerNames = new String[] {Concordia,Ottawa,Waterloo};

	private Logger logger;

	public LibraryServer(String instituteName)
	{
		this.instituteName = instituteName;
		this.setLogger(".\\logs\\library\\"+instituteName+".txt");
	}

	public LibraryServer(){

	}
	public  HashMap<String, Book> getBooksTable()
	{
		return tableBooks;
	}
	private void setLogger(String instituteName) {
		try{
			this.logger = Logger.getLogger(this.instituteName);
			FileHandler fileTxt 	 = new FileHandler(instituteName);
			SimpleFormatter formatterTxt = new SimpleFormatter();
			fileTxt.setFormatter(formatterTxt);
			logger.addHandler(fileTxt);
		}
		catch(Exception err) {
			System.out.println("Couldn't Initiate Logger. Please check file permission");
		}
	}



//	public int getUDPPort()
//	{
//		return this.udpPort;
//	}
//	public LibraryServer(String strInstitution, int iPortNum) {
//		// TODO Auto-generated constructor stub
//		instituteName = strInstitution;
//		//udpPort = iPortNum;
//		this.setLogger("logs/library/"+instituteName+".txt");
//	}

	//	public static void main(String[] args)  {
	//
	//		// TODO Auto-generated method stub
	//
	//		LibraryServer Server1 = new LibraryServer("Concordia",50001);
	//		LibraryServer Server2 = new LibraryServer("Ottawa",50002);
	//		LibraryServer Server3 = new LibraryServer("Waterloo",50003);
	//
	//		
	//
	//		Server1.start();
	//		System.out.println("Concordia server up and running!");
	//		Server2.start();
	//		System.out.println("Ottawa server up and running!");
	//		Server3.start();
	//		System.out.println("Waterloo server up and running!");
	//
	//		addData(Server1);
	//		addData(Server2);
	//		addData(Server3);
	//
	//		LibraryServers = new ArrayList<LibraryServer>();
	//		LibraryServers.add(Server1);
	//		LibraryServers.add(Server2);
	//		LibraryServers.add(Server3);
	//
	//
	//	}
	//
	private static int i=1;
	public void addData()
	{	
		if(this.instituteName == "Concordia")
		{
			for(int j=1; j<3; j++) { 

				Book book = new Book("Book"+j, "Author"+j, 10);
				this.tableBooks.put(book.getName(), book);
			}
			Book book = new Book("Book3", "Author3", 10);
			this.tableBooks.put("Book3", book);


		}
		else{
			for(int j=2; j<3; j++) { 
				Book book = new Book("Book"+j, "Author"+j, 0);
				this.tableBooks.put(book.getName(), book);
			}
			Book book = new Book("Book1", "Author1", 10);
			this.tableBooks.put(book.getName(), book);
		}


		ArrayList<Student> s = new ArrayList<Student>();
		for(char i = 'A'; i <= 'Z' ; i++)
		{
			this.tableStudents.putIfAbsent(i, s);
		}

		if(this.instituteName == "Concordia")
		{
			this.createAccount("yogesh", "rawat","yogesh@gmail.com","5145156743","yogesh","yogesh",this.instituteName);
			this.reserveBook("yogesh", "yogesh", "Book1", "Author1");
			ArrayList<Student> list = this.tableStudents.get('y');

			Book Book1 = new Book("Book2","Author2",8);
			for(Student student : list)
			{
				if(student.getUserName().equals("yogesh"))
				{
					student.getReservedBooks().put(Book1, 2);
				}
			}
		}

		if(this.instituteName == "Ottawa"){
			this.createAccount("aron", "engineer","aron@gmail.com","5145156743","aron123","aron123",this.instituteName);
			ArrayList<Student> list = this.tableStudents.get('a');

			Book Book1 = new Book("Book1","Author1",8);
			for(Student student : list)
			{
				if(student.getUserName().equals("aron"))
				{
					student.getReservedBooks().put(Book1, 2);
				}
			}
		}

		if(this.instituteName == "Waterloo"){
			this.createAccount("ashish", "guhe","ashish@gmail.com","5145656743","ashish","ashish",this.instituteName);
			ArrayList<Student> list = this.tableStudents.get('a');

			Book Book1 = new Book("Book1","Author1",8);
			for(Student student : list)
			{
				if(student.getUserName().equals("ashish"))
				{
					student.getReservedBooks().put(Book1, 2);
				}
			}
		}

	}

//	public void run()
//	{
//		DatagramSocket socket = null;
//
//		try
//		{
//			socket = new DatagramSocket(this.udpPort);
//			byte[] msg = new byte[10000];
//			//Logger call
//
//			while(true)
//			{
//				DatagramPacket request = new DatagramPacket(msg, msg.length);
//				socket.receive(request);
//				String data = new String(request.getData());
//				String response = GetNonReturnersByServer(Integer.parseInt(data.trim()));
//				DatagramPacket reply = new DatagramPacket(response.getBytes(),response.length(),request.getAddress(),request.getPort());
//				socket.send(reply);
//			}
//		}
//		catch(Exception ex)
//		{
//			ex.printStackTrace();
//		}
//		finally
//		{
//			socket.close();
//		}
//	}

	@Override
	public boolean createAccount(String strFirstName, String strLastName, String strEmailAddress, String strPhoneNumber, String strUsername,
			String strPassword, String strEducationalInstitution)
	{
		if(isExist(strUsername, strEducationalInstitution )){
			return false;
		}
		else 
		{
			Student objStudent = new Student(strUsername,strPassword,strEducationalInstitution);
			objStudent.setFirstName(strFirstName);
			objStudent.setLastName(strLastName);
			objStudent.setEmailAddress(strEmailAddress);
			objStudent.setPhoneNumber(strPhoneNumber);

			//Add student to HashTable 'tableStudents' with Lock
			if(tableStudents.get(strUsername.charAt(0)) != null){
				synchronized(tableStudents.get(strUsername.charAt(0))) {
					ArrayList<Student> objNewStudent = tableStudents.get(strUsername.charAt(0));
					if(objNewStudent == null) {
						objNewStudent = new ArrayList<Student>();
						tableStudents.put(strUsername.charAt(0), objNewStudent);
					}
					objNewStudent.add(objStudent);

					logger.info("New User added to the library with username as : "+objStudent.getUserName());
				}
			}
			else {
				ArrayList<Student> objNewStudent = tableStudents.get(strUsername.charAt(0));
				if(objNewStudent == null) {
					objNewStudent = new ArrayList<Student>();
					tableStudents.put(strUsername.charAt(0), objNewStudent);
				}
				objNewStudent.add(objStudent);

				logger.info("New User added to the library with username as : "+objStudent.getUserName());

			}


			return true;
		}
	}

	@Override
	public boolean reserveBook(String strUsername, String strPassword, String strBookName, String strAuthor)   
	{
		boolean success =false;
		int iLoginResult = 0;
		Student objStudent = null;
		objStudent = getStudent(strUsername);
		if(objStudent!=null)
		{
			iLoginResult = checkUser(strUsername, strPassword, objStudent.getInst());
			if(iLoginResult==1)
			{
				synchronized(tableBooks)
				{
					Book objBook = tableBooks.get(strBookName);
					if(objBook!= null)
					{
						//reserve the book
						if(objBook.getNumOfCopy()>0)
						{
							objBook.setNumOfCopy(objBook.getNumOfCopy()-1);//Decrement available copies
							(objStudent.getReservedBooks()).put(objBook,14);//Add Book to Student's reserved list for 14 days
							success = true;
							logger.info(strUsername+": Reserved the book "+strBookName+"\n. Remaining copies of"+ strBookName+"is/are"+objBook.getNumOfCopy());
							System.out.println(this.instituteName +" Library : "+strUsername+": Reserved the book "+strBookName+"\n. Remaining copies of "+ strBookName+" is/are "+objBook.getNumOfCopy());

						}
						else
						{
							System.out.println("Required book not available currently");
						}
					}
					else
					{
						System.out.println("Required book not found");	
					}
				}


			}
			else
			{
				if(iLoginResult==2)
					System.out.println("Login is invalid!");
			}
		}
		else
		{
			System.out.println("Student "+strUsername+ " does not exist!");
		}
		return success;
	}

	//Checks availability of requested book
	private boolean isBookAvailable(String strBookName)
	{
		System.out.println("I am "+this.instituteName+". isAvailable.");
		boolean isAvailable=false;
		Book objBook = tableBooks.get(strBookName);
		if(objBook!= null)
		{
			//reserve the book
			if(objBook.getNumOfCopy()>0)
			{
				isAvailable=true;
			}
		}
		return isAvailable;
	}

	//Reserves book from other libraries if Book not available locally
	@Override
	public boolean reserveInterLibrary(String m_username, String m_password,
			String m_bookName, String m_authorName) 
	{
		boolean bookReserved = false;
		if(isBookAvailable(m_bookName))
		{
			bookReserved = reserveBook(m_username, m_password, m_bookName, m_authorName);
		}
		else
		{
			//boolean result = false;
			// TODO Auto-generated method stub
			for(String libraryServer : ServerNames)
			{
				if(this.instituteName!=libraryServer)
				{
					try 
					{
						String portNumber = getPortNumber(libraryServer);
						LibraryInterface remoteServer = getService(portNumber, libraryServer);
						
						if(remoteServer.grantBookInterServer(m_bookName))
						{
							bookReserved =true;
							System.out.println(this.instituteName +" Library : "+m_username+": Reserved the book "+m_bookName+" from " + libraryServer+" server.");
							//Logger.info(m_username+": Reserved the book "+m_bookName+"\n. Remaining copies of"+ m_bookName +"is/are "+objBook.getNumOfCopy());
							//Logger.info(m_username+": Reserved the book "+m_bookName+"from "+libraryServer.instituteName+" server.");
							//System.out.println(this.instituteName +" Library : "+m_username+": Reserved the book "+m_bookName+"\n. Remaining copies of "+ m_bookName+" is/are "+objBook.getNumOfCopy());
							break;
						}
						//						//LibraryServer libraryServer = (LibraryServer) getServerObject(args, LibraryServers[i]);
						//						DatagramSocket socket = null;
						//						try
						//						{
						//							socket = new DatagramSocket();
						//							String strInputParameters="ReserveBook:"+m_bookName;
						//							byte[] msgOut = (strInputParameters).getBytes();
						//							InetAddress host = InetAddress.getByName("localhost");
						//							int ServerPort = libraryServer.getUDPPort();
						//							
						//							DatagramPacket request = new DatagramPacket(msgOut, (strInputParameters).length(),host,ServerPort);
						//							socket.send(request);
						//
						//							byte[] msgIn = new byte[10000];
						//							DatagramPacket reply = new DatagramPacket(msgIn, msgIn.length);
						//							socket.receive(reply);
						//							response=new String(reply.getData());
						//
						//							if(response.equals("true"))//Book granted by other Server
						//							{
						//								bookAvailabe=true;
						//								//Add granted book to Students Reserved book list
						//								Student objStudent = null;
						//								objStudent = getStudent(m_username);
						//								Book objBook = new Book(m_bookName,m_authorName,0);
						//								(objStudent.getReservedBooks()).put(objBook,14);//Add Book to Student's reserved list for 14 days
						//								//success = true;
						//								logger.info(m_username+": Reserved the book "+m_bookName+"\n. Remaining copies of"+ m_bookName+" is/are "+objBook.getNumOfCopy());
						//								System.out.println(this.instituteName +" Library : "+m_username+": Reserved the book "+m_bookName+"\n. Remaining copies of "+ m_bookName+" is/are "+objBook.getNumOfCopy());	
						//								break;
						//							}
						//							

						//						}
						//						catch(Exception ex)
						//						{
						//							ex.printStackTrace();
						//						}
						//						finally
						//						{
						//							socket.close();
						//						}

						//					response += libraryServer.GetNonReturnersByServer(NumDays);
					} 
					catch (Exception e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

		return bookReserved;
	}

	public boolean grantBookInterServer(String strBookName)
	{
		boolean isAvailable=false;
			System.out.println(strBookName);
			//strBookName=strBookName.substring(1);
		System.out.println("I am "+this.instituteName+". Grant book.");
		 HashMap<String, Book> BookTable = this.getBooksTable();
		Book objBook = BookTable.get(strBookName);
		if(objBook!= null)
		{
			//reserve the book
			if(objBook.getNumOfCopy()>0)
			{
				objBook.setNumOfCopy(objBook.getNumOfCopy()-1);//Decrement available copies
				isAvailable = true;
			}
		}
		else
		{
			isAvailable = false;
		}
		return isAvailable;
	}

	public boolean isExist(String strUsername, String strEducationalInstitution) 
	{
		// TODO Auto-generated method stub
		boolean exist = false;
		ArrayList<Student> listStudent = new ArrayList<Student>();
		listStudent = tableStudents.get(strUsername.charAt(0));
		if(listStudent!=null)
		{
			if(listStudent.size()>0)
			{
				for(Student student : listStudent)
				{
					if(student.getUserName().equals(strUsername))
					{
						exist = true;
					}
				}
			}
		}
		return exist;
	}


	@Override
	public int checkUser(String strUsername, String strPassword,String strEducationalInstitution)  
	{
		// TODO Auto-generated method stub
		int login = 0;
		Student objStudent = null;
		ArrayList<Student> listStudent = new ArrayList<Student>();
		listStudent = tableStudents.get(strUsername.charAt(0));
		if(listStudent!=null)
		{
			if(listStudent.size()>0)
			{
				for(Student student : listStudent)
				{
					if(student.getUserName().equals(strUsername))
					{
						if(student.getPassword().equals(strPassword))
						{
							login =1;
						}
						else
						{
							login = 2;
						}
					}
				}
			}
		}
		return login;
	}




	@Override
	public String getNonReturners(String AdminUsername, String AdminPassword,String InstitutionName, int NumDays) 
	{
		String response = null;
		if(AdminUsername.equalsIgnoreCase("admin")&&AdminPassword.equals("admin"))
		{
			response += GetNonReturnersByServer(NumDays);
			for(String Server : ServerNames)
			{
				synchronized(Server)
				{
					if(!Server.equals(this.instituteName))
					{
						try
						{
							String portNumber = getPortNumber(Server);
							LibraryInterface remoteServer = getService(portNumber, Server);
							response += remoteServer.GetNonReturnersByServer(NumDays);
						}
						catch(Exception ex)
						{
							ex.printStackTrace();
						}
					}
				}
			}
		}
		else
			System.out.println("Invalid Login");
		return response;
	}
	@Override
	public String GetNonReturnersByServer(int NumDays)
	{
		StringBuilder sbStudentList = new StringBuilder();
		sbStudentList.append(instituteName+": \n");
		// TODO Auto-generated method stub
		Iterator<?> it = tableStudents.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry pair = (Map.Entry)it.next();
			ArrayList<Student> listStudent = (ArrayList<Student>) pair.getValue();
			if(!listStudent.isEmpty())
			{					
				for(Student objStudent : listStudent)
				{
					if(!objStudent.getReservedBooks().isEmpty())
					{
						Iterator<?> innerIterator = objStudent.getReservedBooks().entrySet().iterator();
						while(innerIterator.hasNext())
						{
							Map.Entry innerPair = (Map.Entry)innerIterator.next();

							if((int)innerPair.getValue()<=(14-NumDays))
							{
								sbStudentList.append(objStudent.getFirstName() +" "+objStudent.getLastName()+" "+objStudent.getPhoneNumber()+"\n");
							}
						}
					}
				}
			}
		}
		return sbStudentList.toString();
	}

	private Student getStudent(String strUserName)
	{
		Student objStudent = null;
		ArrayList<Student> listStudent = tableStudents.get(strUserName.charAt(0));
		if(tableStudents.get(strUserName.charAt(0)) != null){
			synchronized(tableStudents.get(strUserName.charAt(0)))
			{
				if(listStudent.size()>0)
				{
					for(Student student : listStudent)
					{
						if(student.getUserName().equals(strUserName))
						{
							objStudent = student;
						}
					}
				}
			}
		}
		else {
			if(listStudent.size()>0)
			{
				for(Student student : listStudent)
				{
					if(student.getUserName().equals(strUserName))
					{
						objStudent = student;
					}
				}
			}

		}
		return objStudent;
	}

	private LibraryInterface getService(String portNumber,String strInstituteName) throws MalformedURLException
	{
		URL url = new URL("http://localhost:" +portNumber+"/"+strInstituteName+"/ws?wsdl");
		QName qname = new QName("http://server/","LibraryServerService");
		Service service = Service.create(url, qname);
		LibraryInterface store = service.getPort(LibraryInterface.class);
		return store;
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
