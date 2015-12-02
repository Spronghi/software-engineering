package com.carloan.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.carloan.business.model.Status;
import com.carloan.integration.database.ConnectorFactory;

public class CarStatusDAO extends DAO<Status> {
	private static final String SELECT_ALL = "SELECT * FROM car_status";
	private static final String SELECT_FROM_ID = "SELECT * FROM car_status WHERE id=?";
	private static final String INSERT = "INSERT INTO car_status(status) VALUES ('?')";
	private static final String UPDATE = "UPDATE car_status SET status='?' WHERE id=?";
	private static final String DELETE = "DELETE FROM car_status WHERE id=?";
	
	private Status setStatus(ResultSet rs) throws SQLException{
		return new Status(rs.getInt(1),rs.getString(2));
	}
	@Override
	public List<Status> getAll() {
		List<Status> statusList = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT_ALL);
		try {
			while(rs.next())
				statusList.add(setStatus(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statusList;
	}
	@Override
	public Status get(int id) {
		String query = SELECT_FROM_ID.replaceFirst("[?]", Integer.toString(id));
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
		try {
			if(rs.next())
				return setStatus(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public void create(Status status) {
		String query = INSERT.replaceFirst("[?]", status.get());
		status.setId(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void update(Status status) {
		String query = UPDATE.replaceFirst("[?]", status.get())
				.replaceFirst("[?]", Integer.toString(status.getId()));
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Status status) {
		String query = DELETE.replaceFirst("[?]", Integer.toString(status.getId()));
		ConnectorFactory.getConnection().executeUpdate(query);		
	}
}
