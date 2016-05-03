package com.carloan.integration.database;

import com.carloan.presentation.factory.FXAlert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class SqlConnector implements Connector{
    private final static String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
	private final static String SQL_PATH = "jdbc:mysql://localhost:3306/carloan";
    private final static String USER_ID = "carloan_user";
    private final static String PASSWORD = "popo";
    private Connection connection;
    private static SqlConnector instance;

    private SqlConnector() {
        initConnection();
    }
    static {
        instance = new SqlConnector();
    }
    private void initConnection(){
        try {
			Class.forName(DRIVER_CLASS_NAME).newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
            FXAlert.warning(e,"Database Error");
		}
        try {
        	connection = DriverManager.getConnection(SQL_PATH, USER_ID, PASSWORD);
        	connection.setAutoCommit(false);
    	} catch (SQLException e) {
            FXAlert.warning(e,"Database Error");
        }
    }
    public static SqlConnector getInstance(){
        return instance;
    }
	@Override
	public ResultSet executeQuery(String query){
    	ResultSet resultSet = null;
    	try {
			Statement stmt = connection.createStatement();
			resultSet = stmt.executeQuery(query);
            connection.commit();
		} catch (SQLException e) {
            FXAlert.warning(e,"Database Error");
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
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
            connection.commit();    
		} catch (SQLException e) {
            FXAlert.warning(e,"Database Error");
		}
		return id;
    }
}
