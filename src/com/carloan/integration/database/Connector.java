package com.carloan.integration.database;

import java.sql.ResultSet;

public interface Connector {
	public ResultSet executeQuery(String query);
	public int executeUpdate(String query);
}
