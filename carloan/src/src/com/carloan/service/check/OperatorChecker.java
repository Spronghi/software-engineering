package com.carloan.service.check;

import com.carloan.business.model.Operator;
import com.carloan.service.ServiceFactory;
import com.carloan.service.model_service.ModelService;
import com.carloan.service.control.ServiceControl;
import com.carloan.exception.*;

class OperatorChecker implements Checker<Operator>{
    private String errorMessage = "";
    public void checkCreate(Operator operator) throws CreateModelException {
        errorMessage="";
        checkFirstName(operator.getFirstName());
        checkLastName(operator.getLastName());
        checkEmail(operator.getEmail(), "create");
        checkUsername(operator.getUsername(), "create");
        checkPassword(operator.getPassword(),"create");
        if(!errorMessage.equals("")) {
            throw new CreateModelException(errorMessage);
        }
    }
    public void checkUpdate(Operator operator) throws UpdateModelException {
        errorMessage="";
        checkFirstName(operator.getFirstName());
        checkLastName(operator.getLastName());
        checkEmail(operator.getEmail(), "update");
        checkUsername(operator.getUsername(), "update");
        if(!errorMessage.equals("")) {
            throw new UpdateModelException(errorMessage);
        }
    }
    private void checkFirstName(String firstName){
        if(firstName.equals("") || firstName == null){
            errorMessage += "First name field is empty\n";
        }
    }
    private void checkLastName(String lastName){
        if(lastName.equals("") || lastName == null){
            errorMessage += "First name field is empty\n";
        }
    }
    private void checkEmail(String email, String operation){
        ModelService<Operator> operatorService = ServiceFactory.getModelFactory().getInstance(ServiceControl.OPERATOR);
        if(email.equals("") || email == null){
            errorMessage += "E-mail field is empty\n";
        } else if(!email.matches(".*[A-Za-z0-9-]@.*[A-Za-z0-9-]\\..*[A-Za-z0-9-]")){
            errorMessage += "Incorrect e-mail address\n";
        } else if(operation.equals("create") && operatorService.getAll().stream().anyMatch(p->p.getEmail().equalsIgnoreCase(email))){
            errorMessage += "E-mail address already used\n";
        }
    }
    private void checkUsername(String username, String operation){
        ModelService<Operator> operatorService = ServiceFactory.getModelFactory().getInstance(ServiceControl.OPERATOR);
        if(username.equals("") || username == null){
            errorMessage += "Username field is empty\n";
        } else if(operation.equals("create") && operatorService.getAll().stream().anyMatch(p->p.getUsername().equalsIgnoreCase(username))){
            errorMessage += "The username is already used\n";
        }
    }
    private void checkPassword(String password, String operation){
        if(password.equals("") || password == null){
            errorMessage += "Password field is empty\n";
        } else if(operation.equals("create") || operation.equals("updatePassword")) {
            if (!password.matches("(?=.*[0-9]).*")) {
                errorMessage += "The password must contain at least one number\n";
            } else if (!password.matches("(.*).{5,}")) {
                errorMessage += "At least 4 letters and 1 number in password\n";
            }
        }
    }
}