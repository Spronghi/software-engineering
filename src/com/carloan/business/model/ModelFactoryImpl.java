package com.carloan.business.model;

public class ModelFactoryImpl implements ModelFactory{
	private RegisterService register;
	
	public ModelFactoryImpl(){
		register = new RegisterService();
	}
	public <T> T getInstance(String clazz){
		return register.getModel(clazz);
	}
}
