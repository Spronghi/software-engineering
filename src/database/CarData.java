package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import address.model.Car;

public class CarData implements CarDataInterface{
    private Statement statement;
    ResultSet resultSet;
    private ObservableList<Car> container;
    
    public CarData(){
        try {
            statement = DbAccess.getInstance().createStatement();
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        }
        container = populateContainer();
    }
    public ObservableList<Car> getContainer(){
    	return container;
    }
    private ObservableList<Car> populateContainer(){
    	String SQL = "SELECT * FROM car_view";
        try {
            resultSet = statement.executeQuery(SQL);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        ObservableList<Car> resultList = FXCollections.observableArrayList();
        try {
            while(resultSet.next()){
                Car istance = new Car(resultSet.getString("NAME"),
                		resultSet.getString("LICENSE_PLATE"), resultSet.getInt("LAST_KM"), 
                		resultSet.getString("CATEGORY"), resultSet.getString("STATUS"));
                resultList.add(istance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
    public void edit(Car car){
    	Integer categoryId=0;
    	try {
			resultSet = statement.executeQuery("SELECT id FROM car_category WHERE category='"+car.getCategory()+"';");
			resultSet.next();
	    	categoryId = resultSet.getInt("ID");
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	Integer statusId=0;
    	try {
			resultSet = statement.executeQuery("SELECT id FROM car_status WHERE status='"+car.getStatus()+"';");
			resultSet.next();
			statusId = resultSet.getInt("ID");
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	String SQL="UPDATE car SET name='"+car.getName()+"',last_km='"+car.getLastKm().toString()+
    			"', car_category_id="+categoryId.toString()+ ",car_status_id="+statusId.toString()+
    			" WHERE license_plate="+car.getLicensePlate()+";";
    	try {
            statement.executeUpdate(SQL);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    public void add(Car car){
    	Integer categoryId=0;
    	try {
			resultSet = statement.executeQuery("SELECT id FROM car_category WHERE category='"+car.getCategory()+"';");
			resultSet.next();
			categoryId = resultSet.getInt("ID");
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	Integer statusId=0;
    	try {
			resultSet = statement.executeQuery("SELECT id FROM car_status WHERE status='"+car.getStatus()+"';");
			resultSet.next();
			statusId = resultSet.getInt("ID");
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	String SQL = "INSERT INTO car(name, license_plate, last_km, car_category_id, car_status_id) VALUES ('" + 
    			car.getName()+"','"+car.getLicensePlate()+ "',"+car.getLastKm().toString()+","+
    			categoryId.toString()+","+statusId.toString()+");";
    	try {
            statement.executeUpdate(SQL);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
	public void delete(Car car){
        String SQL = "DELETE FROM car WHERE LICENSE_PLATE=" + car.getLicensePlate()+";";
    	try {
            statement.executeUpdate(SQL);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
	}
	public Car get(String licensePlate) throws NullPointerException{
		for(Car car: container)
			if(car.getLicensePlate().compareToIgnoreCase(licensePlate)==0)
				return car;
		return null;
	}
}



