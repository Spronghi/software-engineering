package com.carloan.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.carloan.business.model.Location;
import com.carloan.integration.database.ConnectorFactory;

class LocationDAO extends DAO<Location> {
	private static final String SELECT_LOCATION = "SELECT * FROM location";
	private static final String SELECT_LOCATION_FROM_ID = "SELECT * FROM location WHERE ID=?";
	private static final String INSERT_LOCATION = "INSERT INTO location(city,postal_no,road) VALUES ('?','?','?')";
	private static final String DELETE_LOCATION = "DELETE FROM location WHERE id=?";
	private static final String UPDATE_LOCATION = "UPDATE location SET city='?',postal_no='?',road='?' WHERE id=?";
	
	private Location setLocation(ResultSet rs) throws SQLException{
		Location location = new Location(rs.getString(2), rs.getString(3), rs.getString(4));
		location.setId(rs.getInt(1));
		return location;
	}
	@Override
	public Location get(int id){
		String query = SELECT_LOCATION_FROM_ID.replaceFirst("[?]", Integer.toString(id));
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
		try {
			if(rs.next())
				return setLocation(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<Location> getAll() {
		List<Location> locations = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT_LOCATION);
		try {
			while(rs.next())
				locations.add(setLocation(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return locations;
	}
	@Override
	public void create(Location location) {
		String query = INSERT_LOCATION.replaceFirst("[?]", location.getCity())
				.replaceFirst("[?]", location.getPostalNo())
				.replaceFirst("[?]", location.getRoad());
		location.setId(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void update(Location location) {
		String query = UPDATE_LOCATION.replaceFirst("[?]", location.getCity())
				.replaceFirst("[?]", location.getPostalNo())
				.replaceFirst("[?]", location.getRoad())
				.replaceFirst("[?]", Integer.toString(location.getId()));
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Location location) {
		String query = DELETE_LOCATION.replaceFirst("[?]", Integer.toString(location.getId()));
		ConnectorFactory.getConnection().executeUpdate(query);
	}
}
