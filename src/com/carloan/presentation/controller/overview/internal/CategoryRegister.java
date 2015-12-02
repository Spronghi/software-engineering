package com.carloan.presentation.controller.overview.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.carloan.business.model.Agency;
import com.carloan.business.model.Car;
import com.carloan.business.model.Contract;
import com.carloan.business.model.Customer;
import com.carloan.business.model.Location;
import com.carloan.business.model.Operator;
import com.carloan.business.model.Payment;
import com.carloan.business.model.PriceCategory;

public enum CategoryRegister {
	INSTANCE;
	private static Map<Class<?>, String> container;
	
	static {
		container = new HashMap<>();
		container.put(Agency.class, "Agency");
		container.put(Car.class, "Car");
		container.put(Contract.class, "Contract");
		container.put(Customer.class, "Customer");
		container.put(Location.class, "Location");
		container.put(Operator.class, "Operator");
		container.put(Payment.class, "Payment");
		container.put(ArrayList.class, "Payments");
		container.put(PriceCategory.class, "PriceCategory");
	}
	public static String get(Class<?> clazz){
		return container.get(clazz);
	}
}
