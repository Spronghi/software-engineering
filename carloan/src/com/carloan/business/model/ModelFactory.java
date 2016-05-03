package com.carloan.business.model;

public class ModelFactory {
	private RegisterService register;
	
	public ModelFactory(){
		register = new RegisterService();
	}
	public <T> T getInstance(String clazz){
		return register.getModel(clazz);
	}
}
