package database;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DbAccess {
    private static String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
    private final static String DBMS = "jdbc:mysql";
    private final static String SERVER = "localhost";
    private final static String DATABASE = "CarloanDB";
    private final static String PORT = "3306";
    private final static String USER_ID = "CarloanUser";
    private final static String PASSWORD = "carloan";
    private static Connection conn;

    private static void initConnection() throws DatabaseConnectionException {
        String connectionString = DBMS+"://" + SERVER + ":" + PORT + "/" + DATABASE;
        try {
            Class.forName(DRIVER_CLASS_NAME).newInstance();
        } catch (Exception ex) {
        }
        try {
            conn = DriverManager.getConnection(connectionString, USER_ID, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getInstance() throws DatabaseConnectionException { 
        if(conn==null){
            DbAccess.initConnection();
        }
        return conn;
    }
    public static void closeConnection(){
        try{
            conn.close();
        } catch(SQLException e){
            e.getMessage();
        }
    }
}