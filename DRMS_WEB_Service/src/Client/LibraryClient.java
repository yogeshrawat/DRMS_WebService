package Client;

public class LibraryClient {
	
	public static void main(String args[])
	{
		LibraryServerService libraryService = new LibraryServerService();
		LibraryInterface library = libraryService.getLibraryServerPort();
		library.createAccount("vijay", "patil", "vj15", "5145571540", "vj15", "vj1508", "Concordia");
		
	}

}
