package com.carloan.service.util_service;


public class UtilServiceFactory {
    public LoginServiceInt getLoginService(){
        return new LoginService();
    }
    public PasswordServiceInt getPasswordService(){
        return new PasswordService();
    }
    public PermissionServiceInt getPermissionService(){
        return new PermissionService();
    }
}
