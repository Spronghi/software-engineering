package com.carloan.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.carloan.business.model.Status;
import com.carloan.integration.database.ConnectorFactory;
import com.carloan.integration.util.Replacer;
import com.carloan.presentation.factory.FXAlert;

class CarStatusDAO implements DAO<Status> {
	private static final String SELECT_ALL = "SELECT `id`,`status` FROM car_status";
	private static final String SELECT_FROM_ID = "SELECT `id`,`status` FROM car_status WHERE id=?";
	private static final String INSERT = "INSERT INTO car_status(status) VALUES ('?')";
	private static final String UPDATE = "UPDATE car_status SET status='?' WHERE id=?";
	private static final String DELETE = "DELETE FROM car_status WHERE id=?";
	
	private Status setStatus(ResultSet rs) throws SQLException{
        Status status = new Status();
        status.setId(rs.getInt(1));
        status.set(rs.getString(2));
		return status;
	}
	@Override
	public List<Status> getAll() {
		List<Status> statusList = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT_ALL);
        try {
            while(rs.next()) {
                statusList.add(setStatus(rs));
            }
        } catch (SQLException e) {
            FXAlert.warning(e,"Database Error");
        }
        return statusList;
	}
	@Override
	public Status get(int id) {
		String query = SELECT_FROM_ID;
        query = Replacer.replaceFirst(query, id);
        ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
        try {
            if(rs.next()) {
                return setStatus(rs);
            }
        } catch (SQLException e) {
            FXAlert.warning(e,"Database Error");
        }
        return null;
	}
	@Override
	public void create(Status status) {
		String query = INSERT;
        query = Replacer.replaceFirst(query, status.get());
        status.setId(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void update(Status status) {
		String query = UPDATE;
        query = Replacer.replaceFirst(query, status.get());
        query = Replacer.replaceFirst(query, status.getId());
        ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Status status) {
		String query = DELETE;
        query = Replacer.replaceFirst(query, status.getId());
        ConnectorFactory.getConnection().executeUpdate(query);
	}
}
