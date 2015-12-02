package com.carloan.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.carloan.business.model.Customer;
import com.carloan.integration.database.ConnectorFactory;

class CustomerDAO extends DAO<Customer> {
	private static final String SELECT_CUSTOMER = "SELECT * FROM customer";
	private static final String SELECT_CUSTOMER_FROM_ID = "SELECT * FROM customer WHERE id=?";
	private static final String INSERT_CUSTOMER = "INSERT INTO customer(first_name,last_name,telephone,email)"
			+ "VALUES('?','?','?','?')";
	private static final String UPDATE_CUSTOMER = "UPDATE customer SET first_name='?',last_name='?',telephone='?',"
			+ "email='?' WHERE id=?";
	private static final String DELETE_CUSTOMER = "DELETE FROM customer WHERE first_name='?' && last_name='?' && "
			+ "telephone='?' && email='?'";
	
	private Customer setCustomer(ResultSet rs) throws SQLException{
		Customer customer = new Customer(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
		customer.setId(rs.getInt(1));
		return customer;
	}
	@Override
	public Customer get(int id){
		String query = SELECT_CUSTOMER_FROM_ID.replaceFirst("[?]", Integer.toString(id));
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
		try {
			if(rs.next())
				return setCustomer(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<Customer> getAll() {
		List<Customer> customers = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT_CUSTOMER);
		try {
			while(rs.next())
				customers.add(setCustomer(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}
	@Override
	public void create(Customer customer) {
		String query = INSERT_CUSTOMER.replaceFirst("[?]", customer.getFirstName())
				.replaceFirst("[?]", customer.getLastName())
				.replaceFirst("[?]", customer.getTelephone())
				.replaceFirst("[?]", customer.getEmail());
		customer.setId(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void update(Customer customer) {
		String query = UPDATE_CUSTOMER.replaceFirst("[?]", customer.getFirstName())
				.replaceFirst("[?]", customer.getLastName())
				.replaceFirst("[?]", customer.getTelephone())
				.replaceFirst("[?]", customer.getEmail())
				.replaceFirst("[?]", Integer.toString(customer.getId()));
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Customer customer) {
		String query = DELETE_CUSTOMER.replaceFirst("[?]", customer.getFirstName())
				.replaceFirst("[?]", customer.getLastName())
				.replaceFirst("[?]", customer.getTelephone())
				.replaceFirst("[?]", customer.getEmail());
		ConnectorFactory.getConnection().executeUpdate(query);
	}

}
