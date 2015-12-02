package com.carloan.service.customer;

import java.util.List;

import com.carloan.business.model.Customer;
import com.carloan.integration.dao.DAOFactory;
import com.carloan.service.exception.CommonServiceException;

public enum CustomerService {
	INSTANCE;
	public static List<Customer> getAll() {
		return DAOFactory.getInstance(Customer.class).getAll();
	}
	public static Customer get(int id) {
		return DAOFactory.getInstance(Customer.class).get(id);
	}
	public static void create(Customer customer) throws CommonServiceException {
		CustomerChecker.checkCreate(customer);
		DAOFactory.getInstance(Customer.class).create(customer);
	}

	public static void update(Customer customer) throws CommonServiceException {
		CustomerChecker.checkUpdate(customer);
		DAOFactory.getInstance(Customer.class).update(customer);
	}

	public static void delete(Customer customer) {
		DAOFactory.getInstance(Customer.class).delete(customer);
	}
}
