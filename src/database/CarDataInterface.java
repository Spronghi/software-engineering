package database;

import javafx.collections.ObservableList;
import address.model.Car;

public interface CarDataInterface {
    public ObservableList<Car> getContainer();
    public void edit(Car car);
    public void add(Car car);
    public void delete(Car car);
    public Car get(String licensePlate);
}
