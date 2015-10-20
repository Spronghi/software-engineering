package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import address.model.Agency;
import address.model.Location;

public class AgencyData implements AgencyDataInterface{
	private Statement statement;
    ResultSet resultSet;
    private ObservableList<Agency> container;
    
    public AgencyData(){
        try {
            statement = DbAccess.getInstance().createStatement();
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
        }
        container = populateContainer();
    }
    public ObservableList<Agency> getContainer(){
    	return container;
    }
    private ObservableList<Agency> populateContainer(){
    	String SQL = "SELECT * FROM agency_location_view";
        try {
            resultSet = statement.executeQuery(SQL);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        ObservableList<Agency> resultList = FXCollections.observableArrayList();
        try {
            while(resultSet.next()){
            	Location locationIstance = new Location(resultSet.getString("CITY"), resultSet.getString("CAP"),
            			resultSet.getString("ADDRESS"),"","");
            	Agency agencyIstance = new Agency(resultSet.getString("NAME"), locationIstance);
                resultList.add(agencyIstance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
    public Agency get(String name) throws NullPointerException{
		for(Agency agency: container)
			if(agency.getName().compareToIgnoreCase(name)==0)
				return agency;
		return null;
	}
}
