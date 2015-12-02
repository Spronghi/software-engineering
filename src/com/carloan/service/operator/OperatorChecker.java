package com.carloan.service.operator;

import com.carloan.business.model.Operator;
import com.carloan.service.exception.UpdateOperatorException;
import com.carloan.service.exception.OperatorNotFoundException;

enum OperatorChecker{
	INSTANCE;
	public static void checkUpdate(Operator operator) throws UpdateOperatorException {
		String errorMessage="";
		if(operator.getFirstName().equals("") || operator.getFirstName() == null){
			errorMessage += "First name field is empty\n";
		}
		if(operator.getLastName().equals("") || operator.getLastName() == null){
			errorMessage += "Last name field is empty\n";
		}
		if(operator.getEmail().equals("") || operator.getEmail() == null){
			errorMessage += "E-mail field is empty\n";
		} else if(!operator.getEmail().matches(".*[A-Za-z0-9-]@.*[A-Za-z0-9-]\\..*[A-Za-z0-9-]")){
			errorMessage += "Uncorrect e-mail address\n";
		} else if(OperatorService.getAll().stream().anyMatch(p->p.getEmail().equalsIgnoreCase(operator.getEmail()))){
			errorMessage += "E-mail address already used\n";
		}
		if(operator.getUsername().equals("") || operator.getUsername() == null){
			errorMessage += "Username field is empty\n";
		} else if(OperatorService.getAll().stream().anyMatch(p->p.getUsername().equalsIgnoreCase(operator.getUsername()))){
			errorMessage += "The username is already used\n";
		}
		if(operator.getPassword().equals("") || operator.getPassword() == null){
			errorMessage += "Password field is empty\n";
		} else if(!operator.getPassword().matches("(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{5,}")){
			errorMessage += "The password must contain at least one number\n";
		}
		if(!errorMessage.equals(""))
			throw new UpdateOperatorException(errorMessage);
	}
	public static void checkLogin(String username, String password) throws OperatorNotFoundException {
		String errorMessage="";
		if(username.equals("") || username == null){
			errorMessage += "Username field is empty\n";
		} else if(password.equals("") || password == null){
			errorMessage += "Password field is empty\n";
		} else if(!OperatorService.getAll().stream().anyMatch(p->p.getUsername().equalsIgnoreCase(username))){
			errorMessage += "Uncorrect username\n";
		} else if(!OperatorService.getAll().stream().anyMatch(p->p.getPassword().equals(password))){
			errorMessage += "Uncorrect password\n";
		}
		if(!errorMessage.equals(""))
			throw new OperatorNotFoundException(errorMessage);
	}

}
