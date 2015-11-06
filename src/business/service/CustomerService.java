package business.service;

import interation.dao.DAOFactory;
import interation.dao.GenericDAO;

import java.util.List;

import business.model.Customer;

class CustomerService extends GenericService<Customer>{
	GenericDAO<Customer> customerDAO;
	public CustomerService() {
		// TODO Auto-generated constructor stub
		customerDAO = DAOFactory.getInstance("CustomerDAO");
	}

	@Override
	public List<Customer> getAll() {
		// TODO Auto-generated method stub
		return customerDAO.getAll();
	}

	@Override
	public void add(Customer model) {
		// TODO Auto-generated method stub
		customerDAO.add(model);
	}

	@Override
	public void edit(Customer model) {
		// TODO Auto-generated method stub
		customerDAO.edit(model);
	}

	@Override
	public void delete(Customer model) {
		// TODO Auto-generated method stub
		customerDAO.delete(model);
	}

}
