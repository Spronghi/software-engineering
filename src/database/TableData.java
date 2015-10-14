package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import address.model.Car;

public class TableData {
    private Statement statement;
    ResultSet resultSet;
    
    public TableData(){
        try {
            statement = DbAccess.getInstance().createStatement();
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<Car> getAvaibleCar(){
        try {
            resultSet = statement.executeQuery("SELECT * FROM car_view");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        ObservableList<Car> resultList = FXCollections.observableArrayList();
        try {
            while(resultSet.next()){
                Car istance = new Car(resultSet.getString("LICENSE_PLATE"), 
                        resultSet.getString("LAST_KM"), resultSet.getString("CATEGORY"),
                        resultSet.getString("STATUS"));
                resultList.add(istance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}