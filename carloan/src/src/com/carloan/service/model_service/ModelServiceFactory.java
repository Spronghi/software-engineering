package com.carloan.service.model_service;

public class ModelServiceFactory {
    @SuppressWarnings("unchecked")
	public <T> ModelService<T> getInstance(String key){
        return (ModelService<T>) ModelServiceRegister.getService(key);
    }
    @SuppressWarnings("unchecked")
	public <T> OrderedService<T> getOrderedInstance(String key){
        return (OrderedService<T>) OrderedServiceRegister.getOrderedService(key);
    }
}
