package com.carloan.service.check;

import com.carloan.business.model.Operator;
import com.carloan.exception.CreateModelException;
import com.carloan.exception.UpdateModelException;

class PasswordChecker implements Checker<Operator>{
    private String errorMessage = "";
    public void checkCreate(Operator operator) throws CreateModelException {
        errorMessage="";
        checkPassword(operator.getPassword(), "updatePassword");
        if(!errorMessage.equals("")) {
            throw new CreateModelException(errorMessage);
        }
    }
    public void checkUpdate(Operator operator) throws UpdateModelException {
        errorMessage="";
        checkPassword(operator.getPassword(), "updatePassword");
        if(!errorMessage.equals("")) {
            throw new UpdateModelException(errorMessage);
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
