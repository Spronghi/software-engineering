package com.carloan.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.carloan.business.model.Location;
import com.carloan.integration.database.ConnectorFactory;
import com.carloan.integration.util.Replacer;
import com.carloan.presentation.factory.FXAlert;

class LocationDAO implements DAO<Location> {
	private static final String SELECT = "SELECT `id`,`city`,`postal_no`,`road` FROM location";
	private static final String SELECT_FROM_ID = "SELECT `id`,`city`,`postal_no`,`road` FROM location WHERE ID=?";
	private static final String INSERT = "INSERT INTO location(city,postal_no,road) VALUES ('?','?','?')";
	private static final String DELETE = "DELETE FROM location WHERE id=?";
	private static final String UPDATE = "UPDATE location SET city='?',postal_no='?',road='?' WHERE id=?";
	
	private Location setLocation(ResultSet rs) throws SQLException{
		Location location = new Location();
        location.setId(rs.getInt(1));
        location.setCity(rs.getString(2));
        location.setPostalNo(rs.getString(3));
        location.setRoad(rs.getString(4));
        return location;
	}
	@Override
	public Location get(int id) {
		String query = SELECT_FROM_ID;
        query = Replacer.replaceFirst(query, id);

        ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
        try {
            if(rs.next()) {
                return setLocation(rs);
            }
        } catch (SQLException e) {
            FXAlert.warning(e,"Database Error");
        }
        return null;
	}
	@Override
	public List<Location> getAll() {
		List<Location> locations = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT);
        try {
            while(rs.next()) {
                locations.add(setLocation(rs));
            }
        } catch (SQLException e) {
            FXAlert.warning(e,"Database Error");
        }
        return locations;
	}
	@Override
	public void create(Location location) {
		String query = INSERT;
        query = Replacer.replaceFirst(query, location.getCity());
        query = Replacer.replaceFirst(query, location.getPostalNo());
        query = Replacer.replaceFirst(query, location.getRoad());
        location.setId(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void update(Location location) {
		String query = UPDATE;
        query = Replacer.replaceFirst(query, location.getCity());
        query = Replacer.replaceFirst(query, location.getPostalNo());
        query = Replacer.replaceFirst(query, location.getRoad());
        query = Replacer.replaceFirst(query, location.getId());
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Location location) {
		String query = DELETE;
        query = Replacer.replaceFirst(query, location.getId());
        ConnectorFactory.getConnection().executeUpdate(query);
	}
}
