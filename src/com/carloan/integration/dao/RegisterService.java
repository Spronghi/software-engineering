package com.carloan.integration.dao;

import java.util.HashMap;
import java.util.Map;

import com.carloan.business.model.Agency;
import com.carloan.business.model.Car;
import com.carloan.business.model.Contract;
import com.carloan.business.model.ContractType;
import com.carloan.business.model.Currency;
import com.carloan.business.model.Customer;
import com.carloan.business.model.Location;
import com.carloan.business.model.Operator;
import com.carloan.business.model.Payment;
import com.carloan.business.model.PaymentType;
import com.carloan.business.model.PriceCategory;
import com.carloan.business.model.Status;

enum RegisterService {
	INSTANCE;
	private static Map< Class<?>, DAO<?> > container;
	static { 
		container = new HashMap<>();
		container.put(Agency.class, new AgencyDAO());
		container.put(Location.class, new LocationDAO());
		container.put(Status.class, new CarStatusDAO());
		container.put(PriceCategory.class, new PriceCategoryDAO());
		container.put(Car.class, new CarDAO());
		container.put(ContractType.class, new ContractTypeDAO());
		container.put(Currency.class, new CurrencyDAO());
		container.put(Customer.class, new CustomerDAO());
		container.put(Operator.class, new OperatorDAO());
		container.put(PaymentType.class, new PaymentTypeDAO());
		container.put(Contract.class, new ContractDAO());
		container.put(Payment.class, new PaymentDAO());
	}
	public static DAO<?> getDAO(Class<?> clazz){
		return container.get(clazz);
	}
}
