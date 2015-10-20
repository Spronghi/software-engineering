package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import address.model.Agency;
import address.model.Car;
import address.model.Contract;
import address.model.Customer;

public class ContractData implements ContractDataInterface{
    private Statement statement;
    ResultSet resultSet;
    private ObservableList<Contract> container;
    public ContractData(){
        try {
            statement = DbAccess.getInstance().createStatement();
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        }
        container = populateContainer();
    }
    public ObservableList<Contract> getContainer(){
    	return container;
    }
    private ObservableList<Contract> populateContainer(){
    	String SQL = "SELECT * FROM contract_view";
        try {
            resultSet = statement.executeQuery(SQL);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        ObservableList<Contract> resultList = FXCollections.observableArrayList();
        try {
            while(resultSet.next()){
            	Car car=null;
    			Agency agency=null;
    			Agency returnAgency=null;
    			Customer customer=null;
            	try{
	            	car = Data.car.get(resultSet.getString("LICENSE_PLATE"));
	            	agency = Data.agency.get(resultSet.getString("AGENCY_NAME"));
	            	returnAgency = Data.agency.get(resultSet.getString("RETURN_AGENCY_NAME"));
	            	customer = Data.customer.get(resultSet.getString("CUSTOMER_USERNAME"));
            	}catch(NullPointerException e){
            		e.getMessage();
            	}
            	Contract istance = new Contract(resultSet.getInt("ID"),resultSet.getInt("CONTRACT_NO"),
            			resultSet.getString("START"),resultSet.getString("RETURN_LIMIT"),
            			resultSet.getString("END"),resultSet.getBoolean("KM_LIMIT"), 
            			resultSet.getInt("END_KM"), customer, car, agency,returnAgency,
            			resultSet.getString("CONTRACT_TYPE"));
    			resultList.add(istance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
    public Contract get(Car car)throws NullPointerException{
		for(Contract contract: container)
			if(contract.getCar().getLicensePlate().compareToIgnoreCase(car.getLicensePlate())==0)
				return contract;
		return null;
    }
}
