package com.carloan.service.login;

import com.carloan.business.model.Operator;
import com.carloan.service.exception.CommonServiceException;
import com.carloan.service.operator.OperatorService;

public enum LoginService {
	INSTANCE;
	private static Operator currentOperator;
	static {
		currentOperator = OperatorService.getAll().stream().filter(p->p.getUsername().equals("admin")).findFirst().get();
	}
	public static void login(String username, String password) throws CommonServiceException{
		currentOperator = OperatorService.get(username, password);
	}
	public static void logout(){
		currentOperator = null;
	}
	public static boolean isLogged(){
		return currentOperator!=null;
	}
	public static Operator getCurrentOperator(){
		return currentOperator;
	}
	public static String getOperatorCredential(){
		return "Operator: "+currentOperator.getLastName()+" "+currentOperator.getFirstName()+", username:"+
				currentOperator.getUsername() +", ID:"+currentOperator.getId();
	}
}
