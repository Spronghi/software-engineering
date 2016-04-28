package com.carloan.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.carloan.business.model.Customer;
import com.carloan.integration.database.ConnectorFactory;
import com.carloan.integration.util.Replacer;
import com.carloan.presentation.factory.FXAlert;

class CustomerDAO implements DAO<Customer> {
	private static final String SELECT_CUSTOMER = "SELECT `id`,`first_name`,`last_name`,`telephone`,`email` FROM customer";
	private static final String SELECT_CUSTOMER_FROM_ID = "SELECT `id`,`first_name`,`last_name`,`telephone`,`email` FROM customer WHERE id=?";
	private static final String INSERT_CUSTOMER = "INSERT INTO customer(first_name,last_name,telephone,email)"
			+ "VALUES('?','?','?','?')";
	private static final String UPDATE_CUSTOMER = "UPDATE customer SET first_name='?',last_name='?',telephone='?',"
			+ "email='?' WHERE id=?";
	private static final String DELETE_CUSTOMER = "DELETE FROM customer WHERE id=?";

    private Customer setCustomer(ResultSet rs) throws SQLException{
		Customer customer = new Customer();
        customer.setId(rs.getInt(1));
        customer.setFirstName(rs.getString(2));
        customer.setLastName(rs.getString(3));
        customer.setTelephone(rs.getString(4));
        customer.setEmail(rs.getString(5));
        return customer;
	}
	@Override
	public Customer get(int id) {
		String query = SELECT_CUSTOMER_FROM_ID;
        query = Replacer.replaceFirst(query, id);
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
        try {
            if(rs.next()) {
                return setCustomer(rs);
            }
        } catch (SQLException e) {
            FXAlert.warning(e,"Database Error");
        }
        return null;
	}
	@Override
	public List<Customer> getAll() {
		List<Customer> customers = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT_CUSTOMER);
        try {
            while(rs.next()) {
                customers.add(setCustomer(rs));
            }
        } catch (SQLException e) {
            FXAlert.warning(e,"Database Error");
        }
        return customers;
	}
	@Override
	public void create(Customer customer) {
		String query = INSERT_CUSTOMER;
        query = Replacer.replaceFirst(query, customer.getFirstName());
        query = Replacer.replaceFirst(query, customer.getLastName());
        query = Replacer.replaceFirst(query, customer.getTelephone());
        query = Replacer.replaceFirst(query, customer.getEmail());
        customer.setId(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void update(Customer customer) {
		String query = UPDATE_CUSTOMER.replaceFirst("[?]", customer.getFirstName())
				.replaceFirst("[?]", customer.getLastName())
				.replaceFirst("[?]", customer.getTelephone())
				.replaceFirst("[?]", customer.getEmail())
				.replaceFirst("[?]", Integer.toString(customer.getId()));
        query = Replacer.replaceFirst(query, customer.getFirstName());
        query = Replacer.replaceFirst(query, customer.getLastName());
        query = Replacer.replaceFirst(query, customer.getTelephone());
        query = Replacer.replaceFirst(query, customer.getEmail());
        query = Replacer.replaceFirst(query, customer.getId());
        ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Customer customer) {
		String query = DELETE_CUSTOMER;
        query = Replacer.replaceFirst(query, customer.getId());
        ConnectorFactory.getConnection().executeUpdate(query);
	}

}
