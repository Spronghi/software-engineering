package database;

import javafx.collections.ObservableList;
import address.model.Car;
import address.model.Contract;

public interface ContractDataInterface {
	public ObservableList<Contract> getContainer();
    public Contract get(Car car);
}
