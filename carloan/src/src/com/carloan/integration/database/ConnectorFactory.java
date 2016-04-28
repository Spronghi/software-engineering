package com.carloan.integration.database;


public class ConnectorFactory {
	public static Connector getConnection(){
		return SqlConnector.getInstance();
	}
}
