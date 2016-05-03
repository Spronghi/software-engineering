package com.carloan.service.util_service;


import com.carloan.business.model.Operator;
import com.carloan.exception.LoginException;

public interface LoginServiceInt {
    public void login(String username, String password) throws LoginException;
    public void logout();
    public boolean isLogged();
    public Operator getCurrentOperator();
    public String getOperatorCredential();
}
