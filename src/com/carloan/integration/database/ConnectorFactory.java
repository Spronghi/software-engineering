package com.carloan.integration.database;


public enum ConnectorFactory {
	INSTANCE;
	public static Connector getConnection(){
		return SqlConnector.getInstance();
	}
}
