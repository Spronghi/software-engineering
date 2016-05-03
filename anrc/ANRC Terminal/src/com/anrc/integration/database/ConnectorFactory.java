package com.anrc.integration.database;

public class ConnectorFactory {
	public static Connector getConnection(){
		return SqlConnector.getInstance();
	}
}