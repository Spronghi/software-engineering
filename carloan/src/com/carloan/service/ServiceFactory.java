package com.carloan.service;

import com.carloan.service.model_service.ModelServiceFactory;
import com.carloan.service.util_service.UtilServiceFactory;

public interface ServiceFactory {
    public static ModelServiceFactory getModelFactory(){
        return new ModelServiceFactory();
    }
    public static UtilServiceFactory getUtilFactory(){
        return new UtilServiceFactory();
    }
}
