package Interface;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;


@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface AdminInterface {
	@WebMethod
	public String getNonReturners(@WebParam String AdminUsername,@WebParam String strPassword,@WebParam String InstitutionName,@WebParam int NumDays) ;	
}
