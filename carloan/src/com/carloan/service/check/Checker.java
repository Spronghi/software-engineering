package com.carloan.service.check;

import com.carloan.exception.CreateModelException;
import com.carloan.exception.UpdateModelException;

public interface Checker<T> {
    void checkCreate(T model) throws CreateModelException;
    void checkUpdate(T model) throws UpdateModelException;
}
