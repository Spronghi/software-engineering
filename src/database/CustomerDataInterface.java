package database;

import javafx.collections.ObservableList;
import address.model.Customer;

public interface CustomerDataInterface {
    public ObservableList<Customer> getContainer();
    public Customer get(String username);
    public void add(Customer contract);
    public Customer log(String username, String password);
}
