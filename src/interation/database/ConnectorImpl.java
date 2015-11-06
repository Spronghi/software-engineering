package interation.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class ConnectorImpl implements Connector {
	public ConnectorImpl(){}
	@Override
	public ResultSet executeQuery(String query){
    	ResultSet resultSet = null;
    	try {
			Statement stmt = SqlConnector.getConnection().createStatement();
			resultSet = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
    }
	@Override
    public int executeUpdate(String query){
		int result=0;
		try {
			Statement stmt = SqlConnector.getConnection().createStatement();
			stmt.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next());
				result=rs.getInt(1);
			SqlConnector.getConnection().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
    }
}
