package interation.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class SqlConnector {
    private static String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
	private final static String SQL_PATH = "jdbc:mysql://localhost:3306/carloan";
    private final static String USER_ID = "CarloanUser";
    private final static String PASSWORD = "popo";
    private static SqlConnector instance;
    private static Connection connection;
    
    private SqlConnector(){
        try {
			Class.forName(DRIVER_CLASS_NAME).newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e1) {
			e1.printStackTrace();
		}
        try {
        	connection = DriverManager.getConnection(SQL_PATH, USER_ID, PASSWORD);
        	connection.setAutoCommit(false);
        	Statement stmt=connection.createStatement();
    		for(String sql : Queries.array())
    			stmt.executeUpdate(sql);
    		stmt.close();        
    	} catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection(){
    	if(instance == null){
    		instance = new SqlConnector();
    	}
    	return connection;
    }

}
