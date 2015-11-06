package interation.dao.exception;

public class AgencyNotFoundException extends Exception {
	private static final long serialVersionUID = 9088079969350536503L;
	public AgencyNotFoundException(){
		super();
	}
	public AgencyNotFoundException(String message){
		super(message);
	}

}
