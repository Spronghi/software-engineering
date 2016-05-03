package com.anrc.integration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.anrc.integration.database.ConnectorFactory;
import com.anrc.integration.util.Replacer;
import com.anrc.model.Detector;

class DetectorDAO implements DAO<Detector> {
	private DetectorTypeDAO typeDAO;
	private RoomDAO roomDAO;
	private static final String SELECT = "SELECT `id`,`name`,`type_id`,`room_id` FROM detector";
	private static final String SELECT_FROM_ID = "SELECT `id`,`name`,`type_id`,`room_id` FROM detector WHERE ID=?";
	private static final String INSERT = "INSERT INTO detector(name,type_id,room_id) VALUES ('?',?,?)";
	private static final String DELETE = "DELETE FROM detector WHERE id=?";
	private static final String UPDATE = "UPDATE detector SET name='?',type_id=?,room_id=? WHERE id=?";
	
	public DetectorDAO() {
		typeDAO = new DetectorTypeDAO();
		roomDAO = new RoomDAO();
	}
	
	private Detector setDetector(ResultSet rs) throws SQLException{
		Detector detector = new Detector();
		detector.setId(rs.getInt(1));
		detector.setName(rs.getString(2));
		detector.setType(typeDAO.get(rs.getInt(3)));
		detector.setRoom(roomDAO.get(rs.getInt(4)));
		return detector;
	}
	@Override
	public List<Detector> getAll() {
		List<Detector> detectors = new ArrayList<>();
		ResultSet rs = ConnectorFactory.getConnection().executeQuery(SELECT);
        try {
            while(rs.next()) {
            	detectors.add(setDetector(rs));
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return detectors;
	}
	@Override
	public Detector get(int id) {
		String query = SELECT_FROM_ID;
        query = Replacer.replaceFirst(query, id);

        ResultSet rs = ConnectorFactory.getConnection().executeQuery(query);
        try {
            if(rs.next()) {
                return setDetector(rs);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return null;
	}
	@Override
	public void create(Detector model) {
		String query = INSERT;
        query = Replacer.replaceFirst(query, model.getName());
        query = Replacer.replaceFirst(query, model.getType().getId());
        query = Replacer.replaceFirst(query, model.getRoom().getId());
        model.setId(ConnectorFactory.getConnection().executeUpdate(query));
	}
	@Override
	public void update(Detector model) {
		String query = UPDATE;
        query = Replacer.replaceFirst(query, model.getName());
        query = Replacer.replaceFirst(query, model.getType().getId());
        query = Replacer.replaceFirst(query, model.getRoom().getId());
		ConnectorFactory.getConnection().executeUpdate(query);
	}
	@Override
	public void delete(Detector model) {
		String query = DELETE;
        query = Replacer.replaceFirst(query, model.getId());
        ConnectorFactory.getConnection().executeUpdate(query);
	}
}
