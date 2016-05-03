package com.carloan.business.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class RegisterService {
	private Map<String, Object> container;
	
	public RegisterService(){
		container = new HashMap<>();
		container.put("Agency", new Agency());
		container.put("Car", new Car());
		container.put("Contract", new Contract());
		container.put("Customer", new Customer());
		container.put("Location", new Location());
		container.put("Operator", new Operator());
		container.put("Payment", new Payment());
		container.put("Payments", new ArrayList<Payment>());
		container.put("PriceCategory", new PriceCategory());
	}
	@SuppressWarnings("unchecked")
	public <T> T getModel(String clazz){
		return (T) container.get(clazz);
	}
}