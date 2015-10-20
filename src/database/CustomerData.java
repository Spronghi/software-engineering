package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import address.model.Customer;

public class CustomerData implements CustomerDataInterface{
    private Statement statement;
    ResultSet resultSet;
    private ObservableList<Customer> container;
    
    public CustomerData(){
        try {
            statement = DbAccess.getInstance().createStatement();
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        }
        container = populateContainer();
    }
    public ObservableList<Customer> getContainer(){
    	return container;
    }

    private ObservableList<Customer> populateContainer(){
    	String SQL = "SELECT * FROM customer";
        try {
            resultSet = statement.executeQuery(SQL);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        ObservableList<Customer> resultList = FXCollections.observableArrayList();
        try {
            while(resultSet.next()){
            	Customer istance = new Customer(resultSet.getString("FIRST_NAME"),
            			resultSet.getString("LAST_NAME"),resultSet.getString("TELEPHONE"),
            			resultSet.getString("EMAIL"),resultSet.getString("USERNAME"),
            			resultSet.getString("PASSWORD"));
    			resultList.add(istance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
    public Customer get(String username) throws NullPointerException{
		for(Customer customer: container)
			if(customer.getUsername().compareToIgnoreCase(username) == 0)
				return customer;
		return null;
	}
    public Customer log(String username, String password) throws NullPointerException{
		for(Customer customer: container)
			if(customer.getUsername().equalsIgnoreCase(username) || customer.getEmail().equalsIgnoreCase(username)
					&& (customer.getPassword().compareTo(password)==0))
				return customer;
		return null;
	}
    public void add(Customer customer){
    	String SQL = "INSERT INTO customer(first_name, last_name, telephone, email, username, password) VALUES ";
    	SQL += "('"+ customer.getFirstName()+"',";
    	SQL += "'"+ customer.getLastName()+"',";
    	SQL += "'"+ customer.getTelephone()+"',";
    	SQL += "'"+ customer.getEmail()+"',";
    	SQL += "'"+ customer.getUsername()+"',";
    	SQL += "'"+ customer.getPassword()+"');";
        try {
            statement.executeUpdate(SQL);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        container.add(customer);
    }
}
