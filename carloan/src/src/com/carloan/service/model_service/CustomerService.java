package com.carloan.service.model_service;

import java.util.List;

import com.carloan.business.model.Customer;
import com.carloan.integration.dao.DAOFactory;
import com.carloan.service.check.CheckerFactory;
import com.carloan.service.control.ServiceControl;
import com.carloan.exception.CreateModelException;
import com.carloan.exception.UpdateModelException;

class CustomerService implements ModelService<Customer> {
	public List<Customer> getAll() {
		return DAOFactory.getInstance(Customer.class).getAll();
	}
	public Customer get(int id) {
		return DAOFactory.getInstance(Customer.class).get(id);
	}
	public void create(Customer customer) throws CreateModelException {
        CheckerFactory.getInstance(ServiceControl.CUSTOMER).checkCreate(customer);
        DAOFactory.getInstance(Customer.class).create(customer);
	}
	public void update(Customer customer) throws UpdateModelException {
        CheckerFactory.getInstance(ServiceControl.CUSTOMER).checkUpdate(customer);
        DAOFactory.getInstance(Customer.class).update(customer);
	}
	public void delete(Customer customer) {
		DAOFactory.getInstance(Customer.class).delete(customer);
	}
}
