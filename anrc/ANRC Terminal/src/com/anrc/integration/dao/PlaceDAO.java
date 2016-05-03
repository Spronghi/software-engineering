package com.anrc.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.anrc.integration.database.ConnectorFactory;
import com.anrc.integration.util.Replacer;
import com.anrc.model.Place;

class PlaceDAO implements DAO<Place>{
	private LocationDAO locationDAO;
	private static final String SELECT = "SELECT `id`,`name`,`location_id` FROM place";
	private static final String SELECT_FROM_ID = "SELECT `id`,`name`,`location_id` FROM place WHERE ID=?";
	private static final String INSERT = "INSERT INTO place(name,location_id) VALUES ('?',?)";
	private static final String DELETE = "DELETE FROM place WHERE id=?";
	private static final String UPDATE = "UPDATE place SET name='?',location_id=? WHERE id=?";
	
	PlaceDAO(){
		locationDAO = new LocationDAO();
	}
	
	private Place setPlace(ResultSet rs) throws SQLException{
		Place place = new Place();
        place.setId(rs.getInt(1));
        place.setName(rs.getString(2));
        place.setLocation(locationDAO.get(rs.getInt(3)));
        return place;
	}
	@Override
	public List<Place> getAll() {
		List<Place> places = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT);
        try {
            while(rs.next()) {
            	places.add(setPlace(rs));
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return places;
	}
	@Override
	public Place get(int id) {
		String query = SELECT_FROM_ID;
        query = Replacer.replaceFirst(query, id);

        ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
        try {
            if(rs.next()) {
                return setPlace(rs);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return null;
	}
	@Override
	public void create(Place model) {
		String query = INSERT;
        query = Replacer.replaceFirst(query, model.getName());
        query = Replacer.replaceFirst(query, model.getLocation().getId());
        model.setId(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void update(Place model) {
		String query = UPDATE;
        query = Replacer.replaceFirst(query, model.getName());
        query = Replacer.replaceFirst(query, model.getLocation().getId());
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Place model) {
		String query = DELETE;
        query = Replacer.replaceFirst(query, model.getId());
        ConnectorFactory.getConnection().executeUpdate(query);
	}
}
