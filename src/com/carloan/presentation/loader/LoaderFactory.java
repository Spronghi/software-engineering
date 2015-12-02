package com.carloan.presentation.loader;

public enum LoaderFactory {	
	INSTANCE;
	public static Loader getInstance(String clazz){
		return RegisterService.getLoader(clazz);
	}
}