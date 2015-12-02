package com.carloan.presentation.loader;

import java.util.HashMap;
import java.util.Map;

enum RegisterService {
	INSTANCE;
	private static Map<String, Loader> container;
	static {
		container = new HashMap<>();
		container.put("HomeView", new HomeViewLoader());
		container.put("OpenContractView", new OpenContractViewLoader());
	}
	
	public static Loader getLoader(String clazz){
		return container.get(clazz);
	}
}
