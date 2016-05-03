package com.anrc.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.anrc.integration.database.ConnectorFactory;
import com.anrc.integration.util.Replacer;
import com.anrc.model.Room;

class RoomDAO implements DAO<Room> {
	private PlaceDAO placeDAO;
	private static final String SELECT = "SELECT `id`,`name`,`place_id` FROM room";
	private static final String SELECT_FROM_ID = "SELECT `id`,`name`,`place_id` FROM room WHERE ID=?";
	private static final String INSERT = "INSERT INTO room(name,place_id) VALUES ('?',?)";
	private static final String DELETE = "DELETE FROM room WHERE id=?";
	private static final String UPDATE = "UPDATE room SET name='?',place_id=? WHERE id=?";
	
	RoomDAO(){
		placeDAO = new PlaceDAO();
	}
	
	private Room setRoom(ResultSet rs) throws SQLException{
		Room room = new Room();
		room.setId(rs.getInt(1));
		room.setName(rs.getString(2));
		room.setPlace(placeDAO.get(rs.getInt(3)));
        return room;
	}
	@Override
	public List<Room> getAll() {
		List<Room> places = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT);
        try {
            while(rs.next()) {
            	places.add(setRoom(rs));
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return places;
	}
	@Override
	public Room get(int id) {
		String query = SELECT_FROM_ID;
        query = Replacer.replaceFirst(query, id);

        ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
        try {
            if(rs.next()) {
                return setRoom(rs);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return null;
	}
	@Override
	public void create(Room model) {
		String query = INSERT;
        query = Replacer.replaceFirst(query, model.getName());
        query = Replacer.replaceFirst(query, model.getPlace().getId());
        model.setId(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void update(Room model) {
		String query = UPDATE;
        query = Replacer.replaceFirst(query, model.getName());
        query = Replacer.replaceFirst(query, model.getPlace().getId());
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Room model) {
		String query = DELETE;
        query = Replacer.replaceFirst(query, model.getId());
        ConnectorFactory.getConnection().executeUpdate(query);
	}
}
