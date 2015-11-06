package interation.dao;

import interation.database.ConnectorFactory;
import business.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class CustomerDAO extends GenericDAO<Customer> {	
	public CustomerDAO() {
	}
	private Customer setCustomer(ResultSet rs) throws SQLException{
		Customer customer= new Customer(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
		customer.setId(rs.getInt(1));
		return customer;
	}
	@Override
	public List<Customer> getAll() {
		List<Customer> customers = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(Queries.SELECT_CUSTOMER);
		try {
			while(rs.next())
				customers.add(setCustomer(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}
	@Override
	public void add(Customer customer) {
		String query = Queries.INSERT_CUSTOMER.replaceFirst("[?]", customer.getFirstName())
				.replaceFirst("[?]", customer.getLastName())
				.replaceFirst("[?]", customer.getTelephone())
				.replaceFirst("[?]", customer.getEmail());
		customer.setId(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void edit(Customer customer) {
		String query = Queries.UPDATE_CUSTOMER.replaceFirst("[?]", customer.getFirstName())
				.replaceFirst("[?]", customer.getLastName())
				.replaceFirst("[?]", customer.getTelephone())
				.replaceFirst("[?]", customer.getEmail())
				.replaceFirst("[?]", Integer.toString(customer.getId()));
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Customer customer) {
		String query = Queries.DELETE_CUSTOMER.replaceFirst("[?]", customer.getFirstName())
				.replaceFirst("[?]", customer.getLastName())
				.replaceFirst("[?]", customer.getTelephone())
				.replaceFirst("[?]", customer.getEmail());
		ConnectorFactory.getConnection().executeUpdate(query);
	}

}
