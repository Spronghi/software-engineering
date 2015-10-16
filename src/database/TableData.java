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
    	String SQL = "SELECT * FROM car_view";
        try {
            resultSet = statement.executeQuery(SQL);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        ObservableList<Car> resultList = FXCollections.observableArrayList();
        try {
            while(resultSet.next()){
                Car istance = new Car(resultSet.getInt("ID"), resultSet.getString("NAME"),
                		resultSet.getString("LICENSE_PLATE"), resultSet.getInt("LAST_KM"), 
                		resultSet.getString("CATEGORY"), resultSet.getString("STATUS"));
                resultList.add(istance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
    public void editCar(Car car){
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
    			"',license_plate='"+car.getLicensePlate()+"', car_category_id="+categoryId.toString()+
    			",car_status_id="+statusId.toString()+" WHERE id="+car.getId().toString()+";";
    	try {
            statement.executeUpdate(SQL);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    public void newCar(Car car){
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
	 public void deleteCar(Car car){
	    	String SQL = "DELETE FROM car WHERE ID=" + car.getId().toString();
	    	try {
	            statement.executeUpdate(SQL);
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
    }
}



