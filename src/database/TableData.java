package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TableData {
    private Statement statement;
    ResultSet resultSet;
    
    public TableData(){
        try {
            statement = DbAccess.getInstance().createStatement();
        } catch (SQLException | DatabaseConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public String getAvaibleCar(){
        try {
            resultSet = statement.executeQuery("SELECT * FROM avaible_car");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        String result = "";
        try {
            while(resultSet.next()){
                result += resultSet.getString("LICENSE_PLATE") + ", ";
                result += resultSet.getString("STATUS") + '\n';
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
