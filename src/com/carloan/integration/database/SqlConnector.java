package com.carloan.integration.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class SqlConnector implements Connector{
    private static String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
	private final static String SQL_PATH = "jdbc:mysql://localhost:3306/carloan";
    private final static String USER_ID = "CarloanUser";
    private final static String PASSWORD = "popo";
    private Connection connection;
    private static SqlConnector instance;
    
    private SqlConnector(){
    	initConnection();
    	initDb();
    }
    private void initConnection(){
        try {
			Class.forName(DRIVER_CLASS_NAME).newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e1) {
			e1.printStackTrace();
		}
        try {
        	connection = DriverManager.getConnection(SQL_PATH, USER_ID, PASSWORD);
        	connection.setAutoCommit(false);
    	} catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void initDb(){
		try{
	    	Statement stmt=connection.createStatement();
			for(String sql : Queries.array())
				stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
	        e.printStackTrace();
	    }
    }
    public static SqlConnector getInstance(){
    	if(instance==null)
    		instance = new SqlConnector();
    	return instance;
    }
    public Connection getConnection(){
    	return connection;
    }
	@Override
	public ResultSet executeQuery(String query){
    	ResultSet resultSet = null;
    	try {
			Statement stmt = connection.createStatement();
			resultSet = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
    }
	@Override
    public int executeUpdate(String query){
		int id=0;
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
			ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next())
                id = generatedKeys.getInt(1);
            connection.commit();    
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
    }
}
