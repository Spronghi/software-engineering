package com.carloan.service.util_service;

import com.carloan.business.model.Operator;
import com.carloan.service.ServiceFactory;
import com.carloan.service.control.ServiceControl;
import com.carloan.exception.LoginException;
import com.carloan.service.model_service.ModelService;

import java.util.List;

class LoginService implements LoginServiceInt{
	private static Operator currentOperator;

	public void login(String username, String password) throws LoginException{
        checkLogin(username, password);
        List<Operator> operatorService = ServiceFactory.getModelFactory().<Operator>getInstance(ServiceControl.OPERATOR).getAll();

		currentOperator = operatorService.stream()
                .filter(p->p.getUsername().equals(username) && ServiceFactory.getUtilFactory().getPasswordService().validatePassword(password, p.getPassword()))
                .findFirst().orElseThrow(() -> new LoginException("Operator not Found"));
	}
	public void logout(){
		currentOperator = null;
	}
	public boolean isLogged(){
		return currentOperator!=null;
	}
	public Operator getCurrentOperator(){
		return currentOperator;
	}
	public String getOperatorCredential(){
		return "Operator: "+currentOperator.getLastName()+" "+currentOperator.getFirstName()+", username:"+
				currentOperator.getUsername() +", Agency:"+currentOperator.getAgency().getName()+", ID:"+currentOperator.getId();
	}
    private void checkLogin(String username, String password) throws LoginException {
        String errorMessage = "";
        ModelService<Operator> operatorService = ServiceFactory.getModelFactory().getInstance(ServiceControl.OPERATOR);

        if(username == null || username.equals("")){
            errorMessage += "Username field is empty\n";
        }
        if(password == null || password.equals("")){
            errorMessage += "Password field is empty\n";
        }
        if(errorMessage.equals("")){
            Operator operator = operatorService.getAll().stream()
                    .filter(p->p.getUsername().equalsIgnoreCase(username)).findFirst()
                    .orElseThrow(()->new LoginException("Incorrect username"));

            if(!ServiceFactory.getUtilFactory().getPasswordService().validatePassword(password,operator.getPassword())) {
                errorMessage += "Incorrect password\n";
            }
        }
        if(!errorMessage.equals("")) {
            throw new LoginException(errorMessage);
        }
    }
}
