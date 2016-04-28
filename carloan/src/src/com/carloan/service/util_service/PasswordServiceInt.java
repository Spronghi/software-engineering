package com.carloan.service.util_service;

import com.carloan.business.model.Operator;
import com.carloan.exception.UpdateModelException;

public interface PasswordServiceInt {
    public void updatePassword(Operator operator) throws UpdateModelException;
    public String generatePasswordHash(String password);
    public boolean validatePassword(String originalPassword, String storedPassword);
}
